package com.wlhb.hongbao.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/5/10/010.
 */

public class Catelists implements Serializable {


    /**
     * currentPage : 0
     * pageSize : 10
     * totalCount : 6
     * pageParameter : {}
     * dataList : [{"id":6,"tstamp":1525724438,"crdate":1525724172,"cruserId":0,"deleted":false,"hidden":false,"sorting":0,"hit":0,"hot":false,"top":false,"name":"测试","parentId":0,"userId":9,"lock":false,"total":0,"image":"http://119.23.238.17:8080/uploads/packet/201804/28/cd496e03-4a7f-4198-bffc-fc4b169aa030.png","remark":""},{"id":5,"tstamp":0,"crdate":0,"cruserId":0,"deleted":false,"hidden":false,"sorting":0,"hit":0,"hot":false,"top":false,"name":"狼人杀","parentId":0,"userId":0,"lock":false,"total":0,"image":"http://119.23.238.17:8080/uploads/packet/201804/28/cd496e03-4a7f-4198-bffc-fc4b169aa030.png","remark":""},{"id":4,"tstamp":0,"crdate":0,"cruserId":0,"deleted":false,"hidden":false,"sorting":0,"hit":0,"hot":false,"top":false,"name":"棋牌室","parentId":0,"userId":0,"lock":false,"total":0,"image":"http://119.23.238.17:8080/uploads/packet/201804/28/cd496e03-4a7f-4198-bffc-fc4b169aa030.png","remark":""},{"id":3,"tstamp":0,"crdate":0,"cruserId":0,"deleted":false,"hidden":false,"sorting":0,"hit":0,"hot":false,"top":false,"name":"娱乐室","parentId":0,"userId":0,"lock":false,"total":0,"image":"http://119.23.238.17:8080/uploads/packet/201804/28/cd496e03-4a7f-4198-bffc-fc4b169aa030.png","remark":""},{"id":2,"tstamp":0,"crdate":0,"cruserId":0,"deleted":false,"hidden":false,"sorting":0,"hit":0,"hot":false,"top":false,"name":"酒吧室","parentId":0,"userId":0,"lock":false,"total":0,"image":"http://119.23.238.17:8080/uploads/packet/201804/28/cd496e03-4a7f-4198-bffc-fc4b169aa030.png","remark":""},{"id":1,"tstamp":0,"crdate":0,"cruserId":0,"deleted":false,"hidden":false,"sorting":0,"hit":0,"hot":false,"top":false,"name":"有趣的人","parentId":0,"userId":0,"lock":true,"total":0,"image":"http://119.23.238.17:8080/uploads/packet/201804/28/cd496e03-4a7f-4198-bffc-fc4b169aa030.png","remark":""}]
     * currentResult : 0
     */

    public int currentPage;
    public int pageSize;
    public int totalCount;
    public PageParameterBean pageParameter;
    public int currentResult;
    public List<DataListBean> dataList;


    public static class PageParameterBean {
    }

    public static class DataListBean {
        /**
         * id : 6
         * tstamp : 1525724438
         * crdate : 1525724172
         * cruserId : 0
         * deleted : false
         * hidden : false
         * sorting : 0.0
         * hit : 0
         * hot : false
         * top : false
         * name : 测试
         * parentId : 0
         * userId : 9
         * lock : false
         * total : 0
         * image : http://119.23.238.17:8080/uploads/packet/201804/28/cd496e03-4a7f-4198-bffc-fc4b169aa030.png
         * remark :
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
        public String name;
        public int parentId;
        public int userId;
        public boolean lock;
        public int total;
        public String image;
        public String remark;

    }
}
