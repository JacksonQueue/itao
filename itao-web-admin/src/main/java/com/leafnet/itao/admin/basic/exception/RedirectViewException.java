package com.leafnet.itao.admin.basic.exception;

/**
 * 需要重定向前端页面显示视图的异常
 * @author Wangd
 * @version 1.0.0
 */
public class RedirectViewException extends RuntimeException {

	private static final long serialVersionUID = -4323845594512317L;

	private String url;

	public RedirectViewException(String message, String url) {
		super(message);
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
