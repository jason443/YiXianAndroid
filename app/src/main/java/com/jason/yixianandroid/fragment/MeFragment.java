package com.jason.yixianandroid.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.jason.yixianandroid.R;
import com.jason.yixianandroid.bean.UserBean;
import com.jason.yixianandroid.manager.UserManager;

/**
 * Created by jason on 2017/4/17.
 */

public class MeFragment extends Fragment{

    public static final String TAG = MeFragment.class.getSimpleName();
    private UserBean mUserBean;
    private TextView mTvName;
    private TextView mTvGender;
    private TextView mTvAge;
    private TextView mTvPhone;
    private TextView mTvBirthday;
    private ImageView mIvAvatar;
    private Button mBtBack;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me,container,false);
        mTvAge = (TextView) view.findViewById(R.id.me_tv_age);
        mTvBirthday = (TextView) view.findViewById(R.id.me_tv_birthday);
        mTvGender = (TextView) view.findViewById(R.id.me_tv_gender);
        mIvAvatar = (ImageView) view.findViewById(R.id.me_iv_avatar);
        mTvName = (TextView) view.findViewById(R.id.me_tv_name);
        mTvPhone = (TextView) view.findViewById(R.id.me_tv_phone);
        mBtBack = (Button) view.findViewById(R.id.me_bt_back);
        mBtBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLoginOut();
            }
        });
        initView();
        return view;
    }

    private void initView() {
        mUserBean = UserManager.getInstance().getUserBean();
        if (mUserBean != null) {
            mTvName.setText(mUserBean.get昵称());
            mTvPhone.setText("手机：" + mUserBean.get手机());
            mTvGender.setText("性别：" + mUserBean.get性别());
            mTvAge.setText("年龄：" + mUserBean.get年龄());
            mTvBirthday.setText("生日：" + mUserBean.get生日());
            if (mUserBean.get头像() != null) {
                Glide.with(getActivity()).load("/sdcard/wechaterData/avatar/" +
                        mUserBean.get头像()).into(mIvAvatar);
            }
        }
    }

    public void onLoginOut() {
        getActivity().finish();
    }
}
