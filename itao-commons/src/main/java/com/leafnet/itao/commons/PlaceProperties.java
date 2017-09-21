package com.leafnet.itao.commons;

import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.PropertyPlaceholderHelper;
import org.springframework.util.PropertyPlaceholderHelper.PlaceholderResolver;

/**
 * Properties扩展，支持嵌套引用
 * @author Wangd
 * @version 1.0
 */
public class PlaceProperties extends Properties {

	private static final long serialVersionUID = 1848767379431725263L;

	protected String placeholderPrefix = "${";

	protected String placeholderSuffix = "}";

	protected PropertyPlaceholderHelper createPlaceholderHelper() {
		return new PropertyPlaceholderHelper(placeholderPrefix, placeholderSuffix);
	}

	public PlaceProperties() {
	}

	public PlaceProperties(Properties defaults) {
		super(defaults);
	}

	public PlaceProperties(String placeholderPrefix, String placeholderSuffix) {
		this.placeholderPrefix = placeholderPrefix;
		this.placeholderSuffix = placeholderSuffix;
	}

	/**
	 * 替换嵌套数据,支持多层嵌套
	 * @param val
	 * @param properties
	 * @return
	 */
	protected String replaceProperty(String val, final Properties properties) {
		if (StringUtils.isNotBlank(val)) {
			PropertyPlaceholderHelper placeholderHelper = createPlaceholderHelper();
			return placeholderHelper.replacePlaceholders(val, new PlaceholderResolver() {
				@Override
				public String resolvePlaceholder(String placeholderName) {
					PropertyPlaceholderHelper placeholderHelper = createPlaceholderHelper();
					String relVal = placeholderHelper.replacePlaceholders(placeholderName, this);
					return properties.getProperty(relVal);
				}
			});
		}
		return StringUtils.EMPTY;
	}

	protected Properties replacePlaceholders(Properties properties) {
		// properties 转换前的数据
		// 不含嵌套表达式的数据,转换后的数据
		Properties newProperties = new Properties();
		// 包含嵌套表达式的数据,转换前 和 转换后的数据
		Properties tempProperties = new Properties(properties);
		if (properties != null && !properties.isEmpty()) {

			Set<String> keys = properties.stringPropertyNames();
			for (String key : keys) {
				String val = properties.getProperty(key);
				String newKey = replaceProperty(key, tempProperties);
				String newVal = replaceProperty(val, tempProperties);
				newProperties.put(newKey, newVal);
				tempProperties.putAll(newProperties);
			}
		}
		return newProperties;
	}

	public Properties replaceProperties() {
		Properties newProperties = new Properties();
		newProperties.putAll(this);
		clear();
		putAll(replacePlaceholders(newProperties));
		return this;
	}
}
