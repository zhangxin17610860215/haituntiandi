package haituntiandi.com.haituntiandi;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import butterknife.BindView;
import butterknife.OnClick;
import haituntiandi.com.haituntiandi.base.BaseActivity;
import haituntiandi.com.haituntiandi.pay.PayUtils;
import haituntiandi.com.haituntiandi.personal.PersonalActivity;
import haituntiandi.com.haituntiandi.utils.ShareUtil;
import haituntiandi.com.haituntiandi.utils.StatusBarUtils;

public class MainActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private SlidingMenu mSlidingMenu;

    @Override
    public void initPresenter() {

    }

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView(View view) {
        new StatusBarUtils(this).setColorBar(Color.parseColor("#00000000"), 50);
//        toolbar.setBackgroundColor(Color.parseColor("#df7fa2"));
        getJurisdiction();
        mSlidingMenu = new SlidingMenu(this);
        mSlidingMenu.setMode(SlidingMenu.LEFT);     //设置从左弹出/滑出SlidingMenu
        mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);   //设置占满屏幕
        mSlidingMenu.setShadowDrawable(R.drawable.shadow);
        mSlidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);    //绑定到哪一个Activity对象
        mSlidingMenu.setMenu(R.layout.activity_main_behind);                   //设置弹出的SlidingMenu的布局文件
        mSlidingMenu.setBehindOffsetRes(R.dimen.slidingmenu);       //设置SlidingMenu所占的偏移
    }

    /**
     * 动态获取权限
     * */
    private void getJurisdiction() {
        if(Build.VERSION.SDK_INT>=23){
            //其中123是requestcode，可以根据这个code判断，用户是否同意了授权。如果没有同意，可以根据回调进行相应处理：(onRequestPermissionsResult())
            String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.CALL_PHONE,Manifest.permission.READ_LOGS,Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.SET_DEBUG_APP,Manifest.permission.SYSTEM_ALERT_WINDOW,Manifest.permission.GET_ACCOUNTS,Manifest.permission.WRITE_APN_SETTINGS};
            ActivityCompat.requestPermissions(this,mPermissionList,123);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void initData(Context mContext) {

    }

    @OnClick({R.id.tv_geren, R.id.tv_wxPay, R.id.tv_AliPay, R.id.tv_WXLogin, R.id.tv_WXShare})
    public void onViewClicked(View view) {
        PayUtils payUtils = PayUtils.getInstance(this);
        switch (view.getId()) {
            case R.id.tv_geren:
                mSlidingMenu.toggle(true);
                break;
            case R.id.tv_wxPay:
                payUtils.pay(PayUtils.PAY_TYPE_WX, "");
                break;
            case R.id.tv_AliPay:
                payUtils.pay(PayUtils.PAY_TYPE_ALI, "");
                break;
            case R.id.tv_WXLogin:
//                startActivity(new Intent(this,PersonalActivity.class));
                UMShareAPI shareAPI = UMShareAPI.get(this);
                shareAPI.getPlatformInfo(this, SHARE_MEDIA.WEIXIN, new ShareUtil.MyUMAuthListener());//登录
//                shareAPI.deleteOauth(this, SHARE_MEDIA.QQ, new ShareUtil.MyUMAuthListener());//撤销授权
                break;
            case R.id.tv_WXShare:
                UMImage image = new UMImage(this, "http://app.chuxinketing.com/api/file/ic_sharelogo.png");//分享图标
                final UMWeb web = new UMWeb("https://developer.umeng.com/docs/66632/detail/66875?um_channel=sdk"); //切记切记 这里分享的链接必须是http开头
                web.setTitle("你要分享内容的标题");//标题
                web.setThumb(image);  //缩略图
                web.setDescription("你要分享内容的描述");//描述
                ShareUtil.getInstence(this).shareMethod(web);
                break;
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
}
