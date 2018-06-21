package com.wlhb.hongbao.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/4/27/027.
 */

public class Commentlist implements Serializable {


    /**
     * currentPage : 0
     * pageSize : 10
     * totalCount : 1
     * pageParameter : {"packetId":189,"pid":0}
     * dataList : [{"id":258,"tstamp":1527241694,"crdate":1527241694,"cruserId":0,"deleted":false,"hidden":false,"sorting":0,"hit":0,"hot":false,"top":false,"fromUserId":47,"fromUserName":"圣伍德","fromUserImage":"http://www.blbuy.com.cn:80/uploads/member/201805/25/3c578fb5-a6ff-46dd-9847-3ca5c52f502e.png","toUserId":47,"packetId":189,"pid":0,"type":0,"content":"hjjjjjh","itemList":[{"id":259,"tstamp":1527242003,"crdate":1527242003,"cruserId":0,"deleted":false,"hidden":false,"sorting":0,"hit":0,"hot":false,"top":false,"fromUserId":47,"fromUserName":"圣伍德","fromUserImage":"410","toUserId":47,"packetId":189,"pid":258,"type":1,"content":"hjjnj"},{"id":260,"tstamp":1527242022,"crdate":1527242022,"cruserId":0,"deleted":false,"hidden":false,"sorting":0,"hit":0,"hot":false,"top":false,"fromUserId":47,"fromUserName":"圣伍德","fromUserImage":"410","toUserId":47,"packetId":189,"pid":258,"type":1,"content":"hhhh"}]}]
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
         * packetId : 189
         * pid : 0
         */

        public int packetId;
        public int pid;

    }

    public static class DataListBean {
        /**
         * id : 258
         * tstamp : 1527241694
         * crdate : 1527241694
         * cruserId : 0
         * deleted : false
         * hidden : false
         * sorting : 0.0
         * hit : 0
         * hot : false
         * top : false
         * fromUserId : 47
         * fromUserName : 圣伍德
         * fromUserImage : http://www.blbuy.com.cn:80/uploads/member/201805/25/3c578fb5-a6ff-46dd-9847-3ca5c52f502e.png
         * toUserId : 47
         * packetId : 189
         * pid : 0
         * type : 0
         * content : hjjjjjh
         * itemList : [{"id":259,"tstamp":1527242003,"crdate":1527242003,"cruserId":0,"deleted":false,"hidden":false,"sorting":0,"hit":0,"hot":false,"top":false,"fromUserId":47,"fromUserName":"圣伍德","fromUserImage":"410","toUserId":47,"packetId":189,"pid":258,"type":1,"content":"hjjnj"},{"id":260,"tstamp":1527242022,"crdate":1527242022,"cruserId":0,"deleted":false,"hidden":false,"sorting":0,"hit":0,"hot":false,"top":false,"fromUserId":47,"fromUserName":"圣伍德","fromUserImage":"410","toUserId":47,"packetId":189,"pid":258,"type":1,"content":"hhhh"}]
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
        public int packetId;
        public int pid;
        public int type;
        public String content;
        public List<ItemListBean> itemList;

        public static class ItemListBean {
            /**
             * id : 259
             * tstamp : 1527242003
             * crdate : 1527242003
             * cruserId : 0
             * deleted : false
             * hidden : false
             * sorting : 0.0
             * hit : 0
             * hot : false
             * top : false
             * fromUserId : 47
             * fromUserName : 圣伍德
             * fromUserImage : 410
             * toUserId : 47
             * packetId : 189
             * pid : 258
             * type : 1
             * content : hjjnj
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
            public int packetId;
            public int pid;
            public int type;
            public String content;

        }
    }
}
