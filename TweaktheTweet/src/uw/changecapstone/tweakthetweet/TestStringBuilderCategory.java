package uw.changecapstone.tweakthetweet;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

public class TestStringBuilderCategory extends Activity {

	private String tweet, category;
	//private TextView char_count;
	//private EditText add_details;
	private ListView category_list;
	
//	private final TextWatcher charCountWatcher = new TextWatcher() {
//		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//			char_count.setText(String.valueOf(140 - tweet.length()) + " characters left in tweet");
//		}
//
//		public void onTextChanged(CharSequence s, int start, int before, int count) {
//			char_count.setText(String.valueOf(140 - tweet.length() - " #loc ".length() - s.length()) + " characters left in tweet");
//		}
//
//		@Override
//		public void afterTextChanged(Editable arg0) {
//			// TODO Auto-generated method stub
//		}
//
//	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_string_builder_category);
		
		Bundle bundle = getIntent().getExtras();
		tweet = bundle.getString("tweet");
		String disaster = bundle.getString("disaster");
		
		category_list = (ListView) findViewById(R.id.list);
		category_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View v, int position,
					long id) {
				
				category = (String) adapter.getItemAtPosition(position);
				nextViewDetails(v);
			}
		
		});
		
		// Create adapter
		category_list.setAdapter(createAdapter(disaster));
		
//		char_count = (TextView) findViewById(R.id.char_count);
//		add_details = (EditText) findViewById(R.id.additional_details);
//		add_details.addTextChangedListener(charCountWatcher);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.test_string_builder, menu);
		return true;
	}
	
	
	protected ListAdapter createAdapter(String disaster)
    {
    	//Test data for initial event list
    	//Read this in from somewhere else
    	String[] testData = new String[] {
    			"#shelter",
    			"#food",
    			"#water",
    			"#help",
    			"#medical",
    	};
 
    	// Create a String array adapter using the testData values
    	ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, testData);
 
    	return adapter;
    }
	
	
	public void nextViewDetails(View view){
//		if (category_tag_spinner.getSelectedItem()==null){
//		AlertDialog alertDialog = new AlertDialog.Builder(this).create();
//		alertDialog.setTitle("Error");
//		alertDialog.setMessage("Please select a category");
//		// -1 = BUTTON_POSITIVE = a positive button?
//		alertDialog.setButton(-1, "OK", new DialogInterface.OnClickListener() {
//			public void onClick(DialogInterface dialog, int which) {
//				// here you can add functions
//			}
//		});
//		alertDialog.show();
//	} else {
		// In case the user backed, we don't want to accidentally duplicate strings, so we pull from the bundle again
		Bundle bundle = getIntent().getExtras();
		tweet = bundle.getString("tweet");
		tweet += " " + category;
		//tweet += " " + add_details.getText().toString();
		Intent i = new Intent(this, TestStringBuilderConfirm.class);
		i.putExtra("tweet", tweet);
		startActivity(i);
	}
}
