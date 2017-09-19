package org.mybatis.generator.plugins;

import java.io.Serializable;

import org.apache.commons.lang3.ClassUtils;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.TopLevelClass;

/**
 * 实现 Serializable 接口
 * @author Wangd
 */
public class LeafSerializablePlugin extends SerializablePlugin {

	@Override
	protected void makeSerializable(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		FullyQualifiedJavaType superClassStr = topLevelClass.getSuperClass();
		if (superClassStr != null) {
			try {
				String fullyQualifiedName = superClassStr.getFullyQualifiedName();
				@SuppressWarnings("unchecked")
				Class<Serializable> superClass = (Class<Serializable>) ClassUtils.getClass(fullyQualifiedName);
				if (superClass == null)
					throw new RuntimeException();
				Field field = new Field();
				field.setFinal(true);
				field.setInitializationString("1L");
				field.setName("serialVersionUID");
				field.setStatic(true);
				field.setType(new FullyQualifiedJavaType("long"));
				field.setVisibility(JavaVisibility.PRIVATE);
				//context.getCommentGenerator().addFieldComment(field, introspectedTable);
				topLevelClass.getFields().add(0, field);
				return;
			} catch (Exception e) {
				// 忽略
			}
		}
		super.makeSerializable(topLevelClass, introspectedTable);
	}
}
