package haituntiandi.com.haituntiandi.requestutils;

public abstract class requestCallback {
    public abstract void onSuccess(Object object);
    public abstract void onFailed(String errCode, String errMessage);
}

