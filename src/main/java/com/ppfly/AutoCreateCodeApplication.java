package com.ppfly;

import com.ppfly.cache.PropertiesContext;
import com.ppfly.factory.AutoCreateCodeCreator;
import com.ppfly.util.SpringContextUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class AutoCreateCodeApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(AutoCreateCodeApplication.class, args);
        SpringContextUtil.setApplicationContext(context);

        final Environment environment = SpringContextUtil.getBean(Environment.class);
        String projectPath = environment.getProperty("propertiescontext.projectpath");
        String codepackage = environment.getProperty("propertiescontext.codepackage");
        String tablename = environment.getProperty("propertiescontext.tablename");
        String schema = environment.getProperty("propertiescontext.schema");
        String author = environment.getProperty("propertiescontext.author");

//        Scanner scan = new Scanner(System.in);
//        // 从键盘接收数据
//        System.out.println("请输入代码存储路径：");
//        // 判断是否还有输入
//        if (scan.hasNextLine()) {
//            projectPath = scan.nextLine();
//            System.out.println("输入的数据为：" + projectPath);
//        }
//        scan.close();

        PropertiesContext propertiesContext = PropertiesContext.getInstance();
        propertiesContext.setProjectPath(projectPath);//生成的类存放的路径
        propertiesContext.setCodePackage(codepackage);//包名
        propertiesContext.setTableName(tablename);//数据库表名
        propertiesContext.setSchema(schema);//数据库名称
        propertiesContext.setAuthor(author);//代码生成者
        new AutoCreateCodeCreator().createCode();
    }
}

