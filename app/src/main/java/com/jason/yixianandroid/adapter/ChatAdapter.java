package com.jason.yixianandroid.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jason.yixianandroid.R;
import com.jason.yixianandroid.bean.MsgBean;
import com.jason.yixianandroid.manager.UserManager;
import com.jason.yixianandroid.util.DataLoader;

import java.util.List;

/**
 * Created by jason on 2017/4/20.
 */

public class ChatAdapter extends BaseAdapter {

    private static int MSG_SEND = 0;
    private static int MSG_RECEIVE = 1;

    private List<MsgBean> mMsgs;

    public ChatAdapter(List<MsgBean> mMsgs) {
        this.mMsgs = mMsgs;
    }

    @Override
    public int getCount() {
        return mMsgs==null?0:mMsgs.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mMsgs.get(position).getMaster().equals(UserManager.getInstance().getLoginBean().getAccount())) {
            return MSG_SEND;
        } else {
            return MSG_RECEIVE;
        }
    }

    @Override
    public MsgBean getItem(int position) {
        return mMsgs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MsgBean msgBean = getItem(position);
        ViewHolder viewHolder;
       if (convertView == null) {
           convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_msg,parent,false);
           viewHolder = new ViewHolder();
           viewHolder.mIvReceiveAvatar = (ImageView) convertView.findViewById(R.id.chat_iv_receive_avatar);
           viewHolder.mIvSendAvatar = (ImageView) convertView.findViewById(R.id.chat_iv_send_avatar);
           viewHolder.mReceiveLayout = convertView.findViewById(R.id.chat_receive_layout);
           viewHolder.mSendLayout = convertView.findViewById(R.id.chat_send_layout);
           viewHolder.mTvReceiveContent = (TextView) convertView.findViewById(R.id.chat_tv_receive_content);
           viewHolder.mTvSendContent = (TextView) convertView.findViewById(R.id.chat_tv_send_content);
           viewHolder.mTvReceiveTime = (TextView) convertView.findViewById(R.id.chat_tv_receive_time);
           viewHolder.mTvSendTime = (TextView) convertView.findViewById(R.id.chat_tv_send_time);
           convertView.setTag(viewHolder);
       } else {
           viewHolder = (ViewHolder) convertView.getTag();
       }
        if (getItemViewType(position) == MSG_SEND) {
            viewHolder.mTvSendTime.setText(msgBean.getTime());
            viewHolder.mTvSendContent.setText(msgBean.getContent());
            viewHolder.mReceiveLayout.setVisibility(View.GONE);
            viewHolder.mTvReceiveTime.setVisibility(View.GONE);
            String avatar = DataLoader.getUser(msgBean.getMaster()).get头像();
            if (avatar != null) {
                Glide.with(parent.getContext()).load("/sdcard/wechaterData/avatar/" + avatar).into(viewHolder.mIvSendAvatar);
            }
        } else {
            viewHolder.mTvReceiveTime.setText(msgBean.getTime());
            viewHolder.mTvReceiveContent.setText(msgBean.getContent());
            viewHolder.mSendLayout.setVisibility(View.GONE);
            viewHolder.mTvSendTime.setVisibility(View.GONE);
            String avatar = DataLoader.getUser(msgBean.getMaster()).get头像();
            if (avatar != null) {
                Glide.with(parent.getContext()).load("/sdcard/wechaterData/avatar/" + avatar).into(viewHolder.mIvReceiveAvatar);
            }
        }
        return convertView;
    }

    public static  class ViewHolder {
        private View mSendLayout,mReceiveLayout;
        private ImageView mIvSendAvatar;
        private TextView mTvSendContent,mTvSendTime;
        private ImageView mIvReceiveAvatar;
        private TextView mTvReceiveContent,mTvReceiveTime;
    }

}
