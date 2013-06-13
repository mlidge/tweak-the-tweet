package uw.changecapstone.tweakthetweet;

import android.os.Bundle;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

/**
 * TweetSentActivity displays the sent tweet to the user and lets the user choose to create 
 * a new report. 
 * 
 * @author Christina Quan
 *
 */
public class TweetSentActivity extends CustomWindow {
	String tweet;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_string_builder_tweet_sent);
		this.title.setText("Tweet Sent");
		
		Bundle bundle = getIntent().getExtras();
		tweet = bundle.getString("tweet");
		
		EditText finalTweet = (EditText) findViewById(R.id.final_tweet_box);
		finalTweet.setText(tweet);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.test_string_builder_tweet_sent, menu);
		return true;
	}

	public void nextViewHome(View view) {
		Intent i = new Intent(this, StartActivity.class);
		startActivity(i);
		
	}
}
