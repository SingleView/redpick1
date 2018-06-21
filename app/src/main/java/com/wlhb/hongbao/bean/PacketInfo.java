package com.wlhb.hongbao.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/4/25/025.
 */

 public class PacketInfo implements Serializable {


    /**
     * id : 191
     * tstamp : 1527476435
     * crdate : 1527249709
     * cruserId : 0
     * deleted : false
     * hidden : false
     * sorting : 0.0
     * hit : 11
     * like : 0
     * hot : false
     * top : false
     * title :
     * link :
     * receiveType : false
     * image : 433,
     * userId : 53
     * money : 0.1
     * quantity : 5
     * province : 湖北省
     * city : 武汉市
     * area : 洪山区
     * address :
     * lng : 114.4225006104
     * lat : 30.5067005157
     * status : 0
     * type : 1
     * content : 你在一起的？好？好了，
     * commentsNum : 0
     * favoriteNum : 0
     * receiveNum : 3
     * isLike : 0
     * isFavorite : 0
     * userReceiveMoney : 0.04
     * category : 1
     * userName : Jin
     * userImage : http://www.blbuy.com.cn:80/uploads/member/201805/25/241c8335-eb5f-40dc-b4a4-438829f19dbf.png
     * listImg : ["http://www.blbuy.com.cn:80/uploads/packet/201805/25/b322d762-1001-42b3-a1e7-2675f15c32fa.png"]
     * shareUrl : http://www.blbuy.com.cn/hongbao/share?id=0EE46EE44D7ED3BDFD18718A6C4751DD_MTkx
     */

    public int id;
    public int tstamp;
    public int crdate;
    public int cruserId;
    public boolean deleted;
    public boolean hidden;
    public double sorting;
    public int hit;
    public int like;
    public boolean hot;
    public boolean top;
    public String title;
    public String link;
    public boolean receiveType;
    public String image;
    public int userId;
    public double money;
    public int quantity;
    public String province;
    public String city;
    public String area;
    public String address;
    public double lng;
    public double lat;
    public int status;
    public int type;
    public String content;
    public int commentsNum;
    public int favoriteNum;
    public int receiveNum;
    public int isLike;
    public int isFavorite;
    public double userReceiveMoney;
    public int category;
    public String userName;
    public String userImage;
    public String shareUrl;
    public List<String> listImg;

}
