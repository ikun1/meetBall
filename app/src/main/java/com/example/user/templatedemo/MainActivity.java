package com.example.user.templatedemo;

import android.accounts.Account;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

import com.example.user.templatedemo.Affairs.ImageAffair;
import com.example.user.templatedemo.Domain.Match;
import com.example.user.templatedemo.Domain.User;
import com.example.user.templatedemo.Handlers.HttpContact;
import com.example.user.templatedemo.Handlers.ProcessHandler;
import com.example.user.templatedemo.Handlers.SocketContact;
import com.example.user.templatedemo.Interfaces.ReplyMethodS;
import com.example.user.templatedemo.Service.AccountService;
import com.example.user.templatedemo.Service.ImageService;
import com.example.user.templatedemo.Service.SocketService;
import com.example.user.templatedemo.Tools.NormalAdapter;
import com.wx.wheelview.adapter.ArrayWheelAdapter;
import com.wx.wheelview.adapter.SimpleWheelAdapter;
import com.wx.wheelview.common.WheelData;
import com.wx.wheelview.util.WheelUtils;
import com.wx.wheelview.widget.WheelView;
import com.wx.wheelview.widget.WheelViewDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    public Fragment1 fragment1;
    public Fragment2 fragment2;
    public Fragment3 fragment3;
    public Fragment4 fragment4;
    private FragmentBall fragmentBall;
    private long mExitTime;//按两次退出时间间隔记录。
    private ImageView blurImageView;
    private ImageView avatarImageView;
    public static SocketService socketService;//注意，socketService只需在主界面声明，后面必须保持单例
    public static AccountService accountService;
    public static String cookie;
    public static ImageService imageService;
    public static MainActivity mainActivity;
    public User user;

    public static MainActivity getInstance(){
        return mainActivity;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //返回键监听实现按两次退出
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            onBackStackChanged();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void onBackStackChanged() {
        System.out.println(getFragmentManager().getBackStackEntryCount());
        if (getFragmentManager().getBackStackEntryCount() == 0) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Toast.makeText(MainActivity.this, getString(R.string.exit).toString(), Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    showNav(R.id.navigation_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    showNav(R.id.navigation_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    showNav(R.id.navigation_notifications);
                    return true;
                case R.id.navigation_ball:
                    mTextMessage.setText(R.string.title_ball);
                    //item.setIcon(R.drawable.ball_selected);
                    showNav(R.id.navigation_ball);
                    return true;
                case R.id.navigation_friend:
                    mTextMessage.setText("好友");
                    showNav(R.id.navigation_friend);
                    return true;
            }
            return false;
        }

    };

    //init（）用来初始化组件
    private void init() {
        fragment1 = new Fragment1();
        fragment2 = new Fragment2();
        fragment3 = new Fragment3();
        fragment4 = new Fragment4();
        fragmentBall = new FragmentBall();

        FragmentTransaction beginTransaction = getFragmentManager().beginTransaction();
        beginTransaction.add(R.id.content, fragment1).add(R.id.content, fragment2).add(R.id.content, fragmentBall).add(R.id.content, fragment3).add(R.id.content, fragment4);//开启一个事务将fragment动态加载到组件
        beginTransaction.hide(fragment1).hide(fragment2).hide(fragment3).hide(fragment4).hide(fragmentBall);//隐藏fragment
        beginTransaction.commit();//每一个事务最后操作必须是commit（），否则看不见效果

        //修改这里可以弄好
        showNav(R.id.navigation_ball);

    }

    private void showNav(int navid) {
        FragmentTransaction beginTransaction = getFragmentManager().beginTransaction();
        switch (navid) {
            case R.id.navigation_home:
                beginTransaction.hide(fragment2).hide(fragment3).hide(fragment4).hide(fragmentBall);
                beginTransaction.show(fragment1);
                beginTransaction.commit();
                break;
            case R.id.navigation_dashboard:
                beginTransaction.hide(fragment1).hide(fragment3).hide(fragment4).hide(fragmentBall);
                beginTransaction.show(fragment2);
                beginTransaction.commit();
                break;
            case R.id.navigation_notifications:
                beginTransaction.hide(fragment2).hide(fragment1).hide(fragment4).hide(fragmentBall);
                beginTransaction.show(fragment3);
                beginTransaction.commit();
                initBack();
                break;
            case R.id.navigation_friend:
                beginTransaction.hide(fragment2).hide(fragment1).hide(fragment3).hide(fragmentBall);
                beginTransaction.show(fragment4);
                beginTransaction.commit();
                break;
            case R.id.navigation_ball:
                beginTransaction.hide(fragment2).hide(fragment1).hide(fragment3).hide(fragment4);
                beginTransaction.show(fragmentBall);
                beginTransaction.commit();
                break;
        }
    }

    //为Fragment3添加磨砂背景
    private void initBack(){
        blurImageView = (ImageView) findViewById(R.id.iv_blur);
        avatarImageView = (ImageView) findViewById(R.id.iv_avatar);

        //final NavigationView mNavigationView = (NavigationView)findViewById(R.id.navigation_notifications);

        List<Transformation<Bitmap>> shapes = new ArrayList<>();
        shapes.add(new BlurTransformation(this, 25));
        shapes.add( new CenterCrop(this));
        ImageAffair affair1 = new ImageAffair(shapes,blurImageView,"avatar_" + user.getUserName());
        imageService.loadAffairs(affair1);

        List<Transformation<Bitmap>> shapes1 = new ArrayList<>();
        shapes1.add(new CropCircleTransformation(this));

        ImageAffair affair2 = new ImageAffair(shapes1,avatarImageView,"avatar_" + user.getUserName());
        imageService.loadAffairs(affair2);



        //使用image事务管理代替了下面的源代码
        /*Glide.with(this).load(R.drawable.head)
                .bitmapTransform(new BlurTransformation(this, 25), new CenterCrop(this))
                .into(blurImageView);

        Glide.with(this).load(R.drawable.head)
                .bitmapTransform(new CropCircleTransformation(this))
                .into(avatarImageView);*/



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = this;
        imageService = new ImageService(this);
        setContentView(R.layout.activity_main);
        //initBack();

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.navigation_ball);//更改默认选中底部菜单
        navigation.setItemIconTintList(null);//删除默认颜色
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        init();
        //initWheel2();
        socketService = new SocketService(new ReplyMethodS() {
            @Override
            public void connect_failed() {
                //连接失败的操作
            }

            @Override
            public void getInfomation(User user) {
                //获取到信息的操作
                MainActivity.getInstance().user = user;
                TextView textView = (TextView)findViewById(R.id.user_name);
                textView.setText(user.getUserName());
            }

            @Override
            public void getMatchResult(int result, List<String> userNames, int matchID)//获取到匹配响应结果触发
            {
                fragment1.reactInfo(result, userNames, matchID);
            }

            @Override
            public void getMatchInfo(Match match){//从匹配ID获取到匹配信息的触发

            }
            @Override
            public void getImage(int result,String imageName,byte[] data){
                imageService.refreshAvatar(result,imageName,data);
            }
        });
        accountService = new AccountService();
        if (true){
            //加入是否首次登录的判断
            Intent intent = new Intent(MainActivity.this, loginActivity.class);
            startActivity(intent);
        }
    }


}
