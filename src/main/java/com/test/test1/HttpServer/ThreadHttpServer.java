package com.test.test1.HttpServer;

import com.alibaba.nacos.api.exception.NacosException;
import com.sun.net.httpserver.HttpServer;
import com.test.test1.HttpHandler.HttpHandlerAdd;
import com.test.test1.HttpHandler.HttpHandlerDemo;
import com.test.test1.Util.NacosUtil;
import com.xxl.job.core.handler.annotation.XxlJob;

import java.net.InetSocketAddress;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadHttpServer {
    private static final int port = 8080;
    public static final String Httpcontext = "/index";
    public static final int nThreads = 3;

    public static void main(String[] args) {

        HttpServer httpServer;
        try {
//            NacosUtil nacosUtil = NacosUtil.Nacos();
            httpServer = HttpServer.create(new InetSocketAddress(port),0);
            httpServer.createContext(Httpcontext+"/add",new HttpHandlerDemo());
            httpServer.createContext(Httpcontext+"/test",new HttpHandlerAdd());
            //设置并发数
            ExecutorService executor = Executors.newFixedThreadPool(nThreads);
            httpServer.setExecutor(executor);
            httpServer.start();
            System.out.println("启动端口："+port);
            System.out.println("根节点："+Httpcontext);
            System.out.println("并发数："+nThreads);

        } catch (IOException e) {
            e.printStackTrace();
        }
//        catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        } catch (NacosException e) {
//            throw new RuntimeException(e);
//        }
    }
}
