package haituntiandi.com.haituntiandi.requestutils;

public class BaseBean {

    /**
     * faceCode : 2
     * resCode : 12
     * errorCode :
     * errorMsg : 1`2
     * returnTime : 2018-07-08 00:00:00
     * data : {}
     */

    private String faceCode;
    private String resCode;
    private String errorCode;
    private String errorMsg;
    private String returnTime;
    private DataBean data;

    public String getFaceCode() {
        return faceCode;
    }

    public void setFaceCode(String faceCode) {
        this.faceCode = faceCode;
    }

    public String getResCode() {
        return resCode;
    }

    public void setResCode(String resCode) {
        this.resCode = resCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(String returnTime) {
        this.returnTime = returnTime;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
    }
}
