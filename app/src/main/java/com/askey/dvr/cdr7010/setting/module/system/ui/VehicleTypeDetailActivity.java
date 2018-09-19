package com.askey.dvr.cdr7010.setting.module.system.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;

import com.askey.dvr.cdr7010.setting.R;
import com.askey.dvr.cdr7010.setting.base.BaseActivity;

/**
 * 项目名称：settings
 * 类描述：
 * 创建人：skysoft  charles.bai
 * 创建时间：2018/4/25 10:33
 * 修改人：skysoft
 * 修改时间：2018/4/25 10:33
 * 修改备注：
 */
public class VehicleTypeDetailActivity extends BaseActivity {
    private static final String TAG = "VehicleTypeDetailActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leveler_detail);

//        String title = getResources().getString(R.string.leveler_detail_title);
        String title = getResources().getString(R.string.vehicle_type);
        setTitleView(title);
        setRightView(false, true, false);
        setBottomView(false,R.drawable.tag_menu_sub_skip);

    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            String[] secondMenuItem = getResources().getStringArray(R.array.vehicle_type);
            Intent intent = new Intent(mContext, VehicleTypeSetting.class);
            intent.putExtra("menu_item", secondMenuItem);
            startActivity(intent);
            finish();
            return true;
        }
//        else if (keyCode == KeyEvent.KEYCODE_BACK) {
//            if (Const.SET_WIZARD) {
//                Intent intent = new Intent(mContext, SetWizardHelpActivity.class);
//                intent.putExtra("set_wizard_help_index", "set_wizard_help_context_mounting_position");
//                startActivity(intent);
//                finish();
//            }
//        }
        return super.onKeyUp(keyCode, event);
    }

}
