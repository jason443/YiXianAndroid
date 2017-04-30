package com.jason.yixianandroid.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jason.yixianandroid.R;
import com.jason.yixianandroid.bean.LabelBean;

/**
 * Created by jason on 2017/4/30.
 */

public class LabelDetailActivity extends Activity {

    public static final String TAG = LabelActivity.class.getSimpleName();
    private LabelBean mLabel;
    private TextView mTvName;
    private TextView mTvMember;
    private TextView mTvTitle;
    private ImageView mIvBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_label_detail);
        findViewById();
        initData();
        initTitle();
        initView();
    }

    public void onBack(View v) {
        this.finish();
    }

    private void initTitle() {
        mTvTitle.setText(mLabel.getName());
        mIvBack.setVisibility(View.VISIBLE);
    }

    private void findViewById() {
        mTvName = (TextView) findViewById(R.id.label_detail_tv_name);
        mTvMember = (TextView) findViewById(R.id.label_detail_tv_member);
        mTvTitle = (TextView) findViewById(R.id.title_tv_title);
        mIvBack = (ImageView) findViewById(R.id.title_iv_left);
    }

    private void initData() {
        mLabel = (LabelBean) getIntent().getSerializableExtra("label");
        Log.d(TAG, "initData: " + mLabel.getMember().size() + "   " + mLabel.getName());
    }

    private void initView() {
        mTvName.setText(mLabel.getName());
        mTvMember.setText("分组成员" + "(" + mLabel.getMember().size() + ")");
    }

    public static void startActivity(LabelBean labelBean, Context context) {
        Intent intent = new Intent(context,LabelDetailActivity.class);
        intent.putExtra("label",labelBean);
        context.startActivity(intent);
    }
}
