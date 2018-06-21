package com.wlhb.hongbao.http;

import com.alibaba.fastjson.JSON;
import com.wlhb.hongbao.bean.Bindmobile;
import com.wlhb.hongbao.bean.BroadcastData;
import com.wlhb.hongbao.bean.Category;
import com.wlhb.hongbao.bean.Catelists;
import com.wlhb.hongbao.bean.Commentlist;
import com.wlhb.hongbao.bean.Communityinfo;
import com.wlhb.hongbao.bean.Communitylists;
import com.wlhb.hongbao.bean.Communitymsglists;
import com.wlhb.hongbao.bean.Countmessage;
import com.wlhb.hongbao.bean.Dynamic;
import com.wlhb.hongbao.bean.Everydaycount;
import com.wlhb.hongbao.bean.Everydaytask;
import com.wlhb.hongbao.bean.Favorite;
import com.wlhb.hongbao.bean.Funnyman;
import com.wlhb.hongbao.bean.Help;
import com.wlhb.hongbao.bean.History;
import com.wlhb.hongbao.bean.LikeLists;
import com.wlhb.hongbao.bean.Lists;
import com.wlhb.hongbao.bean.Memberlists;
import com.wlhb.hongbao.bean.Messageinfo;
import com.wlhb.hongbao.bean.Myblack;
import com.wlhb.hongbao.bean.Mycommunitymsg;
import com.wlhb.hongbao.bean.Myreceive;
import com.wlhb.hongbao.bean.Myreceivecount;
import com.wlhb.hongbao.bean.Normalreg;
import com.wlhb.hongbao.bean.OneKm;
import com.wlhb.hongbao.bean.PacketInfo;
import com.wlhb.hongbao.bean.Packetlists;
import com.wlhb.hongbao.bean.Postcommentslists;
import com.wlhb.hongbao.bean.Postinfo;
import com.wlhb.hongbao.bean.Postlikelists;
import com.wlhb.hongbao.bean.Postlist;
import com.wlhb.hongbao.bean.Publish;
import com.wlhb.hongbao.bean.Receive;
import com.wlhb.hongbao.bean.Receivelists;
import com.wlhb.hongbao.bean.Recommondcount;
import com.wlhb.hongbao.bean.Recommondlists;
import com.wlhb.hongbao.bean.Tradingdetails;
import com.wlhb.hongbao.bean.Upload;
import com.wlhb.hongbao.bean.Views;
import com.wlhb.hongbao.bean.Viplist;
import com.wlhb.hongbao.bean.Wallet;
import com.wlhb.hongbao.bean.Wxregist;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2018/4/19/019.
 */

public interface APIService {


    @GET(API.LOGIN)
    Call<BaseResponse<Wxregist>> login(
            @Query("username") String username,
            @Query("code") String code,
            @Query("loginUuid") String loginUuid

    );

    @GET(API.BROADCAST)
    Call<BaseResponse<BroadcastData>> broadcastdata(
            @Query("token") String token
    );

    @GET(API.HELP)
    Call<BaseResponse<List<Help>>> help(

    );


    @GET(API.ABOUT)
    Call<BaseResponse<String>> about(

    );


    @GET(API.COMMUNITYMSGLISTS)
    Call<BaseResponse<Communitymsglists>> communitymsglists(
            @Query("token") String token,
            @Query("type") int type,
            @Query("communityId") int communityId
    );

    @GET(API.SENDSMS)
    Call<BaseResponse<JSON>> sendsms(
            @Query("mobile") String mobile,
            @Query("type") int type
    );

    @GET(API.REGISTER)
    Call<BaseResponse<JSON>> register(
            @Query("mobile") String mobile,
            @Query("password") String password,
            @Query("repassword") String repassword,
            @Query("code") String code,
            @Query("agreement") int agreement

    );

    @GET(API.COMMUNITYMSG)
    Call<BaseResponse<JSON>> communitymsg(
            @Query("token") String token,
            @Query("content") String content,
            @Query("communityId") int communityId,
            @Query("type") int type

    );

