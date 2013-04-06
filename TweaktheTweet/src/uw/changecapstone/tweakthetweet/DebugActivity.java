package uw.changecapstone.tweakthetweet;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;

public class DebugActivity extends Activity {

	private final static String DATA_ON = "data";
	private final static String GPS_ON = "gps";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_debug);
		// Show the Up button in the action bar.
		
		
		
		// Create the text view
		TextView textView1 = new TextView(this);
		textView1.setTextSize(40);
		
		
		
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
		boolean data = pref.getBoolean(DATA_ON, true);
		boolean gps = pref.getBoolean(GPS_ON, false);
		
		textView1.setText("Data: " + data + "\nGPS: " + gps);
		
		setContentView(textView1);
		//setContentView(R.layout.activity_debug);
	}
	
	public void debugFinish(View view) {
		finish();
	}

}
