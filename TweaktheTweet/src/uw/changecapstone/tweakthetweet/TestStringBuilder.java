package uw.changecapstone.tweakthetweet;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.app.AlertDialog;

public class TestStringBuilder extends Activity implements OnItemSelectedListener {

	private Spinner disaster_tag_spinner, event_tag_spinner;
	private String disaster_tag;
	private EditText test_string, city_name;
	private String tweet;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_string_builder);
		
		disaster_tag_spinner = (Spinner) findViewById(R.id.disaster_tag_spinner);
		event_tag_spinner = (Spinner) findViewById(R.id.event_tag_spinner);
		city_name = (EditText) findViewById(R.id.city_name);
		test_string = (EditText) findViewById(R.id.test_text);
		
		// Set Click Listener
		disaster_tag_spinner.setOnItemSelectedListener(this);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.test_string_builder, menu);
		return true;
	}
	
	
	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		//addEventTagsOnSpinner();
		List<String> listA = new ArrayList<String>();
		List<String> listB = new ArrayList<String>();
		listA.add("Alpha");
		listA.add("Beta");
		listA.add("Gamma");
		listB.add("One");
		listB.add("Two");
		listB.add("Three");
		
		ArrayAdapter<String> dataAdapter = null;
		if (disaster_tag_spinner.getSelectedItem().toString().equals("Earthquake") &&
				city_name.getText().toString().equals("Seattle")){
			dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listA);
			dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			event_tag_spinner.setAdapter(dataAdapter);
			
		} else if (disaster_tag_spinner.getSelectedItem().toString().equals("Flood") &&
				city_name.getText().toString().equals("Seattle")){
			dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listB);
			dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			event_tag_spinner.setAdapter(dataAdapter);
		}
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public void addDisasterOnSpinner() {
		
		List<String> list = new ArrayList<String>();
		list.add("Flood");
		list.add("Earthquake");
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		disaster_tag_spinner.setAdapter(dataAdapter);
	}
	
	public void addEventTagsOnSpinner() {
		List<String> listA = new ArrayList<String>();
		List<String> listB = new ArrayList<String>();
		listA.add("Alpha");
		listA.add("Beta");
		listA.add("Gamma");
		listB.add("One");
		listB.add("Two");
		listB.add("Three");
		
		ArrayAdapter<String> dataAdapter = null;
		if (disaster_tag_spinner.getSelectedItem().toString().equals("Earthquake") &&
				city_name.getText().toString().equals("Seattle")){
			dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listA);
			
		} else if (disaster_tag_spinner.getSelectedItem().toString().equals("Flood") &&
				city_name.getText().toString().equals("Seattle")){
			
			dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listB);
		}
		
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		event_tag_spinner.setAdapter(dataAdapter);
		
	}
	
	public void buildString(View view) {
		//disaster_tag = disaster_tag_spinner.getSelectedItem().toString();
		//disaster_description_string = disaster_description.getText().toString();
		//test_string.setText("#"+disaster_tag + " " + disaster_description_string, TextView.BufferType.NORMAL);

	}
	
	public void nextViewCategory(View view){
		if (event_tag_spinner.getSelectedItem()==null){
			AlertDialog alertDialog = new AlertDialog.Builder(this).create();
			alertDialog.setTitle("Error");
			alertDialog.setMessage("Please select an event");
			// -1 = BUTTON_POSITIVE = a positive button?
			alertDialog.setButton(-1, "OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					// here you can add functions
				}
			});
			alertDialog.show();
		} else {
			tweet += event_tag_spinner.getSelectedItem().toString();
			Intent i = new Intent(this, TestStringBuilderCategory.class);
			i.putExtra("tweet", tweet);
			startActivity(i);
		}
	}


}
