package com.wlhb.hongbao.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/5/3/003.
 */

public class Dynamic implements Serializable {


    /**
     * currentPage : 0
     * pageSize : 10
     * totalCount : 1
     * pageParameter : {"toUserId":9}
     * dataList : [{"id":50,"tstamp":1525199950,"crdate":1525199950,"cruserId":0,"deleted":false,"hidden":false,"sorting":0,"hit":0,"hot":false,"top":false,"fromUserId":10,"fromUserName":"","fromUserImage":"http://119.23.238.17:8080/uploads/member/201805/02/22c8c852-2310-42fe-9a3c-2ca23b931e5c.png","toUserId":9,"toUserName":null,"toUserImage":null,"packetId":2,"pid":0,"type":0,"content":"66666666666666","itemList":null}]
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
         * toUserId : 9
         */

        public int toUserId;

    }

    public static class DataListBean {
        /**
         * id : 50
         * tstamp : 1525199950
         * crdate : 1525199950
         * cruserId : 0
         * deleted : false
         * hidden : false
         * sorting : 0.0
         * hit : 0
         * hot : false
         * top : false
         * fromUserId : 10
         * fromUserName :
         * fromUserImage : http://119.23.238.17:8080/uploads/member/201805/02/22c8c852-2310-42fe-9a3c-2ca23b931e5c.png
         * toUserId : 9
         * toUserName : null
         * toUserImage : null
         * packetId : 2
         * pid : 0
         * type : 0
         * content : 66666666666666
         * itemList : null
         */

        public int id;
        public int tstamp;
        public int crdate;
        public int cruserId;
        public boolean deleted;
        public boolean hidden;
        public double sorting;
        public int hit;
        public boolean hot;
        public boolean top;
        public int fromUserId;
        public String fromUserName;
        public String fromUserImage;
        public int toUserId;
        public Object toUserName;
        public Object toUserImage;
        public int packetId;
        public int pid;
        public int type;
        public String content;
        public Object itemList;

    }
}
