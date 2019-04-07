package haituntiandi.com.haituntiandi.commodity.commoditylist;

import android.app.Activity;

public class CommodityListPresenter implements CommodityListContract.Presenter {
    private CommodityListContract.View rootView;

    private Activity context;

    public CommodityListPresenter(CommodityListContract.View rootView) {
        this.rootView = rootView;
        this.context = rootView.getActivity();
    }
}
