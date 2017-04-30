package com.jason.yixianandroid.bean;

import java.util.List;

/**
 * Created by jason on 2017/3/31.
 */

public class SaveChatBean {

    private List<MsgBean> list;

    public SaveChatBean(List<MsgBean> list) {
        this.list = list;
    }

    public List<MsgBean> getList() {
        return list;
    }

    public void setList(List<MsgBean> list) {
        this.list = list;
    }
}
