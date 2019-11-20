package com.zhihu.daily;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpUtils {
    private static final String TAG = OkHttpUtils.class.getSimpleName();
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    private static final int GET = 1;
    private static final int POSTJSON = 2;
    private OkHttpClient client = new OkHttpClient();
    private static OkHttpUtils instance;
    private OkHttpUtils(){
    }
    public static OkHttpUtils getInstance(){
        if (instance == null){
            instance = new OkHttpUtils();
        }
        return instance;
    }
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GET:
                    Log.i(TAG,"GET---->"+(String) msg.obj);
                    break;
                case POSTJSON:
                    Log.i(TAG, "POSTJSON---->"+(String) msg.obj + msg.arg1);
                    break;
            }
        }
    };
    /**
     *
     * @param url 请求地址
     * @return
     * @throws IOException
     */
    private String get(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }
    /**
     *
     * @param url 请求地址
     * @param json json字符串请求参数
     * @return
     * @throws IOException
     */
    public String postJson(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        //response.body().string()这一句代码在方法体里面只能用一次(包括打印输出的使用)
        return response.body().string();
    }
    /**
     * @param url  请求地址
     */
    public void requestDataFromeGet(final String url) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    String result = get(url);
                    Message message = Message.obtain();
                    message.what = GET;
                    message.obj = result;
                    handler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    /**
     *
     * @param url 请求地址
     * @param map 请求参数(把map转换成gson)
     */
    public void rquestDataFromePostJson(final String url, final Map<String, Object> map) {
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    //Map转JSON数据
                    Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
                    String result = postJson(url, gson.toJson(map));
                    Log.i(TAG,gson.toJson(map));
                    Message message = Message.obtain();
                    message.what = POSTJSON;
                    message.obj = result;
                    handler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

}
