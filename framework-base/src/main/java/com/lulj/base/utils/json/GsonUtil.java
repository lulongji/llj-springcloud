package com.lulj.base.utils.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.lulj.base.constants.CommonConstants;
import com.lulj.base.exception.CommonException;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Type;

/**
 * @author lu
 */
public class GsonUtil {

    /**
     * 返回JSON字符串
     */
    public static final Gson gson = new GsonBuilder().create();
    public static final JsonParser jsonParser = new JsonParser();


    public static String getJsonString(Object obj) throws CommonException {
        if (obj == null) {
            return "";
        } else {
            try {
                return gson.toJson(obj);
            } catch (Exception e) {
                throw new CommonException(CommonConstants.ValType.FAILURE_CODE, e.getMessage());
            }
        }
    }

    public static <T> T getTString(String json, Type typeOfT) throws CommonException {
        if (StringUtils.isEmpty(json)) {
            return null;
        } else {
            try {
                return gson.fromJson(json, typeOfT);
            } catch (Exception e) {
                throw new CommonException(CommonConstants.ValType.FAILURE_CODE, e.getMessage());
            }
        }
    }

    public static Gson getGson() {
        return gson;
    }

    public static JsonParser getJsonParser() {
        return jsonParser;
    }

    public static JsonElement getJsonElement(String json) {
        return jsonParser.parse(json);
    }

}
