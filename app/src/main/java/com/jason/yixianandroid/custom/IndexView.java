package com.jason.yixianandroid.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by jason on 2017/4/25.
 */

public class IndexView extends View{

    /*字母表数组*/
    private String letter = "ABCDEFGHIJKLMNOPQRSTUVWXYZ#";
    private Paint mPaint;
    private int mTextColor;
    private int mTextSize;
    private int mPadding;
    private int mLetterWidth;
    private int mLetterHeight;
    //当被触摸时，绘制背景
    private boolean isTouched;
    private int mBackgroundColor;
    private int mTransparentColor;
    //除了用于绘制外，更涉及到触摸事件
    private ArrayList<Rect> mRects;
    private OnCharTouchEvent mListener;
    //上一次获取的字母
    private String mPreLetter;

    public void setOnLetterTouchedListener(OnCharTouchEvent listener) {
        this.mListener = listener;
    }

    public IndexView(Context context) {
        super(context);
        init(context);
    }

    public IndexView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public IndexView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mTextColor = Color.parseColor("#666666");
        mBackgroundColor =  Color.parseColor("#bbbbbb");
        mTransparentColor = Color.parseColor("#00000000");
        mRects = new ArrayList<>();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mRects.clear();
        for (int i = 0; i < letter.length(); i++) {
            int x = 0;
            int y = mLetterHeight * i;
            Rect rect = new Rect(x, y, x + mLetterWidth, y + mLetterHeight);
            mRects.add(rect);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //widthMeasureSpec是父类中，调用该类的measure方法设置的
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        mLetterWidth = mLetterHeight = heightSize / letter.length();
        mPadding = mLetterHeight / 5;
        mTextSize = mLetterHeight - mPadding * 2;
        setMeasuredDimension(mLetterWidth, heightSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(isTouched ? mBackgroundColor : mTransparentColor);
        for (int i = 0; i < letter.length(); i++) {
            char c = letter.charAt(i);
            String s = String.valueOf(c);
            mPaint.setColor(mTextColor);
            mPaint.setTextSize(mTextSize);
            mPaint.setTextAlign(Paint.Align.CENTER);
            drawTextCenter(canvas, mPaint, s, mRects.get(i));
        }
    }

    private void doDown(MotionEvent event) {
        isTouched = true;
        int downX = (int) event.getX();
        int downY = (int) event.getY();
        String s = getTouchedLetter(downX, downY);
        Log.v("@s", s + "");
        if (mListener != null)
            mListener.onTouch(s);
        invalidate();
    }

    /**
     * 移动时，判断选中的字母是否发生了变化,并进行回调
     *
     * @param event
     */
    private void doMove(MotionEvent event) {
        isTouched = true;
        int downX = (int) event.getX();
        int downY = (int) event.getY();
        String s = getTouchedLetter(downX, downY);
        if (s == null)
            return;
        if (mListener != null) {
            if (mPreLetter == null || (!mPreLetter.equalsIgnoreCase(s))) {
                mListener.onLetterChanged(mPreLetter, s);
                mPreLetter = s;
            }
        }

        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                doDown(event);
                break;
            case MotionEvent.ACTION_MOVE:
                doMove(event);
                break;
            case MotionEvent.ACTION_UP:
                doCancelOrUp();
                break;
            case MotionEvent.ACTION_CANCEL:
                doCancelOrUp();
                break;
        }
        return true;
    }

    private void doCancelOrUp() {
        isTouched = false;
        if (mListener != null)
            mListener.onRelease();
        invalidate();
    }

    private String getTouchedLetter(int x, int y) {
        for (int i = 0; i < mRects.size(); i++) {
            Rect rect = mRects.get(i);
            if (rect.contains(x, y)) {
                return String.valueOf(letter.charAt(i));
            }
        }

        return null;
    }

    /**
     * 将文本绘制在居中位置
     *
     * @param canvas
     * @param paint
     * @param s
     * @param rect
     */
    private void drawTextCenter(Canvas canvas, Paint paint, String s, Rect rect) {
        Paint.FontMetricsInt fontMetrics = mPaint.getFontMetricsInt();
        int baseline = rect.top + (rect.bottom - rect.top - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(s, rect.centerX(), baseline, paint);
    }

    public interface OnCharTouchEvent {
        void onTouch(String s);

        void onLetterChanged(String preLetter, String letter);

        void onRelease();
    }


}
