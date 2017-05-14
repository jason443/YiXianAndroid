package com.jason.yixianandroid.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jason.yixianandroid.R;
import com.jason.yixianandroid.bean.UserBean;

import java.util.List;

/**
 * Created by jason on 2017/5/11.
 */

public class LabelDetetailAdapter extends BaseAdapter {

    private List<UserBean> mSelectBeans;
    private boolean isDelete;
    private static final String TAG = LabelDetetailAdapter.class.getSimpleName();
    private DeleteButtonListener mDeleteListener;

    public LabelDetetailAdapter(List<UserBean> mSelectBeans,boolean isDelete) {
        this.mSelectBeans = mSelectBeans;
        this.isDelete = isDelete;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        LabelDetetailAdapter.LabelDetailViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new LabelDetetailAdapter.LabelDetailViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_delete_member,parent,false);
            viewHolder.mTvName = (TextView) convertView.findViewById(R.id.selected_member_tv_name);
            viewHolder.mIvAvatar = (ImageView) convertView.findViewById(R.id.selected_member_iv_avatar);
            viewHolder.mBtIsDelete = (Button) convertView.findViewById(R.id.selected_member_bt_delete);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (LabelDetetailAdapter.LabelDetailViewHolder) convertView.getTag();
        }
        UserBean userBean = getItem(position);
        viewHolder.mTvName.setText(userBean.get昵称());
        if (userBean.get头像() != null) {
            Glide.with(parent.getContext()).load("/sdcard/wechaterData/avatar/"  + userBean.get头像()).into(viewHolder.mIvAvatar);
        } else {
            Glide.with(parent.getContext()).load(R.mipmap.ic_launcher).into(viewHolder.mIvAvatar);
        }
        Log.d(TAG, "getView: " + isDelete);
        if (isDelete) {
            viewHolder.mBtIsDelete.setVisibility(View.VISIBLE);
        } else {
            viewHolder.mBtIsDelete.setVisibility(View.GONE);
        }
        if (mDeleteListener != null) {
            viewHolder.mBtIsDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDeleteListener.onDelete(position);
                }
            });
        }
        return convertView;
    }

    class LabelDetailViewHolder {
        TextView mTvName;
        ImageView mIvAvatar;
        Button mBtIsDelete;
    }

    public void setIsDelete(boolean b) {
        isDelete = b;
    }

    public void setDeleteListener(DeleteButtonListener listener) {
        this.mDeleteListener = listener;
    }

    public interface DeleteButtonListener {
        void onDelete(int position);
    }

}
