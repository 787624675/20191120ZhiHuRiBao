package com.zhihu.daily;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.net.URL;

import static com.zhihu.daily.MainActivity.url1;

public class NewsDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        WebView webView_two = findViewById(R.id.web_view_two_two);




        webView_two.getSettings().setJavaScriptEnabled(true);
        webView_two.setWebViewClient(new WebViewClient());
        webView_two.loadUrl(url1);

    }

}
