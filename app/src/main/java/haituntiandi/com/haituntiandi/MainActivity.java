package haituntiandi.com.haituntiandi;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yuyh.easyadapter.recyclerview.EasyRVAdapter;
import com.yuyh.easyadapter.recyclerview.EasyRVHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import haituntiandi.com.haituntiandi.base.BaseActivity;
import haituntiandi.com.haituntiandi.bean.HomeListBean;
import haituntiandi.com.haituntiandi.bean.HttdSignBean;
import haituntiandi.com.haituntiandi.commodity.commoditylist.CommodityListActivity;
import haituntiandi.com.haituntiandi.config.Constants;
import haituntiandi.com.haituntiandi.personal.login.LoginActivity;
import haituntiandi.com.haituntiandi.personal.mydata.MyDataActivity;
import haituntiandi.com.haituntiandi.personal.register.RegisterActivity;
import haituntiandi.com.haituntiandi.personal.setting.SettingActivity;
import haituntiandi.com.haituntiandi.requestutils.api.MyApi;
import haituntiandi.com.haituntiandi.requestutils.requestCallback;
import haituntiandi.com.haituntiandi.utils.LogUtil;
import haituntiandi.com.haituntiandi.utils.StatusBarUtils;
import haituntiandi.com.haituntiandi.utils.ToastUtil;
import haituntiandi.com.haituntiandi.utils.encryptionutils.CrypticUtil;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    /**
     * 购物车商品数量
     */
    @BindView(R.id.tv_main_ShoppingNum)
    TextView tvMainShoppingNum;
    /**
     * 搜索框中的内容
     */
    @BindView(R.id.tv_main_searchData)
    TextView tvMainSearchData;
    /**
     * 日期
     */
    @BindView(R.id.tv_main_time)
    TextView tvMainTime;
    /**
     * 首页logo
     */
    @BindView(R.id.img_main_logoUrl)
    ImageView imgMainLogoUrl;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.rv_main_recyclerView)
    RecyclerView rvMainRecyclerView;
    /**
     * 头像
     */
    ImageView ivBehindHead;
    /**
     * 用户名
     */
    TextView tvBehindName;
    /**
     * 是否显示VIP图标
     */
    ImageView ivBehindIsVIP;
    /**
     * 积分进度条
     */
    ProgressBar mProgressBar;
    /**
     * 积分值
     */
    TextView tvBehindVIP;
    /**
     * 当前积分
     */
    TextView tvBehindJiFenNum;
    TextView tvJiChangVIP;
    TextView tvShengRiFuLi;
    TextView tvGengDuo;
    TextView tvMyOrder;
    TextView tvMyWish;
    TextView tvMyDiscount;
    TextView tvMyMessger;
    TextView tvMyData;
    TextView tvMyCustomer;
    TextView tvMySetting;
    private SlidingMenu mSlidingMenu;

    private List<HomeListBean.DataBean.ListBean> list = new ArrayList<>();
    private int pageNo;
    private EasyRVAdapter mAssetsAdapter;

    @Override
    public void initPresenter() {

    }

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView(View view) {
        new StatusBarUtils(this).setColorBar(Color.parseColor("#00000000"), 50);
//        toolbar.setBackgroundColor(Color.parseColor("#df7fa2"));
//        getJurisdiction();
        initSlidingMenu();
        pagingLoad();
    }

    /**
     * 分页加载
     */
    private void pagingLoad() {
        rvMainRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        refreshLayout.setRefreshHeader(new ClassicsHeader(this));
//        refreshLayout.setRefreshFooter(new ClassicsFooter(this));
//        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
//            @Override
//            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
//                pageNo = pageNo + 1;
//                initData(MainActivity.this);
//            }
//        });
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNo = 1;
                initData(MainActivity.this);
            }
        });
    }

    /**
     * 初始化侧滑栏控件
     */
    private void initSlidingMenu() {
        mSlidingMenu = new SlidingMenu(this);
        mSlidingMenu.setMode(SlidingMenu.LEFT);     //设置从左弹出/滑出SlidingMenu
        mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);   //设置占满屏幕
        mSlidingMenu.setShadowDrawable(R.drawable.shadow);
        mSlidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);    //绑定到哪一个Activity对象
        mSlidingMenu.setMenu(R.layout.activity_main_behind);                   //设置弹出的SlidingMenu的布局文件
        mSlidingMenu.setBehindOffsetRes(R.dimen.slidingmenu);       //设置SlidingMenu所占的偏移
        mSlidingMenu.setOffsetFadeDegree(0.4f);//设置剩余部分阴影效果

        ivBehindHead = this.findViewById(R.id.iv_behind_head);
        tvBehindName = this.findViewById(R.id.tv_behind_name);
        ivBehindIsVIP = this.findViewById(R.id.iv_behind_isVIP);
        mProgressBar = this.findViewById(R.id.mProgressBar);
        tvBehindVIP = this.findViewById(R.id.tv_behind_VIP);
        tvBehindJiFenNum = this.findViewById(R.id.tv_behind_JiFenNum);

        tvJiChangVIP = this.findViewById(R.id.tv_behind_JiChangVIP);
        tvShengRiFuLi = this.findViewById(R.id.tv_behind_ShengRiFuLi);
        tvGengDuo = this.findViewById(R.id.tv_behind_GengDuo);
        tvMyOrder = this.findViewById(R.id.tv_behind_myOrder);
        tvMyWish = this.findViewById(R.id.tv_behind_myWish);
        tvMyDiscount = this.findViewById(R.id.tv_behind_myDiscount);
        tvMyMessger = this.findViewById(R.id.tv_behind_myMessger);
        tvMyData = this.findViewById(R.id.tv_behind_myData);
        tvMyCustomer = this.findViewById(R.id.tv_behind_myCustomer);
        tvMySetting = this.findViewById(R.id.tv_behind_mySetting);

        tvJiChangVIP.setOnClickListener(this);
        tvShengRiFuLi.setOnClickListener(this);
        tvGengDuo.setOnClickListener(this);
        tvMyOrder.setOnClickListener(this);
        tvMyWish.setOnClickListener(this);
        tvMyDiscount.setOnClickListener(this);
        tvMyMessger.setOnClickListener(this);
        tvMyData.setOnClickListener(this);
        tvMyCustomer.setOnClickListener(this);
        tvMySetting.setOnClickListener(this);
    }

