package com.ppfly;

import com.ppfly.cache.PropertiesContext;
import com.ppfly.cache.TableMetaCache;
import com.ppfly.factory.AutoGenerationCodeTool;
import com.ppfly.util.SpringContextUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class AutoCreateCodeApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(AutoCreateCodeApplication.class, args);
        SpringContextUtil.setApplicationContext(context);
        PropertiesContext propertiesContext = PropertiesContext.getInstance();
        propertiesContext.setProjectPath("D:/java");//生成的类存放的路径
        propertiesContext.setCodePackage("com.ppfly.core.test");//包名
        propertiesContext.setTableName("COUPON_INST");//数据库表名
        propertiesContext.setSchema("test");//数据库名称
        propertiesContext.setAuthor("ppfly");//代码生成者
        //生成代码
        //获取表的字段、注释、字段类型等信息
        TableMetaCache.getInstance().initTableMsg();
        new AutoGenerationCodeTool().createCode();
    }
}

