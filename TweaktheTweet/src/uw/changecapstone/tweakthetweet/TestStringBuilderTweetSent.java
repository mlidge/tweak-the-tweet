package uw.changecapstone.tweakthetweet;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class TestStringBuilderTweetSent extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_string_builder_tweet_sent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.test_string_builder_tweet_sent, menu);
		return true;
	}

}
