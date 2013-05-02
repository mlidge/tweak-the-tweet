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

public class OtherActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_other);
		
		EditText editText = (EditText) findViewById(R.id.edit_message);
		editText.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.other, menu);
		return true;
	}
	
	public void reviewTweet(View view){
		showReviewTweetPopUp();
	}
	
	private void showReviewTweetPopUp() {

   	 AlertDialog.Builder reviewBuilder = new AlertDialog.Builder(this);
   	 reviewBuilder.setTitle("Review");
   	 reviewBuilder.setMessage("Review your tweet:");
   	 final EditText input = new EditText(this);

  	 input.setKeyListener(null); //Make the tweet not editable in the review box?
  	 input.setText("Your tweet should show up here");
  	 reviewBuilder.setView(input);
  	 
   	 reviewBuilder.setPositiveButton("Send", new DialogInterface.OnClickListener() {

   	  @Override
   	  public void onClick(DialogInterface dialog, int which) {
   		 //Do nothing for now
   	  }
   	 });

   	 AlertDialog helpDialog = reviewBuilder.create();
   	 helpDialog.show();

   }
	

}
