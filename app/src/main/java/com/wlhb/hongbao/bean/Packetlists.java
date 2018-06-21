package com.wlhb.hongbao.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/5/8/008.
 */

public class Packetlists implements Serializable {

    /**
     * currentPage : 0
     * pageSize : 10
     * totalCount : 3
     * pageParameter : {"province":null,"lng":0,"maxlat":0.01798643211837461,"city":"","minlat":-0.01798643211837461,"minlng":-0.01798643211837461,"communityId":null,"type":1,"userId":null,"lat":0,"maxlng":0.01798643211837461}
     * dataList : [{"id":4,"tstamp":1525673762,"crdate":0,"cruserId":1,"deleted":false,"hidden":false,"sorting":0,"hit":3,"like":0,"hot":true,"top":true,"title":"百度","link":"http://www.baid.com","receiveType":true,"image":"233,2323","userId":9,"money":23,"quantity":435,"province":"430000","city":"430200","area":"430202","address":"","lng":0,"lat":0,"status":0,"type":0,"content":"测试红包\n萨达萨达多少擦拭的擦多少擦拭","commentsNum":3,"favoriteNum":43,"receiveNum":6,"userName":"","userImage":null,"provinceName":"湖南省","cityName":"株洲市","areaName":"荷塘区","receiveMoney":null,"listImg":[]},{"id":3,"tstamp":1524768808,"crdate":0,"cruserId":1,"deleted":false,"hidden":false,"sorting":0,"hit":21,"like":0,"hot":true,"top":true,"title":"百度","link":"http://www.baid.com","receiveType":true,"image":"233,2323","userId":9,"money":23,"quantity":435,"province":"430000","city":"430200","area":"430202","address":"","lng":0,"lat":0,"status":0,"type":0,"content":"测试红包\n萨达萨达多少擦拭的擦多少擦拭","commentsNum":2,"favoriteNum":3,"receiveNum":3,"userName":"","userImage":null,"provinceName":"湖南省","cityName":"株洲市","areaName":"荷塘区","receiveMoney":null,"listImg":[]},{"id":2,"tstamp":1525688999,"crdate":0,"cruserId":1,"deleted":false,"hidden":false,"sorting":0,"hit":6,"like":3,"hot":true,"top":true,"title":"百度","link":"http://www.baid.com","receiveType":true,"image":"233,2323","userId":9,"money":23,"quantity":435,"province":"430000","city":"430200","area":"430202","address":"","lng":0,"lat":0,"status":0,"type":0,"content":"测试红包\n萨达萨达多少擦拭的擦多少擦拭","commentsNum":23,"favoriteNum":43,"receiveNum":5,"userName":"","userImage":null,"provinceName":"湖南省","cityName":"株洲市","areaName":"荷塘区","receiveMoney":null,"listImg":[]}]
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
         * province : null
         * lng : 0.0
         * maxlat : 0.01798643211837461
         * city :
         * minlat : -0.01798643211837461
         * minlng : -0.01798643211837461
         * communityId : null
         * type : 1
         * userId : null
         * lat : 0.0
         * maxlng : 0.01798643211837461
         */

        public Object province;
        public double lng;
        public double maxlat;
        public String city;
        public double minlat;
        public double minlng;
        public Object communityId;
        public int type;
        public Object userId;
        public double lat;
        public double maxlng;


    }

    public static class DataListBean {
        /**
         * id : 4
         * tstamp : 1525673762
         * crdate : 0
         * cruserId : 1
         * deleted : false
         * hidden : false
         * sorting : 0.0
         * hit : 3
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
         * userImage : null
         * provinceName : 湖南省
         * cityName : 株洲市
         * areaName : 荷塘区
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
        public String provinceName;
        public String cityName;
        public String areaName;
        public Object receiveMoney;
        public List<String> listImg;

    }
}
