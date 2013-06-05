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
import android.widget.Toast;

public class SignUpTwitterActivity extends Activity {
	private static final String TWITTER_URL = "http://mobile.twitter.com/welcome/interests";
	private static final String TWITTER_URL_S = "https://mobile.twitter.com/welcome/interests";
	private static final String TWITTER_SIGNUP = "http://mobile.twitter.com/signup";
	WebView webview;
	ProgressDialog _dialog;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oauthtwitter);
      
        
        
        try {
            webview = (WebView) findViewById(R.id.webview);
            webview.getSettings().setJavaScriptEnabled(true);

            webview.getSettings().setDomStorageEnabled(true);
            webview.getSettings().setSavePassword(false);
            webview.getSettings().setSaveFormData(false);
            webview.getSettings().setSupportZoom(false);
            
            
            webview.setWebViewClient(new WebViewClient(){
            	
  
            	
            	public boolean shouldOverrideUrlLoading(WebView view, String url) {
            		view.loadUrl(url);
            		 Uri uri = Uri.parse(url);
            		 
            		 Log.d("WEB", url);
            		if (url.equals(TWITTER_URL) || url.equals(TWITTER_URL_S)) {
            			//_dialog.dismiss();
            			Intent i = new Intent(SignUpTwitterActivity.this, OAuthTwitterActivity.class);
            			startActivity(i);
            		}
                    
                    return true;
            	}	
            	
            	@Override
                public void onPageFinished(WebView view, String url) {
                 // TODO Auto-generated method stub
                 super.onPageFinished(view, url);
                 findViewById(R.id.webload).setVisibility(View.GONE);
                 findViewById(R.id.webview).setVisibility(View.VISIBLE);

                }

            }
            	
            );

            //_dialog = ProgressDialog.show(this, "", "Loading");
            Log.d("WEB", "about to load url");
            webview.loadUrl(TWITTER_SIGNUP);
            
			
        
			
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
