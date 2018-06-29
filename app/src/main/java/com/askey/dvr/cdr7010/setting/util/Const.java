package com.askey.dvr.cdr7010.setting.util;


import android.os.Environment;

/***
 * 常量配置类
 * Company: Chengdu Skysoft Info&Tech Co., Ltd.
 * Copyright ©2014-2018 成都天软信息技术有限公司
 * @since:JDK1.7
 * @version:1.0
 * @see
 * @author charles
 ***/
public class Const {

    /**
     * 是否是DEBUG模式
     */
    public static final boolean DEBUG = true;

    public static final String PLAY_BACK_PAKAGE = "com.askey.jvc.mediaplayer";

    public static final String OTA_PACKAGE_PATH = Environment.getExternalStorageDirectory() + "/update.zip";
    public static final String COMMAND_PATH = "/cache/recovery/command";

    public static final String DRIVING_SUPPORT = "driving_support";
    public static final String DVR_MAIN_CLASS = "com.askey.dvr.cdr7010.dashcam.ui.MainActivity";
    public static final String DVR_MAIN_PAKAGE = "com.askey.dvr.cdr7010.dashcam";

    public static final String ON = "ON";
    public static final String OFF = "OFF";
    public static  boolean SET_WIZARD = false;
    public static final String ACTION_MENU_TRANSITION = "android.intent.action.MENU_TRANSITION";
    public static final String ACTION_VERSIONUP_CHECK = "action_update_check";
}
