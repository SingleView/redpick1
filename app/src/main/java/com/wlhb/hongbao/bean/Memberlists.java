package com.wlhb.hongbao.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/5/8/008.
 */

public class Memberlists implements Serializable {


    /**
     * currentPage : 0
     * pageSize : 10
     * totalCount : 1
     * pageParameter : {"gender":1,"lng":114.42247009277344,"communityId":null,"userId":null,"lat":30.506765365600586}
     * dataList : [{"id":9,"tstamp":1526636846,"crdate":1524456572,"cruserId":0,"deleted":false,"hidden":false,"sorting":0,"username":"12345","password":"1C015C54E9CC2DC503F369EA79D71477","transPassword":"","nickname":"测试账号","gender":1,"email":"","image":"http://119.23.238.17:8080/uploads/member/201805/15/7097807d-f512-4064-bba2-1b461c5c8479.png","mobile":"13098839980","openid":"","address":"湖南省岳阳市岳阳楼区","level":false,"remark":"","cardFork":"","birthday":"","isLogin":false,"loginUuid":"","loginTime":0,"pullBlack":false,"pullBlackTime":0,"amount":264.31,"avilableAmount":264.31,"pushType":0,"pushTypeName":"不推送","personalMark":"11111","luckyValue":10,"inviteCode":"1000936974","token":null,"vip":1,"vipExpiry":1526422519,"hot":1,"isLike":null,"distance":"100米","sendMoney":null,"receiveNum":null,"login":false}]
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
         * gender : 1
         * lng : 114.42247009277344
         * communityId : null
         * userId : null
         * lat : 30.506765365600586
         */

        public int gender;
        public double lng;
        public Object communityId;
        public Object userId;
        public double lat;


    }

    public static class DataListBean {
        /**
         * id : 9
         * tstamp : 1526636846
         * crdate : 1524456572
         * cruserId : 0
         * deleted : false
         * hidden : false
         * sorting : 0.0
         * username : 12345
         * password : 1C015C54E9CC2DC503F369EA79D71477
         * transPassword :
         * nickname : 测试账号
         * gender : 1
         * email :
         * image : http://119.23.238.17:8080/uploads/member/201805/15/7097807d-f512-4064-bba2-1b461c5c8479.png
         * mobile : 13098839980
         * openid :
         * address : 湖南省岳阳市岳阳楼区
         * level : false
         * remark :
         * cardFork :
         * birthday :
         * isLogin : false
         * loginUuid :
         * loginTime : 0
         * pullBlack : false
         * pullBlackTime : 0
         * amount : 264.31
         * avilableAmount : 264.31
         * pushType : 0
         * pushTypeName : 不推送
         * personalMark : 11111
         * luckyValue : 10
         * inviteCode : 1000936974
         * token : null
         * vip : 1
         * vipExpiry : 1526422519
         * hot : 1
         * isLike : null
         * distance : 100米
         * sendMoney : null
         * receiveNum : null
         * login : false
         */

        public int id;
        public int tstamp;
        public int crdate;
        public int cruserId;
        public boolean deleted;
        public boolean hidden;
        public double sorting;
        public String username;
        public String password;
        public String transPassword;
        public String nickname;
        public int gender;
        public String email;
        public String image;
        public String mobile;
        public String openid;
        public String address;
        public boolean level;
        public String remark;
        public String cardFork;
        public String birthday;
        public boolean isLogin;
        public String loginUuid;
        public int loginTime;
        public boolean pullBlack;
        public int pullBlackTime;
        public double amount;
        public double avilableAmount;
        public int pushType;
        public String pushTypeName;
        public String personalMark;
        public String userImage;
        public int luckyValue;
        public String inviteCode;
        public Object token;
        public int vip;
        public int vipExpiry;
        public int hot;
        public Object isLike;
        public String distance;
        public Object sendMoney;
        public Object receiveNum;
        public boolean login;
    }
}
