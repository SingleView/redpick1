package com.wlhb.hongbao.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/5/30/030.
 */

public class Mycommunitymsg implements Serializable {


    /**
     * currentPage : 0
     * pageSize : 10
     * totalCount : 20
     * pageParameter : {"userId":47}
     * dataList : [{"id":65,"tstamp":1527644285,"crdate":1527644285,"cruserId":0,"deleted":false,"hidden":false,"sorting":0,"hit":0,"hot":false,"top":false,"name":"","userId":52,"communityId":50,"packetId":0,"communityName":"和","remark":"","type":2,"content":"啊"},{"id":64,"tstamp":1527644283,"crdate":1527644283,"cruserId":0,"deleted":false,"hidden":false,"sorting":0,"hit":0,"hot":false,"top":false,"name":"","userId":52,"communityId":50,"packetId":0,"communityName":"和","remark":"","type":2,"content":"啊"},{"id":63,"tstamp":1527581044,"crdate":1527581044,"cruserId":0,"deleted":false,"hidden":false,"sorting":0,"hit":0,"hot":false,"top":false,"name":"","userId":47,"communityId":48,"packetId":0,"communityName":"讲不后悔火狐狸精灵球队来说话呀呀呀呀呀呀呀呀呀呀呀呀呀呀呀的","remark":"","type":2,"content":"jjjnnj"},{"id":62,"tstamp":1527580928,"crdate":1527580928,"cruserId":0,"deleted":false,"hidden":false,"sorting":0,"hit":0,"hot":false,"top":false,"name":"","userId":47,"communityId":48,"packetId":0,"communityName":"讲不后悔火狐狸精灵球队来说话呀呀呀呀呀呀呀呀呀呀呀呀呀呀呀的","remark":"","type":2,"content":"jjjnnj"},{"id":61,"tstamp":1527580920,"crdate":1527580920,"cruserId":0,"deleted":false,"hidden":false,"sorting":0,"hit":0,"hot":false,"top":false,"name":"","userId":47,"communityId":48,"packetId":0,"communityName":"讲不后悔火狐狸精灵球队来说话呀呀呀呀呀呀呀呀呀呀呀呀呀呀呀的","remark":"","type":2,"content":"jjjnnj"},{"id":60,"tstamp":1527580919,"crdate":1527580919,"cruserId":0,"deleted":false,"hidden":false,"sorting":0,"hit":0,"hot":false,"top":false,"name":"","userId":47,"communityId":48,"packetId":0,"communityName":"讲不后悔火狐狸精灵球队来说话呀呀呀呀呀呀呀呀呀呀呀呀呀呀呀的","remark":"","type":2,"content":"jjjnnj"},{"id":59,"tstamp":1527562891,"crdate":1527562891,"cruserId":0,"deleted":false,"hidden":false,"sorting":0,"hit":0,"hot":false,"top":false,"name":"","userId":47,"communityId":45,"packetId":0,"communityName":"hjjjjjj","remark":"","type":2,"content":"vvvvvg"},{"id":58,"tstamp":1527562889,"crdate":1527562889,"cruserId":0,"deleted":false,"hidden":false,"sorting":0,"hit":0,"hot":false,"top":false,"name":"","userId":47,"communityId":45,"packetId":0,"communityName":"hjjjjjj","remark":"","type":2,"content":"vvvvvg"},{"id":56,"tstamp":1527490092,"crdate":1527490092,"cruserId":0,"deleted":false,"hidden":false,"sorting":0,"hit":0,"hot":false,"top":false,"name":"","userId":47,"communityId":47,"packetId":0,"communityName":"好几年巴菲特呵呵更丰富的内容进行时","remark":"","type":2,"content":"jjjjj"},{"id":54,"tstamp":1527240044,"crdate":1527240044,"cruserId":0,"deleted":false,"hidden":false,"sorting":0,"hit":0,"hot":false,"top":false,"name":"","userId":47,"communityId":45,"packetId":0,"communityName":"hjjjjjj","remark":"","type":2,"content":"有一个干活吧宝贝回北京的路上越走越近期"}]
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
         * userId : 47
         */

        public int userId;


    }

    public static class DataListBean {
        /**
         * id : 65
         * tstamp : 1527644285
         * crdate : 1527644285
         * cruserId : 0
         * deleted : false
         * hidden : false
         * sorting : 0.0
         * hit : 0
         * hot : false
         * top : false
         * name :
         * userId : 52
         * communityId : 50
         * packetId : 0
         * communityName : 和
         * remark :
         * type : 2
         * content : 啊
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
        public int packetId;
        public String communityName;
        public String remark;
        public int type;
        public String content;
    }

}
