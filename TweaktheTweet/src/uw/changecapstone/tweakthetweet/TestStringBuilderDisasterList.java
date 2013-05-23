package uw.changecapstone.tweakthetweet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		
		// TODO: We get coordinates of location from Google Maps
		// We compare it with all the parameter coordinates of events
		// We build a list of events that the coordinates fall within
		
//		Bundle bundle = getIntent().getExtras();
//		int coord_x = bundle.getString("coord_x");
//		int coord_y = bundle.getString("coord_y");
		int coord_x = 0;
		int coord_y = 0;
		
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
		disaster_list.setAdapter(createAdapter(coord_x, coord_y));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.test_string_builder_disaster_list,
				menu);
		return true;
	}
	
	
	protected ListAdapter createAdapter(int curr_x, int curr_y)
    {
		Map<String, Map<String, Integer>> testMap = new HashMap<String, Map<String, Integer>>();
		testMap.put("#TestEvent1", new HashMap<String, Integer>());
		testMap.get("#TestEvent1").put("max_x", 10);
		testMap.get("#TestEvent1").put("max_y", 10);
		testMap.get("#TestEvent1").put("min_x", 0);
		testMap.get("#TestEvent1").put("min_y", 0);
		testMap.put("#TestEvent2", new HashMap<String, Integer>());
		testMap.get("#TestEvent2").put("max_x", 20);
		testMap.get("#TestEvent2").put("max_y", 20);
		testMap.get("#TestEvent2").put("min_x", 10);
		testMap.get("#TestEvent2").put("min_y", 10);
		testMap.put("#TestEvent3", new HashMap<String, Integer>());
		testMap.get("#TestEvent3").put("max_x", 5);
		testMap.get("#TestEvent3").put("max_y", 5);
		testMap.get("#TestEvent3").put("min_x", 0);
		testMap.get("#TestEvent3").put("min_y", 0);

		
		List<String> testData = new ArrayList<String>();
		for(String event_tag : testMap.keySet()) {
			if (curr_x <= testMap.get(event_tag).get("max_x") && curr_x >= testMap.get(event_tag).get("min_x") &&
					curr_y <= testMap.get(event_tag).get("max_y") && curr_y >= testMap.get(event_tag).get("min_y")){
				testData.add(event_tag);
			}
		}

		return new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, testData);
    }
	
	
	public void nextViewLocation(View view){
		Intent i = new Intent(this, TestStringBuilderMap.class);
		i.putExtra("tweet", tweet);
		i.putExtra("disaster", tweet);
		startActivity(i);
	}

}
