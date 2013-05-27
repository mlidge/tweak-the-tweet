package uw.changecapstone.tweakthetweet;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class TestStringBuilderTweetSent extends CustomWindow {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_string_builder_tweet_sent);
		this.title.setText("Tweet Sent");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.test_string_builder_tweet_sent, menu);
		return true;
	}

	public void nextViewHome(View view) {
		Intent i = new Intent(this, MainActivity.class);
		startActivity(i);
		
	}
}
