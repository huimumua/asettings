package com.askey.dvr.cdr7010.setting.module.vehicle.ui;

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
 * 创建时间：2018/4/8 13:33
 * 修改人：skysoft
 * 修改时间：2018/4/8 13:33
 * 修改备注：
 */
public class VehicleTypeSetting extends SecondBaseActivity implements AdapterView.OnItemClickListener{

    private static final String TAG = "SdcardSetting";
    private Boolean isExist = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_jvclayout);

        menuInfo = getIntent().getStringArrayExtra("menu_item");
        initView(getResources().getString(R.string.vehicle_type),menuInfo,R.layout.second_menu_layout);
        list_view.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String clickItem = currentData.get(position).get("menu_item").toString();
        if(clickItem.equals(getResources().getString(R.string.vehicle_type_options)) ) {

        }else if(clickItem.equals(getResources().getString(R.string.vehicle_type_mini_sedan)) ) {

        }else if(clickItem.equals(getResources().getString(R.string.vehicle_type_minivan)) ) {

        }else if(clickItem.equals(getResources().getString(R.string.vehicle_type_medium_sedan)) ) {

        }else if(clickItem.equals(getResources().getString(R.string.vehicle_type_rv)) ) {

        }else if(clickItem.equals(getResources().getString(R.string.vehicle_type_suv)) ) {

        }else if(clickItem.equals(getResources().getString(R.string.vehicle_type_others)) ) {

        }else if(clickItem.equals(getResources().getString(R.string.vehicle_type_large_sedan)) ) {

        }

    }


}
