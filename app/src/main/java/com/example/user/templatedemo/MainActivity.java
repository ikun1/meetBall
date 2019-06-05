package com.example.user.templatedemo;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

import com.example.user.templatedemo.Domain.User;
import com.example.user.templatedemo.Handlers.HttpContact;
import com.example.user.templatedemo.Handlers.ProcessHandler;
import com.example.user.templatedemo.Handlers.SocketContact;
import com.example.user.templatedemo.Interfaces.ReplyMethodS;
import com.example.user.templatedemo.Service.SocketService;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private Fragment1 fragment1;
    private Fragment2 fragment2;
    private Fragment3 fragment3;
    private long mExitTime;//按两次退出时间间隔记录。
    private ImageView blurImageView;
    private ImageView avatarImageView;
    public static SocketService socketService;//注意，socketService只需在主界面声明，后面必须保持单例



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
            }
            return false;
        }

    };

    //init（）用来初始化组件
    private void init() {
        fragment1 = new Fragment1();
        fragment2 = new Fragment2();
        fragment3 = new Fragment3();
        FragmentTransaction beginTransaction = getFragmentManager().beginTransaction();
        beginTransaction.add(R.id.content, fragment1).add(R.id.content, fragment2).add(R.id.content, fragment3);//开启一个事务将fragment动态加载到组件
        beginTransaction.hide(fragment1).hide(fragment2).hide(fragment3);//隐藏fragment
        beginTransaction.commit();//每一个事务最后操作必须是commit（），否则看不见效果
        showNav(R.id.navigation_home);
    }

    private void showNav(int navid) {
        FragmentTransaction beginTransaction = getFragmentManager().beginTransaction();
        switch (navid) {
            case R.id.navigation_home:
                beginTransaction.hide(fragment2).hide(fragment3);
                beginTransaction.show(fragment1);
                beginTransaction.commit();
                break;
            case R.id.navigation_dashboard:
                beginTransaction.hide(fragment1).hide(fragment3);
                beginTransaction.show(fragment2);
                beginTransaction.commit();
                break;
            case R.id.navigation_notifications:
                beginTransaction.hide(fragment2).hide(fragment1);
                beginTransaction.show(fragment3);
                beginTransaction.commit();
                break;
        }
    }
    //为Fragment3添加磨砂背景
    private void initBack(){
        blurImageView = (ImageView) findViewById(R.id.iv_blur);
        avatarImageView = (ImageView) findViewById(R.id.iv_avatar);

        Glide.with(this).load(R.drawable.head)
                .bitmapTransform(new BlurTransformation(this, 25), new CenterCrop(this))
                .into(blurImageView);

        Glide.with(this).load(R.drawable.head)
                .bitmapTransform(new CropCircleTransformation(this))
                .into(avatarImageView);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        setContentView(R.layout.activity_main);
        init();
        //initBack();

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        socketService = new SocketService(new ReplyMethodS() {
            @Override
            public void connect_failed() {
                //连接失败的操作
            }

            @Override
            public void getInfomation(User user) {
                //获取到信息的操作
                fragment1.reactInfo(user);
            }
        });

    }


}
