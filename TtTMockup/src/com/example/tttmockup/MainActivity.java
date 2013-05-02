package com.example.tttmockup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class MainActivity extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		show911PopUp();
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
    	List<Map<String, String>> data = new ArrayList<Map<String, String>>();
    	
    	//Use a loop to generate a new map and add it to the data list when real data is parsed
    	for(int i=0; i<10; i++){
    		Map<String, String> datum = new HashMap<String, String>();
            datum.put("First Line", "#TestEvent"+i);
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
        Intent i = new Intent(getApplicationContext(), LocationActivity.class);
        startActivity(i);
    }
    
    private void show911PopUp() {

    	 AlertDialog.Builder helpBuilder = new AlertDialog.Builder(this);
    	 helpBuilder.setTitle("911 Notification");
    	 helpBuilder.setMessage("Insert Notification Text Here!");
    	 
    	 helpBuilder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {

    	  @Override
    	  public void onClick(DialogInterface dialog, int which) {
    		  showCitySelectionPopUp();
    	  }
    	 });

    	 AlertDialog helpDialog = helpBuilder.create();
    	 helpDialog.show();

    }
    
    private void showCitySelectionPopUp() {

	   	 AlertDialog.Builder cityBuilder = new AlertDialog.Builder(this);
	   	 cityBuilder.setTitle("City");
	   	 cityBuilder.setMessage("Enter your city:");
	   	 final EditText input = new EditText(this);

	   	 input.setSingleLine(true); //Restrict city input to a single line
	   	 input.setText("");
	   	 cityBuilder.setView(input);
	   	
	   	 cityBuilder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
	
	   	  @Override
	   	  public void onClick(DialogInterface dialog, int which) {
	   		  // Do nothing for now
	   	  }
	   	 });
	
	   	 AlertDialog helpDialog = cityBuilder.create();
	   	 helpDialog.show();

   	}

}
