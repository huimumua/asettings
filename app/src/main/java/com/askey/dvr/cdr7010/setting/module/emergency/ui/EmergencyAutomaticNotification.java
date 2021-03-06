package com.askey.dvr.cdr7010.setting.module.emergency.ui;

import android.content.ContentResolver;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;

import com.askey.dvr.cdr7010.setting.R;
import com.askey.dvr.cdr7010.setting.base.SecondBaseActivity;
import com.askey.platform.AskeySettings;

/**
 * 项目名称：settings
 * 类描述：
 * 创建人：skysoft  charles.bai
 * 创建时间：2018/4/26 20:03
 * 修改人：skysoft
 * 修改时间：2018/4/26 20:03
 * 修改备注：
 */
public class EmergencyAutomaticNotification extends SecondBaseActivity implements AdapterView.OnItemClickListener {
    private static final String TAG = "EmergencyAutomaticNotification";
    private ContentResolver contentResolver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_jvclayout);

        menuInfo = getIntent().getStringArrayExtra("menu_item");
        initView(getResources().getString(R.string.communication_emergency_automatic_notification),R.drawable.icon_submenu_communication, menuInfo, R.layout.second_menu_layout);
        list_view.setOnItemClickListener(this);
        contentResolver = getContentResolver();
        focusItem();
    }

    private void focusItem() {
        list_view.requestFocus();
        jvcRelativeLayout.setMarquee_visible(true);
        jvcRelativeLayout.setMarquee_text(getString(R.string.communication_emergency_notify));
        int emergency_auto = Settings.Global.getInt(contentResolver, AskeySettings.Global.COMM_EMERGENCY_AUTO, 1);
        int position = 0;
        if (emergency_auto == 1) {
            position = 0;
        } else {
            position = 1;
        }
        list_view.setSelection(position);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String clickItem = currentData.get(position).get("menu_item").toString();
        if (clickItem.equals(getResources().getString(R.string.communication_emergency_automatic_notification_on))) {
            Settings.Global.putInt(contentResolver, AskeySettings.Global.COMM_EMERGENCY_AUTO, 1);
        } else if (clickItem.equals(getResources().getString(R.string.communication_emergency_automatic_notification_off))) {
            Settings.Global.putInt(contentResolver, AskeySettings.Global.COMM_EMERGENCY_AUTO, 0);
        }
        finish();
    }
}
