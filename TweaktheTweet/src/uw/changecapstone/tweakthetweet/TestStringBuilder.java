package uw.changecapstone.tweakthetweet;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.app.AlertDialog;

public class TestStringBuilder extends Activity implements OnItemSelectedListener {

	private Spinner disaster_tag_spinner;
	private ListView disaster_list;
	private String disaster_tag;
	private EditText city_name;
	private EditText test_tweet;
	private String tweet;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_string_builder);
		
		disaster_tag_spinner = (Spinner) findViewById(R.id.disaster_tag_spinner);
		city_name = (EditText) findViewById(R.id.city_name);
		test_tweet = (EditText) findViewById(R.id.test_tweet);
		disaster_list = (ListView) findViewById(R.id.list);
		disaster_tag = "";
		tweet = "";
		
		// Disable input for test_tweet: It's only there to display the current tweet
		test_tweet.setEnabled(false);
		test_tweet.setFocusable(false);
		
		// Set Click Listener
		disaster_tag_spinner.setOnItemSelectedListener(this);

		disaster_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View v, int position,
					long id) {
				
				disaster_tag = (String) adapter.getItemAtPosition(position);
				test_tweet.setText(disaster_tag);
			}
		
		});
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
//		//addEventTagsOnSpinner();
//		List<String> listA = new ArrayList<String>();
//		List<String> listB = new ArrayList<String>();
//		listA.add("Alpha");
//		listA.add("Beta");
//		listA.add("Gamma");
//		listB.add("One");
//		listB.add("Two");
//		listB.add("Three");
//		
//		ArrayAdapter<String> dataAdapter = null;
//		if (disaster_tag_spinner.getSelectedItem().toString().equals("Earthquake") &&
//				city_name.getText().toString().equals("Seattle")){
//			dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listA);
//			dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//			event_tag_spinner.setAdapter(dataAdapter);
//			
//		} else if (disaster_tag_spinner.getSelectedItem().toString().equals("Flood") &&
//				city_name.getText().toString().equals("Seattle")){
//			dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listB);
//			dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//			event_tag_spinner.setAdapter(dataAdapter);
//		}
		
		//Create adapter
		if (disaster_tag_spinner.getSelectedItem().toString().equals("Earthquake") && 
				city_name.getText().toString().equals("Seattle")){
			ListAdapter adapter = createAdapter();
			disaster_list.setAdapter(adapter);
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
	
//	//Automatically switch to next screen no matter what button is clicked (for test mockup purposes)
//    @Override 
//    public void onListItemClick(ListView l, View v, int position, long id) {
//    	tweet += l.getSelectedItem().toString();
//		Intent i = new Intent(this, TestStringBuilderCategory.class);
//		i.putExtra("tweet", tweet);
//		startActivity(i);
//        //Intent i = new Intent(getApplicationContext(), LocationActivity.class);
//        //startActivity(i);
//    }
	
//	public void addDisasterOnSpinner() {
//		
//		List<String> list = new ArrayList<String>();
//		list.add("Flood");
//		list.add("Earthquake");
//		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
//		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//		disaster_tag_spinner.setAdapter(dataAdapter);
//	}
	
	
	public void nextViewLocation(View view){
//		if (event_tag_spinner.getSelectedItem()==null){
//			AlertDialog alertDialog = new AlertDialog.Builder(this).create();
//			alertDialog.setTitle("Error");
//			alertDialog.setMessage("Please select an event");
//			// -1 = BUTTON_POSITIVE = a positive button?
//			alertDialog.setButton(-1, "OK", new DialogInterface.OnClickListener() {
//				public void onClick(DialogInterface dialog, int which) {
//					// here you can add functions
//				}
//			});
//			alertDialog.show();
//		} else {
		// In case the user backed, we don't want to accidentally duplicate an event tag
		tweet = "";
		tweet += disaster_tag;
		Intent i = new Intent(this, TestStringBuilderLocation.class);
		i.putExtra("tweet", tweet);
		startActivity(i);
//		}
	}
	
	protected ListAdapter createAdapter()
    {
    	//Test data for initial event list
    	//Read this in from somewhere else
    	String[] testData = new String[] {
    			"#TestEvent1",
    			"#TestEvent2",
    			"#TestEvent3",
    			"#TestEvent4",
    			"#TestEvent5",
    			"#TestEvent6",
    	};
 
    	// Create a String array adapter using the testData values
    	ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, testData);
 
    	return adapter;
    }


}
