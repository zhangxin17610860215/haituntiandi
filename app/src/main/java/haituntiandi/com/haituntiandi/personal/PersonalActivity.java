package haituntiandi.com.haituntiandi.personal;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.carbswang.android.numberpickerview.library.NumberPickerView;
import haituntiandi.com.haituntiandi.R;
import haituntiandi.com.haituntiandi.base.BaseActivity;

public class PersonalActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.picker)
    NumberPickerView picker;

    private String[] datas = new String[]{"商品一","商品一","商品一","商品一","商品一","商品一",
            "商品一","商品一","商品一","商品一","商品一","商品一",
            "商品一","商品一","商品一","商品一","商品一","商品一",
            "商品一","商品一","商品一","商品一","商品一","商品一",
            "商品一","商品一","商品一","商品一","商品一","商品一"};

    @Override
    public void initPresenter() {

    }

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_personal;
    }

    @Override
    public void initView(View view) {
//        StatusBarUtils statusBarUtils = new StatusBarUtils(this);
//        statusBarUtils.setColorBar(Color.parseColor("#235"),50);
//        toolbar.setBackgroundColor(Color.parseColor("#235"));

        picker.refreshByNewDisplayedValues(datas);
    }

    @Override
    public void initData(Context mContext) {

    }
}
