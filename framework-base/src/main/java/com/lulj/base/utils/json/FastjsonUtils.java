package com.lulj.base.utils.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.JSONLibDataFormatSerializer;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.lulj.base.exception.BootException;
import com.lulj.base.exception.ExceptionEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * fastjson工具类
 * </p>
 *
 * @author lu
 * @version 1.0
 */
public class FastjsonUtils {

    /**
     * 日志
     */
    private static Logger log = LoggerFactory.getLogger(FastjsonUtils.class);

    /**
     * 序列化设置
     */
    private static final SerializeConfig config;

    static {
        // 设置JSON序列化格式
        config = new SerializeConfig();
        config.put(java.util.Date.class, new JSONLibDataFormatSerializer()); // 使用和json-lib兼容的日期输出格式
        config.put(java.sql.Date.class, new JSONLibDataFormatSerializer()); // 使用和json-lib兼容的日期输出格式
    }

    /**
     * 序列化特色
     *
     * @author lu 2016年7月1日
     * @param jsonStr
     * @return
     * @throws Exception
     */
    private static final SerializerFeature[] features = {SerializerFeature.WriteMapNullValue, // 输出空置字段
            SerializerFeature.WriteNullListAsEmpty, // list字段如果为null，输出为[]，而不是null
            SerializerFeature.WriteNullNumberAsZero, // 数值字段如果为null，输出为0，而不是null
            SerializerFeature.WriteNullBooleanAsFalse, // Boolean字段如果为null，输出为false，而不是null
            SerializerFeature.DisableCircularReferenceDetect, // 避免循环引用
            SerializerFeature.WriteNullStringAsEmpty // 字符类型字段如果为null，输出为""，而不是null
    };

