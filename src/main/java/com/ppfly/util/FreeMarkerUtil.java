package com.ppfly.util;

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

public class FreeMarkerUtil {

    /**
     * @param modelName 模板名如： 模板.htm
     * @param fileName  要生成的文件的文件名如：index.html
     * @param charSet   生成文件的编码 如：UTF-8
     * @param distDir   生成目标文件的目录  不需要以 / 开头
     */
    public static void otherProcess(String modelName, String fileName, String charSet, Map<String, String> freeMarkerData, String distDir)
            throws Exception {
        //检查是否不存在此目录，不存在时创建目录
        String filePathDir = getOrMkdirs(distDir) + "/";
        final Configuration cfg = SpringContextUtil.getBean("freemarkerConfig");
        Template t = cfg.getTemplate(modelName);
        Writer out = null;
        try {
            out = new OutputStreamWriter(new FileOutputStream(filePathDir + fileName), charSet);
            t.process(freeMarkerData, out);
        } finally {
            out.close();
        }
    }

    /**
     * 返回或创建并返回目标目录
     *
     * @param distDir
     * @return
     */
    private static String getOrMkdirs(String distDir) {
        File file = new File(distDir);
        if (!file.exists()) {
            file.mkdirs();
        }
        return distDir;
    }

}
