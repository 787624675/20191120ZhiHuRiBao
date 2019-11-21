package com.zhihu.daily;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.zhihu.daily.adapter.WebPageAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.net.URL;
import java.util.List;

import static com.zhihu.daily.MainActivity.url1;

public class NewsDetail extends AppCompatActivity {


    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);


        WebView webView_two = findViewById(R.id.web_view_two_two);
        ViewPager webScroll = findViewById(R.id.web_scroll);


        webView_two.getSettings().setJavaScriptEnabled(true);
        webView_two.getSettings().setBlockNetworkImage(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView_two.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);

        }
        webView_two.setWebViewClient(new WebViewClient());
        webView_two.loadUrl(url1);

    }
    @SuppressLint("SetJavaScriptEnabled")
    private void addView(List<WebView> webViewList, String url){
        WebView webView = new WebView(this);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBlockNetworkImage(false);

    }

}
