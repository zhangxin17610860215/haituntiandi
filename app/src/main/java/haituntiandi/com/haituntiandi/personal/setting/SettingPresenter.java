package haituntiandi.com.haituntiandi.personal.setting;

import android.app.Activity;

public class SettingPresenter implements SettingContract.Presenter {
    private SettingContract.View rootView;

    private Activity context;

    public SettingPresenter(SettingContract.View rootView) {
        this.rootView = rootView;
        this.context = rootView.getActivity();
    }
}
