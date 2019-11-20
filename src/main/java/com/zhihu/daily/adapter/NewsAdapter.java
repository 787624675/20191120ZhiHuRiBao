package com.zhihu.daily.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.zhihu.daily.MainActivity;
import com.zhihu.daily.R;
import com.zhihu.daily.viewholder.News;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private Context context;

    //新增内容
    private OnItemClickListener monItemClickListener;
    public interface OnItemClickListener{
        void onItemClicked(View view,int position);
    }
    public void setMonItemClickListener(OnItemClickListener onItemClickListener){
        this.monItemClickListener = onItemClickListener;
    }

    //新增内容
    private List<News> mNewsList;
    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView newsImage;
        TextView newsTitle;
        TextView newsReader;
                                              //新增内容
        public ViewHolder(@NonNull View view,final OnItemClickListener onClickListener) {
            super(view);

            newsImage = view.findViewById(R.id.news_image);
            newsTitle = view.findViewById(R.id.news_title);
            newsReader = view.findViewById(R.id.news_reader);
            //新增内容
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onClickListener!=null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            onClickListener.onItemClicked(v,position);
                        }
                    }
                }
            });
            //新增内容
        }
    }
    public NewsAdapter(List<News> newsList){
        mNewsList = newsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_item,parent,false);
        ViewHolder holder = new ViewHolder(view,monItemClickListener);
                                   //新增内容



        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        News news = mNewsList.get(position);
        holder.newsImage.setImageResource(news.getImageId());
    //    Glide.with(context).load(news.getImageUrl()).into(holder.newsImage);

        holder.newsTitle.setText(news.getTitle());
        holder.newsReader.setText(news.getReader());
        //新增内容

        //新增内容
    }

    @Override
    public int getItemCount() {
        return mNewsList.size();
    }

}
