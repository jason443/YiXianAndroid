package com.jason.yixianandroid.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.jason.yixianandroid.R;
import com.jason.yixianandroid.activity.EquipmentActivity;
import com.jason.yixianandroid.activity.FriendsDetailActivity;
import com.jason.yixianandroid.activity.LabelActivity;
import com.jason.yixianandroid.adapter.FriendsAdapter;
import com.jason.yixianandroid.bean.ContactBean;
import com.jason.yixianandroid.bean.UserBean;
import com.jason.yixianandroid.custom.IndexView;
import com.jason.yixianandroid.custom.LetterWindow;
import com.jason.yixianandroid.manager.UserManager;
import com.jason.yixianandroid.util.DataLoader;
import com.jason.yixianandroid.util.SortUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jason on 2017/4/25.
 */

public class FriendsFragment extends Fragment implements IndexView.OnCharTouchEvent,View.OnClickListener{


    private static final String TAG = FriendsFragment.class.getSimpleName();
    private IndexView mIndexView;
    private LetterWindow mLetterWindow;
    private View mLayout;
    private ListView mLvContact;
    private ArrayList<UserBean> mUserBeans;
    private List<ContactBean> mContactList;
    private FriendsAdapter mAdapter;
    private View mLabel;
    private View mGroupChat;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mLayout = inflater.inflate(R.layout.fragment_friends,container,false);
        mLvContact = (ListView) mLayout.findViewById(R.id.friends_lv_contacts);
        mIndexView = (IndexView) mLayout.findViewById(R.id.friends_iv_index);
        mLabel = mLayout.findViewById(R.id.friends_ll_label);
        mGroupChat = mLayout.findViewById(R.id.friends_ll_group_chat);
        mIndexView.setOnLetterTouchedListener(this);
        mLetterWindow = new LetterWindow(getActivity());
        initView();
        return mLayout;
    }

    private void initView() {
        mUserBeans = DataLoader.getContacts(UserManager.getInstance().getLoginBean().getAccount());
        mContactList = DataLoader.getContactsList(UserManager.getInstance().getLoginBean().getAccount());
        SortUtil.sortByLetter(mUserBeans);
        mIndexView.bringToFront();
        mAdapter = new FriendsAdapter((ArrayList<UserBean>) mUserBeans);
        mLvContact.setAdapter(mAdapter);
        mLabel.setOnClickListener(this);
        mGroupChat.setOnClickListener(this);
        mAdapter.setmListenrer(new FriendsAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position, View v) {
                UserBean userBean = mUserBeans.get(position);
                if (userBean.get类型().equals("人")) {
                    FriendsDetailActivity.startActivity(mContactList.get(position).getAccount(),getContext());
                } else {
                    Log.d(TAG, "onClick: " + userBean.getHMI设备绘图());
                    EquipmentActivity.startActivity(getContext(),userBean);
                }
            }
        });
    }

    @Override
    public void onTouch(String s) {
        mLetterWindow.show(s);
        int index = mAdapter.getSectionIndex(s);
        int position = mAdapter.getPositionForSection(index);
        if (position != -1)
            mLvContact.setSelection(position);
    }

    @Override
    public void onLetterChanged(String preLetter, String letter) {
        mLetterWindow.update(letter);
        int index = mAdapter.getSectionIndex(letter);
        int position = mAdapter.getPositionForSection(index);
        if (position != -1)
            mLvContact.setSelection(position);
    }

    @Override
    public void onRelease() {
        mLetterWindow.hide();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.friends_ll_label:
                LabelActivity.startActivity(getContext());
                break;
            case R.id.friends_ll_group_chat:
                break;
            default:
        }
    }
}
