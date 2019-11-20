package com.zhihu.daily;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.zhihu.daily.adapter.NewsAdapter;
import com.zhihu.daily.bean.Newspaper;
import com.zhihu.daily.bean.YesterdayNewspaper;
import com.zhihu.daily.viewholder.Date;
import com.zhihu.daily.viewholder.News;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Timer;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {


    private SwipeRefreshLayout mRefreshLayout;
    private BottomScrollView mScrollView;
    public static String url1;
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler();
    private Newspaper newspaper;
    private String result;
    private YesterdayNewspaper yesterdayNewspaper;
    private TextView day;
    private TextView month;
    private TextView shouye;
    private ViewPager viewPager;
    private TextView sum;
    private TextView writer;
    private LinearLayout point;
    private int[] image = {R.drawable.p1,R.drawable.p2,R.drawable.p3,R.drawable.p4,R.drawable.p5};
    private int[] gradients = {R.drawable.gradient_for_viewpager,R.drawable.gradient_for_viewpager_1,R.drawable.gradient_for_viewpager_2,R.drawable.gradient_for_viewpager_3,R.drawable.gradient_for_viewpager_4};
    private View gradient;
    private NewsAdapter1 adapter2;
    private ArrayList<ImageView> images;
    private int lastPointPosition;
    private List<News> newsList = new ArrayList<>();
    private List<News> yesterdayNewspaperList = new ArrayList<>();
    private CircleImageView touxiang;
    private RecyclerView recyclerView ;
    private TextView test ;
    Gson gson = new Gson();

    public MainActivity() {
    }
    //RecyclerView
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        sendRequestWithOkHttp();


        while(newspaper==null) {
        }

        recyclerView = findViewById(R.id.swipe_target);
        mRefreshLayout = findViewById(R.id.refresh_layout);
        mScrollView = findViewById(R.id.scroll_view);
        viewPager = findViewById(R.id.vp0);
        sum = findViewById(R.id.sum);
        point = findViewById(R.id.point);
        writer = findViewById(R.id.writer);
        gradient =   findViewById(R.id.gradient) ;
        images = new ArrayList<>();
        day = findViewById(R.id.day);
        month = findViewById(R.id.month);
        shouye = findViewById(R.id.shouye);
        touxiang = findViewById(R.id.touxiang);
        test = findViewById(R.id.text);

        OkHttpUtils okHttpUtils =  OkHttpUtils.getInstance();




        touxiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,SettingPage.class);
                startActivity(i);

            }
        });
        for(int i =0;i<image.length;i++){
            //添加图片

            ImageView imageView = new ImageView(this);
            Glide.with(this).load(newspaper.getTop_stories().get(i).getImage()).into(imageView);
            images.add(imageView);
            lastPointPosition = 0;
            //添加图片的指示器
            ImageView point1 = new ImageView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(18,18);
            params.rightMargin = 10;
            point1.setLayoutParams(params);
            point1.setBackgroundResource(R.drawable.point_bg);
            if(i==0){
                point1.setEnabled(true);
            }else {
                point1.setEnabled(false);
            }
            point.addView(point1);
        }
        //添加图片对应的文字
        sum.setText(newspaper.getTop_stories().get(0).getTitle());
        writer.setText(newspaper.getTop_stories().get(0).getHint());

        viewPager.setAdapter(new MyPagerAdapter());
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


            }

            @Override
            public void onPageSelected(int position) {
                sum.setText(newspaper.getTop_stories().get(position).getTitle());
                writer.setText(newspaper.getTop_stories().get(position).getHint());
                gradient.setBackgroundResource(gradients[position]);
                point.getChildAt(position).setEnabled(true);
                point.getChildAt(lastPointPosition).setEnabled(false);
                lastPointPosition = position;


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });






        //这一段用来写RecyclerView
        initAction();
        initNews();


        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter2 = new NewsAdapter1(newsList);
        if(newsList!=null){
            for(int i=0;i<newsList.size();i++){
                for(int j=0;j<i;j++){
                    if (newsList.get(j).getReader().equals(newsList.get(i).getReader())){
                        newsList.remove(j);
                    }
                }

            }
        }
        adapter2.notifyDataSetChanged();
        recyclerView.setAdapter(adapter2);


        RecyclerView.OnScrollListener loadingMoreListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
            @SuppressLint("HandlerLeak")

            Handler handler1 = new Handler(){
                public void handleMessage(Message msg){
                    super.handleMessage(msg);
                    if(true){

                    }

                }
            } ;
            @Override
            public void onScrolled(@NonNull final RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(dy>0)
                {
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int pastVisiblesItems = layoutManager.findFirstVisibleItemPosition();
                    if((visibleItemCount + pastVisiblesItems) >= totalItemCount){

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(3000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                        initNewsYesterday();

                    }

                }
            }
        };


        recyclerView.addOnScrollListener(loadingMoreListener);
        sendRequestWithOkHttp(yesterdayOf(newspaper.getDate()));

        if(newsList!=null){
            for(int i =0;i<newsList.size();i++){
                for(int j =0;j<i;j++){
                    if(newsList.get(j).getReader().equals(newsList.get(i).getReader())){
                        newsList.remove(j);
                    }

                }
            }
        }



        adapter2.setMonItemClickListener(new NewsAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(View view, int position) {
                view.setBackgroundResource(R.drawable.color);
                Toast.makeText(MainActivity.this, "跳转到新闻详情页", Toast.LENGTH_SHORT).show();
                url1 = newsList.get(position).getUrl();
                startActivity(new Intent(MainActivity.this,NewsDetail.class));
                view.setBackgroundResource(R.drawable.color1);
            }
        });

        //这一段用来设置日期

        Calendar calendar = new GregorianCalendar();
        day.setText(newspaper.getDate().substring(6,8));
        month.setText(monthToString(Integer.valueOf(newspaper.getDate().substring(4,6))));
        if(calendar.get(Calendar.HOUR)>=10||calendar.get(Calendar.HOUR)<=3){
            shouye.setText("早点休息~");
        }
    }








    public String monthToString(int month){
        switch (month){
            default:return "默认日期";
            case 1:return "一 月";
            case 2:return "二 月";
            case 3:return "三 月";
            case 4:return "四 月";
            case 5:return "五 月";
            case 6:return "六 月";
            case 7:return "七 月";
            case 8:return "八 月";
            case 9:return "九 月";
            case 10:return "十 月";
            case 11:return "十一月";
            case 12:return "十二月";

        }
    }


    //根据页面的个数动态添加圆点指示器









    //初始化新闻
    private void initNews(){


        for(int i=0;i<newspaper.getStories().size();i++){
            News news = new News(newspaper.getStories().get(i).getTitle(), newspaper.getStories().get(i).getImages()[0], newspaper.getStories().get(i).getHint(),newspaper.getStories().get(i).getUrl());
            newsList.add(news);

        }




    }
    private void initNewsYesterday(){


        sendRequestWithOkHttp(yesterdayNewspaper==null?newspaper.getDate():yesterdayNewspaper.getDate());
        newsList.add(new News(null,null,transDate(yesterdayNewspaper==null?newspaper.getDate():yesterdayNewspaper.getDate())));
        for(int i=0;i< yesterdayNewspaper.getStories().size();i++){
            News news = new News(yesterdayNewspaper.getStories().get(i).getTitle(), yesterdayNewspaper.getStories().get(i).getImages()[0], yesterdayNewspaper.getStories().get(i).getHint(),yesterdayNewspaper.getStories().get(i).getUrl());
            newsList.add(news);
        }

        adapter2.setmNewsList(newsList);
        adapter2.notifyDataSetChanged();
        adapter2.setMonItemClickListener(new NewsAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(View view, int position) {
                view.setBackgroundResource(R.drawable.color);
                Toast.makeText(MainActivity.this, "跳转到新闻详情页", Toast.LENGTH_SHORT).show();
                url1 = newsList.get(position).getUrl();
                startActivity(new Intent(MainActivity.this,NewsDetail.class));
                view.setBackgroundResource(R.drawable.color1);
            }
        });

    }







    //ViewPager的适配器
    private class MyPagerAdapter extends PagerAdapter{
        @Override
        public int getCount() {
            return image.length;
        }
        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view==object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, final int position) {
            container.addView(images.get(position));
            images.get(position).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    url1 = newspaper.getTop_stories().get(position).getUrl();
                    startActivity(new Intent(MainActivity.this,NewsDetail.class));
                }
            });
            return images.get(position);
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
            object=null;
        }
    }







    //设置0.5秒后加载试图消失
    private void initAction() {
        //模拟下拉刷新，0.5秒后，下拉刷新状态视图消失
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        newsList.clear();
                        sendRequestWithOkHttp(yesterdayOf(newspaper.getDate()));
                        initNews();
                        mRefreshLayout.setRefreshing(false);
                    }
                }, 2000);
            }
        });









    }




    //打开程序时发送请求
    private void sendRequestWithOkHttp(){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("https://news-at.zhihu.com/api/3/news/latest").get().build();
        final Call call = client.newCall(request);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response response = call.execute();
                    if(response.body()!=null){
                        String result = response.body().string();
                        Message message = Message.obtain();
                        message.what = 1;
                        message.obj = result;
                        handler.sendMessage(message);
                        parseJSONWithGson(result);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }).start();
    }






    //加载上一天新闻时发送请求
    private void sendRequestWithOkHttp(String date){
        OkHttpClient client1 = new OkHttpClient();
        final Request request = new Request.Builder().url("https://news-at.zhihu.com/api/3/news/before/"+date).get().build();
        final Call call = client1.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull final IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        test.setText(e.getMessage());
                    }
                });

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull final Response response) throws IOException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(response.isSuccessful()) {
                            try {

                                yesterdayNewspaper = gson.fromJson(response.body().string(),YesterdayNewspaper.class);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                });

            }
        });

    }








    //解析json数据
    private void parseJSONWithGson(String jsonData){

        newspaper = gson.fromJson(jsonData,Newspaper.class);

    }




    //recyclerview的适配器
    public static class NewsAdapter1 extends RecyclerView.Adapter<NewsAdapter1.ViewHolder> {
        //设置列表的点击监听器
        private com.zhihu.daily.adapter.NewsAdapter.OnItemClickListener monItemClickListener;
        public interface OnItemClickListener{
            void onItemClicked(View view,int position);
        }
        public void setMonItemClickListener(com.zhihu.daily.adapter.NewsAdapter.OnItemClickListener onItemClickListener){
            this.monItemClickListener = onItemClickListener;
        }
        private List<News> mNewsList;

        public List<News> getmNewsList() {
            return mNewsList;
        }

        public void setmNewsList(List<News> mNewsList) {
            this.mNewsList = mNewsList;
        }

        static class ViewHolder extends RecyclerView.ViewHolder{
            ImageView newsImage;
            TextView newsTitle;
            TextView newsReader;
            TextView dates;


            //新增内容
            public ViewHolder(@NonNull View view,final com.zhihu.daily.adapter.NewsAdapter.OnItemClickListener onClickListener) {
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

        public NewsAdapter1(List<News> newsList){
            mNewsList = newsList;

        }

        public NewsAdapter1(List<News> newsList,List<Date> dateList){
            mNewsList = newsList;
        }

        @NonNull
        @Override
        public NewsAdapter1.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = null;
            if (viewType==1){
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.for_date,parent,false);
            }else{
                view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.news_item,parent,false);}
            NewsAdapter1.ViewHolder holder = new NewsAdapter1.ViewHolder(view,monItemClickListener);
            //新增内容



            return holder;
        }



        @Override
        public void onBindViewHolder(@NonNull NewsAdapter1.ViewHolder holder, final int position) {
            News news = mNewsList.get(position);
            if (news.getTitle()==null){
                holder.dates.setText(news.getReader());
            }else {

                Glide.with(holder.newsImage).load(news.getImageUrl()).into(holder.newsImage);
                //终于解决了问题！！把with里面写成“holder.newsImage”
                holder.newsTitle.setText(news.getTitle());
                holder.newsReader.setText(news.getReader());
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



       //实际上并不需要这个函式。。。。。。。。。
    //把日期转换格式
    public String transDate(String date){
        //这个函数用来将类似于“20191118”转化成“11 月 18 日”
        String ri = date.substring(date.length()-2,date.length());
        String yue = date.substring(date.length()-4,date.length()-2);
        String newDate = yue + " 月 "+ ri+" 日";
        return newDate;
    }

    //求上一天日期
    private String yesterdayOf(String date){
        String yesterday = date;
        String ri_ = date.substring(date.length()-2,date.length());
        String yue_ = date.substring(date.length()-4,date.length()-2);
        String nian_ = date.substring(0,4);
        int ri = Integer.valueOf(ri_);
        int yue = Integer.valueOf(yue_);
        int nian = Integer.valueOf(nian_);
        if(ri==1&&yue==1){
            nian-=1;
            yue = 12;
        }
        if(ri == 1&&yue!=1){
            yue -=1;
            ri = dayOfMonth(yue,nian);
        }
        if(ri!=1){
            ri-=1;
        }

        yesterday = nian+""+yue+ri;
        return yesterday;
    }


    private int dayOfMonth(int yue,int nian){
        switch (yue){
            case 1: return 31;
            case 2: return  bissextile(nian)?29:28;
            case 3: return  31;
            case 4: return 30;
            case 5: return 31;
            case 6: return 30;
            case 7: return 31;
            case 8: return 31;
            case 9: return 30;
            case 10: return 31;
            case 11: return 30;
            case 12: return 31;
            default: return 30;

        }

    }
    //判断闰年与否
    boolean bissextile(int year) {  //创建boolean类型的方法
        if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {  //平闰年判断算法
            return true;
        } else {
            return false;
        }
    }











}
