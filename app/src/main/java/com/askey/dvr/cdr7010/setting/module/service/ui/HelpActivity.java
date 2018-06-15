package com.askey.dvr.cdr7010.setting.module.service.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.askey.dvr.cdr7010.setting.R;
import com.askey.dvr.cdr7010.setting.base.BaseActivity;

/**
 * 项目名称：settings
 * 类描述：
 * 创建人：skysoft  charles.bai
 * 创建时间：2018/4/28 13:50
 * 修改人：skysoft
 * 修改时间：2018/4/28 13:50
 * 修改备注：
 */
public class HelpActivity  extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        setRightView(false,true,false);
        setBottomView(R.drawable.tag_menu_sub_cancel);
        setRightView(false,0,true,R.drawable.tag_menu_sub_ok,false,0);
    }

}
