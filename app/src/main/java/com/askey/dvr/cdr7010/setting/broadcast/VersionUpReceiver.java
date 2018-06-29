package com.askey.dvr.cdr7010.setting.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.askey.dvr.cdr7010.setting.util.Const;

public class VersionUpReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Const.ACTION_VERSIONUP_CHECK)) {
            switch (intent.getIntExtra("result", 0)) {
                case 0:
                    break;
                case -1:
                    break;
                case -2:
                    break;
                case -3:
                    break;
                case -4:
                    break;
            }
        }
    }
}
