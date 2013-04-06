package uw.changecapstone.tweakthetweet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class TweetActivity extends Activity {

	final static String SHORT_CODE = "40404";
	String tweet;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_tweet);
		
		TextView t = (TextView) findViewById(R.id.tweet_string);
		
		Intent intent = getIntent();
		tweet = intent.getStringExtra(MainActivity.TWEET_STRING);
		t.setText(tweet);
	}

	public void sendTweet(View view) {
		 try {
				SmsManager smsManager = SmsManager.getDefault();
				smsManager.sendTextMessage(SHORT_CODE, null, tweet, null, null);
				Toast.makeText(getApplicationContext(), "SMS Sent!",
							Toast.LENGTH_LONG).show();
			  } catch (Exception e) {
				Toast.makeText(getApplicationContext(),
					"SMS faild, please try again later!",
					Toast.LENGTH_LONG).show();
				e.printStackTrace();
			  }
		 }
}

