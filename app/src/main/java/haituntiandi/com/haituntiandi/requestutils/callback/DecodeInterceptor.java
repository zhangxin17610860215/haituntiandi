package haituntiandi.com.haituntiandi.requestutils.callback;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import haituntiandi.com.haituntiandi.MyApplication;
import haituntiandi.com.haituntiandi.requestutils.RequestHelp;
import haituntiandi.com.haituntiandi.utils.AesUtilCommon;
import haituntiandi.com.haituntiandi.utils.Base64Utils;
import haituntiandi.com.haituntiandi.utils.FileUtils;
import haituntiandi.com.haituntiandi.utils.LogUtil;
import haituntiandi.com.haituntiandi.utils.TimeUtils;

//import static haituntiandi.com.haituntiandi.config.Constants.ENABLE_REQUEST_TIME_WRITE;
//import static haituntiandi.com.haituntiandi.config.Constants.EXIT_APP_ACTION;
import static haituntiandi.com.haituntiandi.utils.TimeUtils.TIME_TYPE_06;

/**
 * Create by hsxiao. 2018/8/10
 */
public class DecodeInterceptor implements Interceptor {
    private static final Charset UTF8 = Charset.forName("UTF-8");

    /**
     * 告警时间阈值(毫秒), 请求时长超过该值时，特殊显示
     */
    private static final long WARN_REQUEST_TIME_MS = 2000;
    /**
     * 网络请求耗时记录文件名。在SD卡上
     */
    private static final String REQUEST_TIME_RECORDER_FILE_NAME = "tamc/request.txt";

    private File mFile = null;
    PackageManager mPkgManager = MyApplication.getInstance().getPackageManager();

    public DecodeInterceptor() {
        // 判断外置SD卡是否挂载
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            String path = Environment.getExternalStorageDirectory().getAbsolutePath();
            mFile = new File(path, REQUEST_TIME_RECORDER_FILE_NAME);
        }
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        String url = String.valueOf(request.url());

        long beginRequestTime = System.currentTimeMillis();
//        LogUtil.d(DecodeInterceptor.class.getSimpleName(), url + "请求开始的时间" + TimeUtils.getDateToString(beginRequestTime, TIME_TYPE_06));

        Response originalResponse = null;// = chain.proceed(request);
        try {
            originalResponse = chain.proceed(request);
        } catch (Exception e) {
            LogUtil.e("DecodeInterceptor", "<-- HTTP FAILED: " + e);
            throw e;
        }
        if (null != originalResponse) {
            ResponseBody responseBody = originalResponse.body();
            BufferedSource source = responseBody.source();
            source.request(Long.MAX_VALUE); // Buffer the entire body.
            Buffer buffer = source.buffer();
            Charset charset = UTF8;
            MediaType contentType = responseBody.contentType();
            if (contentType != null) {
                charset = contentType.charset(UTF8);
            }
            String bodyString = buffer.clone().readString(charset);
            if (null != bodyString && bodyString.startsWith("\"")) {
                bodyString = bodyString.replace("\"", ""); // 某些接口返回收尾会多个双引号，去掉。
//                LogUtil.w("response", "\n多了引号，url : \n" + url);
            }
            String decodeString = bodyString;
//            if (!url.contains(API_UPDATEPACKAGE)) { // 对于升级接口，不需要解密。非上级接口，都是需要解密
//                decodeString = decodeResponseData(bodyString);
//            }
            // 对返回数据进行校验，看是否是已被"踢出"登录
            verifyKickOut(decodeString);

            ResponseBody responseBodyDecode = ResponseBody.create(contentType, decodeString);

            // 如果允许保存请求时长，则写到文件
//            if (ENABLE_REQUEST_TIME_WRITE) {
//                writeRequest(url, beginRequestTime);
//            }

            return originalResponse.newBuilder().body(responseBodyDecode).build();
        } else {
            return null;
        }
    }

    /**
     * 解密返回的数据
     *
     * @param sourceData
     */
    private String decodeResponseData(String sourceData) {
        String decodeString;
        if (RequestHelp.IS_ENCRYPTION_AES) {
            String password = RequestHelp.sKey;

//            if (UserAccount.getmInstanse().isLogin()) {
//                password = UserAccount.getmInstanse().getUserBean().getToken().substring(64, 80);
//            }
            try {
                decodeString = AesUtilCommon.getInstance().decrypt(sourceData, password);
            } catch (Exception e) {
                e.printStackTrace();
                LogUtil.e(DecodeInterceptor.class.getSimpleName(), "decodeResponseData error, " + e.getMessage());
                decodeString = e.getMessage();
            }
        } else {
            // 未启用AES，仅进行Base64解密
            if (RequestHelp.IS_ENCRYPTION_BASE64) {
                decodeString = Base64Utils.decode(sourceData);

            } else {
                decodeString = sourceData;
            }
        }
        return decodeString;
    }

    /**
     * 保存请求耗时到文件
     *
     * @param requestUrl       请求的url
     * @param beginRequestTime 请求开始时间
     */
    private void writeRequest(String requestUrl, long beginRequestTime) {
        if (null == mFile) {
            return;
        }

        boolean sdCardWritePermission =
                mPkgManager.checkPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        MyApplication.getInstance().getPackageName()) == PackageManager.PERMISSION_GRANTED;

        if(!sdCardWritePermission){
            return;
        }

