package haituntiandi.com.haituntiandi.utils;

import android.content.Context;
import android.widget.Toast;

import haituntiandi.com.haituntiandi.MyApplication;

/**
 * ToastUtil 工具类
 *
 * @author wangzl
 * @version 1.0
 */
public class ToastUtil {

    private static Toast mToast;

    /**
     * 显示简单文本信息
     *
     * @param msg
     */
    public static final void showMsg(final String msg) {
        if(StringUtil.isEmpty(msg)){
            return;
        }

        if (mToast == null) {

            mToast = Toast.makeText(MyApplication.getInstance(), msg, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(msg);
            mToast.setDuration(Toast.LENGTH_LONG);
        }

        mToast.show();
    }

    /**
     * 取消显示
     */
    public static final void cancelMsg() {

        if (mToast != null) {
            mToast.cancel();
        }
    }


    public static final void showMsg(Context context, final String msg) {
        if(StringUtil.isEmpty(msg)){
            return;
        }

        if (mToast == null) {

            mToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(msg);
            mToast.setDuration(Toast.LENGTH_LONG);
        }

        mToast.show();
    }
}
