package com.askey.dvr.cdr7010.setting.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.askey.dvr.cdr7010.setting.R;

public class CommDialog extends Dialog {
    private MarqueeTextView messageText;
    private Context mContext;
    private String msg;
    private int type = TYPE_BUTTON_OK | TYPE_BUTTON_CANCEL;
    public static final int TYPE_BUTTON_OK = 1;
    public static final int TYPE_BUTTON_CANCEL = 1 << 2;
    public static final int TYPE_BUTTON_HIDE = 0;
    private String BUTTON_OK_MSG = "OK";
    private String BUTTON_CANCEL_MSG = "Cancel";
    private OnClickListener mPositiveButtonListener;
    private OnClickListener mNegativeButtonListener;
    private int width = 0;
    private int height = 0;
    private Button btnOk, btnCancel;

    public CommDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        mContext = context;
    }

    public CommDialog(Context context, int theme) {
        super(context, theme);
        mContext = context;
    }

    public CommDialog(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_comm);
        android.view.WindowManager.LayoutParams parames = getWindow().getAttributes();
        parames.height = height == 0 ? 136 : height;
        parames.width = width == 0 ? 248 : width;
        getWindow().setAttributes(parames);
        setCanceledOnTouchOutside(false);
        initViews();
    }

    private void initViews() {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);

        messageText = (MarqueeTextView) findViewById(R.id.content);
        messageText.setText(msg);
        messageText.setTextColor(0xcc000000);
        messageText.setTextSize(TypedValue.COMPLEX_UNIT_PX, 20);
        messageText.setGravity(Gravity.CENTER);
        messageText.setPadding(10,0,10,0);
        messageText.setLineSpacing(1.0f, 1.2f);
        messageText.setSingleLine();
        messageText.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        messageText.setMarqueeRepeatLimit(-1);

        btnOk = (Button) findViewById(R.id.ib_ok);
        btnOk.setOnClickListener(clickListener);
        btnOk.setTextColor(Color.BLACK);
        btnOk.setText(mContext.getResources().getString(R.string.sdcard_init_ok));
        btnOk.setGravity(Gravity.CENTER);
        btnOk.getPaint().setTextSize(20);
        btnOk.getLayoutParams().width = 80;
        btnOk.getLayoutParams().height = 26;
        ((ViewGroup.MarginLayoutParams) btnOk.getLayoutParams()).leftMargin = 52;

        btnCancel = (Button) findViewById(R.id.ib_cancle);
        btnCancel.setOnClickListener(clickListener);
        btnCancel.setTextColor(Color.BLACK);
        btnCancel.setText(mContext.getResources().getString(R.string.sdcard_init_cancel));
        btnCancel.setGravity(Gravity.CENTER);
        btnCancel.getPaint().setTextSize(20);
        btnCancel.getLayoutParams().width = 80;
        btnCancel.getLayoutParams().height = 26;
        ((ViewGroup.MarginLayoutParams) btnCancel.getLayoutParams()).leftMargin = 20;

        if ((type & TYPE_BUTTON_OK) == 0 && (type & TYPE_BUTTON_CANCEL) == 0) {
            btnOk.setVisibility(View.GONE);
            btnCancel.setVisibility(View.GONE);
            return;
        }
        if ((type & TYPE_BUTTON_OK) == 0) {
            btnOk.setVisibility(View.GONE);
            btnCancel.setLayoutParams(layoutParams);
        }
        if ((type & TYPE_BUTTON_CANCEL) == 0) {
            btnCancel.setVisibility(View.GONE);
            btnOk.setLayoutParams(layoutParams);
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                btnCancel.requestFocusFromTouch();
            }
        }, 20);
    }

    public void setMessage(int resId) {
        this.msg = mContext.getResources().getString(resId);
        if (messageText != null) {
            messageText.setText(msg);
        }
    }

    public void setMessage(String msg) {
        this.msg = msg;
        if (messageText != null) {
            messageText.setText(msg);
        }
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setButtonOkMsg(String msg) {
        BUTTON_OK_MSG = msg;
    }

    public void setButtonCancelMsg(String msg) {
        BUTTON_CANCEL_MSG = msg;
    }

    public void setDialogWidth(int width) {
        this.width = width;
    }

    public void setDialogHeight(int height) {
        this.height = height;
    }

    public void setPositiveButtonListener(final OnClickListener onClickListener) {
        this.mPositiveButtonListener = onClickListener;
    }

    public void setNegativeButtonListener(final OnClickListener onClickListener) {
        this.mNegativeButtonListener = onClickListener;
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ib_ok:
                    if (mPositiveButtonListener != null) {
                        mPositiveButtonListener.onClick(CommDialog.this, BUTTON_POSITIVE);
                    }
                    dismiss();
                    break;
                case R.id.ib_cancle:
                    if (mNegativeButtonListener != null) {
                        mNegativeButtonListener.onClick(CommDialog.this, BUTTON_NEGATIVE);
                    }
                    dismiss();
                    break;
                default:
            }

        }
    };

    @Override
    public boolean onKeyDown(int keyCode, @NonNull KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_UP:
                /*if (btnOk.isFocused()) {
                    btnCancel.requestFocus();
                } else */if (btnCancel.isFocused()) {
                    btnOk.requestFocus();
                }
                return true;
            case KeyEvent.KEYCODE_DPAD_DOWN:
                if (btnOk.isFocused()) {
                    btnCancel.requestFocus();
                }/* else if (btnCancel.isFocused()) {
                    btnOk.requestFocus();
                }*/
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}