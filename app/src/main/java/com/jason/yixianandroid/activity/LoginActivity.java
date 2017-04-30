package com.jason.yixianandroid.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.jason.yixianandroid.R;
import com.jason.yixianandroid.bean.LoginBean;
import com.jason.yixianandroid.manager.UserManager;
import com.jason.yixianandroid.net.LoginApi;

/**
 * Created by jason on 2017/4/18.
 */

public class LoginActivity extends FragmentActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();
    private EditText mEtAccount;
    private EditText mEtPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mEtAccount = (EditText) findViewById(R.id.login_et_account);
        mEtPassword = (EditText) findViewById(R.id.login_et_password);
    }

    public void onLogin(View v) {
        String account = mEtAccount.getText().toString();
        String password = mEtPassword.getText().toString();
        Log.d(TAG, "onLogin: " + account + "/" + password);
        if (LoginApi.doLogin(account,password)) {
            LoginBean loginBean = new LoginBean(account,password);
            UserManager.getInstance().setLoginBean(loginBean);
            mEtPassword.setText("");
            mEtAccount.setText("");
            MainActivity.startActivity(this);
        } else {
            Toast.makeText(this,"登录失败",Toast.LENGTH_SHORT).show();
        }
    }

}
