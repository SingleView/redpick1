package com.wlhb.hongbao.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/5/8/008.
 */

public class Communitylists implements Serializable {


    /**
     * currentPage : 0
     * pageSize : 10
     * totalCount : 2
     * pageParameter : {"nowUserId":47,"categoryId":2}
     * dataList : [{"id":47,"tstamp":1527471020,"crdate":1527471020,"cruserId":0,"deleted":false,"hidden":false,"sorting":0,"hit":0,"hot":false,"top":false,"name":"好几年巴菲特呵呵更丰富的内容进行时","categoryId":2,"userId":47,"lock":false,"total":0,"image":"http://www.blbuy.com.cn:80/uploads/member/201805/28/139e29db-1a1e-4825-a155-a4075dfa045a.png","remark":"刚刚很纠结什么时候走路","province":"福建省","city":"福州市","area":"鼓楼区","content":"工业园区内存不足挂齿科","isJoin":1,"parentId":0,"memberNum":3,"manNum":3,"womenNUm":0,"contentNum":1},{"id":45,"tstamp":1527211116,"crdate":1527211116,"cruserId":0,"deleted":false,"hidden":false,"sorting":0,"hit":0,"hot":false,"top":false,"name":"hjjjjjj","categoryId":2,"userId":47,"lock":false,"total":0,"image":"http://www.blbuy.com.cn:80/uploads/member/201805/25/5b61346a-ebf9-448b-944e-dedaeafdd52d.png","remark":"hhhhhhhhh","province":"辽宁省","city":"沈阳市","area":"沈河区","content":"njjjjj","isJoin":1,"parentId":0,"memberNum":2,"manNum":2,"womenNUm":0,"contentNum":2}]
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
         * nowUserId : 47
         * categoryId : 2
         */

        public int nowUserId;
        public int categoryId;

    }

    public static class DataListBean {
        /**
         * id : 47
         * tstamp : 1527471020
         * crdate : 1527471020
         * cruserId : 0
         * deleted : false
         * hidden : false
         * sorting : 0.0
         * hit : 0
         * hot : false
         * top : false
         * name : 好几年巴菲特呵呵更丰富的内容进行时
         * categoryId : 2
         * userId : 47
         * lock : false
         * total : 0
         * image : http://www.blbuy.com.cn:80/uploads/member/201805/28/139e29db-1a1e-4825-a155-a4075dfa045a.png
         * remark : 刚刚很纠结什么时候走路
         * province : 福建省
         * city : 福州市
         * area : 鼓楼区
         * content : 工业园区内存不足挂齿科
         * isJoin : 1
         * parentId : 0
         * memberNum : 3
         * manNum : 3
         * womenNUm : 0
         * contentNum : 1
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
        public int categoryId;
        public int userId;
        public boolean lock;
        public int total;
        public String image;
        public String remark;
        public String province;
        public String city;
        public String area;
        public String content;
        public int isJoin;
        public int parentId;
        public int memberNum;
        public int manNum;
        public int womenNUm;
        public int contentNum;

    }
}
