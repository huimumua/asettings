package com.askey.dvr.cdr7010.setting.module.service.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.askey.dvr.cdr7010.setting.R;
import com.askey.dvr.cdr7010.setting.base.SecondBaseActivity;
import com.askey.dvr.cdr7010.setting.util.Logg;

public class NotificationListActivity extends SecondBaseActivity implements AdapterView.OnItemClickListener {
    private static final String TAG = "NotificationListActivity";
    private ImageView title_icon;
    private TextView title_tv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        menuInfo = getIntent().getStringArrayExtra("menu_item");
        if (null != menuInfo) {
            setContentView(R.layout.base_jvclayout);
            initView(getResources().getString(R.string.query_notification), R.drawable.icon_submenu_help, menuInfo, R.layout.second_menu_layout);
            list_view.setOnItemClickListener(this);
        } else {
            setContentView(R.layout.activity_empty_notification);
            title_tv = (TextView) findViewById(R.id.title_tv);
            title_tv.setText(getResources().getString(R.string.query_notification));
            title_icon = (ImageView) findViewById(R.id.title_icon);
            title_icon.setImageDrawable(getResources().getDrawable(R.drawable.icon_submenu_help));
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String clickItem = currentData.get(position).get("menu_item").toString();
        Logg.i(TAG, "=onItemClick=position=" + position + "=id==" + id + "=clickItem==" + clickItem);
        Intent intent = new Intent(mContext, NotificationInfoActivity.class);
        intent.putExtra("clickItem", clickItem);
        startActivity(intent);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        event.startTracking();

        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
            case KeyEvent.KEYCODE_ENTER:
                finish();
                break;
        }

        return super.onKeyUp(keyCode, event);
    }

    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        return true;
    }

}
