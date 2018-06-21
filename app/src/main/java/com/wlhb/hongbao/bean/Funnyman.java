package com.wlhb.hongbao.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/5/11/011.
 */

public class Funnyman implements Serializable {


    /**
     * currentPage : 0
     * pageSize : 10
     * totalCount : 7
     * pageParameter : {"userId":47}
     * dataList : [{"image":"http://www.blbuy.com.cn:80/uploads/member/201805/29/6d6d8325-2902-49ae-9a26-1df1ca0aa9fd.png","isLike":0,"gender":1,"id":52,"username":"小黑屋"},{"image":"http://thirdwx.qlogo.cn/mmopen/vi_32/G0U7VYeOiaeBzlWLicTiafbmuQckBSp6yYrhtuZQ42ICTiaBvcI9MPjczjvOSkHtEia3V0GUE1ME4Vwd40NibUe9Rm8Q/132","isLike":0,"gender":1,"id":51,"username":"Zombie"},{"image":"http://thirdwx.qlogo.cn/mmopen/vi_32/q13pI6KWje1xlQUWib9tJoUoibmsnR8gnjJUQOrsn7sa8R1nTp4udO31kxHRq7G4Z39BNjycoZvxbGeFRiaoocxCg/132","isLike":1,"gender":2,"id":50,"username":"陈晨"},{"image":"http://thirdwx.qlogo.cn/mmopen/vi_32/9YRh0gX8z9xZTdn5A2oHibibmGfibIGeMrcvB22gmvYfLkD9uRtiauSpWia6w1fLT0HTVLXbRQ7ohVccZdMvImoCJ5A/132","isLike":0,"gender":1,"id":49,"username":"Kef"},{"image":"http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKYXcHxLzlqs4xoeGC6Ikn3T68OEEADYjibmcXrAib69PpKmia6avLRicTxoL3IjGzbgLuvfFRXpNBnKA/132","isLike":0,"gender":1,"id":48,"username":"哎比利"},{"image":"http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIJd4JUNATWS8UMKDj8nicotG4CFnntw6WgfbIMrBC1CZHUHNBupc5awUIXUTfQhYAoDhyN20UOqqw/132","isLike":0,"gender":1,"id":46,"username":"小亮Lee"},{"image":"http://thirdwx.qlogo.cn/mmopen/vi_32/LBJfjMd2Z8EpdH3Klgkkm6ZLV25Ba1johcO2uWwxXBaOu6iaEupIsBnWPyb9NwJK7XyeRN7sKKZ1kgMaSQEEugw/132","isLike":0,"gender":1,"id":45,"username":"郑文斌"}]
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
         * image : http://www.blbuy.com.cn:80/uploads/member/201805/29/6d6d8325-2902-49ae-9a26-1df1ca0aa9fd.png
         * isLike : 0
         * gender : 1
         * id : 52
         * username : 小黑屋
         */

        public String image;
        public int isLike;
        public int gender;
        public int id;
        public String username;

    }
}
