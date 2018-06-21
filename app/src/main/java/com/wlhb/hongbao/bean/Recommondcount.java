package com.wlhb.hongbao.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/5/7/007.
 */

public class Recommondcount implements Serializable {


    /**
     * owner : {"id":null,"tstamp":null,"crdate":null,"cruserId":null,"deleted":null,"hidden":null,"sorting":null,"fromUserId":null,"toUserId":null,"fromUserName":null,"toUserName":null,"rate":0.03,"money":0.04,"total":2}
     * total : 0.0
     * friend : {"id":null,"tstamp":null,"crdate":null,"cruserId":null,"deleted":null,"hidden":null,"sorting":null,"fromUserId":null,"toUserId":null,"fromUserName":null,"toUserName":null,"rate":0.01,"money":0.05,"total":3}
     */

    public OwnerBean owner;
    public double total;
    public FriendBean friend;



    public static class OwnerBean {
        /**
         * id : null
         * tstamp : null
         * crdate : null
         * cruserId : null
         * deleted : null
         * hidden : null
         * sorting : null
         * fromUserId : null
         * toUserId : null
         * fromUserName : null
         * toUserName : null
         * rate : 0.03
         * money : 0.04
         * total : 2
         */

        public Object id;
        public Object tstamp;
        public Object crdate;
        public Object cruserId;
        public Object deleted;
        public Object hidden;
        public Object sorting;
        public Object fromUserId;
        public Object toUserId;
        public Object fromUserName;
        public Object toUserName;
        public double rate;
        public double money;
        public int total;

    }

    public static class FriendBean {
        /**
         * id : null
         * tstamp : null
         * crdate : null
         * cruserId : null
         * deleted : null
         * hidden : null
         * sorting : null
         * fromUserId : null
         * toUserId : null
         * fromUserName : null
         * toUserName : null
         * rate : 0.01
         * money : 0.05
         * total : 3
         */

        public Object id;
        public Object tstamp;
        public Object crdate;
        public Object cruserId;
        public Object deleted;
        public Object hidden;
        public Object sorting;
        public Object fromUserId;
        public Object toUserId;
        public Object fromUserName;
        public Object toUserName;
        public double rate;
        public double money;
        public int total;

    }
}
