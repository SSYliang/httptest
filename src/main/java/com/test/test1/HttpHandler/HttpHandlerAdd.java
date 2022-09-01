package com.test.test1.HttpHandler;

import com.alibaba.nacos.api.exception.NacosException;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.test.test1.Util.NacosUtil;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URI;

public class HttpHandlerAdd implements HttpHandler {
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
        if (requestMethod.equalsIgnoreCase("GET")) {
            Headers responseHeaders = httpExchange.getResponseHeaders();
            responseHeaders.set("Content_Type", "text/html;charset=utf-8");
            String response = "this is Add Server";
//            for (i = 98; i >= 0; i--){
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
            String resmsg = "恭喜你成功了！";
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, resmsg.getBytes("UTF-8").length);
            OutputStream outputStream = httpExchange.getResponseBody();
            outputStream.write(resmsg.getBytes("UTF-8"));
            outputStream.close();
            System.out.println("通讯结束！");
        }
    }
}
