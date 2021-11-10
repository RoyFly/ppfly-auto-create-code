package com.ppfly;

import com.ppfly.cache.PropertiesContext;
import com.ppfly.factory.AutoGenerationCodeTool;
import com.ppfly.util.ConnectionUtil;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AutoCreateCodeApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(AutoCreateCodeApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        PropertiesContext propertiesContext = PropertiesContext.getInstance();
        propertiesContext.setProjectPath("D:/java");//生成的类存放的路径
        propertiesContext.setCodePackage("com.ppfly.core.test");//包名
        propertiesContext.setTableName("COUPON_INST");//数据库表名
        propertiesContext.setSchema("test");//数据库名称
        propertiesContext.setAuthor("ppfly");//代码生成者
        ConnectionUtil.dbUrl = "jdbc:mysql://localhost:3306/test?characterEncoding=utf-8&serverTimezone=UTC";
        ConnectionUtil.theUser = "root";//数据库登录用户
        ConnectionUtil.thePw = "123456";//数据库登录用户密码
        //生成代码
        new AutoGenerationCodeTool().createCode();
    }
}

