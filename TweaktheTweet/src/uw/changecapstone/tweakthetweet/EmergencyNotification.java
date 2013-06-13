package uw.changecapstone.tweakthetweet;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

/**
 * EmergencyNotification is the emergency notification page that notifies users that 
 * Tweak The Tweet is not a replacement for 911.
 * 
 * @author Christina Quan
 *
 */
public class EmergencyNotification extends Activity implements OnTouchListener{
	public static final String PREFS_NAME = "PrefsFile1";
	private CheckBox dontRemindAgain;
	private boolean boxIsChecked;

	final static String TWEET_STRING = "TWEET_STRING";

	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_emergency_notification);
		
		
		dontRemindAgain = (CheckBox) findViewById(R.id.emergency_checkbox);
		dontRemindAgain.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

		   @Override
		   public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
			  boxIsChecked = isChecked;
		   }
		});
		
	
		//the whole screen becomes sensitive to touch
        LinearLayout emergencyLayout = (LinearLayout) findViewById(R.id.emergency_layout);
        emergencyLayout.setOnTouchListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.emergency_notification, menu);
		return true;
	}

	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) {
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		Editor editor = settings.edit();
		editor.putBoolean("skip", boxIsChecked);
		editor.commit();
		Intent i = new Intent(this, StartActivity.class);
		startActivity(i);
		return true;
	}


}
