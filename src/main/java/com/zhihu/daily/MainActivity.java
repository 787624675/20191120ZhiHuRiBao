package com.zhihu.daily;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.zhihu.daily.TopNewsDetail.TopNewsDetail;
import com.zhihu.daily.ViewOfMain.ViewOfMainActivity;
import com.zhihu.daily.adapter.NewsAdapter2;
import com.zhihu.daily.bean.Newspaper;
import com.zhihu.daily.bean.YesterdayNewspaper;
import com.zhihu.daily.viewholder.News;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity implements ViewOfMainActivity {
    private SwipeRefreshLayout mRefreshLayout;
    private BottomScrollView mScrollView;
    public static String url1;
    public static String url0;
    public static String url2;
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler();
    public static Newspaper newspaper;
    private String result;
    private YesterdayNewspaper yesterdayNewspaper;
    private TextView day;
    private TextView month;
    private TextView shouye;
    private ViewPager viewPager;
    private TextView sum;
    private TextView writer;
    private LinearLayout point;
    private static int[] image = {R.drawable.p1,R.drawable.p2,R.drawable.p3,R.drawable.p4,R.drawable.p5};
    private static int[] gradients = {R.drawable.gradient_for_viewpager,R.drawable.gradient_for_viewpager_1,R.drawable.gradient_for_viewpager_2,R.drawable.gradient_for_viewpager_3,R.drawable.gradient_for_viewpager_4};
    private View gradient;
    private NewsAdapter2 adapter2;
    private  ArrayList<ImageView> images = new ArrayList<>();
    private static int lastPointPosition;
    public static List<News> newsList = new ArrayList<>();
    private List<News> topNewsList = new ArrayList<>();
    private List<News> yesterdayNewspaperList = new ArrayList<>();
    private CircleImageView touxiang;
    private RecyclerView recyclerView ;
    private TextView test ;
    private ArrayList<ImageView> points = new ArrayList<>();
    private ArrayList<ImageView> gradient_one = new ArrayList<>();
    private ArrayList<ImageView> gradient_zero = new ArrayList<>();
    private ArrayList<String> urlList = new ArrayList<>();
    Gson gson = new Gson();
    private Canvas mCanvas;
    private Paint mPaint;
    Bitmap bgBitmap;
    int i1;
    private ArrayList<Bitmap> bitmap_one;


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
        viewPager = findViewById(R.id.vp02);



        while(newspaper==null){}







        for(int i=0;i<5;i++){
            i1 = i;
            ImageView imageView = new ImageView(this);
            Glide.with(this).load(newspaper.getTop_stories().get(i).getImage()).into(imageView);

            images.add(imageView);
            gradient_one.add(imageView);
            ImageView point1 = new ImageView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(40,18);
            params.rightMargin = 30;
            point1.setLayoutParams(params);
            point1.setBackgroundResource(R.drawable.point_bg);
            if(i==0){
                point1.setEnabled(true);
            }else {
                point1.setEnabled(false);
            }
            points.add(point1);
      //      Glide(newspaper.getTop_stories().get(i).getImage())



//            Palette.from(bm).generate(new Palette.PaletteAsyncListener(){
//                @Override
//                public void onGenerated(@Nullable Palette palette) {
//                    if(palette==null)return;
//                    //palette取色不一定取得到某些特定的颜色，这里通过取多种颜色来避免取不到颜色的情况
//                    if (palette.getDarkVibrantColor(Color.TRANSPARENT) != Color.TRANSPARENT) {
//                        createLinearGradientBitmap(palette.getDarkVibrantColor(Color.TRANSPARENT), palette.getVibrantColor(Color.TRANSPARENT),gradient_one.get(i1),bitmap_one);
//                    } else if (palette.getDarkMutedColor(Color.TRANSPARENT) != Color.TRANSPARENT) {
//                        createLinearGradientBitmap(palette.getDarkMutedColor(Color.TRANSPARENT), palette.getMutedColor(Color.TRANSPARENT),gradient_one.get(i1),bitmap_one);
//                    } else {
//                        createLinearGradientBitmap(palette.getLightMutedColor(Color.TRANSPARENT), palette.getLightVibrantColor(Color.TRANSPARENT),gradient_one.get(i1),bitmap_one);
//                    }
//
//                }
//            });
//



        }
        for(final ImageView imageView : images){
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, TopNewsDetail.class);
                    intent.putExtra("position",images.indexOf(imageView));
                    startActivity(intent);
                }
            });
        }






        for(int i =0;i<5;i++){
            topNewsList.add(new News(
                    newspaper.getTop_stories().get(i).getTitle(),
                    newspaper.getTop_stories().get(i).getImage(),
                    newspaper.getTop_stories().get(i).getHint(),
                    newspaper.getTop_stories().get(i).getUrl()));
        }
        recyclerView = findViewById(R.id.swipe_target);
        mRefreshLayout = findViewById(R.id.refresh_layout);
       // mScrollView = findViewById(R.id.scroll_view);
