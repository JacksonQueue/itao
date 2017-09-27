package org.mybatis.generator.plugins;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.springframework.util.ReflectionUtils;

import com.leafnet.itao.mapper.basic.BasicMapper;

/**
 * 按模板生成Java Mapper
 * @author Wangd
 */
public class GeneratorMapperPlugin extends PluginAdapter {
	
	@Override
	public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		FullyQualifiedJavaType superInterface = new FullyQualifiedJavaType("BasicMapper");
		superInterface.addTypeArgument(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()));

		interfaze.getMethods().clear();
		FullyQualifiedJavaType importSuperInterface = new FullyQualifiedJavaType(BasicMapper.class.getName());
		interfaze.addImportedType(importSuperInterface);

		interfaze.getSuperInterfaceTypes().add(superInterface);

		try {
			// 反射重置导入的类文件
			Field importedTypesField = ReflectionUtils.findField(interfaze.getClass(), "importedTypes");
			importedTypesField.setAccessible(true);
			@SuppressWarnings("unchecked")
			
			
			Set<FullyQualifiedJavaType> importedTypes = (Set<FullyQualifiedJavaType>) importedTypesField.get(interfaze);
			Set<FullyQualifiedJavaType> newImportedTypes = new HashSet<FullyQualifiedJavaType>(importedTypes);
			
			if (importedTypes != null && !importedTypes.isEmpty()) {
				for (FullyQualifiedJavaType importedType : importedTypes) {
					if ("List".equals(importedType.getShortName())) {
						newImportedTypes.remove(importedType);
					}
				}
				importedTypesField.set(interfaze, newImportedTypes);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return super.clientGenerated(interfaze, topLevelClass, introspectedTable);
		
	}

	@Override
	public boolean validate(List<String> warnings) {
		return true;
	}
}
