package com.test.test1.Util;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.concurrent.Executor;

public class NacosUtil {
    public static NacosUtil Nacos() throws NacosException, InterruptedException {
        String serverAdd = "192.168.132.129";
        String dataId = "nacos_config.properties";
        String group = "DEFAULT_GROUP";
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.SERVER_ADDR, serverAdd);
//        properties.put("namespace",namespace);
        ConfigService configService = NacosFactory.createConfigService(properties);
        String content = configService.getConfig(dataId,group,5000);
//        System.out.println(content);
        try {
            InputStream is = new ByteArrayInputStream(content.getBytes("UTF-8"));
            properties.load(is);
            System.out.print("Nacos："+"name="+properties.getProperty("name")+"  ");
            System.out.println("age="+properties.getProperty("age"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        System.out.println(is);
//        properties.load(is);
        configService.addListener(dataId, group, new Listener() {
            @Override
            public Executor getExecutor() {
                return null;
            }

            @Override
            public void receiveConfigInfo(String configInfo) {
                System.out.println("recieve:"+configInfo);
            }
        });

//        while (true){
//            try {
//                Thread.sleep(2000);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }

//        boolean isPublishOk = configService.publishConfig(dataId,group,content);
//        System.out.println(isPublishOk);
//
//        Thread.sleep(3000);
//        content = configService.getConfig(dataId,group,5000);
//        System.out.println(content);
////
//////        boolean isRemoveOk = !configService.removeConfig(dataId,group);
//////        System.out.println(isRemoveOk);
//////        Thread.sleep(3000);
////
//        content = configService.getConfig(dataId,group,5000);
//        System.out.println(content);
//        Thread.sleep(300000);
        return null;
    }
}
