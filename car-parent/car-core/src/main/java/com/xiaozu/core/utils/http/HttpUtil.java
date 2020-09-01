package com.xiaozu.core.utils.http;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

@Log4j2
public class HttpUtil {

    private static final String HTTP_JSON = "application/json; charset=utf-8";
    private static final String CONTENT_TYPE = "text/plain";

    private static final CloseableHttpClient httpclient = HttpClients.createDefault();

    /**
     * 发送HttpGet请求
     *
     * @param url
     * @return
     */
    public static String sendGet(String url) {
        HttpGet httpget = new HttpGet(url);
        CloseableHttpResponse response = null;
        String result = null;
        try {
            response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                result = EntityUtils.toString(entity);
            }
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 发送HttpPost请求，参数为map
     *
     * @param url
     * @param map
     * @return
     */
    public static String sendPost(String url, Map<String, String> map) {
        String params = SortUtil.assembleSortMap(map);
        return sendPost(url, null, params);
    }

    /**
     * 发送HTTP post请求 参数为String
     *
     * @param url     url地址
     * @param headers 请求头
     * @param params  参数
     * @return
     */
    public static String sendPost(String url, Map<String, String> headers, String params) {
        HttpPost httpPost = new HttpPost(url);
        HttpResponse httpResponse;
        try {
            httpPost.setHeader("Content-Type", CONTENT_TYPE);
            if (!CollectionUtils.isEmpty(headers)) {
                headers.forEach((String key, String value) -> httpPost.setHeader(key, value));
            }
            if (StringUtils.isNotBlank(params)) {
                StringEntity stringEntity = new StringEntity(params, Charset.forName("UTF-8"));
                stringEntity.setContentEncoding(HTTP_JSON);
                httpPost.setEntity(stringEntity);
            }
            httpResponse = httpclient.execute(httpPost);
            String response = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
            return response;
        } catch (Exception e) {
            log.error("httpPost请求异常：{}", e);
        } finally {
            httpPost.releaseConnection();
        }
        return null;
    }

    /**
     * 发送不带参数的HttpPost请求
     *
     * @param url
     * @return
     */
    public static String sendPost(String url) {
        return sendPost(url, null, null);
    }

}