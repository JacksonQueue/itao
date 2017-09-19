package org.mybatis.generator.plugins.xml;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.config.Context;

public interface XmlElementPluginAdapter {

	public void sqlMapElementGenerated(Context context, XmlElement rootEl, IntrospectedTable introspectedTable);

}
