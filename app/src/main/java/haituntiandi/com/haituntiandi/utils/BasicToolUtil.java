package haituntiandi.com.haituntiandi.utils;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.os.StatFs;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BasicToolUtil {
    /**
     * 拷贝单个文件，不支持拷贝文件夹
     *
     * @param srcPath
     * @param destPath
     * @return
     */
    public static boolean copyFile(String srcPath, String destPath) {
        InputStream inStream = null;
        FileOutputStream outStream = null;
        try {
            if (StringUtil.isEmpty(srcPath) || StringUtil.isEmpty(destPath)) {
                return false;
            }
            File srcFile = new File(srcPath);
            File destFile = new File(destPath);
            if (!srcFile.exists() || srcFile.isDirectory()) {
                return false;
            }
            if (destFile.exists()) {
                if (destFile.isDirectory()) {
                    return false;
                }
                destFile.deleteOnExit();
            }
            inStream = new FileInputStream(srcFile);
            outStream = new FileOutputStream(destFile);
            byte[] buffer = new byte[2048];
            int read = 0;
            while (-1 != (read = inStream.read(buffer))) {
                outStream.write(buffer, 0, read);
            }
            return true;
        } catch (Exception e) {
        } finally {
            if (null != inStream) {
                try {
                    inStream.close();
                } catch (Exception e2) {
                }
            }
            if (null != outStream) {
                try {
                    outStream.close();
                } catch (Exception e2) {
                }
            }
        }
        return false;
    }

    public static String getSDCardDir(String subFile, Context appContext) {
        if (StringUtil.isEmpty(subFile)) {
            return null;
        }

        if (null == appContext) {
            return null;
        }
        try {
            String state = Environment.getExternalStorageState();
            if (StringUtil.isNotEmpty(state) && state.equals(Environment.MEDIA_MOUNTED)) {
                String extDir = Environment.getExternalStorageDirectory() + File.separator + appContext.getPackageName() + File.separator + subFile;
                File extFile = new File(extDir);
                if (!extFile.exists()) {
                    extFile.mkdirs();
                } else if (extFile.isFile()) {
                    extFile.deleteOnExit();
                    new File(extDir).mkdirs();
                }
                return extDir;
            }
        } catch (Exception e) {
        }

        return null;
    }

    public static boolean getSDCardState() {
        String state = Environment.getExternalStorageState();
        if (StringUtil.isNotEmpty(state) && state.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    public static String getCustomDate(long ctime) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        if (System.currentTimeMillis() - ctime <= 24 * 60 * 60 * 1000) {
            Date today = new Date(System.currentTimeMillis());
            Date date = new Date(ctime);
            if (today.getDate() == date.getDate()) {
                return "今天  " + sdf.format(date);
            } else {
                return "昨天";
            }
        } else if (System.currentTimeMillis() - ctime <= 2 * 24 * 60 * 60 * 1000 && System.currentTimeMillis() - ctime > 24 * 60 * 60 * 1000) {
            return "昨天";
        } else if (System.currentTimeMillis() - ctime <= 3 * 24 * 60 * 60 * 1000 && System.currentTimeMillis() - ctime > 2 * 24 * 60 * 60 * 1000) {
            return "两天前";
        } else if (System.currentTimeMillis() - ctime <= 4 * 24 * 60 * 60 * 1000 && System.currentTimeMillis() - ctime > 3 * 24 * 60 * 60 * 1000) {
            return "三天前";
        } else if (System.currentTimeMillis() - ctime <= 5 * 24 * 60 * 60 * 1000 && System.currentTimeMillis() - ctime > 4 * 24 * 60 * 60 * 1000) {
            return "四天前";
        } else if (System.currentTimeMillis() - ctime <= 6 * 24 * 60 * 60 * 1000 && System.currentTimeMillis() - ctime > 5 * 24 * 60 * 60 * 1000) {
            return "五天前";
        } else if (System.currentTimeMillis() - ctime <= 7 * 24 * 60 * 60 * 1000 && System.currentTimeMillis() - ctime > 6 * 24 * 60 * 60 * 1000) {
            return "六天前";
        } else {
            return "一周以前";
        }
    }

    public static String getSDCardDir(Context appContext) {
        try {
            if (null != appContext) {
                String state = Environment.getExternalStorageState();
                if (StringUtil.isNotEmpty(state) && state.equals(Environment.MEDIA_MOUNTED)) {
                    String extDir = Environment.getExternalStorageDirectory() + File.separator + appContext.getPackageName();
                    File extFile = new File(extDir);
                    if (!extFile.exists()) {
                        extFile.mkdirs();
                    } else if (extFile.isFile()) {
                        extFile.deleteOnExit();
                        new File(extDir).mkdirs();
                    }
                    return extDir;
                }
            }
        } catch (Exception e) {
        }

        return null;
    }

    public static String getTimeLabel() {
        Calendar calendar = Calendar.getInstance();
        return "__W" + calendar.get(Calendar.DAY_OF_WEEK) + "__H" + calendar.get(Calendar.HOUR_OF_DAY);
    }

    public static String getHourLabel() {
        Calendar calendar = Calendar.getInstance();
        return "__H" + calendar.get(Calendar.HOUR_OF_DAY);
    }

    public static boolean isActivityShowing(Context context, String activityName) {
        if (null == context) {
            return false;
        }
        try {
            ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            List<RunningTaskInfo> list = am.getRunningTasks(1);
            if (list == null) {
                return true;
            }
            for (RunningTaskInfo info : list) {
                if (StringUtil.isEquals(activityName, info.topActivity.getClassName())) {
                    return true;
                }
            }
        } catch (Exception e) {
        }
        return false;
    }

    public static boolean isActivityAtTop(Context context) {
        if (null == context) {
            return false;
        }
        try {
            ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            List<RunningTaskInfo> list = am.getRunningTasks(1);
            if (list == null) {
                return true;
            }
            for (RunningTaskInfo info : list) {
                if (context.getPackageName().equals(info.topActivity.getPackageName())) {
                    return true;
                }
            }
        } catch (Exception e) {
        }
        return false;
    }

    public static boolean isConnectingToInternet(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean isWifiState(Context context) {
        try {
            ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (null != connectivity) {
                NetworkInfo info = connectivity.getActiveNetworkInfo();
                if (info != null) {
                    return info.getType() == ConnectivityManager.TYPE_WIFI || info.getType() == AndroidUtil.ConnectivityManager.TYPE_ETHERNET
                            || info.getType() == ConnectivityManager.TYPE_WIMAX;
                }
            }
        } catch (Exception e) {
        }
        return false;
    }

    /**
     * @param boundary
     * @return random integer between 0 and boundary.
     */
    public static double random(long boundary) {
        return Math.random() * boundary;
    }

    public static boolean randomEvent() {
        long event = Math.round(Math.random());
        return 1 == event;
    }

    public static int randomForward() {
        int event = (int) Math.round(Math.random() * 2);
        switch (event) {
            case 0:
                return -1;
            case 2:
                return 1;
            case 1:
            default:
                return 0;
        }
    }

    public static String getCpuInfo() {
        FileReader fr;
        String[] array = null;
        BufferedReader br;
        try {
            fr = new FileReader("/proc/cpuinfo");
            br = new BufferedReader(fr);
            String text = br.readLine();
            br.close();
            array = text.split(":\\s+", 2);
            return array[1];
        } catch (Exception e) {
            e.printStackTrace();
        }
        return " ";
    }

    public static String getTotalMemory() {
        FileReader fr;
        String[] array = null;
        BufferedReader br;
        try {
            fr = new FileReader("/proc/meminfo");
            br = new BufferedReader(fr);
            String text = br.readLine();
            br.close();
            array = text.split(":\\s+");
            return array[1];
        } catch (Exception e) {
            e.printStackTrace();
        }
        return " ";
    }

    public static String getTotalRomMemroy() {
        StatFs stat = new StatFs(Environment.getDataDirectory().getPath());
        return stat.getBlockCount() * stat.getBlockSize() / 1024 / 1024 + "MB";
    }

    public static String getAvailableRomMemroy() {
        StatFs stat = new StatFs(Environment.getDataDirectory().getPath());
        return stat.getBlockSize() * stat.getAvailableBlocks() / 1024 / 1024 + "MB";
    }

    /**
     * 得到当前手机号码
     *
     * @return 本机手机号码（仅部分能获取到）
     */
    public static String getPhoneNum(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return "nopermission";
        }
        String num = tm.getLine1Number();
        if (StringUtil.isEmpty(num) || num.length() <= 3) {
            return "";
        }
        if (num.substring(0, 3).equals("+86")) {
            num = num.substring(3);
        }
        return num;
    }

    /**
     * 格式化当前手机号码
     *
     * @return 去除空格换行符的字符串
     */
    public static String FomatPhoneNum(String num) {
        String dest = "";
        if (num != null) {
            if (StringUtil.isEmpty(num) || num.length() <= 3) {
                return "";
            }
            if (num.substring(0, 3).equals("+86")) {
                num = num.substring(3);
            }
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(num);
            dest = m.replaceAll("");
        }
        return dest;
    }

    // 判断email格式是否正确
    public static boolean checkEmailaddress(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);

        return m.matches();
    }

    // 判断是否是小米miui系统
    public static boolean getIsMiuiSystem() {
        String propName = "ro.miui.ui.version.name";
        String line;
        BufferedReader input = null;
        try {
            Process p = Runtime.getRuntime().exec("getprop " + propName);
            input = new BufferedReader(new InputStreamReader(p.getInputStream()), 1024);
            line = input.readLine();
            input.close();
        } catch (IOException ex) {
            return false;
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                }
            }
        }
        return (null == line || line.length() == 0) ? false : true;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int getdip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static float sp2px(Context context, float sp) {
        final float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return sp * scale;
    }

    /**
     * Format a time in millisecond, result is in hh:mm:ss format.
     *
     * @param millis
     * @return hh:mm:ss
     */
    public static String formatTimeMillis(long millis) {
        String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
        return hms;
    }

    public static String formatMacAdress(String macAdress) {
        if (StringUtil.isEmpty(macAdress)) return "";
        macAdress = macAdress.toUpperCase();
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < macAdress.length()-1; i = i + 2) {
            buffer.append(macAdress.substring(i, i + 2)).append("-");
        }
        macAdress = buffer.toString();
        return macAdress.substring(0,macAdress.length()-1);
    }

    /**
     * 将时间戳转换为指定格式(format)的字符串
     *
     * @param format
     * @param millis
     * @return
     */
    public static String formatTimeMillisToDate(String format, long millis) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        Date date = new Date(millis);
        return formatter.format(date);
    }

    /**
     * Format net traffic in byte, result is in GB, MB or KB.
     *
     * @param traffic
     * @return GB, MB or KB
     */
    public static String formatNetworkTraffic(long traffic) {
        long b = traffic / 8;
        if (b <= 0) return "0Kb";
        final String[] units = new String[]{"b", "Kb", "Mb", "Gb", "Tb"};
        int digitGroups = (int) (Math.log10(traffic) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(b / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }

    /**
     * 隐藏软键盘
     *
     * @param activity
     */
    public static void hideKeyBoard(Activity activity) {
        if (activity != null && activity.getWindow() != null && activity.getWindow().getAttributes() != null) {
            if (activity.getCurrentFocus() != null && activity.getCurrentFocus().getWindowToken() != null) {
                ((InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(activity
                        .getCurrentFocus()
                        .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }


    /**
     * 将指定日期 从 templateFrom 格式转换为 templateTo 格式。
     *
     * @param dateStr      字符串类型的日期
     * @param templateFrom 指定日期的模板(默认为)"yyyy年MM月dd日"
     * @param templateTo   转换成的模板
     * @return 转换成templateTo格式的模板后的字符串
     */
    public static String transDate(String dateStr, String templateFrom, String templateTo) {
        if (StringUtil.isEmpty(templateFrom)) {
            templateFrom = "yyyy年MM月dd日";
        }
        SimpleDateFormat sdfFrom = new SimpleDateFormat(templateFrom, Locale.getDefault());
        SimpleDateFormat sdfTo = new SimpleDateFormat(templateTo, Locale.getDefault());
        Date date = new Date();
        try {
            date = sdfFrom.parse(dateStr);
        } catch (Exception e) {

        }
        return sdfTo.format(date);
    }

    /**
     * 获取虚拟功能键高度
     */
    public static int getVirtualBarHeigh(Context context) {
        int vh = 0;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        try {
            @SuppressWarnings("rawtypes")
            Class c = Class.forName("android.view.Display");
            @SuppressWarnings("unchecked")
            Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, dm);
            vh = dm.heightPixels - windowManager.getDefaultDisplay().getHeight();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vh;
    }

    /*
    * 根据包名判断是否安装
    */
    public static boolean isAppInstalled(Context context, String packagename) {
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(packagename, 0);
        } catch (PackageManager.NameNotFoundException e) {
            packageInfo = null;
            e.printStackTrace();
        }
        if (packageInfo == null) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 截取当前view生成的图片，并保存到本地，返回保存的图片地址
     *
     * @return
     */
    public static String screenShotByView(View view) {
        view.setDrawingCacheEnabled(true);
        String path = null;
        try {
            File myCaptureFile = new File(PhotoCaptureUtil.getFileDir(), "pic_" + String.valueOf(System.currentTimeMillis()) +
                    ".jpg");
            BufferedOutputStream bos = new BufferedOutputStream(
                    new FileOutputStream(myCaptureFile));
            view.buildDrawingCache();
            view.getDrawingCache()
                    .compress(Bitmap.CompressFormat.JPEG, 80, bos);
            bos.flush();
            bos.close();
            path = myCaptureFile.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
        }
        view.setDrawingCacheEnabled(false);
        return path;
    }

    public static String transfer(int num) {
        try {
            if (num < 10000) {
                return num + "";
            } else {
                float ten_thousand = (float) num / 10000;
                DecimalFormat df = new DecimalFormat("####.0");
                return df.format(Double.parseDouble(ten_thousand + "")) + "万";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return num + "";
        }
    }


    /**
     * 获取ip地址
     *
     * @return
     */
    public static String getHostIP() {
        String hostIp = null;
        try {
            Enumeration nis = NetworkInterface.getNetworkInterfaces();
            InetAddress ia = null;
            while (nis.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) nis.nextElement();
                Enumeration<InetAddress> ias = ni.getInetAddresses();
                while (ias.hasMoreElements()) {
                    ia = ias.nextElement();
                    if (ia instanceof Inet6Address) {
                        continue;// skip ipv6
                    }
                    String ip = ia.getHostAddress();
                    if (!"127.0.0.1".equals(ip)) {
                        hostIp = ia.getHostAddress();
                        break;
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return hostIp;

    }

}