//    /**
//     * 动态获取权限
//     * */
//    private void getJurisdiction() {
//        if(Build.VERSION.SDK_INT>=23){
//            //其中123是requestcode，可以根据这个code判断，用户是否同意了授权。如果没有同意，可以根据回调进行相应处理：(onRequestPermissionsResult())
//            String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.CALL_PHONE,Manifest.permission.READ_LOGS,Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.SET_DEBUG_APP,Manifest.permission.SYSTEM_ALERT_WINDOW,Manifest.permission.GET_ACCOUNTS,Manifest.permission.WRITE_APN_SETTINGS};
//            ActivityCompat.requestPermissions(this,mPermissionList,123);
//        }
//
//    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void initData(Context mContext) {
        MyApi.getCommodityList(this, new requestCallback() {
            @Override
            public void onSuccess(Object object) {

            }

            @Override
            public void onFailed(String errMessage) {

            }
        });
        MyApi.getHomeData(this, new requestCallback() {
            @Override
            public void onSuccess(Object object) {
                if (refreshLayout != null) {
                    refreshLayout.finishRefresh();
                    refreshLayout.finishLoadMore();
                }
                if (null != object){
                    HomeListBean bean = (HomeListBean) object;
                    HomeListBean.DataBean data = bean.getData();
                    initHomeParms(data);
                    list = data.getList();
                    loadData();
                }
            }

            @Override
            public void onFailed(String errMessage) {
                ToastUtil.showMsg(errMessage);
                if (refreshLayout != null) {
                    refreshLayout.finishRefresh();
                    refreshLayout.finishLoadMore();
                }
            }
        });
        MyApi.getHomePersonalData("", this, new requestCallback() {
            @Override
            public void onSuccess(Object object) {

            }

            @Override
            public void onFailed(String errMessage) {

            }
        });
    }

    private void initHomeParms(HomeListBean.DataBean data) {
        if (data.getShoppingNo() > 99){
            tvMainShoppingNum.setVisibility(View.VISIBLE);
            tvMainShoppingNum.setText("99+");
        }else if (data.getShoppingNo() == 0){
            tvMainShoppingNum.setVisibility(View.GONE);
        }else {
            tvMainShoppingNum.setVisibility(View.VISIBLE);
            tvMainShoppingNum.setText(data.getShoppingNo() + "");
        }
        tvMainSearchData.setText(data.getHotWords());
        tvMainTime.setText(data.getReleaseTime());
        Glide.with(this).load(data.getLogoUrl()).into(imgMainLogoUrl);
    }

    private void loadData() {
        mAssetsAdapter = new EasyRVAdapter(this, list, R.layout.item_homedata_layout) {
            @Override
            protected void onBindData(EasyRVHolder viewHolder, int position, Object item) {
                ImageView img = viewHolder.getView(R.id.iv_item_homedata);
                HomeListBean.DataBean.ListBean listBean = list.get(position);
                Glide.with(MainActivity.this).load(listBean.getTypeImage()).into(img);
            }
        };
        rvMainRecyclerView.setAdapter(mAssetsAdapter);
    }

    @OnClick({R.id.iv_main_goPersonal, R.id.iv_main_goShoppingCart, R.id.rl_main_goSearch, R.id.iv_main_goXuYuanChi})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_main_goPersonal:
                //个人侧滑页
                mSlidingMenu.toggle(true);
                break;
            case R.id.iv_main_goShoppingCart:
                //跳转购物车
                RegisterActivity.startActivity(this,"1");
                break;
            case R.id.rl_main_goSearch:
                //跳转搜索页面
                LoginActivity.startActivity(this);
                break;
            case R.id.iv_main_goXuYuanChi:
                //跳转许愿池页面
                CommodityListActivity.startActivity(this);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_behind_JiChangVIP:
                //机场VIP
                break;
            case R.id.tv_behind_ShengRiFuLi:
                //生日福利
                break;
            case R.id.tv_behind_GengDuo:
                //更多
                break;
            case R.id.tv_behind_myOrder:
                //我的订单
                break;
            case R.id.tv_behind_myWish:
                //我的心愿
                break;
            case R.id.tv_behind_myDiscount:
                //我的优惠
                break;
            case R.id.tv_behind_myMessger:
                //我的消息
                break;
            case R.id.tv_behind_myData:
                //我的资料
                MyDataActivity.startActivity(this);
                break;
            case R.id.tv_behind_myCustomer:
                //专属客服
                break;
            case R.id.tv_behind_mySetting:
                //设置
                SettingActivity.startActivity(this);
                break;
        }
    }

