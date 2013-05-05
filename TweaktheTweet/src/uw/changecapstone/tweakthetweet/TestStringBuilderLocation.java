package uw.changecapstone.tweakthetweet;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class TestStringBuilderLocation extends Activity {

	private String tweet;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_string_builder_location);
		
		Bundle bundle = getIntent().getExtras();
		tweet = bundle.getString("tweet");
				
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.test_string_builder_location, menu);
		return true;
	}
	
	// commented the "nextViewMap() method and added two methods for 
	// current and previous locations (Mussie)
	/*public void nextViewMap(View view){
		Intent i = new Intent(this, TestStringBuilderMap.class);
		i.putExtra("tweet", tweet);
		startActivity(i);
	}*/
	public void useCurrentLoc(View view){
		Intent i = new Intent(this, TestStringBuilderMap.class);
		i.putExtra("tweet", tweet);
		startActivity(i);
	}
	
	public void usePreviousLoc(View view){
		Intent i = new Intent(this, PreviousLocationActivity.class);
		i.putExtra("tweet", tweet);
		startActivity(i);
	}
}
