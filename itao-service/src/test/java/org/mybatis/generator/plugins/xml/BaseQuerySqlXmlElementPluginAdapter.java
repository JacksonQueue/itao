package org.mybatis.generator.plugins.xml;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.config.Context;

/**
 * 生成 BaseQuerySql 节点
 * @author Wangd
 */
public class BaseQuerySqlXmlElementPluginAdapter extends BasicXmlElementPluginAdapter {

	@Override
	public void sqlMapElementGenerated(Context context, XmlElement parentElement, IntrospectedTable introspectedTable) {
		super.sqlMapElementGenerated(context, parentElement, introspectedTable);
		addCommentTag(parentElement, "单表的查询语句,提供给其他单表查询的SQL引用");
		XmlElement baseQuerySqlElement = createElement("sql", ID_BASE_QUERY_SQL);

		addTextElement(baseQuerySqlElement, "select");
		baseQuerySqlElement.addElement(createElement("include", "refid", ID_BASE_QUERY_COLUM_LIST));

		addTextElement(baseQuerySqlElement, "from " + introspectedTable.getFullyQualifiedTableNameAtRuntime());

		baseQuerySqlElement.addElement(createElement("include", "refid", ID_BASE_QUERY_WHERE));
		parentElement.addElement(baseQuerySqlElement);
	}
}
