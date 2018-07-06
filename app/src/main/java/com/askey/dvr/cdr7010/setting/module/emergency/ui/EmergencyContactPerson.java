package com.askey.dvr.cdr7010.setting.module.emergency.ui;

import android.content.ContentResolver;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.widget.TextView;

import com.askey.dvr.cdr7010.setting.R;
import com.askey.dvr.cdr7010.setting.base.BaseActivity;
import com.askey.dvr.cdr7010.setting.widget.JVCRelativeLayout;
import com.askey.platform.AskeySettings;

/**
 * 项目名称：settings
 * 类描述：
 * 创建人：skysoft  charles.bai
 * 创建时间：2018/4/26 20:04
 * 修改人：skysoft
 * 修改时间：2018/4/26 20:04
 * 修改备注：
 */
public class EmergencyContactPerson extends BaseActivity {
    private static final String TAG = "EmergencyContactPerson";
    private TextView name, number;
    private ContentResolver contentResolver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_communication_emergency_contact_person);
        contentResolver = getContentResolver();
        setRightView(false, true, false);
        setBottomView(R.drawable.tag_menu_sub_cancel);
        setRightView(false, 0, true, R.drawable.tag_menu_sub_ok, false, 0);
        name = (TextView) findViewById(R.id.content_info);
        String emergencyName = Settings.Global.getString(contentResolver, AskeySettings.Global.COMM_EMERGENCY_NAME);
        name.setText(emergencyName);
        number = (TextView) findViewById(R.id.content_number);
        String emergencyNumber = Settings.Global.getString(contentResolver, AskeySettings.Global.COMM_EMERGENCY_NUMBER);
        number.setText(emergencyNumber);
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
