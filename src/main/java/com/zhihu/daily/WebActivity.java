package com.zhihu.daily;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

public class WebActivity extends AppCompatActivity {
    protected void onCreat(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_view_one);
        WebView webView_one = findViewById(R.id.web_view_one_one);
        webView_one.getSettings().setJavaScriptEnabled(true);
        webView_one.setWebViewClient(new WebViewClient());
        webView_one.loadUrl("https://daily.zhihu.com/story/9717053");
    }
}
