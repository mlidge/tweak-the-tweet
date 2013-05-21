package com.example.tttmockup;


import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;

public class MainActivity extends CustomWindow {
	public static final String PREFS_NAME = "MyPrefsFile1";
	public CheckBox dontRemindAgain;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
        this.title.setText("Let's start tweaking");
        
		show911PopUp();
		
		
	}
	
    public void proceedForNow(View view){
    	Intent i = new Intent(getApplicationContext(), DisasterActivity.class);
        startActivity(i);
    }
    
    private void show911PopUp() {
    	 AlertDialog.Builder reminderBuilder = new AlertDialog.Builder(this);
    	 LayoutInflater inflater = getLayoutInflater();
   	     View checkboxLayout = inflater.inflate(R.layout.emergency_popup, null);
    	 dontRemindAgain = (CheckBox) checkboxLayout.findViewById(R.id.emergency_checkbox);
    	 
    	 reminderBuilder.setTitle("911 Notification");
    	 reminderBuilder.setMessage("Remember, if you need immediate emergency assistance, call 911!");
    	 
    	 reminderBuilder.setView(checkboxLayout);
    	 
    	 reminderBuilder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {

    	  @Override
    	  public void onClick(DialogInterface dialog, int which) {
    		  Boolean isChecked = false;
    		  if(dontRemindAgain.isChecked()){
    			  isChecked = true;
    			  SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
    			  Editor editor = settings.edit();
    			  editor.putBoolean("skip", isChecked);
    			  editor.commit();
    			  
    		  }
    		  //Do nothing for now
    	  }
    	 });
    	 SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
    	 Boolean toSkip = settings.getBoolean("skip", false);
    	 if(!toSkip){
    		 AlertDialog helpDialog = reminderBuilder.create();
        	 helpDialog.show(); 
    	 }
    }

}
