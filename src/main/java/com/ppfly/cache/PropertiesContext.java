package com.ppfly.cache;

import lombok.Data;

@Data
public class PropertiesContext {

    /**
     * 代码生成后存放的位置
     */
    private String projectPath;

    /**
     * 业务代码的包名
     */
    private String codePackage;

    /**
     * 生成代码人员
     */
    private String author;

    /**
     * 表名
     * 大小均可
     */
    private String tableName;

    /**
     * 数据库名称
     */
    private String schema;

    /**
     * 声明成 volatile
     */
    private static volatile PropertiesContext instance;

    /**
     * 私有构造函数
     */
    private PropertiesContext() {
    }

    /**
     * 单例模式：双重检验锁模式
     *
     * @return
     */
    public static PropertiesContext getInstance() {
        if (instance == null) {
            synchronized (PropertiesContext.class) {
                if (instance == null) {
                    instance = new PropertiesContext();
                }
            }
        }
        return instance;
    }

}
