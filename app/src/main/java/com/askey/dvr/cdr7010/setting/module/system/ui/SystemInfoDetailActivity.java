package com.askey.dvr.cdr7010.setting.module.system.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.askey.dvr.cdr7010.setting.R;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SystemInfoDetailActivity extends AppCompatActivity {
    private String type;
    private ImageView title_icon;
    private TextView title_tv, phone_number, net, signal, imei, imei_sv;
    private LinearLayout systemVersion, sim, openLicense;
    private TelephonyManager mPhoneManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_info_detail);

        type = getIntent().getStringExtra("info_type");
        initView();
        loadInfo(type);
    }

    private void initView() {
        systemVersion = (LinearLayout) findViewById(R.id.system_version);
        sim = (LinearLayout) findViewById(R.id.sim);
        openLicense = (LinearLayout) findViewById(R.id.open_license);

        phone_number = (TextView) findViewById(R.id.phone_number);
        net = (TextView) findViewById(R.id.net);
        signal = (TextView) findViewById(R.id.signal);
        imei = (TextView) findViewById(R.id.imei);
        imei_sv = (TextView) findViewById(R.id.imei_sv);

        title_tv = (TextView) findViewById(R.id.title_tv);
        title_icon = (ImageView) findViewById(R.id.title_icon);
    }

    private void loadInfo(String type) {
        if (type.equals("version")) {
            systemVersion.setVisibility(View.VISIBLE);
            sim.setVisibility(View.GONE);
            openLicense.setVisibility(View.GONE);

            title_tv.setText(getString(R.string.sys_version));
        }
        if (type.equals("SIM")) {
            sim.setVisibility(View.VISIBLE);
            systemVersion.setVisibility(View.GONE);
            openLicense.setVisibility(View.GONE);

            title_tv.setText(getString(R.string.sys_sim));

            mPhoneManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
            mPhoneManager.listen(phoneStateListener, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
            phone_number.setText(getPhoneNumber(mPhoneManager));
            net.setText(getNet(mPhoneManager));
            imei.setText(getImei(mPhoneManager));
        }
        if (type.equals("open")) {
            openLicense.setVisibility(View.VISIBLE);
            sim.setVisibility(View.GONE);
            systemVersion.setVisibility(View.GONE);

            title_tv.setText(getString(R.string.sys_open_license));
        }
    }

    private String getPhoneNumber(TelephonyManager manager) {
        return manager.getLine1Number();
    }

    private String getNet(TelephonyManager manager) {
        return manager.getSimOperatorName();
    }

    private String getImei(TelephonyManager manager) {
        return manager.getDeviceId();
    }

    PhoneStateListener phoneStateListener = new PhoneStateListener() {
        @Override
        public void onSignalStrengthsChanged(SignalStrength signalStrength) {
            super.onSignalStrengthsChanged(signalStrength);
            Log.i("lte",signalStrength.toString()+ "");
            try {
                Method method;
                int strenth = 0;
                int networkType = mPhoneManager.getNetworkType();
                if (networkType == TelephonyManager.NETWORK_TYPE_LTE) {
                    method = signalStrength.getClass().getDeclaredMethod("getLteDbm");
                    int lteRsrp = (int) method.invoke(signalStrength);
                    if (lteRsrp > -44) signal.setText("UNKNOWN");
                    else if (lteRsrp >= -97) signal.setText("GREAT");
                    else if (lteRsrp >= -105) signal.setText("GOOD");
                    else if (lteRsrp >= -113) signal.setText("MODERATE");
                    else if (lteRsrp >= -120) signal.setText("POOR");
                    else if (lteRsrp >= -140) signal.setText("UNKNOWN");
                    Log.i("lte", lteRsrp + "");
                } else {
                    method = signalStrength.getClass().getDeclaredMethod("getLevel");
                    strenth = (int)method.invoke(signalStrength);
                    Log.i("非lte",strenth+"");
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    };
}
