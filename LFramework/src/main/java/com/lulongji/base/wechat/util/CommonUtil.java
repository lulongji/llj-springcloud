package com.lulongji.base.wechat.util;

import com.alibaba.fastjson.JSONObject;
import com.lulongji.base.rl.common.RedisKeys;
import com.lulongji.base.wechat.util.aes.AesException;
import com.lulongji.base.wechat.util.aes.WXBizMsgCrypt;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.*;
import java.net.ConnectException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by lu on 2017/2/13.
 * <p>
 * Description:常用类
 */
public class CommonUtil {
    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(CommonUtil.class);

    /**
     * 根据内容类型判断文件扩展名
     *
     * @param contentType 内容类型
     * @return
     */
    public static String getFileExt(String contentType) {
        String fileExt = "";
        if ("image/jpeg".equals(contentType)) {
            fileExt = ".jpg";
        } else if ("audio/mpeg".equals(contentType)) {
            fileExt = ".mp3";
        } else if ("audio/amr".equals(contentType)) {
            fileExt = ".amr";
        } else if ("video/mp4".equals(contentType)) {
            fileExt = ".mp4";
        } else if ("video/mpeg4".equals(contentType)) {
            fileExt = ".mp4";
        }
        return fileExt;
    }

    /**
     * 发送https请求
     *
     * @param requestUrl    请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param outputStr     提交的数据
     * @return JSONObject(通过JSONObject.get ( key)的方式获取json对象的属性值)
     */
    public static JSONObject httpsRequest(String requestUrl, String requestMethod, String outputStr) {
        JSONObject jsonObject = null;
        try {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = {new MyX509TrustManager()};
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL url = new URL(requestUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setSSLSocketFactory(ssf);

            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            conn.setRequestMethod(requestMethod);

            // 当outputStr不为null时向输出流写数据
            if (null != outputStr) {
                OutputStream outputStream = conn.getOutputStream();
                // 注意编码格式
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }

            // 从输入流读取返回内容
            InputStream inputStream = conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            StringBuffer buffer = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }

            // 释放资源
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            inputStream = null;
            conn.disconnect();
            jsonObject = JSONObject.parseObject(buffer.toString());
        } catch (ConnectException ce) {
            logger.error("连接超时：{}", ce);
        } catch (Exception e) {
            logger.error("https请求异常：{}", e);
        }
        return jsonObject;
    }


    /**
     * 下载文件
     *
     * @param fileUrl
     * @param storefile
     */
    private static void getData(String fileUrl, File storefile) throws Exception {
        HttpClient client = new HttpClient();
        GetMethod getMethod = new GetMethod(fileUrl);
        BufferedOutputStream bos = null;
        BufferedInputStream bis = null;
        try {
            client.executeMethod(getMethod);
            InputStream in = getMethod.getResponseBodyAsStream();
            bos = new BufferedOutputStream(new FileOutputStream(storefile));
            bis = new BufferedInputStream(in);
            int c;
            while ((c = bis.read()) != -1) {
                bos.write(c);
                bos.flush();
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("文件保存失败!");
        } finally {
            bos.close();
            bis.close();
        }
    }


    public static String sha1(String str) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA");
        md.update(str.getBytes());
        String result = Hex.encodeHexString(md.digest());
        return result;
    }

    public static String md5(String str) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("md5");
        md.update(str.getBytes());
        String result = Hex.encodeHexString(md.digest());
        return result;
    }


    public static WXBizMsgCrypt getWxCrypt(String token, String encodingAesKey, String appId) {
        WXBizMsgCrypt crypt = null;
        try {
            crypt = new WXBizMsgCrypt(token, encodingAesKey, appId);
        } catch (AesException e) {
            e.printStackTrace();
        }
        return crypt;
    }


    public static StringBuilder getRedisKeys(String ytx1, String ytx2, String ytx3, String ytx4) {
        return getRedisKeys(ytx1, ytx2, ytx3).append(ytx4);
    }

    public static StringBuilder getRedisKeys(String ytx1, String ytx2, String ytx3) {
        return getRedisKeys(ytx1, ytx2).append(RedisKeys.ytx.REDIS_SEPARATOR_J).append(ytx3);
    }

    public static StringBuilder getRedisKeys(String ytx1, String ytx2) {
        StringBuilder sb = new StringBuilder();
        sb.append(ytx1);
        sb.append(ytx2);
        return sb;
    }

}
