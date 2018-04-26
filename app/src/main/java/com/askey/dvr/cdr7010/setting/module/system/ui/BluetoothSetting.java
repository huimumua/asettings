package com.askey.dvr.cdr7010.setting.module.system.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.askey.dvr.cdr7010.setting.R;
import com.askey.dvr.cdr7010.setting.base.BaseActivity;
import com.askey.dvr.cdr7010.setting.widget.JVCRelativeLayout;

/**
 * 项目名称：settings
 * 类描述：
 * 创建人：skysoft  charles.bai
 * 创建时间：2018/4/8 13:42
 * 修改人：skysoft
 * 修改时间：2018/4/8 13:42
 * 修改备注：
 */
public class BluetoothSetting extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_jvclayout);

        JVCRelativeLayout jvcRelativeLayout = (JVCRelativeLayout) findViewById(R.id.root);
        jvcRelativeLayout.setContentView(R.layout.activity_base_nemu);
        View view = jvcRelativeLayout.getMyView();

        Button button = (Button) view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(BluetoothSetting.this, "ok", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
