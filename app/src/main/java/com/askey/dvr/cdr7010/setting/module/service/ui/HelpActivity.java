package com.askey.dvr.cdr7010.setting.module.service.ui;

import android.content.ContentResolver;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.widget.TextView;

import com.askey.dvr.cdr7010.setting.R;
import com.askey.dvr.cdr7010.setting.base.BaseActivity;
import com.askey.dvr.cdr7010.setting.util.Logg;
import com.askey.platform.AskeySettings;

/**
 * 项目名称：settings
 * 类描述：
 * 创建人：skysoft  charles.bai
 * 创建时间：2018/4/28 13:50
 * 修改人：skysoft
 * 修改时间：2018/4/28 13:50
 * 修改备注：
 */
public class HelpActivity extends BaseActivity {
    private TextView name, number;
    private ContentResolver contentResolver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        contentResolver = getContentResolver();
        setRightView(false, true, false);
        setBottomView(R.drawable.tag_menu_sub_cancel);
        setRightView(false, 0, true, R.drawable.tag_menu_sub_ok, false, 0);
        name = (TextView) findViewById(R.id.tv_content_info);
        String contactName = Settings.Global.getString(contentResolver, AskeySettings.Global.COMM_INQUIRY_NAME);
        Logg.i("HelpActivity", "==contactName=" + contactName);
        if (null != contactName) {
            name.setText(contactName);
        }
        number = (TextView) findViewById(R.id.tv_content_number);
        String contactNumber = Settings.Global.getString(contentResolver, AskeySettings.Global.COMM_INQUIRY_NO);
        Logg.i("HelpActivity", "==contactNumber=" + contactNumber);
        if (null != contactNumber) {
            number.setText(contactNumber);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_ENTER:
                finish();
                break;
        }
        return super.onKeyDown(keyCode, event);
    }
}
