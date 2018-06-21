package com.wlhb.hongbao.app;

/**
 * Created by hetwen on 16/8/17.
 */
public interface IConfiger {

    public String readString(String key, String defaultValue);

    public void writeString(String key, String value);


    public int readInt(String key, int defaultValue);

    public void writeInt(String key, int value);

    public float readFloat(String key, float defaultValue);

    public void writeFloat(String key, float value);

    public boolean readBoolean(String key, boolean defaultValue);

    public void writeBoolean(String key, boolean value);

}
