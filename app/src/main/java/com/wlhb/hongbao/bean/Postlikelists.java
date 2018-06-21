package com.wlhb.hongbao.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/5/11/011.
 */

public class Postlikelists implements Serializable {

    /**
     * currentPage : 0
     * pageSize : 10
     * totalCount : 1
     * pageParameter : {"ccid":2}
     * dataList : [{"image":"http://119.23.238.17:8080/uploads/member/201805/10/19c70f26-4ab3-4e29-989b-8e0f7fc3e660.png","id":9}]
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
         * ccid : 2
         */

        public int ccid;

    }

    public static class DataListBean {
        /**
         * image : http://119.23.238.17:8080/uploads/member/201805/10/19c70f26-4ab3-4e29-989b-8e0f7fc3e660.png
         * id : 9
         */

        public String image;
        public int id;

    }
}
