package com.example.user.templatedemo.Handlers;

import java.io.IOException;
import java.util.logging.Handler;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ContactHandler {
    //该类用来进行所有访问网络操作,构造方法传入UI处理类
    Handler ProcessHandler;
    ContactHandler(Handler ProcessHandler){
        this.ProcessHandler = ProcessHandler;
    }

    private void sendRequestWithHttpClient() {
        new Thread().start();

    }

    class sendRequest implements Runnable{
        //发送请求专用线程

        @Override
        public void run(){
            try {
                OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder().url("url").build();
                Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    String retext = response.body().string();

                }

            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

}
