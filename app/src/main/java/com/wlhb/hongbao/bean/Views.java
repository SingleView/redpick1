package com.wlhb.hongbao.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/4/25/025.
 */

public class Views implements Serializable {

    /**
     * currentPage : 0
     * pageSize : 10
     * totalCount : 3
     * pageParameter : {"toUserId":9}
     * dataList : [{"id":5,"tstamp":0,"crdate":0,"cruserId":0,"deleted":false,"hidden":false,"sorting":0,"fromUserId":4,"toUserId":9,"fromUserName":"32","fromUserImage":"http://119.23.238.17:8080/hongbao/upload/62cebb30-c89e-40e3-a613-e93b63188515.jpg","toUserName":"","toUserImage":"http://119.23.238.17:8080/hongbao/upload/62cebb30-c89e-40e3-a613-e93b63188515.jpg"},{"id":4,"tstamp":0,"crdate":0,"cruserId":0,"deleted":false,"hidden":false,"sorting":0,"fromUserId":3,"toUserId":9,"fromUserName":"32","fromUserImage":"http://119.23.238.17:8080/hongbao/upload/62cebb30-c89e-40e3-a613-e93b63188515.jpg","toUserName":"","toUserImage":"http://119.23.238.17:8080/hongbao/upload/62cebb30-c89e-40e3-a613-e93b63188515.jpg"},{"id":3,"tstamp":0,"crdate":0,"cruserId":0,"deleted":false,"hidden":false,"sorting":0,"fromUserId":2,"toUserId":9,"fromUserName":"132","fromUserImage":"http://119.23.238.17:8080/hongbao/upload/62cebb30-c89e-40e3-a613-e93b63188515.jpg","toUserName":"","toUserImage":"http://119.23.238.17:8080/hongbao/upload/62cebb30-c89e-40e3-a613-e93b63188515.jpg"}]
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

        private int toUserId;

    }

    public static class DataListBean {
        /**
         * id : 5
         * tstamp : 0
         * crdate : 0
         * cruserId : 0
         * deleted : false
         * hidden : false
         * sorting : 0.0
         * fromUserId : 4
         * toUserId : 9
         * fromUserName : 32
         * fromUserImage : http://119.23.238.17:8080/hongbao/upload/62cebb30-c89e-40e3-a613-e93b63188515.jpg
         * toUserName :
         * toUserImage : http://119.23.238.17:8080/hongbao/upload/62cebb30-c89e-40e3-a613-e93b63188515.jpg
         */

        public int id;
        public int tstamp;
        public int crdate;
        public int cruserId;
        public boolean deleted;
        public boolean hidden;
        public double sorting;
        public int fromUserId;
        public int toUserId;
        public String fromUserName;
        public String fromUserImage;
        public String toUserName;
        public String toUserImage;


    }
}
