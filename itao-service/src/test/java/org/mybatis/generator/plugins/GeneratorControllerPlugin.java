package org.mybatis.generator.plugins;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.plugins.java.AbsJavaGenerator;
import org.mybatis.generator.plugins.java.GeneratorControllerClassAdapter;

/**
 * 按模板生成Java Mapper
 */
public class GeneratorControllerPlugin extends PluginAdapter {

	@Override
	public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {
		List<AbsJavaGenerator> fileList = new ArrayList<>();
		fileList.add(new GeneratorControllerClassAdapter());

		for (AbsJavaGenerator javaGenerator : fileList) {
			javaGenerator.setIntrospectedTable(introspectedTable);
			javaGenerator.setContext(getContext());
			javaGenerator.setProperties(getProperties());
			javaGenerator.getCompilationUnits();
		}
		return Collections.emptyList();
	}

	@Override
	public boolean validate(List<String> warnings) {
		return true;
	}
}
