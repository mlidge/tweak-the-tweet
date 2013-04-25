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
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_string_builder_category);
		
		category_tag_spinner = (Spinner) findViewById(R.id.disaster_tag_spinner);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.test_string_builder, menu);
		return true;
	}
	
	public void nextViewLocation(View view){
		if (category_tag_spinner.getSelectedItem()==null){
			AlertDialog alertDialog = new AlertDialog.Builder(this).create();
			alertDialog.setTitle("Error");
			alertDialog.setMessage("Please select an issue");
			// -1 = BUTTON_POSITIVE = a positive button?
			alertDialog.setButton(-1, "OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					// here you can add functions
				}
			});
			alertDialog.show();
		} else {
			//tweet += event_tag_spinner.getSelectedItem().toString();
			//Intent i = new Intent(this, TestStringBuilderCategory.class);
			//i.putExtra("tweet", tweet);
			//startActivity(i);
		}
	}
}
