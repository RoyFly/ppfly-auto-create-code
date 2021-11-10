package com.ppfly.strategy.sql;

public class MysqlSqlStrategy implements SqlStrategy {

    /**
     * 获取表中的字段名称、字段类型、字段描述、字段类型长度
     */
    private static String METADATA_MYSQL = "select column_name, data_type, character_maximum_length as data_length, " +
            " column_comment as comments from information_schema.columns where table_schema = ? and table_name = ? ;";

    /**
     * 获取表名的描述信息
     */
    private static String COMMENTS_MYSQL = "select table_comment as comments from information_schema.tables where table_name = ?";

    @Override
    public String getMetaDataSql() {
        return METADATA_MYSQL;
    }

    @Override
    public String getTableCommentsSql() {
        return COMMENTS_MYSQL;
    }
}
