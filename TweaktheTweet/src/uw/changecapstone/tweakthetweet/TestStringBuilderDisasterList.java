package uw.changecapstone.tweakthetweet;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class TestStringBuilderDisasterList extends Activity {

	private ListView disaster_list;
	private String tweet;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_string_builder_disaster_list);
		
		Bundle bundle = getIntent().getExtras();
		String city_name = bundle.getString("city");
		
		disaster_list = (ListView) findViewById(R.id.list);
		disaster_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View v, int position,
					long id) {
				
				tweet = (String) adapter.getItemAtPosition(position);
				nextViewLocation(v);
			}
		
		});
		
		// Create adapter
		disaster_list.setAdapter(createAdapter(city_name));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.test_string_builder_disaster_list,
				menu);
		return true;
	}
	
	
	protected ListAdapter createAdapter(String city)
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
    	String[] testData2 = new String[] {};
 
    	// Create a String array adapter using the testData values
    	ListAdapter adapter;
    	if (city.equals("Seattle")){
    		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, testData);
    	} else {
    		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, testData2);
    	}
 
    	return adapter;
    }
	
	
	public void nextViewLocation(View view){
		Intent i = new Intent(this, TestStringBuilderLocation.class);
		i.putExtra("tweet", tweet);
		i.putExtra("disaster", tweet);
		startActivity(i);
	}

}
