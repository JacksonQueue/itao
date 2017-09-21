package com.leafnet.itao.commons;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Title:基础常量
 * @author: Wangd
 */
public interface Constants {

	public static final String DOT = ".";
	public static final String EQ = "=";
	public static final String AND = "&";
	
	/**
	 * 默认字符编码
	 */
	public static final Charset CHARSET = StandardCharsets.UTF_8;
	public static final String CHARSET_NAME = CHARSET.name();

	/**
	 * 全局配置文件的存储当前环境的Key
	 */
	final static String ENV = "env";

	/**
	 * 返回地址
	 */
	final static String REQ_NEXT_URL = "nextURL";
	
	/**
	 * 当前用户
	 */
    final static String CURRENT_USER = "itao:cur-user";
}
