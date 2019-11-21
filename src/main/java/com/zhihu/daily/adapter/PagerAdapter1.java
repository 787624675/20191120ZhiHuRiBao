package com.zhihu.daily.adapter;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;

import com.zhihu.daily.MainActivity;

import java.util.List;

public class PagerAdapter1 extends PagerAdapter {
    private List<ImageView> imageViews;
    private List<String> titles;
    private List<String> imageUrls;
    private List<String> writers;
    private List<ImageView> points;

    public PagerAdapter1(List<ImageView> imageViews) {

        this.imageViews = imageViews;
    }

    public List<ImageView> getImageViews() {
        return imageViews;
    }

    public void setImageViews(List<ImageView> imageViews) {
        this.imageViews = imageViews;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    @Override
    public int getCount() {
        return 5;

    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(imageViews.get(position));
        return imageViews.get(position);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
