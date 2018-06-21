package com.wlhb.hongbao.ui.flingswipe;

import java.util.List;

/**
 * Created by Shall on 2015-06-23.
 */
public class CardMode {
    private String name;
    private int year;
    private String images;

    public CardMode(String name,  String images) {
        this.name = name;
        this.images = images;
    }

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getImages() {
        return images;
    }
}
