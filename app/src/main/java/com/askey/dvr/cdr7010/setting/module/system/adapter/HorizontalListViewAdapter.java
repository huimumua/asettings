package com.askey.dvr.cdr7010.setting.module.system.adapter;

/**
 * 项目名称：settings
 * 类描述：
 * 创建人：skysoft  charles.bai
 * 创建时间：2018/4/26 16:30
 * 修改人：skysoft
 * 修改时间：2018/4/26 16:30
 * 修改备注：
 */
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.askey.dvr.cdr7010.setting.R;
import com.askey.dvr.cdr7010.setting.module.system.bean.GpsSvInfo;
import com.askey.dvr.cdr7010.setting.util.Logg;

import java.util.ArrayList;

public class HorizontalListViewAdapter extends BaseAdapter{
    private ArrayList<GpsSvInfo> mGpsStatusList;
    private Context mContext;
    private LayoutInflater mInflater;
    private int selectIndex = -1;
    private int mGpsStatusListSize = 0;

    public int getmGpsStatusListSize() {
        return mGpsStatusListSize;
    }

    public void setmGpsStatusListSize(int mGpsStatusListSize) {
        this.mGpsStatusListSize = mGpsStatusListSize;
    }

    public HorizontalListViewAdapter(Context context, ArrayList<GpsSvInfo> gpsStatusList){
        this.mContext = context;
        this.mGpsStatusList = gpsStatusList;
        mInflater=(LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);//LayoutInflater.from(mContext);
    }
    @Override
    public int getCount() {
        if(mGpsStatusList==null || mGpsStatusList.size()==0 ){
            return 1;
        }
        return 9;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if(convertView==null){
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.horizontal_list_item, null);
            holder.mImage=(TextView)convertView.findViewById(R.id.gps_signal_strength);
            holder.mTitle=(TextView)convertView.findViewById(R.id.gps_satellite_number);
            holder.mValue=(TextView)convertView.findViewById(R.id.gps_signal_strength_value);
            convertView.setTag(holder);
        }else{
            holder=(ViewHolder)convertView.getTag();
        }

        if(position == selectIndex){
            convertView.setSelected(true);
        }else{
            convertView.setSelected(false);
        }

        LinearLayout.LayoutParams linearParams =(LinearLayout.LayoutParams) holder.mImage.getLayoutParams(); //取控件textView当前的布局参数


        if(position==0){
            holder.mTitle.setText("LV.");
            holder.mValue.setText("");
            holder.mValue.setBackgroundResource(R.drawable.icon_gpslevel);
            holder.mImage.setVisibility(View.INVISIBLE);
            holder.mImage.setText("");
        }else{

//            A: 以8星級來分: 3星以下(含3星) - 橘色
//            超過3星, 5星以下(含5星) - 黃色
//            超過5星 - 藍色
//            B: 以  % 來分:        38%以下(含38%) - 橘色
//            超過38%, 63%以下(含63%) - 黃色
//            超過63% - 藍色

            if(position <= getmGpsStatusListSize()){
                int prn = mGpsStatusList.get(position-1).getPrn();
                int snr = (int) mGpsStatusList.get(position-1).getSnr();
                Logg.i("SatelliteReceptionStatus","=====snr====="+snr + "====prn==="+prn);
                linearParams.height = snr;// 控件的高强制设成20
                holder.mImage.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
                if(snr>=0 && snr<=38){
                    holder.mImage.setBackgroundResource(R.drawable.icon_gpscolor_orange);
                }else if(snr>38 && snr<=63){
                    holder.mImage.setBackgroundResource(R.drawable.icon_gpscolor_yellow);
                }else if(snr>63){
                    holder.mImage.setBackgroundResource(R.drawable.icon_gpscolor_blue);
                }
                if(snr==0.0){
                    holder.mTitle.setText("");
                }else{
                    holder.mTitle.setText(snr+"");
                }
                if(prn==0){
                    holder.mValue.setText("");
                }else{
                    holder.mValue.setText(prn+"");
                }
                holder.mImage.setText("");
            }else{
                linearParams.height = 0;// 控件的高强制设成20
                holder.mImage.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
            }

        }

        return convertView;
    }

    private static class ViewHolder {
        private TextView mTitle ;
        private TextView mValue ;
        private TextView mImage;
    }

    public void setSelectIndex(int i){
        selectIndex = i;
    }
}