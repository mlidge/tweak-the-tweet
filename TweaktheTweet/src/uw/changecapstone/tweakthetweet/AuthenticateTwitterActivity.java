package uw.changecapstone.tweakthetweet;

import java.util.concurrent.ExecutionException;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * AuthenticateTwitterActivity handles authenticating with twitter
 * so that tweets can be sent in the main application. 
 * @author Mary Jones
 */
public class AuthenticateTwitterActivity extends Activity {

	// Constants to access consumer keys from metadata
	static String TWITTER_CONSUMER_KEY = "twitterconsumerkey"; 
	static String TWITTER_CONSUMER_SECRET = "twitterconsumersecret"; 

	// Constants to access preferences
	static String PREFERENCE_NAME = "twitter_oauth";
	static final String PREF_KEY_OAUTH_TOKEN = "oauth_token";
	static final String PREF_KEY_OAUTH_SECRET = "oauth_token_secret";
	static final String PREF_KEY_TWITTER_LOGIN = "isTwitterLoggedIn";

	// The redirect url to be captured
	static final String TWITTER_CALLBACK_URL = "tttcallback://connect";

	// Twitter oauth urls
	static final String URL_TWITTER_AUTH = "auth_url";
	static final String URL_TWITTER_OAUTH_VERIFIER = "oauth_verifier";
	static final String URL_TWITTER_OAUTH_TOKEN = "oauth_token";

	private static Twitter twitter;
	private static RequestToken requestToken;
	private static SharedPreferences pref;
	private String twitterConsumerKey;
	private String twitterConsumerSecret;
	private String verifier;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		
		pref = PreferenceManager.getDefaultSharedPreferences(this);
		setContentView(R.layout.activity_authtwitter);
		// Ensure twitter is not logged in before trying to authenticate
		if (!isTwitterLoggedInAlready()) {
			ApplicationInfo ai;
			try {
				// retrieve the consumer keys from the application metadata
				ai = getPackageManager().getApplicationInfo(this.getPackageName(), PackageManager.GET_META_DATA);
				Bundle metadata = ai.metaData;
				twitterConsumerKey = metadata.getString(TWITTER_CONSUMER_KEY);
				twitterConsumerSecret = metadata.getString(TWITTER_CONSUMER_SECRET);

			} catch (NameNotFoundException e) {
				e.printStackTrace();
			}
			// Begin a loading dialog and authenticate to Twitter
			// in an asynchronous task (web requests cannot be on the 
			// UI thread)
			TwitterAuthenticate task = new TwitterAuthenticate();
			try {
				// Wait for the authentication task to complete so that we can
				// use the authentication url
				String resultUrl = task.execute(new String[] {null}).get();
				
				WebView webview = (WebView)findViewById(R.id.webview);
				webview.getSettings().setJavaScriptEnabled(true);
	            webview.getSettings().setDomStorageEnabled(true);
	            webview.getSettings().setSavePassword(false);
	            webview.getSettings().setSaveFormData(false);
	            webview.getSettings().setSupportZoom(false);
	            
	            // The custom webview client provides the ability to capture the
	            // redirect url and return to the application
	            webview.setWebViewClient(new WebViewClient(){

	            	
	            	public boolean shouldOverrideUrlLoading(WebView view, String url) {
	            		
	            		findViewById(R.id.webview).setVisibility(View.GONE);
            			findViewById(R.id.webload).setVisibility(View.VISIBLE);
	            		
            			
	            		Uri uri = Uri.parse(url);
	            		// if the url is the callback url (loaded after a successful login)
	            		// parse out the verification string, and store the 
	            		// authentication credentials
	            		if (uri.toString().contains(TWITTER_CALLBACK_URL)) {
	            			verifier = uri.getQueryParameter( "oauth_verifier" );
	            			StorePreferences storeTask = new StorePreferences();
	        				storeTask.execute(new String[] {verifier});
	            		}
	                    
	                    return true;
	            	}	
	            	
	            	@Override
	                public void onPageFinished(WebView view, String url) {
	                 
	                 super.onPageFinished(view, url);
	                 // Hide the loading page and let the webpage be seen.
	                 findViewById(R.id.webload).setVisibility(View.GONE);
	                 findViewById(R.id.webview).setVisibility(View.VISIBLE);
	                }

	            }
	            	
	            );

	            webview.loadUrl(resultUrl);
				
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			finish();
		}
	}
	
	private boolean isTwitterLoggedInAlready() {
		// return twitter login status from Shared Preferences
		return pref.getBoolean(PREF_KEY_TWITTER_LOGIN, false);
	}

	// Store authentications credentials for a user in the application.
	// This accesses twitter and so must be done in a separate thread.
	private class StorePreferences extends AsyncTask<String, String, String> {
		@Override
		protected String doInBackground(String... params) {
			try {
				// The AccessToken is the credentials returned
				// from the signin process
				AccessToken accessToken;


				accessToken = twitter.getOAuthAccessToken(
						requestToken, params[0]);
				// Shared Preferences
				Editor e = pref.edit();

				// After getting access token, access token secret from Twitter
				// store them in application preferences
				e.putString(PREF_KEY_OAUTH_TOKEN, accessToken.getToken());
				e.putString(PREF_KEY_OAUTH_SECRET,
						accessToken.getTokenSecret());
				// Store login status - true
				e.putBoolean(PREF_KEY_TWITTER_LOGIN, true);
				e.commit();
			}catch (Exception e) {
				e.printStackTrace();
			} 

			return null;
		}

		// Dismiss the progess dialog
		@Override
		protected void onPostExecute(String result) {
		
			finish();
		}
	}

	// TwitterAuthenticate handles the initial authentication and 
	// redirecting to the login page
	private class TwitterAuthenticate extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... urls) {
			
			try {
				// make a twitter instance
				twitter = new TwitterFactory().getInstance();
				// set our applications consumer keys
				twitter.setOAuthConsumer(
						twitterConsumerKey,
						twitterConsumerSecret);

				// get a request token and get the authentication URL to 
				// direct to
				requestToken = twitter.getOAuthRequestToken(
						TWITTER_CALLBACK_URL);
				String resultUrl =requestToken.getAuthenticationURL();
				
				return resultUrl;

			}catch (TwitterException e) {
				e.printStackTrace();
			} 
			return null;
			
		}

	}

}
