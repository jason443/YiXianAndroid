package com.jason.yixianandroid.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.jason.yixianandroid.R;
import com.jason.yixianandroid.adapter.SelectedMemberAdapter;
import com.jason.yixianandroid.bean.LabelBean;
import com.jason.yixianandroid.bean.UserBean;
import com.jason.yixianandroid.event.ChooseMemberFinishEvent;
import com.jason.yixianandroid.event.LabelChangeEvent;
import com.jason.yixianandroid.manager.UserManager;
import com.jason.yixianandroid.util.DataLoader;
import com.jason.yixianandroid.util.Dp2PxUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jason on 2017/4/30.
 */

public class LabelDetailActivity extends Activity {

    public static final String TAG = LabelActivity.class.getSimpleName();
    private LabelBean mLabel;
    private EditText mEtName;
    private TextView mTvMember;
    private TextView mTvTitle;
    private TextView mTvSave;
    private ImageView mIvBack;
    private SwipeMenuListView mLvList;
    private SelectedMemberAdapter mAdapter;
    private List<UserBean> mUserList;
    private SwipeMenuCreator mCreator;
    private int mPosition; // 记录在数据中的位置
    private boolean isChecking; // 判断是否在进行修改

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_label_detail);
        EventBus.getDefault().register(this);
        findViewById();
        initData();
        initTitle();
        initView();
    }

    public void onAdd(View v) {
        if (isChecking) {
            ArrayList<String> mSelectList = new ArrayList<>();
            for (UserBean u : mUserList) {
                mSelectList.add(u.getAccount());
            }
            String[] mSelect =  mSelectList.toArray(new String[mSelectList.size()]);
            AddMemberActivity.startActivity(this,TAG,mSelect);
        } else {
            Toast.makeText(this,"请点击修改后进行修改操作",Toast.LENGTH_SHORT).show();
        }
    }

    public void onBack(View v) {
        this.finish();
    }

    public void onSave(View v) {
        if (mTvSave.getText().toString().equals("修改")) {
            mTvSave.setText("保存");
            isChecking = true;
            mEtName.setFocusable(true);
            mEtName.setFocusableInTouchMode(true);
        } else {
            if (mEtName.getText().toString().equals("")) {
                Toast.makeText(this,"分组名称不能为空",Toast.LENGTH_SHORT).show();
            } else {
                isChecking = false;
                mTvSave.setText("修改");
                mEtName.setFocusable(false);
                mEtName.setFocusableInTouchMode(false);
                InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(mEtName.getWindowToken(), 0);
                List<LabelBean.MemberBean> memBeans = new ArrayList<>();
                for (UserBean user : mUserList) {
                    memBeans.add(new LabelBean.MemberBean(user.getAccount()));
                }
                mLabel.setMember(memBeans);
                mLabel.setName(mEtName.getText().toString());
                DataLoader.changeLabel(mLabel, UserManager.getInstance().getLoginBean().getAccount(),mPosition);
                EventBus.getDefault().post(new LabelChangeEvent());
            }
        }
    }

    private void initTitle() {
        mTvTitle.setText(mLabel.getName());
        mTvSave.setText("修改");
    }

    private void findViewById() {
        mEtName = (EditText) findViewById(R.id.label_detail_tv_name);
        mTvMember = (TextView) findViewById(R.id.label_detail_tv_member);
        mTvTitle = (TextView) findViewById(R.id.title_tv_title);
        mIvBack = (ImageView) findViewById(R.id.title_iv_left);
        mLvList = (SwipeMenuListView) findViewById(R.id.label_detail_lv_list);
        mTvSave = (TextView) findViewById(R.id.title_tv_right);
    }

    private void initData() {
        mLabel = (LabelBean) getIntent().getSerializableExtra("label");
        mPosition = getIntent().getIntExtra("position",0);
        mUserList = DataLoader.getUsersByMembers(mLabel.getMember());
    }

    private void initView() {
        mEtName.setText(mLabel.getName());
        mTvMember.setText("分组成员" + "(" + mLabel.getMember().size() + ")");
        mAdapter = new SelectedMemberAdapter(mUserList);
        mLvList.setAdapter(mAdapter);
        mCreator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getBaseContext());
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
        mLvList.setMenuCreator(mCreator);
        mLvList.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                       if (isChecking) {
                           mUserList.remove(mUserList.get(position));
                           mAdapter.notifyDataSetChanged();
                           mLabel.getMember().remove(mLabel.getMember().get(position));
//                           DataLoader.changeLabel(mLabel,UserManager.getInstance().getLoginBean().getAccount()
//                                   ,mPosition);
//                           EventBus.getDefault().post(new LabelChangeEvent());
                       } else {
                           Toast.makeText(getBaseContext(),"请点击修改后进行修改操作",Toast.LENGTH_SHORT).show();
                       }
                        break;
                    default:
                }
                return false;
            }
        });
        mLvList.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);
        mLvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!isChecking) {
                    if (mUserList.get(position).get类型().equals("人")) {
                        FriendsDetailActivity.startActivity(mUserList.get(position).getAccount(),LabelDetailActivity.this);
                    } else {
                        EquipmentActivity.startActivity(LabelDetailActivity.this,mUserList.get(position));
                    }
                }
            }
        });
        mEtName.setFocusable(false);
        mEtName.setFocusableInTouchMode(false);
    }

    public static void startActivity(LabelBean labelBean, Context context,int position) {
        Intent intent = new Intent(context,LabelDetailActivity.class);
        intent.putExtra("label",labelBean);
        intent.putExtra("position",position);
        context.startActivity(intent);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void chooseFinishEvent(ChooseMemberFinishEvent event) {
        if (event.getDeliverClazz().equals(TAG)) {
            mUserList.addAll(event.getmSelectBeans());
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
