package com.edu.sdu.util;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * HTTP 客户端工具类
 */
public class HttpClientUtil {
    
    private static final OkHttpClient client = new OkHttpClient();
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final String BASE_URL = "http://localhost:8080/api";
    
    /**
     * 发送 GET 请求
     */
    public static String get(String url) throws IOException {
        Request request = new Request.Builder()
                .url(BASE_URL + url)
                .build();
        
        try (Response response = client.newCall(request).execute()) {
            return response.body() != null ? response.body().string() : "";
        }
    }
    
    /**
     * 发送 POST 请求
     */
    public static String post(String url, Object body) throws IOException {
        // TODO: 实现 POST 请求
        return "";
    }
}
