package haituntiandi.com.haituntiandi.commodity.commoditylist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import haituntiandi.com.haituntiandi.R;
import haituntiandi.com.haituntiandi.base.BaseActivity;
import haituntiandi.com.haituntiandi.utils.LogUtil;
import haituntiandi.com.haituntiandi.utils.StatusBarUtils;
import haituntiandi.com.haituntiandi.view.LineBreakLayout;

/**
 * 商品列表页
 */
public class CommodityListActivity extends BaseActivity implements CommodityListContract.View {

    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.tv_commoditylist_screen)
    TextView tvShaiXuan;

    private LineBreakLayout mLineBreakLayout;
    private CommodityListPresenter presenter;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, CommodityListActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void initPresenter() {
        presenter = new CommodityListPresenter(this);
    }

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public int bindLayout() {
        return R.layout.commoditylist_activity;
    }

    @Override
    public void initView(View view) {
        new StatusBarUtils(this).setColorBar(Color.parseColor("#FFFFFF"), 50);
    }

    @Override
    public void initData(Context mContext) {

    }

    @Override
    public Activity getActivity() {
        return null;
    }

    @OnClick({R.id.iv_commoditylist_back, R.id.tv_commoditylist_screen})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_commoditylist_back:
                finish();
                break;
            case R.id.tv_commoditylist_screen:
                //筛选
                showPopupwindow();
                break;
        }
    }

    private void showPopupwindow() {
        View layout = LayoutInflater.from(this).inflate(R.layout.pop_shaixuan_layout, null);
        final PopupWindow pop = new PopupWindow(layout,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                true);
        pop.setBackgroundDrawable(new ColorDrawable(0xffffff));
        pop.showAtLocation(tvShaiXuan, Gravity.NO_GRAVITY, 0, 0);
        layout.findViewById(R.id.tv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pop.dismiss();
            }
        });
        mLineBreakLayout = layout.findViewById(R.id.mLineBreakLayout);

        List<String> lable = new ArrayList<>();
        lable.add("全部");
        lable.add("经济");
        lable.add( "娱乐");
        lable.add("八卦");
        lable.add("小道消息");
        lable.add("政治中心");
        lable.add("彩票");
        lable.add("情感");
//设置标签
        mLineBreakLayout.setLables(lable, true);
//获取选中的标签
        List<String> selectedLables = mLineBreakLayout.getSelectedLables();
//        LogUtil.e("TATATA",selectedLables.size() + "");
    }

    /**
     * 获取状态通知栏高度
     *
     * @param activity
     * @return
     */
    public static int getStatusBarHeight(Activity activity) {
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        return frame.top;
    }
}
