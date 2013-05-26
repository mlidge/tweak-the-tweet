package com.example.tttmockup;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class OtherActivity extends CustomWindow {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_other);
		this.title.setText("#more info");
		
	}
	
	public void proceedToReview(View view){
		Intent i = new Intent(getApplicationContext(), ReviewTweetActivity.class);
        startActivity(i);
	}
	

}
