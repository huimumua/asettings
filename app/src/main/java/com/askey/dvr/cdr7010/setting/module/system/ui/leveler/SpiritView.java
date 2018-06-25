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
import com.askey.dvr.cdr7010.setting.util.Logg;

/**
 * Created by Hades on 16/10/9.
 */
public class SpiritView extends View {
    private String TAG = "SpiritView";
    //定义水平仪仪表盘图片
    public Bitmap backBitmap;
    //定义水平仪中气泡图标
    public Bitmap bubbleBitmap;
    public Bitmap bubbleBitmapGreen;
    //定义水平仪中气泡的X、Y坐标
    public int bubbleX,bubbleY;
    private int backBitmapX,backBitmapY;

    public SpiritView(Context context, AttributeSet attrs) {
        super(context, attrs);
        backBitmap  = BitmapFactory.decodeResource(getResources(), R.drawable.image_center_level);

        bubbleBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_lebel_red);

        bubbleBitmapGreen = BitmapFactory.decodeResource(getResources(), R.drawable.image_level_green);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制水平仪表盘
        int with = backBitmap.getWidth()/2;
        canvas.drawBitmap(backBitmap, getWidth()/2-with, getHeight()/2-with, null);
        //根据气泡坐标绘制气泡  bubbleX = 125 bubbleY = 85
        if(bubbleX>95 && bubbleX<155 && bubbleY>75 && bubbleY< 95){
            canvas.drawBitmap(bubbleBitmapGreen, bubbleX, bubbleY, null);
        }else{
            canvas.drawBitmap(bubbleBitmap, bubbleX, bubbleY, null);
        }

    }

}
