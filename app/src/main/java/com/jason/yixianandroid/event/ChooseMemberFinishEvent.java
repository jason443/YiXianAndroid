package com.jason.yixianandroid.event;

import com.jason.yixianandroid.bean.UserBean;

import java.util.ArrayList;

/**
 * Created by jason on 2017/5/1.
 */

public class ChooseMemberFinishEvent {

    private ArrayList<UserBean> mSelectBeans;
    private String deliverClazz;

    public ChooseMemberFinishEvent(ArrayList<UserBean> mSelectBeans, String deliverClazz) {
        this.mSelectBeans = mSelectBeans;
        this.deliverClazz = deliverClazz;
    }

    public String getDeliverClazz() {
        return deliverClazz;
    }

    public void setDeliverClazz(String diliverClazz) {
        this.deliverClazz = diliverClazz;
    }

    public ArrayList<UserBean> getmSelectBeans() {
        return mSelectBeans;
    }

    public void setmSelectBeans(ArrayList<UserBean> mSelectBeans) {
        this.mSelectBeans = mSelectBeans;
    }
}
