package com.ppfly.cache;

import com.ppfly.strategy.sql.SqlProvider;
import com.ppfly.strategy.sql.SqlStrategy;
import com.ppfly.strategy.sql.SqlStrategyFactory;
import com.ppfly.util.SpringContextUtil;
import lombok.Data;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class TableMetaCache {

    /**
     * 表名注释
     */
    public String tableName_comments = "";

    /**
     * 表字段集合
     */
    private List<String> colNames = new ArrayList();
    /**
     * 表字段类型集合
     */
    private List<String> colTypes = new ArrayList();
    /**
     * 表字段注释集合
     */
    private List<String> comments = new ArrayList();
    /**
     * 表单字段长度集合
     */
    private List<Integer> intLengths = new ArrayList();

    /**
     * 声明成 volatile
     */
    private static volatile TableMetaCache instance;

    /**
     * 私有构造函数
     */
    private TableMetaCache() {
    }

    /**
     * 单例模式：双重检验锁模式
     *
     * @return
     */
    public static TableMetaCache getInstance() {
        if (instance == null) {
            synchronized (TableMetaCache.class) {
                if (instance == null) {
                    instance = new TableMetaCache();
                }
            }
        }
        return instance;
    }

    /**
     * 获取表注释信息
     * 获取字段名称、字段注释、字段类型、字段长度等信息
     */
    public void initTableMsg() {
        SqlProvider sqlProvider = new SqlProvider();
        try {
            final SqlStrategy sqlStrategy = SqlStrategyFactory.getSqlStrategy(SqlStrategyFactory.DB_TYPE_MYSQL);
            sqlProvider.setSqlStrategy(sqlStrategy);
        } catch (Exception e) {
            e.printStackTrace();
        }


        //从容器获取JdbcTemplate
        JdbcTemplate jdbcTemplate = SpringContextUtil.getBean(JdbcTemplate.class);
        final List<Map<String, Object>> metaDataList = jdbcTemplate.queryForList(sqlProvider.getMetaDataSql(),
                new Object[]{PropertiesContext.getInstance().getSchema().toUpperCase(), PropertiesContext.getInstance().getTableName().toUpperCase()});
        for (Map<String, Object> map : metaDataList) {
            colNames.add((String) map.get("column_name"));
            colTypes.add((String) map.get("data_type"));
            comments.add((String) map.get("comments"));
            intLengths.add((map.get("data_length") == null) ? 0 : Integer.valueOf(map.get("data_length").toString()));
        }

        final List<Map<String, Object>> tableCommentList = jdbcTemplate.queryForList(sqlProvider.getTableCommentsSql(),
                new Object[]{PropertiesContext.getInstance().getTableName().toUpperCase()});
        final Object comments = tableCommentList.get(0).get("comments");
        tableName_comments = (comments != null) ? comments.toString() : "";
    }
}
