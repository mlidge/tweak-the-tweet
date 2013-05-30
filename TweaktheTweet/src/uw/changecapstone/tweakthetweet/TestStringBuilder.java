package uw.changecapstone.tweakthetweet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import twitter4j.internal.http.HttpResponse;
//import uw.changecapstone.tweakthetweet.LocationAndMapActivity.GeocoderTask;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.SQLException;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class TestStringBuilder extends CustomWindow implements DialogListener{

	private EditText location_text_box;
	Context context = this;
	public final static String LAT = "uw.changecapstone.tweakthetweet.latitude";
	public final static String LONG = "uw.changecapstone.tweakthetweet.longitude";
	public final static String CITY_LAT = "uw.changecapstone.tweakthetweet.latitude";
	public final static String CITY_LONG = "uw.changecapstone.tweakthetweet.longitude";
	static boolean isGpsUsed = false;
	
	final static String TWEET_STRING = "TWEET_STRING";

	private static final String PREF_KEY_TWITTER_LOGIN = "isTwitterLoggedIn";
	private static final String LOGIN_DIALOG_TAG = "logindialog";
	private static final String NETWORK_DIALOG_TAG = "networkdialog";
	private final static String DATA_ON = "data";
	
	private SharedPreferences pref; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_string_builder);
		this.title.setText("Let's start tweaking");
		
		location_text_box = (EditText) findViewById(R.id.location_text_box);
		
		pref = PreferenceManager.getDefaultSharedPreferences(this);
		checkNetworkStatus();
		checkLogInStatus();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.test_string_builder, menu);
		return true;
	}
	
	
	public void nextViewLocText(View view){
		// TODO: Mussie needs to pull the text location, find it on google maps,
		// and pass the coordinates on
		String location = location_text_box.getText().toString();
		if (location == null || location.equals("")){
			Toast.makeText(getBaseContext(), "Please enter city or region", Toast.LENGTH_SHORT).show();
		} else	if (location !=null && !location.equals("")){
			new GeocoderTask().execute(location);
		} 
	}
	
	public void nextViewCurrentGPS(View view){
		// TODO: Mussie needs to pull the GPS coordinates and pass it on
		// (check that it exists on google maps?)
		isGpsUsed = true;
		LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE); 	
		LocationListener locationListener = new GPSListener();
		if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER)){
			Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			if (location != null){
				//Get lat/long, show using toast, and animate to the location
				double lat = location.getLatitude();
				double lng = location.getLongitude();
				Intent i = new Intent(this, TestStringBuilderDisasterList.class);
				i.putExtra(LAT, lat);
			    i.putExtra(LONG, lng);
				startActivity(i);
				//Toast.makeText(getBaseContext(), "Current Location: "+lat + "," + lng, Toast.LENGTH_SHORT).show();
			}
			lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, locationListener);
		}
		else {
			showSettingsAlert();
		}
	}
	
	public void accessServer(View view) throws SQLException {
        
    	new NetworkAccessTask().execute("");
    }
	
	
	class NetworkAccessTask extends AsyncTask<String, Void, String> {
    	
    	@Override
    	protected String doInBackground(String... params) {
    		String result = "";
        	ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        	nameValuePairs.add(new BasicNameValuePair("id","message"));
    		InputStream is = null;
    		//http get
    		try{
		        HttpClient httpclient = new DefaultHttpClient();
		        HttpGet httpget = new HttpGet("http://uw-cse403-nonogram.co.nf/test.php?id=*");
		        HttpResponse response = (HttpResponse) httpclient.execute(httpget);
		        HttpEntity entity = ((org.apache.http.HttpResponse) response).getEntity();
		        is = entity.getContent();
    		} catch(Exception e) {
		        Log.e("log_tag", "Error in http connection "+e.toString());
    		}
    		//convert response to string
    		try {
		        BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
		        StringBuilder sb = new StringBuilder();
		        String line = null;
		        while ((line = reader.readLine()) != null) {
	                sb.append(line + "\n");
		        }
		        is.close();
		 
		        result=sb.toString();
		        System.out.println(result);
    		} catch (Exception e) {
		        Log.e("log_tag", "Error converting result "+e.toString());
    		}
    		 
    		String returnResult = "";
    		
    		//parse json data
    		try {
		        JSONArray jArray = new JSONArray(result);
		        for(int i=0;i<jArray.length();i++){
	                JSONObject json_data = jArray.getJSONObject(i);
	                returnResult += json_data.getString("message");
	                returnResult += " ";
		        }
		        
    		} catch(JSONException e) {
		        Log.e("log_tag", "Error parsing data "+e.toString());
    		}
    		
    		return returnResult;
    	}

    	@Override
        protected void onPostExecute(String result) {
//              EditText txt = (EditText) findViewById(R.id.editText1);
//              txt.setText(result); // txt.setText(result);
        }
    	
    	
    }
	private class GPSListener implements LocationListener {
		@Override
		public void onLocationChanged(Location location) {
			if (location != null){
				//Get Lat/long, show using toast, and animate to the location
				double lat = location.getLatitude();
				double lng = location.getLongitude();
				//TODO: "this" was not being accepted. investigate later. 
				Intent i = new Intent(context, TestStringBuilderDisasterList.class);
				i.putExtra(LAT, lat);
			    i.putExtra(LONG, lng);
				startActivity(i);
				//Toast.makeText(getBaseContext(), "Current Location: "+lat + "," + lng, Toast.LENGTH_SHORT).show();
			}			
		}
		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub	
		}
		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub			
		}
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub			
		}
	}
	public void showSettingsAlert(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
      
        // Set Title
        alertDialogBuilder.setTitle("GPS Settings");
  
        // Set Dialog Message
        alertDialogBuilder.setMessage("GPS is not enabled. Do you want to go to settings menu?")
        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int id) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        })
        .setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            dialog.cancel();
            }
        });
  
        // Show it
        alertDialogBuilder.show();
    }
	/* reference: http://wptrafficanalyzer.in/blog */
	private class GeocoderTask extends AsyncTask<String, Void, List <Address> >{

		@Override
		protected List<Address> doInBackground(String... arg0) {
			Geocoder geocoder = new Geocoder(getBaseContext());
			List <Address> addresses = null;
			
			try {
				//Get maximum of 1 Address that matches the input text
				addresses = geocoder.getFromLocationName(arg0[0],1);
			} catch (IOException e){
				e.printStackTrace();
			}
			return addresses;
		}
		@Override
		protected void onPostExecute(List <Address> addresses){
			if(addresses==null || addresses.size()==0){
				Toast.makeText(getBaseContext(), "No Location Found", Toast.LENGTH_SHORT).show();
			}
			else /*if (addresses != null && addresses.size() > 0)*/{
				Address address = addresses.get(0);
				double lat = address.getLatitude();
				double lng = address.getLongitude();
				//TODO: context -- this
				Intent i = new Intent(context, TestStringBuilderDisasterList.class);
				// TODO: we don't need to save the city location but just passing it
				i.putExtra("loc", location_text_box.getText().toString());
				i.putExtra(CITY_LAT, lat);
				i.putExtra(CITY_LONG, lng);
				startActivity(i);
				Toast.makeText(getBaseContext(), "Tweet City: "+address.getAddressLine(0), Toast.LENGTH_SHORT).show();
			}
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
	
	private void checkLogInStatus() {
		  if (!pref.getBoolean(PREF_KEY_TWITTER_LOGIN, false)) {
			  DialogFragment logIn = new LoginDialogFragment();
			  logIn.show(getFragmentManager(), LOGIN_DIALOG_TAG);
		  }
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
}