//        viewPager = findViewById(R.id.vp0);
//        sum = findViewById(R.id.sum);
//        point = findViewById(R.id.point);
//        writer = findViewById(R.id.writer);
//        gradient =   findViewById(R.id.gradient) ;

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







        //这一段用来写RecyclerView
        initAction();
        initNews();


        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter2 = new NewsAdapter2(newsList,topNewsList,images,points,gradient_one);
        adapter2.setGradients(gradients);
        adapter2.setBitmap_one(bitmap_one);
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

//                        new Thread(new Runnable() {
//                            @Override
//                            public void run() {
//                                try {
//                                    Thread.sleep(3000);
//                                } catch (InterruptedException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        });

                        initNewsYesterday();

                    }

                }
            }
        };


        recyclerView.addOnScrollListener(loadingMoreListener);
        sendRequestWithOkHttp(newspaper.getDate());

        if(newsList!=null){
            for(int i =0;i<newsList.size();i++){
                for(int j =0;j<i;j++){
                    if(newsList.get(j).getReader().equals(newsList.get(i).getReader())){
                        newsList.remove(j);
                    }

                }
            }

        }

        adapter2.setMonItemClickListener(new NewsAdapter2.OnItemClickListener() {
            @Override
            public void onItemClicked(View view, int position) {
                view.setBackgroundResource(R.drawable.color);
                Toast.makeText(MainActivity.this, "跳转到新闻详情页", Toast.LENGTH_SHORT).show();
                if(position>0){
                    url0 = newsList.get(position-1).getUrl();
                }
                url1 = newsList.get(position).getUrl();
                if(position<newsList.size()-1){
                    url2 = newsList.get(position+1).getUrl();
                }
                Intent intent = new Intent(MainActivity.this,NewsDetail.class);
                intent.putStringArrayListExtra("urls",urlList);
                intent.putExtra("position",position);
                startActivity(intent);
                view.setBackgroundResource(R.drawable.color1);
            }
        });


        //









