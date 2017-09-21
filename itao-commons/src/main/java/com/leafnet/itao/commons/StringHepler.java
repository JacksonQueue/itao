package com.leafnet.itao.commons;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 字符串辅助工具
 * @author xiongmw
 * @version 1.0.0
 */
public class StringHepler extends StringUtils implements Constants {

	/**
	 * 版本号比较
	 * @param ver1
	 * @param ver2
	 * @return	
	 * 	true: ver1 大于等于 ver2<br/>
	 * 	false: ver1 小于 ver2<br/>
	 */
	public static boolean compareVersion(String ver1, String ver2) {
		List<String> v1List = split2List(ver1, ".");
		List<String> v2List = split2List(ver2, ".");

		int len = Math.max(v1List.size(), v2List.size());

		for (int i = 0; i < len; i++) {
			String v1 = v1List.size() > i ? v1List.get(i) : "0";
			String v2 = v2List.size() > i ? v2List.get(i) : "0";
			if (isNumeric(v1) && isNumeric(v2)) {
				// 转换成数字比较
				long v1l = Long.parseLong(v1);
				long v2l = Long.parseLong(v2);
				if (v1l == v2l)
					continue;
				else
					return v1l > v2l;
			} else {
				// 使用字符串比较
				if (v1.equalsIgnoreCase(v2))
					continue;
				else
					return v1.compareTo(v2) >= 0;
			}
		}
		return true;
	}

	public static List<String> split2List(String str, String separatorChar) {
		String[] arrays = StringUtils.split(str, separatorChar);
		if (arrays != null && arrays.length > 0)
			return new ArrayList<>(Arrays.asList(arrays));
		return new ArrayList<>();
	}

	public static Set<String> split2Set(String str, String separatorChar) {
		String[] arrays = StringUtils.split(str, separatorChar);
		if (arrays != null && arrays.length > 0)
			return new HashSet<>(Arrays.asList(arrays));
		return new HashSet<>();
	}

	public static String encode(String url) {
		return encode(url, CHARSET_NAME);
	}

	public static String encode(String url, String enc) {
		try {
			return URLEncoder.encode(url, enc);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	public static String decode(String url) {
		return decode(url, CHARSET_NAME);
	}

	public static String decode(String url, String enc) {
		try {
			return URLDecoder.decode(url, enc);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 格式化替换占位符.
	 * @param source
	 * @param values
	 * @return
	 */
	public static String format(String source, Map<String, String> values) {
		Properties properties = new Properties();
		properties.putAll(values);
		return new PlaceProperties().replaceProperty(source, properties);
	}

	/**
	 * 文字溢出截断追加点点点
	 * @return
	 */
	public static String ellipsis(String source, int byteLen) {
		String minusStr = "...";
		int minusLen = minusStr.getBytes(CHARSET).length;
		byte[] sourceArrays = source.getBytes(CHARSET);

		if (sourceArrays.length <= byteLen)
			return source;

		byte[] newArrays = Arrays.copyOf(sourceArrays, byteLen - minusLen);
		String newStr = new String(newArrays, CHARSET);

		char[] charArray = newStr.toCharArray();
		charArray = Arrays.copyOf(charArray, charArray.length - 1);
		return String.valueOf(charArray) + minusStr;
	}

	/**
	 * 生成随机数
	 * @param count
	 * @return
	 */
	public static String randomNumber(int count) {
		return RandomStringUtils.random(count, false, true);
	}

	/**
	 * 生成主键
	 */
	public static String randomNumberForKey(int count) {
		StringBuffer sb = new StringBuffer();
		String str = "0123456789";
		Random r = new Random();
		for (int i = 0; i < count; i++) {
			int num = r.nextInt(str.length());
			sb.append(str.charAt(num));
			str = str.replace((str.charAt(num) + ""), "");
		}
		return sb.toString();
	}

	/**
	 * 将驼峰式命名的字符串转换为下划线小写方式。如果转换前的驼峰式命名的字符串为空，则返回空字符串。</br>
	 * 例如：HelloWorld->hello_world
	 * @param name 转换前的驼峰式命名的字符串
	 * @return 转换后下划线大写方式命名的字符串
	 */
	public static String underscoreName(String name) {
		StringBuilder result = new StringBuilder();
		if (name != null && name.length() > 0) {
			result.append(name.substring(0, 1).toLowerCase());
			for (int i = 1; i < name.length(); i++) {
				String s = name.substring(i, i + 1);
				if (s.equals(s.toUpperCase()) && !Character.isDigit(s.charAt(0))) {
					result.append("_");
				}
				result.append(s.toLowerCase());
			}
		}
		return result.toString();
	}

	/**
	 * 将下划线大写方式命名的字符串转换为驼峰式。如果转换前的下划线大写方式命名的字符串为空，则返回空字符串。</br>
	 * 例如：HELLO_WORLD->helloWorld
	 * @param name 转换前的下划线大写方式命名的字符串
	 * @return 转换后的驼峰式命名的字符串
	 */
	public static String camelName(String name) {
		StringBuilder result = new StringBuilder();
		if (StringUtils.isBlank(name))
			return "";
		if (!name.contains("_"))
			return name.substring(0, 1).toLowerCase() + name.substring(1);

		String camels[] = name.split("_");
		for (String camel : camels) {
			if (camel.isEmpty())
				continue;
			if (result.length() == 0) {
				result.append(camel.toLowerCase());
			} else {
				result.append(camel.substring(0, 1).toUpperCase());
				result.append(camel.substring(1).toLowerCase());
			}
		}
		return result.toString();
	}

	public static boolean matcher(String regex, String str) {
		if (StringUtils.isBlank(str) || StringUtils.isBlank(regex))
			return false;
		
		return Pattern.compile(regex).matcher(str).find();	
	}
    
	/**
	 * 判断是否包含字母
	 * @param str
	 * @return
	 */
	public static boolean containLetter(String str) {
		if (StringUtils.isBlank(str))
			return false;
		
		final String regex = "[a-zA-Z]";
		return matcher(regex, str);
	}
}
