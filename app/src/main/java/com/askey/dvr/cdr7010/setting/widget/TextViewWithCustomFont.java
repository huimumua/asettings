package com.askey.dvr.cdr7010.setting.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

public class TextViewWithCustomFont extends android.support.v7.widget.AppCompatTextView{
    public TextViewWithCustomFont(Context context) {
        super(context);
    }

    public TextViewWithCustomFont(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
//        setTypeface(context);
    }

    public TextViewWithCustomFont(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setTypeface(context);
    }

    public void setTypeface(Context context){
        Typeface typeface = Typeface.createFromAsset(context.getAssets(),"medium.otf");
        setTypeface(typeface);
    }
}
