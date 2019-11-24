package com.zhihu.daily;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.zhihu.daily.NewsDetail1.ViewOfNewsDetail;
import com.zhihu.daily.adapter.WebPageAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.zhihu.daily.MainActivity.newsList;
import static com.zhihu.daily.MainActivity.newspaper;
import static com.zhihu.daily.MainActivity.url1;
import static com.zhihu.daily.MainActivity.url2;
import static com.zhihu.daily.MainActivity.yesterdayNewspaper;

public class NewsDetail extends AppCompatActivity implements ViewOfNewsDetail {
    private ArrayList<String> urlList;
    private ArrayList<WebView> webViews = new ArrayList<>();
    private int position1;


    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);


        WebView webView_two = findViewById(R.id.web_view_two_two);
        ViewPager webScroll = findViewById(R.id.web_scroll);

//
//        webView_two.getSettings().setJavaScriptEnabled(true);
//        webView_two.getSettings().setBlockNetworkImage(false);
//
//        webView_two.loadUrl(url1);
        urlList = getIntent().getStringArrayListExtra("urls");
        position1 = getIntent().getIntExtra("position",1);
        for(int i =0;i<newsList.size();i++){
            if(newsList.get(i).getImageUrl()!=null){
                addView(webViews,newsList.get(i).getUrl());
            }
        }

        WebPageAdapter adapter = new WebPageAdapter(webViews);
        webScroll.setAdapter(adapter);
        webScroll.setCurrentItem(position1);

        webScroll.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {



            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

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
