package haituntiandi.com.haituntiandi.requestutils;

import android.content.Context;
import android.os.Environment;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.request.PostRequest;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import haituntiandi.com.haituntiandi.MyApplication;
import haituntiandi.com.haituntiandi.config.Constants;
import haituntiandi.com.haituntiandi.requestutils.api.ClientInfoBean;
import haituntiandi.com.haituntiandi.requestutils.callback.JsonCallback;
import haituntiandi.com.haituntiandi.utils.AESUtil;
import haituntiandi.com.haituntiandi.utils.AesUtilCommon;
import haituntiandi.com.haituntiandi.utils.Base64Utils;
import haituntiandi.com.haituntiandi.utils.LogUtil;
import haituntiandi.com.haituntiandi.utils.MD5Utils;
import haituntiandi.com.haituntiandi.utils.StringUtil;


/**
 * 网络请求的简单，统一处理
 */
public class RequestHelp {
    private static final String TAG = "RequestHelp";
    /**
     * 请求参数的base64串的key
     */
    private static final String DATA_PARAMETER_KEY = "data";
    /**
     * 请求参数的md5串的key
     */
    private static final String MD5_CODE_PARAMETER_KEY = "code";

    public static final String sKey = "!t@mc#mania%1111";

    /**
     * 加密开关配置规则：
     * 1.开发阶段，请求mock接口，将IS_ENCRYPTION直接设置为false；
     * 2.联调阶段，将IS_ENCRYPTION设置为true，但是IS_ENCRYPTION_BASE64设置为false；
     * 3.真实上线阶段，IS_ENCRYPTION和IS_ENCRYPTION_BASE64都设置为true。
     */
    public static final boolean IS_ENCRYPTION = false;
    public static final boolean IS_ENCRYPTION_BASE64 = false; // 是否做Base64加密

    public static final boolean IS_ENCRYPTION_AES = false; // 是否做AES加密


    /**
     * 客户端信息的token字符串，用于网络请求的通用参数
     */
    private static String sClientInfoData;

