package haituntiandi.com.haituntiandi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import butterknife.BindView;
import haituntiandi.com.haituntiandi.base.BaseActivity;
import haituntiandi.com.haituntiandi.bean.HttdSignBean;
import haituntiandi.com.haituntiandi.config.Constants;
import haituntiandi.com.haituntiandi.requestutils.api.MyApi;
import haituntiandi.com.haituntiandi.requestutils.requestCallback;

public class SplashActivity extends BaseActivity {

//    @BindView(R.id.splash_statePage_img)
//    ImageView splashImg;
    private AlphaAnimation animation;

    @Override
    public void initPresenter() {

    }

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_splash;
    }

    @Override
    public void initView(View view) {

    }

    @Override
    public void initData(Context mContext) {
        MyApi.getHttdSignKey(this, new requestCallback() {
            @Override
            public void onSuccess(Object object) {
                HttdSignBean bean = (HttdSignBean) object;
                Constants.HTTDSIGNKEY = bean.getData().getHttdSignKey();
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }

            @Override
            public void onFailed(String errMessage) {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    private void initAnimation() {
        animation = new AlphaAnimation(1.0f, 1.0f);
        animation.setDuration(2000);
//        splashImg.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

}
