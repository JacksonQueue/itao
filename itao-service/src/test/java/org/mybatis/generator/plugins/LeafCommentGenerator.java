package org.mybatis.generator.plugins;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.InnerClass;
import org.mybatis.generator.internal.DefaultCommentGenerator;
import org.mybatis.generator.internal.util.StringUtility;

/**
 * 公共注解
 * @author Wangd
 */
public class LeafCommentGenerator extends DefaultCommentGenerator {

	protected Properties properties = new Properties();

	public void addConfigurationProperties(Properties properties) {
		this.properties.putAll(properties);
		super.addConfigurationProperties(properties);
	}
	
	@Override
	public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable) {
		innerClass.addJavaDocLine("/**");
		innerClass.addJavaDocLine(" * @author " + properties.getProperty("author"));
		innerClass.addJavaDocLine(" * @version 1.0.0");
		innerClass.addJavaDocLine(" * @date " + new SimpleDateFormat("yyyy年MM月dd日").format(new Date()));
		innerClass.addJavaDocLine(" */");
	}

	@Override
	public void addFieldComment(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
		String remarks = introspectedColumn.getRemarks();
		if (StringUtility.stringHasValue(remarks)) {
			field.addJavaDocLine("/**");
			field.addJavaDocLine(" * " + remarks);
			field.addJavaDocLine(" */");
		}
	}
}
