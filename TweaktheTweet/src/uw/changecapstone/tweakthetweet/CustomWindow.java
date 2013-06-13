package uw.changecapstone.tweakthetweet;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

/**
 * This file is used to handle the customized header on each page.
 * Each page that extends CustomWindow has a customized header.
 */

public class CustomWindow extends Activity{
	public TextView title;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
 
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
 
        setContentView(R.layout.activity_main);
        
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_header);
 
        title = (TextView) findViewById(R.id.title);
    }
}
