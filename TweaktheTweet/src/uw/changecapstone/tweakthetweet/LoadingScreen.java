package uw.changecapstone.tweakthetweet;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.Window;

/**
 * Loading Screen is the initial screen that displays the Tweak the Tweet logo while
 * the app loads. 
 * 
 * @author Christina Quan
 *
 */
public class LoadingScreen extends Activity {
	public static final String PREFS_NAME = "PrefsFile1";
	Intent i;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loading_screen);
		
		Thread splashThread = new Thread() {
	         @Override
	         public void run() {
	            try {
	               int waited = 0;
	               while (waited < 5000) {
	                  sleep(100);
	                  waited += 100;
	               }
	            } catch (InterruptedException e) {
	               // do nothing
	            } finally {
	                finish();
	                Intent i = new Intent();
	                SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		       		Boolean toSkip = settings.getBoolean("skip", false);
		       		
		       		//If toSkip is set to true, skip the emergency notification and go straight to the
		       		//start page where the user can select GPS or enter a location.
		       		if(toSkip){
		       			i.setClassName("uw.changecapstone.tweakthetweet",
	                              "uw.changecapstone.tweakthetweet.StartActivity");
		       		}else{
		       			//If toSkip is set to false, go to the emergency notification first.
		       			i.setClassName("uw.changecapstone.tweakthetweet",
	                              "uw.changecapstone.tweakthetweet.EmergencyNotification");
		       		}
		       		
	                startActivity(i);
	            }
	         }
	      };
	      splashThread.start();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.loading_screen, menu);
		return true;
	}

}
