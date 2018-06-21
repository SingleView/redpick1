package com.wlhb.hongbao.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/5/7/007.
 */

public class Favorite implements Serializable {

    /**
     * currentPage : 0
     * pageSize : 10
     * totalCount : 2
     * pageParameter : {"userId":9}
     * dataList : [{"id":4,"tstamp":1523735557,"crdate":0,"cruserId":1,"deleted":false,"hidden":false,"sorting":0,"hit":0,"like":0,"hot":true,"top":true,"title":"百度","link":"http://www.baid.com","receiveType":true,"image":"233,2323","userId":9,"money":23,"quantity":435,"province":"430000","city":"430200","area":"430202","address":"","lng":0,"lat":0,"status":0,"type":0,"content":"测试红包\n萨达萨达多少擦拭的擦多少擦拭","commentsNum":3,"favoriteNum":43,"receiveNum":6,"userName":"","userImage":"http://119.23.238.17:8080/uploads/member/201805/07/7c50f1e9-6c35-4b9d-b904-84e144b206f1.png","provinceName":null,"cityName":null,"areaName":null,"receiveMoney":null,"listImg":[]},{"id":1,"tstamp":1525663503,"crdate":0,"cruserId":1,"deleted":false,"hidden":false,"sorting":0,"hit":676,"like":2,"hot":true,"top":true,"title":"百度","link":"http://www.baid.com","receiveType":true,"image":"233,2323","userId":1,"money":23,"quantity":435,"province":"430000","city":"430200","area":"430202","address":"","lng":0,"lat":0,"status":0,"type":0,"content":"测试红包\n萨达萨达多少擦拭的擦多少擦拭","commentsNum":0,"favoriteNum":0,"receiveNum":0,"userName":"1234","userImage":"http://119.23.238.17:8080/uploads/member/201805/02/22c8c852-2310-42fe-9a3c-2ca23b931e5c.png","provinceName":null,"cityName":null,"areaName":null,"receiveMoney":null,"listImg":[]}]
     * currentResult : 0
     */

    public int currentPage;
    public int pageSize;
    public int totalCount;
    public PageParameterBean pageParameter;
    public int currentResult;
    public List<DataListBean> dataList;


    public static class PageParameterBean {
        /**
         * userId : 9
         */

        private int userId;

    }

    public static class DataListBean {
        /**
         * id : 4
         * tstamp : 1523735557
         * crdate : 0
         * cruserId : 1
         * deleted : false
         * hidden : false
         * sorting : 0.0
         * hit : 0
         * like : 0
         * hot : true
         * top : true
         * title : 百度
         * link : http://www.baid.com
         * receiveType : true
         * image : 233,2323
         * userId : 9
         * money : 23.0
         * quantity : 435
         * province : 430000
         * city : 430200
         * area : 430202
         * address :
         * lng : 0.0
         * lat : 0.0
         * status : 0
         * type : 0
         * content : 测试红包
         萨达萨达多少擦拭的擦多少擦拭
         * commentsNum : 3
         * favoriteNum : 43
         * receiveNum : 6
         * userName :
         * userImage : http://119.23.238.17:8080/uploads/member/201805/07/7c50f1e9-6c35-4b9d-b904-84e144b206f1.png
         * provinceName : null
         * cityName : null
         * areaName : null
         * receiveMoney : null
         * listImg : []
         */

        public int id;
        public int tstamp;
        public int crdate;
        public int cruserId;
        public boolean deleted;
        public boolean hidden;
        public double sorting;
        public int hit;
        public int like;
        public boolean hot;
        public boolean top;
        public String title;
        public String link;
        public boolean receiveType;
        public String image;
        public int userId;
        public double money;
        public int quantity;
        public String province;
        public String city;
        public String area;
        public String address;
        public double lng;
        public double lat;
        public int status;
        public int type;
        public String content;
        public int commentsNum;
        public int favoriteNum;
        public int receiveNum;
        public String userName;
        public String userImage;
        public Object provinceName;
        public Object cityName;
        public Object areaName;
        public Object receiveMoney;
        public List<String> listImg;

    }
}
