package com.wlhb.hongbao.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/5/7/007.
 */

public class Recommondlists implements Serializable {


    /**
     * currentPage : 0
     * pageSize : 10
     * totalCount : 2
     * pageParameter : {"type":0,"userId":9}
     * dataList : [{"id":3,"tstamp":1523725728,"crdate":1372103935,"cruserId":1,"deleted":false,"hidden":false,"sorting":0,"username":"32","password":"","transPassword":"","nickname":"","gender":0,"email":"qw@qq.wq","image":"http://119.23.238.17:8080/uploads/member/201805/02/22c8c852-2310-42fe-9a3c-2ca23b931e5c.png","mobile":"","openid":"","address":"","level":false,"remark":"","cardFork":"","birthday":"","isLogin":false,"loginUuid":"","loginTime":0,"pullBlack":true,"pullBlackTime":1523725728,"amount":0,"avilableAmount":0,"pushType":0,"pushTypeName":"不推送","personalMark":"","luckyValue":0,"inviteCode":"","token":null,"vip":0,"vipExpiry":0,"login":false},{"id":2,"tstamp":1523725328,"crdate":1373741126,"cruserId":1,"deleted":false,"hidden":false,"sorting":0,"username":"132","password":"","transPassword":"","nickname":"","gender":0,"email":"qq@qq.com","image":"http://119.23.238.17:8080/uploads/member/201805/02/22c8c852-2310-42fe-9a3c-2ca23b931e5c.png","mobile":"","openid":"","address":"","level":false,"remark":"","cardFork":"","birthday":"","isLogin":false,"loginUuid":"","loginTime":0,"pullBlack":true,"pullBlackTime":1523725328,"amount":0,"avilableAmount":0,"pushType":1,"pushTypeName":"推送","personalMark":"","luckyValue":0,"inviteCode":"","token":null,"vip":0,"vipExpiry":0,"login":false}]
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
         * type : 0
         * userId : 9
         */

        public int type;
        public int userId;


    }

    public static class DataListBean {
        /**
         * id : 3
         * tstamp : 1523725728
         * crdate : 1372103935
         * cruserId : 1
         * deleted : false
         * hidden : false
         * sorting : 0.0
         * username : 32
         * password :
         * transPassword :
         * nickname :
         * gender : 0
         * email : qw@qq.wq
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
         * pullBlackTime : 1523725728
         * amount : 0.0
         * avilableAmount : 0.0
         * pushType : 0
         * pushTypeName : 不推送
         * personalMark :
         * luckyValue : 0
         * inviteCode :
         * token : null
         * vip : 0
         * vipExpiry : 0
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
        public boolean login;

    }
}
