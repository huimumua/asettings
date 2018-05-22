package com.askey.dvr.cdr7010.setting.module.sdcard.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;

import com.askey.dvr.cdr7010.setting.R;
import com.askey.dvr.cdr7010.setting.base.SecondBaseActivity;
import com.askey.dvr.cdr7010.setting.util.SdcardUtil;

/**
 * 项目名称：settings
 * 类描述：
 * 创建人：skysoft  charles.bai
 * 创建时间：2018/4/8 13:29
 * 修改人：skysoft
 * 修改时间：2018/4/8 13:29
 * 修改备注：
 */
public class SdcardSetting extends SecondBaseActivity implements AdapterView.OnItemClickListener{

    private static final String TAG = "SdcardSetting";
    private Boolean isExist = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_jvclayout);

        menuInfo = getIntent().getStringArrayExtra("menu_item");
        initView(getResources().getString(R.string.sdcard_setting),R.drawable.icon_submenu_sdcard,menuInfo,R.layout.second_menu_layout);
        list_view.setOnItemClickListener(this);

}

    @Override
    protected void onResume() {
        super.onResume();
        isExist = SdcardUtil.checkSdcardExist();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String clickItem = currentData.get(position).get("menu_item").toString();
        if(clickItem.equals(getResources().getString(R.string.sdcard_setting_information)) && isExist) {
            startActivity(new Intent(mContext, SdcardInformation.class));
        }
        if(clickItem.equals(getResources().getString(R.string.sdcard_setting_initialization)) /*&& isExist*/) {
            startActivity(new Intent(mContext, SdcardInitialization.class));
        }

    }


}
