package haituntiandi.com.haituntiandi.personal.settingreceivingaddress;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectChangeListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import haituntiandi.com.haituntiandi.R;
import haituntiandi.com.haituntiandi.base.BaseActivity;
import haituntiandi.com.haituntiandi.bean.CityBean;
import haituntiandi.com.haituntiandi.utils.GetJsonDataUtil;
import haituntiandi.com.haituntiandi.utils.LogUtil;
import haituntiandi.com.haituntiandi.view.TimePickerView;

/**
 * 设置收货地址
 */
public class SettingReceivingAddressActivity extends BaseActivity implements SettingReceivingAddressContract.View {

    /**
     * 收件人姓名
     */
    @BindView(R.id.et_settingreceiving_name)
    EditText etSettingreceivingName;
    /**
     * 收件人手机号
     */
    @BindView(R.id.et_settingreceiving_phone)
    EditText etSettingreceivingPhone;
    /**
     * 收件人地址
     */
    @BindView(R.id.et_settingreceiving_address)
    EditText etSettingreceivingAddress;
    @BindView(R.id.cb_ido)
    CheckBox cbIdo;
    private SettingReceivingAddressPresenter presenter;
    private List<CityBean> options1Text = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Text = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Text = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Value = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Value = new ArrayList<>();

    private CityBean cityBean = new CityBean();
    private List<CityBean.ChildBeanX> city = new ArrayList<>();
    private List<CityBean.ChildBeanX.ChildBean> child = new ArrayList<>();
    public static void startActivity(Context context) {
        Intent intent = new Intent(context, SettingReceivingAddressActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void initPresenter() {
        presenter = new SettingReceivingAddressPresenter(this);
    }

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public int bindLayout() {
        return R.layout.settingreceivingaddress_activity;
    }

    @Override
    public void initView(View view) {

    }
    @Override
    public void initData(Context mContext) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                initJsonData();
            }
        }).start();
    }

    private void initJsonData() {
        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String JsonData = new GetJsonDataUtil().getJson(this, "province.json");//获取assets目录下的json文件数据
        LogUtil.e(TAG,"JsonData>>>>>>>>" + JsonData);
        ArrayList<CityBean> CityBean = parseData(JsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */

        for (int i = 0; i < CityBean.size(); i ++ ){
            cityBean = CityBean.get(i);
//            LogUtil.e(TAG, "省>>>>"+cityBean.getText());
            ArrayList<String> cityStringList = new ArrayList<>();
            ArrayList<String> cityStringValue = new ArrayList<>();
            ArrayList<ArrayList<String>> countyStringList = new ArrayList<>();
            ArrayList<ArrayList<String>> countyStringValue = new ArrayList<>();
            for (int j = 0; j < cityBean.getChild().size(); j ++){
                ArrayList<String> strings = new ArrayList<>();
                ArrayList<String> stringv = new ArrayList<>();
                city = CityBean.get(i).getChild();
//                LogUtil.e(TAG, "市>>>>"+city.get(j).getText());
                cityStringList.add(city.get(j).getText());
                cityStringValue.add(city.get(j).getValue());
                for (int x = 0; x < city.get(j).getChild().size(); x ++){
                    child = city.get(j).getChild();
//                    LogUtil.e(TAG, "县>>>>"+child.get(x).getText());
                    strings.add(child.get(x).getText());
                    stringv.add(child.get(x).getValue());
                }
                countyStringList.add(strings);
                countyStringValue.add(stringv);
            }
            options1Text.add(cityBean);
            options2Text.add(cityStringList);
            options2Value.add(cityStringValue);
            options3Text.add(countyStringList);
            options3Value.add(countyStringValue);
        }
    }

    public ArrayList<CityBean> parseData(String result) {//Gson 解析
        ArrayList<CityBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                CityBean entity = gson.fromJson(data.optJSONObject(i).toString(), CityBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return detail;
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @OnClick({R.id.cb_ido, R.id.iv_settingreceiving_back, R.id.rl_settingreceiving_address, R.id.tv_settingreceiving_Submission})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_settingreceiving_back:
                finish();
                break;
            case R.id.rl_settingreceiving_address:
                //选择地区
                if (options1Text.size() >= 0 && options2Text.size() >= 0 && options3Text.size() >= 0){
                    initPopupWindow();
                }
                break;
            case R.id.tv_settingreceiving_Submission:
                //提交
                break;
            case R.id.cb_ido:
                //是否设置为默认地址
                if (cbIdo.isChecked()) {

                } else {

                }
                break;
        }
    }

    private void initPopupWindow() {
        // 用于PopupWindow的View
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                LogUtil.e("YUYUYU", "省》》》" + options1 + options1Text.get(options1).getText());
                LogUtil.e("YUYUYU", "省》》》" + options1 + options1Text.get(options1).getValue());
                LogUtil.e("YUYUYU", "市》》》" + options2 + options2Text.get(options1).get(options2));
                LogUtil.e("YUYUYU", "市》》》" + options2 + options2Value.get(options1).get(options2));
                LogUtil.e("YUYUYU", "县》》》" + options3 + options3Text.get(options1).get(options2).get(options3));
                LogUtil.e("YUYUYU", "县》》》" + options3 + options3Value.get(options1).get(options2).get(options3));
            }
        }).setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .isRestoreItem(true)//切换时是否还原，设置默认选中第一项。
                .setCancelText("取消")
                .setSubmitText("确定")
                .setSelectOptions(0, 0, 0)//默认选中项
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();
        pvOptions.setPicker(options1Text, options2Text, options3Text);//三级选择器
        pvOptions.show();
    }
}
