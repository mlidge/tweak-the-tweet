package com.example.tttmockup;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class ReviewTweetActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_review_tweet);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.review_tweet, menu);
		return true;
	}

}
