package com.askey.dvr.cdr7010.setting.module.system.ui;

import android.content.ContentResolver;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.view.KeyEvent;

import com.askey.dvr.cdr7010.setting.R;
import com.askey.dvr.cdr7010.setting.SetWizardHelpActivity;
import com.askey.dvr.cdr7010.setting.base.BaseActivity;
import com.askey.platform.AskeySettings;

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

        String title = getResources().getString(R.string.leveler_detail_title);
        setTitleView(title);
        setRightView(false,true,false);
        setBottomView(R.drawable.tag_menu_sub_skip);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            startActivity(new Intent(mContext, LevelerActivity.class));
            finish();
            return true;
        }else if(keyCode == KeyEvent.KEYCODE_BACK){
            ContentResolver contentResolver = getContentResolver();
            int car_type = Settings.Global.getInt(contentResolver, AskeySettings.Global.SETUP_WIZARD_AVAILABLE, 1);
            if (car_type==1) {
                Intent intent = new Intent(mContext,SetWizardHelpActivity.class);
                intent.putExtra("set_wizard_help_index", "set_wizard_help_context_vehicle_type");
                startActivity(intent);
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

}
