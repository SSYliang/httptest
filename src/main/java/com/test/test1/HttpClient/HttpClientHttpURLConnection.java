package com.test.test1.HttpClient;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpClientHttpURLConnection
{

    public static void main( String[] args ) throws IOException {

        URL url = new URL("http://localhost:8080/index/add");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setRequestMethod("POST");
        DataOutputStream out = new DataOutputStream(connection.getOutputStream());
        String jsonData = "username=admin&password=1234&name=尚思阳";
        out.write(jsonData.getBytes("utf-8"));
        connection.setConnectTimeout(30000);
        connection.setReadTimeout(30000);
        InputStream in = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder builder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null){
            builder.append(line);
        }
    }
}