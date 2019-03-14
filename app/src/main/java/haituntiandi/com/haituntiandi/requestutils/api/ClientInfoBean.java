package haituntiandi.com.haituntiandi.requestutils.api;

import com.google.gson.Gson;

/**
 * Create by hsxiao. 2018/8/8
 * 客户端信息，一次获取，在网络请求中带上
 */
public class ClientInfoBean {

    /**
     * clientVersion : 130
     * channel : 360
     * platform : 1
     * osVersion : 444
     * cid : cid123
     * deviceId : ffffffff-ac88-19ab-72cc-554874503984
     */

    private String clientVersion;
    private String channel;
    private String platform;
    private String osVersion;
    private String cid;
    private String deviceId;

    public static ClientInfoBean objectFromData(String str) {

        return new Gson().fromJson(str, ClientInfoBean.class);
    }

    public String getClientVersion() {
        return clientVersion;
    }

    public void setClientVersion(String clientVersion) {
        this.clientVersion = clientVersion;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
