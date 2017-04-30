package com.jason.yixianandroid.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.jason.yixianandroid.R;
import com.jason.yixianandroid.adapter.LabelAdapter;
import com.jason.yixianandroid.bean.LabelBean;
import com.jason.yixianandroid.manager.UserManager;
import com.jason.yixianandroid.util.DataLoader;

import java.util.List;

/**
 * Created by jason on 2017/4/27.
 */

public class LabelActivity extends Activity implements View.OnClickListener{

    private static final String TAG = LabelActivity.class.getSimpleName();
    private View mLLNewLabel;
    private SwipeMenuListView mLvLabelList;
    private LabelAdapter mAdapter;
    private ImageView mIvBack;
    private TextView mTvTitle;
    private List<LabelBean> mLabels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_label);
        findViewById();
        initData();
        initTitle();
        initView();
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context,LabelActivity.class);
        context.startActivity(intent);
    }

    private void findViewById() {
        mLLNewLabel = findViewById(R.id.label_ll_new_label);
        mLvLabelList = (SwipeMenuListView) findViewById(R.id.label_lv_list);
        mIvBack = (ImageView) findViewById(R.id.title_iv_left);
        mTvTitle = (TextView) findViewById(R.id.title_tv_title);
    }

    private void initView() {
        mLLNewLabel.setOnClickListener(this);
        mAdapter = new LabelAdapter(mLabels);
        mLvLabelList.setAdapter(mAdapter);
        mLvLabelList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LabelDetailActivity.startActivity(mLabels.get(position),view.getContext());
            }
        });
    }

    public void onBack(View v) {
        this.finish();
    }

    private void initTitle() {
        mTvTitle.setText("我的分组");
        mIvBack.setVisibility(View.VISIBLE);
    }

    private void initData() {
        mLabels = DataLoader.getLabelList(UserManager.getInstance().getLoginBean().getAccount());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.label_ll_new_label:
                Toast.makeText(this,"11111",Toast.LENGTH_SHORT).show();
                break;
            default:
        }
    }
}
