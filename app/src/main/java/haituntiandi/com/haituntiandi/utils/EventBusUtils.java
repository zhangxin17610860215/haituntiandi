package haituntiandi.com.haituntiandi.utils;

import android.content.Intent;
import android.os.Bundle;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by wangzl on 2017/12/27.
 * 该类是用于封装EventBus操作的工具类
 */

public class EventBusUtils {
    private static EventBus mEventBus;

    public static void init() {
        initInternal();
    }

    private static void initInternal() {
        mEventBus = EventBus.getDefault();
    }

    public static void register(Object subscriber) {
        mEventBus.register(subscriber);
    }

    public static void unregister(Object subscriber) {
        mEventBus.unregister(subscriber);
    }

    public static void post(int id) {
        post(new CommonEvent(id));
    }

    public static void post(int id, Bundle data) {
        post(new CommonEvent(id, data));
    }

    public static void post(CommonEvent event) {
        mEventBus.post(event);
    }

    public static void postSticky(Object event) {
        mEventBus.postSticky(event);
    }

    /**
     * 通用事件类型，如无特殊情况请使用该事件类型发送事件
     * {@link CommonEvent#id}事件类型标识
     * {@link CommonEvent#data}事件附加数据
     * <p>
     * 注意：不要使用data传递过大的数据量
     */
    public static class CommonEvent {
        public int id;
        public Bundle data;

        public Intent intent;

        public CommonEvent() {
        }

        public CommonEvent(int id) {
            this.id = id;
        }

        public CommonEvent(int id, Bundle data) {
            this.id = id;
            this.data = data;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setData(Bundle data) {
            this.data = data;
        }
    }

    /**
     * 公告信息所用事件类型
     * 因公告信息显示页面的注册时机可能晚于获取到公告的时间，需要使用粘性事件方式
     */
    public static class AppNoticeEvent {

    }

    /**
     * 事件类型标识码统一在此处添加
     */
    public static final int EVENT_CHANGE_GLOBAL_CURRENCY = 1001;//更换全局币种类型
    public static final int EVENT_CHANGE_GOLOAN = 1002;//跳转指定借贷页面
    public static final int EVENT_CHANGE_GOFINACIAL = 1003;//跳转指定理财页面
    public static final int EVENT_CHANGE_GOASSETS = 1004;//跳转指定钱包页面
    public static final int EVENT_CHANGE_GOPERSON = 1005;//跳转指定我的页面
    public static final int EVENT_CHANGE_GOOTCBUY = 1007;//跳转指定OTC买币页面
    public static final int EVENT_CHANGE_GOOTCSELLING = 1008;//跳转指定OTC卖币页面
    public static final int EVENT_CHANGE_GOFLICKER = 1009;//跳转指定闪兑页面

    public static final int EVENT_SET_GESTURE_SUCCESS = 1006;//设置手势密码成功
    public static final int EVENT_LOGIN_SUCCESS = 1009;//登录成功
    public static final int EVENT_QUICK_LOGIN_SUCCESS = 1010;//快速登录成功
    public static final int EVENT_REGISTE_SUCCESS = 1011;//注册成功

    public static final int EVENT_CHANGE_GO_SHAN_DUI = 1012;//跳转到闪兑页面

}
