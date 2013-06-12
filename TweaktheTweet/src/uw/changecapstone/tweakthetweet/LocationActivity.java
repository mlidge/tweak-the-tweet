package uw.changecapstone.tweakthetweet;

import java.io.IOException;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
/* LocationActivity file implements different functionalities on the 
 * location page of the application. It takes care of the mapping of both 
 * current location and other locations. It also uses either GPS or Google 
 * Maps API server to geo-code a given text address and allows users to 
 * use send text address or send none as part of the tweet. It also lets users
 * to send GPS latitude longitude, geo-latitude and geo-longitude, tapped 
 * latitude and longitude as part of tweet metadata or lets them say, continute
 * without location */
@SuppressLint("NewApi")

public class LocationActivity extends CustomWindow{
	private GoogleMap mMap;
	private LatLng geoLatLng;
	private LatLng tappedLatLng;
	public final static String LAT = "geolat";
	public final static String LONG = "geolong";
	public final static String GPS_LAT = "uw.changecapstone.tweakthetweet.latitude";
	public final static String GPS_LONG = "uw.changecapstone.tweakthetweet.longitude";
	public final static String CITY_LAT = "uw.changecapstone.tweakthetweet.latitude";
	public final static String CITY_LONG = "uw.changecapstone.tweakthetweet.longitude";
	private String tweet, disaster;
	private TextView char_count;
	private EditText location_text, test_tweet;
	public final static String LOCATION_TEXT = "uw.changecapstone.tweakthetweet.MESSAGE";
	double gps_lat; 
	double gps_long;
	double city_lat; 
	double city_long;
	String message;
	ImageButton doNotAddLoc;
	Marker tappedMarker;
	Button tappedLocBtn;
	TextView textPrompt1;
	TextView textPrompt2;
	Button firstLocBtn;
	private final String GPS_TEXT_1 = "Your GPS shows you are here";
	private final String GPS_TEXT_2 = "Locate and tap on the map to get an accurate location or use GPS";
	private final String ENTERED_TEXT_1 = "Locate and tap on the map to get an accurate location";
	private final String ENTERED_TEXT_2 = "";
	private final String GPS_BUTTON_TEXT = "Use my GPS location";
	private final String GPS_MARKER_TEXT = "Your GPS location";
	private final String CITY_BUTTON_TEXT = "Use my address";
	private final String CITY_MARKER_TEXT = "Your entered location";
	
	/* This counts the number of characters already used up and return 
	 * what is left to reach the 140 character limit in twitter.*/
	private final TextWatcher addLocationTag = new TextWatcher() {
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		}

		public void onTextChanged(CharSequence s, int start, int before, int count) {
			//Handle tag creation and display in footer box
			message = s.toString();
			
			test_tweet.setText(tweet + " " + "#loc " + message);
			
			//Handle character count display
			int crntLength = 140 - tweet.length() - message.length();
			
			if(crntLength < 0){
				char_count.setTextColor(Color.RED);
			}else{
				char_count.setTextColor(Color.BLACK);
			}
			
			if(crntLength != 1){
				char_count.setText(String.valueOf(crntLength) + " characters left");
			}else{
				char_count.setText(String.valueOf(crntLength) + " character left");
			}
		}
		
