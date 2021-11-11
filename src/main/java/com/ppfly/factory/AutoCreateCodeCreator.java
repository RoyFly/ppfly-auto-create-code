package com.ppfly.factory;

import com.ppfly.cache.PropertiesContext;
import com.ppfly.cache.TableMetaCache;
import com.ppfly.exception.CreateException;
import com.ppfly.strategy.sql.SqlStrategyFactory;
import com.ppfly.util.FreeMarkerUtil;
import com.ppfly.util.Table2ClassUtil;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 自动创建代码创建者
 */
@Slf4j
public class AutoCreateCodeCreator {

    /**
     * @param
     * @description: 生成代码核心逻辑
     * @date 2021/10/03 14:06
     */
    public void createCode() {
        //获取表的字段、注释、字段类型等信息
        TableMetaCache.getInstance().initTableMsg(SqlStrategyFactory.DB_TYPE_MYSQL);

        try {
            //获取实体名称
            String entityName = Table2ClassUtil.getEntityNameFromCodePackage(PropertiesContext.getInstance().getCodePackage());

            //生成代码所需的参数键值对
            final Map<String, String> freeMarkerData = getFreeMarkerData(entityName);

            //生成实体
            createEntity(freeMarkerData, entityName);

            //开始生成service、controller、dao等代码
            generateOtherCode(freeMarkerData, entityName);

        } catch (CreateException ce) {
            ce.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取并返回生成代码所需的参数键值对
     *
     * @param entityName
     * @return
     * @date 2021/10/03 21:57
     */
    private Map<String, String> getFreeMarkerData(String entityName) {
        Map<String, String> freeMarkerData = new HashMap<>();
        //获取当前日期
        String createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(Calendar.getInstance().getTime());
        //设置生成代码所需的参数
        freeMarkerData.put("instanceName", Table2ClassUtil.getCaptureName(entityName));
        freeMarkerData.put("tableNameComments", TableMetaCache.getInstance().getTableName_comments());//表名中文解释
        freeMarkerData.put("tableName", PropertiesContext.getInstance().getTableName().toUpperCase());
        freeMarkerData.put("createPropStr", processAllAttrs());
        freeMarkerData.put("entityPackage", PropertiesContext.getInstance().getCodePackage() + ".entity");
        freeMarkerData.put("columnName", Table2ClassUtil.getColumnName(TableMetaCache.getInstance().getColNames()));
        freeMarkerData.put("entityName", entityName);
        freeMarkerData.put("package", PropertiesContext.getInstance().getCodePackage());
        freeMarkerData.put("author", PropertiesContext.getInstance().getAuthor());
        freeMarkerData.put("now", createTime);
        return freeMarkerData;
    }


    /**
     * 生成实体entity
     *
     * @param freeMarkerData
     * @param entityName
     */
    private void createEntity(Map<String, String> freeMarkerData, String entityName) {

        String filePath = PropertiesContext.getInstance().getProjectPath() + "/" + PropertiesContext.getInstance().getCodePackage().replace(".", "/") + "/entity";

        log.info("实体存放路径:" + filePath);
        try {
            FreeMarkerUtil.otherProcess("entityTemplate.ftl", entityName + ".java", "GBK", freeMarkerData, filePath);
            log.error("生成实体" + entityName + ".java成功!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成service、controller、dao等代码
     *
     * @param freeMarkerData
     * @param entityName
     */
    private void generateOtherCode(Map<String, String> freeMarkerData, String entityName) {

        Map<String, List<String>> beanMap = new HashMap<>();

        List<String> controllerList = new ArrayList<>();
        controllerList.add("DataController");
        controllerList.add("InfoController");
        controllerList.add("PageController");
        beanMap.put("web.controller", controllerList);

        List<String> serviceList = Arrays.asList("Service");
        beanMap.put("service", serviceList);

        List<String> serviceImplList = Arrays.asList("ServiceImpl");
        beanMap.put("service.impl", serviceImplList);

        List<String> daoList = Arrays.asList("Dao");
        beanMap.put("dao", daoList);

        List<String> daoImplList = Arrays.asList("DaoImpl");
        beanMap.put("dao.impl", daoImplList);

        for (Map.Entry<String, List<String>> entry : beanMap.entrySet()) {
            String filePath = PropertiesContext.getInstance().getProjectPath() + "/" + (PropertiesContext.getInstance().getCodePackage() + "." + entry.getKey()).replace(".", "/");
            final List<String> suffixList = entry.getValue();
            for (String suffix : suffixList) {
                try {
                    FreeMarkerUtil.otherProcess(suffix + "Template.ftl", entityName + suffix + ".java", "GBK", freeMarkerData, filePath);
                    log.error("生成" + entityName + suffix + "成功!");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * @param
     * @description: 解析输出属性
     * @date 2021/10/03 13:23
     */
    private String processAllAttrs() {

        StringBuffer content = new StringBuffer();
        StringBuffer idStr = new StringBuffer();
        StringBuffer otherStr = new StringBuffer();
        for (int i = 0; i < TableMetaCache.getInstance().getColNames().size(); i++) {
            //列名
            final String ColName = TableMetaCache.getInstance().getColNames().get(i);
            //属性名称（首字母小写）
            String attr = Table2ClassUtil.initcapColName(ColName);
            //列类型
            final String colType = TableMetaCache.getInstance().getColTypes().get(i);
            //列长度
            final Integer intlength = TableMetaCache.getInstance().getIntLengths().get(i);
            final String comment = TableMetaCache.getInstance().getComments().get(i);
            String dataType = Table2ClassUtil.fieldType2JavaType(colType);
            if (attr.equals("id")) {
                idStr.append("@Id\r\n");
                idStr.append("\t@Column(name = \"ID\", nullable = false)\r\n");
                idStr.append("\t@GenericGenerator(name = \"uuid\", strategy = \"org.hibernate.id.UUIDGenerator\")\r\n");
                idStr.append("\t@GeneratedValue(generator = \"uuid\")\r\n");
                idStr.append("\tprivate " + dataType + " " + attr + ";\r\n\r\n");
            } else if (dataType.equals("CLOB") || dataType.equals("TEXT")) {//String 大字段
                otherStr.append("\t@Column(name = \"" + ColName + "\", columnDefinition = \"text COMMENT '" + comment + "'\")\r\n");
                otherStr.append("\tprivate String " + attr + ";\r\n\r\n");
            } else if (dataType.equals("Date") || dataType.equals("Long")) {
                otherStr.append("\t@Column(name = \"" + ColName + "\", columnDefinition = \"" + colType + " COMMENT '" + comment + "'\")\r\n");
                otherStr.append("\tprivate " + dataType + " " + attr + ";\r\n\r\n");
            } else {
                otherStr.append("\t@Column(name = \"" + ColName + "\", columnDefinition = \"" + colType + "(" + intlength + ")" + " COMMENT '" + comment + "'\")\r\n");
                otherStr.append("\tprivate " + dataType + " " + attr + ";\r\n\r\n");
            }
        }
        content.append(idStr.toString());
        content.append(otherStr.toString());
        return content.toString();
    }

}
