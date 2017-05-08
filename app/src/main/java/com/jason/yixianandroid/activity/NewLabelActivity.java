package com.jason.yixianandroid.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.jason.yixianandroid.R;
import com.jason.yixianandroid.adapter.SelectedMemberAdapter;
import com.jason.yixianandroid.bean.LabelBean;
import com.jason.yixianandroid.bean.UserBean;
import com.jason.yixianandroid.event.ChooseMemberFinishEvent;
import com.jason.yixianandroid.event.LabelChangeEvent;
import com.jason.yixianandroid.manager.UserManager;
import com.jason.yixianandroid.util.DataLoader;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jason on 2017/4/30.
 */

public class NewLabelActivity extends Activity {


    private static final String TAG = NewLabelActivity.class.getSimpleName();
    private TextView mTvTitle;
    private TextView mTvSave;
    private ImageView mIvBack;
    private EditText mEtName;
    private ArrayList<UserBean> mSelectBeans;
    private ListView mLvSelectMember;
    private SelectedMemberAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_label);
        EventBus.getDefault().register(this);
        findViewById();
        initData();
        initTitle();
        initView();
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context,NewLabelActivity.class);
        context.startActivity(intent);
    }

    private void initView() {
        mAdapter = new SelectedMemberAdapter(mSelectBeans);
        mLvSelectMember.setAdapter(mAdapter);
    }

    private void initData() {
        mSelectBeans = new ArrayList<>();
    }

    private void findViewById() {
        mTvTitle = (TextView) findViewById(R.id.title_tv_title);
        mTvSave = (TextView) findViewById(R.id.title_tv_right);
        mIvBack = (ImageView) findViewById(R.id.title_iv_left);
        mEtName = (EditText) findViewById(R.id.new_label_et_name);
        mLvSelectMember = (ListView) findViewById(R.id.new_label_lv_select_member);
    }

    private void initTitle() {
        mTvTitle.setText("新建分组");
        mTvSave.setText("完成");
    }

    public  void onBack(View v) {
        this.finish();
    }

    public void onSave(View v) {
        String labelName = mEtName.getText().toString();
        if (!labelName.equals("")) {
            if (mSelectBeans.size()>0) {
                List<LabelBean.MemberBean> memBeans = new ArrayList<>();
                for (UserBean user : mSelectBeans) {
                    memBeans.add(new LabelBean.MemberBean(user.getAccount()));
                }
                LabelBean labelBean = new LabelBean(labelName,memBeans);
                DataLoader.addLabel(labelBean, UserManager.getInstance().getLoginBean().getAccount());
                EventBus.getDefault().post(new LabelChangeEvent());
                this.finish();
            } else {
                Toast.makeText(this,"联系人的个数需大于0",Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this,"请输入分组名称",Toast.LENGTH_SHORT).show();
        }
    }

    public void onAdd(View v) {
          AddMemberActivity.startActivity(this,TAG);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void chooseFinishEvent(ChooseMemberFinishEvent event) {
        if (event.getDeliverClazz().equals(TAG)) {
            mSelectBeans.clear();
            mSelectBeans.addAll(event.getmSelectBeans());
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
