package uw.changecapstone.tweakthetweet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class SignUpTwitterActivity extends Activity {
	// URLs for sign up and to redirect from
	private static final String TWITTER_URL = "http://mobile.twitter.com/welcome/interests";
	private static final String TWITTER_URL_S = "https://mobile.twitter.com/welcome/interests";
	private static final String TWITTER_SIGNUP = "http://mobile.twitter.com/signup";
	private WebView webview;
	
    protected void onCreate(Bundle savedInstanceState) {
    	this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authtwitter);
        try {
            webview = (WebView) findViewById(R.id.webview);
            webview.getSettings().setJavaScriptEnabled(true);
            webview.getSettings().setDomStorageEnabled(true);
            webview.getSettings().setSavePassword(false);
            webview.getSettings().setSaveFormData(false);
            webview.getSettings().setSupportZoom(false);
            
            // The custom webview client provides the ability to capture the
            // redirect url and return to the application
            webview.setWebViewClient(new WebViewClient(){
            	
  
            	
            	public boolean shouldOverrideUrlLoading(WebView view, String url) {
            		view.loadUrl(url);
            		// After a successful signup, send the user to be authenticated through
            		// the application
            		if (url.equals(TWITTER_URL) || url.equals(TWITTER_URL_S)) {
            			Intent i = new Intent(SignUpTwitterActivity.this, AuthenticateTwitterActivity.class);
            			startActivity(i);
            			finish();
            		}
                    
                    return true;
            	}	
            	
            	@Override
                public void onPageFinished(WebView view, String url) {
                 super.onPageFinished(view, url);
                 // Hide the loading page and allow the webpage to be displayed.
                 findViewById(R.id.webload).setVisibility(View.GONE);
                 findViewById(R.id.webview).setVisibility(View.VISIBLE);

                }

            }
            	
            );
            // Load the signup page in the webview
            webview.loadUrl(TWITTER_SIGNUP);
	
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
