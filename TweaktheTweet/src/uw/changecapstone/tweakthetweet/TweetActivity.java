package uw.changecapstone.tweakthetweet;

import twitter4j.GeoLocation;
import twitter4j.StatusUpdate;
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
	private static String TWITTER_CONSUMER_KEY = "aKvxacsn9CcPme65ZGIJw"; // place your consumer key here
	private static String TWITTER_CONSUMER_SECRET = "FHmGqglOorKw1ArGsPJo6XvvbPqHgck360lx4zc"; // place your consumer secret here

	// Preference Constants
	
	private static final String PREF_KEY_OAUTH_TOKEN = "oauth_token";
	private static final String PREF_KEY_OAUTH_SECRET = "oauth_token_secret";
	
	

	
	private static final String GEO_LOC = "geolocation";
	private static final String SHORT_CODE = "40404";
	private String tweet;
	private SharedPreferences pref;
	private boolean geoLocation;
	private static final String LAT = "uw.changecapstone.tweakthetweet.latitude";
	private static final String LONG = "uw.changecapstone.tweakthetweet.longitude";
	private String latitude;
	private String longitude;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_tweet);
		
		TextView t = (TextView) findViewById(R.id.tweet_string);
		
		Intent intent = getIntent();
		tweet = intent.getStringExtra(MainActivity.TWEET_STRING);
		geoLocation = intent.getBooleanExtra(GEO_LOC, false);
		
		latitude = ((Double)intent.getDoubleExtra(LAT, 0.0)).toString();
		longitude = ((Double)intent.getDoubleExtra(LONG, 0.0)).toString();
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
			 		updateTask.execute(new String[] {tweet, latitude, longitude});
			 		
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
			double lat = Double.parseDouble(args[1]);
			double longitude = Double.parseDouble(args[2]);
			try	{
				ConfigurationBuilder builder = new ConfigurationBuilder();
				builder.setOAuthConsumerKey(TWITTER_CONSUMER_KEY);
				builder.setOAuthConsumerSecret(TWITTER_CONSUMER_SECRET);
				
				// Access Token 
				String access_token = pref.getString(PREF_KEY_OAUTH_TOKEN, "");
				// Access Token Secret
				String access_token_secret = pref.getString(PREF_KEY_OAUTH_SECRET, "");
				StatusUpdate newStatus = new StatusUpdate(status);
				if (geoLocation) {
					GeoLocation location = new GeoLocation(lat, longitude);
					newStatus.setLocation(location);
				}
				AccessToken accessToken = new AccessToken(access_token, access_token_secret);
				Twitter twitter = new TwitterFactory(builder.build()).getInstance(accessToken);
				
				// Update status
				twitter4j.Status response = twitter.updateStatus(status);
				
				Log.d("Status", "> " + response.getText());
			} catch (TwitterException e) {
				// Error in updating status
				Log.d("Twitter Update Error", e.getMessage());
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						Toast.makeText(getApplicationContext(),
								"Tweet Failed", Toast.LENGTH_SHORT)
								.show();
						
					}
				});
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

