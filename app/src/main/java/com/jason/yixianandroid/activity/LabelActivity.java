package com.jason.yixianandroid.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.jason.yixianandroid.R;
import com.jason.yixianandroid.adapter.LabelAdapter;
import com.jason.yixianandroid.bean.LabelBean;
import com.jason.yixianandroid.event.LabelChangeEvent;
import com.jason.yixianandroid.manager.UserManager;
import com.jason.yixianandroid.util.DataLoader;
import com.jason.yixianandroid.util.Dp2PxUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
    private SwipeMenuCreator mCreator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_label);
        EventBus.getDefault().register(this);
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
        mCreator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem deleteItem = new SwipeMenuItem(getBaseContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(Dp2PxUtil.dip2px(getBaseContext(),90));
                // set a icon
                deleteItem.setIcon(R.drawable.delete);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };
        mLLNewLabel.setOnClickListener(this);
        mAdapter = new LabelAdapter(mLabels);
        mLvLabelList.setAdapter(mAdapter);
        mLvLabelList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LabelDetailActivity.startActivity(mLabels.get(position),view.getContext(),position);
            }
        });
        mLvLabelList.setMenuCreator(mCreator);
        mLvLabelList.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);
        mLvLabelList.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        mLabels.remove(mLabels.get(position));
                        mAdapter.notifyDataSetChanged();
                        DataLoader.changeLabel(mLabels,UserManager.getInstance().getLoginBean().getAccount());
                        break;
                    default:
                }
                return false;
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLabelChangeEvent(LabelChangeEvent event) {
        mLabels.clear();
        mLabels.addAll(DataLoader.getLabelList(UserManager.getInstance().getLoginBean().getAccount()));
        Log.d(TAG, "onLabelChangeEvent: " + mLabels.size());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.label_ll_new_label:
                NewLabelActivity.startActivity(this);
                break;
            default:
        }
    }
}
