package uw.changecapstone.tweakthetweet;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.content.Intent;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**
 * DisasterActivity handles picking a disaster tag from a generated list of tags or
 * entering a custom tag. 
 *
 */
public class DisasterActivity extends CustomWindow {

	private ListView disaster_list;
	private String tweet, custom_disaster_tag;
	public final static String GPS_LAT = "uw.changecapstone.tweakthetweet.latitude";
	public final static String GPS_LONG = "uw.changecapstone.tweakthetweet.longitude";
	public final static String CITY_LAT = "uw.changecapstone.tweakthetweet.latitude";
	public final static String CITY_LONG = "uw.changecapstone.tweakthetweet.longitude";
	static double gps_lat ;
	static double gps_long;
	static double city_lat ;
	static double city_long;
	private TextView char_count;
	private EditText crnt_tweet;
	private ImageButton proceed_custom_disaster_tag;
	private Map<String, HashTagData> eventMap;
	
	/* This counts the number of characters already used up and return 
	 * what is left to reach the 140 character limit in twitter.*/
	private final TextWatcher createNewDisasterTag = new TextWatcher() {
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		}
	
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			
			//Handle tag creation and display in footer box
			custom_disaster_tag = s.toString();
			
			//Add "#" character to hash tag if the user did not already enter it
			if(!custom_disaster_tag.contains("#")){
				custom_disaster_tag = "#" + custom_disaster_tag;
			}
			crnt_tweet.setText(custom_disaster_tag);
			
