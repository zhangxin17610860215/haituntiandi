package haituntiandi.com.haituntiandi.personal.setting;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import butterknife.OnClick;
import haituntiandi.com.haituntiandi.R;
import haituntiandi.com.haituntiandi.base.BaseActivity;

public class SettingActivity extends BaseActivity implements SettingContract.View {

    private SettingPresenter presenter;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, SettingActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void initPresenter() {
        presenter = new SettingPresenter(this);
    }

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public int bindLayout() {
        return R.layout.setting_activity;
    }

    @Override
    public void initView(View view) {

    }

    @Override
    public void initData(Context mContext) {

    }

    @Override
    public Activity getActivity() {
        return null;
    }

    @OnClick({R.id.iv_settimg_back, R.id.rl_setting_aboutUs, R.id.tv_setting_Logout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_settimg_back:
                finish();
                break;
            case R.id.rl_setting_aboutUs:
                //关于我们
                break;
            case R.id.tv_setting_Logout:
                //退出登录
                break;
        }
    }
}
