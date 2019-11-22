package com.zhihu.daily.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.zhihu.daily.R;

import java.util.ArrayList;

public class BannerViewHolder extends RecyclerView.ViewHolder {
    public BannerViewHolder(@NonNull View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.sum2);
        writer = itemView.findViewById(R.id.writer2);
        gradient = itemView.findViewById(R.id.gradient2);
        viewPager = itemView.findViewById(R.id.vp02);
        point = itemView.findViewById(R.id.point2);
        imageView = itemView.findViewById(R.id.imageview);

    }

    public TextView getTitle() {
        return title;
    }

    public void setTitle(TextView title) {
        this.title = title;
    }

    public TextView getWriter() {
        return writer;
    }

    public void setWriter(TextView writer) {
        this.writer = writer;
    }

    public ImageView getGradient() {
        return gradient;
    }

    public void setGradient(ImageView gradient) {
        this.gradient = gradient;
    }

    public ViewPager getViewPager() {
        return viewPager;
    }

    public void setViewPager(ViewPager viewPager) {
        this.viewPager = viewPager;
    }

    public LinearLayout getPoint() {
        return point;
    }

    public void setPoint(LinearLayout point) {
        this.point = point;
    }

    TextView title;
    TextView writer;
    ImageView gradient;
    ViewPager viewPager;
    LinearLayout point;
    ImageView imageView;
    int last;
    int[] gradients;

    public int[] getGradients() {
        return gradients;
    }

    public void setGradients(int[] gradients) {
        this.gradients = gradients;
    }

    public int getLast() {
        return last;
    }

    public void setLast(int last) {
        this.last = last;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public ArrayList<ImageView> getImageViews() {
        return imageViews;
    }

    public void setImageViews(ArrayList<ImageView> imageViews) {
        this.imageViews = imageViews;
    }

    ArrayList<ImageView> imageViews;


}
