package com.wlhb.hongbao.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/5/19/019.
 */

public class Viplist implements Serializable {


        /**
         * id : 1
         * tstamp : 1526412395
         * crdate : 1526412349
         * cruserId : 1
         * deleted : false
         * hidden : false
         * sorting : 0.0
         * title : 28元/月
         * money : 28.0
         * remark : 额前热情而且
         * expiry : 30
         */

        public int id;
        public int tstamp;
        public int crdate;
        public int cruserId;
        public boolean deleted;
        public boolean hidden;
        public double sorting;
        public String title;
        public double money;
        public String remark;
        public int expiry;

}
