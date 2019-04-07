package haituntiandi.com.haituntiandi.personal.login;

import android.app.Activity;

public class LoginPresenter implements LoginContract.Presenter {
    private LoginContract.View rootView;

    private Activity context;

    public LoginPresenter(LoginContract.View rootView) {
        this.rootView = rootView;
        this.context = rootView.getActivity();
    }
}
