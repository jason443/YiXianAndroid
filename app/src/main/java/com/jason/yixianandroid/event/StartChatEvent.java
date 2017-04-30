package com.jason.yixianandroid.event;

/**
 * Created by jason on 2017/4/28.
 */

public class StartChatEvent {

    private String account;

    public StartChatEvent(String account) {
        this.account = account;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
