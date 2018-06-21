package com.wlhb.hongbao.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/5/13/013.
 */

public class Myreceive implements Serializable {

    /**
     * currentPage : 0
     * pageSize : 10
     * totalCount : 7
     * pageParameter : {"userId":40}
     * dataList : [{"id":8,"title":"","link":"","image":"","userId":9,"money":3333,"quantity":333,"province":"湖北省","city":"武汉市","area":"洪山区","address":"光谷创业街9号","lng":114.4223937988,"lat":30.5068187714,"status":0,"type":0,"commentsNum":0,"favoriteNum":0,"receiveNum":5,"userName":"12345","userImage":"http://www.blbuy.com.cn:80/uploads/member/201805/22/a8824738-1a23-4fec-bc77-777c370a2a79.png","receiveMoney":"5.98"},{"id":7,"title":"","link":"","image":"","userId":9,"money":3333,"quantity":333,"province":"湖北省","city":"武汉市","area":"洪山区","address":"光谷创业街9号","lng":114.4223937988,"lat":30.5068187714,"status":0,"type":0,"commentsNum":0,"favoriteNum":1,"receiveNum":5,"userName":"12345","userImage":"http://www.blbuy.com.cn:80/uploads/member/201805/22/a8824738-1a23-4fec-bc77-777c370a2a79.png","receiveMoney":"10.96"},{"id":6,"title":"","link":"","image":"","userId":9,"money":3333,"quantity":333,"province":"湖北省","city":"武汉市","area":"洪山区","address":"光谷创业街9号","lng":114.4223937988,"lat":30.5068187714,"status":0,"type":0,"commentsNum":0,"favoriteNum":0,"receiveNum":5,"userName":"12345","userImage":"http://www.blbuy.com.cn:80/uploads/member/201805/22/a8824738-1a23-4fec-bc77-777c370a2a79.png","receiveMoney":"10.37"},{"id":96,"title":"","link":"","image":"148,","userId":9,"money":23,"quantity":22,"province":"湖北省","city":"武汉市","area":"洪山区","address":"","lng":114.4199981689,"lat":30.5116996765,"status":0,"type":1,"commentsNum":0,"favoriteNum":0,"receiveNum":4,"userName":"12345","userImage":"http://www.blbuy.com.cn:80/uploads/member/201805/22/a8824738-1a23-4fec-bc77-777c370a2a79.png","receiveMoney":"1.78"},{"id":79,"title":"","link":"","image":"125,126,","userId":20,"money":23,"quantity":23,"province":"湖北省","city":"武汉市","area":"洪山区","address":"","lng":114.417098999,"lat":30.5093002319,"status":0,"type":1,"commentsNum":0,"favoriteNum":0,"receiveNum":3,"userName":"135***1354","userImage":"http://www.blbuy.com.cn:80/uploads/member/201805/16/f5ea7d5b-f543-4cba-bf01-1f754c4b4d6d.png","receiveMoney":"0.73"},{"id":5,"title":"","link":"","image":"","userId":9,"money":3333,"quantity":333,"province":"湖北省","city":"武汉市","area":"洪山区","address":"","lng":114.4223937988,"lat":30.5068187714,"status":0,"type":0,"commentsNum":0,"favoriteNum":3,"receiveNum":35,"userName":"12345","userImage":"http://www.blbuy.com.cn:80/uploads/member/201805/22/a8824738-1a23-4fec-bc77-777c370a2a79.png","receiveMoney":"8.64"},{"id":125,"title":"","link":"","image":"189,","userId":9,"money":23,"quantity":23,"province":"湖北省","city":"武汉市","area":"洪山区","address":"","lng":114.4184036255,"lat":30.5091991425,"status":0,"type":1,"commentsNum":0,"favoriteNum":0,"receiveNum":2,"userName":"12345","userImage":"http://www.blbuy.com.cn:80/uploads/member/201805/22/a8824738-1a23-4fec-bc77-777c370a2a79.png","receiveMoney":"1.10"}]
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
         * userId : 40
         */

        public int userId;


    }

    public static class DataListBean {
        /**
         * id : 8
         * title :
         * link :
         * image :
         * userId : 9
         * money : 3333.0
         * quantity : 333
         * province : 湖北省
         * city : 武汉市
         * area : 洪山区
         * address : 光谷创业街9号
         * lng : 114.4223937988
         * lat : 30.5068187714
         * status : 0
         * type : 0
         * commentsNum : 0
         * favoriteNum : 0
         * receiveNum : 5
         * userName : 12345
         * userImage : http://www.blbuy.com.cn:80/uploads/member/201805/22/a8824738-1a23-4fec-bc77-777c370a2a79.png
         * receiveMoney : 5.98
         */

        public int id;
        public String title;
        public String link;
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
        public int commentsNum;
        public int favoriteNum;
        public int receiveNum;
        public String userName;
        public String userImage;
        public String receiveMoney;
        public List<String> listImg;

    }
}
