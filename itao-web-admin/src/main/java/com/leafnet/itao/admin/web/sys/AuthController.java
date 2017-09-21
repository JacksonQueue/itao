package com.leafnet.itao.admin.web.sys;

import java.awt.image.BufferedImage;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.code.kaptcha.Producer;
import com.leafnet.itao.admin.basic.AbsBasicController;
import com.leafnet.itao.admin.basic.SysConf;
import com.leafnet.itao.admin.basic.WebClient;
import com.leafnet.itao.admin.basic.WebHelper;
import com.leafnet.itao.commons.StringHepler;

/**
 * 认证管理
 * @author Wangd
 * @version 1.0.0
 */
@Controller
@RequestMapping("/auth")
public class AuthController extends AbsBasicController {
	
	@Autowired
	private Producer captchaProducer;
	
	/**
	 * 用户登录页面
	 */
	@RequestMapping("/login")
	public String login() {
		return "user/login";
	}
	
	/**
	 * 登录校验
	 */
	@RequestMapping("/loginCheck")
	public String loginCheck(String authId) {
		if (WebClient.hasUser()) {
			String redirectUrl = getNextUrlOrIndexUrl();
			return "redirect:" + redirectUrl;
		} else {
			String loginUrl = SysConf.loginUrl();
			loginUrl = WebHelper.createFullServerUrl(getRequest(), loginUrl);
			
			String redirectUrl = getNextUrlOrIndexUrl();
			StringBuilder nextURLParam = new StringBuilder();
			nextURLParam.append(REQ_NEXT_URL);
			nextURLParam.append(EQ);
			nextURLParam.append(StringHepler.encode(redirectUrl));
			loginUrl = WebHelper.urlAddParams(loginUrl, nextURLParam.toString());
			return "redirect:" + loginUrl;
		}
	}
	
	/**
	 * 系统验证码
	 * @return
	 */
	@RequestMapping("/genSecCode_{sessionKey}")
	public void genSecCode(@PathVariable String sessionKey) throws Exception {
		Assert.hasText(sessionKey, "无效的验证码类型!");
		HttpServletResponse response = getResponse();
		response.setDateHeader("Expires", 0);
		response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
		response.addHeader("Cache-Control", "post-check=0, pre-check=0");
		response.setHeader("Pragma", "no-cache");
		response.setContentType("image/jpeg");

		OutputStream out = null;
		try {
			String capText = captchaProducer.createText();
			WebClient.setSessionAttr(sessionKey, capText);
			out = response.getOutputStream();
			BufferedImage bi = captchaProducer.createImage(capText);
			ImageIO.write(bi, "jpg", out);
			out.flush();
			LOG.debug("genSecurityCode:[" + capText + "]");
		} catch (Exception e) {
			LOG.error("genSecCode error!", e);
		} finally {
			IOUtils.closeQuietly(out);
		}
	}

}