    @GET(API.FEEDBACK)
    Call<BaseResponse<JSON>> feedback(
            @Query("token") String token,
            @Query("content") String content

    );

    @GET(API.TRANSPASSWORD)
    Call<BaseResponse<JSON>> transpassword(
            @Query("token") String token,
            @Query("password") String password,
            @Query("repassword") String repassword

    );


    @GET(API.WITHDRAWAL)
    Call<BaseResponse<JSON>> withdrawal(
            @Query("token") String token,
            @Query("money") String money
    );


    @GET(API.COMPLAIN)
    Call<BaseResponse<JSON>> complain(
            @Query("token") String token,
            @Query("content") String content,
            @Query("packetId") int packetId

    );


    @POST(API.PUBLISH)
    @FormUrlEncoded
    Call<BaseResponse<Publish>> publish(
            @Field("token") String token,
            @Field("content") String content,
            @Field("money") double money,
            @Field("quantity") String quantity,
            @Field("province") String province,
            @Field("city") String city,
            @Field("area") String area,
            @Field("lng") float lng,
            @Field("lat") float lat,
            @Field("receive_type") int receive_type,
            @Field("title") String title,
            @Field("link") String link,
            @Field("type") int type,
            @Field("listImg[0]") String listImg,
            @Field("category") int category


    );

    @POST(API.POSTSAVE)
    @FormUrlEncoded
    Call<BaseResponse<JSON>> postsave(
            @Field("token") String token,
            @Field("communityId") int communityId,
            @Field("name") String name,
            @Field("type") int type,
            @Field("content") String content,
            @Field("id") String id,
            @Field("listImg[0]") String imageList

    );

    @POST(API.COMMUNITYSAVE)
    @FormUrlEncoded
    Call<BaseResponse<JSON>> communitysave(
            @Field("token") String token,
            @Field("name") String name,
            @Field("image") String image,
            @Field("remark") String remark,
            @Field("content") String content,
            @Field("province") String province,
            @Field("city") String city,
            @Field("area") String area,
            @Field("categoryId") int categoryId,
            @Field("id") String id

    );

    @GET(API.PAY)
    Call<BaseResponse<JSON>> pay(
            @Query("token") String token,
            @Query("type") String type,
            @Query("number") String number

    );


    @GET(API.INFO)
    Call<BaseResponse<JSON>> info(
            @Query("token") String token,
            @Query("id") int id

    );

    @GET(API.FUNNYMAN)
    Call<BaseResponse<Funnyman>> funnyman(
            @Query("token") String token
    );

    @GET(API.CATEGORY)
    Call<BaseResponse<List<Category>>> category(
            @Query("token") String token
    );

    @GET(API.MYCOMMUNITYMSG)
    Call<BaseResponse<Mycommunitymsg>> mycommunitymsg(
            @Query("token") String token
    );

    @GET(API.COUNTMESSAGE)
    Call<BaseResponse<Countmessage>> countmessage(
            @Query("token") String token
    );

    @GET(API.GETBYGPS)
    Call<BaseResponse<List<OneKm>>> getbygps(
            @Query("token") String token,
            @Query("lng") float lng,
            @Query("lat") float lat
    );

    @GET(API.MESSAGEINFO)
    Call<BaseResponse<Messageinfo>> messageinfo(
            @Query("token") String token,
            @Query("type") int type,
            @Query("id") int id
    );


    @GET(API.CHECKGPS)
    Call<BaseResponse<JSON>> checkgps(
            @Query("token") String token,
            @Query("lng") float lng,
            @Query("lat") float lat
    );

    @GET(API.PACKETINFO)
    Call<BaseResponse<PacketInfo>> packetinfo(
            @Query("token") String token,
            @Query("id") int id
    );

    @GET(API.POSTINFO)
    Call<BaseResponse<Postinfo>> postinfo(
            @Query("token") String token,
            @Query("id") int id
    );

    @GET(API.POSTDEL)
    Call<BaseResponse<JSON>> postdel(
            @Query("token") String token,
            @Query("id") int id
    );

