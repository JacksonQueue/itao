package org.mybatis.generator;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.SqlMapGeneratorConfiguration;
import org.mybatis.generator.config.TableConfiguration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

/**
 * 自动生成指定模板的MyBatis代码
 */
public class GeneratorCodeMain {

	/**
	 * 清空历史Xml文件
	 * @param contexts
	 * @throws Exception
	 */
	public static void clearXmlFiles(List<Context> contexts) throws Exception {
		DefaultShellCallback shellCallback = new DefaultShellCallback(true);
		for (Context context : contexts) {
			SqlMapGeneratorConfiguration sqlMapGeneratorConf = context.getSqlMapGeneratorConfiguration();
			for (TableConfiguration tableConfiguration : context.getTableConfigurations()) {
				String domainObjectName = tableConfiguration.getDomainObjectName();
				String fileName = String.format("%sMapper.xml", domainObjectName);

				String targetProject = sqlMapGeneratorConf.getTargetProject();
				String targetPackage = sqlMapGeneratorConf.getTargetPackage();
				File directory = shellCallback.getDirectory(targetProject, targetPackage);
				File targetFile = new File(directory, fileName);
				if (targetFile.exists())
					targetFile.delete();
				System.out.println("Existing file " + targetFile);
			}
		}
	}

	public static void main(String[] args) throws Exception {
		/**
		 * 初始化环境变量
		 */
		boolean overwrite = true;
		String encoding = "UTF-8";
		String xmlConfigName = "generatorConfig.xml";

		List<String> warnings = new ArrayList<String>();
		URL classPath = GeneratorCodeMain.class.getResource("/");
		String projectPath = StringUtils.replace(new File("").getAbsolutePath(), "\\", "/") + "/";
		URL confPath = GeneratorCodeMain.class.getResource(xmlConfigName);

		Map<String, String> replaceMap = new HashMap<String, String>();
		replaceMap.put("\"classpath:", "\"" + classPath.getFile());
		replaceMap.put("\"projectpath:", "\"" + projectPath);
		replaceMap.put("\"mavenprojectpath:", "\"" + new File(projectPath).getParent());

		/**
		* 读取配置文件内容,批量替换占位符
		*/
		String fileInfo = IOUtils.toString(confPath.toURI(), encoding);
		for (String replaceText : replaceMap.keySet())
			fileInfo = StringUtils.replace(fileInfo, replaceText, replaceMap.get(replaceText));
		InputStream confInputStream = IOUtils.toInputStream(fileInfo, encoding);

		try {
			/**
			* 生成数据
			*/
			ConfigurationParser cp = new ConfigurationParser(warnings);
			Configuration config = cp.parseConfiguration(confInputStream);

			clearXmlFiles(config.getContexts());

			DefaultShellCallback callback = new DefaultShellCallback(overwrite);
			MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
			myBatisGenerator.generate(null);

			for (String warning : warnings) {
				System.out.println(warning);
			}
		} catch (Exception e) {
			IOUtils.closeQuietly(confInputStream);
			e.printStackTrace();
		}

	}
}
