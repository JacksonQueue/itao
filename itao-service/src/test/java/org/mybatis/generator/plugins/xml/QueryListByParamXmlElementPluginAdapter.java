package org.mybatis.generator.plugins.xml;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.config.Context;

/**
 * 生成 queryListByParam 节点
 * @author Wangd
 */
public class QueryListByParamXmlElementPluginAdapter extends BasicXmlElementPluginAdapter {

	@Override
	public void sqlMapElementGenerated(Context context, XmlElement parentElement, IntrospectedTable introspectedTable) {
		super.sqlMapElementGenerated(context, parentElement, introspectedTable);
		XmlElement queryListByParamElement = createElement("select", "queryListByParam");
		queryListByParamElement.addAttribute(new Attribute("parameterType", "map"));
		queryListByParamElement.addAttribute(new Attribute("resultMap", introspectedTable.getBaseResultMapId()));

		queryListByParamElement.addElement(createElement("include", "refid", ID_BASE_PAGINATION_PREFIX));
		queryListByParamElement.addElement(createElement("include", "refid", ID_BASE_QUERY_SQL));
		queryListByParamElement.addElement(createElement("include", "refid", ID_BASE_PAGINATION_SUFFIX));

		parentElement.addElement(queryListByParamElement);
	}
}
