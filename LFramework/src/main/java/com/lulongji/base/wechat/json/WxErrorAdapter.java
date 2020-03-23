package com.lulongji.base.wechat.json;

import com.google.gson.*;
import com.lulongji.base.wechat.error.WxError;

import java.lang.reflect.Type;

/**
 * @author lu
 */
public class WxErrorAdapter implements JsonDeserializer<WxError> {

    @Override
    public WxError deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        WxError.WxErrorBuilder errorBuilder = WxError.builder();
        JsonObject wxErrorJsonObject = json.getAsJsonObject();

        if (wxErrorJsonObject.get("errcode") != null && !wxErrorJsonObject.get("errcode").isJsonNull()) {
            errorBuilder.errorCode(GsonHelper.getAsPrimitiveInt(wxErrorJsonObject.get("errcode")));
        }
        if (wxErrorJsonObject.get("errmsg") != null && !wxErrorJsonObject.get("errmsg").isJsonNull()) {
            errorBuilder.errorMsg(GsonHelper.getAsString(wxErrorJsonObject.get("errmsg")));
        }

        errorBuilder.json(json.toString());

        return errorBuilder.build();
    }

}
