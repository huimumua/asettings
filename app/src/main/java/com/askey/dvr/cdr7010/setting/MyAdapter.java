package com.askey.dvr.cdr7010.setting;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

public class MyAdapter extends BaseAdapter {
    private List<HashMap<String, Object>> data;
    private LayoutInflater mInflater = null;
    private Context context;

    public MyAdapter(Context context, List<HashMap<String, Object>> data) {
        this.data = data;
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
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
        ViewHolder holder = null;
        //这里不缓存view的原因是在跳转上几页的时候会导致之前的item变灰
        //如果缓存convertView为空，则需要创建View
//        if(convertView == null)
//        {
            holder = new ViewHolder();
            //根据自定义的Item布局加载布局
            convertView = mInflater.inflate(R.layout.system_settings_list_item, null);
            holder.item = (TextView)convertView.findViewById(R.id.list_item);
            //将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
//            convertView.setTag(holder);
//        }else
//        {
//            holder = (ViewHolder)convertView.getTag();
//        }
        String clickItem = data.get(position).get("menu_item").toString();
        Log.d("MyAdapter", clickItem);
        if (context.getString(R.string.sdcard_setting_information).equals(clickItem) || context.getString(R.string.sdcard_setting_initialization).equals(clickItem) || context.getString(R.string.main_menu_fp).equals(clickItem)) {
            Log.d("MyAdapter", "getView: ");
            if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                holder.item.setTextColor(0xffa9a9a9);
            } else {
                holder.item.setTextColor(0xffffffff);
            }
        }
        holder.item.setText(clickItem);

        return convertView;
    }

    static class ViewHolder {
        public TextView item;
    }
}
