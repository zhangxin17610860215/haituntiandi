package haituntiandi.com.haituntiandi.personal.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import haituntiandi.com.haituntiandi.R;
import haituntiandi.com.haituntiandi.base.BaseActivity;
import haituntiandi.com.haituntiandi.utils.FocusChangeListener;
import haituntiandi.com.haituntiandi.utils.StatusBarUtils;

/**
 * 登录
 */
public class LoginActivity extends BaseActivity implements LoginContract.View {

    /**
     * 登录账号EditText
     * */
    @BindView(R.id.et_login_phoneNub)
    EditText etLoginPhoneNub;
    /**
     * 登录密码EditText
     * */
    @BindView(R.id.et_login_Password)
    EditText etLoginPassword;
    /**
     * 确认登录
     * */
    @BindView(R.id.tv_login_Determine)
    TextView tvLoginDetermine;
    private LoginPresenter presenter;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void initPresenter() {
        presenter = new LoginPresenter(this);
    }

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public int bindLayout() {
        return R.layout.login_activity;
    }

    @Override
    public void initView(View view) {
        new StatusBarUtils(this).setColorBar(Color.parseColor("#FFFFFF"), 50);
    }

    @Override
    protected void onResume() {
        super.onResume();
        etLoginPhoneNub.setOnFocusChangeListener(new FocusChangeListener());
        etLoginPassword.setOnFocusChangeListener(new FocusChangeListener());
    }

    @Override
    public void initData(Context mContext) {

    }

    @Override
    public Activity getActivity() {
        return null;
    }

    @OnClick({R.id.iv_login_back, R.id.tv_login_Determine})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_login_back:
                finish();
                break;
            case R.id.tv_login_Determine:
                //确认登录
                break;
        }
    }
}
