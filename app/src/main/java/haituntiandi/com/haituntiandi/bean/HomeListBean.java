package haituntiandi.com.haituntiandi.bean;

import java.util.List;

public class HomeListBean {

    /**
     * code : 0
     * message : SUCCESS
     * data : {"title":"海屯天地","hotWords":"雅诗兰黛套盒","logoUrl":"https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1552910185&di=5b7c636e10e2dee5c0370d08751a2b83&src=http://b-ssl.duitang.com/uploads/item/201602/20/20160220202952_TsYBv.jpeg","releaseTime":"Aug.21","shoppingNo":0,"list":[{"businessId":null,"typeDesc":"衣服","typeImage":"product/shebeilist_tiaojie.png","type":1},{"businessId":null,"typeDesc":"裤子","typeImage":"product/shebeilist_tiaojie.png","type":2},{"businessId":null,"typeDesc":"鞋子","typeImage":"product/shebeilist_tiaojie.png","type":3},{"businessId":null,"typeDesc":"帽子","typeImage":"product/shebeilist_tiaojie.png","type":4}]}
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
         * title : 海屯天地
         * hotWords : 雅诗兰黛套盒
         * logoUrl : https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1552910185&di=5b7c636e10e2dee5c0370d08751a2b83&src=http://b-ssl.duitang.com/uploads/item/201602/20/20160220202952_TsYBv.jpeg
         * releaseTime : Aug.21
         * shoppingNo : 0
         * list : [{"businessId":null,"typeDesc":"衣服","typeImage":"product/shebeilist_tiaojie.png","type":1},{"businessId":null,"typeDesc":"裤子","typeImage":"product/shebeilist_tiaojie.png","type":2},{"businessId":null,"typeDesc":"鞋子","typeImage":"product/shebeilist_tiaojie.png","type":3},{"businessId":null,"typeDesc":"帽子","typeImage":"product/shebeilist_tiaojie.png","type":4}]
         */

        private String title;
        private String hotWords;
        private String logoUrl;
        private String releaseTime;
        private int shoppingNo;
        private List<ListBean> list;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getHotWords() {
            return hotWords;
        }

        public void setHotWords(String hotWords) {
            this.hotWords = hotWords;
        }

        public String getLogoUrl() {
            return logoUrl;
        }

        public void setLogoUrl(String logoUrl) {
            this.logoUrl = logoUrl;
        }

        public String getReleaseTime() {
            return releaseTime;
        }

        public void setReleaseTime(String releaseTime) {
            this.releaseTime = releaseTime;
        }

        public int getShoppingNo() {
            return shoppingNo;
        }

        public void setShoppingNo(int shoppingNo) {
            this.shoppingNo = shoppingNo;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * businessId : null
             * typeDesc : 衣服
             * typeImage : product/shebeilist_tiaojie.png
             * type : 1
             */

            private int businessId;
            private String typeDesc;
            private String typeImage;
            private int type;

            public int getBusinessId() {
                return businessId;
            }

            public void setBusinessId(int businessId) {
                this.businessId = businessId;
            }

            public String getTypeDesc() {
                return typeDesc;
            }

            public void setTypeDesc(String typeDesc) {
                this.typeDesc = typeDesc;
            }

            public String getTypeImage() {
                return typeImage;
            }

            public void setTypeImage(String typeImage) {
                this.typeImage = typeImage;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }
        }
    }
}
