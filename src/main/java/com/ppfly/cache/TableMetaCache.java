package com.ppfly.cache;

import com.ppfly.enums.ResultEnum;
import com.ppfly.exception.AccException;
import com.ppfly.util.ConnectionUtil;
import lombok.Data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

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
     * 获取表的字段、注释、字段类型等信息
     */
    public void initTableMetaData() {
        ConnectionUtil jdbc = new ConnectionUtil();
        Connection conn = null;
        ResultSet rs = null;
        try {

            conn = jdbc.getConn(); // 得到数据库连接
            //1.获取表中的字段名称、字段类型、字段描述、字段类型长度
            PreparedStatement ps = conn.prepareStatement(ConnectionUtil.getMetaDataSql());
            ps.setString(1, PropertiesContext.getInstance().getSchema().toUpperCase());
            ps.setString(2, PropertiesContext.getInstance().getTableName().toUpperCase());
            rs = ps.executeQuery();

            if (rs.next()) {
                do {
                    colNames.add(rs.getString("column_name"));
                    colTypes.add(rs.getString("data_type"));
                    comments.add(rs.getString("comments"));
                    intLengths.add(rs.getInt("data_length"));
                } while (rs.next());
            } else {
                throw new AccException(ResultEnum.TABLE_NOT_EXIST);
            }
            rs.close();

            //2.获取表名的描述信息
            PreparedStatement psTabname = conn.prepareStatement(ConnectionUtil.getTableCommentsSql());
            psTabname.setString(1, PropertiesContext.getInstance().getTableName().toUpperCase());
            rs = psTabname.executeQuery();
            if (rs.next()) {
                tableName_comments = rs.getString("comments");
            }
            rs.close();
        } catch (AccException ae) {
            throw new AccException(ResultEnum.ERROR.getCode(), ae.getMessage());
        } catch (Exception e) {
            throw new AccException(ResultEnum.ERROR.getCode(), e.getMessage());
        } finally {
            jdbc.closeConnection(conn);
        }
    }
}
