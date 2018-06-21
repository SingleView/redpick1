package com.wlhb.hongbao.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/5/9/009.
 */

public class Postinfo implements Serializable {


    /**
     * id : 19
     * tstamp : 1526805052
     * crdate : 1526284400
     * cruserId : 0
     * deleted : false
     * hidden : false
     * sorting : 0.0
     * hit : 107
     * hot : false
     * top : false
     * name : 你好啊你好啊
     * communityId : 2
     * userId : 9
     * type : 1
     * image : 101,102,103,
     * remark : null
     * province :
     * city :
     * area :
     * commentNum : 10
     * likeNum : 1
     * content : null
     * imageList : ["http://119.23.238.17:8080/uploads/communitycontents/201805/14/b93333d2-1e1b-499e-910e-82bf88f588c6.png","http://119.23.238.17:8080/uploads/communitycontents/201805/14/1f8bc55c-7517-43a1-b64c-dc0769a7c3f8.png","http://119.23.238.17:8080/uploads/communitycontents/201805/14/ce920d21-3fcf-45e3-bf3d-011af1f8b489.png"]
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
    public int communityId;
    public int userId;
    public int type;
    public String image;
    public Object remark;
    public String province;
    public String city;
    public String area;
    public int commentNum;
    public int likeNum;
    public String content;
    public List<String> imageList;


}
