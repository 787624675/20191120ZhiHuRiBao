package com.zhihu.daily.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class WebPageAdapterTop extends PagerAdapter {
    private List<WebView> webViewList;

    public WebPageAdapterTop(List<WebView> webViewList) {
        this.webViewList = webViewList;
    }

    public List<WebView> getWebViewList() {
        return webViewList;
    }

    public void setWebViewList(List<WebView> webViewList) {
        this.webViewList = webViewList;
    }

    @Override
    public int getCount() {
        return webViewList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView(webViewList.get(position));
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        container.addView(webViewList.get(position));
        return webViewList.get(position);
    }
}
