package com.askey.dvr.cdr7010.setting.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.askey.dvr.cdr7010.setting.R;

/**
 * 项目名称：settings
 * 类描述：
 * 创建人：skysoft  charles.bai
 * 创建时间：2018/4/8 11:17
 * 修改人：skysoft
 * 修改时间：2018/4/8 11:17
 * 修改备注：
 */
public class BaseActivity extends AppCompatActivity  {
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
        if((RadioButton) this.findViewById(R.id.center_btn)!=null){
            RadioButton topMenu = (RadioButton) this.findViewById(R.id.top_btn);
            RadioButton centerMenu = (RadioButton) this.findViewById(R.id.center_btn);
            RadioButton bottomMenu = (RadioButton) this.findViewById(R.id.bottom_btn);
            if(top){
                topMenu.setVisibility(View.VISIBLE);
            }else{
                topMenu.setVisibility(View.INVISIBLE);
            }
            if(middle){
                centerMenu.setVisibility(View.VISIBLE);
            }else{
                centerMenu.setVisibility(View.INVISIBLE);
            }
            if(bottom){
                bottomMenu.setVisibility(View.VISIBLE);
            }else{
                bottomMenu.setVisibility(View.INVISIBLE);
            }
        }
    }

    protected void setRightView(boolean top,int topButId,boolean middle,int middleButId,boolean bottom,int bottomButId){
        if((RadioButton) this.findViewById(R.id.center_btn)!=null){
            RadioButton topMenu = (RadioButton) this.findViewById(R.id.top_btn);
            RadioButton centerMenu = (RadioButton) this.findViewById(R.id.center_btn);
            RadioButton bottomMenu = (RadioButton) this.findViewById(R.id.bottom_btn);
            if(top){
                topMenu.setVisibility(View.VISIBLE);
                if(topButId != 0){
                    topMenu.setBackgroundResource(topButId);
                }
            }else{
                topMenu.setVisibility(View.GONE);
            }
            if(middle){
                if(middleButId != 0){
                    centerMenu.setBackgroundResource(middleButId);
                }
                centerMenu.setVisibility(View.VISIBLE);
            }else{
                centerMenu.setVisibility(View.GONE);
            }
            if(bottom){
                bottomMenu.setVisibility(View.VISIBLE);
                if(bottomButId != 0){
                    bottomMenu.setBackgroundResource(bottomButId);
                }
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
    protected void setBottomView(boolean visable,int resid){
        if((RadioButton) this.findViewById(R.id.back)!=null){
            RadioButton back_btn = (RadioButton) this.findViewById(R.id.back);
            if(visable){
                back_btn.setVisibility(View.VISIBLE);
            }else{
                back_btn.setVisibility(View.GONE);
            }
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
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        keydowmRepeatCount++;
        if(keydowmRepeatCount==1){
            onKeyHoldHalfASecond(keyCode);
        }else if(keydowmRepeatCount==2){
            onKeyHoldOneSecond(keyCode);
        }else if(keydowmRepeatCount==3){
            onKeyHoldThreeSecond(keyCode);
        }
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        event.startTracking();
        Log.d(TAG, "onKeyDown: ");
        if(keydowmRepeatCount==1){
            onContinueKeyHoldHalfASecond(keyCode);
        }else if(keydowmRepeatCount==2){
            onContinueKeyHoldOneSecond(keyCode);
        }else if(keydowmRepeatCount==3){
            onContinueKeyHoldThreeSecond(keyCode);
        }
        return true;
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        event.startTracking();
        Log.d(TAG, "onKeyUp: ");
//        if (keydowmRepeatCount==0) {
            onKeyShortPressed(keyCode);
//        }else{
            keydowmRepeatCount= 0;
//        }
        return super.onKeyUp(keyCode, event);
//        return true;
    }

    public void onKeyShortPressed(int keyCode) {

    }

    public  void onKeyHoldHalfASecond(int keyCode){

    }

    public  void onKeyHoldOneSecond(int keyCode){

    }

    public  void onKeyHoldThreeSecond(int keyCode){

    }

    private void onContinueKeyHoldHalfASecond(int keyCode) {

    }

    private void onContinueKeyHoldOneSecond(int keyCode) {

    }

    private void onContinueKeyHoldThreeSecond(int keyCode) {

    }






}
