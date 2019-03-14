package haituntiandi.com.haituntiandi.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.View;

public class
ScreenUtils {
    public static void hideNavigationBar(Activity activity) {
        View decorView = activity.getWindow().getDecorView();
        int fullFlag = View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        decorView.setSystemUiVisibility(fullFlag);
    }

    public static void showNavigationBar(Activity activity) {
        int showFlag = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;

        int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        View decorView = activity.getWindow().getDecorView();
        decorView.setSystemUiVisibility(uiOptions);
    }

    public static int dp2Px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    private static int getHideFlag() {
        int apiVersion = Build.VERSION.SDK_INT;
        int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                | View.SYSTEM_UI_FLAG_IMMERSIVE;
        if (apiVersion < Build.VERSION_CODES.KITKAT) {
            flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        }
        return flags;
    }
}