    @GET(API.FUNNYMANLIKE)
    Call<BaseResponse<JSON>> funnymanlike(
            @Query("token") String token,
            @Query("id") int id
    );

    @GET(API.POSTLIKE)
    Call<BaseResponse<JSON>> postlike(
            @Query("token") String token,
            @Query("id") int id
    );

    @GET(API.LISTS)
    Call<BaseResponse<Lists>> lists(
            @Query("token") String token,
            @Query("userId") int userId
    );


    @GET(API.RECEIVELISTS)
    Call<BaseResponse<Receivelists>> receivelists(
            @Query("token") String token,
            @Query("id") int id
    );

    @GET(API.POSTLIKELISTS)
    Call<BaseResponse<Postlikelists>> postlikelists(
            @Query("token") String token,
            @Query("id") int id
    );


    @GET(API.RECEIVE)
    Call<BaseResponse<Receive>> receive(
            @Query("token") String token,
            @Query("id") int id
    );


    @GET(API.VIEWS)
    Call<BaseResponse<Views>> views(
            @Query("token") String token
    );


    @GET(API.VIPLIST)
    Call<BaseResponse<List<Viplist>>> viplist(
            @Query("token") String token
    );


    @GET(API.UPLOAD)
    Call<BaseResponse<Upload>> upload(
            @Query("token") String token,
            @Query("image") String image
    );

    @GET(API.RECHARGE)
    Call<BaseResponse<JSON>> recharge(
            @Query("token") String token,
            @Query("type") int type,
            @Query("money") double money,
            @Query("payType") String payType,
            @Query("id") Integer id,
            @Query("password") String password
    );


    @GET(API.COMMENTLIST)
    Call<BaseResponse<Commentlist>> commentlist(
            @Query("token") String token,
            @Query("id") int id
    );

    @GET(API.MEMBERLIKE)
    Call<BaseResponse<JSON>> memberlike(
            @Query("token") String token,
            @Query("id") int id
    );

    @GET(API.POSTCOMMENTSLISTS)
    Call<BaseResponse<Postcommentslists>> postcommentslists(
            @Query("token") String token,
            @Query("currentPage") int currentPage,
            @Query("pageSize") int pageSize,
            @Query("ccid") int ccid
    );


    @GET(API.COMMENT)
    Call<BaseResponse<JSON>> comment(
            @Query("token") String token,
            @Query("packetId") int packetId,
            @Query("content") String content,
            @Query("pid") Integer pid,
            @Query("type") int type
    );

    @GET(API.POSTCOMMENTS)
    Call<BaseResponse<JSON>> postcomments(
            @Query("token") String token,
            @Query("ccid") int ccid,
            @Query("content") String content,
            @Query("type") int type,
            @Query("pid") Integer pid
    );


    @GET(API.DYNAMIC)
    Call<BaseResponse<Dynamic>> dynamic(
            @Query("token") String token
    );


    @GET(API.EDIT)
    Call<BaseResponse<JSON>> edit(
            @Query("token") String token,
            @Query("nickname") String nickname,
            @Query("personalMark") String personalMark,
            @Query("gender") Integer gender,
            @Query("address") String address,
            @Query("pushType") Integer pushType
    );


    @GET(API.LIKE)
    Call<BaseResponse<JSON>> like(
            @Query("token") String token,
            @Query("id") int id
    );

    @GET(API.PACKETFAVORITE)
    Call<BaseResponse<JSON>> packetfavorite(
            @Query("token") String token,
            @Query("id") int id
    );


    @GET(API.PULLBLACK)
    Call<BaseResponse<JSON>> pullblack(
            @Query("token") String token,
            @Query("userId") int userId
    );

    @GET(API.REMOVEBLACK)
    Call<BaseResponse<JSON>> removeblack(
            @Query("token") String token,
            @Query("userId") int userId
    );

    @GET(API.WALLET)
    Call<BaseResponse<Wallet>> wallet(
            @Query("token") String token
    );

    @GET(API.TRADINGDETAILS)
    Call<BaseResponse<Tradingdetails>> tradingdetails(
            @Query("token") String token
    );

