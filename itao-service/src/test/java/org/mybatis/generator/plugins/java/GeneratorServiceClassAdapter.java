package org.mybatis.generator.plugins.java;

import java.util.Collections;
import java.util.List;

import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.TopLevelClass;

public class GeneratorServiceClassAdapter extends AbsJavaGenerator {

	public static final String ROOT_CLASS_KEY = "rootClass";

	private TopLevelClass serviceClass;

	@Override
	protected FullyQualifiedJavaType getJavaType() {
		return serviceClass.getType();
	}

	@Override
	public List<CompilationUnit> getCompilationUnits() {
		String targetPackage = getTargetPackage();
		String rootClass = getProperty(ROOT_CLASS_KEY);

		FullyQualifiedJavaType modelType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());

		String serviceInterfaceName = String.format("%s.I%sService", targetPackage, modelType.getShortName());
		String serviceClassName = String.format("%s.impl.%sServiceImpl", targetPackage, modelType.getShortName());

		serviceClass = new TopLevelClass(serviceClassName);
		serviceClass.setVisibility(JavaVisibility.PUBLIC);
		serviceClass.addImportedType(modelType);

		getContext().getCommentGenerator().addClassComment(serviceClass, introspectedTable);

		FullyQualifiedJavaType serviceInterface = new FullyQualifiedJavaType(serviceInterfaceName);
		serviceClass.addSuperInterface(serviceInterface);
		serviceClass.addImportedType(serviceInterface);

		FullyQualifiedJavaType basicService = new FullyQualifiedJavaType(rootClass);
		basicService.addTypeArgument(modelType);
		serviceClass.setSuperClass(basicService);
		serviceClass.addImportedType(basicService);

		serviceClass.addAnnotation("@Service");
		serviceClass.addImportedType(new FullyQualifiedJavaType("org.springframework.stereotype.Service"));

		writeFile(serviceClass.getFormattedContent(), serviceClass.getType().getShortName());
		return Collections.emptyList();
	}

}