    /**
     * 检测字符串是否为json格式
     *
     * @param jsonStr
     * @return
     * @throws Exception
     * @author lu 2016年7月1日
     */
    public static boolean checkJsonStr(String jsonStr) throws Exception {
        boolean flag = false;
        try {
            if (StringUtils.hasText(jsonStr)) {
                JSONObject.parse(jsonStr);
                flag = true;
            }
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * <p>
     * 实体对象转换为JSON字符串
     * </p>
     *
     * @param object
     * @return
     * @throws Exception
     * @author lu 2016年7月1日
     */
    public static String beanToJsonString(Object object) throws BootException {
        try {
            return JSON.toJSONString(object, config, features);
        } catch (Exception e) {
            log.error("An error occurred when the entity converted JSON:", e);
            throw new BootException(ExceptionEnum.JSON_CONVERTED_EXCEPTION_1001);
        }
    }

    /**
     * <p>
     * 实体对象转换为JSON字符串
     * </p>
     *
     * @param object
     * @return
     * @throws Exception
     * @author lu 2016年7月1日
     */
    public static Object beanToJson(Object object) throws BootException {
        try {
            return JSON.toJSON(object);
        } catch (Exception e) {
            log.error("An error occurred when the entity converted JSON:", e);
            throw new BootException(ExceptionEnum.JSON_CONVERTED_EXCEPTION_1001);
        }
    }

    /**
     * <p>
     * Map转换为JSON字符串
     * </p>
     *
     * @param map
     * @return
     * @throws Exception
     * @author lu 2016年7月12日
     */
    public static String mapToJson(Map<String, Object> map) throws BootException {
        try {
            return JSON.toJSONString(map, config, features);
        } catch (Exception e) {
            log.error("An error occurred when the entity converted JSON:", e);
            throw new BootException(ExceptionEnum.JSON_CONVERTED_EXCEPTION_1001);
        }
    }

    /**
     * <p>
     * List转换为JSON字符串
     * </p>
     *
     * @param list
     * @return
     * @throws Exception
     * @author lu 2016年7月12日
     */
    public static String listToJson(List<?> list) throws BootException {
        try {
            return JSON.toJSONString(list, config, features);
        } catch (Exception e) {
            log.error("An error occurred when the entity converted JSON:", e);
            throw new BootException(ExceptionEnum.JSON_CONVERTED_EXCEPTION_1001);
        }
    }

    /**
     * <p>
     * array字符串转换为JSON字符串
     * </p>
     *
     * @param array
     * @return
     * @throws Exception
     * @author lu 2016年7月12日
     */
    public static String arrayToJson(Object[] array) throws BootException {
        try {
            return JSON.toJSONString(array, config, features);
        } catch (Exception e) {
            log.error("An error occurred when the entity converted JSON:", e);
            throw new BootException(ExceptionEnum.JSON_CONVERTED_EXCEPTION_1001);
        }
    }

    /**
     * <p>
     * JSON转换为Map
     * </p>
     *
     * @param jsonStr
     * @return
     * @throws Exception
     * @author lu 2015年12月31日
     */
    public static <K, V> Map<K, V> jsonToMap(Class<K> keyClass, Class<V> valueClass, String jsonStr) throws BootException {
        Map<K, V> returnMap = null;
        try {
            if (checkJsonStr(jsonStr)) {
                returnMap = JSON.parseObject(jsonStr, new TypeReference<Map<K, V>>() {
                });
            }
        } catch (Exception e) {
            log.error("An error occurred when the entity converted JSON:", e);
            throw new BootException(ExceptionEnum.JSON_CONVERTED_EXCEPTION_1001);
        }
        return returnMap;
    }

    /**
     * <p>
     * JSON转换为List/p>
     * <p>
     * 注意事项：
     * </p>
     * <ul>
     * <li>list泛型对应实体中含有list属性也可转换</li>
     * </ul>
     *
     * @param className
     * @param jsonStr
     * @return
     * @throws Exception
     * @author lu 2015年12月31日
     */
    public static <T> List<T> jsonToList(Class<T> className, String jsonStr) throws BootException {
        List<T> returnList = null;
        try {
            if (null != className && checkJsonStr(jsonStr)) {
                returnList = JSON.parseArray(jsonStr, className);
            }
        } catch (Exception e) {
            log.error("An error occurred when the entity converted JSON:", e);
            throw new BootException(ExceptionEnum.JSON_CONVERTED_EXCEPTION_1001);
        }
        return returnList;
    }

    /**
     * <p>
     * JSON转换为实体
     * </p>
     * <p>
     * 注意事项：
     * </p>
     * <ul>
     * <li>实体中含有list属性也可转换</li>
     * </ul>
     *
     * @param className
     * @param jsonStr
     * @return
     * @throws Exception
     * @author lu 2016年7月12日
     */
    public static <T> T jsonToBean(Class<T> className, String jsonStr) throws BootException {
        T returnObject = null;
        try {
            if (null != className && checkJsonStr(jsonStr)) {
                returnObject = JSONObject.toJavaObject(JSONObject.parseObject(jsonStr), className);
            }
        } catch (Exception e) {
            log.error("An error occurred when the entity converted JSON:", e);
            throw new BootException(ExceptionEnum.JSON_CONVERTED_EXCEPTION_1001);
        }
        return returnObject;
    }

    /**
     * <p>
     * JSON转换为List<Map>
     * </p>
     *
     * @param keyClass
     * @param valueClass
     * @param jsonStr
     * @return
     * @throws Exception
     * @author lu
     */
    public static <K, V> List<Map<K, V>> jsonToListMap(Class<K> keyClass, Class<V> valueClass, String jsonStr)
            throws BootException {
        List<Map<K, V>> returnListMap = null;
        try {
            if (null != keyClass && null != valueClass && checkJsonStr(jsonStr)) {
                returnListMap = JSON.parseObject(jsonStr, new TypeReference<List<Map<K, V>>>() {
                });
            }
        } catch (Exception e) {
            log.error("An error occurred when the entity converted JSON:", e);
            throw new BootException(ExceptionEnum.JSON_CONVERTED_EXCEPTION_1001);
        }
        return returnListMap;
    }

    /**
     * <p>
     * JSON转换为数组
     * </p>
     *
     * @param className
     * @param jsonStr
     * @return
     * @throws Exception
     * @author lu 2016年7月12日
     */
    public static Object[] jsonToArray(Class<?> className, String jsonStr) throws BootException {
        Object[] returnArray = null;
        try {
            if (null != className && checkJsonStr(jsonStr)) {
                returnArray = JSON.parseArray(jsonStr).toArray();
            }
        } catch (Exception e) {
            log.error("An error occurred when the entity converted JSON:", e);
            throw new BootException(ExceptionEnum.JSON_CONVERTED_EXCEPTION_1001);
        }
        return returnArray;
    }

    public static void main(String[] args) throws Exception {

    }
}
