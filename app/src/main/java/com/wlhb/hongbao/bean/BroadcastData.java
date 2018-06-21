package com.wlhb.hongbao.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/4/19/019.
 */

public class BroadcastData implements Serializable {


    /**
     * currentPage : 0
     * pageSize : 10
     * totalCount : 68
     * pageParameter : {}
     * dataList : [{"id":64,"tstamp":1527760419,"crdate":1527760419,"deleted":0,"hidden":0,"userId":49,"title":"红包消息","content":"您的红包被收藏了","jpushContent":"","status":2,"noticeId":0,"type":0},{"id":63,"tstamp":1527759527,"crdate":1527759527,"deleted":0,"hidden":0,"userId":49,"title":"红包消息","content":"您的红包被收藏了","jpushContent":"","status":2,"noticeId":0,"type":0},{"id":62,"tstamp":1527752729,"crdate":1527752729,"deleted":0,"hidden":0,"userId":47,"title":"红包消息","content":"有人评论了您的红包","jpushContent":"{\"msg_id\":1655665217,\"originalContent\":\"{\\\"sendno\\\":\\\"333597734\\\",\\\"msg_id\\\":\\\"1655665217\\\"}\",\"rateLimitQuota\":600,\"rateLimitRemaining\":599,\"rateLimitReset\":60,\"responseCode\":200,\"resultOK\":true,\"sendno\":333597734}","status":2,"noticeId":0,"type":0},{"id":60,"tstamp":1527750966,"crdate":1527750966,"deleted":0,"hidden":0,"userId":49,"title":"红包消息","content":"您的红包被点赞了","jpushContent":"","status":2,"noticeId":0,"type":0},{"id":58,"tstamp":1527680275,"crdate":1527680275,"deleted":0,"hidden":0,"userId":49,"title":"红包消息","content":"您的红包被领取了","jpushContent":"{\"msg_id\":2272937234,\"originalContent\":\"{\\\"sendno\\\":\\\"1490340702\\\",\\\"msg_id\\\":\\\"2272937234\\\"}\",\"rateLimitQuota\":600,\"rateLimitRemaining\":599,\"rateLimitReset\":60,\"responseCode\":200,\"resultOK\":true,\"sendno\":1490340702}","status":2,"noticeId":0,"type":0},{"id":57,"tstamp":1527680151,"crdate":1527680151,"deleted":0,"hidden":0,"userId":49,"title":"红包消息","content":"您的红包被领取了","jpushContent":"{\"msg_id\":2788256478,\"originalContent\":\"{\\\"sendno\\\":\\\"1698335926\\\",\\\"msg_id\\\":\\\"2788256478\\\"}\",\"rateLimitQuota\":600,\"rateLimitRemaining\":599,\"rateLimitReset\":60,\"responseCode\":200,\"resultOK\":true,\"sendno\":1698335926}","status":2,"noticeId":0,"type":0},{"id":56,"tstamp":1527679583,"crdate":1527679583,"deleted":0,"hidden":0,"userId":49,"title":"红包消息","content":"您的红包被领取了","jpushContent":"{\"msg_id\":2787385542,\"originalContent\":\"{\\\"sendno\\\":\\\"2104675079\\\",\\\"msg_id\\\":\\\"2787385542\\\"}\",\"rateLimitQuota\":600,\"rateLimitRemaining\":599,\"rateLimitReset\":60,\"responseCode\":200,\"resultOK\":true,\"sendno\":2104675079}","status":2,"noticeId":0,"type":0},{"id":55,"tstamp":1527671680,"crdate":1527671680,"deleted":0,"hidden":0,"userId":47,"title":"红包消息","content":"有人评论了您的红包","jpushContent":"{\"msg_id\":2255691388,\"originalContent\":\"{\\\"sendno\\\":\\\"1448553687\\\",\\\"msg_id\\\":\\\"2255691388\\\"}\",\"rateLimitQuota\":600,\"rateLimitRemaining\":599,\"rateLimitReset\":60,\"responseCode\":200,\"resultOK\":true,\"sendno\":1448553687}","status":2,"noticeId":0,"type":0},{"id":54,"tstamp":1527670938,"crdate":1527670938,"deleted":0,"hidden":0,"userId":49,"title":"红包消息","content":"有人评论了您的红包","jpushContent":"{\"msg_id\":2254340993,\"originalContent\":\"{\\\"sendno\\\":\\\"1928451375\\\",\\\"msg_id\\\":\\\"2254340993\\\"}\",\"rateLimitQuota\":600,\"rateLimitRemaining\":599,\"rateLimitReset\":60,\"responseCode\":200,\"resultOK\":true,\"sendno\":1928451375}","status":2,"noticeId":0,"type":0},{"id":53,"tstamp":1527670400,"crdate":1527670400,"deleted":0,"hidden":0,"userId":50,"title":"朋友消息","content":"有人关注了您","jpushContent":"{\"msg_id\":2768640536,\"originalContent\":\"{\\\"sendno\\\":\\\"804758366\\\",\\\"msg_id\\\":\\\"2768640536\\\"}\",\"rateLimitQuota\":600,\"rateLimitRemaining\":596,\"rateLimitReset\":33,\"responseCode\":200,\"resultOK\":true,\"sendno\":804758366}","status":2,"noticeId":0,"type":0}]
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
         * id : 64
         * tstamp : 1527760419
         * crdate : 1527760419
         * deleted : 0
         * hidden : 0
         * userId : 49
         * title : 红包消息
         * content : 您的红包被收藏了
         * jpushContent :
         * status : 2
         * noticeId : 0
         * type : 0
         */

        public int id;
        public int tstamp;
        public int crdate;
        public int deleted;
        public int hidden;
        public int userId;
        public String title;
        public String content;
        public String jpushContent;
        public int status;
        public int noticeId;
        public int type;

    }
}
