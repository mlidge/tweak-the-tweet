package uw.changecapstone.tweakthetweet;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class TwitterWebView extends Activity {

	Uri uri;
	String url;
    WebView webview;
	// Preference Constants
	static String PREFERENCE_NAME = "twitter_oauth";
	static final String PREF_KEY_OAUTH_TOKEN = "oauth_token";
	static final String PREF_KEY_OAUTH_SECRET = "oauth_token_secret";
	static final String PREF_KEY_TWITTER_LOGIN = "isTwitterLoggewedIn";

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
            webview = (WebView) findViewById(R.id.webview);
            webview.setVisibility(View.VISIBLE);

            setContentView(webview);
            
            webview.setWebViewClient(new WebViewClient(){
            	 ProgressDialog _dialog;
            	@Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                 // TODO Auto-generated method stub
                 _dialog = ProgressDialog.show(TwitterWebView.this, "", "Loading");
                 super.onPageStarted(view, url, favicon);
                }
            	
            	public boolean shouldOverrideUrlLoading(WebView view, String url) {
            		view.loadUrl(url);
            		 Uri uri = Uri.parse(url);
            		 Log.d("WEB", "in override");
            		if (uri.toString().contains(TWITTER_CALLBACK_URL)) {
            			Log.d("WEB", "in if");
            			_dialog.dismiss();
            			String oauthVerifier = uri.getQueryParameter( "oauth_verifier" );
            			mIntent.putExtra( "oauth_verifier", oauthVerifier );
            			setResult( RESULT_OK, mIntent );
            			finish();
            		}
                    
                    return true;
            	}	
            	
            	@Override
                public void onPageFinished(WebView view, String url) {
                 // TODO Auto-generated method stub
                 super.onPageFinished(view, url);
                 _dialog.dismiss();
                }

            }
            	
            );
            webview.getSettings().setJavaScriptEnabled(true);
            webview.getSettings().setDomStorageEnabled(true);
            webview.getSettings().setSavePassword(false);
            webview.getSettings().setSaveFormData(false);
            webview.getSettings().setSupportZoom(false);
            
            Log.d("WEB", "about to load url");
            webview.loadUrl(url);
            
			
        
			
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
