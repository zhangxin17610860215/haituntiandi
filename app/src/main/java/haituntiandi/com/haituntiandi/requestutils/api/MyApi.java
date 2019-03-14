package haituntiandi.com.haituntiandi.requestutils.api;

import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.util.HashMap;
import java.util.Map;

import haituntiandi.com.haituntiandi.config.Constants;
import haituntiandi.com.haituntiandi.requestutils.RequestHelp;
import haituntiandi.com.haituntiandi.requestutils.requestCallback;
import haituntiandi.com.haituntiandi.utils.GsonHelper;
import haituntiandi.com.haituntiandi.utils.LogUtil;
import haituntiandi.com.haituntiandi.utils.StringUtil;

import static haituntiandi.com.haituntiandi.config.Constants.ERROR_REQUEST_EXCEPTION;
import static haituntiandi.com.haituntiandi.config.Constants.ERROR_REQUEST_EXCEPTION_MESSAGE;
import static haituntiandi.com.haituntiandi.config.Constants.ERROR_REQUEST_FAILED;
import static haituntiandi.com.haituntiandi.config.Constants.ERROR_REQUEST_FAILED_MESSAGE;

public class MyApi {

    private final static String TAG = "MyApi";

    public static void getHuoQiProfit(String currency, Object object, final requestCallback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("type","top");
        map.put("key","9edd8a27f78be6235f319be7cfd68c41");
        RequestHelp.postRequest(ApiUrl.API_GETHUOQIPROFIT, object, map, new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                try {
                    LogUtil.e(TAG, "测试接口 onSuccess" + response.body());
//                    HuoQiProfitBean bean = GsonHelper.getSingleton().fromJson(response.body(), HuoQiProfitBean.class);
//                    if (StringUtil.isNotEmpty(bean.getResCode())) {
//                        if (Constants.SUCCESS_CODE.equals(bean.getResCode())) {
//                            callback.onSuccess(bean);
//                        } else {
//                            callback.onFailed(bean.getErrorCode(), bean.getErrorMsg());
//                        }
//                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    callback.onFailed(ERROR_REQUEST_EXCEPTION, ERROR_REQUEST_EXCEPTION_MESSAGE);
                }
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                try {
                    LogUtil.e(TAG, "onError。" + response);
                    callback.onFailed(ERROR_REQUEST_FAILED, ERROR_REQUEST_FAILED_MESSAGE);
                } catch (Exception e) {
                    e.printStackTrace();
                    callback.onFailed(ERROR_REQUEST_FAILED, ERROR_REQUEST_FAILED_MESSAGE);
                }
            }
        });
    }
}
