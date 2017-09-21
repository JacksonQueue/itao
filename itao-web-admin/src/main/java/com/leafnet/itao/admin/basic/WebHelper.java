package com.leafnet.itao.admin.basic;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.leafnet.itao.commons.Constants;
import com.leafnet.itao.commons.StringHepler;

/**
 * Title:常用功能
 * @author: Wangd
 * @version: 1.0
 */
public class WebHelper extends org.springframework.web.util.WebUtils implements Constants {

	/**
	 * 获取 客户端 Locale
	 * @return
	 */
	public static Locale getClientLocale() {
		return LocaleContextHolder.getLocale();
	}

	/**
	 * 获取客户端IP
	 * @param request
	 * @return
	 */
	public static String getIpAddress(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if (StringUtils.hasText(ip)) {
			// 只获取第一个ip为客户端ip
			String clientIp = ip.split(",")[0];
			if (!"unknown".equalsIgnoreCase(clientIp))
				ip = clientIp;
		}
		return ip;
	}

	/**
	 * url 追加 QueryString
	 *  如例一: 
	 *      url          :http://aa.com/aa.do
	 *     queryString   : cccc=bb&cc=1
	 *     return       :http://aa.com/aa.do?cccc=bb&cc=1
	 *  如例二:   
	 *      url          :http://aa.com/aa.do?bb=1
	 *     queryString   : cccc=bb&cc=1
	 *     return       :http://aa.com/aa.do?bb=1&cccc=bb&cc=1
	 * @param url
	 * @param queryString
	 * @return
	 */
	public static String urlAddParams(String url, String queryString) {
		if (queryString == null)
			queryString = "";
		if (StringUtils.hasText(queryString))
			queryString = (url.split("\\?").length > 1 ? "&" : "?") + queryString;
		return url + queryString;
	}

	/**
	 * 根据名值生成类似request query格式的字符串
	 * @param request
	 * @return 类似 a=1&b=5&c=6
	 */
	public static String generateQueryString(Map<String, String[]> paramValues) {
		return generateQueryString(paramValues, CHARSET_NAME);
	}

	public static String generateQueryString(Map<String, String[]> paramValues, String encoding) {
		if (CollectionUtils.isEmpty(paramValues))
			return "";

		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		for (String name : paramValues.keySet()) {
			String[] value = paramValues.get(name);
			for (String val : value)
				parameters.add(new BasicNameValuePair(name, val));
		}
		return URLEncodedUtils.format(parameters, encoding);
	}

	/**
	 * {@link #generateNextUrl(String, Map, String)}
	 * @param httpRequest
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String generateNextUrl(HttpServletRequest httpRequest) {
		return generateNextUrl(httpRequest, CHARSET_NAME);
	}

	/**
	 * {@link #generateNextUrl(String, Map, String)}
	 * @param httpRequest
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String generateNextUrl(HttpServletRequest httpRequest, String encoding) {
		return generateNextUrl(getUrl(httpRequest), httpRequest.getParameterMap(), encoding);
	}

	/**
	 * {@link #generateNextUrl(String, Map, String)}
	 * @param url
	 * @return
	 */
	public static String generateNextUrl(String url) {
		return generateNextUrl(url, null);
	}

	/**
	 * {@link #generateNextUrl(String, Map, String)}
	 * @param url
	 * @param parameterMap
	 * @return
	 */
	public static String generateNextUrl(String url, Map<String, String[]> parameterMap) {
		return generateNextUrl(url, parameterMap, CHARSET_NAME);
	}

	/**
	 * 生成 nextURL
	 * @param url
	 * @param parameterMap
	 * @param encoding
	 * @return 如:nextURL=http%3A%2F%2F127.0.0.1%3A8080%2Fabc.do%3Fabc%3D1
	 * @throws UnsupportedEncodingException
	 */
	public static String generateNextUrl(String url, Map<String, String[]> parameterMap, String encoding) {
		if (!StringUtils.hasText(url)) {
			return "";
		}
		String queryString = generateQueryString(parameterMap);
		String nextURL = url.split("\\?")[0];
		if (StringUtils.hasText(queryString))
			nextURL += "?" + queryString;

		StringBuilder nextURLParam = new StringBuilder();
		nextURLParam.append(REQ_NEXT_URL);
		nextURLParam.append(EQ);
		nextURLParam.append(StringHepler.encode(nextURL));
		return nextURLParam.toString();
	}

	/**
	 * 获得url 不包括参数
	 * @param request
	 * @return
	 */
	public static String getUrl(HttpServletRequest request) {
		StringBuilder result = new StringBuilder();
		result.append(createServerString(request));
		result.append(request.getRequestURI());
		return result.toString();
	}

	/**
	 * 获得url 包括参数
	 * @param request
	 * @return
	 */
	public static String getAbsoluteUrl(HttpServletRequest request) {
		StringBuilder result = new StringBuilder();
		result.append(getUrl(request));
		String queryString = request.getQueryString();
		if (StringUtils.hasText(queryString))
			result.append("?").append(queryString);
		return result.toString();
	}

