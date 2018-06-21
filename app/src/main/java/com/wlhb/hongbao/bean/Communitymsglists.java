package com.wlhb.hongbao.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/5/8/008.
 */

public class Communitymsglists implements Serializable {


    /**
     * currentPage : 0
     * pageSize : 10
     * totalCount : 1
     * pageParameter : {"type":2,"communityId":1}
     * dataList : [{"id":3,"tstamp":1525941487,"crdate":1525941487,"cruserId":0,"deleted":false,"hidden":false,"sorting":0,"hit":0,"hot":false,"top":false,"name":"","userId":9,"communityId":1,"remark":"","type":2,"content":"dsfsdfsfsdfsdf\nhhsdfsdf"}]
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
         * type : 2
         * communityId : 1
         */

        public int type;
        public int communityId;

    }

    public static class DataListBean {
        /**
         * id : 3
         * tstamp : 1525941487
         * crdate : 1525941487
         * cruserId : 0
         * deleted : false
         * hidden : false
         * sorting : 0.0
         * hit : 0
         * hot : false
         * top : false
         * name :
         * userId : 9
         * communityId : 1
         * remark :
         * type : 2
         * content : dsfsdfsfsdfsdf
         hhsdfsdf
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
        public int userId;
        public int communityId;
        public String remark;
        public int type;
        public String content;

    }
}
