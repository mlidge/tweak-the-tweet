package uw.changecapstone.tweakthetweet;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

public class OAuthTwitterActivity extends Activity {

	static String TWITTER_CONSUMER_KEY = "aKvxacsn9CcPme65ZGIJw"; // place your consumer key here
	static String TWITTER_CONSUMER_SECRET = "FHmGqglOorKw1ArGsPJo6XvvbPqHgck360lx4zc"; // place your consumer secret here

	// Preference Constants
	static String PREFERENCE_NAME = "twitter_oauth";
	static final String PREF_KEY_OAUTH_TOKEN = "oauth_token";
	static final String PREF_KEY_OAUTH_SECRET = "oauth_token_secret";
	static final String PREF_KEY_TWITTER_LOGIN = "isTwitterLoggedIn";

	static final String TWITTER_CALLBACK_URL = "tttcallback://connect";

	// Twitter oauth urls
	static final String URL_TWITTER_AUTH = "auth_url";
	static final String URL_TWITTER_OAUTH_VERIFIER = "oauth_verifier";
	static final String URL_TWITTER_OAUTH_TOKEN = "oauth_token";
	
	private static Twitter twitter;
	private static RequestToken requestToken;
	private static SharedPreferences pref;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		pref = PreferenceManager.getDefaultSharedPreferences(this);
		Log.d("OAUTH", "starting task");
		if (!isTwitterLoggedInAlready()) {
			TwitterAuthenticate task = new TwitterAuthenticate();
			task.execute(new String[] {null});
		
		} else {
		finish();
		}
		// Get the access token
		
		

		
	}
	protected void onActivityResult(int result, int returnVal, Intent i) {
		
		Log.d("OAUTH", "activity result");
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
	    		
	    		AccessToken accessToken;
	    		
	    			
	    			accessToken = twitter.getOAuthAccessToken(
	    					requestToken, params[0]);
	    			// Shared Preferences
	    			Editor e = pref.edit();

	    			// After getting access token, access token secret
	    			// store them in application preferences
	    			e.putString(PREF_KEY_OAUTH_TOKEN, accessToken.getToken());
	    			e.putString(PREF_KEY_OAUTH_SECRET,
	    					accessToken.getTokenSecret());
	    			// Store login status - true
	    			e.putBoolean(PREF_KEY_TWITTER_LOGIN, true);
	    			long userID = accessToken.getUserId();
	    			User user = twitter.showUser(userID);
	    			String username = user.getName();
	    			e.putString("USERNAME", username);
	    			Log.d("OAUTH", username);
	    			e.commit();
			}catch (Exception e) {
				e.printStackTrace();
			} 
			
	        return null;
	    }

		@Override
		protected void onPostExecute(String result) {
			finish();
		}
	}
	
	private class TwitterAuthenticate extends AsyncTask<String, String, String> {
		   
		String resultUrl;
	
		
	    @Override
	    protected String doInBackground(String... urls) {
	    	TwitterFactory factory = new TwitterFactory();
			twitter = factory.getInstance();
			Log.d("OAUTH", "do in background");
			try {
				twitter = new TwitterFactory().getInstance();
				  twitter.setOAuthConsumer(
				      TWITTER_CONSUMER_KEY,
				     TWITTER_CONSUMER_SECRET);
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
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			Log.d("OAUTH", "starting post");
			Intent i = new Intent(OAuthTwitterActivity.this, TwitterWebView.class);
			i.putExtra("url", resultUrl);

			startActivityForResult(i, 1);
	
			
		}
	}
	    
	    
	         
						
	        
					
	    

}
