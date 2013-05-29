package uw.changecapstone.tweakthetweet;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

public class TestStringBuilderConfirm extends CustomWindow {

	private EditText test_tweet, add_details, add_time, add_source, add_contact;
	private TextView char_count;
	private String category, tweet, final_tweet;
	int crntLength;
	private final String TIME_TAG = "#time";
	private final String SOURCE_TAG = "#src";
	private final String CONTACT_TAG = "#cont";	
	
	private final static String TWEET_STRING = "TWEET_STRING";
	private static final String PREF_KEY_TWITTER_LOGIN = "isTwitterLoggedIn";
	private static final String LOGIN_DIALOG_TAG = "logindialog";
	private static final String NETWORK_DIALOG_TAG = "networkdialog";
	private static final String PHOTO_PATH = "photopath";
	private static final String GEO_LOC = "geolocation";
	private static final String HAS_PHOTO = "hasphoto";
	private static final String LAT = "uw.changecapstone.tweakthetweet.latitude";
	private static final String LONG = "uw.changecapstone.tweakthetweet.longitude";
	
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
			crntLength = 140 - final_tweet.length();
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
				String[] splitTweet = tweet.split(category);
				
				if(!tweet.contains(category)){
					//if the tweet does not contain the category tag, just append it to the end of the string
					tweet = tweet + " " + category + " " + s;
				}
				
				if(splitTweet.length==1){
					tweet = tweet + " " + s;
				}else{
					if(splitTweet[1].contains("#")){
						//If the tweet contains another hash tag, replace the current category text
						int indexOfNextTag = splitTweet[1].indexOf("#");
						//Toast.makeText(getBaseContext(), splitTweet[1].substring(indexOfNextTag), Toast.LENGTH_SHORT).show();
						tweet = splitTweet[0] + category + " " + s + " " + splitTweet[1].substring(indexOfNextTag);
					}else{
						//If it does not contain another hash tag, just replace the string after category tag
						tweet = splitTweet[0] + category + " " + s;
					}
					test_tweet.setText(tweet);
				}
		}
	
		@Override
		public void afterTextChanged(Editable arg0) {		
			
		}
	
	};
	
	
	private final TextWatcher addTimeText = new TextWatcher() {
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		}
	
		public void onTextChanged(CharSequence s, int start, int before, int count) {
				String[] splitTweet = tweet.split(TIME_TAG);
				if(!tweet.contains(TIME_TAG)){
					//if the tweet does not contain the time tag, just append it to the end of the string
					tweet = tweet + " " + TIME_TAG + " " + s;
				}else{
					if(splitTweet[1].contains("#")){
						//If the tweet contains another hash tag, replace the current time text
						int indexOfNextTag = splitTweet[1].indexOf("#");
						//Toast.makeText(getBaseContext(), splitTweet[1].substring(indexOfNextTag), Toast.LENGTH_SHORT).show();
						tweet = splitTweet[0] + TIME_TAG + " " + s + " " +splitTweet[1].substring(indexOfNextTag);
					}else{
						//If it does not contain another hash tag, just replace the string after time tag
						tweet = splitTweet[0] + TIME_TAG + " " + s;
					}
					test_tweet.setText(tweet);
				}
		}
	
		@Override
		public void afterTextChanged(Editable arg0) {
		}
	
	};
	
	private final TextWatcher addSourceText = new TextWatcher() {
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		}
	
		public void onTextChanged(CharSequence s, int start, int before, int count) {
				String[] splitTweet = tweet.split(SOURCE_TAG);
				if(!tweet.contains(SOURCE_TAG)){
					//if the tweet does not contain the source tag, just append it to the end of the string
					tweet = tweet + " " + SOURCE_TAG + " " + s;
				}else{
					if(splitTweet[1].contains("#")){
						//If the tweet contains another hash tag, replace the current time text
						int indexOfNextTag = splitTweet[1].indexOf("#");
						//Toast.makeText(getBaseContext(), splitTweet[1].substring(indexOfNextTag), Toast.LENGTH_SHORT).show();
						tweet = splitTweet[0] + SOURCE_TAG + " " + s + " " + splitTweet[1].substring(indexOfNextTag);
					}else{
						//If it does not contain another hash tag, just replace the string after time tag
						tweet = splitTweet[0] + SOURCE_TAG + " " + s;
					}
					test_tweet.setText(tweet);
				}
		}
	
		@Override
		public void afterTextChanged(Editable arg0) {
		}
	
	};
	
	private final TextWatcher addContactText = new TextWatcher() {
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		}
	
		public void onTextChanged(CharSequence s, int start, int before, int count) {
				String[] splitTweet = tweet.split(CONTACT_TAG);
				if(!tweet.contains(CONTACT_TAG)){
					//if the tweet does not contain the source tag, just append it to the end of the string
					tweet = tweet + " " + CONTACT_TAG + " " + s;
				}else{
					if(splitTweet[1].contains("#")){
						//If the tweet contains another hash tag, replace the current time text
						int indexOfNextTag = splitTweet[1].indexOf("#");
						//Toast.makeText(getBaseContext(), splitTweet[1].substring(indexOfNextTag), Toast.LENGTH_SHORT).show();
						tweet = splitTweet[0] + CONTACT_TAG + " " + s + " " + splitTweet[1].substring(indexOfNextTag);
					}else{
						//If it does not contain another hash tag, just replace the string after time tag
						tweet = splitTweet[0] + CONTACT_TAG + " " + s;
					}
					test_tweet.setText(tweet);
				}
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
		test_tweet.setOnEditorActionListener(new OnEditorActionListener() {        
		    @Override
		    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		        if(actionId==EditorInfo.IME_ACTION_DONE){
		            //Clear focus when the done button is clicked 
		            test_tweet.clearFocus();
		        }
		    return false;
		    }
		});
		
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
		add_source.addTextChangedListener(addSourceText);
		
		//Set up add contact text box
		add_contact = (EditText) findViewById(R.id.contact_text);
		add_contact.addTextChangedListener(addContactText);
		
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
	
	public void nextViewSent(View view) throws NameNotFoundException {
		if(crntLength<0){
			Toast.makeText(getApplicationContext(), "Your tweet is longer than 140 characters, please shorten it.", Toast.LENGTH_SHORT).show();
		}else{
			
			Intent i = new Intent(this, TweetActivity.class);
			// the tweet
			// the latitude to be embedded
			// the longitude to be embedded
			// if there are coordinates to embed
			// if there is a photo to embed
			// the photo path
			i.putExtra(TWEET_STRING, tweet);
			i.putExtra(LAT, 0.0);
			i.putExtra(LONG, 0.0);
			i.putExtra(GEO_LOC, true);
			i.putExtra(HAS_PHOTO, false);
			i.putExtra(PHOTO_PATH, "");
			startActivity(i);
		}
	}

}