    public static <T> void getRequets(String url, Object tag, Map<String, String> map, JsonCallback<T> callback) {
        LogUtil.d("OkGoUtil", "method get");
        Map<String, String> fullParam = buildFullParam(map);
        OkGo.<T>get(url)
                .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)//设置缓存模式
                .cacheKey(url)//作为缓存的key
                .tag(tag)
                .params(fullParam)
                .execute(callback);
    }

    /**
     * 取消网络请求.如果tag为null，则取消所有的
     * @param tag
     */
    public static void cancelRequest(Object tag){
        if(null == tag){
            OkGo.getInstance().cancelAll();
        }else {
            OkGo.getInstance().cancelTag(tag);
        }
    }

    public static void postRequest(String url, Object tag, Map<String, String> map, StringCallback callback) {
        LogUtil.e("OkGoUtil", "method post");
//        Map<String, String> fullParam = new HashMap<>();
//        if (url.contains(API_UPDATEPACKAGE)) {
//            // 对于app升级接口请求，不加密
//            map = appendCommonParam(map);
//            String strParam = new Gson().toJson(map);
//            fullParam.put(DATA_PARAMETER_KEY, strParam);
//        } else{
//            fullParam = buildFullParam(map);
//        }
//        fullParam = buildFullParam(map);


        String key =url;

        PostRequest postRequest = OkGo.<String>post(url)
                .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)//设置缓存模式
                .cacheKey(key)//作为缓存的key
                .tag(tag)
                .params(map);
        postRequest.execute(callback);
    }

    /**
     * 用户头像图片上传
     * @param imgFullPath 图片文件全路径
     */
    public static void uploadUserHeadPhoto(String imgFullPath, Object tag, StringCallback callback) {
        if(StringUtil.isEmpty(imgFullPath)) {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                // 获取SD卡对应的存储目录
                imgFullPath = Environment.getExternalStorageDirectory().getAbsoluteFile() + "/at/test.jpg"; // for test
            }
        }
        if(!StringUtil.isEmpty(imgFullPath)) {
//            uploadFile(ApiUrl.API_USER_UPLOAD_HEAD_PHOTO, imgFullPath, tag, callback);
        }
    }

    /**
     * 上传文件
     *
     * @param url 服务器端的接口url
     * @param fileFullPath 文件全路径
     */
    private static void uploadFile(String url, String fileFullPath, Object tag, StringCallback callback) {
        HashMap map = new HashMap();
        Map<String, String> fullParam = buildFullParam(map);

//        OkGo.<String>post(url)
//                .tag(tag)
//                .headers("token", UserAccount.getmInstanse().isLogin()?UserAccount.getmInstanse().getUserBean().getToken():"")
//                .params(fullParam)  // 请求参数
//                .params("file", new File(fileFullPath))
//                .execute(callback);
    }

    /**
     * 将map转成Base64的串，增加"data" key
     *
     * @param map key-value的业务参数
     * @return 返回一个map，key为"data"，值为base64之后的值
     */
    private static Map<String, String> buildBase64Param(Map<String, String> map) {
        // 如果加密，则加上md5的=======================
        String strMd5;
        if(IS_ENCRYPTION){
            strMd5 = getMd5Code(map);

            // 在map中增加md5的key-value
            map.put(MD5_CODE_PARAMETER_KEY, strMd5);
        }
        // =========================================

        if (IS_ENCRYPTION_BASE64) {
            String strParam = new Gson().toJson(map);
            String strBase64 = null;
            if(IS_ENCRYPTION_AES) {
                strBase64 = getAesData(strParam); // 得到AES加密之后的内容
            }else{
                strBase64 = Base64Utils.encode(strParam);
            }

            ////////////
            HashMap<String, String> mapResult = new HashMap<>();
            mapResult.put(DATA_PARAMETER_KEY, strBase64);
            map = mapResult;
        }
        return map;
    }

    /**
     * AES 加密
     * @param strSourceData
     * @return aes之后，转base64后的字符串
     */
    private static String getAesData(String strSourceData){
        String password = sKey;
//        if (UserAccount.getmInstanse().isLogin()) {
//            password = UserAccount.getmInstanse().getUserBean().getToken().substring(64, 80);
//        }
//        String encData = AESUtil.encrypt(AESUtil.getRawKey(sKey.getBytes()), strSourceData);
        String encData = null;
        try {
            encData = AesUtilCommon.getInstance().encrypt(strSourceData, password);
        } catch (Exception e) {
            e.printStackTrace();
            encData = e.getMessage();
        }
        return encData;
    }


    /**
     * 增加通用请求参数
     *
     * @param map
     * @return
     */
    private static Map<String, String> appendCommonParam(Map<String, String> map) {
        map.put("token", sClientInfoData);
        return map;
    }

    /**
     * 生成md5校验码.先按key排序-》转成字符串-》生成md5
     * @param map
     * @return
     */
    private static String getMd5Code(Map<String, String> map) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : getSortedMapByKey(map).entrySet()) {
            if(null == entry.getValue()) // 如果是null，则跳过
                continue;

            sb.append(entry.getKey())
                    .append("=")
                    .append(String.valueOf(entry.getValue()))
                    .append("&");
        }
        sb.delete(sb.length() - 1, sb.length());

        if(IS_ENCRYPTION_AES) {
//            if (UserAccount.getmInstanse().isLogin()) {
//                sb.append(UserAccount.getmInstanse().getUserBean().getToken().substring(5, 21));
//            } else {
//                sb.append(sKey);
//            }
            sb.append(sKey);
        }
        String sign = MD5Utils.encode(sb.toString());
        return sign;
    }

    /**
     * 构造完整的请求参数，包括base64、md5
     *
     * @param map
     * @return
     */
    private static Map<String, String> buildFullParam(Map<String, String> map) {
        map = appendCommonParam(map);
        if (IS_ENCRYPTION) {
            Map<String, String> mapBase64 = buildBase64Param(map);
            map = mapBase64;
        }
        return map;

    }

    /**
     * 得到参数列表的json string
     * @param mapParam
     * @return
     */
    private static String getJsonString(Map<String, String> mapParam){
        StringBuilder sb = new StringBuilder("{");
//        for (Map.Entry<String, String> entry : mapParam.entrySet()) {
//            sb.append(entry.getKey()).append("=").append(String.valueOf(entry.getValue())).append("&");
//        }
        for (Map.Entry<String, String> entry : mapParam.entrySet()) {
            sb.append("\"").append(entry.getKey()).append("\":\"").append(String.valueOf(entry.getValue())).append("\",");
        }

        sb.delete(sb.length() - 1, sb.length()); // 去掉最后一个","
        sb.append("}");
        return sb.toString();
    }

    /**
     * 按照key的自然顺序进行排序，并返回
     * @param map
     * @return
     */
    private static Map<String, String> getSortedMapByKey(Map<String, String> map) {
        Comparator<String> comparator = new Comparator<String>() {
            @Override
            public int compare(String lhs, String rhs) {
                return lhs.compareTo(rhs);
            }
        };
        Map<String, String> treeMap = new TreeMap<>(comparator);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            treeMap.put(entry.getKey(), entry.getValue());
        }
        return treeMap;
    }

}
