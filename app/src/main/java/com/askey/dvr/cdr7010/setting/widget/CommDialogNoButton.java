package com.askey.dvr.cdr7010.setting.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.askey.dvr.cdr7010.setting.R;

public class CommDialogNoButton extends Dialog {
    private static final String TAG = "CommDialogNoButton";
    private MarqueeTextView messageText;
    private ImageView icon;
    private int iconResId;
    private boolean isShow;
    private Context mContext;
    private String msg;
    private OnClickListener mPositiveButtonListener;
    private OnClickListener mNegativeButtonListener;
    private int width = 0;
    private int height = 0;

    public CommDialogNoButton(@NonNull Context context) {
        super(context);
        mContext = context;
    }

    public CommDialogNoButton(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comm_dialog_no_button);
        android.view.WindowManager.LayoutParams parames = getWindow().getAttributes();
        parames.height = height == 0 ? 136 : height;
        parames.width = width == 0 ? 248 : width;
        getWindow().setAttributes(parames);
        setCanceledOnTouchOutside(false);

        initView();
    }

    private void initView() {
        Log.d(TAG, "initView: "+isShow);
        messageText = (MarqueeTextView) findViewById(R.id.content);
        messageText.setText(msg);
        icon = (ImageView) findViewById(R.id.icon);
        if (isShow) {
            icon.setImageResource(iconResId);
        } else {
            icon.setVisibility(View.GONE);
        }
    }

    public void setMessage(String msg) {
        this.msg = msg;
    }

    public void setIcon(int resId){
        iconResId = resId;
    }

    public void isShowIcon(boolean isShow){
       this.isShow = isShow;
    }

    public void setDialogWidth(int width) {
        this.width = width;
    }

    public void setDialogHeight(int height) {
        this.height = height;
    }
}
