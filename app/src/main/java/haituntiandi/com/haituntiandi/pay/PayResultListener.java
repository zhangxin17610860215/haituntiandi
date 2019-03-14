package haituntiandi.com.haituntiandi.pay;

/**
 * 支付返回结果
 */

public interface PayResultListener {
    public void onPaySuccess();

    public void onPayError();

    public void onPayCancel();
}
