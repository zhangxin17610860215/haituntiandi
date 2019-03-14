package haituntiandi.com.haituntiandi.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkUtils {
	
    /**
     * 运营商名称
     * 1:移动
     * 2:联通
     * 3:电信
     * */
    public static final int OPERATOR_UNKNOWN        = 0;
    public static final int OPERATOR_CHINA_MOBILE   = 1;
    public static final int OPERATOR_CHINA_UNICOM   = 2;
    public static final int OPERATOR_CHINA_TELECOM  = 3;


    /**
     * 检测网络是否可用
     * @return
     */
    public static boolean isNetworkAvailable(Context context) {
        return getAvailableNetworkInfo(context) != null;
    }

    /**
     * 获取当前网络信息
     * @return
     */
    public static NetworkInfo getAvailableNetworkInfo(Context context) {
        NetworkInfo ni = null;
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(
                    Context.CONNECTIVITY_SERVICE);
            ni = cm.getActiveNetworkInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return (ni != null && ni.isAvailable() ? ni : null);
    }

    /**
     * 判断网络是否连接
     */
    public static boolean isNetworkConnected(Context context) {
        boolean result = false;
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivityManager != null) {
                NetworkInfo[] networkInfos = connectivityManager.getAllNetworkInfo();
                if (networkInfos != null) {
                    final int length = networkInfos.length;
                    for (int i = 0; i < length; i++) {
                        if (networkInfos[i].getState() == NetworkInfo.State.CONNECTED) {
                            return true;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * 获取手机wifi的Ip地址~
     *
     */
    public static String getIPAddress(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();
        // 格式化IP address，例如：格式化前：1828825280，格式化后：192.168.1.109
        return String.format("%d.%d.%d.%d", (ipAddress & 0xff), (ipAddress >> 8 & 0xff), (ipAddress >> 16 & 0xff), (ipAddress >> 24 & 0xff));
    }




    /**
     * 功能：检测当前URL是否可连接或是否有效,
     * 描述：如果连接不成功，视为该地址不可用
     * @param urlStr 指定URL网络地址
     * @return URL
     */
    public static boolean isURLConnect(String urlStr) {
        boolean isConnect = false;
        if (urlStr == null || urlStr.length() <= 0) {
            return isConnect;
        }
        try {
            URL url = new URL(urlStr);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.connect();
            int state = con.getResponseCode();
            if (state == 200) {
                isConnect = true;
            }else {
                isConnect = false;
            }

        }catch (Exception ex) {
            isConnect = false;
        }
        return isConnect;
    }

}
