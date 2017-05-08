package com.jason.yixianandroid.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jason.yixianandroid.R;
import com.jason.yixianandroid.adapter.AddMemberAdapter;
import com.jason.yixianandroid.bean.ContactBean;
import com.jason.yixianandroid.bean.UserBean;
import com.jason.yixianandroid.custom.IndexView;
import com.jason.yixianandroid.custom.LetterWindow;
import com.jason.yixianandroid.event.ChooseMemberFinishEvent;
import com.jason.yixianandroid.manager.UserManager;
import com.jason.yixianandroid.util.DataLoader;
import com.jason.yixianandroid.util.SortUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by jason on 2017/5/1.
 */

public class AddMemberActivity extends Activity implements IndexView.OnCharTouchEvent{

    private static final String TAG = AddMemberActivity.class.getSimpleName();
    private TextView mTvTitle;
    private TextView mTvSave;
    private String targetClazz;
    private AddMemberAdapter mAdapter;
    private IndexView mIndexView;
    private LetterWindow mLetterWindow;
    private ArrayList<UserBean> mUserBeans;
    private ArrayList<UserBean> mSelectUserBeans;
    private ListView mLvList;
    private boolean[] mSelect;
    private String[] mSelectAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_memeber);
        findViewById();
        initData();
        initTitle();
        initView();
    }

    private void initView() {
        mAdapter = new AddMemberAdapter(mUserBeans);
        mLvList.setAdapter(mAdapter);
        mIndexView.setOnLetterTouchedListener(this);
        mIndexView.bringToFront();
        mAdapter.setmListenrer(new AddMemberAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position, View v, ImageButton select) {
                if (mSelect[position]) {
                    select.setBackgroundResource(R.drawable.select_normal);
                    mSelect[position] = false;
                } else {
                    select.setBackgroundResource(R.drawable.select_click);
                    mSelect[position] = true;
                }
            }
        });
    }

    private void findViewById() {
        mTvTitle = (TextView) findViewById(R.id.title_tv_title);
        mTvSave = (TextView) findViewById(R.id.title_tv_right);
        mLvList = (ListView) findViewById(R.id.add_member_lv_list);
        mIndexView = (IndexView) findViewById(R.id.add_member_iv_index);
        mLetterWindow = new LetterWindow(this);
    }

    private void initTitle() {
        mTvTitle.setText("选择联系人");
        mTvSave.setText("确定");
    }

    private void initData() {
        targetClazz = getIntent().getStringExtra("className");
        mSelectAccount = getIntent().getStringArrayExtra("selectAccount");
        mUserBeans = DataLoader.getContacts(UserManager.getInstance().getLoginBean().getAccount());
        if (mSelectAccount != null && mSelectAccount.length >0) {
           for (int i=0; i<mSelectAccount.length; i++) {
               String s = mSelectAccount[i];
               Iterator<UserBean> it = mUserBeans.iterator();
               while (it.hasNext()) {
                   UserBean userBean = it.next();
                   if (userBean.getAccount().equals(s)) {
                       it.remove();
                   }
               }
           }
        }
        SortUtil.sortByLetter(mUserBeans);
        mSelect = new boolean[mUserBeans.size()];
        mSelectUserBeans = new ArrayList<>();
    }

    public static void startActivity(Context context,String clazzName,String[] mSelectAccount) {
        Intent intent = new Intent(context,AddMemberActivity.class);
        intent.putExtra("className",clazzName);
        intent.putExtra("selectAccount",mSelectAccount);
        context.startActivity(intent);
    }

    public static void startActivity(Context context, String clazzName) {
        Intent intent = new Intent(context,AddMemberActivity.class);
        intent.putExtra("className",clazzName);
        context.startActivity(intent);
    }

    public void onBack(View v) {
        this.finish();
    }

    public void onSave(View v) {
        for (int i= 0; i<mSelect.length; i++) {
            if (mSelect[i]) {
                mSelectUserBeans.add(mUserBeans.get(i));
            }
        }
        Log.d(TAG, "onSave: " + mSelectUserBeans.size());
        if (mSelectUserBeans.size() == 0) {
            Toast.makeText(this,"请选择联系人",Toast.LENGTH_SHORT).show();
        } else {
            EventBus.getDefault().post(new ChooseMemberFinishEvent(mSelectUserBeans,targetClazz));
            this.finish();
        }
    }

    @Override
    public void onTouch(String s) {
        mLetterWindow.show(s);
        int index = mAdapter.getSectionIndex(s);
        int position = mAdapter.getPositionForSection(index);
        if (position != -1)
            mLvList.setSelection(position);
    }

    @Override
    public void onLetterChanged(String preLetter, String letter) {
        mLetterWindow.update(letter);
        int index = mAdapter.getSectionIndex(letter);
        int position = mAdapter.getPositionForSection(index);
        if (position != -1)
            mLvList.setSelection(position);
    }

    @Override
    public void onRelease() {
        mLetterWindow.hide();
    }

}
