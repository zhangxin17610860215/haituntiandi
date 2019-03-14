package haituntiandi.com.haituntiandi.utils;

import android.app.Activity;
import android.content.Context;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;

import java.util.Map;

public class ShareUtil {
    private static Activity activity;
    public static ShareUtil getInstence(Context context){
        activity = (Activity) context;
        return new ShareUtil();
    }
    public void shareMethod(final UMWeb web){
        new ShareAction(activity)
                .setDisplayList(SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                .setShareboardclickCallback(new ShareBoardlistener() {
                    @Override
                    public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
                        if (share_media == SHARE_MEDIA.QQ) {
                            LogUtil.e("Share","点击QQ");
                            new ShareAction(activity).setPlatform(SHARE_MEDIA.QQ)
                                    .withMedia(web)
                                    .setCallback(new MyUMShareListener())
                                    .share();
                        } else if (share_media == SHARE_MEDIA.WEIXIN) {
                            LogUtil.e("Share","点击微信");
                            new ShareAction(activity).setPlatform(SHARE_MEDIA.WEIXIN)
                                    .withMedia(web)
                                    .setCallback(new MyUMShareListener())
                                    .share();
                        } else if (share_media == SHARE_MEDIA.QZONE) {
                            new ShareAction(activity).setPlatform(SHARE_MEDIA.QZONE)
                                    .withMedia(web)
                                    .setCallback(new MyUMShareListener())
                                    .share();
                        } else if (share_media == SHARE_MEDIA.WEIXIN_CIRCLE) {
                            new ShareAction(activity).setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                                    .withMedia(web)
                                    .setCallback(new MyUMShareListener())
                                    .share();
                        }
                    }
                }).open();
    }
    public static class MyUMShareListener implements UMShareListener {

        @Override
        public void onStart(SHARE_MEDIA share_media) {
            //分享开始的回调
            ToastUtil.showMsg("开始分享");
        }

        @Override
        public void onResult(SHARE_MEDIA share_media) {
            ToastUtil.showMsg("分享成功");
        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
            ToastUtil.showMsg("分享失败");
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
            ToastUtil.showMsg("分享取消");
        }
    }

    public static class MyUMAuthListener implements UMAuthListener {

        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        @Override
        public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> data) {
                LogUtil.e("Auth","openid: " + data.get("uid"));
                LogUtil.e("Auth","昵称: " + data.get("name"));
                LogUtil.e("Auth","头像: " + data.get("iconurl"));
                LogUtil.e("Auth","性别: " + data.get("gender"));
        }

        @Override
        public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                LogUtil.e("Auth","登陆失败");
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media, int i) {
            LogUtil.e("Auth","登录取消");
        }
    }
}
