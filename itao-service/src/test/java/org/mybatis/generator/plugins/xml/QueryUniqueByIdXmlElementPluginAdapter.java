package org.mybatis.generator.plugins.xml;

import java.util.List;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import org.mybatis.generator.config.Context;

/**
 * 生成 queryUniqueById 节点
 * @author Wangd
 */
public class QueryUniqueByIdXmlElementPluginAdapter extends BasicXmlElementPluginAdapter {

	@Override
	public void sqlMapElementGenerated(Context context, XmlElement parentElement, IntrospectedTable introspectedTable) {
		super.sqlMapElementGenerated(context, parentElement, introspectedTable);
		XmlElement queryUniqueByIdElement = createElement("select", ID_QUERY_UNIQUE_BY_ID);

		queryUniqueByIdElement.addAttribute(new Attribute("parameterType", getPrimaryKeyParameterType(introspectedTable)));
		queryUniqueByIdElement.addAttribute(new Attribute("resultMap", introspectedTable.getBaseResultMapId()));

		addTextElement(queryUniqueByIdElement, "select");
		queryUniqueByIdElement.addElement(createElement("include", "refid", ID_BASE_QUERY_COLUM_LIST));
		addTextElement(queryUniqueByIdElement, "from " + introspectedTable.getFullyQualifiedTableNameAtRuntime());

		boolean and = false;
		StringBuilder sb = new StringBuilder();
		List<IntrospectedColumn> primaryKeyColumns = introspectedTable.getPrimaryKeyColumns();
		for (IntrospectedColumn introspectedColumn : primaryKeyColumns) {
			sb.setLength(0);
			if (and) {
				sb.append("  and ");
			} else {
				sb.append("where ");
				and = true;
			}
			sb.append(MyBatis3FormattingUtilities.getAliasedEscapedColumnName(introspectedColumn));
			sb.append(" = ");
			sb.append(MyBatis3FormattingUtilities.getParameterClause(introspectedColumn));
			queryUniqueByIdElement.addElement(new TextElement(sb.toString()));
		}
		parentElement.addElement(queryUniqueByIdElement);
	}
}
