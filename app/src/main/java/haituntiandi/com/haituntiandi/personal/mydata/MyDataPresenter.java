package haituntiandi.com.haituntiandi.personal.mydata;

import android.app.Activity;

public class MyDataPresenter implements MyDataContract.Presenter {
    private MyDataContract.View rootView;

    private Activity context;

    public MyDataPresenter(MyDataContract.View rootView) {
        this.rootView = rootView;
        this.context = rootView.getActivity();
    }
}
