package haituntiandi.com.haituntiandi.personal.addressmanager;

import android.app.Activity;

public class AddressManagerPresenter implements AddressManagerContract.Presenter {
    private AddressManagerContract.View rootView;

    private Activity context;

    public AddressManagerPresenter(AddressManagerContract.View rootView) {
        this.rootView = rootView;
        this.context = rootView.getActivity();
    }
}
