package org.mybatis.generator.plugins.java;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.TopLevelClass;

public class GeneratorControllerClassAdapter extends AbsJavaGenerator {

	public static final String ROOT_CLASS_KEY = "rootClass";

	private TopLevelClass controllerClass;

	@Override
	protected FullyQualifiedJavaType getJavaType() {
		return controllerClass.getType();
	}

	@Override
	public List<CompilationUnit> getCompilationUnits() {
		String targetPackage = getTargetPackage();
		String rootClass = getProperty(ROOT_CLASS_KEY);

		FullyQualifiedJavaType modelType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
		String controllerClassName = String.format("%s.%sController", targetPackage, modelType.getShortName());

		String serviceTargetPackage = getProperty("serviceTargetPackage");
		String serviceClassName = String.format("%s.I%sService", serviceTargetPackage, modelType.getShortName());

		controllerClass = new TopLevelClass(controllerClassName);
		controllerClass.setVisibility(JavaVisibility.PUBLIC);

		FullyQualifiedJavaType basicService = new FullyQualifiedJavaType(rootClass);
		controllerClass.setSuperClass(basicService);
		controllerClass.addImportedType(basicService);

		getContext().getCommentGenerator().addClassComment(controllerClass, introspectedTable);

		String springPackage = "org.springframework.web.bind.annotation.";
		/**
		 * 添加 ControllerAdvice 注解
		 */
		FullyQualifiedJavaType controllerAdviceAnnotation = new FullyQualifiedJavaType(springPackage + "ControllerAdvice");
		controllerClass.addAnnotation("@" + controllerAdviceAnnotation.getShortName());
		controllerClass.addImportedType(controllerAdviceAnnotation);

		/**
		 * 添加 ControllerAdvice 注解
		 */
		String contextPackage = "org.springframework.context.annotation.";
		FullyQualifiedJavaType acopeAnnotation = new FullyQualifiedJavaType(contextPackage + "Scope");
		controllerClass.addAnnotation("@" + acopeAnnotation.getShortName() + "(BeanHolder.SCOPE_PROTOTYPE)");
		controllerClass.addImportedType(new FullyQualifiedJavaType("com.leafnet.itao.commons.BeanHolder"));
		controllerClass.addImportedType(acopeAnnotation);

		/**
		 * 添加 RequestMapping 注解
		 */
		FullyQualifiedJavaType requestMappingAnnotation = new FullyQualifiedJavaType(springPackage + "RequestMapping");
		String requestMappingUrl = "\"/" + StringUtils.uncapitalize(modelType.getShortName() + "\"");
		controllerClass.addAnnotation("@" + requestMappingAnnotation.getShortName() + "(" + requestMappingUrl + ")");
		controllerClass.addImportedType(requestMappingAnnotation);

		/**
		 * 添加 Service 自动注入
		 */
		FullyQualifiedJavaType serviceType = new FullyQualifiedJavaType(serviceClassName);
		controllerClass.addImportedType(requestMappingAnnotation);

		String serviceFieldName = StringUtils.uncapitalize(serviceType.getShortName());
		Field field = new Field(serviceFieldName, serviceType);
		field.setVisibility(JavaVisibility.PRIVATE);
		controllerClass.addImportedType(serviceType);

		String beanPackage = "org.springframework.beans.factory.annotation.";
		FullyQualifiedJavaType autowiredAnnotation = new FullyQualifiedJavaType(beanPackage + "Autowired");
		field.addAnnotation("@" + autowiredAnnotation.getShortName());
		controllerClass.addImportedType(autowiredAnnotation);
		controllerClass.addField(field);

		writeFile(controllerClass.getFormattedContent(), controllerClass.getType().getShortName());
		return Collections.emptyList();
	}
}
