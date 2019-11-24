package com.zhihu.daily.adapter;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.zhihu.daily.R;
import com.zhihu.daily.viewholder.BannerViewHolder;
import com.zhihu.daily.viewholder.News;
import com.zhihu.daily.viewholder.NewsViewHolder;

import java.nio.DoubleBuffer;
import java.util.ArrayList;
import java.util.List;

public  class NewsAdapter2 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    //设置列表的点击监听器
    private OnItemClickListener monItemClickListener;
    private ArrayList<Bitmap> bitmap_one;

    public ArrayList<Bitmap> getBitmap_one() {
        return bitmap_one;
    }

    public void setBitmap_one(ArrayList<Bitmap> bitmap_one) {
        this.bitmap_one = bitmap_one;
    }

    public interface OnItemClickListener{
        void onItemClicked(View view,int position);
    }
    public void setMonItemClickListener(OnItemClickListener onItemClickListener){
        this.monItemClickListener = onItemClickListener;
    }
    private List<News> mNewsList;
    private List<News> topNews;
    private List<ImageView> imageViewList;
    private List<ImageView> points;
    int[] gradients;
    private ArrayList<ImageView> gradients_one;

    public ArrayList<ImageView> getGradients_one() {
        return gradients_one;
    }

    public void setGradients_one(ArrayList<ImageView> gradients_one) {
        this.gradients_one = gradients_one;
    }

    public List<ImageView> getImageViewList() {
        return imageViewList;
    }

    public void setImageViewList(List<ImageView> imageViewList) {
        this.imageViewList = imageViewList;
    }

    public List<ImageView> getPoints() {
        return points;
    }

    public void setPoints(List<ImageView> points) {
        this.points = points;
    }

    public int[] getGradients() {
        return gradients;
    }

    public void setGradients(int[] gradients) {
        this.gradients = gradients;
    }

    public NewsAdapter2(List<News> mNewsList, List<News> topNews, List<ImageView> imageViewList, List<ImageView> points, int[] gradients) {
        this.mNewsList = mNewsList;
        this.topNews = topNews;
        this.imageViewList = imageViewList;
        this.points = points;
        this.gradients = gradients;

    }
    public NewsAdapter2(List<News> mNewsList, List<News> topNews, List<ImageView> imageViewList, List<ImageView> points, ArrayList<ImageView> gradient_one) {
        this.mNewsList = mNewsList;
        this.topNews = topNews;
        this.imageViewList = imageViewList;
        this.points = points;
        this.gradients_one = gradient_one;

    }
    public NewsAdapter2(List<News> mNewsList, List<News> topNews, List<ImageView> imageViewList,List<ImageView> points) {
        this.mNewsList = mNewsList;
        this.topNews = topNews;
        this.imageViewList = imageViewList;
        this.points = points;
    }



    public List<News> getTopNews() {
        return topNews;
    }

    public void setTopNews(List<News> topNews) {
        this.topNews = topNews;
    }

    public List<News> getmNewsList() {
        return mNewsList;
    }

    public void setmNewsList(List<News> mNewsList) {
        this.mNewsList = mNewsList;
    }







    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        if(viewType == 3){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.banner,parent,false);
            return new BannerViewHolder(view);
        }
        else if (viewType==1){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.for_date,parent,false);
            return new NewsViewHolder(view,monItemClickListener);
        }else{
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.news_item,parent,false);
            return new NewsViewHolder(view,monItemClickListener);
        }






    }


    @SuppressLint("CheckResult")
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        if (holder.getItemViewType()==3){

            final BannerViewHolder bannerViewHolder = (BannerViewHolder)holder;
            bannerViewHolder.getTitle().setText(topNews.get(position).getTitle());
            bannerViewHolder.getWriter().setText(topNews.get(position).getReader());
            bannerViewHolder.getViewPager().setAdapter(new PagerAdapter1(imageViewList));
            bannerViewHolder.getViewPager().setCurrentItem(0);
            final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(18,18);
            params.rightMargin = 20;


            bannerViewHolder.getPoint().removeAllViews();
            for (int i = 0;i<imageViewList.size();i++){
                points.get(i).setLayoutParams(params);
                points.get(i).setImageResource(R.drawable.point_bg);
                if(i==0){
                points.get(i).setEnabled(true);
            }else {
                points.get(i).setEnabled(false);
            }


                bannerViewHolder.getPoint().addView(points.get(i));//错误记录：这里把i写成position

            }


            bannerViewHolder.getViewPager().addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


                }

                @Override
                public void onPageSelected(int position) {


                    bannerViewHolder.getTitle().setText(topNews.get(position).getTitle());
                    bannerViewHolder.getWriter().setText(topNews.get(position).getReader());
                    bannerViewHolder.getGradient().setBackgroundResource(gradients[position]);
                    bannerViewHolder.getPoint().getChildAt(position).setEnabled(true);
                    bannerViewHolder.getPoint().getChildAt(position==0?4:position-1).setEnabled(false);
                    bannerViewHolder.setLast(position) ;

                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });







        }
        else if (holder.getItemViewType()==1){
            NewsViewHolder newsViewHolder = (NewsViewHolder)holder;
            newsViewHolder.getDates().setText(mNewsList.get(position).getReader());
        }else {
            NewsViewHolder newsViewHolder = (NewsViewHolder)holder;
            newsViewHolder.getNewsTitle().setText(mNewsList.get(position).getTitle());
            newsViewHolder.getNewsReader().setText(mNewsList.get(position).getReader());
            Glide.with(newsViewHolder.itemView).load(mNewsList.get(position).getImageUrl()).into(newsViewHolder.getNewsImage());
        }
    }


    @Override
    public int getItemCount() {
        return mNewsList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(position==0){
            return 3;
        } else if(mNewsList.get(position).getTitle()==null){
            return 1;
        }else return 2;


    }

}
