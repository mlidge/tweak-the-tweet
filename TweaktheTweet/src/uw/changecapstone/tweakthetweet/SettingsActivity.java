package uw.changecapstone.tweakthetweet;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;

public class SettingsActivity extends Activity {
	private final static String DATA_ON = "data";
	private final static String GPS_ON = "gps";
	private final static String TAG = "Settings";
	private SharedPreferences.Editor editor;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		editor = PreferenceManager.getDefaultSharedPreferences(this).edit();

		
		final RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radiogroup);
		
		/** Define and implement a listener to monitor radio group and update
		 * preferences accordingly
		 */
		radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.radiobuttondata:
					Log.d(TAG, "data checked");
					editor.putBoolean(DATA_ON, true);
					break;
				case R.id.radiobuttonsms:
					Log.d(TAG, "sms checked");
					editor.putBoolean(DATA_ON, false);
					break;
				}
				
				Log.d(TAG, "editor commit");
				editor.commit();
			}
		});
	}
	
	public void commitSettings(View view) {
		finish();
	}
}
