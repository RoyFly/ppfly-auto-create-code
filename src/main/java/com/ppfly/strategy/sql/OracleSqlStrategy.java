package com.ppfly.strategy.sql;

public class OracleSqlStrategy implements SqlStrategy {
    /**
     * 获取表中的字段名称、字段类型、字段描述、字段类型长度
     */
    private static String METADATA_ORACLE = "select A.column_name, A.data_type,  B.comments, a.data_length, from " +
            " user_tab_columns A,user_col_comments B where A.table_name = B.table_name and A.column_name = B.column_name" +
            " and A.table_name = ? order by a.column_id asc";

    /**
     * 获取表名的描述信息
     */
    private static String COMMENTS_ORACLE = "select table_name, comments from user_tab_comments where table_name = ?";

    @Override
    public String getMetaDataSql() {
        return METADATA_ORACLE;
    }

    @Override
    public String getTableCommentsSql() {
        return COMMENTS_ORACLE;
    }
}
