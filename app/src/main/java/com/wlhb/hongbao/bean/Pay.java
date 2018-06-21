package com.wlhb.hongbao.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/5/13/013.
 */

public class Pay implements Serializable {


    /**
     * package : Sign=WXPay
     * appid : wx59462dc0749720d9
     * sign : 40BEEABC7C68015FD2D8A134FF3C1DF4
     * partnerid : 1503328941
     * prepayid : wx19143349874022fe4b3b5d861552115104
     * noncestr : fe5bee67f5f143cdaad48406a68f2928
     * timestamp : 1526711629
     */


    public String appid;
    public String sign;
    public String partnerid;
    public String prepayid;
    public String noncestr;
    public String timestamp;


}
