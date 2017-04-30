package com.jason.yixianandroid.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jason.yixianandroid.R;
import com.jason.yixianandroid.bean.UserBean;

import java.util.List;

/**
 * Created by jason on 2017/4/17.
 */

public class HomeMsgAdapter extends BaseAdapter {

    private List<UserBean> mUserBeans;

    public HomeMsgAdapter(List<UserBean> mUserBeans) {
        this.mUserBeans = mUserBeans;
    }

    @Override
    public int getCount() {
        return mUserBeans == null?0:mUserBeans.size();
    }

    @Override
    public UserBean getItem(int position) {
        return mUserBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        HomeViewHolder viewHolder;
        UserBean userBean = getItem(position);
        if (convertView == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_msg,parent,false);
            viewHolder = new HomeViewHolder();
            viewHolder.mIvAvatar = (ImageView) view.findViewById(R.id.home_msg_iv_avatar);
            viewHolder.mTvMsg = (TextView) view.findViewById(R.id.home_msg_tv_content);
            viewHolder.mTvTime = (TextView) view.findViewById(R.id.home_msg_time);
            viewHolder.mTvName = (TextView) view.findViewById(R.id.home_msg_tv_name);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (HomeViewHolder) view.getTag();
        }
        viewHolder.mTvTime.setText(userBean.getTime());
        viewHolder.mTvName.setText(userBean.get昵称());
        if (userBean.get头像() != null) {
            Glide.with(parent.getContext()).load("/sdcard/wechaterData/avatar/"  + userBean.get头像()).into(viewHolder.mIvAvatar);
        }
        if (userBean.getLatesetMsg() != null && userBean.getLatesetMsg().length() > 15) {
            viewHolder.mTvMsg.setText(userBean.getLatesetMsg().substring(0,14) + "...");
        } else {
            viewHolder.mTvMsg.setText(userBean.getLatesetMsg());
        }
        return view;
    }


    private class HomeViewHolder {
        ImageView mIvAvatar;
        TextView mTvTime,mTvMsg,mTvName;
    }
}
