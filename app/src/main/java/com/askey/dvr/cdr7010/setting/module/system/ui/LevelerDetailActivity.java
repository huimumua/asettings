package com.askey.dvr.cdr7010.setting.module.system.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;

import com.askey.dvr.cdr7010.setting.R;
import com.askey.dvr.cdr7010.setting.base.BaseActivity;
import com.askey.dvr.cdr7010.setting.module.dirving.ui.RangeSettingActivity;
import com.askey.dvr.cdr7010.setting.module.vehicle.ui.VehicleTypeSetting;
import com.askey.dvr.cdr7010.setting.util.Const;
import com.askey.dvr.cdr7010.setting.util.PreferencesUtils;

/**
 * 项目名称：settings
 * 类描述：
 * 创建人：skysoft  charles.bai
 * 创建时间：2018/4/25 10:33
 * 修改人：skysoft
 * 修改时间：2018/4/25 10:33
 * 修改备注：
 */
public class LevelerDetailActivity extends BaseActivity{
    private static final String TAG = "LevelerDetailActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leveler_detail);


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            boolean isFirstInit = (boolean) PreferencesUtils.get(mContext, Const.SETTTING_FIRST_INIT,true);
            if(isFirstInit){
                Intent intent = new Intent(mContext,RangeSettingActivity.class);
                startActivity(intent);
            }
            return true;
        }else if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {

            return true;
        }else if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
