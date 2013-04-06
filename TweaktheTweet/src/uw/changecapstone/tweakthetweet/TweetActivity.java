package uw.changecapstone.tweakthetweet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class TweetActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_tweet);
		
		TextView t = (TextView) findViewById(R.id.tweet_string);
		
		Intent intent = getIntent();
		String tweet = intent.getStringExtra(MainActivity.TWEET_STRING);
		t.setText(tweet);
	}
}
