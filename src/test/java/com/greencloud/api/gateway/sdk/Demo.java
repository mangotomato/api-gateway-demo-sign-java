/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.greencloud.api.gateway.sdk;

import com.alibaba.fastjson.JSON;
import com.greencloud.api.gateway.sdk.constant.Constants;
import com.greencloud.api.gateway.sdk.constant.ContentType;
import com.greencloud.api.gateway.sdk.constant.HttpHeader;
import com.greencloud.api.gateway.sdk.constant.HttpSchema;
import com.greencloud.api.gateway.sdk.enums.Method;
import com.greencloud.api.gateway.sdk.util.MessageDigestUtil;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 调用示例
 * 请替换APP_KEY,APP_SECRET,HOST,CUSTOM_HEADERS_TO_SIGN_PREFIX为真实配置
 */
public class Demo {
    //APP KEY
    private final static String APP_KEY = "25396816";
    // APP密钥
    private final static String APP_SECRET = "ba09305bef13bf8c17ace9987c66326f";
    //API域名
    private final static String HOST = "localhost:2222";// "192.168.0.73:8111";
    //自定义参与签名Header前缀（可选,默认只有"X-Ca-"开头的参与到Header签名）
    private final static List<String> CUSTOM_HEADERS_TO_SIGN_PREFIX = new ArrayList<String>();

    /**
     * HTTP GET
     *
     * @throws Exception
     */
    @Test
    public void get() throws Exception {
        //请求path
        String path = "/s/weather";

        Map<String, String> headers = new HashMap<String, String>();
        //（必填）根据期望的Response内容类型设置
        headers.put(HttpHeader.HTTP_HEADER_ACCEPT, "application/json");
//        headers.put("a-header1", "header1Value");
//        headers.put("b-header2", "header2Value");

        CUSTOM_HEADERS_TO_SIGN_PREFIX.clear();
//        CUSTOM_HEADERS_TO_SIGN_PREFIX.add("a-header1");
//        CUSTOM_HEADERS_TO_SIGN_PREFIX.add("a-header2");

        Request request = new Request(Method.GET, HttpSchema.HTTP + HOST, path, APP_KEY, APP_SECRET, Constants.DEFAULT_TIMEOUT);
        request.setHeaders(headers);
        request.setSignHeaderPrefixList(CUSTOM_HEADERS_TO_SIGN_PREFIX);

        //请求的query
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("city", "呼伦贝尔");
        request.setQuerys(querys);

        //调用服务端
        Response response = Client.execute(request);

        System.out.println(JSON.toJSONString(response.getBody()));
    }

    /**
     * HTTP POST 表单
     *
     * @throws Exception
     */
    @Test
    public void postForm() throws Exception {
        //请求path
        String path = "/postform";

        Map<String, String> headers = new HashMap<String, String>();
        //（必填）根据期望的Response内容类型设置
        headers.put(HttpHeader.HTTP_HEADER_ACCEPT, "application/json");
        headers.put("a-header1", "header1Value");
        headers.put("b-header2", "header2Value");
        CUSTOM_HEADERS_TO_SIGN_PREFIX.clear();
        CUSTOM_HEADERS_TO_SIGN_PREFIX.add("a-header1");
        CUSTOM_HEADERS_TO_SIGN_PREFIX.add("a-header2");

        Request request = new Request(Method.POST_FORM, HttpSchema.HTTP + HOST, path, APP_KEY, APP_SECRET, Constants.DEFAULT_TIMEOUT);
        request.setHeaders(headers);
        request.setSignHeaderPrefixList(CUSTOM_HEADERS_TO_SIGN_PREFIX);

        //请求的query
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("a-query1", "query1Value");
        querys.put("b-query2", "query2Value");
        request.setQuerys(querys);

        Map<String, String> bodys = new HashMap<String, String>();
        bodys.put("a-body1", "body1Value");
        bodys.put("b-body2", "body2Value");
        request.setBodys(bodys);

        //调用服务端
        Response response = Client.execute(request);

        System.out.println(JSON.toJSONString(response));
    }

