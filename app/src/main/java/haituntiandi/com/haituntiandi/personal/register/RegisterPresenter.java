package haituntiandi.com.haituntiandi.personal.register;

import android.app.Activity;

public class RegisterPresenter implements RegisterContract.Presenter {
    private RegisterContract.View rootView;

    private Activity context;

    public RegisterPresenter(RegisterContract.View rootView) {
        this.rootView = rootView;
        this.context = rootView.getActivity();
    }
}
