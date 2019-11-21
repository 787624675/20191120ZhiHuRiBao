package com.zhihu.daily.adapter;

import android.annotation.SuppressLint;
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
import com.zhihu.daily.viewholder.News;
import com.zhihu.daily.viewholder.NewsViewHolder;

import java.util.ArrayList;
import java.util.List;

public  class NewsAdapter2 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    //设置列表的点击监听器
    private OnItemClickListener monItemClickListener;
    public interface OnItemClickListener{
        void onItemClicked(View view,int position);
    }
    public void setMonItemClickListener(OnItemClickListener onItemClickListener){
        this.monItemClickListener = onItemClickListener;
    }
    private List<News> mNewsList;
    private List<News> topNews;
    private List<ImageView> imageViewList;

    public NewsAdapter2(List<News> mNewsList, List<News> topNews, List<ImageView> imageViewList) {
        this.mNewsList = mNewsList;
        this.topNews = topNews;
        this.imageViewList = imageViewList;
    }

    public NewsAdapter2(List<News> mNewsList, List<News> topNews) {
        this.mNewsList = mNewsList;
        this.topNews = topNews;
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



    public NewsAdapter2(List<News> newsList){
        mNewsList = newsList;

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

        public View getGradient() {
            return gradient;
        }

        public void setGradient(View gradient) {
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
        View gradient;
        ViewPager viewPager;
        LinearLayout point;
        ImageView imageView;

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


    @SuppressLint("CheckResult")
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        if (holder.getItemViewType()==3){
            final BannerViewHolder bannerViewHolder = (BannerViewHolder)holder;
            bannerViewHolder.getTitle().setText(topNews.get(position).getTitle());
            bannerViewHolder.getWriter().setText(topNews.get(position).getReader());
            bannerViewHolder.viewPager.setAdapter(new PagerAdapter1(imageViewList));
            bannerViewHolder.viewPager.setCurrentItem(0);






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
