package uw.changecapstone.tweakthetweet;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class TestStringBuilderConfirm extends CustomWindow {

	private EditText test_tweet, add_details, add_time, add_source, add_contact;
	private TextView char_count;
	private String category, tweet, final_tweet;
	private String TIME_TAG = "#time";
	private String SOURCE_TAG = "#src";
	private String CONTACT_TAG = "#cont";		
	
	/*private final TextWatcher charCountWatcher = new TextWatcher() {
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			char_count.setText(String.valueOf(140 - tweet.length()) + " characters left in tweet");
		}
	
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			final_tweet = tweet + " " + s;
			char_count.setText(String.valueOf(140 - final_tweet.length()) + " characters left");
			test_tweet.setText(final_tweet);
		}
	
		@Override
		public void afterTextChanged(Editable arg0) {
			// TODO Auto-generated method stub
		}
	
	}; */
	
	private final TextWatcher charCountWatcher = new TextWatcher() {
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			int crntLength = 140 - final_tweet.length();
			if(crntLength != 1){
				char_count.setText(String.valueOf(140 - tweet.length()) + " characters left in tweet");
			}else{
				char_count.setText(String.valueOf(140 - tweet.length()) + " character left in tweet");
			}
		}
	
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			final_tweet = tweet + " " + s;
			int crntLength = 140 - final_tweet.length();
			if(crntLength < 0){
				char_count.setTextColor(Color.RED);
			}else{
				char_count.setTextColor(Color.BLACK);
			}
			
			if(crntLength != 1){
				char_count.setText(String.valueOf(crntLength) + " characters left");
			}else{
				char_count.setText(String.valueOf(crntLength) + " character left");
			}
		}
	
		@Override
		public void afterTextChanged(Editable arg0) {
			// TODO Auto-generated method stub
		}
	
	};
	
	private final TextWatcher addCategoryText = new TextWatcher() {
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		}
	
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			StringBuilder editedTweet = new StringBuilder(tweet);
			int indexOfCategory = editedTweet.indexOf(category) + category.length();
			editedTweet.insert(indexOfCategory, " " + s);
			tweet = editedTweet.toString();
			test_tweet.setText(tweet);
		}
	
		@Override
		public void afterTextChanged(Editable arg0) {		
			
		}
	
	};
	
	private final TextWatcher addTimeText = new TextWatcher() {
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		}
	
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			StringBuilder editedTweet = new StringBuilder(tweet);
			int indexOfCategory = editedTweet.indexOf(TIME_TAG);
			if(indexOfCategory == -1){
				editedTweet.append(TIME_TAG + " " + s);
			}else{
				editedTweet.insert(indexOfCategory, " " + s);
			}
			tweet = editedTweet.toString();
			test_tweet.setText(editedTweet);
		}
	
		@Override
		public void afterTextChanged(Editable arg0) {
		}
	
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_string_builder_confirm);
		this.title.setText("#more info");
		
		Bundle bundle = getIntent().getExtras();
		category = bundle.getString("category");
		tweet = bundle.getString("tweet");
		final_tweet = tweet;
		
		//Set up main tweet text box
		test_tweet = (EditText) findViewById(R.id.test_tweet);
		test_tweet.setText(tweet);
		test_tweet.setHorizontallyScrolling(false);
		test_tweet.setMaxLines(Integer.MAX_VALUE);		
		test_tweet.addTextChangedListener(charCountWatcher);
		
		//Set up char count
		char_count = (TextView) findViewById(R.id.char_count);
		char_count.setText(String.valueOf(140 - tweet.length()) + " characters left in tweet");
		
		//Set up add details text box
		add_details = (EditText) findViewById(R.id.additional_details);	
		add_details.addTextChangedListener(addCategoryText);
		
		//Set up add time text box
		add_time = (EditText) findViewById(R.id.time_text);
		add_time.addTextChangedListener(addTimeText);
		
		//Set up add source text box
		add_source = (EditText) findViewById(R.id.source_text);
		//add_source.addTextChangedListener(addTimeText);
		
		//Set up add source text box
		add_contact = (EditText) findViewById(R.id.contact_text);
		//add_contact.addTextChangedListener(addTimeText);
		
		//Set instruction text based on category
		TextView instructionText = (TextView) findViewById(R.id.add_more_details_text);
		instructionText.setText("Add more details for your " + category + " category");
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
