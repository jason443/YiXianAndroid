package com.jason.yixianandroid.bean;

import java.util.List;

/**
 * Created by jason on 2017/5/2.
 */

public class SaveLabelBean {

    private List<LabelBean> list;

    public SaveLabelBean(List<LabelBean> list) {
        this.list = list;
    }

    public List<LabelBean> getList() {
        return list;
    }

    public void setList(List<LabelBean> list) {
        this.list = list;
    }
}
