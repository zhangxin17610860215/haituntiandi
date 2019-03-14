package haituntiandi.com.haituntiandi.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import haituntiandi.com.haituntiandi.MyApplication;

public abstract class BaseFragment extends Fragment implements View.OnClickListener {
    private boolean isDebug;
    private String APP_NAME;
    protected final String TAG = this.getClass().getSimpleName();
    private View mContextView = null;

    /**
     * Fragment当前状态是否可见
     */
    protected boolean isVisible = false;
    /**
     * Fragment的view是否已创建
     */
    protected boolean mIsViewCreated = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isDebug = MyApplication.isDebug;
        APP_NAME = MyApplication.APP_NAME;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContextView = inflater.inflate(bindLayout(), container, false);
        mIsViewCreated = true;
        initView(mContextView);
        initData(getActivity());
        return mContextView;
    }

    /**
     * [绑定布局]
     *
     * @return
     */
    public abstract int bindLayout();

    /**
     * [初始化控件]
     *
     * @param view
     */
    public abstract void initView(final View view);

    /**
     * [业务操作]
     *
     * @param mContext
     */
    public abstract void initData(Context mContext);

    /** View点击 **/
    public abstract void widgetClick(View v);

    @Override
    public void onClick(View v) {
        if (fastClick())
            widgetClick(v);
    }

    @SuppressWarnings("unchecked")
    public <T extends View> T $(View view, int resId) {
        return (T) view.findViewById(resId);
    }

    /**
     * [防止快速点击]
     *
     * @return
     */
    private boolean fastClick() {
        long lastClick = 0;
        if (System.currentTimeMillis() - lastClick <= 1000) {
            return false;
        }
        lastClick = System.currentTimeMillis();
        return true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mIsViewCreated = false;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!mIsViewCreated)//view没有创建的时候不进行操作
            return;

        if (getUserVisibleHint()) {
            if (!isVisible) {//确保在一个可见周期中只调用一次onVisible()
                isVisible = true;
                onVisible();
            }
        } else {
            if (isVisible) {
                isVisible = false;
                onHidden();
            }
        }
    }

    /**
     * 可见
     */
    protected void onVisible() {

    }

    /**
     * fragment不可见的时候操作,onPause的时候,以及不可见的时候调用
     */
    protected void onHidden() {

    }

    @Override
    public void onResume() {
        //和activity的onResume绑定，Fragment初始化的时候必调用，但切换fragment的hide和visible的时候可能不会调用！
        super.onResume();
        if (isAdded() && !isHidden()) {//用isVisible此时为false，因为mView.getWindowToken为null
            onVisible();
            isVisible = true;
        }
    }

    @Override
    public void onPause() {
        if (isVisible()||isVisible) {
            onHidden();
            isVisible = false;
        }
        super.onPause();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        //默认fragment创建的时候是可见的，但是不会调用该方法！切换可见状态的时候会调用，但是调用onResume，onPause的时候却不会调用
        super.onHiddenChanged(hidden);
        if (!hidden) {
            onVisible();
            isVisible = true;
        } else {
            onHidden();
            isVisible = false;
        }
    }

}
