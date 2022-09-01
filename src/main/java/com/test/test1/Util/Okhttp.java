package com.test.test1.Util;

import okhttp3.*;

import java.io.IOException;

public class Okhttp {

    public static String doPost(String url, String contentType, String charset, String s) throws IOException {

        OkHttpClient client = new OkHttpClient();
        MediaType json = MediaType.parse(contentType + ";" + charset);
        RequestBody requestBody = new FormBody.Builder()
                .add("username","root")
                .add("password","111")
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        Call call = client.newCall(request);
        Response response = call.execute();
        String result = response.body().string();
        System.out.println(result);
        return "ok";
    }
}
