package com.askey.dvr.cdr7010.setting.module.system.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import com.askey.dvr.cdr7010.setting.R;
import com.askey.dvr.cdr7010.setting.SetWizardHelpActivity;
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

        ImageView topMenu = (ImageView) this.findViewById(R.id.top_btn);
        ImageView centerMenu = (ImageView) this.findViewById(R.id.center_btn);
        ImageView bottomMenu = (ImageView) this.findViewById(R.id.bottom_btn);
        topMenu.setVisibility(View.GONE);
        bottomMenu.setVisibility(View.GONE);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            startActivity(new Intent(mContext, LevelerActivity.class));
            finish();
            return true;
        }else if(keyCode == KeyEvent.KEYCODE_BACK){
            startActivity(new Intent(mContext, LevelerDetailActivity.class));
            finish();
        }else if(keyCode == KeyEvent.KEYCODE_CALL){
            Intent intent = new Intent(mContext,SetWizardHelpActivity.class);
            intent.putExtra("set_wizard_help_index", "set_wizard_help_context_vehicle_type");
            startActivity(intent);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

}
