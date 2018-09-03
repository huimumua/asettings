package com.askey.dvr.cdr7010.setting.module.system.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.telephony.CellInfo;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
import android.telephony.CellInfoWcdma;
import android.telephony.CellSignalStrengthGsm;
import android.telephony.CellSignalStrengthLte;
import android.telephony.CellSignalStrengthWcdma;
import android.telephony.TelephonyManager;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.askey.dvr.cdr7010.setting.R;
import com.askey.dvr.cdr7010.setting.base.BaseActivity;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.zip.GZIPInputStream;

public class SystemInfoDetailActivity extends BaseActivity {
    private static final String TAG = "SystemInfoDetailActivity";
    private String type;
    private ScrollView scrollView;
    private ImageView title_icon;
    private TextView title_tv, phone_number, net, signal, imei, service_status, version, network_type, network_status;
    private LinearLayout sim;
    private WebView mWebView;
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
        mWebView = (WebView) findViewById(R.id.open_license);

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
            mWebView.setVisibility(View.GONE);
            title_tv.setText(getString(R.string.sys_version) + " " + Build.DISPLAY);
            setRightView(false, 0, true, R.drawable.tag_menu_sub_ok, false, 0);
            version = (TextView) findViewById(R.id.version);
            String systemVersion = Build.DISPLAY;
            version.setText(systemVersion);
        }
        if (type.equals("SIM")) {
            systemVersion.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);
            sim.setVisibility(View.VISIBLE);
            mWebView.setVisibility(View.GONE);

            title_tv.setText(getString(R.string.sys_sim));

