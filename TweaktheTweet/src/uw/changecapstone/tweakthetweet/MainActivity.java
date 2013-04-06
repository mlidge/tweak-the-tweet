package uw.changecapstone.tweakthetweet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

	private final static int ACTIVITY_COMPOSE = 5;
	final static String TWEET_STRING = "TWEET_STRING";
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		String tweet = data.getStringExtra(TWEET_STRING);
		Intent intent = new Intent(this, TweetActivity.class);
		intent.putExtra(TWEET_STRING, tweet);
		startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void startSettings(View view) {
		Intent intent = new Intent(this, SettingsActivity.class);
		startActivity(intent);
	}

	public void printDebug(View view) {
		Intent intent = new Intent(this, DebugActivity.class); 
		startActivity(intent);
	}
	
	public void composeTweet(View view) {
		Intent intent = new Intent(this, ComposeActivity.class);
		startActivityForResult(intent, ACTIVITY_COMPOSE);
	}
}
