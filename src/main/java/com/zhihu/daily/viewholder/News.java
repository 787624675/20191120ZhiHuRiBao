package com.zhihu.daily.viewholder;

import com.zhihu.daily.NewsDetail;

public class News {
    private String title;
    private int imageId;
    private String reader;
    private String imageUrl;
    private String date;
    private String url;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public News(String title, int imageId, String reader) {
        this.title = title;
        this.reader = reader;
        this.imageId = imageId;
    }
    public News(String title, String imageUrl,String reader) {
        this.title = title;
        this.reader = reader;
        this.imageUrl = imageUrl;
    }

    public News(String title, String imageUrl,String reader,String url) {
        this.title = title;
        this.reader = reader;
        this.imageUrl = imageUrl;
        this.url = url;
    }
    public News(String title, String imageUrl,String reader,String date,String url) {
        this.title = title;
        this.reader = reader;
        this.imageUrl = imageUrl;
        this.date = date;
        this.url = url;
    }
    public News(String title, int imageId) {
        this.title = title;

        this.imageId = imageId;
    }

    public String getReader() {
        return reader;
    }

    public void setReader(String reader) {
        this.reader = reader;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getTitle() {
        return title;
    }

    public int getImageId() {
        return imageId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
