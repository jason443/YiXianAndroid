package com.jason.yixianandroid.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jason on 2017/4/30.
 */

public class LabelBean implements Serializable{


    public LabelBean(String name, List<MemberBean> member) {
        this.name = name;
        this.member = member;
    }

    /**
     * name : 宝宝
     * member : [{"account":23549371}]
     */



    private String name;
    /**
     * account : 23549371
     */

    private List<MemberBean> member;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MemberBean> getMember() {
        return member;
    }

    public void setMember(List<MemberBean> member) {
        this.member = member;
    }

    public static class MemberBean implements Serializable {
        private String account;

        public MemberBean(String account) {
            this.account = account;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }
    }
}
