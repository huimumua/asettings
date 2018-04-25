package com.askey.dvr.cdr7010.setting.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.askey.dvr.cdr7010.setting.R;

public class VerticalProgressBar extends View {

    private Paint paint;// 画笔
    private int progress;// 进度值
    private int secondProgress;// 进度值
    private int total;
    private int width;// 宽度值
    private int height;// 高度值

    public VerticalProgressBar(Context context, AttributeSet attrs,
                               int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public VerticalProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public VerticalProgressBar(Context context) {
        super(context);
        init();
    }

    private void init() {
        paint = new Paint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasuredWidth() - 1;// 宽度值
        height = getMeasuredHeight() - 1;// 高度值
        Log.i("VerticalProgressBar", "==height==" + height + "=width==" + width);
    }

    private Bitmap bitmap;
    private Rect srcRect = new Rect();
    private Rect dstRect = new Rect();
    private int imgWidth;
    private int imgHeight;
    private int mLeft = 0;
    private int mTop = 0;

    @Override
    protected void onDraw(Canvas canvas) {
//		paint.setColor(Color.rgb(55, 200, 255));// 设置画笔颜色
//		float progressHeight = (float) 6 * height / total;
//		Log.i("test", "==progressHeight=" + progressHeight);
//		if (progressHeight < 8) {
//			progressHeight = 8;
//		}
//		if ((secondProgress - progress) == 6) {
//			int top = progress * height / total;
//			canvas.drawRect(0, top, width, (top + progressHeight), paint);// 画矩形
//			Log.i("test", "=top=" + top + "=(top + progressHeight)==" + (top + progressHeight));
//		} else if (secondProgress - progress < 6) {//不满一页
//			canvas.drawRect(0, (float) progress / total * height, width, (float) secondProgress / total * height, paint);// 画矩形
//			Log.i("test", "=height - progress / total * height=" + ((float) progress / total * height) + "=width==" + width + "=height=" + height);
//		}
////		canvas.drawRect(0, height - progress / 100f * height, width, height,
////				paint);// 画矩形
//		canvas.drawLine(0, 0, width, 0, paint);// 画顶边
//		canvas.drawLine(0, 0, 0, height, paint);// 画左边
//		canvas.drawLine(width, 0, width, height, paint);// 画右边
//		canvas.drawLine(0, height, width, height, paint);// 画底边
//
////		paint.setColor(Color.RED);// 设置画笔颜色为红色
////		paint.setTextSize(width / 3);// 设置文字大小
////		canvas.drawText(progress + "%",
////				(width - getTextWidth(progress + "%")) / 2, height / 2, paint);// 画文字
////		paint.setColor(Color.WHITE);// 设置画笔颜色
////		canvas.drawRect(1, (float)secondProgress / total * height, width, height, paint);// 画矩形
////		canvas.drawRect(1, height - secondProgress / 100f * height, width, height, paint);// 画矩形
        super.onDraw(canvas);
        //获取图片资源
        BitmapDrawable drawableBackground = (BitmapDrawable) getResources().getDrawable(R.drawable.bg_scroll_menu_main);
        bitmap = drawableBackground.getBitmap();
        dstRect.left = 0;
        dstRect.top = 0;
        dstRect.right = width;
        dstRect.bottom = height;
        if (bitmap != null) {
            imgWidth = bitmap.getWidth();
            imgHeight = bitmap.getHeight();
            srcRect.left = 0 + mLeft;
            srcRect.right = imgWidth - mLeft;
            srcRect.top = 0 + mTop;
            srcRect.bottom = imgHeight - mTop;
            canvas.drawBitmap(bitmap, srcRect, dstRect, null);
        }
        BitmapDrawable drawable = (BitmapDrawable) getResources().getDrawable(R.drawable.scroll_menu_main);
        bitmap = drawable.getBitmap();
        dstRect.left = 0;
        dstRect.right = width;
        if (bitmap != null) {
            int progressHeight = (int) (float) 6 * height / total;
            if (progressHeight < 8) {
                progressHeight = 8;
            }
            if ((secondProgress - progress) == 6) {
                dstRect.top = progress * height / total;
                dstRect.bottom = dstRect.top + progressHeight;
                canvas.drawBitmap(bitmap, srcRect, dstRect, null);
            } else if (secondProgress - progress < 6) {//不满一页
                dstRect.top = progress / total * height;
                dstRect.bottom = secondProgress / total * height;
                canvas.drawBitmap(bitmap, srcRect, dstRect, null);
            }
        }
    }

    /**
     * 拿到文字宽度
     *
     * @param str 传进来的字符串
     *            return 宽度
     */
    private int getTextWidth(String str) {
        // 计算文字所在矩形，可以得到宽高
        Rect rect = new Rect();
        paint.getTextBounds(str, 0, str.length(), rect);
        return rect.width();
    }

    /**
     * 设置progressbar进度
     */
    public void setProgress(int progress, int secondProgress, int total) {
        this.progress = progress;
        this.secondProgress = secondProgress;
        this.total = total;
        postInvalidate();
    }

}
