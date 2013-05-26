package uw.changecapstone.tweakthetweet;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class TestStringBuilderAddDetails extends CustomWindow {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_string_builder_add_details);
		this.title.setText("#more info");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.test_string_builder_add_details, menu);
		return true;
	}

}
