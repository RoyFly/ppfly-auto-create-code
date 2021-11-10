package com.ppfly.strategy.sql;

/**
 * Sql语句提供者，持有策略接口
 */
public class SqlProvider {

    private SqlStrategy sqlStrategy;

    /**
     * 设置策略接口
     *
     * @param sqlStrategy
     */
    public void setSqlStrategy(SqlStrategy sqlStrategy) {
        this.sqlStrategy = sqlStrategy;
    }

    /**
     * 获取表中的字段名称、字段类型、字段描述、字段类型长度等元数据
     */
    public String getMetaDataSql() {
        return sqlStrategy.getMetaDataSql();
    }

    /**
     * 获取表名的描述信息
     */
    public String getTableCommentsSql() {
        return sqlStrategy.getTableCommentsSql();
    }
}
