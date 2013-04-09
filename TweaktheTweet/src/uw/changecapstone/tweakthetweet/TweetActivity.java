package uw.changecapstone.tweakthetweet;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.ConfigurationBuilder;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class TweetActivity extends Activity {
	static String TWITTER_CONSUMER_KEY = "aKvxacsn9CcPme65ZGIJw"; // place your consumer key here
	static String TWITTER_CONSUMER_SECRET = "FHmGqglOorKw1ArGsPJo6XvvbPqHgck360lx4zc"; // place your consumer secret here

	// Preference Constants
	static String PREFERENCE_NAME = "twitter_oauth";
	static final String PREF_KEY_OAUTH_TOKEN = "oauth_token";
	static final String PREF_KEY_OAUTH_SECRET = "oauth_token_secret";
	static final String PREF_KEY_TWITTER_LOGIN = "isTwitterLoggedIn";

	static final String TWITTER_CALLBACK_URL = "oauth://connect";

	// Twitter oauth urls
	static final String URL_TWITTER_AUTH = "auth_url";
	static final String URL_TWITTER_OAUTH_VERIFIER = "oauth_verifier";
	static final String URL_TWITTER_OAUTH_TOKEN = "oauth_token";
	

	final static String TWEET_STRING = "TWEET_STRING";
	
	final static String SHORT_CODE = "40404";
	String tweet;
	SharedPreferences pref;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_tweet);
		
		TextView t = (TextView) findViewById(R.id.tweet_string);
		
		Intent intent = getIntent();
		tweet = intent.getStringExtra(MainActivity.TWEET_STRING);
		t.setText(tweet);
		pref = PreferenceManager.getDefaultSharedPreferences(this);

	}
	public void sendTweet(View view) {
		 try { 
			 /*
			  * choose to use data or sms based on settings
			  */
			 	boolean data = pref.getBoolean("data", true);
			 	if (data) {
			 		UpdateTwitterStatus updateTask = new UpdateTwitterStatus();
			 		updateTask.execute(new String[] {tweet});
			 		
			 	} else {
			 		SmsManager smsManager = SmsManager.getDefault();
					smsManager.sendTextMessage(SHORT_CODE, null, tweet, null, null);
					Toast.makeText(getApplicationContext(), "SMS Sent!",
								Toast.LENGTH_LONG).show();	
			 	}
			 	
			  } catch (Exception e) {
				e.printStackTrace();
			  }
	}
	
	/*
	 * Http requests must be done on separate thread 
	 */
	
	private class UpdateTwitterStatus extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
	

		/**
		 * getting Places JSON
		 * */
		protected String doInBackground(String... args) {
			Log.d("Tweet Text", "> " + args[0]);
			String status = args[0];
			try {
				ConfigurationBuilder builder = new ConfigurationBuilder();
				builder.setOAuthConsumerKey(TWITTER_CONSUMER_KEY);
				builder.setOAuthConsumerSecret(TWITTER_CONSUMER_SECRET);
				
				// Access Token 
				String access_token = pref.getString(PREF_KEY_OAUTH_TOKEN, "");
				// Access Token Secret
				String access_token_secret = pref.getString(PREF_KEY_OAUTH_SECRET, "");
				
				AccessToken accessToken = new AccessToken(access_token, access_token_secret);
				Twitter twitter = new TwitterFactory(builder.build()).getInstance(accessToken);
				
				// Update status
				twitter4j.Status response = twitter.updateStatus(status);
				
				Log.d("Status", "> " + response.getText());
			} catch (TwitterException e) {
				// Error in updating status
				Log.d("Twitter Update Error", e.getMessage());
			}
			return null;
		}

		/**
		 * After completing background task Dismiss the progress dialog and show
		 * the data in UI Always use runOnUiThread(new Runnable()) to update UI
		 * from background thread, otherwise you will get error
		 * **/
		protected void onPostExecute(String file_url) {
			
			
			// updating UI from Background Thread
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					Toast.makeText(getApplicationContext(),
							"Status tweeted successfully", Toast.LENGTH_SHORT)
							.show();
					
				}
			});
		}

	}
	
	
}

