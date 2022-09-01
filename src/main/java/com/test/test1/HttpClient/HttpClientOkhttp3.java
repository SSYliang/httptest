package com.test.test1.HttpClient;

import com.test.test1.Util.Okhttp;

import java.io.*;

public class HttpClientOkhttp3
{
    public static void main( String[] args ) throws IOException {

        String url="http://localhost:8080/index/add";
        String reqStr="OK";
        String contentType="application/json";
        String charset="UTF-8";
        String ss = Okhttp.doPost(url,reqStr,contentType,charset);
        System.out.println("返回内容为：" +ss);
    }
}