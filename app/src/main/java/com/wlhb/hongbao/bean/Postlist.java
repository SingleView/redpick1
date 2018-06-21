package com.wlhb.hongbao.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/5/9/009.
 */

public class Postlist implements Serializable {

    /**
     * currentPage : 0
     * pageSize : 10
     * totalCount : null
     * pageParameter : {"area":"","nowUserId":47,"province":"","city":"","communityId":45,"type":1}
     * dataList : [{"id":43,"tstamp":1527645731,"crdate":1527562801,"cruserId":0,"deleted":false,"hidden":false,"sorting":0,"hit":6,"hot":false,"top":false,"name":"bhbbg","communityId":45,"userId":47,"type":1,"typeName":"失物招领","image":"","province":"","city":"","area":"","commentNum":0,"likeNum":0},{"id":40,"tstamp":1527643613,"crdate":1527560411,"cruserId":0,"deleted":false,"hidden":false,"sorting":0,"hit":11,"hot":false,"top":false,"name":"好的家具家电","communityId":45,"userId":47,"type":1,"typeName":"失物招领","image":"","province":"","city":"","area":"","commentNum":0,"likeNum":1},{"id":36,"tstamp":1527562491,"crdate":1527230022,"cruserId":0,"deleted":false,"hidden":false,"sorting":0,"hit":45,"hot":false,"top":false,"name":"fgghhhh","communityId":45,"userId":47,"type":1,"typeName":"失物招领","image":"","province":"","city":"","area":"","commentNum":2,"likeNum":2},{"id":34,"tstamp":1527669626,"crdate":1527211146,"cruserId":0,"deleted":false,"hidden":false,"sorting":0,"hit":155,"hot":false,"top":false,"name":"ujjjjjj","communityId":45,"userId":47,"type":1,"typeName":"失物招领","image":"","province":"","city":"","area":"","commentNum":13,"likeNum":1}]
     * currentResult : 0
     */

    public int currentPage;
    public int pageSize;
    public Object totalCount;
    public PageParameterBean pageParameter;
    public int currentResult;
    public List<DataListBean> dataList;


    public static class PageParameterBean {
        /**
         * area :
         * nowUserId : 47
         * province :
         * city :
         * communityId : 45
         * type : 1
         */

        public String area;
        public int nowUserId;
        public String province;
        public String city;
        public int communityId;
        public int type;

    }

    public static class DataListBean {
        /**
         * id : 43
         * tstamp : 1527645731
         * crdate : 1527562801
         * cruserId : 0
         * deleted : false
         * hidden : false
         * sorting : 0.0
         * hit : 6
         * hot : false
         * top : false
         * name : bhbbg
         * communityId : 45
         * userId : 47
         * type : 1
         * typeName : 失物招领
         * image :
         * province :
         * city :
         * area :
         * commentNum : 0
         * likeNum : 0
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
        public int communityId;
        public int userId;
        public int type;
        public String typeName;
        public String image;
        public String province;
        public String city;
        public String area;
        public int commentNum;
        public int likeNum;

    }
}