    /**
     * HTTP POST 字符串
     *
     * @throws Exception
     */
    @Test
    public void postString() throws Exception {
        //请求path
        String path = "/poststring";
        //Body内容
        String body = "demo string body content";

        Map<String, String> headers = new HashMap<String, String>();
        //（必填）根据期望的Response内容类型设置
        headers.put(HttpHeader.HTTP_HEADER_ACCEPT, "application/json");
        //（可选）Body MD5,服务端会校验Body内容是否被篡改,建议Body非Form表单时添加此Header
        headers.put(HttpHeader.HTTP_HEADER_CONTENT_MD5, MessageDigestUtil.base64AndMD5(body));
        //（POST/PUT请求必选）请求Body内容格式
        headers.put(HttpHeader.HTTP_HEADER_CONTENT_TYPE, ContentType.CONTENT_TYPE_TEXT);

        headers.put("a-header1", "header1Value");
        headers.put("b-header2", "header2Value");
        CUSTOM_HEADERS_TO_SIGN_PREFIX.clear();
        CUSTOM_HEADERS_TO_SIGN_PREFIX.add("a-header1");
        CUSTOM_HEADERS_TO_SIGN_PREFIX.add("a-header2");


        Request request = new Request(Method.POST_STRING, HttpSchema.HTTP + HOST, path, APP_KEY, APP_SECRET, Constants.DEFAULT_TIMEOUT);
        request.setHeaders(headers);
        request.setSignHeaderPrefixList(CUSTOM_HEADERS_TO_SIGN_PREFIX);

        //请求的query
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("a-query1", "query1Value");
        querys.put("b-query2", "query2Value");
        request.setQuerys(querys);

        request.setStringBody(body);

        //调用服务端
        Response response = Client.execute(request);

        System.out.println(JSON.toJSONString(response));
    }

    /**
     * HTTP POST 字节数组
     *
     * @throws Exception
     */
    @Test
    public void postBytes() throws Exception {
        //请求path
        String path = "/poststream";
        //Body内容
        byte[] bytesBody = "demo bytes body content".getBytes(Constants.ENCODING);

        Map<String, String> headers = new HashMap<String, String>();
        //（必填）根据期望的Response内容类型设置
        headers.put(HttpHeader.HTTP_HEADER_ACCEPT, "application/json");
        //（可选）Body MD5,服务端会校验Body内容是否被篡改,建议Body非Form表单时添加此Header
        headers.put(HttpHeader.HTTP_HEADER_CONTENT_MD5, MessageDigestUtil.base64AndMD5(bytesBody));
        //（POST/PUT请求必选）请求Body内容格式
        headers.put(HttpHeader.HTTP_HEADER_CONTENT_TYPE, ContentType.CONTENT_TYPE_TEXT);

        headers.put("a-header1", "header1Value");
        headers.put("b-header2", "header2Value");
        CUSTOM_HEADERS_TO_SIGN_PREFIX.clear();
        CUSTOM_HEADERS_TO_SIGN_PREFIX.add("a-header1");
        CUSTOM_HEADERS_TO_SIGN_PREFIX.add("a-header2");

        Request request = new Request(Method.POST_BYTES, HttpSchema.HTTP + HOST, path, APP_KEY, APP_SECRET, Constants.DEFAULT_TIMEOUT);
        request.setHeaders(headers);
        request.setSignHeaderPrefixList(CUSTOM_HEADERS_TO_SIGN_PREFIX);

        //请求的query
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("a-query1", "query1Value");
        querys.put("b-query2", "query2Value");
        request.setQuerys(querys);

        request.setBytesBody(bytesBody);

        //调用服务端
        Response response = Client.execute(request);

        System.out.println(JSON.toJSONString(response));
    }

    @Test
    public void testPostJsonBody() throws Exception {
        //请求path
       // String path = "/guardian/uc/api/login";
        String path = "/uc/api/login";

        String requestBody = "{\"appCode\":\"UC\",\"orgCode\":\"\",\"userCode\":\"UCADMIN\",\"password\":\"e10adc3949ba59abbe56e057f20f883e\"}";

        //Body内容
        byte[] bytesBody = requestBody.getBytes(Constants.ENCODING);

        Map<String, String> headers = new HashMap<String, String>();
        //（必填）根据期望的Response内容类型设置
        headers.put(HttpHeader.HTTP_HEADER_ACCEPT, "application/json");
        //（可选）Body MD5,服务端会校验Body内容是否被篡改,建议Body非Form表单时添加此Header
        headers.put(HttpHeader.HTTP_HEADER_CONTENT_MD5, MessageDigestUtil.base64AndMD5(bytesBody));
        //（POST/PUT请求必选）请求Body内容格式
        headers.put(HttpHeader.HTTP_HEADER_CONTENT_TYPE, ContentType.CONTENT_TYPE_JSON);

        headers.put("a-header1", "header1Value");
        headers.put("b-header2", "header2Value");
        CUSTOM_HEADERS_TO_SIGN_PREFIX.clear();
        CUSTOM_HEADERS_TO_SIGN_PREFIX.add("a-header1");
        CUSTOM_HEADERS_TO_SIGN_PREFIX.add("a-header2");

        Request request = new Request(Method.POST_BYTES, HttpSchema.HTTP + HOST, path, APP_KEY, APP_SECRET, Constants.DEFAULT_TIMEOUT);
        request.setHeaders(headers);
        request.setSignHeaderPrefixList(CUSTOM_HEADERS_TO_SIGN_PREFIX);

        //请求的query
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("a-query1", "query1Value");
        querys.put("b-query2", "query2Value");
        request.setQuerys(querys);

        request.setBytesBody(bytesBody);

        //调用服务端
        Response response = Client.execute(request);

        System.out.println(JSON.toJSONString(response));



    }

