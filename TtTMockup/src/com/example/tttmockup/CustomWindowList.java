package com.example.tttmockup;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;


public class CustomWindowList extends ListActivity{
	public TextView title;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
 
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
 
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_header);
 
        title = (TextView) findViewById(R.id.title);
    }
}
