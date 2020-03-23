package com.lulongji.base.wechat.common;

import com.alibaba.fastjson.JSONObject;
import com.lulongji.base.wechat.model.WeChatOauth2Token;
import com.lulongji.base.wechat.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

/**
 * Created by lu
 * 描述: 微信通用工具类 </br>
 * 发布版本：V1.0 </br>
 */
@Slf4j
public class WeChatUtil {

    /**
     * 获取微信授权信息
     *
     * @param appId
     * @param appSecret
     * @param code
     * @return
     */
    public static WeChatOauth2Token getOauth2AccessToken(String appId, String appSecret, String code) throws Exception {
        WeChatOauth2Token wat = new WeChatOauth2Token();
        String requestUrl = getAuthUrl(appId, appSecret, code);
        JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "GET", null);
        if (null != jsonObject) {
            try {
                wat = new WeChatOauth2Token();
                wat.setAccessToken(jsonObject.getString("access_token"));
                wat.setExpiresIn(jsonObject.getInteger("expires_in"));
                wat.setRefreshToken(jsonObject.getString("refresh_token"));
                wat.setOpenId(jsonObject.getString("openid"));
                wat.setScope(jsonObject.getString("scope"));
            } catch (Exception e) {
                wat = null;
                String errorCode = jsonObject.getString("errcode");
                String errorMsg = jsonObject.getString("errmsg");
                log.error("获取网页授权凭证失败 errcode{},errMsg", errorCode, errorMsg);
            }

        }
        return wat;
    }

    /**
     * 将请求参数转换为xml格式的string
     *
     * @param parameters 请求参数
     * @return
     * @Description：将请求参数转换为xml格式的string
     */
    @SuppressWarnings("rawtypes")
    public static String getRequestXml(SortedMap<Object, Object> parameters) {
        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            if ("attach".equalsIgnoreCase(k) || "body".equalsIgnoreCase(k)) {
                sb.append("<" + k + ">" + "<![CDATA[" + v + "]]></" + k + ">");
            } else {
                sb.append("<" + k + ">" + v + "</" + k + ">");
            }
        }
        sb.append("</xml>");
        return sb.toString();
    }

    /**
     * 返回给微信的参数
     *
     * @param return_code 返回编码
     * @param return_msg  返回信息
     * @return
     * @Description：返回给微信的参数
     */
    public static String setXML(String return_code, String return_msg) {
        return "<xml><return_code><![CDATA[" + return_code + "]]></return_code><return_msg><![CDATA[" + return_msg
                + "]]></return_msg></xml>";
    }

    /**
     * url编码
     *
     * @param source
     * @return
     */
    public static String urlEncodeUTF8(String source) {
        String result = source;
        try {
            result = java.net.URLEncoder.encode(source, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 获取openId
     *
     * @param code
     * @param appid
     * @param secret
     * @return
     * @throws Exception
     */
    public static String getOpenId(String code, String appid, String secret) throws Exception {
        log.info("用户同意授权后的code: " + code);
        WeChatOauth2Token wt = getOauth2AccessToken(appid, secret, code);
        String openId = wt.getOpenId();
        return openId;
    }


    /**
     * 生成用于获取access_token的Code的Url
     *
     * @param redirectUrl
     * @return
     */
    public static String getRequestCodeUrl(String redirectUrl, String scope, String state, String appid) {
        return String.format(WxUrl.wxrest.WX_AUTHORIZE, appid, redirectUrl, scope, state);
    }

    public static String getAuthUrl(String appId, String appSecret, String code) {
        return String.format(WxUrl.wxrest.AUTH_URL, appId, appSecret, code);
    }


}

