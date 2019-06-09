package com.example.user.templatedemo.Handlers;

import android.os.Message;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketContact {
    //这个是socket的控制类，外部调用的方法跟http的那个一样一样的，当成http那种一样用就行了
    private Socket socket;
    private String message = "";
    private BufferedReader in = null;
    private static final String HOST = "120.79.35.179";//服务器地址
    private static final int PORT = 2836;//连接端口号
    public static  final int CONNECT_FAILED = 0;
    public static final  int MATCH = 1;
    public static final  int GETINFO = 2;
    public static final int MATCHINFO = 3;

    private SocketHandler socketHandler;

    public SocketContact (SocketHandler socketHandler){
        //传入重写过UI更新方法后的socketHandler
        this.socketHandler = socketHandler;
    }



    public void sendMessage(final String msg) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(msg);// 先在控制台输出
                PrintWriter out = null;
                try {
                    out = new PrintWriter(new BufferedWriter(
                            new OutputStreamWriter(socket.getOutputStream())),
                            true);// 创建输出流对象
                    out.println(msg);// 转发
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void Connect(){
        /**
         * 与http不同的是，这个类第一次建立连接必须先调用这个connect方法，且只在主界面调用一次，
         * 连接成功不作回调，连接失败会调用绑定socketHandler的connect_failed方法
         */
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    socket = new Socket(HOST, PORT);//连接服务器
                    in = new BufferedReader(new InputStreamReader(socket
                            .getInputStream()));//初始化接收消息的流对象
                    WaitMessage waitMessage = new WaitMessage();
                    new Thread(waitMessage).start();

                }
                catch (Exception p){
                    p.printStackTrace();
                    Message message = new Message();
                    message.what = CONNECT_FAILED;
                    socketHandler.sendMessage(message);
                }
            }
        }).start();

    }

    private void controlToHandler(String retext,String Type){
        try {
            JSONObject jsonObject = new JSONObject(retext);

            //通过消息传输机制将结果发送给Handler
            Message message = new Message();
            switch (Type){
                case "<matchRe>":message.what=MATCH;break;
                case"<userInfo>":message.what=GETINFO;break;
            }
            message.obj = jsonObject;
            socketHandler.sendMessage(message);

        }catch (Exception e){
            e.printStackTrace();
        }

    }


    class WaitMessage implements Runnable {
        String typeTarget;
        @Override
        public void run() {
            try {
                while (true) {
                    if ((message = in.readLine()) != null) {
                        // 正则表达式匹配将tag前后内容包围发送
                        if (message.matches("<.*>")) {
                            typeTarget = message;
                            String s = in.readLine();
                            String json = "";
                            while (!s.matches("</.*>")) {
                                json = json + s + System.getProperty("line.separator");
                                s = in.readLine();
                            }
                            controlToHandler(json,typeTarget);

                        }
                        else{
                            System.out.println(message);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
