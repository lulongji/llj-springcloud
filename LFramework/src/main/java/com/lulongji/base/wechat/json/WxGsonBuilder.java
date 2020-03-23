package com.lulongji.base.wechat.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lulongji.base.wechat.error.WxError;

/**
 * @author lu
 */
public class WxGsonBuilder {

    public static final GsonBuilder INSTANCE = new GsonBuilder();

    static {
        INSTANCE.disableHtmlEscaping();
        INSTANCE.registerTypeAdapter(WxError.class, new WxErrorAdapter());
    }

    public static Gson create() {
        return INSTANCE.create();
    }

}
