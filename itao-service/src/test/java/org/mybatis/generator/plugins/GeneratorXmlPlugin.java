package org.mybatis.generator.plugins;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.plugins.xml.BaseQueryColumListXmlElementPluginAdapter;
import org.mybatis.generator.plugins.xml.BaseQuerySqlXmlElementPluginAdapter;
import org.mybatis.generator.plugins.xml.BaseQueryWhereXmlElementPluginAdapter;
import org.mybatis.generator.plugins.xml.BaseResultMapXmlElementPluginAdapter;
import org.mybatis.generator.plugins.xml.DeleteByParamXmlElementPluginAdapter;
import org.mybatis.generator.plugins.xml.InsertXmlElementPluginAdapter;
import org.mybatis.generator.plugins.xml.QueryCountByParamXmlElementPluginAdapter;
import org.mybatis.generator.plugins.xml.QueryListByParamXmlElementPluginAdapter;
import org.mybatis.generator.plugins.xml.QueryUniqueByIdXmlElementPluginAdapter;
import org.mybatis.generator.plugins.xml.UpdateXmlElementPluginAdapter;
import org.mybatis.generator.plugins.xml.XmlElementPluginAdapter;

/**
 * 按模板生成XML
 * @author Wangd
 */
public class GeneratorXmlPlugin extends PluginAdapter {

	static List<XmlElementPluginAdapter> adapterList;

	static {
		adapterList = new ArrayList<>();
		adapterList.add(new BaseResultMapXmlElementPluginAdapter());
		adapterList.add(new BaseQueryColumListXmlElementPluginAdapter());
		adapterList.add(new BaseQueryWhereXmlElementPluginAdapter());
		adapterList.add(new BaseQuerySqlXmlElementPluginAdapter());

		adapterList.add(new QueryUniqueByIdXmlElementPluginAdapter());
		adapterList.add(new QueryListByParamXmlElementPluginAdapter());
		adapterList.add(new QueryCountByParamXmlElementPluginAdapter());

		adapterList.add(new InsertXmlElementPluginAdapter());
		adapterList.add(new UpdateXmlElementPluginAdapter());
		adapterList.add(new DeleteByParamXmlElementPluginAdapter());
	}

	@Override
	public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
		XmlElement rootEl = document.getRootElement();
		rootEl.getElements().clear();
		for (XmlElementPluginAdapter adapter : adapterList)
			adapter.sqlMapElementGenerated(context, rootEl, introspectedTable);
		return super.sqlMapDocumentGenerated(document, introspectedTable);
	}

	@Override
	public boolean validate(List<String> warnings) {
		return true;
	}
}
