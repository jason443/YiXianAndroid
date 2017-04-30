package com.jason.yixianandroid.event;

/**
 * Created by jason on 2017/4/21.
 */

public class ReFreshMsgEvent {

    private String msg;
    private String time;
    private int position;

    public ReFreshMsgEvent(String msg, String time, int position) {
        this.msg = msg;
        this.time = time;
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
