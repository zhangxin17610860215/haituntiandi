package haituntiandi.com.haituntiandi.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * EditText内容输入限制小数点后8位
 *
 * @author wangzl
 * @date 2018/9/30
 */
public class EditTextPointWatcher implements TextWatcher {
    private EditText editText;
    static int strLength = 10000;

    public EditTextPointWatcher(EditText editText) {
        this.editText = editText;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        judgeNumber(editable, editText);
    }

    /**
     * 金额输入框中的内容限制（最大：小数点前五位，小数点后8位）
     *
     * @param edt
     */
    public static void judgeNumber(Editable edt, EditText editText) {

        String temp = edt.toString();
        int posDot = temp.indexOf(".");//返回指定字符在此字符串中第一次出现处的索引
        int index = editText.getSelectionStart();//获取光标位置
        if (posDot < 0) {//不包含小数点
            if (temp.length() <= strLength) {
                return;//小于8位数直接返回
            } else {
                edt.delete(index - 1, index);//删除光标前的字符
                return;
            }
        }
        if (posDot > strLength) {//小数点前大于8位数就删除光标前一位
            edt.delete(index - 1, index);//删除光标前的字符
            return;
        }
        if (temp.length() - posDot - 1 > 8)//如果包含小数点
        {
            edt.delete(index - 1, index);//删除光标前的字符
            return;
        }
    }

}
