package com.askey.dvr.cdr7010.setting.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.animation.LinearInterpolator;
import android.widget.Scroller;

import com.askey.dvr.cdr7010.setting.R;

public class JVCMarqueeTextView extends android.support.v7.widget.AppCompatTextView {
    private static final String TAG = "MarqueeTextView";
    /**
     * 默认滚动时间
     */
    private static final int ROLLING_INTERVAL_DEFAULT = 10000;
    /**
     * 第一次滚动默认延迟
     */
    private static final int FIRST_SCROLL_DELAY_DEFAULT = 1000;
    /**
     * 滚动模式-一直滚动
     */
    public static final int SCROLL_FOREVER = 100;
    /**
     * 滚动模式-只滚动一次
     */
    public static final int SCROLL_ONCE = 101;

    /**
     * 滚动器
     */
    private Scroller mScroller;
    /**
     * 滚动一次的时间
     */
    private int mRollingInterval;
    /**
     * 滚动的初始 X 位置
     */
    private int mXPaused = 0;
    /**
     * 是否暂停
     */
    private boolean mPaused = true;
    /**
     * 是否第一次
     */
    private boolean mFirst = true;
    /**
     * 滚动模式
     */
    private int mScrollMode;
    /**
     * 初次滚动时间间隔
     */
    private int mFirstScrollDelay;
    /**
     * 是否允许滚动
     */
    private boolean canScroll = true;

    /**
     * 控件宽度
     */
    private int viewWidth;

    private Runnable runnable;

    public JVCMarqueeTextView(Context context) {
        this(context, null);
    }

    public JVCMarqueeTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public JVCMarqueeTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context, attrs, defStyle);
    }

    private void initView(Context context, AttributeSet attrs, int defStyle) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.JVCMarqueeTextView);
        mRollingInterval = typedArray.getInt(R.styleable.JVCMarqueeTextView_scroll_interval, ROLLING_INTERVAL_DEFAULT);
        mScrollMode = typedArray.getInt(R.styleable.JVCMarqueeTextView_scroll_mode, SCROLL_FOREVER);
        mFirstScrollDelay = typedArray.getInt(R.styleable.JVCMarqueeTextView_scroll_first_delay, FIRST_SCROLL_DELAY_DEFAULT);
        typedArray.recycle();
        setSingleLine();
        setEllipsize(null);
//        startScroll();
    }

    public void setContentText(final CharSequence text) {

        post(new Runnable() {
            @Override
            public void run() {
                //控件中内容可以滚动的长度
                viewWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
                Log.d(TAG, "setContentText: " + viewWidth + ", " + calculateScrollingLen(text));
                if (viewWidth > calculateScrollingLen(text)) {
                    setGravity(Gravity.CENTER);
                    canScroll = false;
                }
                setText(text);
                startScroll();
            }
        });
    }

    /**
     * 开始滚动
     */
    public void startScroll() {
        mXPaused = 0;
        mPaused = true;
        mFirst = true;
        resumeScroll();
    }

    /**
     * 继续滚动
     */
    public void resumeScroll() {
        if (!mPaused || !canScroll)
            return;
        // 设置水平滚动
        setHorizontallyScrolling(true);

        // 使用 LinearInterpolator 进行滚动
        if (mScroller == null) {
            mScroller = new Scroller(this.getContext(), new LinearInterpolator());
            setScroller(mScroller);
        }
        int scrollingLen = calculateScrollingLen(getText());
//        int scrollingLen = (int)getLayout().getLineWidth(0);

        Log.d(TAG, "scrollingLen: " + scrollingLen);
        final int distance = scrollingLen - mXPaused ;
        final int duration = (Double.valueOf(mRollingInterval * distance * 1.00000
                / scrollingLen)).intValue();
        if (mFirst) {
//            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    Log.d(TAG, "run_1: " + mXPaused + "," + distance + ", " + duration);
//                    mScroller.startScroll(mXPaused, 0, distance, 0, duration);
//                    invalidate();
//                    mPaused = false;
//                }
//            }, mFirstScrollDelay);
            //由于存在会延迟开始滚动，这里是为了避免在开始滚动之前此方法被多次连续调用而导致滚动重叠
            if (null != runnable) {
                removeCallbacks(runnable);
                runnable = null;
            }
            runnable = new Runnable() {
                @Override
                public void run() {
                    Log.d(TAG, "run_1: " + mXPaused + "," + distance + ", " + duration);
                    mScroller.startScroll(mXPaused, 0, distance, 0, duration);
                    invalidate();
                    mPaused = false;
                }
            };
            postDelayed(runnable, mFirstScrollDelay);
        } else {
            Log.d(TAG, "run_2: " + mXPaused + "," + distance + ", " + duration);
            mScroller.startScroll(mXPaused, 0, distance, 0, duration);
            invalidate();
            mPaused = false;
        }
    }

    /**
     * 暂停滚动
     */
    public void pauseScroll() {
        if (null == mScroller)
            return;

        if (mPaused)
            return;

        mPaused = true;

        mXPaused = mScroller.getCurrX();

        mScroller.abortAnimation();
    }

    /**
     * 停止滚动，并回到初始位置
     */
    public void stopScroll() {
        if (null == mScroller) {
            return;
        }
        mPaused = true;
        mScroller.startScroll(0, 0, 0, 0, 0);
    }

    /**
     * 计算滚动的距离
     *
     * @return 滚动的距离
     */
    private int calculateScrollingLen(CharSequence text) {
        TextPaint tp = getPaint();
        Rect rect = new Rect();
        String strTxt = text.toString();
        tp.getTextBounds(strTxt, 0, strTxt.length(), rect);
        return rect.width()+rect.left;//getTextBounds获取的是文本的绝对宽度，而真实的绘制会在第一个字符开头留一点空白，rect.left就是这个空白的宽度
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (null == mScroller) return;
        if (mScroller.isFinished() && (!mPaused)) {
            if (mScrollMode == SCROLL_ONCE) {
                stopScroll();
                return;
            }
            mPaused = true;
            mXPaused = -1 * getWidth();
            mFirst = false;
            resumeScroll();
        }
    }

    /**
     * 获取滚动一次的时间
     */
    public int getRndDuration() {
        return mRollingInterval;
    }

    /**
     * 设置滚动一次的时间
     */
    public void setRndDuration(int duration) {
        this.mRollingInterval = duration;
    }

    /**
     * 设置滚动模式
     */
    public void setScrollMode(int mode) {
        this.mScrollMode = mode;
    }

    /**
     * 获取滚动模式
     */
    public int getScrollMode() {
        return this.mScrollMode;
    }

    /**
     * 设置第一次滚动延迟
     */
    public void setScrollFirstDelay(int delay) {
        this.mFirstScrollDelay = delay;
    }

    /**
     * 获取第一次滚动延迟
     */
    public int getScrollFirstDelay() {
        return mFirstScrollDelay;
    }

    public boolean isPaused() {
        return mPaused;
    }
}
