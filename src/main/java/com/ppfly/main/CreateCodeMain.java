package com.ppfly.main;

import com.ppfly.factory.AutoGenerationCodeTool;
import com.ppfly.factory.BeanProperties;
import com.ppfly.util.ConnectionUtil;

import java.sql.SQLException;


public class CreateCodeMain {

	/**
	 * @param  args
	 * @throws SQLException 
	 */
	public static void main(String[] args) {

		BeanProperties.setPackage("com.ztesoft.bss.salesres");//包名
		BeanProperties.setTableName("COUPON_INST");//数据库表名
		BeanProperties.setSchema("test");//数据库名称
 		BeanProperties.setPath("D:/java");//生成的类存放的路径
 		BeanProperties.setAuthor("ppfly");//代码生成者
		ConnectionUtil.dbUrl = "jdbc:mysql://localhost:3306/test?characterEncoding=utf-8&serverTimezone=UTC";
		ConnectionUtil.theUser = "root";//数据库登录用户
		ConnectionUtil.thePw = "123456";//数据库登录用户密码
		//生成代码
		new AutoGenerationCodeTool().createCode();

	}

}
