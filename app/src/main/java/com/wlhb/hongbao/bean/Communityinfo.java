package com.wlhb.hongbao.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/5/9/009.
 */

public class Communityinfo implements Serializable {

    /**
     * id : 1
     * tstamp : 0
     * crdate : 0
     * cruserId : 0
     * deleted : false
     * hidden : false
     * sorting : 0.0
     * hit : 0
     * hot : false
     * top : false
     * name : 有趣的人
     * parentId : 0
     * userId : 0
     * lock : true
     * total : 0
     * image : http://119.23.238.17:8080/uploads/packet/201804/28/cd496e03-4a7f-4198-bffc-fc4b169aa030.png
     * remark :
     * province :
     * city :
     * area :
     * content : null
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
    public int parentId;
    public int userId;
    public boolean lock;
    public int total;
    public String image;
    public String remark;
    public String province;
    public String city;
    public String area;
    public String content;

}
