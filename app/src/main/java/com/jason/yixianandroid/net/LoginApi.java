package com.jason.yixianandroid.net;

import com.jason.yixianandroid.bean.LoginBean;
import com.jason.yixianandroid.util.DataLoader;

import java.util.List;

/**
 * Created by jason on 2017/4/18.
 */

public class LoginApi {

    public static boolean doLogin(String account, String password) {
        List<LoginBean> loginBeen = DataLoader.getLoginBeans();
        for (LoginBean lb : loginBeen) {
            if (lb.getAccount().equals(account) && lb.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

}
