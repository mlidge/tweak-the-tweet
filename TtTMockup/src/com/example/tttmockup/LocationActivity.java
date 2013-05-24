package com.example.tttmockup;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.Window;

public class LocationActivity extends CustomWindow {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_location);
		this.title.setText("#location");
		
	}
	
	public void chooseCategory(View view){
		//For demonstration purposes, proceed to the next page no matter which button is pressed
		Intent i = new Intent(this, CategoryActivity.class);
		startActivity(i);
	}

}
