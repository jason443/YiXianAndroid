package com.jason.yixianandroid.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jason.yixianandroid.R;
import com.jason.yixianandroid.bean.DrawBean;
import com.jason.yixianandroid.bean.UserBean;
import com.jason.yixianandroid.custom.DrawHMIView;
import com.jason.yixianandroid.util.DataLoader;

/**
 * Created by jason on 2017/4/21.
 */

public class EquipmentActivity extends FragmentActivity {

    private static final String TAG = EquipmentActivity.class.getSimpleName();
    private UserBean mUserBean;
    private DrawBean mDrawBean;
    private DrawHMIView mDrawView;
    private TextView mTvTitle;
    private ImageView mIvRight,mIvLeft;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipment);
        findViewById();
        initData();
        initTitleBar();
        initView();
    }

    private void initView() {
        mDrawView.setmDrawBean(mDrawBean);
        mDrawView.startDraw();
    }

    public static void startActivity(Context context, UserBean userBean) {
        Intent intent = new Intent(context,EquipmentActivity.class);
        intent.putExtra("类型",userBean.get类型());
        intent.putExtra("昵称",userBean.get昵称());
        intent.putExtra("设备序列号",userBean.get设备序列号());
        intent.putExtra("HMI设备绘图",userBean.getHMI设备绘图());
        context.startActivity(intent);
    }

    private void initData() {
        Intent intent = this.getIntent();
        mUserBean = new UserBean();
        mUserBean.set类型(intent.getStringExtra("类型"));
        mUserBean.set昵称(intent.getStringExtra("昵称"));
        mUserBean.set设备序列号(intent.getStringExtra("设备序列号"));
        mUserBean.setHMI设备绘图(intent.getStringExtra("HMI设备绘图"));
        mDrawBean = DataLoader.getDrawData(mUserBean.getHMI设备绘图());
    }

    private void initTitleBar() {
        mTvTitle.setText(mUserBean.get昵称());
        mIvLeft.setVisibility(View.VISIBLE);
        mIvRight.setVisibility(View.VISIBLE);
    }

    private void findViewById() {
        mTvTitle = (TextView) findViewById(R.id.title_tv_title);
        mIvRight = (ImageView) findViewById(R.id.title_iv_right);
        mIvLeft = (ImageView) findViewById(R.id.title_iv_left);
        mDrawView = (DrawHMIView) findViewById(R.id.equipment_dv_draw);
    }

    public void onMore(View v) {
        //
    }

    public void onBack(View v) {
        this.finish();
    }

}
