package com.jason.yixianandroid.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jason.yixianandroid.R;
import com.jason.yixianandroid.bean.ActionItem;
import com.jason.yixianandroid.custom.TitlePopup;
import com.jason.yixianandroid.fragment.FriendsFragment;
import com.jason.yixianandroid.fragment.HomeFragment;
import com.jason.yixianandroid.fragment.MeFragment;
import com.jason.yixianandroid.manager.UserManager;
import com.jason.yixianandroid.util.DataLoader;

public class MainActivity extends FragmentActivity implements View.OnClickListener{

    private static final String TAG = "MainActivity";
    private TextView mTvTitle;
    private ImageView mIvRight;
    private ImageView mIvLeft;
    private FriendsFragment mFriendsFragment;
    private HomeFragment mHomeFragment;
    private MeFragment mMeFragment;
    private Fragment[] mFragments;
    private ImageView[] mImageButtons;
    private TextView[] mTextViews;
    private int index;
    private int currentTabIndex;// 当前fragment的index
    private UserManager mManager;
    private TitlePopup mTitlePopup;
    private TitlePopup.OnItemOnClickListener onitemClick = new TitlePopup.OnItemOnClickListener() {
        @Override
        public void onItemClick(ActionItem item, int position) {
            switch(position) {
                case 0: //设备通讯
                    AddFriendActivity.startActivity(MainActivity.this);
                    break;
            }
        }
    };


    public static void startActivity(Context context) {
        Intent intent = new Intent(context,MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById();
        initViews();
        initTabView();
        loadData();
        initTitlePopup();
    }

    private void initTitlePopup() {
        mTitlePopup = new TitlePopup(this, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        mTitlePopup.setItemOnClickListener(onitemClick);
        mTitlePopup.addAction(new ActionItem(this, "添加朋友",
                R.drawable.icon_menu_addfriend));
    }

    private void loadData() {
        mManager = UserManager.getInstance();
        mManager.setUserBean(DataLoader.getUser(mManager.getLoginBean().getAccount()));
    }

    private void findViewById() {
        mTvTitle = (TextView) findViewById(R.id.title_tv_title);
        mIvRight = (ImageView) findViewById(R.id.title_iv_right);
        mIvLeft = (ImageView) findViewById(R.id.title_iv_left);
    }

    private void initViews() {
        mIvRight.setImageResource(R.drawable.icon_add);
        mIvRight.setVisibility(View.VISIBLE);
        mIvRight.setOnClickListener(this);
    }


    private void initTabView() {
        mHomeFragment = new HomeFragment();
        mMeFragment = new MeFragment();
        mFriendsFragment = new FriendsFragment();
        mFragments = new Fragment[] {
          mHomeFragment,mFriendsFragment,mMeFragment
        };
        mImageButtons = new ImageView[3];
        mImageButtons[0] = (ImageView) findViewById(R.id.ib_weixin);
        mImageButtons[1] = (ImageView) findViewById(R.id.ib_contact_list);
        mImageButtons[2] = (ImageView) findViewById(R.id.ib_profile);
        mImageButtons[0].setSelected(true);
        mTextViews = new TextView[3];
        mTextViews[0] = (TextView) findViewById(R.id.tv_weixin);
        mTextViews[1] = (TextView) findViewById(R.id.tv_contact_list);
        mTextViews[2] = (TextView) findViewById(R.id.tv_profile);
        mTextViews[0].setTextColor(0xFF45C01A);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, mHomeFragment)
                .add(R.id.fragment_container, mFriendsFragment)
                .add(R.id.fragment_container, mMeFragment)
                .hide(mFriendsFragment)
                .hide(mMeFragment).show(mHomeFragment).commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_iv_right:
                if (currentTabIndex == 1) {
                    AddFriendActivity.startActivity(this);
                } else {
                    mTitlePopup.show(findViewById(R.id.layout_bar));
                }
                break;
            default:
        }
    }

    public void onTabClicked(View view) {
        mIvRight.setVisibility(View.GONE);
        switch (view.getId()) {
            case R.id.re_weixin:
                mIvRight.setVisibility(View.VISIBLE);
                index = 0;
                if (mHomeFragment != null) {
              //      mHomeFragment.refresh();
                }
                mTvTitle.setText("易显");
                mIvRight.setImageResource(R.drawable.icon_add);
                break;
            case R.id.re_contact_list:
                index = 1;
                mTvTitle.setText("好友");
                mIvRight.setVisibility(View.VISIBLE);
                mIvRight.setImageResource(R.drawable.icon_titleaddfriend);
                break;
            case R.id.re_profile:
                index = 2;
                mTvTitle.setText("我");
                break;
        }
        if (currentTabIndex != index) {
            FragmentTransaction trx = getSupportFragmentManager()
                    .beginTransaction();
            trx.hide(mFragments[currentTabIndex]);
            if (!mFragments[index].isAdded()) {
                trx.add(R.id.fragment_container, mFragments[index]);
            }
            trx.show(mFragments[index]).commit();
        }
        mImageButtons[currentTabIndex].setSelected(false);
        // 把当前tab设为选中状态
        mImageButtons[index].setSelected(true);
        mTextViews[currentTabIndex].setTextColor(0xFF999999);
        mTextViews[index].setTextColor(0xFF45C01A);
        currentTabIndex = index;
    }
}