		@Override
		public void afterTextChanged(Editable arg0) {
			// TODO Auto-generated method stub
		}

	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_location);
		this.title.setText("#location");
		Bundle bundle = getIntent().getExtras();	
		tweet = bundle.getString("tweet");
		disaster = bundle.getString("disaster");
		textPrompt1 = (TextView) findViewById(R.id.text_prompt_1);
		textPrompt2 = (TextView) findViewById(R.id.text_prompt_2);
		firstLocBtn = (Button) findViewById(R.id.location_btn);
		if(StartActivity.isGpsUsed){
			gps_lat = bundle.getDouble(GPS_LAT, 0.0);
			gps_long = bundle.getDouble(GPS_LONG);
			textPrompt1.setText(GPS_TEXT_1);
			textPrompt2.setText(GPS_TEXT_2);
			firstLocBtn.setText(GPS_BUTTON_TEXT);
			setMap(GPS_MARKER_TEXT);
			
		}else{
			city_lat = bundle.getDouble(CITY_LAT);
			city_long = bundle.getDouble(CITY_LONG);
			textPrompt1.setText(ENTERED_TEXT_1);
			textPrompt2.setText(ENTERED_TEXT_2);
			firstLocBtn.setText(CITY_BUTTON_TEXT);
			setMap(CITY_MARKER_TEXT);
			
		}
		
		//Set char count
		char_count = (TextView) findViewById(R.id.character_count_location);
		char_count.setText(String.valueOf(140 - tweet.length()) + " characters left");
		
		//Set tweet box
		test_tweet = (EditText) findViewById(R.id.tweet_display);
		test_tweet.setText(tweet);
		
		//Set location button
		tappedLocBtn = (Button) findViewById(R.id.tapped_location_btn);
		location_text = (EditText) findViewById(R.id.location_text_box);
		location_text.addTextChangedListener(addLocationTag);
		location_text.setOnEditorActionListener(new OnEditorActionListener(){
			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_DONE) {
		            // hide virtual keyboard
		            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		            imm.hideSoftInputFromWindow(location_text.getWindowToken(), 0);
		            readLocationMessage();
		            return true;
		        }
		        return false;
			}		
		});
		
		//Get scrollview
		final ScrollView locationScroll = (ScrollView) findViewById(R.id.location_scrollview);

		/*
		mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener(){

			@Override
			public void onMapLongClick(LatLng arg0) {
				
				// Disable Scrolling by setting up an OnTouchListener to do nothing
				locationScroll.setOnTouchListener( new OnTouchListener(){ 
					@Override
					public boolean onTouch(View arg0, MotionEvent arg1) {
						Toast.makeText(getBaseContext(), "The map was dragged", Toast.LENGTH_SHORT).show();
						return true;
						
					}
				}); 
			}
			
		});
		*/
	}
	
	private void setMap(String markerTitle){
		mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.loc_map)).getMap();
		if(StartActivity.isGpsUsed){
			LatLng gpslatLng = new LatLng(gps_lat, gps_long);
			mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(gpslatLng, 14));
			mMap.addMarker(new MarkerOptions().position(gpslatLng).title("Your GPS location"));
			mMap.setMyLocationEnabled(true);		
		}else{
			LatLng cityGeoLatLng = new LatLng(city_lat, city_long);
			mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cityGeoLatLng, 14));
			mMap.addMarker(new MarkerOptions().position(cityGeoLatLng).title("Your address location"));
		}
		mMap.setOnMapClickListener(getOnMapClickListener());
	}

	/*Gets the lat/long location that was touched and adds a marker.*/
	private OnMapClickListener getOnMapClickListener() {
		return new OnMapClickListener() {			
		public void onMapClick(LatLng point) {
			if(tappedMarker != null){
				tappedMarker.remove();
			}
			double lat = point.latitude;
			double lng = point.longitude;
			tappedLatLng = new LatLng(lat, lng);
			tappedMarker = mMap.addMarker(new MarkerOptions().position(tappedLatLng).title("Your tapped location"));
			tappedLocBtn.setBackgroundColor(getResources().getColor(R.color.default_button_color));
			tappedLocBtn.setOnClickListener(new OnClickListener() {
	
			    @Override
			    public void onClick(View v) {
			        useTappedLocation(v);
			    }
			});
			
			Toast.makeText(getBaseContext(), "Tapped Location: "+lat + "," + 
					lng, Toast.LENGTH_SHORT).show();
		}
		
	};
	}
	
	/* This method accepts a text address from the user and
	 * parses (geo-codes) it, meaning, it returns the latitude and longitude
	 * of the address to be sent as part of the metadata of tweet. 
	 * Reference: http://wptrafficanalyzer.in/blog */
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
	
	/*when the user clicks the "Done" button, 
	 * we read in the textfield content and 
	 * do some validity checks before we show/zoom map*/
	public void readLocationMessage(){
		String location = location_text.getText().toString();
		if (location !=null && !location.equals(""))
			new GeocoderTask().execute(location);
	}
	
	/* This method handles the case that if the user does not 
	 * want to provide address or if he/she does not know the address
	 * he/she can just say 'continue without location' and skip to the 
	 * next tweet generation page*/
	public void nextViewCategory(View view){
		Bundle bundle = getIntent().getExtras();
		tweet = bundle.getString("tweet");
		
		EditText editText = (EditText) findViewById(R.id.location_text_box);
	    message = editText.getText().toString();
	    tweet += " #loc none";
	    
		Intent i = new Intent(this, CategoryActivity.class);
		i.putExtra("tweet", tweet);
		i.putExtra("disaster", disaster);
		startActivity(i);
	}
	
	/* This method handles the case of sending
	 *  GPS or geo-coded street address as part 
	 *  of the tweet metadata.
	 */
	public void useLocation(View view){
		Bundle bundle = getIntent().getExtras();
		tweet = bundle.getString("tweet");
		
		EditText editText = (EditText) findViewById(R.id.location_text_box);
	    String message = editText.getText().toString();
	    if (!message.isEmpty()) {
	    	tweet += " #loc " + message;
	    }else{
	    	tweet += " #loc none";
	    }
	    
	    Intent i = new Intent(this, CategoryActivity.class);
		i.putExtra("tweet", tweet);
		i.putExtra("disaster", disaster);
		
		if(StartActivity.isGpsUsed){
			i.putExtra(LAT, gps_lat);
			i.putExtra(LONG, gps_long);
			startActivity(i);
		}else{
			if(geoLatLng == null)
				Toast.makeText(getBaseContext(), "Please enter address and press enter first: ", Toast.LENGTH_SHORT).show();
			else {
				i.putExtra(LAT, geoLatLng.latitude);
				i.putExtra(LONG, geoLatLng.longitude);
				startActivity(i);
			}
		}
	}
	
	/* This method implements the case of sending
	 *  tapped latitude and longitude as part 
	 *  of the tweet metadata.
	 */
	public void useTappedLocation(View view){
		
		Bundle bundle = getIntent().getExtras();
		tweet = bundle.getString("tweet");
		
		EditText editText = (EditText) findViewById(R.id.location_text_box);
	    message = editText.getText().toString();
	    if (!message.isEmpty()) {
	    	tweet += " #loc " + message;
	    } else {
	    	tweet += " #loc none";
	    }
	    
		Intent i = new Intent(this, CategoryActivity.class);
		i.putExtra("tweet", tweet);
		i.putExtra("disaster", disaster);
		
		if(tappedLatLng==null)
			Toast.makeText(getBaseContext(), "Please touch the map first.", Toast.LENGTH_SHORT).show();
		else {
			i.putExtra(LAT, tappedLatLng.latitude);
			i.putExtra(LONG, tappedLatLng.longitude);
			startActivity(i);
		}
	}
}