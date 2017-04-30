package com.jason.yixianandroid.custom;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.jason.yixianandroid.R;

/**
 * Created by jason on 2017/4/7.
 */

public class LineDialog extends Dialog{

    private EditText mEtX1,mEtX2,mEtY1,mEtY2,mEtColor,mEtW;
    private Button mBtYes,mBtNo;

    public LineDialog(Context context) {
        super(context);
        setCustomDialog();
    }

    private void setCustomDialog() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_dialog_line,null);
        mEtX1 = (EditText) view.findViewById(R.id.line_et_x1);
        mEtX2 = (EditText) view.findViewById(R.id.line_et_x2);
        mEtY1 = (EditText) view.findViewById(R.id.line_et_y1);
        mEtY2 = (EditText) view.findViewById(R.id.line_et_y2);
        mEtColor = (EditText) view.findViewById(R.id.line_et_color);
        mEtW = (EditText) view.findViewById(R.id.line_et_w);
        mBtYes = (Button) view.findViewById(R.id.line_bt_yes);
        mBtNo = (Button) view.findViewById(R.id.line_bt_no);
        super.setContentView(view);
    }

    public EditText getmEtX1() {
        return mEtX1;
    }

    public EditText getmEtX2() {
        return mEtX2;
    }

    public EditText getmEtY1() {
        return mEtY1;
    }

    public EditText getmEtY2() {
        return mEtY2;
    }

    public EditText getmEtColor() {
        return mEtColor;
    }

    public EditText getmEtW() {
        return mEtW;
    }

    public void setOnYesListener(View.OnClickListener listener) {
        mBtYes.setOnClickListener(listener);
    }

    public void setOnNoListsner(View.OnClickListener listsner) {
        mBtNo.setOnClickListener(listsner);
    }
}
