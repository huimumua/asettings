package com.askey.dvr.cdr7010.setting.module.service.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.askey.dvr.cdr7010.setting.R;

public class NotificationInfoActivity extends AppCompatActivity {
    private TextView title_tv;
    private ImageView title_icon;
    private String title;
    private ScrollView scrollView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_info);
        title = getIntent().getStringExtra("clickItem");
        initView();
    }

    private void initView() {
        title_tv = (TextView) findViewById(R.id.title_tv);
        title_tv.setText(title);
        title_icon = (ImageView) findViewById(R.id.title_icon);
        title_icon.setImageDrawable(getResources().getDrawable(R.drawable.icon_submenu_help));
        scrollView = (ScrollView) findViewById(R.id.scrollview);
    }
}
