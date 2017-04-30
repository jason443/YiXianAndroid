package com.jason.yixianandroid.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.jason.yixianandroid.R;
import com.jason.yixianandroid.adapter.ChatAdapter;
import com.jason.yixianandroid.bean.MsgBean;
import com.jason.yixianandroid.bean.UserBean;
import com.jason.yixianandroid.event.ReFreshMsgEvent;
import com.jason.yixianandroid.manager.UserManager;
import com.jason.yixianandroid.util.DataLoader;

import org.greenrobot.eventbus.EventBus;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jason on 2017/4/20.
 */

public class ChatActivity extends FragmentActivity {

    private static final String TAG = ChatActivity.class.getSimpleName();
    private ListView mLvMsgs;
    private ChatAdapter mAdapter;
    private List<MsgBean> mMsgs;
    private EditText mEtMeg;
    private TextView mTvTitle;
    private ImageView mIvBack;
    private ImageView mIvMoreInfo;
    private UserBean mUserBean;
    private String receiver;
    private int position;
    private UserManager mManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        findViewById();
        initData();
        initTitleBar();
        initView();
    }

    private void initView() {
        if (mMsgs == null) {
            mMsgs = new ArrayList<>(0);
        }
        mAdapter = new ChatAdapter(mMsgs);
        mLvMsgs.setAdapter(mAdapter);
        mEtMeg.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    String msg = mEtMeg.getText().toString();
                    if (msg.length() >0 && !msg.equals("")) {
                        Date currenTime = new Date(System.currentTimeMillis());
                        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm");
                        String dateString = format.format(currenTime);
                        Log.d(TAG, "onKey: " + dateString);
                        mMsgs.add(new MsgBean(dateString,msg,mManager.getLoginBean().getAccount()));
                        mAdapter.notifyDataSetChanged();
                        mLvMsgs.setSelection(mMsgs.size()-1);
                        mEtMeg.setText("");
                        EventBus.getDefault().post(new ReFreshMsgEvent(msg,dateString,position));
                    }
                    return true;
                }
                return false;
            }
        });
        mLvMsgs.setSelection(mMsgs.size()-1);
    }

    private void initData() {
        receiver = this.getIntent().getStringExtra("targetAccount");
        position = this.getIntent().getIntExtra("position",0);
        mManager = UserManager.getInstance();
        mUserBean = DataLoader.getUser(receiver);
        mMsgs = DataLoader.getMsgs(mManager.getLoginBean().getAccount(),receiver);
    }

    private void findViewById() {
        mLvMsgs = (ListView) findViewById(R.id.chat_lv_msg);
        mEtMeg = (EditText) findViewById(R.id.et_sendmessage);
        mTvTitle = (TextView) findViewById(R.id.title_tv_title);
        mIvBack = (ImageView) findViewById(R.id.title_iv_left);
        mIvMoreInfo = (ImageView) findViewById(R.id.title_iv_right);
    }

    private void initTitleBar() {
        mIvBack.setVisibility(View.VISIBLE);
        mIvMoreInfo.setVisibility(View.VISIBLE);
        mTvTitle.setText(mUserBean.get昵称());
    }

    public static void startActivity(Context context, String targetAccount,int position) {
        Intent intent = new Intent(context,ChatActivity.class);
        intent.putExtra("targetAccount",targetAccount);
        intent.putExtra("position",position);
        context.startActivity(intent);
    }

    public void onBack(View v) {
        this.finish();
    }

    public void onMore(View v) {
        FriendsDetailActivity.startActivity(receiver,this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        DataLoader.addChats(mManager.getLoginBean().getAccount(),receiver,mMsgs);
    }
}
