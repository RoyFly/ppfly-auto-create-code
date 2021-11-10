package com.ppfly.strategy.sql;

import com.ppfly.enums.ResultEnum;
import com.ppfly.exception.AccException;

/**
 * SQL语句
 */
public class SqlStrategyFactory {

    public static final String DB_TYPE_MYSQL = "Mysql";
    public static final String DB_TYPE_ORACLE = "Oracle";

    /**
     * 获取SqlStrategy
     *
     * @param dbType
     * @return
     * @throws Exception
     */
    public static SqlStrategy getSqlStrategy(final String dbType) throws Exception {
        try {
            return (SqlStrategy) Class.forName(SqlStrategy.class.getPackage().getName() + "." + dbType + "SqlStrategy").newInstance();
        } catch (Exception e) {
            throw new AccException(ResultEnum.DB_TYPE_NOT_SUPPORT);
        }
    }

}
