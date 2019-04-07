package haituntiandi.com.haituntiandi.personal.mydata;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import haituntiandi.com.haituntiandi.R;
import haituntiandi.com.haituntiandi.base.BaseActivity;
import haituntiandi.com.haituntiandi.personal.addressmanager.AddressManagerActivity;

/**
 * 我的资料
 * */
public class MyDataActivity extends BaseActivity implements MyDataContract.View {

    /**
     * 头像
     * */
    @BindView(R.id.img_mydata_head)
    ImageView imgMydataHead;
    /**
     * 名称
     * */
    @BindView(R.id.img_mydata_name)
    TextView imgMydataName;
    /**
     * 微信是否绑定
     * */
    @BindView(R.id.img_mydata_WeiChat)
    TextView imgMydataWeiChat;
    private MyDataPresenter presenter;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, MyDataActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void initPresenter() {
        presenter = new MyDataPresenter(this);
    }

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public int bindLayout() {
        return R.layout.mydata_activity;
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

    @OnClick({R.id.iv_mydata_back, R.id.rl_myData_head, R.id.rl_myData_name, R.id.rl_myData_identity, R.id.rl_myData_address, R.id.rl_myData_invoice, R.id.rl_myData_ChangePassword, R.id.rl_myData_bindPhone, R.id.rl_myData_WeiChat})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_mydata_back:
                finish();
                break;
            case R.id.rl_myData_head:
                //更换头像
                break;
            case R.id.rl_myData_name:
                //设置昵称
                break;
            case R.id.rl_myData_identity:
                //身份信息管理
                break;
            case R.id.rl_myData_address:
                //地址信息管理
                AddressManagerActivity.startActivity(this);
                break;
            case R.id.rl_myData_invoice:
                //发票信息管理
                break;
            case R.id.rl_myData_ChangePassword:
                //修改密码
                break;
            case R.id.rl_myData_bindPhone:
                //绑定手机
                break;
            case R.id.rl_myData_WeiChat:
                //绑定微信
                break;
        }
    }
}
