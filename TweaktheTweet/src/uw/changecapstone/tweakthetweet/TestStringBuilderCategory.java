package uw.changecapstone.tweakthetweet;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

public class TestStringBuilderCategory extends Activity {

	private Spinner category_tag_spinner;
	private String tweet;
	private EditText add_descript, test_tweet;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_string_builder_category);
		
		Bundle bundle = getIntent().getExtras();
		tweet = bundle.getString("tweet");
		
		category_tag_spinner = (Spinner) findViewById(R.id.category_tag_spinner);
		add_descript = (EditText) findViewById(R.id.additional_details_text);
		test_tweet = (EditText) findViewById(R.id.test_tweet);
		test_tweet.setText(tweet);
		
		// Disable input for test_tweet: It's only there to display the current tweet
		test_tweet.setEnabled(false);
		test_tweet.setFocusable(false);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.test_string_builder, menu);
		return true;
	}
	
	public void nextViewLocation(View view){
//		if (category_tag_spinner.getSelectedItem()==null){
//			AlertDialog alertDialog = new AlertDialog.Builder(this).create();
//			alertDialog.setTitle("Error");
//			alertDialog.setMessage("Please select a category");
//			// -1 = BUTTON_POSITIVE = a positive button?
//			alertDialog.setButton(-1, "OK", new DialogInterface.OnClickListener() {
//				public void onClick(DialogInterface dialog, int which) {
//					// here you can add functions
//				}
//			});
//			alertDialog.show();
//		} else {
		// In case the user backed, we don't want to accidentally duplicate strings, so we pull from the bundle again
		Bundle bundle = getIntent().getExtras();
		tweet = bundle.getString("tweet");
		tweet += " " + category_tag_spinner.getSelectedItem().toString();
		tweet += " " + add_descript.getText().toString();
		Intent i = new Intent(this, TestStringBuilderConfirm.class);
		i.putExtra("tweet", tweet);
		startActivity(i);
//		}
	}
}
