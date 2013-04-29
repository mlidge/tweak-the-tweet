package com.example.tttmockup;

import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class MainActivity extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//Create adapter
		ListAdapter adapter = createAdapter();
		setListAdapter(adapter);
	}
	
	/**
     * Create and return a list adapter for the current list activity
     * @return a ListAdapter for the current list activity
     */
    protected ListAdapter createAdapter()
    {
    	//Test data for initial event list
    	//Read this in from somewhere else
    	String[] testData = new String[] {
    			"#TestEvent1\n\tDescription1",
    			"#TestEvent2\n\tDescription2",
    			"#TestEvent3\n\tDescription3",
    			"#TestEvent4\n\tDescription4",
    			"#TestEvent5\n\tDescription5",
    			"#TestEvent6\n\tDescription6",
    			"#TestEvent7\n\tDescription7",
    			"#TestEvent8\n\tDescription8",
    			"#TestEvent9\n\tDescription9",
    			"#TestEvent10\n\tDescription10",
    			"#TestEvent11\n\tDescription11",
    			"#TestEvent12\n\tDescription12",
    	};
 
    	// Create a String array adapter using the testData values
    	ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, testData);
 
    	return adapter;
    }
    
    //Automatically switch to next screen no matter what button is clicked (for test mockup purposes)
    @Override 
    public void onListItemClick(ListView l, View v, int position, long id) {
        Intent i = new Intent(getApplicationContext(), LocationActivity.class);
        startActivity(i);
    }

}
