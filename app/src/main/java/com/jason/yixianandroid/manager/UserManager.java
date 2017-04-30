package com.jason.yixianandroid.manager;

import com.jason.yixianandroid.bean.LoginBean;
import com.jason.yixianandroid.bean.UserBean;

/**
 * Created by jason on 2017/4/18.
 */

public class UserManager {

    private static final String TAG = UserManager.class.getSimpleName();
    private static UserManager mUserManager;
    private UserBean userBean;
    private LoginBean loginBean;

    public static UserManager getInstance() {
        if (mUserManager == null) {
            synchronized (UserManager.class) {
                if (mUserManager == null) {
                    mUserManager = new UserManager();
                }
            }
        }
        return mUserManager;
    }

    public  UserBean getUserBean() {
        return userBean;
    }

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }

    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

}
