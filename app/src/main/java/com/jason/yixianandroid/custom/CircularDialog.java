package com.jason.yixianandroid.custom;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jason.yixianandroid.R;

/**
 * Created by jason on 2017/4/5.
 */

public class CircularDialog extends Dialog {

    private EditText mEtX,mEtY,mEtW,mEtColor,mEtR;
    private Button mBtYes,mBtNo;

    public CircularDialog(Context context) {
        super(context);
        setCustomDialog();
    }

    private void setCustomDialog() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_dialog_circular,null);
        mEtX = (EditText) view.findViewById(R.id.circular_et_x);
        mEtY = (EditText) view.findViewById(R.id.circular_et_y);
        mEtW = (EditText) view.findViewById(R.id.circular_et_w);
        mEtR = (EditText) view.findViewById(R.id.circular_et_r);
        mEtColor = (EditText) view.findViewById(R.id.circular_et_color);
        mBtYes = (Button) view.findViewById(R.id.circular_bt_yes);
        mBtNo = (Button) view.findViewById(R.id.circular_bt_no);
        super.setContentView(view);
    }

    public EditText getmEtX() {
        return mEtX;
    }

    public EditText getmEtY() {
        return mEtY;
    }

    public EditText getmEtW() {
        return mEtW;
    }

    public EditText getmEtColor() {
        return mEtColor;
    }

    public EditText getmEtR() {
        return mEtR;
    }

    public void setOnYesListener(View.OnClickListener listener) {
        mBtYes.setOnClickListener(listener);
    }

    public void setOnNoListsner(View.OnClickListener listsner) {
        mBtNo.setOnClickListener(listsner);
    }
}
