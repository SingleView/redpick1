package com.wlhb.hongbao.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/4/25/025.
 */

public class Lists implements Serializable {


    /**
     * currentPage : 0
     * pageSize : 10
     * totalCount : 35
     * pageParameter : {"userId":9}
     * dataList : [{"id":37,"tstamp":1525201226,"crdate":1525201226,"cruserId":0,"deleted":false,"hidden":false,"sorting":0,"hit":0,"like":0,"hot":false,"top":false,"title":"","link":"","receiveType":false,"image":"33,34,","userId":9,"money":3333,"quantity":333,"province":"湖北省","city":"武汉市","area":"洪山区","address":"光谷创业街9号","lng":114.4223937988,"lat":30.5068187714,"status":0,"type":1,"content":"基督教的简单","commentsNum":0,"favoriteNum":0,"receiveNum":0,"userName":"","userImage":null,"provinceName":null,"cityName":null,"areaName":null,"listImg":["http://119.23.238.17:8080/uploads/packet/201805/02/f98a5287-e3ff-4b44-ab81-3c5756604982.png","http://119.23.238.17:8080/uploads/packet/201805/02/4ee130b5-53e2-47cb-a074-bf8024f031c4.png"]},{"id":36,"tstamp":1525195028,"crdate":1525195028,"cruserId":0,"deleted":false,"hidden":false,"sorting":0,"hit":0,"like":0,"hot":false,"top":false,"title":"","link":"","receiveType":false,"image":"29,30,","userId":9,"money":3333,"quantity":333,"province":"湖北省","city":"武汉市","area":"洪山区","address":"光谷创业街9号","lng":114.4223937988,"lat":30.5068187714,"status":0,"type":1,"content":"基督教的简单","commentsNum":0,"favoriteNum":0,"receiveNum":0,"userName":"","userImage":null,"provinceName":null,"cityName":null,"areaName":null,"listImg":["http://119.23.238.17:8080/uploads/packet/201805/02/0e2b86fa-5122-4dc9-a240-f42dcefeb0c7.png","http://119.23.238.17:8080/uploads/packet/201805/02/b0ae5c2d-7a1f-48f2-b20a-3b04b7874131.png"]},{"id":35,"tstamp":1525188690,"crdate":1525188690,"cruserId":0,"deleted":false,"hidden":false,"sorting":0,"hit":0,"like":0,"hot":false,"top":false,"title":"","link":"","receiveType":false,"image":"27,28,","userId":9,"money":3333,"quantity":333,"province":"湖北省","city":"武汉市","area":"洪山区","address":"光谷创业街9号","lng":114.4223937988,"lat":30.5068187714,"status":0,"type":1,"content":"基督教的简单","commentsNum":0,"favoriteNum":0,"receiveNum":0,"userName":"","userImage":null,"provinceName":null,"cityName":null,"areaName":null,"listImg":["http://119.23.238.17:8080/uploads/packet/201805/01/42a5cd0d-6b30-42e7-a510-5d631339d746.png","http://119.23.238.17:8080/uploads/packet/201805/01/0e4a45e2-5b49-46bd-b754-4dd1beffbf48.png"]},{"id":34,"tstamp":1525198725,"crdate":1524885574,"cruserId":0,"deleted":false,"hidden":false,"sorting":0,"hit":7,"like":0,"hot":false,"top":false,"title":"","link":"","receiveType":false,"image":"25,26,","userId":9,"money":3333,"quantity":333,"province":"湖北省","city":"武汉市","area":"洪山区","address":"光谷创业街9号","lng":114.4223937988,"lat":30.5068187714,"status":0,"type":0,"content":"基督教的简单","commentsNum":0,"favoriteNum":0,"receiveNum":0,"userName":"","userImage":null,"provinceName":null,"cityName":null,"areaName":null,"listImg":["http://119.23.238.17:8080/uploads/packet/201804/28/df4398b3-8b32-4459-8154-5c401fe00bd5.png","http://119.23.238.17:8080/uploads/packet/201804/28/e29c08a2-5f87-486d-b13d-609675c87776.png"]},{"id":33,"tstamp":1524885473,"crdate":1524885473,"cruserId":0,"deleted":false,"hidden":false,"sorting":0,"hit":0,"like":0,"hot":false,"top":false,"title":"","link":"","receiveType":false,"image":"23,24,","userId":9,"money":3333,"quantity":333,"province":"湖北省","city":"武汉市","area":"洪山区","address":"光谷创业街9号","lng":114.4223937988,"lat":30.5068187714,"status":0,"type":0,"content":"基督教的简单","commentsNum":0,"favoriteNum":0,"receiveNum":0,"userName":"","userImage":null,"provinceName":null,"cityName":null,"areaName":null,"listImg":["http://119.23.238.17:8080/uploads/packet/201804/28/5efd7007-8eaa-4148-b580-65d4baa50972.png","http://119.23.238.17:8080/uploads/packet/201804/28/7147181b-65c1-4657-a018-60fab67710e0.png"]},{"id":32,"tstamp":1524885456,"crdate":1524885456,"cruserId":0,"deleted":false,"hidden":false,"sorting":0,"hit":0,"like":0,"hot":false,"top":false,"title":"谷创业街9号","lng":114.4223937988,"lat":30.5068187714,"status":0,"type":0,"content":"基督教的简单","commentsNum":0,"favoriteNum":0,"receiveNum":0,"userName":"","userImage":null,"provinceName":null,"cityName":null,"areaName":null,"listImg":["http://119.23.238.17:8080/uploads/packet/201804/28/1a086772-e30b-402c-a991-2fde067144c4.png"]},{"id":31,"tstamp":1524885429,"crdate":1524885429,"cruserId":0,"deleted":false,"hidden":false,"sorting":0,"hit":0,"like":0,"hot":false,"top":false,"title":"","link":"","receiveType":false,"image":"20,21,","userId":9,"money":3333,"quantity":333,"province":"湖北省","city":"武汉市","area":"洪山区","address":"光谷创业街9号","lng":114.4223937988,"lat":30.5068187714,"status":0,"type":0,"content":"基督教的简单","commentsNum":0,"favoriteNum":0,"receiveNum":0,"userName":"","userImage":null,"provinceName":null,"cityName":null,"areaName":null,"listImg":["http://119.23.238.17:8080/uploads/packet/201804/28/1a3667a1-20d9-4a15-be43-799ccfcb20af.png","http://119.23.238.17:8080/uploads/packet/201804/28/43d9c88e-8ce8-4bcb-b532-3188bc11feb2.png"]},{"id":30,"tstamp":1524885376,"crdate":1524885376,"cruserId":0,"deleted":false,"hidden":false,"sorting":0,"hit":0,"like":0,"hot":false,"top":false,"title":"","link":"","receiveType":false,"image":"18,19,","userId":9,"money":3333,"quantity":333,"province":"湖北省","city":"武汉市","area":"洪山区","address":"光谷创业街9号","lng":114.4223937988,"lat":30.5068187714,"status":0,"type":0,"content":"基督教的简单","commentsNum":0,"favoriteNum":0,"receiveNum":0,"userName":"","userImage":null,"provinceName":null,"cityName":null,"areaName":null,"listImg":["http://119.23.238.17:8080/uploads/packet/201804/28/8c2ff10e-784f-4af5-923f-8e8802856d2f.png","http://119.23.238.17:8080/uploads/packet/201804/28/1c8052dc-070c-4ebd-8f62-0c4ae93b5cbb.png"]},{"id":29,"tstamp":1524885221,"crdate":1524885221,"cruserId":0,"deleted":false,"hidden":false,"sorting":0,"hit":0,"like":0,"hot":false,"top":false,"title":"","link":"","receiveType":false,"image":"16,17,","userId":9,"money":3333,"quantity":333,"province":"湖北省","city":"武汉市","area":"洪山区","address":"光谷创业街9号","lng":114.4223937988,"lat":30.5068187714,"status":0,"type":0,"content":"基督教的简单","commentsNum":0,"favoriteNum":0,"receiveNum":0,"userName":"","userImage":null,"provinceName":null,"cityName":null,"areaName":null,"listImg":["http://119.23.238.17:8080/uploads/packet/201804/28/50e8ddf9-cef3-47f3-8508-867fc3c696b2.png","http://119.23.238.17:8080/uploads/packet/201804/28/1f5718f0-3948-4743-8b3d-ce9ae4780a18.png"]},{"id":28,"tstamp":1524884207,"crdate":1524884207,"cruserId":0,"deleted":false,"hidden":false,"sorting":0,"hit":0,"like":0,"hot":false,"top":false,"title":"","link":"","receiveType":false,"image":"14,15,","userId":9,"money":3333,"quantity":333,"province":"湖北省","city":"武汉市","area":"洪山区","address":"光谷创业街9号","lng":114.4223937988,"lat":30.5068187714,"status":0,"type":0,"content":"基督教的简单","commentsNum":0,"favoriteNum":0,"receiveNum":0,"userName":"","userImage":null,"provinceName":null,"cityName":null,"areaName":null,"listImg":["http://119.23.238.17:8080/uploads/packet/201804/28/34827727-dd06-4741-b6f2-6d91ef447879.png","http://119.23.238.17:8080/uploads/packet/201804/28/5044672c-6178-4fc8-9f9c-0cf36e237295.png"]}]
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

        public int userId;


    }

    public static class DataListBean {
        /**
         * id : 37
         * tstamp : 1525201226
         * crdate : 1525201226
         * cruserId : 0
         * deleted : false
         * hidden : false
         * sorting : 0
         * hit : 0
         * like : 0
         * hot : false
         * top : false
         * title :
         * link :
         * receiveType : false
         * image : 33,34,
         * userId : 9
         * money : 3333
         * quantity : 333
         * province : 湖北省
         * city : 武汉市
         * area : 洪山区
         * address : 光谷创业街9号
         * lng : 114.4223937988
         * lat : 30.5068187714
         * status : 0
         * type : 1
         * content : 基督教的简单
         * commentsNum : 0
         * favoriteNum : 0
         * receiveNum : 0
         * userName :
         * userImage : null
         * provinceName : null
         * cityName : null
         * areaName : null
         * listImg : ["http://119.23.238.17:8080/uploads/packet/201805/02/f98a5287-e3ff-4b44-ab81-3c5756604982.png","http://119.23.238.17:8080/uploads/packet/201805/02/4ee130b5-53e2-47cb-a074-bf8024f031c4.png"]
         */

        public int id;
        public int tstamp;
        public int crdate;
        public int cruserId;
        public boolean deleted;
        public boolean hidden;
        public int sorting;
        public int hit;
        public int like;
        public boolean hot;
        public boolean top;
        public String title;
        public String link;
        public boolean receiveType;
        public String image;
        public int userId;
        public int money;
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
        public Object userImage;
        public Object provinceName;
        public Object cityName;
        public Object areaName;
        public List<String> listImg;


    }
}
