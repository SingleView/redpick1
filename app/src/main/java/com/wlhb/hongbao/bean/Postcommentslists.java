package com.wlhb.hongbao.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/5/10/010.
 */

public class Postcommentslists implements Serializable {


    /**
     * currentPage : 0
     * pageSize : 10
     * totalCount : 4
     * pageParameter : {"ccid":34,"pid":0}
     * dataList : [{"id":65,"tstamp":1527229854,"crdate":1527229854,"cruserId":0,"deleted":false,"hidden":false,"sorting":0,"hit":0,"hot":false,"top":false,"fromUserId":47,"toUserId":47,"ccid":34,"pid":0,"type":0,"content":"hhjh","fromUserName":"圣伍德","fromUserImage":"http://www.blbuy.com.cn:80/uploads/member/201805/25/3c578fb5-a6ff-46dd-9847-3ca5c52f502e.png","itemList":[{"id":69,"tstamp":1527233610,"crdate":1527233610,"cruserId":0,"deleted":false,"hidden":false,"sorting":0,"hit":0,"hot":false,"top":false,"fromUserId":47,"toUserId":47,"ccid":34,"pid":65,"type":1,"content":"vvgfff","fromUserName":"圣伍德","fromUserImage":"http://www.blbuy.com.cn:80/uploads/member/201805/25/3c578fb5-a6ff-46dd-9847-3ca5c52f502e.png"},{"id":70,"tstamp":1527233673,"crdate":1527233673,"cruserId":0,"deleted":false,"hidden":false,"sorting":0,"hit":0,"hot":false,"top":false,"fromUserId":47,"toUserId":47,"ccid":34,"pid":65,"type":1,"content":"hhhh","fromUserName":"圣伍德","fromUserImage":"http://www.blbuy.com.cn:80/uploads/member/201805/25/3c578fb5-a6ff-46dd-9847-3ca5c52f502e.png"}]},{"id":64,"tstamp":1527229740,"crdate":1527229740,"cruserId":0,"deleted":false,"hidden":false,"sorting":0,"hit":0,"hot":false,"top":false,"fromUserId":47,"toUserId":47,"ccid":34,"pid":0,"type":0,"content":"gggg","fromUserName":"圣伍德","fromUserImage":"http://www.blbuy.com.cn:80/uploads/member/201805/25/3c578fb5-a6ff-46dd-9847-3ca5c52f502e.png","itemList":[]},{"id":63,"tstamp":1527229272,"crdate":1527229272,"cruserId":0,"deleted":false,"hidden":false,"sorting":0,"hit":0,"hot":false,"top":false,"fromUserId":47,"toUserId":47,"ccid":34,"pid":0,"type":0,"content":"ffffff","fromUserName":"圣伍德","fromUserImage":"http://www.blbuy.com.cn:80/uploads/member/201805/25/3c578fb5-a6ff-46dd-9847-3ca5c52f502e.png","itemList":[]},{"id":62,"tstamp":1527227747,"crdate":1527227747,"cruserId":0,"deleted":false,"hidden":false,"sorting":0,"hit":0,"hot":false,"top":false,"fromUserId":52,"toUserId":47,"ccid":34,"pid":0,"type":0,"content":"哈哈哈","fromUserName":"小黑屋","fromUserImage":"http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83epqnUYWGjaPqEJics1ia0Ovf7YIUHr78lmRTBp0Ihgc3TWrkCA1LyxQnNswyaqUw3OUZlqI3Nia0Y76w/132","itemList":[]}]
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
         * ccid : 34
         * pid : 0
         */

        public int ccid;
        public int pid;

    }

    public static class DataListBean {
        /**
         * id : 65
         * tstamp : 1527229854
         * crdate : 1527229854
         * cruserId : 0
         * deleted : false
         * hidden : false
         * sorting : 0.0
         * hit : 0
         * hot : false
         * top : false
         * fromUserId : 47
         * toUserId : 47
         * ccid : 34
         * pid : 0
         * type : 0
         * content : hhjh
         * fromUserName : 圣伍德
         * fromUserImage : http://www.blbuy.com.cn:80/uploads/member/201805/25/3c578fb5-a6ff-46dd-9847-3ca5c52f502e.png
         * itemList : [{"id":69,"tstamp":1527233610,"crdate":1527233610,"cruserId":0,"deleted":false,"hidden":false,"sorting":0,"hit":0,"hot":false,"top":false,"fromUserId":47,"toUserId":47,"ccid":34,"pid":65,"type":1,"content":"vvgfff","fromUserName":"圣伍德","fromUserImage":"http://www.blbuy.com.cn:80/uploads/member/201805/25/3c578fb5-a6ff-46dd-9847-3ca5c52f502e.png"},{"id":70,"tstamp":1527233673,"crdate":1527233673,"cruserId":0,"deleted":false,"hidden":false,"sorting":0,"hit":0,"hot":false,"top":false,"fromUserId":47,"toUserId":47,"ccid":34,"pid":65,"type":1,"content":"hhhh","fromUserName":"圣伍德","fromUserImage":"http://www.blbuy.com.cn:80/uploads/member/201805/25/3c578fb5-a6ff-46dd-9847-3ca5c52f502e.png"}]
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
        public int toUserId;
        public int ccid;
        public int pid;
        public int type;
        public String content;
        public String fromUserName;
        public String fromUserImage;
        public List<ItemListBean> itemList;

        public static class ItemListBean {
            /**
             * id : 69
             * tstamp : 1527233610
             * crdate : 1527233610
             * cruserId : 0
             * deleted : false
             * hidden : false
             * sorting : 0.0
             * hit : 0
             * hot : false
             * top : false
             * fromUserId : 47
             * toUserId : 47
             * ccid : 34
             * pid : 65
             * type : 1
             * content : vvgfff
             * fromUserName : 圣伍德
             * fromUserImage : http://www.blbuy.com.cn:80/uploads/member/201805/25/3c578fb5-a6ff-46dd-9847-3ca5c52f502e.png
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
            public int toUserId;
            public int ccid;
            public int pid;
            public int type;
            public String content;
            public String fromUserName;
            public String fromUserImage;

        }
    }
}
