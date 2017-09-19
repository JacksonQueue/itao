package org.mybatis.generator.plugins.xml;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.AbstractXmlElementGenerator;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.GeneratedKey;

public abstract class BasicXmlElementPluginAdapter implements XmlElementPluginAdapter {

	static final String ID_BASE_PAGINATION_PREFIX = "CommonMapper.Base_Pagination_Prefix";

	static final String ID_BASE_PAGINATION_SUFFIX = "CommonMapper.Base_Pagination_Suffix";

	static final String ID_BASE_RESULT_MAP = "BaseResultMap";

	static final String ID_BASE_QUERY_COLUM_LIST = "BaseQueryColumList";

	static final String ID_BASE_QUERY_WHERE = "BaseQueryWhere";

	static final String ID_BASE_QUERY_SQL = "BaseQuerySql";

	static final String ID_QUERY_UNIQUE_BY_ID = "queryUniqueById";

	protected void addNewlineTag(XmlElement parentElement) {
		parentElement.addElement(new TextElement(""));
	}

	protected void addCommentTag(XmlElement parentElement, String text) {
		parentElement.addElement(new TextElement("<!-- " + text + " -->"));
	}

	protected void addTextElement(XmlElement parentElement, String text) {
		parentElement.addElement(new TextElement(text));
	}

	protected XmlElement createElement(String type, String id) {
		XmlElement xmlElement = new XmlElement(type);
		xmlElement.addAttribute(new Attribute("id", id));
		return xmlElement;
	}

	protected XmlElement createElement(String type, String attr, String attrVal) {
		XmlElement xmlElement = new XmlElement(type);
		xmlElement.addAttribute(new Attribute(attr, attrVal));
		return xmlElement;
	}

	protected String getPrimaryKeyParameterType(IntrospectedTable introspectedTable) {
		String parameterType;
		if (introspectedTable.getRules().generatePrimaryKeyClass()) {
			parameterType = introspectedTable.getPrimaryKeyType();
		} else {
			if (introspectedTable.getPrimaryKeyColumns().size() > 1) {
				parameterType = "map";
			} else {
				IntrospectedColumn introspectedColumn = introspectedTable.getPrimaryKeyColumns().get(0);
				parameterType = introspectedColumn.getFullyQualifiedJavaType().toString();
			}
		}
		return parameterType;
	}

	protected XmlElement getSelectKey(IntrospectedColumn introspectedColumn, GeneratedKey generatedKey) {
		String identityColumnType = introspectedColumn.getFullyQualifiedJavaType().getFullyQualifiedName();
		XmlElement answer = new XmlElement("selectKey");
		answer.addAttribute(new Attribute("resultType", identityColumnType));
		answer.addAttribute(new Attribute("keyProperty", introspectedColumn.getJavaProperty()));
		answer.addAttribute(new Attribute("order", generatedKey.getMyBatis3Order()));
		answer.addElement(new TextElement(generatedKey.getRuntimeSqlStatement()));
		return answer;
	}

	protected void initializeAndExecuteGenerator(Context context, AbstractXmlElementGenerator elementGenerator, XmlElement parentElement,
			IntrospectedTable introspectedTable) {
		elementGenerator.setContext(context);
		elementGenerator.setIntrospectedTable(introspectedTable);
		elementGenerator.addElements(parentElement);
	}

	@Override
	public void sqlMapElementGenerated(Context context, XmlElement rootEl, IntrospectedTable introspectedTable) {
		addNewlineTag(rootEl);
	}
}
