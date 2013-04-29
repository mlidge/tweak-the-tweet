package com.example.tttmockup;

import android.os.Bundle;
import android.app.Activity;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.Menu;
import android.widget.EditText;

public class OtherActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_other);
		
		EditText editText = (EditText) findViewById(R.id.edit_message);
		editText.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.other, menu);
		return true;
	}
	

}
