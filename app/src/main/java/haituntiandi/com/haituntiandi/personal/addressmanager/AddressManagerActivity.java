package haituntiandi.com.haituntiandi.personal.addressmanager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import haituntiandi.com.haituntiandi.R;
import haituntiandi.com.haituntiandi.base.BaseActivity;
import haituntiandi.com.haituntiandi.personal.settingreceivingaddress.SettingReceivingAddressActivity;

/**
 * 地址信息管理
 */
public class AddressManagerActivity extends BaseActivity implements AddressManagerContract.View {

    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    private AddressManagerPresenter presenter;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, AddressManagerActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void initPresenter() {
        presenter = new AddressManagerPresenter(this);
    }

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public int bindLayout() {
        return R.layout.addressmanager_activity;
    }

    @Override
    public void initView(View view) {

    }

    @Override
    public void initData(Context mContext) {

    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @OnClick({R.id.iv_addressmanager_back, R.id.tv_addressmanager_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_addressmanager_back:
                finish();
                break;
            case R.id.tv_addressmanager_add:
                //新增地址信息
                SettingReceivingAddressActivity.startActivity(this);
                break;
        }
    }
}
