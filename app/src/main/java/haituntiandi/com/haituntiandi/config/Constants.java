package haituntiandi.com.haituntiandi.config;

import java.util.HashMap;

import haituntiandi.com.haituntiandi.utils.EventBusUtils;

public class Constants {
    // DEBUG模式。影响log级别输出
    public static boolean DEBUG = false;//BuildConfig.DEBUG;

//    // 加密开关
//    public static final boolean ENABLE_ENCRYPT = false;

    public static Integer GOOTCSELLING = EventBusUtils.EVENT_CHANGE_GOOTCBUY;

    public static final String ERROR_REQUEST_FAILED = "-1"; // 网络请求失败，出现onerror
    public static final String ERROR_REQUEST_EXCEPTION = "-2"; // 网络请求失败，出现异常

    public static final String ERROR_REQUEST_FAILED_MESSAGE = "网络请求失败";//REQUEST_FAILED"; // 网络请求失败，出现onerror
    public static final String ERROR_REQUEST_EXCEPTION_MESSAGE = "服务返回数据异常";//REQUEST_EXCEPTION"; // 网络请求数据异常

    //订单状态 10.待支付  11.取消  12.支付确认中  20.完成
    public static final HashMap<String, String> ORDER_STATUS_TYPE = new HashMap<>();

    static {
        ORDER_STATUS_TYPE.put("10", "待支付");
        ORDER_STATUS_TYPE.put("11", "取消");
        ORDER_STATUS_TYPE.put("12", "支付确认中");
        ORDER_STATUS_TYPE.put("20", "完成");

    }

    /**
     * 发短信时的业务类型
     */
    public class SMS_BUSINESS_TYPE {
        public static final String USER_REGIST = "1";            // 1注册
        public static final String UPDATE_LOGIN_PASSWORD = "2";  // 2修改密码
        public static final String UPDATE_MOBILE = "4";          // 4修改登录手机号
        public static final String UPDATE_NEW_MOBILE = "9";          // 9 更换为新的手机号
        public static final String USER_FORGET_LOGIN_PWD = "10";          //  10 忘记密码
        public static final String USER_KUAI_SU_LOGIN = "11";          //  11 快速登录
        public static final String UPDATE_PAY_PWD = "12";          //  12 修改支付密码
    }

}
