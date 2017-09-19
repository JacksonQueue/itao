package org.mybatis.generator.plugins;

import java.lang.reflect.Modifier;

import org.apache.commons.lang3.ClassUtils;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;

/**
 * 重写 toString 方法
 * @author Wangd
 * @version 1.0.0
 * @date 2015年7月8日
 */
public class LeafToStringPlugin extends ToStringPlugin {

	@Override
	public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		generateToString(introspectedTable, topLevelClass);
		return true;
	}

	@Override
	public boolean modelRecordWithBLOBsClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		generateToString(introspectedTable, topLevelClass);
		return true;
	}

	@Override
	public boolean modelPrimaryKeyClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		generateToString(introspectedTable, topLevelClass);
		return true;
	}

	private void generateToString(IntrospectedTable introspectedTable, TopLevelClass topLevelClass) {
		Method method = new Method();
		method.setVisibility(JavaVisibility.PUBLIC);
		method.setReturnType(FullyQualifiedJavaType.getStringInstance());
		method.setName("toString");
		if (introspectedTable.isJava5Targeted()) {
			method.addAnnotation("@Override");
		}

		context.getCommentGenerator().addGeneralMethodComment(method, introspectedTable);

		method.addBodyLine("StringBuilder sb = new StringBuilder();");
		method.addBodyLine("sb.append(getClass().getSimpleName());");
		method.addBodyLine("sb.append(\" [\");");
		method.addBodyLine("sb.append(\"Hash = \").append(hashCode());");

		StringBuilder sb = new StringBuilder();

		/**
		 * 反射父类数据项
		 */
		try {
			FullyQualifiedJavaType superClassStr = topLevelClass.getSuperClass();

			if (superClassStr != null) {
				Class<?> superClass = ClassUtils.getClass(superClassStr.getFullyQualifiedName());
				java.lang.reflect.Field[] fields = superClass.getDeclaredFields();

				for (java.lang.reflect.Field field : fields) {
					if (Modifier.isStatic(field.getModifiers())) {
						continue;
					}
					field.setAccessible(true);
					String property = field.getName();
					sb.setLength(0);
					sb.append("sb.append(\"").append(", ").append(property);
					sb.append("=\")").append(".append(").append(property).append(");");
					method.addBodyLine(sb.toString());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (Field field : topLevelClass.getFields()) {
			String property = field.getName();
			sb.setLength(0);
			sb.append("sb.append(\"").append(", ").append(property);
			sb.append("=\")").append(".append(").append(property).append(");");
			method.addBodyLine(sb.toString());
		}
		method.addBodyLine("sb.append(\"]\");");
		method.addBodyLine("return sb.toString();");
		topLevelClass.addMethod(method);
	}
}
