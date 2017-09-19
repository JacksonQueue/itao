package org.mybatis.generator.plugins.xml;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import org.mybatis.generator.config.Context;

/**
 * 生成 BaseQueryWhere 节点
 * @author Wangd
 */
public class BaseQueryWhereXmlElementPluginAdapter extends BasicXmlElementPluginAdapter {

	@Override
	public void sqlMapElementGenerated(Context context, XmlElement parentElement, IntrospectedTable introspectedTable) {
		super.sqlMapElementGenerated(context, parentElement, introspectedTable);
		addCommentTag(parentElement, "基础的查询条件,提供给其他单表查询的SQL引用");
		XmlElement baseQueryWhereElement = createElement("sql", ID_BASE_QUERY_WHERE);

		addTextElement(baseQueryWhereElement, "where 1=1");

		StringBuilder sb = new StringBuilder();
		List<IntrospectedColumn> columns = new ArrayList<>();
		columns.addAll(introspectedTable.getPrimaryKeyColumns());

		IntrospectedColumn validityColumn = introspectedTable.getColumn("validity");
		if (validityColumn != null) {
			columns.add(validityColumn);
		}

		Iterator<IntrospectedColumn> iter = columns.iterator();
		while (iter.hasNext()) {
			IntrospectedColumn introspectedColumn = iter.next();
			XmlElement ifEl = createElement("if", "test", introspectedColumn.getJavaProperty() + " != null");
			sb.append("and ");
			sb.append(MyBatis3FormattingUtilities.getEscapedColumnName(introspectedColumn));
			sb.append(" = ");
			sb.append(MyBatis3FormattingUtilities.getParameterClause(introspectedColumn));
			addTextElement(ifEl, sb.toString());
			sb.setLength(0);
			baseQueryWhereElement.addElement(ifEl);
		}
		parentElement.addElement(baseQueryWhereElement);
	}
}
