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

    public static final String DRIVING_SUPPORT = "driving_support";

    public static final String FRONT_COLLISION_WARING = "Front collision warning";
    public static final String LANE_DEPARTURE = "Lane departure warning";
    public static final String PEDESTRIAN_DETECTION = "Pedestrian detection";
    public static final String DEVICE_INSTALL_SETTING = "Device install setting";
    public static final String DEPARTURE_DELAY_WARNING = "Departure delay warning";
    public static final String DVR_MAIN_CLASS = "com.askey.dvr.cdr7010.dashcam.ui.MainActivity";
    public static final String DVR_MAIN_PAKAGE = "com.askey.dvr.cdr7010.dashcam";
    public static final String SETTTING_FIRST_INIT = "firstInit";
    public static final String REVERSE_RUN_DETECTION = "Reverse run detection";
    public static final String SPEED_REGULATION_AREA = "Speed regulation area";
    public static final String PAUSE = "Pause";
    public static final String ACCIDENT_FREQUENTLY_OCCURRING_AREA = "Accident frequently occurring area";
    public static final String DRIVING_TIME = "Driving time";
    public static final String RAPID_ACCELERATION_SUDDEN_DECELERATION = "Rapid acceleration, sudden deceleration";
    public static final String HANDLING = "Handling";
    public static final String FLUCTUATION_DETECTION = "Fluctuation detection";
    public static final String DRIVING_OUTSIDE_THE_DESIGNATED_AREA = "Driving outside the designated area";
    public static final String DRIVING_REPORT = "Driving report";
    public static final String ADVICE_BEFORE_DRIVING = "Advice before driving";
    public static final String NOTIFICATION = "Notification";
    public static final String WEATHER_INFORMATION = "Weather information";
    public static final String ROAD_KILL = "Road Kill";
    public static final String LOCATION_INFORMATION = "Location information";

    public static final String ON = "ON";
    public static final String OFF = "OFF";
}
