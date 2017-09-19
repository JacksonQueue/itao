package org.mybatis.generator.plugins.xml;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.AbstractXmlElementGenerator;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.ResultMapWithoutBLOBsElementGenerator;
import org.mybatis.generator.config.Context;

/**
 * 生成 BaseResultMap 节点
 * @author Wangd
 * @version 1.0.0
 * @date 2015年7月9日
 */
public class BaseResultMapXmlElementPluginAdapter extends BasicXmlElementPluginAdapter {

	@Override
	public void sqlMapElementGenerated(Context context, XmlElement parentElement, IntrospectedTable introspectedTable) {
		super.sqlMapElementGenerated(context, parentElement, introspectedTable);
		addCommentTag(parentElement, "基础字段映射");
		AbstractXmlElementGenerator elementGenerator = new ResultMapWithoutBLOBsElementGenerator(true);
		initializeAndExecuteGenerator(context, elementGenerator, parentElement, introspectedTable);
	}
}
