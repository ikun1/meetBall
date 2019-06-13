package com.example.user.templatedemo.Service;

import android.widget.ImageView;

import com.example.user.templatedemo.MainActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImageService {
    public static ImageService imageService;
    public ImageService getInstance(){
        return imageService;
    }

    Map<String,byte[]> imageMap;
    Map<String,ImageView> imageViewMap;
    List<Runnable> imageAffairs;
    //图片动态加载事务交给它统一管理
    MainActivity mainActivity;

    public ImageService(MainActivity mainActivity){
        imageService = this;
        this.mainActivity = mainActivity;
        imageMap = new HashMap<>();
        imageViewMap = new HashMap<>();
        imageAffairs = new ArrayList<>();
    }

    public void refreshAvatar(int result,String imageName,byte[] data)
    //刷新图片资源
    {
        if(result != -1)
        {
            imageMap.put(imageName,data);
        }
    }

    private void loadImages()
    //装载图片
    {

    }

    public void loadAffairs(String name,ImageView imageView,Runnable runnable)
    {
        imageViewMap.put(name,imageView);
        imageAffairs.add(runnable);
    }
}
