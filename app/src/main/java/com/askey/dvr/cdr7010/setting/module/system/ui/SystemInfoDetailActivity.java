package com.askey.dvr.cdr7010.setting.module.system.ui;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.askey.dvr.cdr7010.setting.R;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SystemInfoDetailActivity extends AppCompatActivity {
    private String type;
    private ScrollView scrollView;
    private ImageView title_icon;
    private TextView title_tv, phone_number, net, signal, imei, service_status, version, network_type, network_status;
    private LinearLayout sim, openLicense;
    private RelativeLayout systemVersion;
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

        scrollView = (ScrollView) findViewById(R.id.scrollview);

        systemVersion = (RelativeLayout) findViewById(R.id.system_version);
        sim = (LinearLayout) findViewById(R.id.sim);
        openLicense = (LinearLayout) findViewById(R.id.open_license);

        phone_number = (TextView) findViewById(R.id.phone_number);
        net = (TextView) findViewById(R.id.net);
        signal = (TextView) findViewById(R.id.signal);
        imei = (TextView) findViewById(R.id.imei);
        service_status = (TextView) findViewById(R.id.service_status);
        network_type = (TextView) findViewById(R.id.network_type);
        network_status = (TextView) findViewById(R.id.network_status);

        title_tv = (TextView) findViewById(R.id.title_tv);
        title_icon = (ImageView) findViewById(R.id.title_icon);
    }

    private void loadInfo(String type) {
        if (type.equals("version")) {
            systemVersion.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.GONE);
            title_tv.setText(getString(R.string.sys_version));

            version = (TextView) findViewById(R.id.version);
            String systemVersion = Build.DISPLAY;
            version.setText(systemVersion);
        }
        if (type.equals("SIM")) {
            systemVersion.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);
            sim.setVisibility(View.VISIBLE);
            openLicense.setVisibility(View.GONE);

            title_tv.setText(getString(R.string.sys_sim));

            mPhoneManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
            mPhoneManager.listen(phoneStateListener, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
            phone_number.setText(getPhoneNumber(mPhoneManager));
            net.setText(getNet(mPhoneManager));
            imei.setText(getImei(mPhoneManager));
            network_type.setText(getNetworkType(mPhoneManager));
            network_status.setText(getNetworkStatus(this));
        }
        if (type.equals("open")) {
            systemVersion.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);
            sim.setVisibility(View.GONE);
            openLicense.setVisibility(View.VISIBLE);

            title_tv.setText(getString(R.string.sys_open_license));
        }
    }

    private String getPhoneNumber(TelephonyManager manager) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, "android.permission.READ_PHONE_NUMBERS") != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return "";
        }
        return manager.getLine1Number();
    }

    private String getNet(TelephonyManager manager) {
        return manager.getSimOperatorName();
    }

    private String getNetworkType(TelephonyManager manager) {
        String type = "";
        switch (manager.getNetworkType()) {
            case TelephonyManager.NETWORK_TYPE_HSDPA:
                type = "3G";
                break;
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                type = "4G";
                break;
            case TelephonyManager.NETWORK_TYPE_EDGE:
                type = "2G";
                break;
            case TelephonyManager.NETWORK_TYPE_LTE:
                type = "LTE";
                break;
            default:
                type = "N/A";
        }
        return type;
    }

//    private String getServiceStatus(TelephonyManager manager){
//        return
//    }

    private String getNetworkStatus(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if (isConnected) {
            return getString(R.string.net_conn);
        }
        return getString(R.string.net_not_conn);
    }

    private String getImei(TelephonyManager manager) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return "";
        }
        return manager.getDeviceId();
    }

    PhoneStateListener phoneStateListener = new PhoneStateListener() {
        @Override
        public void onSignalStrengthsChanged(SignalStrength signalStrength) {
            super.onSignalStrengthsChanged(signalStrength);
            Log.i("lte", signalStrength.toString() + "");
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
                    strenth = (int) method.invoke(signalStrength);
                    Log.i("非lte", strenth + "");
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
