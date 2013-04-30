package uw.changecapstone.tweakthetweet;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class TwitterWebView extends Activity {

	Uri uri;
	String url;
    WebView TwitterWebView;
	// Preference Constants
	static String PREFERENCE_NAME = "twitter_oauth";
	static final String PREF_KEY_OAUTH_TOKEN = "oauth_token";
	static final String PREF_KEY_OAUTH_SECRET = "oauth_token_secret";
	static final String PREF_KEY_TWITTER_LOGIN = "isTwitterLoggedIn";

	static final String TWITTER_CALLBACK_URL = "tttcallback://connect";

	// Twitter oauth urls
	static final String URL_TWITTER_AUTH = "auth_url";
	static final String URL_TWITTER_OAUTH_VERIFIER = "oauth_verifier";
	static final String URL_TWITTER_OAUTH_TOKEN = "oauth_token";
	
	
private Intent mIntent;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oauthtwitter);
       mIntent=getIntent();
        Bundle extras = mIntent.getExtras();
        url = extras.getString("url");
        uri = Uri.parse(url);
        url = uri.toString();

        Log.d("WEB", url);
        Log.d("WEB", "starting webview setup");
        
        try {
            TwitterWebView = (WebView) findViewById(R.id.webview);
            TwitterWebView.setVisibility(View.VISIBLE);

            setContentView(TwitterWebView);
            TwitterWebView.setWebViewClient(new TwitterWebViewClient());
            TwitterWebView.getSettings().setJavaScriptEnabled(true);
            TwitterWebView.getSettings().setDomStorageEnabled(true);
            TwitterWebView.getSettings().setSavePassword(false);
            TwitterWebView.getSettings().setSaveFormData(false);
            TwitterWebView.getSettings().setSupportZoom(false);
            
            Log.d("WEB", "about to load url");
            TwitterWebView.loadUrl(url);
            
			
        
			
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public class TwitterWebViewClient extends WebViewClient {
    	public boolean shouldOverrideUrlLoading(WebView view, String url) {
    		view.loadUrl(url);
    		 Uri uri = Uri.parse(url);
    		 Log.d("WEB", "in override");
    		if (uri.toString().contains(TWITTER_CALLBACK_URL)) {
    			Log.d("WEB", "in if");
    			String oauthVerifier = uri.getQueryParameter( "oauth_verifier" );
    		 mIntent.putExtra( "oauth_verifier", oauthVerifier );
             setResult( RESULT_OK, mIntent );
             finish();
    		}
            
            return true;
    	}	

    }
}
