package com.askey.dvr.cdr7010.setting.module.system.ui.leveler;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import com.askey.dvr.cdr7010.setting.R;

/**
 * Created by Hades on 16/10/9.
 */
public class SpiritView extends View {
    //定义水平仪仪表盘图片
    public Bitmap back;
    //定义水平仪中气泡图标
    public Bitmap bubble;
    //定义水平仪中气泡的X、Y坐标
    public int bubbleX,bubbleY;

    public SpiritView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //获取窗口管理器
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        //获取屏幕的高度和宽度
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        int screenWidth = metrics.widthPixels;
        int screenHeight = metrics.heightPixels;

        //创建位图
        back = Bitmap.createBitmap(screenWidth, screenWidth, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(back);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        //设置绘制风格：仅填充
        paint.setStyle(Paint.Style.FILL);
        //创建一个线性渐变来绘制线性渐变
        int color = getResources().getColor(R.color.color_66cc66);
        Shader shader = new LinearGradient(0, screenWidth, screenWidth * 0.8f, screenWidth * 0.2f,
                color, Color.WHITE, Shader.TileMode.MIRROR);
        paint.setShader(shader);
        //绘制圆形
        canvas.drawCircle(screenWidth / 2, screenHeight / 2, screenHeight / 2-50, paint);
        Paint paint2 = new Paint();
        paint2.setAntiAlias(true);
        //设置绘制风格：仅绘制边框
        paint2.setStyle(Paint.Style.STROKE);
        paint2.setStrokeWidth(5);
        paint2.setColor(Color.BLACK);
        //绘制圆形边框
        canvas.drawCircle(screenWidth / 2, screenHeight / 2, screenHeight / 2-50, paint2);
        canvas.drawCircle(screenWidth / 2, screenHeight / 2, 15, paint2);
        //设置画笔宽度
        paint2.setStrokeWidth(10);
        paint2.setColor(Color.RED);

        bubble = BitmapFactory.decodeResource(getResources(), R.mipmap.bubble);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制水平仪表盘
        canvas.drawBitmap(back, 0, 0, null);
        //根据气泡坐标绘制气泡
        canvas.drawBitmap(bubble, bubbleX, bubbleY, null);
    }
}
