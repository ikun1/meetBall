package com.example.user.templatedemo.Tools;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.user.templatedemo.Domain.TalkingTip;
import com.example.user.templatedemo.MainActivity;
import com.example.user.templatedemo.R;
import com.example.user.templatedemo.Service.ImageService;

import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

// ① 创建Adapter
public class NormalAdapter extends RecyclerView.Adapter<NormalAdapter.VH>{
    //② 创建ViewHolder
    public static class VH extends RecyclerView.ViewHolder{
        public final TextView userName;
        public final TextView content;
        public final ImageView avatar;
        public final ImageView attach;

        public VH(View v) {
            super(v);
            userName = (TextView) v.findViewById(R.id.com_userName);
            content = v.findViewById(R.id.com_content);
            avatar = v.findViewById(R.id.com_avatar);
            attach = v.findViewById(R.id.com_attach);
        }
    }

    private List<TalkingTip> mDatas;
    public NormalAdapter(List<TalkingTip> data) {
        this.mDatas = data;
    }

    //③ 在Adapter中实现3个方法
    @Override
    public void onBindViewHolder(VH holder, int position) {
        TalkingTip target = mDatas.get(position);
        holder.userName.setText(target.getUserName());
        holder.content.setText(target.getContent());
        Glide.with(MainActivity.getInstance()).load(target.getAvatar())
                .bitmapTransform(new RoundedCornersTransformation(MainActivity.getInstance(),10,10))
                .into(holder.avatar);
        holder.attach.setImageBitmap(ImageService.Bytes2Bitmap(target.getAttach()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //item 点击事件
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        //LayoutInflater.from指定写法
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.community, parent, false);
        return new VH(v);
    }

    public void addItem(TalkingTip tip){
        mDatas.add(tip);
        notifyItemInserted(4);
        notifyItemRangeChanged(4,mDatas.size()-4);
    }

}