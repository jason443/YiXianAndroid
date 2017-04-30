package com.jason.yixianandroid.fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.jason.yixianandroid.R;
import com.jason.yixianandroid.activity.ChatActivity;
import com.jason.yixianandroid.adapter.HomeMsgAdapter;
import com.jason.yixianandroid.bean.ContactBean;
import com.jason.yixianandroid.bean.MsgBean;
import com.jason.yixianandroid.bean.UserBean;
import com.jason.yixianandroid.event.ReFreshMsgEvent;
import com.jason.yixianandroid.event.StartChatEvent;
import com.jason.yixianandroid.manager.UserManager;
import com.jason.yixianandroid.util.DataLoader;
import com.jason.yixianandroid.util.Dp2PxUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * Created by jason on 2017/4/17.
 */

public class HomeFragment extends Fragment{
    
    
    private static final String TAG = HomeFragment.class.getSimpleName();
    private SwipeMenuListView mMsgListView;
    private SwipeMenuCreator mCreator;
    private HomeMsgAdapter mAdapter;
    private View mLayout;
    private List<UserBean> mContacts;
    private List<ContactBean> mContactList;
    private List<MsgBean> mLasterMsgsList;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mLayout = inflater.inflate(R.layout.fragment_home,container,false);
        findViewById(mLayout);
        initData();
        initView();
        return mLayout;
    }

    private void initData() {
        mContactList = DataLoader.getContactsList(UserManager.getInstance().getLoginBean().getAccount());
       // mContacts = DataLoader.getContacts(UserManager.getInstance().getLoginBean().getAccount());
        mContacts = DataLoader.getFriends(UserManager.getInstance().getLoginBean().getAccount());
        mLasterMsgsList = DataLoader.getAllLatestMsg(UserManager.getInstance().getLoginBean().getAccount(),mContactList);
        if (mLasterMsgsList != null) {
            for (int i=0; i<mLasterMsgsList.size(); i++) {
                mContacts.get(i).setLatesetMsg(mLasterMsgsList.get(i).getContent());
                mContacts.get(i).setTime(mLasterMsgsList.get(i).getTime());
            }
        }
    }

    private void initView() {
        mCreator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(Dp2PxUtil.dip2px(getContext(),90));
                // set a icon
                deleteItem.setIcon(R.drawable.delete);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };
        mMsgListView.setMenuCreator(mCreator);
        mMsgListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        mContacts.remove(mContacts.get(position));
                        mAdapter.notifyDataSetChanged();
                        break;
                    default:
                }
                return false;
            }
        });
        mMsgListView.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);
        mAdapter = new HomeMsgAdapter(mContacts);
        mMsgListView.setAdapter(mAdapter);
        mMsgListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mContacts.get(position).get类型().equals("人")) {
                    ChatActivity.startActivity(getContext(),mContactList.get(position).getAccount(),position);
                }
            }
        });
    }

    private void findViewById(View view) {
        mMsgListView = (SwipeMenuListView) view.findViewById(R.id.home_lv_msg);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void startChatEvent(StartChatEvent event) {
        int position = findPositionByAccount(event.getAccount());
        ChatActivity.startActivity(getContext(),event.getAccount(),position);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void RefreshEvent(ReFreshMsgEvent event) {
        Log.d(TAG, "RefreshEvent: ");
        mContacts.get(event.getPosition()).setLatesetMsg(event.getMsg());
        mContacts.get(event.getPosition()).setTime(event.getTime());
        mContactList.add(0,mContactList.remove(event.getPosition()));
        mContacts.add(0,mContacts.remove(event.getPosition())); //移到第一位
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    private int findPositionByAccount(String account) {
        for (int i = 0; i < mContactList.size(); i++) {
            if (mContactList.get(i).getAccount().equals(account)) {
                return i;
            }
        }
        return 0;
    }
}
