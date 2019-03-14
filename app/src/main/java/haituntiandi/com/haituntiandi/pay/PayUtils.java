package haituntiandi.com.haituntiandi.pay;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.alipay.sdk.app.PayTask;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.Map;

import haituntiandi.com.haituntiandi.MyApplication;
import haituntiandi.com.haituntiandi.utils.LogUtil;
import haituntiandi.com.haituntiandi.utils.ToastUtil;

public class PayUtils {
    private static String TAG = PayUtils.class.getSimpleName();
    /**
     * 支付类型
     */
    public static final int PAY_TYPE_WX = 1;
    public static final int PAY_TYPE_ALI = 2;
    /**
     * 支付宝返回参数
     */
    final static int SDK_PAY_FLAG = 1001;

    private static PayUtils instance;
    private static Context mContext;
    private static Activity mActivity;

    private PayUtils() {
    }

    public static PayUtils getInstance(Context context) {
        if (instance == null) {
            instance = new PayUtils();
        }
        mContext = context;
        mActivity = (Activity) mContext;
        return instance;
    }

    /**
     * @param result 接口调试成功后换成bean对象
     */
    public static void pay(int payType, String result) {
        switch (payType) {
            case PAY_TYPE_WX:
                //微信
                toWXPay(result);
                break;
            //
            case PAY_TYPE_ALI:
                toAliPay(result);
                break;
        }
    }


    /**
     * 微信支付
     *
     * @param result
     */
    private static void toWXPay(String result) {
        //result中是重服务器请求到的签名后各个字符串，可自定义
        //result是服务器返回结果
        PayReq request = new PayReq();
        request.appId = MyApplication.WXAPP_ID;
        request.partnerId = "";
        request.prepayId = "";
        request.packageValue = "";
        request.nonceStr = "";
        request.timeStamp = "";
//        request.timeStamp = System.currentTimeMillis() / 1000 + "";
        request.sign = "";
        boolean send = getWxapi().sendReq(request);
    }

    private static IWXAPI getWxapi() {
        IWXAPI wxapi = WXAPIFactory.createWXAPI(mActivity, MyApplication.WXAPP_ID);  //应用ID 即微信开放平台审核通过的应用APPID
        wxapi.registerApp(MyApplication.WXAPP_ID);    //应用ID
        return wxapi;
    }


    /**
     * 支付宝
     */
    private static void toAliPay(String result) {
        //result中是在服务器请求到的签名后字符串,赋值，此处随便写的
        final String orderInfo = result;   // 订单信息
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(mActivity);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
                LogUtil.e(TAG, "aliresult--->" + result);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     * 支付宝状态
     * 9000 订单支付成功
     * 8000 正在处理中，支付结果未知（有可能已经支付成功），请查询商户订单列表中订单的支付状态
     * 4000 订单支付失败
     * 5000 重复请求
     * 6001 用户中途取消
     * 6002 网络连接出错
     * 6004 支付结果未知（有可能已经支付成功），请查询商户订单列表中订单的支付状态
     * 其它   其它支付错误
     */
    @SuppressLint("HandlerLeak")
    private static Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        showMessage("支付成功");
                        PayListenerUtils.getInstance(mContext).addSuccess();
                    } else if (TextUtils.equals(resultStatus, "6001")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        showMessage("取消");
                        PayListenerUtils.getInstance(mContext).addCancel();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        showMessage("支付失败");
                        PayListenerUtils.getInstance(mContext).addError();
                    }
                    break;
                }
            }
        }

    };

    private static void showMessage(String str) {
        ToastUtil.showMsg(str);
    }

    public static class PayResult {
        private String resultStatus;
        private String result;
        private String memo;

        public PayResult(Map<String, String> rawResult) {
            if (rawResult == null) {
                return;
            }

            for (String key : rawResult.keySet()) {
                if (TextUtils.equals(key, "resultStatus")) {
                    resultStatus = rawResult.get(key);
                } else if (TextUtils.equals(key, "result")) {
                    result = rawResult.get(key);
                } else if (TextUtils.equals(key, "memo")) {
                    memo = rawResult.get(key);
                }
            }
        }

        @Override
        public String toString() {
            return "resultStatus={" + resultStatus + "};memo={" + memo
                    + "};result={" + result + "}";
        }

        /**
         * @return the result
         **/
        public String getResultStatus() {
            return resultStatus;
        }

        /**
         * @return the memo
         **/
        public String getMemo() {
            return memo;
        }

        /**
         * @return the result
         */
        public String getResult() {
            return result;
        }
    }
}
