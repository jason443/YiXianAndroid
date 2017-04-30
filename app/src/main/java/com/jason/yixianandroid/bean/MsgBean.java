package com.jason.yixianandroid.bean;

/**
 * Created by jason on 2017/4/20.
 */

public class MsgBean {

    /**
     * time : 2016/8/20 23:33
     * content : 你好
     * master : 23549370
     */

    private String time;
    private String content;
    private String master;

    public MsgBean(String content, String master) {
        this.content = content;
        this.master = master;
    }

    public MsgBean(String time, String content, String master) {
        this.time = time;
        this.content = content;
        this.master = master;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMaster() {
        return master;
    }

    public void setMaster(String master) {
        this.master = master;
    }

}