    @GET(API.FAVORITE)
    Call<BaseResponse<Favorite>> favorite(
            @Query("token") String token
    );

    @GET(API.MYBLACK)
    Call<BaseResponse<Myblack>> myblack(
            @Query("token") String token
    );

    @GET(API.LIKELISTS)
    Call<BaseResponse<LikeLists>> likeLists(
            @Query("token") String token,
            @Query("type") int type
    );


    @GET(API.RECOMMONDCOUNT)
    Call<BaseResponse<Recommondcount>> recommondcount(
            @Query("token") String token
    );

    @GET(API.RECOMMONDLISTS)
    Call<BaseResponse<Recommondlists>> recommondlists(
            @Query("token") String token,
            @Query("type") int type

    );

    @GET(API.HISTORY)
    Call<BaseResponse<History>> history(
            @Query("token") String token

    );

    @GET(API.COMMUNITYINFO)
    Call<BaseResponse<Communityinfo>> communityinfo(
            @Query("token") String token,
            @Query("id") int id

    );

    @GET(API.ADD)
    Call<BaseResponse<JSON>> add(
            @Query("token") String token,
            @Query("id") int id

    );

    @GET(API.COMMENTDEL)
    Call<BaseResponse<JSON>> commentdel(
            @Query("token") String token,
            @Query("id") int id

    );


    @GET(API.POSTCOMMENTSDEL)
    Call<BaseResponse<JSON>> postcommentsdel(
            @Query("token") String token,
            @Query("id") int id

    );

    @GET(API.MYRECEIVECOUNT)
    Call<BaseResponse<Myreceivecount>> myreceivecount(
            @Query("token") String token

    );

    @GET(API.MYRECEIVE)
    Call<BaseResponse<Myreceive>> myreceive(
            @Query("token") String token

    );


    @GET(API.EVERYDAYCOUNT)
    Call<BaseResponse<Everydaycount>> everydaycount(
            @Query("token") String token

    );

    @GET(API.EVERYDAYTASK)
    Call<BaseResponse<List<Everydaytask>>> everydaytask(
            @Query("token") String token

    );

    @GET(API.PACKETLISTS)
    Call<BaseResponse<Packetlists>> packetlists(
            @Query("token") String token,
            @Query("type") int type,
            @Query("province") String province,
            @Query("city") String city,
            @Query("area") String area,
            @Query("lng") double lng,
            @Query("lat") double lat,
            @Query("category") int category


    );

    @GET(API.MEMBERLISTS)
    Call<BaseResponse<Memberlists>> memberlists(
            @Query("token") String token,
            @Query("gender") int gender,
            @Query("lng") double lng,
            @Query("lat") double lat

    );


    @GET(API.POSTLIST)
    Call<BaseResponse<Postlist>> postlist(
            @Query("token") String token,
            @Query("currentPage") int currentPage,
            @Query("pageSize") int pageSize,
            @Query("type") int type,
            @Query("communityId") int communityId,
            @Query("province") String province,
            @Query("city") String city,
            @Query("area") String area

    );

    @GET(API.COMMUNITYLISTS)
    Call<BaseResponse<Communitylists>> communitylists(
            @Query("token") String token,
            @Query("categoryId") Integer categoryId,
            @Query("userId") Integer userId

    );

    @GET(API.CATELISTS)
    Call<BaseResponse<Catelists>> catelists(
            @Query("token") String token

    );

    @GET(API.WXREGIST)
    Call<BaseResponse<Wxregist>> wxregist(
            @Query("accessToken") String accessToken,
            @Query("expiresIn") String expiresIn,
            @Query("refreshToken") String refreshToken,
            @Query("openid") String openid,
            @Query("scope") String scope,
            @Query("unionid") String unionid,
            @Query("loginUuid") String loginUuid

    );

    @GET(API.BINDMOBILE)
    Call<BaseResponse<Bindmobile>> bindmobile(
            @Query("token") String token,
            @Query("mobile") String mobile,
            @Query("code") String code

    );

    @GET(API.NORMALREG)
    Call<BaseResponse<Normalreg>> normalreg(


    );
}