//        for(int i =0;i<image.length;i++){
//            //添加图片
//
//            ImageView imageView = new ImageView(this);
//            Glide.with(this).load(newspaper.getTop_stories().get(i).getImage()).into(imageView);
//            images.add(imageView);
//            lastPointPosition = 0;
//            //添加图片的指示器
//            ImageView point1 = new ImageView(this);
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(18,18);
//            params.rightMargin = 10;
//            point1.setLayoutParams(params);
//            point1.setBackgroundResource(R.drawable.point_bg);
//            if(i==0){
//                point1.setEnabled(true);
//            }else {
//                point1.setEnabled(false);
//            }
//            bannerViewHolder1.getPoint().addView(point1);
//        }
//        //添加图片对应的文字
//        bannerViewHolder1.getTitle().setText(newspaper.getTop_stories().get(0).getTitle());
//        bannerViewHolder1.getWriter().setText(newspaper.getTop_stories().get(0).getHint());
//
//        bannerViewHolder1.getViewPager().setAdapter(new MyPagerAdapter());
//        bannerViewHolder1.getViewPager().addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//
//
//
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });


        //Viewpager(Banner)


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
            urlList.add(newspaper.getStories().get(i).getUrl());

        }




    }
    private void initNewsYesterday(){


        sendRequestWithOkHttp(yesterdayNewspaper==null?newspaper.getDate():yesterdayNewspaper.getDate());
        newsList.add(new News(null,null,transDate(yesterdayNewspaper==null?newspaper.getDate():yesterdayNewspaper.getDate())));
        urlList.add("yes");
        for(int i=0;i< yesterdayNewspaper.getStories().size();i++){
            News news = new News(yesterdayNewspaper.getStories().get(i).getTitle(), yesterdayNewspaper.getStories().get(i).getImages()[0], yesterdayNewspaper.getStories().get(i).getHint(),yesterdayNewspaper.getStories().get(i).getUrl());

            urlList.add(yesterdayNewspaper.getStories().get(i).getUrl());
            newsList.add(news);
        }
        if(newsList!=null){
            for(int i =0;i<newsList.size();i++){
                for(int j =0;j<i;j++){
                    if(j<newsList.size()-1){
                    if(newsList.get(j).getReader().equals(newsList.get(i).getReader())){
                        newsList.remove(j);
                    }
                    }

                }
            }
        }


        adapter2.setmNewsList(newsList);
        adapter2.notifyDataSetChanged();
        adapter2.setMonItemClickListener(new NewsAdapter2.OnItemClickListener() {
            @Override
            public void onItemClicked(View view, int position) {
                view.setBackgroundResource(R.drawable.color);
                Toast.makeText(MainActivity.this, "跳转到新闻详情页", Toast.LENGTH_SHORT).show();
                if(position>0){
                    url0 = newsList.get(position-1).getUrl();
                }
                url1 = newsList.get(position).getUrl();
                if(position<newsList.size()-1){
                    url2 = newsList.get(position+1).getUrl();
                }
                Intent intent = new Intent(MainActivity.this,NewsDetail.class);
                intent.putStringArrayListExtra("urls",urlList);
                intent.putExtra("position",position);
                startActivity(intent);
                view.setBackgroundResource(R.drawable.color1);
            }
        });

    }

    @Override
    public void noInternet() {
        Toast.makeText(this, "请确认网络连接再试哦~", Toast.LENGTH_SHORT).show();

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
    public void initAction() {
        //模拟下拉刷新，0.5秒后，下拉刷新状态视图消失
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        newsList.clear();
                        sendRequestWithOkHttp(newspaper.getDate());
                        initNews();

                        mRefreshLayout.setRefreshing(false);
                    }
                }, 2000);
            }
        });









    }




    //打开程序时发送请求
    public void sendRequestWithOkHttp(){
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









    //把日期转换格式
    public String transDate(String date){
        //这个函数用来将类似于“20191118”转化成“11 月 18 日”
        String ri = date.substring(date.length()-2,date.length());
        String yue = date.substring(date.length()-4,date.length()-2);
        String newDate = yue + " 月 "+ ri+" 日";
        return newDate;
    }

    //......省略一些




    //创建线性渐变背景色
    private void createLinearGradientBitmap(int darkColor,int color,ImageView ivbg,ArrayList<Bitmap> bitmap_one) {
        int bgColors[] = new int[2];
        bgColors[0] = darkColor;
        bgColors[1] = color;


        if(bgBitmap==null){
            bgBitmap= Bitmap.createBitmap(ivbg.getWidth(),ivbg.getHeight(), Bitmap.Config.ARGB_4444);
        }
        if(mCanvas==null){
            mCanvas=new Canvas();
        }
        if(mPaint==null){
            mPaint=new Paint();
        }
        mCanvas.setBitmap(bgBitmap);
        mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        LinearGradient gradient=new LinearGradient(0, 0, 0, bgBitmap.getHeight(),bgColors[0],bgColors[1], Shader.TileMode.CLAMP);
        mPaint.setShader(gradient);
        RectF rectF=new RectF(0,0,bgBitmap.getWidth(),bgBitmap.getHeight());
        // mCanvas.drawRoundRect(rectF,16,16,mPaint); 这个用来绘制圆角的哈
        mCanvas.drawRect(rectF,mPaint);
        bitmap_one.add(bgBitmap);

    }



}
