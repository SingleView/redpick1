package com.wlhb.hongbao.http;

/**
 * Created by Administrator on 2018/4/19/019.
 */

public class API {
    public static final String BASE_URL = " http://www.blbuy.com.cn/hongbao/";

    public static class Event {
        public static final int REFRESH = 0x001;
        public static final int TOKEN_TIMEOUT = 0x009;

    }

    static final String BROADCAST = BASE_URL + "Api/broadcast";  //红包顶部广播
    static final String SENDSMS = BASE_URL + "Api/sendSms";  //短信验证
    static final String HELP = BASE_URL + "Api/help";  //帮助中心
    static final String ABOUT = BASE_URL + "Api/about";  //关于网龄
    static final String MESSAGEINFO = BASE_URL + "Api/member/messageInfo";  //消息详情
    static final String NORMALREG = BASE_URL + "Api/normalReg";  //是否普通等录

    static final String LOGIN = BASE_URL + "Api/login";  //登录
    static final String REGISTER = BASE_URL + "Api/register";  //注册
    static final String FORGETPASSWORD = BASE_URL + "Api/forgetPassword";  //忘记密码
    static final String INFO = BASE_URL + "Api/member/info";  //用户信息
    static final String VIEWS = BASE_URL + "Api/member/views";  //用户访客列表
    static final String EDIT = BASE_URL + "Api/member/edit";  //用户信息修改
    static final String UPLOAD = BASE_URL + "Api/member/upload";  //用户信息修改上传图像（新）
    static final String DYNAMIC = BASE_URL + "Api/member/dynamic";  //动态
    static final String PULLBLACK = BASE_URL + "Api/member/pullBlack";  //加入黑名单
    static final String REMOVEBLACK = BASE_URL + "Api/member/removeBlack";  //移除黑名单
    static final String WALLET = BASE_URL + "Api/member/wallet";  //钱包
    static final String TRADINGDETAILS = BASE_URL + "Api/member/tradingDetails";  //交易记录
    static final String HISTORY = BASE_URL + "Api/member/history";  //领取历史
    static final String FAVORITE = BASE_URL + "Api/member/favorite";  //收藏列表
    static final String RECOMMONDCOUNT = BASE_URL + "Api/member/recommondCount";  //推荐朋友收益
    static final String RECOMMONDLISTS = BASE_URL + "Api/member/recommondLists";  //推荐朋友列表
    static final String MYBLACK = BASE_URL + "Api/member/myBlack";  //黑名单
    static final String LIKELISTS = BASE_URL + "Api/member/likeLists";  //我的关注／粉丝
    static final String PAY = BASE_URL + "Api/member/pay";  //支付
    static final String FEEDBACK = BASE_URL + "Api/member/feedback";  //意见反馈
    static final String VIPLIST = BASE_URL + "Api/member/vipList";  //VIP列表
    static final String RECHARGE = BASE_URL + "Api/member/recharge";  //红包续费／购买vip／诚意金（新）
    static final String MEMBERLIKE = BASE_URL + "Api/member/like";  //关注/取消关注
    static final String WXREGIST = BASE_URL + "Api/wxRegist";  //微信注册
    static final String BINDMOBILE = BASE_URL + "Api/member/bindMobile";  //绑定手机号
    static final String TRANSPASSWORD = BASE_URL + "Api/member/transPassword";  //设置交易密码
    static final String COUNTMESSAGE = BASE_URL + "Api/member/countMessage";  //消息统计
    static final String WITHDRAWAL = BASE_URL + "Api/member/withdrawal";  //提现


