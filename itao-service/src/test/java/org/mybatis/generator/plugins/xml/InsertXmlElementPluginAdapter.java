package org.mybatis.generator.plugins.xml;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.OutputUtilities;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.GeneratedKey;

/**
 * 生成 insert 节点
 * @author Wangd
 */
public class InsertXmlElementPluginAdapter extends BasicXmlElementPluginAdapter {

	@Override
	public void sqlMapElementGenerated(Context context, XmlElement parentElement, IntrospectedTable introspectedTable) {
		super.sqlMapElementGenerated(context, parentElement, introspectedTable);
		XmlElement insertElement = createElement("insert", "insert");

		insertElement.addAttribute(new Attribute("parameterType", introspectedTable.getBaseRecordType()));

		GeneratedKey gk = introspectedTable.getGeneratedKey();
		if (gk != null) {
			IntrospectedColumn introspectedColumn = introspectedTable.getColumn(gk.getColumn());
			if (introspectedColumn != null) {
				if (gk.isJdbcStandard()) {
					insertElement.addAttribute(new Attribute("useGeneratedKeys", "true"));
					insertElement.addAttribute(new Attribute("keyProperty", introspectedColumn.getJavaProperty()));
				} else {
					insertElement.addElement(getSelectKey(introspectedColumn, gk));
				}
			}
		}

		StringBuilder insertClause = new StringBuilder();
		StringBuilder valuesClause = new StringBuilder();

		insertClause.append("insert into ");
		insertClause.append(introspectedTable.getFullyQualifiedTableNameAtRuntime());
		insertClause.append(" (");

		valuesClause.append("values (");

		List<String> valuesClauses = new ArrayList<String>();
		Iterator<IntrospectedColumn> iter = introspectedTable.getAllColumns().iterator();
		while (iter.hasNext()) {
			IntrospectedColumn introspectedColumn = iter.next();
			if (introspectedColumn.isIdentity()) {
				continue;
			}

			insertClause.append(MyBatis3FormattingUtilities.getEscapedColumnName(introspectedColumn));
			valuesClause.append(MyBatis3FormattingUtilities.getParameterClause(introspectedColumn));
			if (iter.hasNext()) {
				insertClause.append(", ");
				valuesClause.append(", ");
			}

			if (valuesClause.length() > 80) {
				insertElement.addElement(new TextElement(insertClause.toString()));
				insertClause.setLength(0);
				OutputUtilities.xmlIndent(insertClause, 1);

				valuesClauses.add(valuesClause.toString());
				valuesClause.setLength(0);
				OutputUtilities.xmlIndent(valuesClause, 1);
			}
		}

		insertClause.append(')');
		insertElement.addElement(new TextElement(insertClause.toString()));

		valuesClause.append(')');
		valuesClauses.add(valuesClause.toString());

		for (String clause : valuesClauses) {
			insertElement.addElement(new TextElement(clause));
		}
		parentElement.addElement(insertElement);
	}
}
