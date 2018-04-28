package com.askey.dvr.cdr7010.setting.util;

/**
 * 项目名称：settings
 * 类描述：
 * 创建人：skysoft  charles.bai
 * 创建时间：2018/4/19 13:19
 * 修改人：skysoft
 * 修改时间：2018/4/19 13:19
 * 修改备注：
 */
public class SdcardUtil {

    static final String TAG = "SdcardUtil";

    public static boolean checkSdcardExist(){
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
        {
            Logg.i(TAG,"===checkSdcardExist==true=");
            return true;
        }
        return false;
    }

}
