package uw.changecapstone.tweakthetweet;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

/*
 * OAuthTwitterActivity handles authenticating twitter with
 * so that tweets can be sent. This is started by the 
 * login dialog
 */
public class OAuthTwitterActivity extends Activity {

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
	private ProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		pref = PreferenceManager.getDefaultSharedPreferences(this);

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
			dialog = ProgressDialog.show(this, "", "Loading");
			TwitterAuthenticate task = new TwitterAuthenticate();
			task.execute(new String[] {null});

		} else {
			finish();
		}

		// Get the access token




	}

	// onActivityResult starts the process of storing credentials
	// after the signin finishes and the user is redirected to the 
	// application
	protected void onActivityResult(int result, int returnVal, Intent i) {
		String verifier = (String) i.getExtras().get("oauth_verifier");
		StorePreferences storeTask = new StorePreferences();
		storeTask.execute(new String[] {verifier});
	}

	private boolean isTwitterLoggedInAlready() {
		// return twitter login status from Shared Preferences
		return pref.getBoolean(PREF_KEY_TWITTER_LOGIN, false);
	}

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
				long userID = accessToken.getUserId();
				e.commit();
			}catch (Exception e) {
				e.printStackTrace();
			} 

			return null;
		}

		// Dismiss the progess dialog
		@Override
		protected void onPostExecute(String result) {
			dialog.dismiss();
			finish();
		}
	}

	// TwitterAuthenticate handles the intial authentication and 
	// redirecting to hte login page
	private class TwitterAuthenticate extends AsyncTask<String, String, String> {

		String resultUrl;

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
				resultUrl =requestToken.getAuthenticationURL();

			}catch (TwitterException e) {
				e.printStackTrace();
			} 

			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			
			super.onPostExecute(result);
			// TwitterWebView handles loading the login url and redirection 
			// back to the application
			Intent i = new Intent(OAuthTwitterActivity.this, TwitterWebView.class);
			i.putExtra("url", resultUrl);

			startActivityForResult(i, 1);


		}
	}








}
