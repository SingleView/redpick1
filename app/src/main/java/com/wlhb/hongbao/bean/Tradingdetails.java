package com.wlhb.hongbao.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/5/7/007.
 */

public class Tradingdetails implements Serializable {

    /**
     * currentPage : 0
     * pageSize : 10
     * totalCount : 1
     * pageParameter : {"userId":9}
     * dataList : [{"id":1,"tstamp":0,"crdate":0,"cruserId":0,"deleted":false,"hidden":false,"sorting":0,"title":"红包-test","userId":9,"type":2,"typeName":"领取红包","packetId":5,"status":1,"payType":0,"payTypeName":"","number":"20180506002323"}]
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
         * tstamp : 0
         * crdate : 0
         * cruserId : 0
         * deleted : false
         * hidden : false
         * sorting : 0.0
         * title : 红包-test
         * userId : 9
         * type : 2
         * typeName : 领取红包
         * packetId : 5
         * status : 1
         * payType : 0
         * payTypeName :
         * number : 20180506002323
         */

        public int id;
        public int tstamp;
        public int crdate;
        public int cruserId;
        public boolean deleted;
        public boolean hidden;
        public double sorting;
        public String title;
        public int userId;
        public int type;
        public String typeName;
        public int packetId;
        public int status;
        public int payType;
        public String payTypeName;
        public String number;
        public String money;

    }
}