	/**
	 * 创建完整的系统URL路径
	 * 非系统相对路径
	 * @param requestUri
	 * @return
	 */
	public static String createFullServerUrl(HttpServletRequest request, String requestUri) {
		if (request == null)
			return null;
		StringBuilder nextURL = new StringBuilder();
		nextURL.append(WebHelper.createServerString(request));
		nextURL.append(request.getContextPath());
		nextURL.append(requestUri);
		return nextURL.toString();
	}

	/**
	 * {@link #createServerString(String, String, int)}
	 * @param request
	 * @return
	 */
	public static String createServerString(HttpServletRequest request) {
		String scheme = request.getScheme();
		String server = request.getServerName();
		int port = request.getServerPort();

		// 获取客户端协议 http or https?
		String realScheme = request.getHeader("X-Real-SCHEME");
		if (StringUtils.hasText(realScheme))
			scheme = realScheme;

		String forwardedProto = request.getHeader("x-forwarded-proto");
		if (StringUtils.hasText(forwardedProto))
			scheme = forwardedProto;

		return createServerString(scheme, server, port);
	}

	/**
	 * 创建服务器得完整路径
	 * @param scheme
	 * @param server
	 * @param port
	 * @return  如:http://www.baidu.com/
	 */
	public static String createServerString(String scheme, String server, int port) {
		StringBuilder url = new StringBuilder();
		if (port < 0) {
			port = 80;
		}
		url.append(scheme);
		url.append("://");
		url.append(server);

		// 如果是 http协议并且端口号不为80
		// 如果是 https 协议并且端口号不为 443并且端口号不为80(客户端和http反向代理通过https(443) 服务器内部转发为 80)
		if ((scheme.equals("http") && port != 80) //
				|| (scheme.equals("https") && port != 443 && port != 80)) {
			url.append(":");
			url.append(port);
		}
		return url.toString();
	}

	/**
	 * 创建服务器域名
	 * @param server
	 * @param port
	 * @return  如:www.baidu.com
	 */
	public static String createServerDomain(HttpServletRequest request) {
		String scheme = request.getScheme();
		String server = request.getServerName();
		int port = request.getServerPort();

		// 获取客户端协议 http or https?
		String realScheme = request.getHeader("X-Real-SCHEME");
		if (StringUtils.hasText(realScheme))
			scheme = realScheme;

		String forwardedProto = request.getHeader("x-forwarded-proto");
		if (StringUtils.hasText(forwardedProto))
			scheme = forwardedProto;

		StringBuilder url = new StringBuilder();
		if (port < 0) {
			port = 80;
		}
		url.append(server);

		// 如果是 http协议并且端口号不为80
		// 如果是 https 协议并且端口号不为 443并且端口号不为80(客户端和http反向代理通过https(443) 服务器内部转发为 80)
		if ((scheme.equals("http") && port != 80) //
				|| (scheme.equals("https") && port != 443 && port != 80)) {
			url.append(":");
			url.append(port);
		}
		return url.toString();
	}

	/**
	 * 获取钉钉版本号
	 * @param userAgent
	 * @return
	 */
	public static String getDingtalkVersion(String userAgent) {
		String regEl = "(?<=dingtalk/)(.*)(?=[\\)])";
		Pattern p = Pattern.compile(regEl, Pattern.CASE_INSENSITIVE);
		Matcher matcher = p.matcher(userAgent);
		if (matcher.find())
			return matcher.group();
		return null;
	}

	/**
	 * 获取微信版本号
	 * @param userAgent
	 * @return
	 */
	public static String getWechatVersion(String userAgent) {
		String regEl = "(?<=micromessenger/)(.*)(?= NetType)";
		Pattern p = Pattern.compile(regEl, Pattern.CASE_INSENSITIVE);
		Matcher matcher = p.matcher(userAgent);
		if (matcher.find())
			return matcher.group();
		return null;
	}
	
	/**
	 * 获取客户端访问协议
	 * @param request
	 * @return
	 */
	public static String getClientScheme(HttpServletRequest request) {
		// 获取真实的Http协议
		String scheme = request.getScheme();
		String realScheme = request.getHeader("X-Real-SCHEME");
		if (StringUtils.hasText(realScheme))
			scheme = realScheme;

		String forwardedProto = request.getHeader("x-forwarded-proto");
		if (StringUtils.hasText(forwardedProto))
			scheme = forwardedProto;
		return scheme;
	}

	/**
	 * 创建服务器得完整路径
	 * @param request
	 * @return
	 */
	public static String createServerString(HttpServletRequest request, String serverName) {
		int port = request.getServerPort();

		StringBuilder url = new StringBuilder();
		if (port < 0)
			port = 80;
		
		url.append(getClientScheme(request));
		url.append("://");
		url.append(serverName);

		if (port != 80 && port != 443) {
			url.append(":");
			url.append(port);
		}
		
		return url.toString();
	}

}