			//Handle character count display
			int crntLength = 140 - custom_disaster_tag.length();
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
		}
	
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent intent = getIntent();
		gps_lat = ((Double)intent.getDoubleExtra(GPS_LAT, 0.0));
		gps_long = ((Double)intent.getDoubleExtra(GPS_LONG, 0.0));
		
		city_lat = ((Double)intent.getDoubleExtra(CITY_LAT, 0.0));
		city_long = ((Double)intent.getDoubleExtra(CITY_LONG, 0.0));
		setContentView(R.layout.activity_test_string_builder_disaster_list);
		this.title.setText("#disaster");
		
		
		disaster_list = (ListView) findViewById(R.id.list);
		disaster_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View v, int position,
					long id) {
				
				tweet = (String) adapter.getItemAtPosition(position);
				nextViewLocation(v);
			} 
		
		});
		
		
		//Add footer for entering your own hashtag and displaying tweet
		View footerView = ((LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE)).inflate(R.layout.listview_footer, null, false);
		disaster_list.addFooterView(footerView);
				
		//Set the enter your own label to match the disaster page
		TextView footerLabel = (TextView) findViewById(R.id.enter_your_own_text);
		footerLabel.setText("or enter your own #disaster");
		
		//Set the hint in the text box to match the disaster page
		EditText stateTextBox = (EditText) findViewById(R.id.custom_text_box);
		stateTextBox.setHint("i.e. #northhurricane");
		
		//Set up disaster text box
		stateTextBox.addTextChangedListener(createNewDisasterTag);
				
		//Set up char count
		char_count = (TextView) findViewById(R.id.footer_character_count);
		char_count.setText("140 characters left");
		
		//Set up tweet text box
		crnt_tweet = (EditText) findViewById(R.id.tweet_display);
		
		//Set up "next" button for custom hash tag
		proceed_custom_disaster_tag = (ImageButton) findViewById(R.id.proceed_with_custom);
		proceed_custom_disaster_tag.setOnClickListener(new OnClickListener() {

		    @Override
		    public void onClick(View v) {
				if(custom_disaster_tag != null){
					tweet = custom_disaster_tag;
					nextViewLocation(v);
				}else{
					Toast.makeText(getApplicationContext(), "Please enter a custom disaster tag", Toast.LENGTH_SHORT).show();
				}
		    }
		});
		
		// Finally, update our local hash tags database
		updateHashTagData();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.test_string_builder_disaster_list, menu);
		return true;
	}
	
	/*
	 * Accesses and pulls the current list of event tag information from the database
	 * through a PHP frontend, sorting and storing it in a HashMap as a HashTagData object.
	 */
	private void updateHashTagData(){
		eventMap = new HashMap<String, HashTagData>();
		
		new AsyncTask<String, Void, String>() {
			@Override
			protected String doInBackground(String... params) {
				String result = "";
	    		InputStream is = null;
	    		
	    		// HTTP Get call
	    		try{
			        HttpClient httpclient = new DefaultHttpClient();
			        HttpGet httpget = new HttpGet("http://homes.cs.washington.edu/~yaluen/main.php");
			        HttpResponse response = (HttpResponse) httpclient.execute(httpget);
			        HttpEntity entity = ((org.apache.http.HttpResponse) response).getEntity();
			        is = entity.getContent();
	    		} catch(Exception e) {
			        Log.e("log_tag", "Error in http connection "+e.toString());
	    		}
	    		
	    		// Convert the response into a string
	    		try {
			        BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
			        StringBuilder sb = new StringBuilder();
			        String line = null;
			        while ((line = reader.readLine()) != null) {
		                sb.append(line + "\n");
			        }
			        is.close();
			        result=sb.toString();
			        
	    		} catch (Exception e) {
			        Log.e("log_tag", "Error converting result "+e.toString());
	    		}
	    		 
	    		String returnResult = "";
	    		// Parse the data through a JSON object
	    		try {
			        JSONArray jArray = new JSONArray(result);
			        for(int i=0;i<jArray.length();i++){
		                JSONObject json_data = jArray.getJSONObject(i);
		                HashTagData currentEvent = new HashTagData(json_data.getString("event_id"), json_data.getString("description"),
		                		json_data.getString("category"), json_data.getString("latitude_top_right"), json_data.getString("longitude_top_right"),
		                		json_data.getString("latitude_bottom_left"), json_data.getString("longitude_bottom_left"));
		                eventMap.put(json_data.getString("event_id"), currentEvent);
			        }
			        
	    		} catch(JSONException e) {
			        Log.e("log_tag", "Error parsing data "+e.toString());
	    		}
	    		
	    		System.out.println(returnResult);
	    		return returnResult;
	    	}

	    	@Override
	        protected void onPostExecute(String result) {
	    		ListAdapter adapter = createAdapter();
	    		disaster_list.setAdapter(adapter);
	        }
			
		}.execute("");
	}
	
	/*
	 * Populates the displayed list of event tags by seeing if the user's inputed general
	 * location coordinates falls within any of the event's coordinate area.
	 */
	protected ListAdapter createAdapter()
    {		
		List<String> testData = new ArrayList<String>();
		for(String event_tag : eventMap.keySet()) {
			if (city_lat <= Double.parseDouble(eventMap.get(event_tag).getLatTopRight()) && 
					city_lat >= Double.parseDouble(eventMap.get(event_tag).getLatBotLeft()) &&
					city_long <= Double.parseDouble(eventMap.get(event_tag).getLongTopRight()) &&
					city_long >= Double.parseDouble(eventMap.get(event_tag).getLongBotLeft())) {
				testData.add(event_tag);
			}
		}

		return new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, testData);
    }	
	
	/* This method will pass the gps or city coordinates to location
	 * activity page. */
	public void nextViewLocation(View view){
		Intent i = new Intent(this, LocationActivity.class);
		i.putExtra("tweet", tweet);
		i.putExtra("disaster", tweet);
		if(StartActivity.isGpsUsed){
			i.putExtra(GPS_LAT, gps_lat);
			i.putExtra(GPS_LONG, gps_long);
		}else{
			i.putExtra(CITY_LAT, city_lat);
			i.putExtra(CITY_LONG, city_long);
		}
		i.putExtra("event_data", eventMap.get(tweet));
		startActivity(i);
	}

}
