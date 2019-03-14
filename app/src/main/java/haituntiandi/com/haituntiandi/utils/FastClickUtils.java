package haituntiandi.com.haituntiandi.utils;

import static java.lang.Thread.sleep;

/**
 * @author wangzl
 */
public class FastClickUtils {
    public static final int DELAY = 2000;
    private static long lastClickTime = 0;

    public static boolean isNotFastClick() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastClickTime > DELAY) {
            lastClickTime = currentTime;
            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return true;
        } else {
            return false;
        }
    }
}
