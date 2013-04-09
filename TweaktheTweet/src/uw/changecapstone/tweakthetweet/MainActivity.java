package uw.changecapstone.tweakthetweet;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.webkit.WebView;

public class MainActivity extends Activity {


	private final static int ACTIVITY_COMPOSE = 5;
	final static String TWEET_STRING = "TWEET_STRING";
	
	WebView webview;
	
	SharedPreferences pref; 
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.d("COMPOSE", "activity result");
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
		pref = PreferenceManager.getDefaultSharedPreferences(this);
		
	}
	
	

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
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
		Log.d("COMPOSE", "startig compose");
		startActivityForResult(intent, ACTIVITY_COMPOSE);
	}
	
	public void authenticateTwitter(View view) {
		Intent i = new Intent(this, OAuthTwitterActivity.class);
		startActivity(i);
	}
		
		

}
