package com.askey.dvr.cdr7010.setting.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.askey.dvr.cdr7010.setting.R;

public class JVCRelativeLayout extends RelativeLayout {

    private ImageView top_btn, center_btn, bottom_btn, back_btn;
    private Drawable top_img, center_img, bottom_img, back_img;
    private boolean top_visible, center_visible, bottom_visible, back_visible;
    private LinearLayout right_button_layout, bottom_button_layout;
    private Context context;
    private View myView;

    public JVCRelativeLayout(Context context) {
        super(context);
        initView(context);
    }

    public JVCRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.JVCRelativeLayout);

        top_img = typedArray.getDrawable(R.styleable.JVCRelativeLayout_top_imageview);
        top_visible = typedArray.getBoolean(R.styleable.JVCRelativeLayout_top_visible, true);
        center_img = typedArray.getDrawable(R.styleable.JVCRelativeLayout_center_imageview);
        center_visible = typedArray.getBoolean(R.styleable.JVCRelativeLayout_center_visible, true);
        bottom_img = typedArray.getDrawable(R.styleable.JVCRelativeLayout_bottom_imageview);
        bottom_visible = typedArray.getBoolean(R.styleable.JVCRelativeLayout_bottom_visible, true);
        back_img = typedArray.getDrawable(R.styleable.JVCRelativeLayout_back_imageview);
        back_visible = typedArray.getBoolean(R.styleable.JVCRelativeLayout_back_visible, true);
        typedArray.recycle();

        initView(context);
    }

    public JVCRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {

        this.context = context;

        right_button_layout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.right_button_layout, this, false);
        bottom_button_layout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.bottom_button_layout, this, false);

        LayoutParams rightParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        rightParams.addRule(RelativeLayout.ALIGN_PARENT_END, TRUE);
        addView(right_button_layout, rightParams);

        LayoutParams bottomParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        bottomParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, TRUE);
        bottomParams.addRule(RelativeLayout.ALIGN_PARENT_END, TRUE);
        addView(bottom_button_layout, bottomParams);

        setBackgroundResource(R.drawable.bg_menu_main);

        top_btn = (ImageView) right_button_layout.findViewById(R.id.top_btn);
        if (null == top_img) {
            top_btn.setImageResource(R.drawable.tag_menu_main_moveup);
        } else {
            top_btn.setImageDrawable(top_img);
        }
        setTop_visible(top_visible);

        center_btn = (ImageView) right_button_layout.findViewById(R.id.center_btn);
        if (null == center_img) {
            center_btn.setImageResource(R.drawable.tag_menu_main_enter);
        } else {
            center_btn.setImageDrawable(center_img);
        }
        setCenter_visible(center_visible);

        bottom_btn = (ImageView) right_button_layout.findViewById(R.id.bottom_btn);
        if (null == bottom_img) {
            bottom_btn.setImageResource(R.drawable.tag_menu_main_movedown);
        } else {
            bottom_btn.setImageDrawable(bottom_img);
        }
        setBottom_visible(bottom_visible);

        back_btn = (ImageView) bottom_button_layout.findViewById(R.id.back);
        if (null == back_img) {
            back_btn.setImageResource(R.drawable.tag_menu_main_back);
        } else {
            back_btn.setImageDrawable(back_img);
        }
        setBack_visible(back_visible);
    }

    public void setTop_img(int id) {
        top_btn.setImageResource(id);
    }

    public void setTop_visible(boolean top_visible) {
        if (top_visible) {
            top_btn.setVisibility(VISIBLE);
        } else {
            top_btn.setVisibility(INVISIBLE);
        }
    }

    public void setCenter_img(int id) {
        center_btn.setImageResource(id);
    }

    public void setCenter_visible(boolean center_visible) {
        if (center_visible) {
            center_btn.setVisibility(VISIBLE);
        } else {
            center_btn.setVisibility(INVISIBLE);
        }
    }

    public void setBottom_img(int id) {
        bottom_btn.setImageResource(id);
    }

    public void setBottom_visible(boolean bottom_visible) {
        if (bottom_visible) {
            bottom_btn.setVisibility(VISIBLE);
        } else {
            bottom_btn.setVisibility(INVISIBLE);
        }
    }

    public void setBack_img(int id) {
        back_btn.setImageResource(id);
    }

    public void setBack_visible(boolean back_visible) {
        if (back_visible) {
            back_btn.setVisibility(VISIBLE);
        } else {
            back_btn.setVisibility(INVISIBLE);
        }
    }


    public void setContentView(int viewId) {

        myView = LayoutInflater.from(context).inflate(viewId, this, false);

        LayoutParams centerParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        centerParams.addRule(RelativeLayout.ABOVE, R.id.bottom);
        centerParams.addRule(RelativeLayout.START_OF, R.id.right);
        addView(myView, centerParams);
        postInvalidate();
    }

    public View getMyView() {
        return myView;
    }
}
