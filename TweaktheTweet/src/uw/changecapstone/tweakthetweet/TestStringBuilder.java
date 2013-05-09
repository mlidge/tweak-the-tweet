package uw.changecapstone.tweakthetweet;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class TestStringBuilder extends Activity{

	private EditText city_name;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_string_builder);

		city_name = (EditText) findViewById(R.id.city_name);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.test_string_builder, menu);
		return true;
	}
	
	
	public void nextViewLocation(View view){
		Intent i = new Intent(this, TestStringBuilderDisasterList.class);
		i.putExtra("city", city_name.getText().toString());
		startActivity(i);
	}



}
