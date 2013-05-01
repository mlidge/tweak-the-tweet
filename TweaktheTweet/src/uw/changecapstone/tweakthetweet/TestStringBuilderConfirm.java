package uw.changecapstone.tweakthetweet;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class TestStringBuilderConfirm extends Activity {

	private EditText test_tweet;
	private String tweet;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_string_builder_confirm);
		
		Bundle bundle = getIntent().getExtras();
		tweet = bundle.getString("tweet");
		
		test_tweet = (EditText) findViewById(R.id.test_tweet);
		test_tweet.setText(tweet);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.test_string_builder_confirm, menu);
		return true;
	}
	
	public void nextViewHome(View view) {
		Intent i = new Intent(this, MainActivity.class);
		startActivity(i);
		
	}

}
