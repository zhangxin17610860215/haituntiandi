package haituntiandi.com.haituntiandi.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 不可左右滑动的ViewPager
 * */
public class MyNoScrollViewPager extends ViewPager {
    private boolean isCanScroll = true;

    public MyNoScrollViewPager(Context context) {
        super(context);
    }

    public MyNoScrollViewPager(Context context, AttributeSet attrs) {

        super(context, attrs);

    }



    public void setScanScroll(boolean isCanScroll){

        this.isCanScroll = isCanScroll;

    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return  false;
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }
}
