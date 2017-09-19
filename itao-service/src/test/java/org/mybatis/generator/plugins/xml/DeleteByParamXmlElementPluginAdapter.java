package org.mybatis.generator.plugins.xml;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import org.mybatis.generator.config.Context;

/**
 * 生成 deleteByParam 节点
 * @author Wangd
 */
public class DeleteByParamXmlElementPluginAdapter extends BasicXmlElementPluginAdapter {

	@Override
	public void sqlMapElementGenerated(Context context, XmlElement parentElement, IntrospectedTable introspectedTable) {
		super.sqlMapElementGenerated(context, parentElement, introspectedTable);

		XmlElement deleteElement = createElement("delete", "deleteByParam");
		deleteElement.addAttribute(new Attribute("parameterType", "map"));

		StringBuilder sb = new StringBuilder();
		sb.append("delete from ");
		sb.append(introspectedTable.getFullyQualifiedTableNameAtRuntime());
		deleteElement.addElement(new TextElement(sb.toString()));

		boolean and = false;
		for (IntrospectedColumn introspectedColumn : introspectedTable.getPrimaryKeyColumns()) {
			sb.setLength(0);
			if (and) {
				sb.append("  and ");
			} else {
				sb.append("where ");
				and = true;
			}

			sb.append(MyBatis3FormattingUtilities.getEscapedColumnName(introspectedColumn));
			sb.append(" = ");
			sb.append(MyBatis3FormattingUtilities.getParameterClause(introspectedColumn));
			deleteElement.addElement(new TextElement(sb.toString()));
		}
		parentElement.addElement(deleteElement);
	}
}
