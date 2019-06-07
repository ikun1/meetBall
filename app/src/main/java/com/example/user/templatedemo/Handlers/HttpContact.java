package com.example.user.templatedemo.Handlers;

import android.os.Message;

import java.io.IOException;
import java.util.ArrayList;


import com.example.user.templatedemo.Domain.User;
import com.example.user.templatedemo.R;


import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpContact {
    /**
     * 该类用来进行所有访问网络操作,构造方法传入重写过的ProcessHandler
     */

    ProcessHandler processHandler;
    String url_head = "http://120.79.35.179:8531/";

    public static final int LOGIN_RESULT = 0;
    public static final int REGISTER = 1;

    public HttpContact(ProcessHandler processHandler){
        this.processHandler = processHandler;
    }

    public void logIn(User user){
        /**把用户名和密码放在user里传进来即可
         *
         */
        ArrayList<String> args = new ArrayList<>();
        args.add("userName=" + user.getUserName());
        args.add("password=" + user.getPassword());
        sendRequest s1 = new sendRequest(args,LOGIN_RESULT);
        new Thread(s1).start();
    }
    public void register(User user){
        /**把注册信息填进user，详见数据交换格式
         *
         */
        ArrayList<String> args = new ArrayList<>();
        args.add("userName=" + user.getUserName());
        args.add("password=" + user.getPassword());
        args.add("role=" + user.getRole());
        args.add("height=" + user.getHeight());
        args.add("age=" + user.getAge());

        sendRequest s1 = new sendRequest(args,REGISTER);
        new Thread(s1).start();
    }

    private class sendRequest implements Runnable{
        /**发送请求专用线程
         *
         */
        private String url; //发送到该url
        private ArrayList<String> args;//get请求所有参数
        private int Type;//请求类型，传入上面的常量如LOGIN_RESULT

        public sendRequest(ArrayList<String> args,int Type){
            this.Type = Type;
            url = url_head;
            String index = null;
            switch (Type){
                case LOGIN_RESULT:index = "login";break;
                case REGISTER:index = "register";break;
            }
            url = url + index + "?";
            for(int i = 0;i<args.size();i++){
                url = url + args.get(i) + "&";
            }
            System.out.println(url);
    }

        @Override
        public void run(){
            try {
                OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder().url(url).build();

                Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    String retext = response.body().string();
                    JSONObject jsonObject = new JSONObject(retext);

                    //通过消息传输机制将结果发送给Handler
                    Message message = new Message();
                    message.what = Type;
                    message.obj = jsonObject;
                    processHandler.sendMessage(message);

                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
