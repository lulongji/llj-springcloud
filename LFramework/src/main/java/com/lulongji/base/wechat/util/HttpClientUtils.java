package com.lulongji.base.wechat.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author lu
 */
public class HttpClientUtils {

    private static CloseableHttpClient closeableHttpClient = HttpClients.createDefault();

    private static RequestConfig requestConfig;

    public static void main(String args[]) {
        String str = "{\"agent\":\"\",\"called\":\"02151265776\",\"caller\":\"87978100000008\",\"cmd\":\"authen\",\"direction\":0,\"origcalled\":\"\",\"service\":1,\"stationno\":1}";
        long start = System.currentTimeMillis();
        for (int i = 0; i < 500; i++) {
            System.out.println(post("http://192.168.177.220:8080/aaa/service", str));
            System.out.println(i);
        }
        System.out.println(System.currentTimeMillis() - start);
    }

    public static String post(String url, String param) {


        HttpPost httpPost = new HttpPost(url);

        StringEntity stringentity = new StringEntity(param, "UTF-8");
        stringentity.setContentType("text/xml;charset=UTF-8");
        stringentity.setContentEncoding("UTF-8");
        httpPost.setEntity(stringentity);
        try {
            HttpResponse httpResponse = closeableHttpClient.execute(httpPost);
            // 获取响应消息实体
            HttpEntity entity = httpResponse.getEntity();
            // 响应状态
            // System.out.println("status:" + httpResponse.getStatusLine());
            // 判断响应实体是否为空
            if (entity != null) {
                // System.out.println("contentEncoding:" + entity.getContentEncoding());
                // System.out.println("response content:" + EntityUtils.toString(entity));
                return EntityUtils.toString(entity, "UTF-8");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            httpPost.releaseConnection();
        }
        return null;

    }

    public static String post(String url, byte[] data, int timeout) throws Exception {
        HttpURLConnection con = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL httpUrl = new URL(url);
            con = (HttpURLConnection) httpUrl.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setConnectTimeout(timeout);
            con.setReadTimeout(timeout);
            con.addRequestProperty("Content-type", "application/json;charset=UTF-8");
            OutputStream out = con.getOutputStream();
            out.write(data);
            out.flush();
            out.close();
            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
                String line;
                while ((line = in.readLine()) != null) {
                    result += line;
                }
                if (StringUtils.isBlank(result)) {
                    JSONObject jsonData = new JSONObject();
                    jsonData.put("success", "true");
                    result = jsonData.toString();
                }
            } else {
                throw new IOException();
            }
        } catch (IOException e) {
            throw e;
        } finally {
            if (in != null) {
                try {
                    if (in != null)
                        in.close();
                } catch (IOException e) {
                }
            }

            if (con != null) {
                try {
                    con.getInputStream().close();
                } catch (Throwable e) {

                }
                try {
                    con.getOutputStream().close();
                } catch (Throwable e) {

                }
                con.disconnect();
            }
        }
        return result;
    }

    public static String get(String url) {

        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        System.out.println("--" + httpGet.getRequestLine());
        try {
            // 执行get请求
            HttpResponse httpResponse = closeableHttpClient.execute(httpGet);
            // 获取响应消息实体
            HttpEntity entity = httpResponse.getEntity();
            // 响应状态
            System.out.println("status:" + httpResponse.getStatusLine());
            // 判断响应实体是否为空
            if (entity != null) {
                // System.out.println("contentEncoding:" + entity.getContentEncoding());
                // System.out.println("response content:" + EntityUtils.toString(entity));
                return EntityUtils.toString(entity, "UTF-8");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭流并释放资源
                closeableHttpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    public static String doPost(String url, Object json) throws Exception {
        String result = null;
        if (StringUtils.isEmpty(url)) {
            return result;
        } else {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(url);
            CloseableHttpResponse response = null;
            InputStream instream = null;

            try {
                httpPost.setConfig(requestConfig);
                StringEntity stringEntity = new StringEntity(json.toString(), "UTF-8");
                stringEntity.setContentEncoding("UTF-8");
                stringEntity.setContentType("application/json");
                httpPost.setEntity(stringEntity);
                response = httpClient.execute(httpPost);
                int statusCode = response.getStatusLine().getStatusCode();
                System.out.println("doPost ERROR :" + statusCode);
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    instream = entity.getContent();
                    result = IOUtils.toString(instream, "UTF-8");
                }
            } catch (IOException var13) {
                System.out.println("doPost BY JSON ERROR :" + var13.getMessage());
            } finally {
                if (null != instream) {
                    instream.close();
                }

                if (null != response) {
                    response.close();
                }

                if (null != httpClient) {
                    httpClient.close();
                }
            }
            return result;
        }
    }


}
