package uw.changecapstone.tweakthetweet;

import java.io.File;

import twitter4j.GeoLocation;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.ConfigurationBuilder;
import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class TweetActivity extends Activity implements DialogListener{
	private static String TWITTER_CONSUMER_KEY = "twitterconsumerkey"; // place your consumer key here
	private static String TWITTER_CONSUMER_SECRET = "twitterconsumersecret"; // place your consumer secret here
	// Preference Constants
	
	private static final String PREF_KEY_OAUTH_TOKEN = "oauth_token";
	private static final String PREF_KEY_OAUTH_SECRET = "oauth_token_secret";
	
	
	private static final String PREF_KEY_TWITTER_LOGIN = "isTwitterLoggedIn";
	private static final String LOGIN_DIALOG_TAG = "logindialog";
	private static final String NETWORK_DIALOG_TAG = "networkdialog";
	private static final String PHOTO_PATH = "photopath";
	private static final String GEO_LOC = "geolocation";
	private static final String SHORT_CODE = "40404";
	private static final String HAS_PHOTO = "hasphoto";
	private String tweet;
	private SharedPreferences pref;
	private boolean geoLocation;
	private static final String LAT = "uw.changecapstone.tweakthetweet.latitude";
	private static final String LONG = "uw.changecapstone.tweakthetweet.longitude";
	private String latitude;
	private String longitude;
	private String twitterConsumerKey;
	private String twitterConsumerSecret;
	private String photoPath;
	private boolean hasPhoto;
	private boolean data;
	
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
		hasPhoto = intent.getBooleanExtra(HAS_PHOTO, false);
		photoPath = (hasPhoto) ? intent.getStringExtra(PHOTO_PATH) : "";
		t.setText(tweet);
		pref = PreferenceManager.getDefaultSharedPreferences(this);
		data = pref.getBoolean("data", true);
		checkNetworkStatus();
	}
	
	public void sendTweet(View view) {
		if (data) {
			dataTweet();
		} else {
			smsTweet();
		}
	}
	
	private void dataTweet() {
		checkLogInStatus();
		try {
			ApplicationInfo ai = getPackageManager().getApplicationInfo(this.getPackageName(), PackageManager.GET_META_DATA);
			Bundle metadata = ai.metaData;
			twitterConsumerKey = metadata.getString(TWITTER_CONSUMER_KEY);
			twitterConsumerSecret = metadata.getString(TWITTER_CONSUMER_SECRET);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		UpdateTwitterStatus updateTask = new UpdateTwitterStatus();
 		updateTask.execute(new String[] {tweet, latitude, longitude, photoPath});
	}
	
	private void smsTweet() {
		SmsManager smsManager = SmsManager.getDefault();
		smsManager.sendTextMessage(SHORT_CODE, null, tweet, null, null);
		Toast.makeText(getApplicationContext(), "SMS Sent!",
					Toast.LENGTH_LONG).show();
	}
	
	private void checkNetworkStatus() {
		ConnectivityManager cm =
		        (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
		 
		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		boolean isConnected = activeNetwork.isConnectedOrConnecting();
		if (!isConnected) {
			DialogFragment network = new NetworkDialogFragment();
			network.show(getFragmentManager(), NETWORK_DIALOG_TAG);
		} else {
			data = true;;
		}
		
	}
	
	private void checkLogInStatus() {
	  if (!pref.getBoolean(PREF_KEY_TWITTER_LOGIN, false)) {
		 DialogFragment logIn = new LoginDialogFragment();
		 logIn.show(getFragmentManager(), LOGIN_DIALOG_TAG);
	  }
	}
	

	@Override
	public void onDialogPositiveClick(DialogFragment dialog) {
		String tag = dialog.getTag();
		if (tag.equals(LOGIN_DIALOG_TAG)) {
			Intent i = new Intent(this, OAuthTwitterActivity.class);
			startActivity(i);
		} else if (tag.equals(NETWORK_DIALOG_TAG)) {
			data = false;
		}
		
	}
	@Override
	public void onDialogNegativeClick(DialogFragment dialog) {
		
		
	}
	
	/*
	 * Http requests must be done on separate thread 
	 */
	
	private class UpdateTwitterStatus extends AsyncTask<String, String, String> {

		
		protected String doInBackground(String... args) {
			Log.d("Tweet Text", "> " + args[0]);
			String status = args[0];
			double lat = Double.parseDouble(args[1]);
			double longitude = Double.parseDouble(args[2]);
			try	{
				ConfigurationBuilder builder = new ConfigurationBuilder();
				builder.setOAuthConsumerKey(twitterConsumerKey);
				builder.setOAuthConsumerSecret(twitterConsumerSecret);
				
				// Access Token 
				String access_token = pref.getString(PREF_KEY_OAUTH_TOKEN, "");
				// Access Token Secret
				String access_token_secret = pref.getString(PREF_KEY_OAUTH_SECRET, "");
				StatusUpdate newStatus = new StatusUpdate(status);
				if (geoLocation) {
					GeoLocation location = new GeoLocation(lat, longitude);
					newStatus.setLocation(location);
				}
				if (hasPhoto) {
					String filePath = args[3];
					File photo = new File(filePath);
					newStatus.setMedia(photo);
				}
				AccessToken accessToken = new AccessToken(access_token, access_token_secret);
				Twitter twitter = new TwitterFactory(builder.build()).getInstance(accessToken);
				
				// Update status
				twitter4j.Status response = twitter.updateStatus(newStatus);
				
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