//        if(mFile.exists() && mFile.getFile.renameTo()) // TODO 待增加检测文件大小逻辑

        long responseTime = System.currentTimeMillis();
//        LogUtil.d("DecodeInterceptor", requestUrl + "响应时间" + TimeUtils.getDateToString(responseTime, TIME_TYPE_06));

        StringBuilder sbContent = new StringBuilder(requestUrl);
        sbContent.append("\n请求时间: ")
                .append(TimeUtils.getDateToString(beginRequestTime, TIME_TYPE_06))
                .append("\n请求耗时: ");
        long timeMs = (responseTime - beginRequestTime);

        sbContent.append(timeMs);
        if (timeMs > WARN_REQUEST_TIME_MS) {
            sbContent.append("\n====================== \n");
        }
        sbContent.append("\n");
        FileUtils.writeStringToFile(mFile, sbContent.toString(), true);
    }

    /**
     * 检测是否被踢出登录
     */
    private void verifyKickOut(String responseData) {
//        try {
//            BaseBean baseBean = GsonHelper.getSingleton().fromJson(responseData, BaseBean.class);
//            if (!SUCCESS_CODE.equals(baseBean.getResCode())) {
//                String errorCode = baseBean.getErrorCode();
//                if (!StringUtil.isEmpty(errorCode)) {
//                    if (errorCode.startsWith("900")) {
//                        // 如果error code 是900开头，认为当前用户已经被踢出登录
//                        UserAccount.getmInstanse().setUserBean(null);
//                        String msg = baseBean.getErrorMsg();
//                        LogUtil.w("DecodeInterceptor", "当前用户被踢出登录。" + errorCode + "," + baseBean.getErrorMsg());
//                        sendExitReceive();
//                        Intent mIntent = new Intent(TamcApplication.getInstance(), LoginOrRegistEntranceActivity.class);
//                        mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//                        TamcApplication.getInstance().startActivity(mIntent);
//                        showDialog(msg);
//                    }
//                }
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            return;
//        }
    }

    private void sendExitReceive() {
        Intent intent = new Intent();
//        intent.setAction(EXIT_APP_ACTION);
        MyApplication.getInstance().sendBroadcast(intent);
    }

    private void showDialog(String msg) {
//        Intent intent = new Intent(MyApplication.getInstance(), AlertActivity.class);
//        intent.putExtra("msg", msg);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        MyApplication.getInstance().startActivity(intent);
    }
}
