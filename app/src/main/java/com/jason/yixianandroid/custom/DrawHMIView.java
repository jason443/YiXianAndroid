package com.jason.yixianandroid.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.jason.yixianandroid.bean.DrawBean;

import java.text.DecimalFormat;

/**
 * Created by jason on 2017/4/21.
 */

public class DrawHMIView extends View {

    private Paint mPaint;
    private DrawBean mDrawBean;
    private DrawBean.绘制圆形Bean mCircularBean;
    private DrawBean.绘制直线Bean mLineBean;
    private DrawBean.绘制图片Bean mPritureBean;
    private DrawBean.设置背景颜色Bean mBackgroundBean;
    private static final String TAG = DrawHMIView.class.getSimpleName();

    public DrawHMIView(Context context) {
        super(context);
    }

    public void setmDrawBean(DrawBean mDrawBean) {
        this.mDrawBean = mDrawBean;
        mCircularBean = mDrawBean.get绘制圆形();
        mLineBean = mDrawBean.get绘制直线();
        mPritureBean = mDrawBean.get绘制图片();
        mBackgroundBean = mDrawBean.get设置背景颜色();
    }

    public void startDraw() {
        invalidate();
    }

    public DrawHMIView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mCircularBean != null) {
            mPaint.setStrokeWidth(mCircularBean.getW());
            mPaint.setColor(Color.parseColor(mCircularBean.get颜色()));
            canvas.drawCircle(mCircularBean.getX(),mCircularBean.getY(),mCircularBean.getR(),mPaint);
        }
        if (mLineBean != null) {
            mPaint.setStrokeWidth(mLineBean.getW());
            mPaint.setColor(Color.parseColor(mLineBean.get颜色()));
            canvas.drawLine(mLineBean.getX1(),mLineBean.getY1(),mLineBean.getX2(),mLineBean.getY2(),mPaint);
        }
        if (mBackgroundBean != null) {
            setBackgroundColor(Color.parseColor(mBackgroundBean.get颜色()));
        }
        if (mPritureBean != null) {
            String url = "/sdcard/wechaterData/HMI绘图/图片/";
            Bitmap bitmap = BitmapFactory.decodeFile(url+mPritureBean.getName());
            canvas.drawBitmap(bitmap,mPritureBean.getX(),mPritureBean.getY(),mPaint);
        }
        Log.d(TAG, "onDraw: ");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        int x0 = mCircularBean.getX();
        int y0 = mCircularBean.getY();
        int r = mCircularBean.getR();
        if (!(Math.pow(x-x0,2) + Math.pow(y-y0,2) > Math.pow(r,2)) && event.getAction() == MotionEvent.ACTION_DOWN) {
            //    Toast.makeText(getContext(),"你点击圆了",Toast.LENGTH_SHORT).show();
            showCircularDialog();
            return true;
        }
        float x1 = mLineBean.getX1();
        float y1 = mLineBean.getY1();
        float x2 = mLineBean.getX2();
        float y2 = mLineBean.getY2();
        DecimalFormat df = new DecimalFormat("0.0");
        String index1 = df.format((y-y1)/(y2-y1));
        String index2 = df.format((x-x1)/(x2-x1));
        Log.d(TAG, "onTouchEvent: " + (y-y1)/(y2-y1) + "     " + (x-x1)/(x2-x1));
        Log.d(TAG, "onTouchEvent: " + index1 + "     " + index2);
        if (index1.equals(index2) && event.getAction() == MotionEvent.ACTION_DOWN){
            //    Toast.makeText(getContext(),"你点击了直线",Toast.LENGTH_SHORT).show();
            showLineDialog();
            return true;
        }
        return false;
    }

    private void showLineDialog() {
        final LineDialog dialog = new LineDialog(getContext());
        dialog.getmEtX1().setText(mLineBean.getX1() + "");
        dialog.getmEtX2().setText(mLineBean.getX2() + "");
        dialog.getmEtY1().setText(mLineBean.getY1() + "");
        dialog.getmEtY2().setText(mLineBean.getY2() + "");
        dialog.getmEtW().setText(mLineBean.getW() + "");
        dialog.getmEtColor().setText(mLineBean.get颜色());
        dialog.setOnNoListsner(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setOnYesListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int x1 = Integer.parseInt(dialog.getmEtX1().getText().toString());
                    int x2 = Integer.parseInt(dialog.getmEtX2().getText().toString());
                    int y1 = Integer.parseInt(dialog.getmEtY1().getText().toString());
                    int y2 = Integer.parseInt(dialog.getmEtY2().getText().toString());
                    int w = Integer.parseInt(dialog.getmEtW().getText().toString());
                    String color = dialog.getmEtColor().getText().toString();
                    int isParse = Color.parseColor(color);
                    mLineBean.set颜色(color);
                    mLineBean.setX1(x1);
                    mLineBean.setX2(x2);
                    mLineBean.setY1(y1);
                    mLineBean.setY2(y2);
                    mLineBean.setW(w);
                    invalidate();
                    dialog.dismiss();
                } catch (NumberFormatException e) {
                    Toast.makeText(getContext(),"请输入正确的参数",Toast.LENGTH_SHORT).show();
                } catch(IllegalArgumentException e) {
                    Toast.makeText(getContext(),"请输入正确的参数",Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.show();
    }

    private void showCircularDialog() {
        final CircularDialog dialog = new CircularDialog(getContext());
        dialog.getmEtX().setText(mCircularBean.getX() + "");
        dialog.getmEtY().setText(mCircularBean.getY() + "");
        dialog.getmEtR().setText(mCircularBean.getR() + "");
        dialog.getmEtW().setText(mCircularBean.getW() + "");
        dialog.getmEtColor().setText(mCircularBean.get颜色());
        dialog.setOnNoListsner(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //    Toast.makeText(getContext(),"no",Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        dialog.setOnYesListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //       Toast.makeText(getContext(),"yes",Toast.LENGTH_SHORT).show();
                try {
                    int x0 = Integer.parseInt(dialog.getmEtX().getText().toString());
                    int y0 = Integer.parseInt(dialog.getmEtY().getText().toString());
                    int r = Integer.parseInt(dialog.getmEtR().getText().toString());
                    int w = Integer.parseInt(dialog.getmEtW().getText().toString());
                    String color = dialog.getmEtColor().getText().toString();
                    int isParse = Color.parseColor(color);
                    mCircularBean.set颜色(color);
                    mCircularBean.setX(x0);
                    mCircularBean.setY(y0);
                    mCircularBean.setR(r);
                    mCircularBean.setW(w);
                    invalidate();
                    dialog.dismiss();
                }catch (NumberFormatException e) {
                    Toast.makeText(getContext(),"请输入正确的参数",Toast.LENGTH_SHORT).show();
                }catch(IllegalArgumentException e) {
                    Toast.makeText(getContext(),"请输入正确的参数",Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.show();

    }

}
