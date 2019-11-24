package com.zhihu.daily.NewsDetail1;

import android.view.View;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;

public class ModelOfNewsDetail {
    private ArrayList<WebView> webList;
    private ArrayList<String> webUrl;

    public ArrayList<WebView> getWebList() {
        return webList;
    }

    public void setWebList(ArrayList<WebView> webList) {
        this.webList = webList;
    }

    public ArrayList<String> getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(ArrayList<String> webUrl) {
        this.webUrl = webUrl;
    }

    public ModelOfNewsDetail(ArrayList<WebView> webList, ArrayList<String> webUrl) {
        this.webList = webList;
        this.webUrl = webUrl;
    }
}
