package com.example.svinc.educationalgame;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebViewClient;

public class TweetCustomWebView extends AppCompatActivity {

    android.webkit.WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet_custom_web_view);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            final String stringToShow = extras.getString("tweettext");
            webView = findViewById(R.id.wv);

            webView.setWebViewClient(new WebViewClient() {
                public boolean shouldOverrideUrlLoading(android.webkit.WebView view, String url) {
                    if (url.contains("latest_status_id=")) {
                        // Twitted
                        setResult(Activity.RESULT_OK, new Intent());
                        TweetCustomWebView.this.finish();
                    }
                    view.loadUrl(url);
                    return true;
                }

                public void onPageFinished(android.webkit.WebView view, String url) {
                    // Finished loading url
                }

                public void onReceivedError(android.webkit.WebView view, int errorCode, String description, String failingUrl) {
                    Log.e("", "Error: " + description);
                    setResult(Activity.RESULT_CANCELED, new Intent());

                }
            });
            webView.loadUrl("https://twitter.com/intent/tweet?text=" + stringToShow);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(Activity.RESULT_CANCELED, new Intent());
    }

}