//    @OnClick({R.id.tv_geren, R.id.tv_wxPay, R.id.tv_AliPay, R.id.tv_WXLogin, R.id.tv_WXShare})
//    public void onViewClicked(View view) {
//        PayUtils payUtils = PayUtils.getInstance(this);
//        switch (view.getId()) {
//            case R.id.tv_geren:
//                mSlidingMenu.toggle(true);
//                break;
//            case R.id.tv_wxPay:
//                payUtils.pay(PayUtils.PAY_TYPE_WX, "");
//                break;
//            case R.id.tv_AliPay:
//                payUtils.pay(PayUtils.PAY_TYPE_ALI, "");
//                break;
//            case R.id.tv_WXLogin:
////                startActivity(new Intent(this,PersonalActivity.class));
//                UMShareAPI shareAPI = UMShareAPI.get(this);
//                shareAPI.getPlatformInfo(this, SHARE_MEDIA.WEIXIN, new ShareUtil.MyUMAuthListener());//登录
////                shareAPI.deleteOauth(this, SHARE_MEDIA.QQ, new ShareUtil.MyUMAuthListener());//撤销授权
//                startActivity(new Intent(this,PersonalActivity.class));
//                break;
//            case R.id.tv_WXShare:
//                UMImage image = new UMImage(this, "http://app.chuxinketing.com/api/file/ic_sharelogo.png");//分享图标
//                final UMWeb web = new UMWeb("https://developer.umeng.com/docs/66632/detail/66875?um_channel=sdk"); //切记切记 这里分享的链接必须是http开头
//                web.setTitle("你要分享内容的标题");//标题
//                web.setThumb(image);  //缩略图
//                web.setDescription("你要分享内容的描述");//描述
//                ShareUtil.getInstence(this).shareMethod(web);
//                break;
//        }
//    }
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
//    }
}
