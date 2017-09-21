package com.leafnet.itao.admin.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.leafnet.itao.admin.basic.AbsBasicController;

/**
 * 根节点控制器
 * @author Wangd
 * @version 1.0.0
 */
@Controller
@RequestMapping
public class RootController extends AbsBasicController {

	/**
	 * 主框架
	 * @param menuId
	 * @return
	 */
	@RequestMapping("/main")
	public String main(String menuId) {
		return "main";
	}
	
	/**
	 * 默认首页
	 * @param menuId
	 * @return
	 */
	@RequestMapping("/index")
	public String index(String menuId) {
		return "index/index_default";
	}

}
