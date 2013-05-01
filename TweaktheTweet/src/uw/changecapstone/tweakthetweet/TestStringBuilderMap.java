package uw.changecapstone.tweakthetweet;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class TestStringBuilderMap extends Activity {

	private String tweet;
	private EditText location_text, test_tweet;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_string_builder_map);
		
		Bundle bundle = getIntent().getExtras();
		tweet = bundle.getString("tweet");
		
		location_text = (EditText) findViewById(R.id.location_text);
		test_tweet = (EditText) findViewById(R.id.test_tweet);
		test_tweet.setText(tweet);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.test_string_builder_map, menu);
		return true;
	}
	
	public void nextViewConfirm(View view){
		tweet += " #loc " + location_text.getText().toString();
		Intent i = new Intent(this, TestStringBuilderConfirm.class);
		i.putExtra("tweet", tweet);
		startActivity(i);
	}

}
