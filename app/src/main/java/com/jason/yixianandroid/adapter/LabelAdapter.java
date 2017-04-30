package com.jason.yixianandroid.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jason.yixianandroid.R;
import com.jason.yixianandroid.bean.LabelBean;

import java.util.List;

/**
 * Created by jason on 2017/4/28.
 */

public class LabelAdapter extends BaseAdapter {

   private List<LabelBean> mLabelBeans;

    public LabelAdapter(List<LabelBean> mLabelBeans) {
        this.mLabelBeans = mLabelBeans;
    }

    @Override
    public int getCount() {
        return mLabelBeans == null ? 0 : mLabelBeans.size();
    }

    @Override
    public LabelBean getItem(int position) {
        return mLabelBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LabelViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_label,null);
            viewHolder = new LabelViewHolder();
            viewHolder.mTvName = (TextView) convertView.findViewById(R.id.label_tv_name);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (LabelViewHolder) convertView.getTag();
        }
        LabelBean labelBean = getItem(position);
        viewHolder.mTvName.setText(labelBean.getName() + "(" + labelBean.getMember().size() + ")");
        return convertView;
    }

    class LabelViewHolder {
        TextView mTvName;
    }

}
