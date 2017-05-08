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
 * Created by jason on 2017/5/1.
 */

public class SelectedMemberAdapter extends BaseAdapter{


    private List<UserBean> mSelectBeans;

    public SelectedMemberAdapter(List<UserBean> mSelectBeans) {
        this.mSelectBeans = mSelectBeans;
    }

    @Override
    public int getCount() {
        return mSelectBeans == null ?0 :mSelectBeans.size();
    }

    @Override
    public UserBean getItem(int position) {
        return mSelectBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SelectViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new SelectViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_selected_member,parent,false);
            viewHolder.mTvName = (TextView) convertView.findViewById(R.id.selected_member_tv_name);
            viewHolder.mIvAvatar = (ImageView) convertView.findViewById(R.id.selected_member_iv_avatar);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (SelectViewHolder) convertView.getTag();
        }
        UserBean userBean = getItem(position);
        viewHolder.mTvName.setText(userBean.get昵称());
//        ImageView imageView = viewHolder.mIvAvatar;
//        final String tag = (String) imageView.getTag();
        if (userBean.get头像() != null) {
            Glide.with(parent.getContext()).load("/sdcard/wechaterData/avatar/"  + userBean.get头像()).into(viewHolder.mIvAvatar);
        } else {
            Glide.with(parent.getContext()).load(R.mipmap.ic_launcher).into(viewHolder.mIvAvatar);
        }
        return convertView;
    }

    class SelectViewHolder {
        TextView mTvName;
        ImageView mIvAvatar;
    }
}
