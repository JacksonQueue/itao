(function($) {
	$.fn.contabs = function() {
		var $this = $(this);
		var $mainContent = $this.find(".J_mainContent");
		var $mainTabs = $this.find(".page-tabs-content");
		var $tabsBar = $this.find(".content-tabs");

		/**
		 * 获取所有选项卡
		 */
		function getTabItems() {
			return $mainTabs.find(".J_menuTab");
		}

		/**
		 * 获取tab选项卡
		 */
		function getTabItem(itemId) {
			return $mainTabs.find('#J_tab_' + itemId);
		}

		/**
		 * 获取Iframe
		 */
		function getIframeItem(itemId) {
			return $mainContent.find('#J_iframe_' + itemId);
		}

		/**
		 * 判断是否存在tab选项卡
		 */
		function hasTabItem(itemId) {
			return getTabItem(itemId).length > 0;
		}

		/**
		* 计算一组元素的outerWidth
		* @param t
		* @returns {Number}
		*/
		function calWidth(itemList) {
			var width = 0;
			$(itemList).each(function() {
				width += $(this).outerWidth(true);
			});
			return width;
		}

		/**
		 * 标签栏左移动
		 */
		function tabsbarMoveLeft(offsetWidth) {
			var opt = {};
			opt["marginLeft"] = offsetWidth + "px";
			$mainTabs.animate(opt, "fast");
		}

		/**
		* 定位选项卡
		* @param menuTab
		*/
		function locationTabItem(menuTab) {
			var prevElWidth = calWidth($(menuTab).prevAll());
			var nextElWidth = calWidth($(menuTab).nextAll());

			var btnGroupWidth = calTabsWidth();
			var tabContentWidth = $tabsBar.outerWidth(true) - btnGroupWidth;
			var offset = 0;

			var toolbarOuterWidth = $mainTabs.outerWidth();

			if (toolbarOuterWidth < tabContentWidth) {
				// 未超出标签栏
				offset = 0;
			} else if (nextElWidth <= tabContentWidth - $(menuTab).outerWidth(true)
					- $(menuTab).next().outerWidth(true)) {
				// 计算超出边界值
				var nextElWidth = $(menuTab).next().outerWidth(true);
				if (tabContentWidth - nextElWidth > nextElWidth) {
					offset = prevElWidth;
					for (var menu = menuTab; offset - $(menu).outerWidth() > toolbarOuterWidth
							- tabContentWidth;) {
						offset -= $(menu).prev().outerWidth();
						menu = $(menu).prev();
					}
				}
			} else {
				prevElWidth > tabContentWidth - $(menuTab).outerWidth(true)
						- $(menuTab).prev().outerWidth(true);
				offset = prevElWidth - $(menuTab).prev().outerWidth(true);
			}
			tabsbarMoveLeft(0 - offset);
		}

		function calTabsWidth() {
			return calWidth($tabsBar.children().not(".J_menuTabs"));
		}

		function openLoading() {
			return layer.load();
		}

		function closeLoading(id) {
			layer.close(id);
		}

		var handler = {
			/**
			 * 添加选项卡
			 */
			addTabItem : function(itemId, text, href, closeBtn) {
				if (hasTabItem(itemId))
					return;

				$mainContent.find("iframe.J_iframe").addClass("hidden");

				var tabTitle = text;
				if (closeBtn != false)
					tabTitle += '<i class="fa fa-times-circle J_tab_closebtn"></i>';

				var menuTabHtml = '<a href="javascript:;" class="J_menuTab" id="J_tab_{0}" itemid="{1}">{2}</a>';
				var menuIframeHtml = '<iframe class="J_iframe hidden" id="J_iframe_{0}" width="100%" height="100%" src="{1}" frameborder="0" seamless></iframe>';
				var itemTab = $(menuTabHtml.format(itemId, itemId, tabTitle));
				var itemIframe = $(menuIframeHtml.format(itemId, href));
				// 显示加载中
				var loaddingId = openLoading();
				itemIframe.one("load", function() {
					closeLoading(loaddingId);
				});

				// 追加新的内容标签和容器
				$mainTabs.append(itemTab);
				$mainContent.append(itemIframe);
			},
			/**
			 * 刷新选项卡
			 */
			reloadTabItem : function(itemId) {
				var iframeItem = getIframeItem(itemId);
				if (iframeItem.length < 1)
					return;
				iframeItem.attr("src", iframeItem.attr("src"));
			},
			/**
			 * 选中选项卡
			 */
			activeTabItem : function(itemId) {
				if (!hasTabItem(itemId))
					return;

				var tabItem = getTabItem(itemId);
				var iframeItem = getIframeItem(itemId);

				// 添加标签栏激活样式
				if (!tabItem.hasClass("active")) {
					tabItem.addClass("active");
					tabItem.siblings(".J_menuTab").removeClass("active");
				}

				// 添加标签栏激活样式
				if ($(iframeItem).hasClass("hidden")) {
					$(iframeItem).removeClass("hidden");
					$(iframeItem).siblings(".J_iframe").addClass("hidden");
				}

				locationTabItem(tabItem);
			},
			/**
			 * 移除选项卡
			 */
			delTabItem : function(itemId) {
				if (!hasTabItem(itemId))
					return;

				var tabItem = getTabItem(itemId);
				var iframeItem = getIframeItem(itemId);
				if (tabItem.hasClass("active")) {
					var nextMenuTab = tabItem.next(".J_menuTab:eq(0)");
					var prevMenuTab = tabItem.prev(".J_menuTab:eq(0)");

					// 计算当前选中标签页
					var selMenuTab = null;
					if (nextMenuTab.length > 0)
						selMenuTab = nextMenuTab;
					else if (prevMenuTab.length > 0)
						selMenuTab = prevMenuTab;

					if (selMenuTab != null)
						this.activeTabItem(selMenuTab.attr("itemid"));
				}
				tabItem.remove();
				iframeItem.remove();
			}
		};

		// 选项卡切换
		$(document).on("click", ".J_menuTab", function() {
			handler.activeTabItem($(this).attr("itemid"));
		});

		// 选项卡刷新
		$(document).on("dblclick", ".J_menuTab", function() {
			var itemId = $(this).attr("itemid");
			if (getTabItem(itemId).hasClass("active"))
				handler.reloadTabItem(itemId);
		});

		// 关闭按钮
		$(document).on("click", ".J_menuTab .J_tab_closebtn", function(event) {
			var tabItem = $(this).closest(".J_menuTab");
			handler.delTabItem(tabItem.attr("itemid"));
			event.stopPropagation();
		});

		// 定位当前选中
		$(document).on("click", ".J_tabShowActive", function() {
			locationTabItem($mainTabs.find(".active"));
		});

		// 选项卡工具条左右移动事件
		$(document).on("click", ".J_tabLeft", function() {
			var tabsMarginLeft = Math.abs(parseInt($mainTabs.css("margin-left")));
			var outWidth = $tabsBar.outerWidth(true) - calTabsWidth();

			var n = 0;
			if ($mainTabs.width() < outWidth)
				return false;

			var r = 0;
			for (var tempMenuTab = $(".J_menuTab:first"); r + $(tempMenuTab).outerWidth(true) <= tabsMarginLeft;) {
				r += $(tempMenuTab).outerWidth(true);
				tempMenuTab = $(tempMenuTab).next();
			}

			if (r = 0, calWidth($(tempMenuTab).prevAll()) > outWidth) {
				for (; r + $(tempMenuTab).outerWidth(true) < outWidth && tempMenuTab.length > 0;)
					r += $(tempMenuTab).outerWidth(true), tempMenuTab = $(tempMenuTab).prev();
				n = calWidth($(tempMenuTab).prevAll())
			}
			tabsbarMoveLeft(0 - n);
		}
		);

		$(document).on("click", ".J_tabRight", function() {
			var tabsMarginLeft = Math.abs(parseInt($mainTabs.css("margin-left")));
			var outWidth = $tabsBar.outerWidth(true) - calTabsWidth();
			var n = 0;
			if ($mainTabs.width() < outWidth)
				return false;

			var r = 0;
			for (var tempMenuTab = $(".J_menuTab:first"); r + $(tempMenuTab).outerWidth(true) <= tabsMarginLeft;) {
				r += $(tempMenuTab).outerWidth(true);
				tempMenuTab = $(tempMenuTab).next();
			}
			for (r = 0; r + $(tempMenuTab).outerWidth(true) < outWidth && tempMenuTab.length > 0;) {
				r += $(tempMenuTab).outerWidth(true);
				tempMenuTab = $(tempMenuTab).next();
			}
			n = calWidth($(tempMenuTab).prevAll());
			if (n > 0)
				tabsbarMoveLeft(0 - n);
		}
		);

		// 关闭其他事件
		$(document).on("click", ".J_tabCloseOther", function() {
			getTabItems().not(".active").filter(function() {
				return $(this).find(".J_tab_closebtn").length > 0;
			}).each(function(index) {
				handler.delTabItem($(this).attr("itemid"));
			});
			$mainTabs.css("margin-left", "0");
		});

		// 关闭所有权限卡
		$(document).on("click", ".J_tabCloseAll", function() {
			getTabItems().filter(function() {
				return $(this).find(".J_tab_closebtn").length > 0;
			}).each(function(index) {
				handler.delTabItem($(this).attr("itemid"));
			});
			$mainTabs.css("margin-left", "0");
		});

		return handler;
	}
})(jQuery);

$(function() {
	window.contabs = $("#page-wrapper").contabs();

	// 默认添加首页
	var indexItemId = "index_page";
	contabs.addTabItem(indexItemId, "首页", "index.htm", false);
	contabs.activeTabItem(indexItemId);

	$(document).on("click", ".J_menuItem", function() {
		var href = $(this).attr("href");
		var label = $.trim($(this).text());
		if ($.trim(href) == "")
			return false;

		var itemId = hex_sha1(href);
		contabs.addTabItem(itemId, label, href, true);
		contabs.activeTabItem(itemId);
		return false;
	});
});