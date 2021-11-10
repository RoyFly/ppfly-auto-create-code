package com.ppfly.strategy.sql;

public interface SqlStrategy {
    /**
     * 获取表中的字段名称、字段类型、字段描述、字段类型长度等元数据
     *
     * @return
     */
    String getMetaDataSql();

    /**
     * 获取表名的描述信息
     *
     * @return
     */
    String getTableCommentsSql();
}
