package uw.changecapstone.tweakthetweet;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

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

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.SQLException;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class TestStringBuilder extends Activity{

	private EditText location_text_box;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_string_builder);

		location_text_box = (EditText) findViewById(R.id.location_text_box);
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
		Intent i = new Intent(this, TestStringBuilderDisasterList.class);
		i.putExtra("loc", location_text_box.getText().toString());
		startActivity(i);
	}
	
	public void nextViewCurrentGPS(View view){
		// TODO: Mussie needs to pull the GPS coordinates and pass it on
		// (check that it exists on google maps?)
		Intent i = new Intent(this, TestStringBuilderDisasterList.class);
		startActivity(i);
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

}
