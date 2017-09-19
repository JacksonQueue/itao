package org.mybatis.generator.plugins.java;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.codegen.AbstractJavaGenerator;


public abstract class AbsJavaGenerator extends AbstractJavaGenerator {

	public static final String TARGET_PROJECT_KEY = "targetProject";
	public static final String TARGET_PACKAGE_KEY = "targetPackage";

	protected Properties properties;

	public AbsJavaGenerator() {
		properties = new Properties();
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	public Properties getProperties() {
		return properties;
	}

	public String getProperty(String name) {
		return properties.getProperty(name);
	}

	protected String getTargetProject() {
		return getProperties().getProperty(TARGET_PROJECT_KEY);
	}

	protected String getTargetPackage() {
		return getProperties().getProperty(TARGET_PACKAGE_KEY);
	}

	protected abstract FullyQualifiedJavaType getJavaType();

	protected String getOutPath(String fileName) {
		String targetPackage = getJavaType().getPackageName();
		String targetProject = getTargetProject();

		targetProject += targetProject.endsWith("/") ? "" : "/";
		String outPath = targetProject + targetPackage.replace(".", "/");
		return outPath + "/" + fileName + ".java";
	}

	protected void writeFile(String content, String fileName) {
		FileOutputStream fos = null;
		try {
			File outPath = new File(getOutPath(fileName));
			if (outPath.exists()) {
				outPath.delete();
				System.out.println("Existing file " + outPath);
			} else {
				File parentFile = outPath.getParentFile();
				if (!parentFile.exists()) {
					parentFile.mkdirs();
				}
			}
			fos = new FileOutputStream(outPath);
			String encoding = getContext().getProperty("javaFileEncoding");
			IOUtils.write(content, fos, StringUtils.defaultIfEmpty(encoding, "UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(fos);
		}
	}
}
