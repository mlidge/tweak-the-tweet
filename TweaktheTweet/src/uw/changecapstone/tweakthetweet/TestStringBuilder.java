package uw.changecapstone.tweakthetweet;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class TestStringBuilder extends Activity {

	private Spinner disaster_tag_spinner;
	private String disaster_tag, disaster_description_string;
	private EditText test_string, disaster_description;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_string_builder);
		
		disaster_tag_spinner = (Spinner) findViewById(R.id.disaster_tag_spinner);
		disaster_description = (EditText) findViewById(R.id.disaster_description);
		test_string = (EditText) findViewById(R.id.test_text);
		
		addTagsOnSpinner();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.test_string_builder, menu);
		return true;
	}
	
	public void addTagsOnSpinner() {
		List<String> list = new ArrayList<String>();
		list.add("Earthquake");
		list.add("Tornado");
		list.add("Volcano");
		list.add("Tsunami");
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		disaster_tag_spinner.setAdapter(dataAdapter);
		
	}
	
	public void buildString(View view) {
		disaster_tag = disaster_tag_spinner.getSelectedItem().toString();
		disaster_description_string = disaster_description.getText().toString();
		test_string.setText("#"+disaster_tag + " " + disaster_description_string, TextView.BufferType.NORMAL);

	}

}
