package com.askey.dvr.cdr7010.setting;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.askey.dvr.cdr7010.setting.controller.FileManager;
import com.askey.dvr.cdr7010.setting.util.Const;

import java.util.HashMap;
import java.util.List;

public class MyAdapter extends BaseAdapter {
    private static final String TAG = "MyAdapter";
    private List<HashMap<String, Object>> data;
    private LayoutInflater mInflater = null;
    private Context context;
    private FileManager fileManager;
    private int sdCardStatus;
    private int layoutRes;

    public MyAdapter(Context context, List<HashMap<String, Object>> data, int layoutRes) {
        this.data = data;
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.layoutRes = layoutRes;
    }

    public void setFileManager(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = new ViewHolder();
        //根据自定义的Item布局加载布局
        convertView = mInflater.inflate(layoutRes, null);
//        convertView = mInflater.inflate(R.layout.system_settings_list_item, null);
        holder.item = (TextView) convertView.findViewById(R.id.list_item);

        String clickItem = data.get(position).get("menu_item").toString();
        Log.d("MyAdapter", clickItem);
        if (context.getString(R.string.sdcard_setting_initialization).equals(clickItem)) {
            if (null != fileManager) {
                sdCardStatus = fileManager.getSdcardStatus();
            }
            if (!(sdCardStatus == Const.SDCARD_NOT_SUPPORT || sdCardStatus == Const.SDCARD_UNRECOGNIZABLE || sdCardStatus == Const.SDCARD_NOT_EXIST) || sdCardStatus == Const.SDCARD_NOT_SUPPORT) {
                holder.item.setTextColor(0xffffffff);
            } else {
                holder.item.setTextColor(0xffa9a9a9);
            }
        }
        if (context.getString(R.string.sdcard_setting_information).equals(clickItem) || context.getString(R.string.main_menu_fp).equals(clickItem) || context.getString(R.string.tv_system_settings_system_update).equals(clickItem)) {
            Log.d("MyAdapter", "getView: ");
            if (null != fileManager) {
                sdCardStatus = fileManager.getSdcardStatus();
            }
            if (!(sdCardStatus == Const.SDCARD_NOT_SUPPORT || sdCardStatus == Const.SDCARD_UNRECOGNIZABLE || sdCardStatus == Const.SDCARD_NOT_EXIST)) {
                holder.item.setTextColor(0xffffffff);
            } else {
                holder.item.setTextColor(0xffa9a9a9);
            }
        }
        Log.d(TAG, "getView: " + sdCardStatus);
        holder.item.setText(clickItem);

        return convertView;
    }

    static class ViewHolder {
        public TextView item;
    }
}
