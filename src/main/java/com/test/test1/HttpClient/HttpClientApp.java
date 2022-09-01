package com.test.test1.HttpClient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import com.google.gson.Gson;
import com.test.test1.Entity.Person;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class HttpClientApp
{

    private static final Logger logger = LoggerFactory.getLogger(HttpClientApp.class);
    public static void main( String[] args ) throws IOException {

        String url="http://localhost:8080/index/add";
        String url1="http://localhost:8080/index/test";
        Gson gson = new Gson();
        String j = setTest();
        Person p = gson.fromJson(j,Person.class);
        System.out.println(p);
        String reqStr="请求报文";
        String contentType="application/json";
        String charset="UTF-8";
        String ss=doPost( url,  reqStr,  contentType,  charset);
        String ss1=doPost( url1,  reqStr,  contentType,  charset) ;
        System.out.println("返回内容为：" +ss);
    }

    /**
     * 执行一个HTTP POST请求，返回请求响应的HTML
     * @param url     请求的URL地址
     * @param reqStr  请求的查询参数,可以为null
     * @param charset 字符集
     * @return 返回请求响应的HTML
     */
    public static String doPost(String url, String reqStr, String contentType, String charset) {

        System.out.println("发送报文："+reqStr);
        HttpClient client = new HttpClient();
        PostMethod method = new PostMethod(url);
        try {
            HttpConnectionManagerParams managerParams = client.getHttpConnectionManager().getParams();
            managerParams.setConnectionTimeout(30000); // 设置连接超时时间(单位毫秒)
            managerParams.setSoTimeout(30000); // 设置读数据超时时间(单位毫秒)
            method.setRequestEntity(new StringRequestEntity(reqStr, contentType, "utf-8"));
            client.executeMethod(method);
            System.out.println("返回的状态码为：" +method.getStatusCode());
            if (method.getStatusCode() == HttpStatus.SC_OK) {
//                return StreamUtils.copyToString(method.getResponseBodyAsStream(),Charset.forName(charset));
                String resultStr= IOUtils.toString(method.getResponseBodyAsStream(),"utf-8");
                System.out.println("resultStr:"+resultStr);
//              return  new String(resultStr.getBytes("ISO-8859-1"),"UTF-8");
                return resultStr;
            }
        } catch (UnsupportedEncodingException e1) {
            logger.error(e1.getMessage());
            return "";
        } catch (IOException e) {
            logger.error("执行HTTP Post请求" + url + "时，发生异常！" + e.toString());
            return "";
        } finally {
            method.releaseConnection();
        }
        return null;
    }

    public static String doPostMuStr(String url, Part[] reqStr) {

        HttpClient client = new HttpClient();
        PostMethod method = new PostMethod(url);
        try {
            HttpConnectionManagerParams managerParams = client.getHttpConnectionManager().getParams();
            managerParams.setConnectionTimeout(30000); // 设置连接超时时间(单位毫秒)
            managerParams.setSoTimeout(30000); // 设置读数据超时时间(单位毫秒)
//            method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
            method.setRequestEntity(new MultipartRequestEntity(reqStr, method.getParams()));
//            method.setContentChunked(true);
            client.executeMethod(method);
            System.out.println("返回的状态码为：" +method.getStatusCode());
            if (method.getStatusCode() == HttpStatus.SC_OK) {
                return IOUtils.toString(method.getResponseBodyAsStream(),"utf-8");
            }
        } catch (UnsupportedEncodingException e1) {
            logger.error(e1.getMessage());
            return "";
        } catch (IOException e) {
            logger.error("执行HTTP Post请求" + url + "时，发生异常！" + e.toString());
            return "";
        } finally {
            method.releaseConnection();
        }
        return null;
    }

    public static String setTest(){

        Person person = new Person();
        person.setName("测试名");
        person.setAge(1);
        person.setSex("man");
        Gson gson = new Gson();
        String json = gson.toJson(person);
        return json;
    }
}