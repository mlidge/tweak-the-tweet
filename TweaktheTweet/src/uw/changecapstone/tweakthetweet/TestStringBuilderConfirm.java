package uw.changecapstone.tweakthetweet;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class TestStringBuilderConfirm extends Activity {

	private EditText test_tweet, add_details;
	private TextView char_count;
	private String tweet, final_tweet;
	
	private final TextWatcher charCountWatcher = new TextWatcher() {
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			char_count.setText(String.valueOf(140 - tweet.length()) + " characters left in tweet");
		}
	
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			final_tweet = tweet + " " + s;
			char_count.setText(String.valueOf(140 - final_tweet.length()) + " characters left in tweet");
			test_tweet.setText(final_tweet);
		}
	
		@Override
		public void afterTextChanged(Editable arg0) {
			// TODO Auto-generated method stub
		}
	
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_string_builder_confirm);
		
		Bundle bundle = getIntent().getExtras();
		tweet = bundle.getString("tweet");
		final_tweet = tweet;
		
		test_tweet = (EditText) findViewById(R.id.test_tweet);
		test_tweet.setText(tweet);
		
		char_count = (TextView) findViewById(R.id.char_count);
		char_count.setText(String.valueOf(140 - tweet.length()) + " characters left in tweet");
		add_details = (EditText) findViewById(R.id.additional_details);
		
		add_details.addTextChangedListener(charCountWatcher);
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