    static final String GETBYGPS = BASE_URL + "Api/packet/getByGps";  //红包-1公里
    static final String PUBLISH = BASE_URL + "Api/packet/publish";  //红包-发布
    static final String PACKETINFO = BASE_URL + "Api/packet/info";  //红包详情
    static final String LISTS = BASE_URL + "Api/packet/lists";  //红包列表
    static final String RECEIVELISTS = BASE_URL + "Api/packet/receiveLists";  //红包领取列表
    static final String RECEIVE = BASE_URL + "Api/packet/receive";  //红包领取
    static final String COMMENT = BASE_URL + "Api/packet/comment";  //红包评论
    static final String COMMENTLIST = BASE_URL + "Api/packet/commentList";  //红包评论列表
    static final String LIKE = BASE_URL + "Api/packet/like";  //红包点赞
    static final String PACKETFAVORITE = BASE_URL + "Api/packet/favorite";  //红包收藏
    static final String COMMENTDEL = BASE_URL + "Api/packet/commentDel";  //红包评论删除
    static final String MYRECEIVE = BASE_URL + "Api/packet/myReceive";  //我领取的红包
    static final String COMPLAIN = BASE_URL + "Api/packet/complain";  //红包投诉
    static final String CATEGORY = BASE_URL + "Api/packet/category";  //红包分类
    static final String EVERYDAYTASK = BASE_URL + "Api/packet/everyDayTask";  //每日任务
    static final String EVERYDAYCOUNT = BASE_URL + "Api/packet/everyDayCount";  //每日任务统计
    static final String MYRECEIVECOUNT = BASE_URL + "Api/packet/myReceiveCount";  //我领取的红包统计
    static final String CHECKGPS = BASE_URL + "Api/packet/checkGps";  //我领取的红包统计


    static final String COMMUNITYLISTS = BASE_URL + "Api/community/lists";  //社区列表
    static final String PACKETLISTS = BASE_URL + "Api/community/packetLists";  //社区动态列表
    static final String MEMBERLISTS = BASE_URL + "Api/community/memberLists";  //社区附近的人
    static final String ADD = BASE_URL + "Api/community/add";  //加入社区
    static final String FUNNYMAN = BASE_URL + "Api/community/funnyman";  //有趣的人
    static final String FUNNYMANLIKE = BASE_URL + "Api/community/funnymanLike";  //有趣的人 喜欢／不喜欢
    static final String COMMUNITYSAVE = BASE_URL + "Api/community/communitySave";  //新建／修改社区
    static final String COMMUNITYINFO = BASE_URL + "Api/community/communityInfo";  //社区详情
    static final String COMMUNITYDEL = BASE_URL + "Api/community/communityDel";  //社区 删除
    static final String COMMUNITYMSG = BASE_URL + "Api/community/communityMsg";  //投诉建议／通知
    static final String COMMUNITYMSGINFO = BASE_URL + "Api/community/communityMsgInfo";  //投诉建议／通知 详情
    static final String COMMUNITYMSGLISTS = BASE_URL + "Api/community/communityMsgLists";  //投诉建议／通知 列表
    static final String FUNNYMANREBACK = BASE_URL + "Api/community/funnymanReback";  //有趣的人撤回
    static final String POSTLIST = BASE_URL + "Api/community/postList";  //社区帖子列表
    static final String POSTSAVE = BASE_URL + "Api/community/postSave";  //社区帖子发布或编辑
    static final String POSTINFO = BASE_URL + "Api/community/postInfo";  //社区帖子详情
    static final String POSTDEL = BASE_URL + "Api/community/postDel";  //社区帖子删除
    static final String POSTLIKE = BASE_URL + "Api/community/postLike";  //社区帖子点赞
    static final String POSTLIKELISTS = BASE_URL + "Api/community/postLikeLists";  //社区帖子点赞列表
    static final String POSTCOMMENTS = BASE_URL + "Api/community/postComments";  //社区帖子评论
    static final String POSTCOMMENTSDEL = BASE_URL + "Api/community/postCommentsDel";  //社区帖子评论删除
    static final String POSTCOMMENTSLISTS = BASE_URL + "Api/community/postCommentsLists";  //社区帖子评论列表
    static final String CATELISTS = BASE_URL + "Api/community/cateLists";  //社区首页分类列表
    static final String MYCOMMUNITYMSG = BASE_URL + "Api/community/myCommunityMsg";  //我的社区公告



}
