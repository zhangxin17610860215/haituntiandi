package haituntiandi.com.haituntiandi.requestutils.api;

import com.google.gson.Gson;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.util.HashMap;
import java.util.Map;

import haituntiandi.com.haituntiandi.bean.BaseBean;
import haituntiandi.com.haituntiandi.bean.HomeListBean;
import haituntiandi.com.haituntiandi.bean.HttdSignBean;
import haituntiandi.com.haituntiandi.config.Constants;
import haituntiandi.com.haituntiandi.requestutils.RequestHelp;
import haituntiandi.com.haituntiandi.requestutils.requestCallback;
import haituntiandi.com.haituntiandi.utils.GsonHelper;
import haituntiandi.com.haituntiandi.utils.LogUtil;
import haituntiandi.com.haituntiandi.utils.encryptionutils.AesUtils;
import haituntiandi.com.haituntiandi.utils.encryptionutils.CrypticUtil;

import static haituntiandi.com.haituntiandi.config.Constants.ERROR_REQUEST_EXCEPTION_MESSAGE;
import static haituntiandi.com.haituntiandi.config.Constants.ERROR_REQUEST_FAILED_MESSAGE;

public class MyApi {

    private final static String TAG = "MyApi";

    /**
     * 获取HttdSignKey接口
     * */
    public static void getHttdSignKey(Object object, final requestCallback callback) {
        Map<String,String> map = new HashMap<>();
        RequestHelp.postRequest(ApiUrl.API_GETHTTDSIGNKEY, object, map, new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                LogUtil.e(TAG, "getHttdSignKey onSuccess" + response.body());
                try {
                    LogUtil.e(TAG, "getHttdSignKey onSuccess" + response.body());
                    BaseBean bean = GsonHelper.getSingleton().fromJson(response.body(), BaseBean.class);
                    String AesKey = CrypticUtil.RSADecrypt(bean.getEncrypted());
                    String decrypt = AesUtils.decrypt(bean.getRequestData(), AesKey);
                    HttdSignBean httdSignBean = GsonHelper.getSingleton().fromJson(decrypt, HttdSignBean.class);
                    callback.onSuccess(httdSignBean);
                } catch (Exception e) {
                    e.printStackTrace();
                    callback.onFailed(ERROR_REQUEST_EXCEPTION_MESSAGE);
                }
            }
            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                LogUtil.e(TAG, "getHttdSignKey onError。" + response.body());
            }
        });
    }

    /**
     * 获取首页List接口
     * */
    public static void getHomeData( Object object, final requestCallback callback) {
        Map<String, String> map = new HashMap<>();
        RequestHelp.postRequest(ApiUrl.API_GETHOMELIST, object, map, new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                try {
                    LogUtil.e(TAG, "getHomeData onSuccess" + response.body());
                    HomeListBean bean = GsonHelper.getSingleton().fromJson(response.body(), HomeListBean.class);
                    if (Constants.SUCCESS_CODE == bean.getCode()) {
                        callback.onSuccess(bean);
                    } else {
                        callback.onFailed( bean.getMessage());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    callback.onFailed(ERROR_REQUEST_EXCEPTION_MESSAGE);
                }
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                try {
                    LogUtil.e(TAG, "getHomeData onError。" + response.body());
                    callback.onFailed(ERROR_REQUEST_FAILED_MESSAGE);
                } catch (Exception e) {
                    e.printStackTrace();
                    callback.onFailed(ERROR_REQUEST_FAILED_MESSAGE);
                }
            }
        });
    }
    /**
     * 获取首页个人相关接口
     * */
    public static void getHomePersonalData(String currency, Object object, final requestCallback callback) {
        Map<String,String> map = new HashMap<>();
        RequestHelp.postRequest(ApiUrl.API_GETHTTDSIGNKEY, object, map, new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {

            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
            }
        });
    }
    /**
     * 获取商品列表
     * */
    public static void getCommodityList( Object object, final requestCallback callback) {
        Map<String,String> map = new HashMap<>();
        RequestHelp.postRequest(ApiUrl.API_GETCOMMODITYLIST, object, map, new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                LogUtil.e(TAG, "getCommodityList onSuccess" + response.body());
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                LogUtil.e(TAG, "getCommodityList onError。" + response.body());
            }
        });
    }
}
