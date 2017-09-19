package org.mybatis.generator.plugins.xml;

import java.util.Iterator;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import org.mybatis.generator.config.Context;

/**
 * 生成 BaseQueryColumList 节点
 * @author Wangd
 */
public class BaseQueryColumListXmlElementPluginAdapter extends BasicXmlElementPluginAdapter {

	@Override
	public void sqlMapElementGenerated(Context context, XmlElement parentElement, IntrospectedTable introspectedTable) {
		super.sqlMapElementGenerated(context, parentElement, introspectedTable);
		addCommentTag(parentElement, "表字段,提供给其他单表查询的SQL引用");
		XmlElement baseQueryColumListElement = createElement("sql", ID_BASE_QUERY_COLUM_LIST);

		StringBuilder sb = new StringBuilder();
		Iterator<IntrospectedColumn> iter = introspectedTable.getAllColumns().iterator();
		while (iter.hasNext()) {
			sb.append(MyBatis3FormattingUtilities.getSelectListPhrase(iter.next()));
			if (iter.hasNext())
				sb.append(", ");

			if (sb.length() > 80) {
				addTextElement(baseQueryColumListElement, sb.toString());
				sb.setLength(0);
			}
		}
		if (sb.length() > 0)
			addTextElement(baseQueryColumListElement, sb.toString());
		parentElement.addElement(baseQueryColumListElement);
	}
}
