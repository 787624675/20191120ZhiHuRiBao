package com.zhihu.daily;

import android.app.Activity;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.FileUtils;
import android.os.Handler;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

public class WebActivity extends AppCompatActivity {

    protected void onCreat(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_view_one);
        WebView webView_one = findViewById(R.id.web_view_one_one);
        webView_one.getSettings().setJavaScriptEnabled(true);
        webView_one.setWebViewClient(new WebViewClient(){
           public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error){
               handler.proceed();

           }
        });

        webView_one.getSettings().setBlockNetworkImage(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView_one.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);

        }


        webView_one.setWebViewClient(new WebViewClient());
        webView_one.loadUrl("https://daily.zhihu.com/story/9717053");
    }

}
