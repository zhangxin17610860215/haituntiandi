package haituntiandi.com.haituntiandi.personal.register;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import haituntiandi.com.haituntiandi.R;
import haituntiandi.com.haituntiandi.base.BaseActivity;
import haituntiandi.com.haituntiandi.utils.FocusChangeListener;
import haituntiandi.com.haituntiandi.utils.LogUtil;
import haituntiandi.com.haituntiandi.utils.StatusBarUtils;

/**
 * 注册 ||  忘记密码
 */
public class RegisterActivity extends BaseActivity implements RegisterContract.View {

    /**
     * 输入手机号EditText
     * */
    @BindView(R.id.et_register_phoneNub)
    EditText etRegisterPhoneNub;
    /**
     * 输入短信验证码EditText
     * */
    @BindView(R.id.et_register_verificationCode)
    EditText etRegisterVerificationCode;
    /**
     * 获取短信验证码
     * */
    @BindView(R.id.tv_register_getVerificationCode)
    TextView tvRegisterGetVerificationCode;
    /**
     * 输入密码EditText
     * */
    @BindView(R.id.et_register_Password)
    EditText etRegisterPassword;
    /**
     * 再次输入密码EditText
     * */
    @BindView(R.id.et_register_againPassword)
    EditText etRegisterAgainPassword;
    /**
     * 标题
     * */
    @BindView(R.id.tv_register_title)
    TextView tvRegisterTitle;
    /**
     * 确认注册
     * */
    @BindView(R.id.tv_register_Determine)
    TextView tvRegisterDetermine;
    private RegisterPresenter presenter;

    private static String mType = "";

    /**
     * mType  1=注册页面   2=忘记密码页面
     * */
    public static void startActivity(Context context, String type) {
        Intent intent = new Intent(context, RegisterActivity.class);
        mType = type;
        context.startActivity(intent);
    }

    @Override
    public void initPresenter() {
        presenter = new RegisterPresenter(this);
    }

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public int bindLayout() {
        return R.layout.register_activity;
    }

    @Override
    public void initView(View view) {
        new StatusBarUtils(this).setColorBar(Color.parseColor("#FFFFFF"), 50);
        if (mType.equals("1")){
            tvRegisterTitle.setText("注册");
            tvRegisterDetermine.setText("确认注册");
        }else {
            tvRegisterTitle.setText("忘记密码");
            tvRegisterDetermine.setText("确认找回");
        }
    }

    @Override
    public void initData(Context mContext) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        etRegisterPhoneNub.setOnFocusChangeListener(new FocusChangeListener());
        etRegisterVerificationCode.setOnFocusChangeListener(new FocusChangeListener());
        etRegisterPassword.setOnFocusChangeListener(new FocusChangeListener());
        etRegisterAgainPassword.setOnFocusChangeListener(new FocusChangeListener());
    }

    @Override
    public Activity getActivity() {
        return null;
    }

    @OnClick({R.id.iv_register_back, R.id.tv_register_getVerificationCode, R.id.tv_register_Determine})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_register_back:
                //返回
                finish();
                break;
            case R.id.tv_register_getVerificationCode:
                //获取验证码
                break;
            case R.id.tv_register_Determine:
                //确认注册
                break;
        }
    }
}
