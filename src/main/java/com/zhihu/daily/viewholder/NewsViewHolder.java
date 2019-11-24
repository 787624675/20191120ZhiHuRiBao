package com.zhihu.daily.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zhihu.daily.R;
import com.zhihu.daily.adapter.NewsAdapter2;

public class NewsViewHolder extends RecyclerView.ViewHolder {
    public NewsViewHolder(@NonNull View itemView) {
        super(itemView);
        newsImage = itemView.findViewById(R.id.news_image);
        newsTitle = itemView.findViewById(R.id.news_title);
        newsReader = itemView.findViewById(R.id.news_reader);
        dates = itemView.findViewById(R.id.dates);
    }
    ImageView newsImage;
    TextView newsTitle;
    TextView newsReader;
    TextView dates;

    public ImageView getNewsImage() {
        return newsImage;
    }

    public void setNewsImage(ImageView newsImage) {
        this.newsImage = newsImage;
    }

    public TextView getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(TextView newsTitle) {
        this.newsTitle = newsTitle;
    }

    public TextView getNewsReader() {
        return newsReader;
    }

    public void setNewsReader(TextView newsReader) {
        this.newsReader = newsReader;
    }

    public TextView getDates() {
        return dates;
    }

    public void setDates(TextView dates) {
        this.dates = dates;
    }

    //新增内容
    public NewsViewHolder(@NonNull View view, final NewsAdapter2.OnItemClickListener onClickListener) {
        super(view);
        newsImage = view.findViewById(R.id.news_image);
        newsTitle = view.findViewById(R.id.news_title);
        newsReader = view.findViewById(R.id.news_reader);
        dates = view.findViewById(R.id.dates);

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
