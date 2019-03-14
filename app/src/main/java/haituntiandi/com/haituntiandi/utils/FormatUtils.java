package haituntiandi.com.haituntiandi.utils;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormatUtils {
    /**
     * 不保留小数
     *
     * @param data
     * @return
     */
    public static String formatNonesepara(Object data) {
        DecimalFormat df = new DecimalFormat("#0");
        return df.format(data);
    }

    /**
     * 保留一位小数
     *
     * @param data
     * @return
     */
    public static String formatOnesepara(Object data) {
        DecimalFormat df = new DecimalFormat("#0.0");
        return df.format(data);
    }

    /**
     * 保留两位小数
     *
     * @param data
     * @return
     */
    public static String formatTwosepara(Object data) {
        DecimalFormat df = new DecimalFormat("#0.00");
        return df.format(data);
    }

    /**
     * 保留八位小数
     *
     * @param data
     * @return
     */
    public static String formatEightsepara(Object data) {
        DecimalFormat df = new DecimalFormat("#0.00000000");
        return df.format(data);
    }

    /**
     * 将每三个数字加上逗号处理
     *
     * @param str
     * @return
     */
    public static String addComma(String str) {
        String result;
        String begin = str.substring(0, 1);
        if (begin.equals("-")) {
            String s1 = str.substring(1, str.length() - 3);
            String s2 = str.substring(str.length() - 3, str.length());
            String reverseStr = new StringBuilder(s1).reverse().toString();
            String strTemp = "";
            for (int i = 0; i < reverseStr.length(); i++) {

                if (i * 3 + 3 > reverseStr.length()) {

                    strTemp += reverseStr.substring(i * 3, reverseStr.length());

                    break;

                }

                strTemp += reverseStr.substring(i * 3, i * 3 + 3) + ",";

            }

            // 将[000,000,] 中最后一个[,]去除

            if (strTemp.endsWith(",")) {

                strTemp = strTemp.substring(0, strTemp.length() - 1);

            }

            // 将数字重新反转

            StringBuilder builder = new StringBuilder(strTemp).reverse();
            result = begin + builder.append(s2);
//            result = begin + resultStr + s2;

        } else {

            String s1 = str.substring(0, str.length() - 3);
            String s2 = str.substring(str.length() - 3, str.length());

            String reverseStr = new StringBuilder(s1).reverse().toString();
            String strTemp = "";

            for (int i = 0; i < reverseStr.length(); i++) {

                if (i * 3 + 3 > reverseStr.length()) {

                    strTemp += reverseStr.substring(i * 3, reverseStr.length());

                    break;

                }

                strTemp += reverseStr.substring(i * 3, i * 3 + 3) + ",";

            }

            // 将[000,000] 中最后一个[,]去除

            if (strTemp.endsWith(",")) {

                strTemp = strTemp.substring(0, strTemp.length() - 1);

            }

            // 将数字重新反转

            StringBuilder builder = new StringBuilder(strTemp).reverse();
            result = builder.append(s2).toString();

        }
        return result;
    }

    /**
     * 保留两位小数，并且每隔三位加一个分隔符
     *
     * @param str
     * @return
     */
    public static String getTwoSeparaAndAddComma(Object str) {
        //首先保留两位小数
        String resultOne = formatTwosepara(str);
        //其次每隔3位加一个逗号分隔符
        String result = addComma(resultOne);
        return result;
    }

    /**
     * 不保留小数，并且每隔三位加一个分隔符
     *
     * @param str
     * @return
     */
    public static String getNoneSeparaAndAddComma(Object str) {
        //首先保留两位小数，并且添加千分位
        String resultOne = FormatUtils.getTwoSeparaAndAddComma(str);
        //去掉后三个字符
        String result = resultOne.substring(0, resultOne.length() - 3);
        return result;
    }


    /**
     * 判断小数点开头
     *
     * @param str
     * @return
     */
    public static boolean isStratWithPoint(String str) {
        boolean b = true;
        if (str.startsWith(".")) {
            b = false;
        }
        if (str.endsWith(".")) {
            b = false;
        }
        if (str.startsWith("0") && str.endsWith("0")) {
            b = false;
        }
        return b;
    }


    /**
     * 判断是否既包含数字又包含字母
     *
     * @param string
     */
    public static boolean isContainNumAndWord(String string) {
//        boolean flag = false;
//        for (int i = 0; i < string.length(); i++) {
//            if (Character.isLowerCase(string.charAt(i))
//                    || Character.isUpperCase(string.charAt(i))
//                    || Character.isDigit(string.charAt(i))) {
//                flag = true;
//            } else {
//                flag = false;
//                return flag;
//            }
//        }
//        return flag;

        boolean flag = false;
        boolean isDigit = false;//定义一个boolean值，用来表示是否包含数字
        boolean isLetter = false;//定义一个boolean值，用来表示是否包含字母
        for (int i = 0; i < string.length(); i++) { //循环遍历字符串
            if (Character.isDigit(string.charAt(i))) {     //用char包装类中的判断数字的方法判断每一个字符
                isDigit = true;
            }
            if (Character.isLetter(string.charAt(i))) {   //用char包装类中的判断字母的方法判断每一个字符
                isLetter = true;
            }
        }

        if (isDigit == true && isLetter == true) {
            flag = true;
        } else {
            flag = false;
        }

        return flag;
    }


    public static boolean isLetterDigit(String str) {
        String regex = "^[a-z0-9A-Z]+$";//其他需要，直接修改正则表达式就好
        return str.matches(regex);
    }

    /**
     * 小数点前后大小不一致
     *
     * @param value
     * @return
     */
    public static SpannableString changTVsize(String value) {
        SpannableString spannableString = new SpannableString(value);
        if (value.contains(".")) {
            spannableString.setSpan(new RelativeSizeSpan(0.7f), value.indexOf("."), value.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spannableString;
    }

    /**
     * 中国电信号段 133、149、153、173、177、180、181、189、199
     * 中国联通号段 130、131、132、145、155、156、166、175、176、185、186
     * 中国移动号段 134(0-8)、135、136、137、138、139、147、150、151、152、157、158、159、178、182、183、184、187、188、198
     * 其他号段
     * 14号段以前为上网卡专属号段，如中国联通的是145，中国移动的是147等等。
     * 虚拟运营商
     * 电信：1700、1701、1702
     * 移动：1703、1705、1706
     * 联通：1704、1707、1708、1709、171
     * 卫星通信：1349
     *
     * @param phone
     * @return
     */

    public static boolean isPhone(String phone) {
        //String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
        String regex = PHONE_FORMAT;
        if (phone.length() != 11) {
//            ToastUtil.showMsg("手机号应为11位数");
            return false;
        } else {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(phone);
            boolean isMatch = m.matches();

            if (!isMatch) {
//                ToastUtil.showMsg("请输入正确的手机号");
            }
            return isMatch;
        }
    }


    /**
     * 正则：手机号（精确）
     * <p>移动：134(0-8)、135、136、137、138、139、147、150、151、152、157、158、159、178、182、183、184、187、188、198</p >
     * <p>联通：130、131、132、145、155、156、175、176、185、186、166</p >
     * <p>电信：133、153、173、177、180、181、189、199</p >
     * <p>全球星：1349</p >
     * <p>虚拟运营商：170</p >
     */
    public static final String PHONE_FORMAT = "^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0,3,5-8])|(18[0-9])|166|198|199|(147))\\d{8}$";
}
