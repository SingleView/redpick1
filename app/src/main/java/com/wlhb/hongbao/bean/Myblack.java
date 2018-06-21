package com.wlhb.hongbao.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/5/9/009.
 */

public class Myblack implements Serializable {


    /**
     * currentPage : 0
     * pageSize : 10
     * totalCount : 1
     * pageParameter : {"userId":9}
     * dataList : [{"id":1,"tstamp":1523725300,"crdate":1410251154,"cruserId":1,"deleted":false,"hidden":false,"sorting":0,"username":"1234","password":"","transPassword":"","nickname":"","gender":0,"email":"1234@qq.ads","image":"http://119.23.238.17:8080/uploads/member/201805/02/22c8c852-2310-42fe-9a3c-2ca23b931e5c.png","mobile":"","openid":"","address":"","level":false,"remark":"","cardFork":"","birthday":"","isLogin":false,"loginUuid":"","loginTime":0,"pullBlack":true,"pullBlackTime":1523725300,"amount":0,"avilableAmount":0,"pushType":1,"pushTypeName":"推送","personalMark":"","luckyValue":0,"inviteCode":"","token":null,"vip":0,"vipExpiry":0,"hot":0,"isLike":null,"distance":null,"login":false}]
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
         * id : 1
         * tstamp : 1523725300
         * crdate : 1410251154
         * cruserId : 1
         * deleted : false
         * hidden : false
         * sorting : 0.0
         * username : 1234
         * password :
         * transPassword :
         * nickname :
         * gender : 0
         * email : 1234@qq.ads
         * image : http://119.23.238.17:8080/uploads/member/201805/02/22c8c852-2310-42fe-9a3c-2ca23b931e5c.png
         * mobile :
         * openid :
         * address :
         * level : false
         * remark :
         * cardFork :
         * birthday :
         * isLogin : false
         * loginUuid :
         * loginTime : 0
         * pullBlack : true
         * pullBlackTime : 1523725300
         * amount : 0.0
         * avilableAmount : 0.0
         * pushType : 1
         * pushTypeName : 推送
         * personalMark :
         * luckyValue : 0
         * inviteCode :
         * token : null
         * vip : 0
         * vipExpiry : 0
         * hot : 0
         * isLike : null
         * distance : null
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
        public int luckyValue;
        public String inviteCode;
        public Object token;
        public int vip;
        public int vipExpiry;
        public int hot;
        public Object isLike;
        public Object distance;
        public boolean login;

    }
}
