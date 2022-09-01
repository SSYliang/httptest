package com.test.test1.HttpHandler;

import com.alibaba.nacos.api.exception.NacosException;
import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.test.test1.Util.TestRedis;
import com.test.test1.Entity.Person;
import com.test.test1.Util.JDBCConnection;
import com.test.test1.Util.NacosUtil;
import com.test.test1.Util.UrlUtil;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URI;

public class HttpHandlerDemo implements HttpHandler {

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        InetSocketAddress inetSocketAddress = httpExchange.getRemoteAddress();
        System.out.println("请求ip地址：" + inetSocketAddress);
        System.out.println("请求host：" + inetSocketAddress.getHostName());
        System.out.println("请求port：" + inetSocketAddress.getPort());
        String requestMethod = httpExchange.getRequestMethod();
        System.out.println("请求方式：" + httpExchange.getRequestMethod());
        URI url = httpExchange.getRequestURI();
        System.out.println("url：" + url);
        try {
            NacosUtil nacosUtil = NacosUtil.Nacos();
        } catch (NacosException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        int i;
//        String jsonN = setPerson();
        if (requestMethod.equalsIgnoreCase("GET")) {
            Headers responseHeaders = httpExchange.getResponseHeaders();
            responseHeaders.set("Content-Type", "application/json");
            String response = "这是一个服务";
            String param_select_redis = UrlUtil.getParam(String.valueOf(url),"redis");
            if (param_select_redis != null){
                TestRedis testRedis = new TestRedis();
                testRedis.connectRedis();
                switch (param_select_redis){
                    case "string": testRedis.testString();break;
                    case "map": testRedis.testMap();break;
                    case "list": testRedis.testList();break;
                    case "set": testRedis.testSet();break;
                    case "sort": testRedis.testSort();break;
                }
//                if (param_select_redis.equals("string")){
//                    testRedis.testString();
//                } else if (param_select_redis.equals("map")){
//                    testRedis.testMap();
//                } else if (param_select_redis.equals("list")){
//                    testRedis.testList();
//                } else if (param_select_redis.equals("set")){
//                    testRedis.testSet();
//                } else if (param_select_redis.equals("sort")){
//                    testRedis.testSort();
//                }
            }
            String param_select_id = UrlUtil.getParam(String.valueOf(url),"id");
            if (param_select_id != null){
                new JDBCConnection(param_select_id);
            }

//            for (i = 100; i >= 0; i--){
//                try {
//                    TimeUnit.SECONDS.sleep(1);
//                    System.out.println(getClass().getName()+"："+i);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//            }
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, response.getBytes("UTF-8").length);
            OutputStream responseBody = httpExchange.getResponseBody();
            OutputStreamWriter writer = new OutputStreamWriter(responseBody, "UTF-8");
            writer.write(response);
            writer.close();
            responseBody.close();
        } else {
            InputStream inputStream = httpExchange.getRequestBody();
            ByteArrayOutputStream bas = new ByteArrayOutputStream();
            while ((i = inputStream.read()) != -1) {
                bas.write(i);
            }
            String requestmsg = bas.toString();
            System.out.println("请求报文：" + requestmsg);
//            System.out.println(jsonN);
            String resmsg = "恭喜你成功了！";
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, resmsg.getBytes("UTF-8").length);
            OutputStream outputStream = httpExchange.getResponseBody();
            outputStream.write(resmsg.getBytes("UTF-8"));
            outputStream.close();
            System.out.println("通讯结束！");
        }
    }

    public static String setPerson(){

        Person person = new Person();
        person.setName("SSY");
        person.setAge(22);
        person.setSex("man");
        Gson gson = new Gson();
        String json = gson.toJson(person);
        return json;
    }
}
