package com.example.user.templatedemo;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.example.user.templatedemo.Domain.User;
import com.example.user.templatedemo.Service.SocketService;
import com.example.user.templatedemo.Tools.getPhotoFromPhotoAlbum;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

import static android.app.Activity.RESULT_OK;

public class Fragment3 extends Fragment {
    private ImageView blurImageView;
    private ImageView avatarImageView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_3, container, false);
    }


    private void goPhotoAlbum() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 2);
    }

    @Override
    public void onActivityCreated(Bundle bundle){
        avatarImageView = (ImageView) getView().findViewById(R.id.iv_avatar);
        blurImageView = (ImageView) getView().findViewById(R.id.iv_blur);
        avatarImageView.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        goPhotoAlbum();
                    }
                }
        );
        super.onActivityCreated(bundle);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        String photoPath;
        Uri uri;
        if (requestCode == 1 && resultCode == RESULT_OK) {


        } else if (requestCode == 2 && resultCode == RESULT_OK) {
            photoPath = getPhotoFromPhotoAlbum.getRealPathFromUri(getActivity(), data.getData());
            System.out.println("wodedizhi:"+photoPath);
            Glide.with(this).load(photoPath)
                    .bitmapTransform(new CropCircleTransformation(getActivity()))
                    .into(avatarImageView);
            SocketService.getInstance().uploadImage(photoPath,1);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public void refreshAvatar(int result,String imageName,byte[] data)
    //刷新图片资源
    {
        if(result != -1)
        {
            Glide.with(this).load(data)
                    .bitmapTransform(new BlurTransformation(getActivity(), 25), new CenterCrop(getActivity()))
                    .into(blurImageView);
            Glide.with(this).load(data)
                    .bitmapTransform(new CropCircleTransformation(getActivity()))
                    .into(avatarImageView);
        }

    }


}


