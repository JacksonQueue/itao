package org.mybatis.generator.plugins.java;

import java.util.Collections;
import java.util.List;

import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;

public class GeneratorServiceInterfaceAdapter extends AbsJavaGenerator {

	public static final String ROOT_INTERFACE_KEY = "rootInterface";

	private Interface serviceInterface;

	@Override
	protected FullyQualifiedJavaType getJavaType() {
		return serviceInterface.getType();
	}

	@Override
	public List<CompilationUnit> getCompilationUnits() {
		String targetPackage = getTargetPackage();
		String rootInterfaceName = getProperties().getProperty(ROOT_INTERFACE_KEY);

		FullyQualifiedJavaType modelType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
		String interfaceClassName = String.format("%s.I%sService", targetPackage, modelType.getShortName());

		serviceInterface = new Interface(interfaceClassName);
		serviceInterface.setVisibility(JavaVisibility.PUBLIC);
		serviceInterface.addImportedType(modelType);
		
		getContext().getCommentGenerator().addJavaFileComment(serviceInterface);

		FullyQualifiedJavaType rootInterface = new FullyQualifiedJavaType(rootInterfaceName);
		rootInterface.addTypeArgument(modelType);
		serviceInterface.addSuperInterface(rootInterface);
		serviceInterface.addImportedType(rootInterface);
		writeFile(serviceInterface.getFormattedContent(), serviceInterface.getType().getShortName());
		return Collections.emptyList();
	}

}
