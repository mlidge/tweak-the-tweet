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
import android.view.Menu;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class CategoryActivity extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//Create adapter
		ListAdapter adapter = createAdapter();
		setListAdapter(adapter);
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
    
    //Automatically switch to next screen no matter what button is clicked (for test mockup purposes)
    @Override 
    public void onListItemClick(ListView l, View v, int position, long id) {
    	Intent i = new Intent(this, OtherActivity.class);
		startActivity(i);
    }

}
