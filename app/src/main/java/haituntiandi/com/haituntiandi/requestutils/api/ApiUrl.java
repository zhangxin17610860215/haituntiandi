package haituntiandi.com.haituntiandi.requestutils.api;

import static haituntiandi.com.haituntiandi.config.Constants.VERSIONCODE;

public class ApiUrl {
//    public static String BASE_URL = "http://123.56.221.64/";
//    public static String BASE_URL = "http://192.168.43.3:11600/";
    public static String BASE_URL = "http://httdapp.guangquanqiu.cn/";

    public static String API_GETHOMELIST = BASE_URL + "httdapp/" + VERSIONCODE + "/Android/1/home/findHomeInfo.json";
    public static String API_GETHTTDSIGNKEY = BASE_URL + "httdapp/" + VERSIONCODE + "/Android/1/config/findHttdSignKey.json";
    public static String API_GETCOMMODITYLIST = BASE_URL + "httdapp/" + VERSIONCODE + "/Android/0/goods/testSearchAfter.json";

}
