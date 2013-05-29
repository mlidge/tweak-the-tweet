/* This class provides functionality for when the user does not want to use their gps
 * It could be their current location or a previous location (TODO: to change the activity name)*/

package uw.changecapstone.tweakthetweet;

import java.io.IOException;
import java.util.List;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TextView.OnEditorActionListener;

public class PreviousLocationActivity extends Activity {
	private GoogleMap mMap;
	private LatLng geoLatLng;
	private LatLng tappedLatLng;
	public final static String GEOLAT = "uw.changecapstone.tweakthetweet.geolat";
	public final static String GEOLONG = "uw.changecapstone.tweakthetweet.geolong";
	public final static String TAPPEDLAT = "uw.changecapstone.tweakthetweet.tappedlat";
	public final static String TAPPEDLONG = "uw.changecapstone.tweakthetweet.tappedlong";
	private String tweet, disaster;
	private TextView char_count;
	private EditText location_text, test_tweet;
	public final static String LOCATION_TEXT = "uw.changecapstone.tweakthetweet.MESSAGE";
	double city_lat; 
	double city_long;
	private final TextWatcher charCountWatcher = new TextWatcher() {
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			//TODO: to put this back
			//char_count.setText(String.valueOf(140 - tweet.length() - " #loc ".length()) + " characters left in tweet");
		}

		public void onTextChanged(CharSequence s, int start, int before, int count) {
			//TODO: to put this back
			//char_count.setText(String.valueOf(140 - tweet.length() - " #loc ".length() - s.length()) + " characters left in tweet");
		}

		@Override
		public void afterTextChanged(Editable arg0) {
			// TODO Auto-generated method stub
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_previous_location);
		
		city_lat = TestStringBuilderDisasterList.city_lat;
		city_long = TestStringBuilderDisasterList.city_long;
		System.out.println("non-gps-city"+city_lat +" "+city_long);
		mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.loc_map)).getMap();
		LatLng cityGeoLatLng = new LatLng(city_lat, city_long);
		mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cityGeoLatLng, 14));
		mMap.addMarker(new MarkerOptions().position(cityGeoLatLng).title("your geo location."));
		mMap.setOnMapClickListener(getOnMapClickListener());
		
		Bundle bundle = getIntent().getExtras();
		tweet = bundle.getString("tweet");
		disaster = bundle.getString("disaster");
		
		char_count = (TextView) findViewById(R.id.char_count);
		//TODO: to put this back
		//char_count.setText(String.valueOf(140 - tweet.length() - " #loc ".length()) + " characters left in tweet");
		location_text = (EditText) findViewById(R.id.location_text_box);
		location_text.addTextChangedListener(charCountWatcher);
		location_text.setOnEditorActionListener(new OnEditorActionListener(){

			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				// TODO Auto-generated method stub
				readLocationMessage();
				return true;
			}		
		});
	}
	/*Gets the lat/long location that was touched.*/
	private OnMapClickListener getOnMapClickListener() {
		return new OnMapClickListener() {			
		public void onMapClick(LatLng point) {
			double lat = point.latitude;
			double lng = point.longitude;
			tappedLatLng = new LatLng(lat, lng);
			Toast.makeText(getBaseContext(), "Tapped Location: "+lat + "," + 
					lng, Toast.LENGTH_SHORT).show();
		}		
	};
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
				geoLatLng = new LatLng(lat, lng);
				mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(geoLatLng, 14));
				mMap.addMarker(new MarkerOptions().position(geoLatLng).title("geolocation of your address."));
				Toast.makeText(getBaseContext(), "Tweet Address: "+address.getAddressLine(0), Toast.LENGTH_SHORT).show();
			}
		}
	}
	public float getMidLat(double minLat, double maxLat){
		return (float) ((minLat+maxLat)/2);
	}
	public float getMidLng(double minLng, double maxLng){
		return (float) ((minLng+maxLng)/2);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.previous_location, menu);
		return true;
	}
	
	public void showMap(View view){
		Intent intent = new Intent(this, MapDisplayActivity.class);
		startActivity(intent);
	}
	/*when the user clicks the "Enter" button, 
	 * we are going to read the textfield content and 
	 * do some validity checks before we show/zoom map*/
	public void readLocationMessage(){
		String location = location_text.getText().toString();
		if (location !=null && !location.equals(""))
			new GeocoderTask().execute(location);
	}
	// this method is going to be used to implement "I don't know my location" button
	public void nextViewCategory(View view){
		//TODO: to factor out with private method later
		Bundle bundle = getIntent().getExtras();
		tweet = bundle.getString("tweet");
		
		EditText editText = (EditText) findViewById(R.id.location_text_box);
	    String message = editText.getText().toString();
	    if (!message.isEmpty()) {
	    	tweet += " #loc " + message;
	    }
	    
		Intent i = new Intent(this, TestStringBuilderCategory.class);
		i.putExtra("tweet", tweet);
		i.putExtra("disaster", disaster);
		startActivity(i);
	}
	public void useGeoLocation(View view){
		Bundle bundle = getIntent().getExtras();
		tweet = bundle.getString("tweet");
		
		EditText editText = (EditText) findViewById(R.id.location_text_box);
	    String message = editText.getText().toString();
	    if (!message.isEmpty()) {
	    	tweet += " #loc " + message;
	    }
	    
		Intent i = new Intent(this, TestStringBuilderCategory.class);
		i.putExtra("tweet", tweet);
		i.putExtra("disaster", disaster);
		//TODO: send position to TestStringBuilderCategory from which to send lat/long to tweetActivity
		if(geoLatLng == null)
			Toast.makeText(getBaseContext(), "Please enter address and press enter first: ", Toast.LENGTH_SHORT).show();
		else {
			i.putExtra(GEOLAT, geoLatLng.latitude);
			i.putExtra(GEOLONG, geoLatLng.longitude);
			startActivity(i);
		}
	}
	public void useTappedLocation(View view){
		
		Bundle bundle = getIntent().getExtras();
		tweet = bundle.getString("tweet");
		
		EditText editText = (EditText) findViewById(R.id.location_text_box);
	    String message = editText.getText().toString();
	    if (!message.isEmpty()) {
	    	tweet += " #loc " + message;
	    }
	    
		Intent i = new Intent(this, TestStringBuilderCategory.class);
		i.putExtra("tweet", tweet);
		i.putExtra("disaster", disaster);
		//TODO: send position to TestStringBuilderCategory from which to send lat/long to tweetActivity
		if(tappedLatLng==null)
			Toast.makeText(getBaseContext(), "Please Touch the map first: ", Toast.LENGTH_SHORT).show();
		else {
			i.putExtra(TAPPEDLAT, tappedLatLng.latitude);
			i.putExtra(TAPPEDLONG, tappedLatLng.longitude);
			startActivity(i);
		}
	}
}
