package org.mybatis.generator.plugins.xml;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.config.Context;

/**
 * 生成 queryCountByParam 节点
 * @author Wangd
 */
public class QueryCountByParamXmlElementPluginAdapter extends BasicXmlElementPluginAdapter {

	@Override
	public void sqlMapElementGenerated(Context context, XmlElement parentElement, IntrospectedTable introspectedTable) {
		super.sqlMapElementGenerated(context, parentElement, introspectedTable);
		XmlElement queryCountByParamElement = createElement("select", "queryCountByParam");
		queryCountByParamElement.addAttribute(new Attribute("parameterType", "map"));
		queryCountByParamElement.addAttribute(new Attribute("resultType", "long"));

		addTextElement(queryCountByParamElement, "select count(*) from (");
		queryCountByParamElement.addElement(createElement("include", "refid", ID_BASE_QUERY_SQL));
		addTextElement(queryCountByParamElement, ") t");
		parentElement.addElement(queryCountByParamElement);
	}
}
