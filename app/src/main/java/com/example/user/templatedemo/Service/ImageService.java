package com.example.user.templatedemo.Service;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

import com.example.user.templatedemo.Affairs.ImageAffair;
import com.example.user.templatedemo.MainActivity;
import com.example.user.templatedemo.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ImageService {
    public static ImageService imageService;
    public static ImageService getInstance(){
        return imageService;
    }

    Map<String,byte[]> imageMap = new HashMap<>();
    //图片资源文件表所有的图片资源都在这里
    List<ImageAffair> affairList = new ArrayList<>();
    //图片动态加载事务交给它统一管理
    MainActivity mainActivity;
    Handler imageHandler = new Handler(Looper.getMainLooper());
    ArrayList<String> waitingLine = new ArrayList<>();
    //等待下载的图片队列，防止同一图片不同显示途径时发生重复下载

    public ImageService(MainActivity mainActivity){
        imageService = this;
        this.mainActivity = mainActivity;

    }


    public void refreshAvatar(int result,String imageName,byte[] data)
    //刷新图片资源
    {
        if(result != -1)
        {
            imageMap.put(imageName,data);
            //更新资源后，遍历图片资源表，将该资源对应的view刷新显示
            for(int i = 0;i < affairList.size();i++)
            {
                if(affairList.get(i).imageName.equals(imageName))
                    affairList.get(i).imageView.post(affairList.get(i));
            }
        }
    }

    public void loadAffairs(ImageAffair imageAffair)
    {
        affairList.add(imageAffair);
        imageHandler.post(imageAffair);
    }

    public byte[] getImageResource(String imageName)
            //获取资源图片，若未加载则立即发出加载请求
    {
        byte[] content = imageMap.get(imageName);
        if(content == null)
        {
            //资源尚未加载，使用默认图片代替，并且发出图片加载申请
            if(imageName.matches("avatar_.*"))
            {
                Resources res = mainActivity.getResources();
                Bitmap bmp = BitmapFactory.decodeResource(res, R.drawable.avatar_deafault);
                content = Bitmap2Bytes(bmp);
            }
            if(!waitingLine.contains(imageName)){
                SocketService.getInstance().downloadImage(2,imageName);
                waitingLine.add(imageName);
            }
        }
        else{
            content = imageMap.get(imageName);
        }
        return content;
    }


    public byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
       bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
       return baos.toByteArray();
       }


}