//            mPhoneManager.listen(phoneStateListener, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
            phone_number.setText(getPhoneNumber(mPhoneManager));
            net.setText(getNet(mPhoneManager));
            imei.setText(getImei(mPhoneManager));
            network_type.setText(getNetworkType(mPhoneManager));
            network_status.setText(getNetworkStatus(this));
            service_status.setText(getDataState(mPhoneManager));
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
            }, 0, 1000);
        }
        if (type.equals("open")) {

            setRightView(true, false, true);
            setBottomView(R.drawable.tag_menu_sub_cancel);

            systemVersion.setVisibility(View.GONE);
            scrollView.setVisibility(View.GONE);
            sim.setVisibility(View.GONE);
            mWebView.setVisibility(View.VISIBLE);

            title_tv.setText(getString(R.string.sys_open_license));

            mWebView.setWebChromeClient(new WebChromeClient());
            mWebView.setWebViewClient(new ViewClient());
            mWebView.setBackgroundColor(0x00000000);
            WebSettings webSettings = mWebView.getSettings();
            //支持javascript
            webSettings.setJavaScriptEnabled(true);
            webSettings.setUseWideViewPort(true);
            webSettings.setSupportZoom(true);
            webSettings.setBuiltInZoomControls(true);
            webSettings.setDisplayZoomControls(false);
            webSettings.setSavePassword(false);
            webSettings.setSaveFormData(false);
            webSettings.setBlockNetworkLoads(true);

            // Javascript is purposely disabled, so that nothing can be
            // automatically run.
            webSettings.setJavaScriptEnabled(false);
            webSettings.setDefaultTextEncodingName("utf-8");
            // 设置可以支持缩放
            requestPermissionAndLoad();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        // We only ever request 1 permission, so these arguments should always have the same form.
        if (permissions.length != 1) throw new AssertionError();
        if (!Manifest.permission.READ_EXTERNAL_STORAGE.equals(permissions[0]))
            throw new AssertionError();

        if (grantResults.length == 1 && PackageManager.PERMISSION_GRANTED == grantResults[0]) {
            // Try again now that we have the permission.
            mWebView.loadUrl("file:///system/etc/NOTICE.html.gz");
        } else {
            finish();
        }
    }

    private void requestPermissionAndLoad() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (PackageManager.PERMISSION_DENIED ==
                    checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
            } else {
                mWebView.loadUrl("file:///system/etc/NOTICE.html.gz");
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

    private String getDataState(TelephonyManager manager) {
        String state = getString(R.string.sim_info_out_service);
        switch (manager.getDataState()) {
            case TelephonyManager.DATA_CONNECTED:
                state = getString(R.string.sim_info_in_service);
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
    public String getSignalStrength(TelephonyManager telephonyManager) {
        List<CellInfo> cellInfos = telephonyManager.getAllCellInfo();
        String strength = " ";
        if (cellInfos != null) {
            for (int i = 0; i < cellInfos.size(); i++) {
                if (cellInfos.get(i).isRegistered()) {
                    if (cellInfos.get(i) instanceof CellInfoWcdma) {
                        CellInfoWcdma cellInfoWcdma = (CellInfoWcdma) telephonyManager.getAllCellInfo().get(0);
                        CellSignalStrengthWcdma cellSignalStrengthWcdma = cellInfoWcdma.getCellSignalStrength();
                        strength = String.valueOf(cellSignalStrengthWcdma.getDbm() + " dBm " + cellSignalStrengthWcdma.getAsuLevel() + " asu");
                    } else if (cellInfos.get(i) instanceof CellInfoGsm) {
                        CellInfoGsm cellInfogsm = (CellInfoGsm) telephonyManager.getAllCellInfo().get(0);
                        CellSignalStrengthGsm cellSignalStrengthGsm = cellInfogsm.getCellSignalStrength();
                        strength = String.valueOf(cellSignalStrengthGsm.getDbm() + " dBm " + cellSignalStrengthGsm.getAsuLevel() + " asu");
                    } else if (cellInfos.get(i) instanceof CellInfoLte) {
                        CellInfoLte cellInfoLte = (CellInfoLte) telephonyManager.getAllCellInfo().get(0);
                        CellSignalStrengthLte cellSignalStrengthLte = cellInfoLte.getCellSignalStrength();
                        strength = String.valueOf(cellSignalStrengthLte.getDbm() + " dBm " + cellSignalStrengthLte.getAsuLevel() + " asu");
                    }
                }
            }
        }
        return strength;
    }

    @Override
    public void onKeyShortPressed(int keyCode) {
        super.onKeyShortPressed(keyCode);
        switch (keyCode) {
            case KeyEvent.KEYCODE_ENTER:
                finish();
                break;
            case KeyEvent.KEYCODE_BACK:
                if (mWebView.canGoBack()) {
                    mWebView.goBack();
                }

                break;
        }
    }

    private class ViewClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {

        }

        @Override
        public boolean shouldOverrideKeyEvent(WebView view,
                                              KeyEvent event) {
            int keyCode = event.getKeyCode();
            if ((keyCode == KeyEvent.KEYCODE_DPAD_UP) || (keyCode == KeyEvent.KEYCODE_DPAD_DOWN)) {
                return false;
            }

            return super.shouldOverrideKeyEvent(view, event);
        }

        @Override
        public void onUnhandledKeyEvent(WebView view,
                                        KeyEvent event) {
            super.onUnhandledKeyEvent(view, event);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Intent intent;
            // Perform generic parsing of the URI to turn it into an Intent.
            try {
                intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
            } catch (URISyntaxException ex) {
                return true;
            }
            // Sanitize the Intent, ensuring web pages can not bypass browser
            // security (only access to BROWSABLE activities).
            intent.addCategory(Intent.CATEGORY_BROWSABLE);
            intent.setComponent(null);
            Intent selector = intent.getSelector();
            if (selector != null) {
                selector.addCategory(Intent.CATEGORY_BROWSABLE);
                selector.setComponent(null);
            }

            try {
                view.getContext().startActivity(intent);
            } catch (ActivityNotFoundException ex) {
//                Log.w(TAG, "No application can handle " + url);
            }
            return true;
        }

        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view,
                                                          WebResourceRequest request) {
            final Uri uri = request.getUrl();
            if (ContentResolver.SCHEME_FILE.equals(uri.getScheme())
                    && uri.getPath().endsWith(".gz")) {
//                Log.d(TAG, "Trying to decompress " + uri + " on the fly");
                try {
                    final InputStream in = new GZIPInputStream(
                            getContentResolver().openInputStream(uri));
                    final WebResourceResponse resp = new WebResourceResponse(
                            getIntent().getType(), "utf-8", in);
                    resp.setStatusCodeAndReasonPhrase(200, "OK");
                    return resp;
                } catch (IOException e) {
//                    Log.w(TAG, "Failed to decompress; falling back", e);
                }
            }
            return null;
        }
    }
}
