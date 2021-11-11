package com.ppfly.util;

import java.util.List;

public class Table2ClassUtil {

    /**
     * 获取实例名称
     *
     * @param name
     * @return
     */
    public static String getCaptureName(String name) {
        char[] cs = name.toCharArray();
        if (cs[0] >= 'a' && cs[0] <= 'z') {
            return String.valueOf(cs);
        } else {
            cs[0] += 32;
            return String.valueOf(cs);
        }
    }

    /**
     * 通过表名获取实体Entity名称
     *
     * @param tableName
     * @return
     */
    public static String getEntityNameFromTableName(String tableName) {
        final String lowerCaseTableName = tableName.trim().toLowerCase();
        if (lowerCaseTableName.startsWith("t_")) {
            tableName = tableName.substring(2);
        }
        String word[] = tableName.split("_");
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < word.length; i++) {
            char[] ch = word[i].toCharArray();
            if (ch[0] >= 'a' && ch[0] <= 'z') {
                ch[0] = (char) (ch[0] - 32);
            }
            sb.append(new String(ch));
        }
        return sb.toString();
    }

    /**
     * 通过包名获取实体Entity名称
     *
     * @param codePackage
     * @return
     */
    public static String getEntityNameFromCodePackage(String codePackage) {
        String lowerCaseEntityName = codePackage.substring(codePackage.lastIndexOf(".") + 1).toLowerCase();
        //进行字母的ASCII编码前移，效率要高于截取字符串进行转换的操作
        char[] chars = lowerCaseEntityName.toCharArray();
        chars[0] -= 32;
        return String.valueOf(chars);
    }


    /**
     * 将字段生成属性首字母小写, 把列名输入字符串的第二"_"后的首字母改成大写
     *
     * @param ColName
     * @return
     */
    public static String initcapColName(String ColName) {
        String word[] = ColName.trim().toLowerCase().split("_");
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < word.length; i++) {
            char[] ch = word[i].toCharArray();
            if (i > 0) {
                if (ch[0] >= 'a' && ch[0] <= 'z') {
                    ch[0] = (char) (ch[0] - 32);
                }
            }
            sb.append(new String(ch));
        }
        return sb.toString();
    }

    /**
     * @param
     * @description: 获取所有数据库字段名称
     * @date 2021/10/03 13:24
     */
    public static String getColumnName(List<String> colNames) {
        StringBuffer content = new StringBuffer();
        for (int i = 0; i < colNames.size(); i++) {
            if (i == colNames.size() - 1) {
                content.append("\t" + colNames.get(i));//字段名称
            } else {
                content.append("\t" + colNames.get(i) + ",");//字段名称
                content.append("\r\n");//换行隔开
            }
        }
        return content.toString();
    }


    /**
     * @param
     * @description: 数据库字段类型转Java实例变量类型
     * @date 2021/10/03 19:31
     */
    public static String fieldType2JavaType(String fileType) {

        if (checkNumber(fileType)) {
            return "Long";
        } else if (checkString(fileType)) {
            return "String";
        } else if (checkDate(fileType)) {
            return "Date";
        } else if (FieldTypeConsts.CLOB.equalsIgnoreCase(fileType)) {
            return "CLOB";
        } else if (FieldTypeConsts.TEXT.equalsIgnoreCase(fileType)) {
            return "TEXT";
        } else {
            return null;
        }

    }

    /**
     * @param
     * @description: 检查字段类型是否是数字类型
     * @date 2021/10/03 19:17
     */
    private static boolean checkNumber(String fieldType) {

        if (FieldTypeConsts.INT.equalsIgnoreCase(fieldType) ||
                FieldTypeConsts.INTEGER.equalsIgnoreCase(fieldType) ||
                FieldTypeConsts.TINYINT.equalsIgnoreCase(fieldType) ||
                FieldTypeConsts.BIGINT.equalsIgnoreCase(fieldType) ||
                FieldTypeConsts.NUMBER.equalsIgnoreCase(fieldType) ||
                FieldTypeConsts.DECIMAL.equalsIgnoreCase(fieldType)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param
     * @description: 检查字段类型是否是字符串类型
     * @date 2021/10/03 19:17
     */
    private static boolean checkString(String fieldType) {

        if (FieldTypeConsts.VARCHAR.equalsIgnoreCase(fieldType) ||
                FieldTypeConsts.CHAR.equalsIgnoreCase(fieldType) ||
                FieldTypeConsts.VARCHAR2.equalsIgnoreCase(fieldType) ||
                FieldTypeConsts.NVARCHAR2.equalsIgnoreCase(fieldType)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param
     * @description: 检查字段类型是否是日期类型
     * @date 2021/10/03 19:17
     */
    private static boolean checkDate(String fieldType) {

        if (FieldTypeConsts.DATE.equalsIgnoreCase(fieldType) ||
                FieldTypeConsts.TIMESTAMP.equalsIgnoreCase(fieldType) ||
                FieldTypeConsts.DATETIME.equalsIgnoreCase(fieldType)) {
            return true;
        } else {
            return false;
        }
    }


}
