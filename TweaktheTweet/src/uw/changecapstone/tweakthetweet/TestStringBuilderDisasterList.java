package uw.changecapstone.tweakthetweet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class TestStringBuilderDisasterList extends CustomWindow {

	private ListView disaster_list;
	private String tweet;
	private String custom_disaster_tag;
	private TextView char_count;
	private EditText crnt_tweet, disaster_tag;
	private ImageButton proceed_custom_disaster_tag;
	
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
		setContentView(R.layout.activity_test_string_builder_disaster_list);
		this.title.setText("#disaster");
		
		// TODO: We get coordinates of location from Google Maps
		// We compare it with all the parameter coordinates of events
		// We build a list of events that the coordinates fall within
		
//		Bundle bundle = getIntent().getExtras();
//		int coord_x = bundle.getString("coord_x");
//		int coord_y = bundle.getString("coord_y");
		int coord_x = 0;
		int coord_y = 0;
		
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
		EditText stateTextBox = (EditText) findViewById(R.id.disaster_text_box);
		stateTextBox.setHint("i.e. #northhurricane");
		
		//Set up disaster text box
		disaster_tag = (EditText) findViewById(R.id.disaster_text_box);
		disaster_tag.addTextChangedListener(createNewDisasterTag);
				
		//Set up char count
		char_count = (TextView) findViewById(R.id.footer_character_count);
		char_count.setText("140 characters left");
		
		//Set up tweet text box
		crnt_tweet = (EditText) findViewById(R.id.tweet_display);
		
		//Set up "next" button for custom hash tag
		proceed_custom_disaster_tag = (ImageButton) findViewById(R.id.proceed_disaster);
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
		
		//Create adapter
		ListAdapter adapter = createAdapter(coord_x, coord_y);
		disaster_list.setAdapter(adapter);
		
		
		// Create adapter
		//disaster_list.setAdapter(createAdapter(coord_x, coord_y));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.test_string_builder_disaster_list,
				menu);
		return true;
	}
	
	
	protected ListAdapter createAdapter(int curr_x, int curr_y)
    {
		Map<String, Map<String, Integer>> testMap = new HashMap<String, Map<String, Integer>>();
		testMap.put("#TestEvent1", new HashMap<String, Integer>());
		testMap.get("#TestEvent1").put("max_x", 10);
		testMap.get("#TestEvent1").put("max_y", 10);
		testMap.get("#TestEvent1").put("min_x", 0);
		testMap.get("#TestEvent1").put("min_y", 0);
		testMap.put("#TestEvent2", new HashMap<String, Integer>());
		testMap.get("#TestEvent2").put("max_x", 20);
		testMap.get("#TestEvent2").put("max_y", 20);
		testMap.get("#TestEvent2").put("min_x", 10);
		testMap.get("#TestEvent2").put("min_y", 10);
		testMap.put("#TestEvent3", new HashMap<String, Integer>());
		testMap.get("#TestEvent3").put("max_x", 5);
		testMap.get("#TestEvent3").put("max_y", 5);
		testMap.get("#TestEvent3").put("min_x", 0);
		testMap.get("#TestEvent3").put("min_y", 0);

		
		List<String> testData = new ArrayList<String>();
		for(String event_tag : testMap.keySet()) {
			if (curr_x <= testMap.get(event_tag).get("max_x") && curr_x >= testMap.get(event_tag).get("min_x") &&
					curr_y <= testMap.get(event_tag).get("max_y") && curr_y >= testMap.get(event_tag).get("min_y")){
				testData.add(event_tag);
			}
		}

		return new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, testData);
    }
	
	private final TextWatcher charCountWatcher = new TextWatcher() {
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			int crntLength = 140 - tweet.length();
			if(crntLength != 1){
				char_count.setText(String.valueOf(140 - tweet.length()) + " characters left in tweet");
			}else{
				char_count.setText(String.valueOf(140 - tweet.length()) + " character left in tweet");
			}
		}
	
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			tweet = tweet + " " + s;
			int crntLength = 140 - tweet.length();
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
	
	public void nextViewLocation(View view){
		Intent i = new Intent(this, TestStringBuilderMap.class);
		i.putExtra("tweet", tweet);
		i.putExtra("disaster", tweet);
		startActivity(i);
	}

}
