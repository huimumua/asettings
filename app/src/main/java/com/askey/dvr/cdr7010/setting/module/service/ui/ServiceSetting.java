package com.askey.dvr.cdr7010.setting.module.service.ui;

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
 * 创建时间：2018/4/8 13:29
 * 修改人：skysoft
 * 修改时间：2018/4/8 13:29
 * 修改备注：
 */
public class ServiceSetting extends SecondBaseActivity implements AdapterView.OnItemClickListener {

    private static final String TAG = "ServiceSetting";
    private String[] secondMenuItem = {"サーバーメンテナンスについて　8月", "サーバーメンテナンスについて　7月",
            "サーバーメンテナンスについて　6月", "サーバーメンテナンスについて　5月", "サーバーメンテナンスについて　4月",
            "サーバーメンテナンスについて　3月", "サーバーメンテナンスについて　2月", "サーバーメンテナンスについて　1月"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_jvclayout);

        menuInfo = getIntent().getStringArrayExtra("menu_item");
        initView(getResources().getString(R.string.main_menu_all_query), R.drawable.icon_submenu_help, menuInfo, R.layout.second_menu_layout);
        list_view.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String clickItem = currentData.get(position).get("menu_item").toString();

        if (clickItem.equals(getResources().getString(R.string.help))) {
            startActivity(new Intent(mContext, HelpActivity.class));
        } else if (clickItem.equals(getResources().getString(R.string.notification_list))) {
            Intent intent = new Intent(mContext, NotificationListActivity.class);
//            intent.putExtra("menu_item", secondMenuItem);
            startActivity(intent);
        }
    }


}
