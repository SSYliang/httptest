package com.test.test1.Util;

import com.test.test1.Entity.Person;
import java.sql.*;

public class JDBCConnection {
    public JDBCConnection(String id) {

        String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
        Connection conn = null;
        String DB_URL = "jdbc:mysql://192.168.132.129:3306/test?useSSL=false&serverTimeZone=UTC";
        String USER = "root";
        String PASS = "SSY2019114girl%";
        //定义sql语句
        String sql = "select * from person where id = ?";
        //获取执行sql的对象Statement
        Statement stmt = null;
        try {
            Class.forName(JDBC_DRIVER);
            System.out.println("加载数据库驱动成功");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            System.out.println("连接数据库驱动成功");
            stmt = conn.createStatement();
            //执行sql
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
//            stmt.executeUpdate(sql[1]);
            Person per = new Person();
            while(resultSet.next()){
                per.setId(resultSet.getInt(1));
                per.setName(resultSet.getString(2));
                per.setAge(resultSet.getInt(3));
                per.setSex(resultSet.getString(4));
            }
            System.out.println(per);
            //7.释放资源
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
