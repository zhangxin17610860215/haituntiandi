package haituntiandi.com.haituntiandi.personal.settingreceivingaddress;

import android.app.Activity;

public class SettingReceivingAddressPresenter implements SettingReceivingAddressContract.Presenter {
    private SettingReceivingAddressContract.View rootView;

    private Activity context;

    public SettingReceivingAddressPresenter(SettingReceivingAddressContract.View rootView) {
        this.rootView = rootView;
        this.context = rootView.getActivity();
    }
}
