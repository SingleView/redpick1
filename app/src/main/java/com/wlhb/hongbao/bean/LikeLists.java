package com.wlhb.hongbao.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/5/10/010.
 */

public class LikeLists implements Serializable {


    /**
     * currentPage : 0
     * pageSize : 10
     * totalCount : 2
     * pageParameter : {"type":0,"userId":9}
     * dataList : [{"id":7,"tstamp":1523725302,"crdate":1523342419,"cruserId":1,"deleted":false,"hidden":false,"sorting":0,"username":"32","password":"","transPassword":"","nickname":"","gender":0,"email":"qw@qq.wq","image":"http://119.23.238.17:8080/uploads/member/201805/02/22c8c852-2310-42fe-9a3c-2ca23b931e5c.png","mobile":"","openid":"","address":"","level":false,"remark":"","cardFork":"","birthday":"","isLogin":false,"loginUuid":"","loginTime":0,"pullBlack":true,"pullBlackTime":1523725302,"amount":0,"avilableAmount":0,"pushType":1,"pushTypeName":"推送","personalMark":"","luckyValue":0,"inviteCode":"","token":null,"vip":0,"vipExpiry":0,"hot":1,"isLike":null,"distance":null,"login":false},{"id":13,"tstamp":1524469889,"crdate":1524469889,"cruserId":0,"deleted":false,"hidden":false,"sorting":0,"username":"","password":"1C015C54E9CC2DC503F369EA79D71477","transPassword":"","nickname":"","gender":0,"email":"","image":"http://119.23.238.17:8080/uploads/member/201805/02/22c8c852-2310-42fe-9a3c-2ca23b931e5c.png","mobile":"17648556235","openid":"","address":"","level":false,"remark":"","cardFork":"","birthday":"","isLogin":false,"loginUuid":"","loginTime":0,"pullBlack":false,"pullBlackTime":0,"amount":0,"avilableAmount":0,"pushType":1,"pushTypeName":"推送","personalMark":"","luckyValue":10,"inviteCode":"1001363259","token":null,"vip":0,"vipExpiry":0,"hot":1,"isLike":null,"distance":null,"login":false}]
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
         * id : 7
         * tstamp : 1523725302
         * crdate : 1523342419
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
         * pullBlackTime : 1523725302
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
         * hot : 1
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
