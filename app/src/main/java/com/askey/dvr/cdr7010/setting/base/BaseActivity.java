package com.askey.dvr.cdr7010.setting.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.askey.dvr.cdr7010.setting.R;
import com.askey.dvr.cdr7010.setting.application.SettingApplication;
import com.askey.dvr.cdr7010.setting.util.Logg;

/**
 * 项目名称：settings
 * 类描述：
 * 创建人：skysoft  charles.bai
 * 创建时间：2018/4/8 11:17
 * 修改人：skysoft
 * 修改时间：2018/4/8 11:17
 * 修改备注：
 */
public class BaseActivity extends AppCompatActivity{
    private final  String  TAG = "BaseActivity";
    protected static Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;


    }

    protected void setTitleView(String title){
        if((TextView) this.findViewById(R.id.title_tv)!=null){
            TextView  menuTitle = (TextView) this.findViewById(R.id.title_tv);
            ImageView menuTitle_icon = (ImageView) this.findViewById(R.id.title_icon);//参数待添加
            menuTitle.setText(title);
        }
    }

    protected void setTitleView(String title,int imageId){
        if((TextView) this.findViewById(R.id.title_tv)!=null){
            TextView  menuTitle = (TextView) this.findViewById(R.id.title_tv);
            ImageView menuTitle_icon = (ImageView) this.findViewById(R.id.title_icon);//参数待添加
            menuTitle.setText(title);
            menuTitle_icon.setImageResource(imageId);
        }
    }

    protected void setRightView(boolean top,boolean middle,boolean bottom){
        if((ImageView) this.findViewById(R.id.center_btn)!=null){
            ImageView topMenu = (ImageView) this.findViewById(R.id.top_btn);
            ImageView centerMenu = (ImageView) this.findViewById(R.id.center_btn);
            ImageView bottomMenu = (ImageView) this.findViewById(R.id.bottom_btn);
            if(top){
                topMenu.setVisibility(View.VISIBLE);
            }else{
                topMenu.setVisibility(View.GONE);
            }
            if(middle){
                centerMenu.setVisibility(View.VISIBLE);
            }else{
                centerMenu.setVisibility(View.GONE);
            }
            if(bottom){
                bottomMenu.setVisibility(View.VISIBLE);
            }else{
                bottomMenu.setVisibility(View.GONE);
            }
        }
    }

    protected void setBottomView(int resid){
        if((RadioButton) this.findViewById(R.id.back)!=null){
            RadioButton back_btn = (RadioButton) this.findViewById(R.id.back);
            back_btn.setBackgroundResource(resid);
        }
    }

    protected void setBottomView(int resId,String title){
        if((RadioButton) this.findViewById(R.id.back)!=null){
            RadioButton back_btn = (RadioButton) this.findViewById(R.id.back);
            back_btn.setText(title);
            back_btn.setBackgroundResource(resId);
        }
    }

    private int keydowmRepeatCount =0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        keydowmRepeatCount = event.getRepeatCount();
        switch (event.getRepeatCount()){
            case 10:
                onKeyHoldHalfASecond(keyCode);
                break;
            case 20:
                onKeyHoldOneSecond(keyCode);
                break;
            case 60:
                onKeyHoldThreeSecond(keyCode);
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if(keydowmRepeatCount < 10){
            onKeyShortPressed(keyCode);
        }
        return super.onKeyUp(keyCode, event);
    }


    public void onKeyShortPressed(int keyCode) {

    }

    public  void onKeyHoldHalfASecond(int keyCode){

    }

    public  void onKeyHoldOneSecond(int keyCode){

    }

    public  void onKeyHoldThreeSecond(int keyCode){

    }


}
