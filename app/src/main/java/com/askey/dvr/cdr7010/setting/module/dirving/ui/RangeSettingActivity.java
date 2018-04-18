package com.askey.dvr.cdr7010.setting.module.dirving.ui;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

import com.askey.dvr.cdr7010.setting.R;

import java.io.IOException;

public class RangeSettingActivity extends AppCompatActivity implements SurfaceHolder.Callback {

    private SurfaceView preview;
    private int previewHeight;
    private SurfaceHolder surfaceHolder;
    private View line;
    private Camera camera;
    private boolean cameraPermission = false;
    private boolean isPreviewing = false;

    private ViewGroup.MarginLayoutParams marginLayoutParams;
    private FrameLayout.LayoutParams layoutParams;
    private int lineCurrentMarginTop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_range_setting);

        preview = findViewById(R.id.preview);
        line = findViewById(R.id.line);
        marginLayoutParams = new ViewGroup.MarginLayoutParams(line.getLayoutParams());

        ViewTreeObserver treeObserver = preview.getViewTreeObserver();
        treeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                preview.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                previewHeight = preview.getHeight();
                Log.i("height", previewHeight+"");
            }
        });
        lineCurrentMarginTop = marginLayoutParams.topMargin;

        requestCameraPermission();

        surfaceHolder = preview.getHolder();
        surfaceHolder.addCallback(this);
    }

    private void requestCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            cameraPermission = true;
        } else {
            cameraPermission = false;
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                new AlertDialog.Builder(this)
                        .setMessage("This function need camera permission")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(RangeSettingActivity.this, new String[]{Manifest.permission.CAMERA}, 1);
                            }
                        })
                        .show();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_DOWN:
                Log.i("top_down",lineCurrentMarginTop+"");
                if (lineCurrentMarginTop < previewHeight-1) {
                    lineCurrentMarginTop += 1;
                    marginLayoutParams.setMargins(marginLayoutParams.leftMargin,lineCurrentMarginTop,marginLayoutParams.rightMargin,marginLayoutParams.bottomMargin);
                    layoutParams = new FrameLayout.LayoutParams(marginLayoutParams);
                    line.setLayoutParams(layoutParams);
                }
                break;
            case KeyEvent.KEYCODE_DPAD_UP:
                Log.i("top_up",lineCurrentMarginTop+"");
                if (lineCurrentMarginTop > 1) {
                    lineCurrentMarginTop -= 1;
                    marginLayoutParams.setMargins(marginLayoutParams.leftMargin,lineCurrentMarginTop,marginLayoutParams.rightMargin,marginLayoutParams.bottomMargin);
                    layoutParams = new FrameLayout.LayoutParams(marginLayoutParams);
                    line.setLayoutParams(layoutParams);
                }
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0) {
                cameraPermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                if (cameraPermission) {
                    startPreview();
                } else {
                    finish();
                }
            }
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!cameraPermission) {
            return;
        }
        startPreview();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (!cameraPermission) {
            return;
        }
        stopPreview();
    }

    private void startPreview() {
        if (null != camera || isPreviewing) {
            stopPreview();
        }
        try {
            camera = Camera.open();
            camera.setDisplayOrientation(0);
            camera.setPreviewDisplay(surfaceHolder);
            camera.startPreview();
            isPreviewing = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void stopPreview() {
        try {
            camera.setPreviewDisplay(null);
            camera.stopPreview();
            camera.release();
            isPreviewing = false;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
