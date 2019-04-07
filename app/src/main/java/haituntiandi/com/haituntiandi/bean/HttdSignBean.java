package haituntiandi.com.haituntiandi.bean;

public class HttdSignBean {

    /**
     * code : 0
     * message : SUCCESS
     * data : {"httdSignKey":"BvVcmcMn8R9ED0dtj1DgjL0I9ta4zoHY48D7Q1JscVZcFevQ8HqeNc/Z53sPi9VwPKd/vVM23HEnvPVjLke2jLUX9NZFK8h3k9kKHWL2HRVTUiQnng62zBqUgyCkIArttDvIrf/Dz5Xjk6NVfXGhyqpGcbbzLJIXC2egkIM0WWk="}
     */

    private int code;
    private String message;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * httdSignKey : BvVcmcMn8R9ED0dtj1DgjL0I9ta4zoHY48D7Q1JscVZcFevQ8HqeNc/Z53sPi9VwPKd/vVM23HEnvPVjLke2jLUX9NZFK8h3k9kKHWL2HRVTUiQnng62zBqUgyCkIArttDvIrf/Dz5Xjk6NVfXGhyqpGcbbzLJIXC2egkIM0WWk=
         */

        private String httdSignKey;

        public String getHttdSignKey() {
            return httdSignKey;
        }

        public void setHttdSignKey(String httdSignKey) {
            this.httdSignKey = httdSignKey;
        }
    }
}
