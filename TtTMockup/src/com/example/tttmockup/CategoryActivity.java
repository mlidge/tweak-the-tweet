package com.example.tttmockup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class CategoryActivity extends CustomWindow {

	private ListView categoryListView;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_category);
		this.title.setText("#category");
		
		categoryListView = (ListView) findViewById(R.id.category_list);
		
		//Set a listener for whenever a button is clicked
		categoryListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				
				//Automatically switch to next screen no matter what button is clicked (for test mockup purposes)
				Intent i = new Intent(getApplicationContext(), OtherActivity.class);
		        startActivity(i);
				
			}
        });
		
		//Add footer for entering your own hashtag and displaying tweet
		View footerView = ((LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE)).inflate(R.layout.listview_footer, null, false);
		categoryListView.addFooterView(footerView);
		
		//Set the enter your own label to match the category page
		TextView footerLabel = (TextView) findViewById(R.id.enter_your_own_text);
		footerLabel.setText("or enter your own #category");
		
		//Create adapter
		ListAdapter adapter = createAdapter();
		categoryListView.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.category, menu);
		return true;
	}
	
	/**
     * Create and return a list adapter for the current list activity
     * @return a ListAdapter for the current list activity
     */
    protected ListAdapter createAdapter()
    {	
    	List<Map<String, String>> data = new ArrayList<Map<String, String>>();
    	
    	//Use a loop to generate a new map and add it to the data list when real data is parsed
    	for(int i=0; i<10; i++){
    		Map<String, String> datum = new HashMap<String, String>();
            datum.put("First Line", "#TestCategory"+i);
            datum.put("Second Line","Description"+i);
            data.add(Collections.unmodifiableMap(datum));
    	}
    	
        SimpleAdapter adapter = new SimpleAdapter(this, data,
                android.R.layout.simple_list_item_2, 
                new String[] {"First Line", "Second Line" }, 
                new int[] {android.R.id.text1, android.R.id.text2 });
    	return adapter;
    }
    

}
