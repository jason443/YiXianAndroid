package com.jason.yixianandroid.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jason.yixianandroid.R;

/**
 * Created by jason on 2017/5/3.
 */

public class AddFriendActivity extends Activity{

    private TextView mTvTitle;
    private ImageView mIvBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        findViewById();
        initTitle();
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context,AddFriendActivity.class);
        context.startActivity(intent);
    }

    private void findViewById() {
        mTvTitle = (TextView) findViewById(R.id.title_tv_title);
        mIvBack = (ImageView) findViewById(R.id.title_iv_left);
    }

    private void initTitle() {
        mTvTitle.setText("添加朋友");
        mIvBack.setVisibility(View.VISIBLE);
    }

    public void onBack(View v) {
        this.finish();
    }
}
