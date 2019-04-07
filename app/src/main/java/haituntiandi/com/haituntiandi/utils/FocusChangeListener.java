package haituntiandi.com.haituntiandi.utils;

import android.view.View;

import haituntiandi.com.haituntiandi.R;

/**
 * EditText有无焦点的监听器
 * */
public class FocusChangeListener implements View.OnFocusChangeListener {
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (v.hasFocus()){
            v.setBackgroundResource(R.drawable.blacklineframe_bg);
        }else {
            v.setBackgroundResource(R.drawable.huiframe_bg);
        }
    }
}
