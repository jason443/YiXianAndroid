package com.jason.yixianandroid.custom;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.TextView;

import com.jason.yixianandroid.R;

/**
 * Created by jason on 2017/4/25.
 */

public class LetterWindow {

    private final TextView tv;
    private WindowManager.LayoutParams mParams;
    private Context context;
    private WindowManager wm;

    public LetterWindow(Activity context) {
        this.context = context;
        wm = (WindowManager) context.getSystemService(
                Context.WINDOW_SERVICE);
        mParams = new WindowManager.LayoutParams();
        mParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_SUB_PANEL;
        mParams.format = PixelFormat.TRANSLUCENT;
        mParams.flags |= WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        DisplayMetrics dm = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);
        mParams.width = dm.widthPixels / 5;//窗口的宽和高
        mParams.height = dm.widthPixels / 5;
        mParams.x = 0;//窗口不偏移
        mParams.y = 0;
        //创建TextView
        tv = new TextView(context);
        tv.setTextColor(Color.WHITE);
        tv.setBackgroundResource(R.color.green);
        tv.setTextSize(30);
        tv.setGravity(Gravity.CENTER);
    }

    public void show(String s) {
        tv.setText(s);
        wm.addView(tv, mParams);
    }

    public void update(String s) {
        tv.setText(s);
        wm.updateViewLayout(tv, mParams);
    }

    public void hide() {
        wm.removeView(tv);
    }


}
