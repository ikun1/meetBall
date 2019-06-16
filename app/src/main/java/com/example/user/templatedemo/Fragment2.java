package com.example.user.templatedemo;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.templatedemo.Domain.TalkingTip;
import com.example.user.templatedemo.Service.ImageService;
import com.example.user.templatedemo.Tools.NormalAdapter;

import java.util.ArrayList;
import java.util.List;

public class Fragment2 extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_2, container, false);
    }

    public NormalAdapter mAdapter;

    @Override
    public void onActivityCreated(Bundle bundle){
        initf2();
        super.onActivityCreated(bundle);
    }

    public void initf2()
    //初始化fragment2信息流
    {
        Resources res = getResources();

        List<TalkingTip> data = new ArrayList<>();
        TalkingTip test = new TalkingTip();
        test.setUserName("测试名吴亦凡");
        test.setContent("这碗大，千万别虚荣心作祟。真心话，这大碗宽面也很贵。先别说话，不想给你机会先别怼。就散了吧，听完这首歌就洗洗睡。");
        test.setAvatar(ImageService.Bitmap2Bytes(BitmapFactory.decodeResource(res, R.drawable.avatar_deafault)));
        test.setAttach(ImageService.Bitmap2Bytes(BitmapFactory.decodeResource(res, R.drawable.head)));
        data.add(test);
        RecyclerView rv = (RecyclerView) getView().findViewById(R.id.rv_id);
        rv.setLayoutManager(new LinearLayoutManager(MainActivity.getInstance()));
        mAdapter = new NormalAdapter(data);
        rv.setAdapter(mAdapter);

        rv.addItemDecoration(new DividerItemDecoration(MainActivity.getInstance(),DividerItemDecoration.VERTICAL));

        TalkingTip test2 = new TalkingTip();
        test2.setUserName("测试名吴亦凡");
        test2.setContent("何必针锋相对，你看这碗又大又圆，相聚就要举起杯，你看这面又长又宽，武侠小说看流泪，从来不相信魔鬼，有时生活特别累。");
        test2.setAvatar(ImageService.Bitmap2Bytes(BitmapFactory.decodeResource(res, R.drawable.avatar_deafault)));
        test2.setAttach(ImageService.Bitmap2Bytes(BitmapFactory.decodeResource(res, R.drawable.head)));
        mAdapter.addItem(test2);
    }

}