    /**
     * HTTP PUT 字符串
     *
     * @throws Exception
     */
    @Test
    public void putString() throws Exception {
        //请求path
        String path = "/putstring";
        //Body内容
        String body = "demo string body content";

        Map<String, String> headers = new HashMap<String, String>();
        //（必填）根据期望的Response内容类型设置
        headers.put(HttpHeader.HTTP_HEADER_ACCEPT, "application/json");
        //（可选）Body MD5,服务端会校验Body内容是否被篡改,建议Body非Form表单时添加此Header
        headers.put(HttpHeader.HTTP_HEADER_CONTENT_MD5, MessageDigestUtil.base64AndMD5(body));
        //（POST/PUT请求必选）请求Body内容格式
        headers.put(HttpHeader.HTTP_HEADER_CONTENT_TYPE, ContentType.CONTENT_TYPE_TEXT);

        headers.put("a-header1", "header1Value");
        headers.put("b-header2", "header2Value");
        CUSTOM_HEADERS_TO_SIGN_PREFIX.clear();
        CUSTOM_HEADERS_TO_SIGN_PREFIX.add("a-header1");
        CUSTOM_HEADERS_TO_SIGN_PREFIX.add("a-header2");

        Request request = new Request(Method.POST_STRING, HttpSchema.HTTP + HOST, path, APP_KEY, APP_SECRET, Constants.DEFAULT_TIMEOUT);
        request.setHeaders(headers);
        request.setSignHeaderPrefixList(CUSTOM_HEADERS_TO_SIGN_PREFIX);
        request.setStringBody(body);

        //调用服务端
        Response response = Client.execute(request);

        System.out.println(JSON.toJSONString(response));
    }

    /**
     * HTTP PUT 字节数组
     *
     * @throws Exception
     */
    @Test
    public void putBytesBody() throws Exception {
        //请求path
        String path = "/putstream";
        //Body内容
        byte[] bytesBody = "demo bytes body content".getBytes(Constants.ENCODING);

        Map<String, String> headers = new HashMap<String, String>();
        //（必填）根据期望的Response内容类型设置
        headers.put(HttpHeader.HTTP_HEADER_ACCEPT, "application/json");
        //（可选）Body MD5,服务端会校验Body内容是否被篡改,建议Body非Form表单时添加此Header
        headers.put(HttpHeader.HTTP_HEADER_CONTENT_MD5, MessageDigestUtil.base64AndMD5(bytesBody));
        //（POST/PUT请求必选）请求Body内容格式
        headers.put(HttpHeader.HTTP_HEADER_CONTENT_TYPE, ContentType.CONTENT_TYPE_TEXT);
        headers.put("a-header1", "header1Value");
        headers.put("b-header2", "header2Value");
        CUSTOM_HEADERS_TO_SIGN_PREFIX.clear();
        CUSTOM_HEADERS_TO_SIGN_PREFIX.add("a-header1");
        CUSTOM_HEADERS_TO_SIGN_PREFIX.add("a-header2");

        Request request = new Request(Method.PUT_BYTES, HttpSchema.HTTP + HOST, path, APP_KEY, APP_SECRET, Constants.DEFAULT_TIMEOUT);
        request.setHeaders(headers);
        request.setSignHeaderPrefixList(CUSTOM_HEADERS_TO_SIGN_PREFIX);
        request.setBytesBody(bytesBody);

        //调用服务端
        Response response = Client.execute(request);

        System.out.println(JSON.toJSONString(response));
    }

    /**
     * HTTP DELETE
     *
     * @throws Exception
     */
    @Test
    public void delete() throws Exception {
        //请求path
        String path = "/delete";

        Map<String, String> headers = new HashMap<String, String>();
        //（必填）根据期望的Response内容类型设置
        headers.put(HttpHeader.HTTP_HEADER_ACCEPT, "application/json");

        Request request = new Request(Method.DELETE, HttpSchema.HTTP + HOST, path, APP_KEY, APP_SECRET, Constants.DEFAULT_TIMEOUT);
        request.setHeaders(headers);
        request.setSignHeaderPrefixList(CUSTOM_HEADERS_TO_SIGN_PREFIX);

        //调用服务端
        Response response = Client.execute(request);

        System.out.println(JSON.toJSONString(response));
    }


}
