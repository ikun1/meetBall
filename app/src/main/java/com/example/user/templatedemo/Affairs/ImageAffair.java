package com.example.user.templatedemo.Affairs;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.example.user.templatedemo.MainActivity;
import com.example.user.templatedemo.Service.ImageService;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class ImageAffair implements Runnable{
    //每个ImageAffair管理一个图片框的图片载入事务
    public List<Transformation<Bitmap>> shapes = new ArrayList<>();
    public ImageView imageView;
    public String imageName;

    public ImageAffair(List<Transformation<Bitmap>> shapes,ImageView imageView,String imageName){
        this.shapes = shapes;
        this.imageName = imageName;
        this.imageView = imageView;
    }

    @Override
    public void run(){
        byte[] content = ImageService.getInstance().getImageResource(imageName);
        DrawableRequestBuilder<byte[]> targetImage = Glide.with(MainActivity.getInstance()).load(content);

        //加载图片后轮番执行需求变换
        for(int i = 0;i < shapes.size();i++)
        {
            targetImage = targetImage.bitmapTransform(shapes.get(i));
        }
        targetImage.into(imageView);
        //将最后处理结果加载到图片框
    }
}
