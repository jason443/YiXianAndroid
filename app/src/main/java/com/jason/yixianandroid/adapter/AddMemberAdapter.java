package com.jason.yixianandroid.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jason.yixianandroid.R;
import com.jason.yixianandroid.bean.UserBean;

import java.util.ArrayList;

/**
 * Created by jason on 2017/5/1.
 */

public class AddMemberAdapter extends BaseSortByLetterAdapter<UserBean>{

    public AddMemberAdapter(ArrayList<UserBean> datas) {
        super(datas);
    }

    private AddMemberAdapter.OnItemClickListener mListenrer;

    public void setmListenrer(AddMemberAdapter.OnItemClickListener mListener) {
        this.mListenrer = mListener;
    }

    @Override
    protected String getSortString(UserBean bean) {
        return bean.getSortName();
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public UserBean getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final AddMemberAdapter.AddMemberViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new AddMemberAdapter.AddMemberViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add_member,parent,false);
            viewHolder.mIvAvatar = (ImageView) convertView.findViewById(R.id.add_member_iv_avatar);
            viewHolder.mTvName = (TextView) convertView.findViewById(R.id.add_member_tv_name);
            viewHolder.mTvIndex = (TextView) convertView.findViewById(R.id.add_member_tv_index);
            viewHolder.mContainer = convertView.findViewById(R.id.add_member_rl_container);
            viewHolder.mBtSelect = (ImageButton) convertView.findViewById(R.id.add_member_bt_select);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (AddMemberAdapter.AddMemberViewHolder) convertView.getTag();
        }
        UserBean userBean = getItem(position);
        int index = getSectionForPosition(position);
        if (getPositionForSection(index) == position) {
            viewHolder.mTvIndex.setVisibility(View.VISIBLE);
            viewHolder.mTvIndex.setText(sections[index]);
        } else {
            viewHolder.mTvIndex.setVisibility(View.GONE);
        }
        viewHolder.mTvName.setText(userBean.get昵称());
        if (userBean.get头像() != null) {
            Glide.with(parent.getContext()).load("/sdcard/wechaterData/avatar/"  + userBean.get头像()).into(viewHolder.mIvAvatar);
        }
        if (mListenrer != null) {
            viewHolder.mContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListenrer.onClick(position,v,viewHolder.mBtSelect);
                }
            });
        }
        return convertView;
    }

    class AddMemberViewHolder {
        ImageView mIvAvatar;
        TextView mTvName,mTvIndex;
        View mContainer;
        ImageButton mBtSelect;
    }

    public interface OnItemClickListener {
        void onClick(int position, View v,ImageButton select);
    }

}
