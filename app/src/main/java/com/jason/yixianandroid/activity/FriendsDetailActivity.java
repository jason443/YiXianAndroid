package com.jason.yixianandroid.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jason.yixianandroid.R;
import com.jason.yixianandroid.bean.UserBean;
import com.jason.yixianandroid.event.StartChatEvent;
import com.jason.yixianandroid.util.DataLoader;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by jason on 2017/4/26.
 */

public class FriendsDetailActivity extends Activity{

    private static final String TAG = FriendsDetailActivity.class.getSimpleName();
    private TextView mTvTitle;
    private ImageView mIvBack;
    private UserBean mUserBean;
    private ImageView mIvAvatar;
    private TextView mTvName;
    private TextView mTvPhone;
    private TextView mTvAge;
    private TextView mTvGender;
    private TextView mTvSetLabel;
    private TextView mTvBirthday;
    private String account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_detail);
        findViewById();
        initData();
        initTitle();
        initView();
    }

    public void onStartChat(View v) {
        EventBus.getDefault().post(new StartChatEvent(account));
    }

    public static void startActivity(String account, Context context) {
        Intent intent = new Intent(context,FriendsDetailActivity.class);
        intent.putExtra("account",account);
        context.startActivity(intent);
    }

    private void findViewById() {
        mIvAvatar = (ImageView) findViewById(R.id.friends_detail_iv_avatar);
        mTvName = (TextView) findViewById(R.id.friends_detail_tv_name);
        mTvAge = (TextView) findViewById(R.id.friends_detail_tv_age);
        mTvBirthday = (TextView) findViewById(R.id.friends_detail_tv_birthday);
        mTvSetLabel = (TextView) findViewById(R.id.friends_detail_tv_set_label);
        mTvGender = (TextView) findViewById(R.id.friends_detail_tv_gender);
        mTvPhone = (TextView) findViewById(R.id.friends_detail_tv_phone);
        mTvTitle = (TextView) findViewById(R.id.title_tv_title);
        mIvBack = (ImageView) findViewById(R.id.title_iv_left);
    }

    private void initData() {
        account = getIntent().getStringExtra("account");
        mUserBean = DataLoader.getUser(account);
    }

    private void initTitle() {
        mTvTitle.setText("详细资料");
        mIvBack.setVisibility(View.VISIBLE);
    }

    private void initView() {
        mTvName.setText(mUserBean.get昵称());
        mTvPhone.setText("电话：" + mUserBean.get手机());
        mTvBirthday.setText("生日：" + mUserBean.get生日());
        mTvGender.setText("性别：" + mUserBean.get性别());
        mTvAge.setText("年龄：" + mUserBean.get年龄());
        if (mUserBean.get头像() != null) {
            Glide.with(this).load("/sdcard/wechaterData/avatar/" + mUserBean.get头像()).into(mIvAvatar);
        }
    }

    public void onBack(View v) {
        this.finish();
    }

}
