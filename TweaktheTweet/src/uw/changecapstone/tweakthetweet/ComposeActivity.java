package uw.changecapstone.tweakthetweet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class ComposeActivity extends Activity {

	public EditText message;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compose);
		message = (EditText) findViewById(R.id.tweet_compose);
	}

	public void confirmTweet(View view){
		String tweet = message.getText().toString();
		Intent intent = new Intent();
		intent.putExtra(MainActivity.TWEET_STRING, tweet);
		setResult(5, intent);
		finish();
	}
}
