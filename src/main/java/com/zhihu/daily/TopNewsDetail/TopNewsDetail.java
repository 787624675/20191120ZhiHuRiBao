package com.zhihu.daily.TopNewsDetail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.zhihu.daily.R;
import com.zhihu.daily.adapter.WebPageAdapterTop;

import java.util.ArrayList;
import java.util.List;

import static com.zhihu.daily.MainActivity.newspaper;

public class TopNewsDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_news_detail);

        WebView webView = findViewById(R.id.top_news);
        ViewPager toppp = findViewById(R.id.toppp);
        ArrayList<WebView> webViews= new ArrayList<>();
        for(int i = 0;i<5;i++){
            addView(webViews,newspaper.getTop_stories().get(i).getUrl());
        }

        toppp.setAdapter(new WebPageAdapterTop(webViews));
        toppp.setCurrentItem(getIntent().getIntExtra("position",1));
    }


    @SuppressLint("SetJavaScriptEnabled")
    public void addView(List<WebView> webViewList, String url){
        WebView webView = new WebView(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);

        }
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBlockNetworkImage(false);
        webView.loadUrl(url);
        webViewList.add(webView);

    }

}

