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
        back  = BitmapFactory.decodeResource(getResources(), R.drawable.image_center_level);

        bubble = BitmapFactory.decodeResource(getResources(), R.drawable.image_lebel_red);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制水平仪表盘
        int with = back.getWidth()/2;
        canvas.drawBitmap(back, getWidth()/2-with, getHeight()/2-with, null);
        //根据气泡坐标绘制气泡
        canvas.drawBitmap(bubble, bubbleX, bubbleY, null);
    }

}
