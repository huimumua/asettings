package com.askey.dvr.cdr7010.setting.module.emergency.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;

import com.askey.dvr.cdr7010.setting.R;
import com.askey.dvr.cdr7010.setting.base.SecondBaseActivity;

/**
 * 项目名称：settings
 * 类描述：
 * 创建人：skysoft  charles.bai
 * 创建时间：2018/4/8 13:19
 * 修改人：skysoft
 * 修改时间：2018/4/8 13:19
 * 修改备注：
 */
public class EmergencySetting extends SecondBaseActivity implements AdapterView.OnItemClickListener {
    private static final String TAG = "EmergencySetting";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_jvclayout);

        menuInfo = getIntent().getStringArrayExtra("menu_item");
        initView(getResources().getString(R.string.communication),R.drawable.icon_submenu_communication,menuInfo,R.layout.second_menu_layout);
        list_view.setOnItemClickListener(this);

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String clickItem = currentData.get(position).get("menu_item").toString();
        if(clickItem.equals(getResources().getString(R.string.communication_emergency_contact_person)) ) {
            startActivity(new Intent(mContext, EmergencyContactPerson.class));

        }else if(clickItem.equals(getResources().getString(R.string.communication_emergency_automatic_notification)) ) {

            String [] secondMenuItem = getResources().getStringArray(R.array.emergency_automatic_notification);
            Intent intent = new Intent(mContext, EmergencyAutomaticNotification.class);
            intent.putExtra("menu_item", secondMenuItem);
            startActivity(intent);

        }
    }
}
