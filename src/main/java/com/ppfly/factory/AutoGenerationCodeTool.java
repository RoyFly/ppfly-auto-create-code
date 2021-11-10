package com.ppfly.factory;

import com.ppfly.cache.PropertiesContext;
import com.ppfly.cache.TableMetaCache;
import com.ppfly.exception.AccException;
import com.ppfly.util.FreeMarkerManager;
import com.ppfly.util.Table2ClassUtil;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.*;


@Slf4j
public class AutoGenerationCodeTool {

    /**
     * @param
     * @Description: 生成代码
     * @author Created on 2019/5/14 20:06
     */
    public void createCode() {

        try {
            //模板
            Map<String, Object> freeMarkerData = new HashMap();
            FreeMarkerManager freeMarker = new FreeMarkerManager();

            //获取实体名称
            String entityName = Table2ClassUtil.initcapTableName(PropertiesContext.getInstance().getTableName());

            //初始化模板数据
            initFreeMarkerData(freeMarker, freeMarkerData, entityName);

            //根据freemarker模板生成代码
            generateCode(freeMarker, freeMarkerData, entityName);

        } catch (AccException ae) {
            ae.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 生成代码核心逻辑
     *
     * @param freeMarker
     * @param freeMarkerData
     * @param entityName
     */
    private void generateCode(FreeMarkerManager freeMarker, Map<String, Object> freeMarkerData, String entityName) {
        try {
            //初始化freemarker配置，并设置freemarker模板文件所在的路径
            freeMarker.init();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //生成实体
        createEntity(freeMarker, freeMarkerData, entityName);
        //开始生成service、controller、dao等代码
        generateOtherCode(freeMarker, freeMarkerData, entityName);
    }

    /**
     * @param entityName
     * @param freeMarkerData
     * @param freeMarker
     * @Description: 生成实体
     * @author Created on 2019/3/21 15:57
     */
    private void createEntity(FreeMarkerManager freeMarker, Map<String, Object> freeMarkerData, String entityName) {

        String filePath = PropertiesContext.getInstance().getProjectPath() + "/" + PropertiesContext.getInstance().getCodePackage().replace(".", "/") + "/entity";

        log.info("实体存放路径:" + filePath);
        boolean y = freeMarker.otherProcess("entityTemplate.ftl", entityName + ".java", "GBK", freeMarkerData, filePath);

        if (y) {
           log.error("生成实体" + entityName + ".java成功!");
        }
    }


    /**
     * @param
     * @Description: 初始化生成代码所需的参数值
     * @author Created on 2019/3/23 13:33
     */
    private void initFreeMarkerData(FreeMarkerManager freeMarker, Map<String, Object> freeMarkerData, String entityName) {
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
    }

    /**
     * 生成service、controller、dao等代码
     *
     * @param freeMarker
     * @param freeMarkerData
     * @param entityName
     */
    private void generateOtherCode(FreeMarkerManager freeMarker, Map<String, Object> freeMarkerData, String entityName) {

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
                boolean y = freeMarker.otherProcess(suffix + "Template.ftl", entityName + suffix + ".java", "GBK", freeMarkerData, filePath);

                if (y) {
                    log.error("生成" + entityName + suffix + "成功!");
                }
            }
        }
    }


    /**
     * @param
     * @Description: 解析输出属性
     * @author Created on 2019/3/23 13:23
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
