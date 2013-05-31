package uw.changecapstone.tweakthetweet;


import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

public class EmergencyNotification extends Activity implements OnTouchListener, DialogListener{
	public static final String PREFS_NAME = "PrefsFile1";
	private CheckBox dontRemindAgain;
	private boolean boxIsChecked;

	final static String TWEET_STRING = "TWEET_STRING";

	private static final String PREF_KEY_TWITTER_LOGIN = "isTwitterLoggedIn";
	private static final String LOGIN_DIALOG_TAG = "logindialog";
	private static final String NETWORK_DIALOG_TAG = "networkdialog";
	private final static String DATA_ON = "data";
	
	private SharedPreferences pref; 
	@Override
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
		
		pref = PreferenceManager.getDefaultSharedPreferences(this);
		checkNetworkStatus();
		checkLogInStatus();
		
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
		Intent i = new Intent(this, TestStringBuilder.class);
		startActivity(i);
		return true;
	}
	
	private void checkLogInStatus() {
		  //if (!pref.getBoolean(PREF_KEY_TWITTER_LOGIN, false)) {
			  DialogFragment logIn = new LoginDialogFragment();
			  logIn.show(getFragmentManager(), LOGIN_DIALOG_TAG);
		  //}
		}
		
	private void checkNetworkStatus() {
		ConnectivityManager cm =
		        (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
		//boolean noConnection = cm==null;
		
		
				NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		
				boolean isConnected = (activeNetwork != null) && (activeNetwork.isConnectedOrConnecting());
		
	
		if (!isConnected) {
			DialogFragment network = new NetworkDialogFragment();
			network.show(getFragmentManager(), NETWORK_DIALOG_TAG);
		} else {
			Editor e = pref.edit();
			e.putBoolean(DATA_ON, true);
			e.commit();
		}
		
	}

		@Override
		public void onDialogPositiveClick(DialogFragment dialog) {
			String tag = dialog.getTag();
			if (tag.equals(LOGIN_DIALOG_TAG)) {
				Intent i = new Intent(this, OAuthTwitterActivity.class);
				startActivity(i);
			} else if (tag.equals(NETWORK_DIALOG_TAG)) {
				setUseSMS();
			}
			
		}

		@Override
		public void onDialogNegativeClick(DialogFragment dialog) {
			String tag = dialog.getTag();
			if (tag.equals(LOGIN_DIALOG_TAG)) {
				/*
				Intent i = new Intent(this, SignUpTwitterActivity.class);
				startActivity(i);
				*/
			}
			
		}
		
		public void setUseSMS() {
			Editor e = pref.edit();
			e.putBoolean(DATA_ON, false);
			e.commit();
		}

}
