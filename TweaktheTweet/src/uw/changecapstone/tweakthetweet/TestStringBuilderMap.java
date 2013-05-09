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

public class TestStringBuilderMap extends Activity {

	private String tweet, disaster;
	private TextView char_count;
	private EditText location_text, test_tweet;
	public final static String LOCATION_TEXT = "uw.changecapstone.tweakthetweet.MESSAGE";
	
	private final TextWatcher charCountWatcher = new TextWatcher() {
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			char_count.setText(String.valueOf(140 - tweet.length() - " #loc ".length()) + " characters left in tweet");
		}

		public void onTextChanged(CharSequence s, int start, int before, int count) {
			char_count.setText(String.valueOf(140 - tweet.length() - " #loc ".length() - s.length()) + " characters left in tweet");
		}

		@Override
		public void afterTextChanged(Editable arg0) {
			// TODO Auto-generated method stub
		}

	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_string_builder_map);
		
		Bundle bundle = getIntent().getExtras();
		tweet = bundle.getString("tweet");
		disaster = bundle.getString("disaster");
		
//		test_tweet = (EditText) findViewById(R.id.test_tweet);
//		test_tweet.setText(tweet);
//		
//		// Disable input for test_tweet: It's only there to display the current tweet
//		test_tweet.setEnabled(false);
//		test_tweet.setFocusable(false);
		
		char_count = (TextView) findViewById(R.id.char_count);
		char_count.setText(String.valueOf(140 - tweet.length() - " #loc ".length()) + " characters left in tweet");
		location_text = (EditText) findViewById(R.id.edit_message);
		location_text.addTextChangedListener(charCountWatcher);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.test_string_builder_map, menu);
		return true;
	}
	
	
	public void showMap(View view){
		Intent intent = new Intent(this, MapDisplayActivity.class);
		startActivity(intent);
	}
	/*when the user clicks the "Enter" button, 
	 * we are going to read the textfield content and 
	 * do some validity checks before we show/zoom map*/
	public void readLocationMessage(View view){
		Intent intent = new Intent(this, LocationAndMapActivity.class);
	    String message = location_text.toString();
	    intent.putExtra(LOCATION_TEXT, message);
	    startActivity(intent);
	}

	
	public void nextViewCategory(View view){
		// In case the user backed, we don't want to accidentally duplicate strings, so we pull from the bundle again
		Bundle bundle = getIntent().getExtras();
		tweet = bundle.getString("tweet");
		//commented the following line (Mussie)
		//tweet += " #loc " + location_text.getText().toString();
		EditText editText = (EditText) findViewById(R.id.edit_message);
	    String message = editText.getText().toString();
		tweet += " #loc " + message;
		Intent i = new Intent(this, TestStringBuilderCategory.class);
		i.putExtra("tweet", tweet);
		i.putExtra("disaster", disaster);
		startActivity(i);
	}

}
