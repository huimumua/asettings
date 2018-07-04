package com.askey.dvr.cdr7010.setting.module.system.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemProperties;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.CellInfo;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
import android.telephony.CellInfoWcdma;
import android.telephony.CellSignalStrengthGsm;
import android.telephony.CellSignalStrengthLte;
import android.telephony.CellSignalStrengthWcdma;
import android.telephony.ServiceState;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.askey.dvr.cdr7010.setting.R;
import com.askey.dvr.cdr7010.setting.util.Logg;
import com.askey.platform.AskeyTelephonyManager;

import java.io.File;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SystemInfoDetailActivity extends AppCompatActivity {
    private static final String TAG = "SystemInfoDetailActivity";
    private String type;
    private ScrollView scrollView;
    private ImageView title_icon;
    private TextView title_tv, phone_number, net, signal, imei, service_status, version, network_type, network_status;
    private LinearLayout sim, openLicense;
    private RelativeLayout systemVersion;
    private TelephonyManager mPhoneManager;
    private Timer timer;
    private static final String DEFAULT_LICENSE_PATH = "/system/etc/NOTICE.html.gz";
    private static final String PROPERTY_LICENSE_PATH = "ro.config.license_path";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_info_detail);
        mPhoneManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        type = getIntent().getStringExtra("info_type");
        timer = new Timer();
        initView();
        loadInfo(type);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
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

//            mPhoneManager.listen(phoneStateListener, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
            phone_number.setText(getPhoneNumber(mPhoneManager));
            net.setText(getNet(mPhoneManager));
            imei.setText(getImei(mPhoneManager));
            network_type.setText(getNetworkType(mPhoneManager));
            network_status.setText(getNetworkStatus(this));
            service_status.setText(getServiceStatus(mPhoneManager));
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            signal.setText(getSignalStrength(mPhoneManager));
                        }
                    });
                }
            },0,1000);
        }
        if (type.equals("open")) {
            systemVersion.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);
            sim.setVisibility(View.GONE);
            openLicense.setVisibility(View.VISIBLE);

            title_tv.setText(getString(R.string.sys_open_license));

            final String path = SystemProperties.get(PROPERTY_LICENSE_PATH, DEFAULT_LICENSE_PATH);
            if (TextUtils.isEmpty(path)) {
                Logg.e(TAG, "The system property for the license file is empty");
                showErrorAndFinish();
                return;
            }

            final File file = new File(path);
            if (!file.exists() || file.length() == 0) {
                Logg.e(TAG, "License file " + path + " does not exist");
                showErrorAndFinish();
                return;
            }

            // Kick off external viewer due to WebView security restrictions; we
            // carefully point it at HTMLViewer, since it offers to decompress
            // before viewing.
            final Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(file), "text/html");
//            intent.putExtra(Intent.EXTRA_TITLE, getString(R.string.settings_license_activity_title));
            intent.putExtra(Intent.EXTRA_TITLE, getString(R.string.sys_open_license));
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.setPackage("com.android.htmlviewer");

            try {
                startActivity(intent);
                finish();
            } catch (ActivityNotFoundException e) {
                Logg.e(TAG, "Failed to find viewer", e);
                showErrorAndFinish();
            }
        }
    }

    private String getPhoneNumber(TelephonyManager manager) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, "android.permission.READ_PHONE_NUMBERS") != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
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

    private String getServiceStatus(TelephonyManager manager) {
        String state = "N/A";
        switch (AskeyTelephonyManager.getServiceState(manager).getState()) {
            case ServiceState.STATE_IN_SERVICE:
                state = getString(R.string.sim_info_in_service);
                break;
            case ServiceState.STATE_OUT_OF_SERVICE:
                state = getString(R.string.sim_info_out_service);
                break;
        }
        return state;
    }

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
            return "";
        }
        return manager.getDeviceId();
    }

//    PhoneStateListener phoneStateListener = new PhoneStateListener() {
//        @Override
//        public void onSignalStrengthsChanged(SignalStrength signalStrength) {
//            super.onSignalStrengthsChanged(signalStrength);
//            Log.i("lte", signalStrength.toString() + "");
//            String[] params = signalStrength.toString().split(" ");
//            signal.setText(params[9] + " dBm");
//        }
//    };

    @SuppressLint("MissingPermission")
    public String getSignalStrength(TelephonyManager telephonyManager){
        List<CellInfo> cellInfos = telephonyManager.getAllCellInfo();
        String strength = " ";
        if(cellInfos!=null){
            for (int i = 0 ; i<cellInfos.size(); i++){
                if (cellInfos.get(i).isRegistered()){
                    if(cellInfos.get(i) instanceof CellInfoWcdma){
                        CellInfoWcdma cellInfoWcdma = (CellInfoWcdma) telephonyManager.getAllCellInfo().get(0);
                        CellSignalStrengthWcdma cellSignalStrengthWcdma = cellInfoWcdma.getCellSignalStrength();
                        strength = String.valueOf(cellSignalStrengthWcdma.getDbm()+" dBm "+cellSignalStrengthWcdma.getAsuLevel()+" asu");
                    }else if(cellInfos.get(i) instanceof CellInfoGsm){
                        CellInfoGsm cellInfogsm = (CellInfoGsm) telephonyManager.getAllCellInfo().get(0);
                        CellSignalStrengthGsm cellSignalStrengthGsm = cellInfogsm.getCellSignalStrength();
                        strength = String.valueOf(cellSignalStrengthGsm.getDbm()+" dBm "+cellSignalStrengthGsm.getAsuLevel()+" asu");
                    }else if(cellInfos.get(i) instanceof CellInfoLte){
                        CellInfoLte cellInfoLte = (CellInfoLte) telephonyManager.getAllCellInfo().get(0);
                        CellSignalStrengthLte cellSignalStrengthLte = cellInfoLte.getCellSignalStrength();
                        strength = String.valueOf(cellSignalStrengthLte.getDbm()+" dBm "+cellSignalStrengthLte.getAsuLevel()+" asu");
                    }
                }
            }
        }
        return strength;
    }

    private void showErrorAndFinish() {
        Toast.makeText(this, "settings_license_activity_unavailable", Toast.LENGTH_LONG)
                .show();// R.string.settings_license_activity_unavailable
        finish();
    }
}
