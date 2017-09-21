/**
 * String 的补充方法
 */
String.prototype.trim = String.prototype.trim || function() {
	return this.replace(/(^\s*)|(\s*$)/g, '');
}

String.prototype.trimLeft = function() {
	return this.replace(/^\s+/, '');
}

String.prototype.trimRight = function() {
	return this.replace(/\s+$/, '');
}
String.prototype.replaceAll = function(s1, s2) {
	return this.replace(new RegExp(s1, "gm"), s2);
}
String.prototype.startsWith = function(prefix) {
	return this.substr(0, prefix.length) === prefix;
}

String.prototype.endsWith = function(suffix) {
	return this.substr(this.length - suffix.length) === suffix;
}

/**
 * 是否只有数字或字母
 * @returns
 */
String.prototype.isLetterOrNum = function() {
	return /^[A-Za-z0-9]*$/.test(this);
}

/**
 * 是否为手机号码
 * @return {}
 */
String.prototype.isMobil = function() {
	return /^1\d{10}$/.test(this);
}

/**
 * 是否为邮箱
 * @return {}
 */
String.prototype.isEmail = function() {
	return /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/.test(this);
}

/**
 * 是否为url
 * @return {}
 */
String.prototype.isURL = function() {
	return !!this
			.match(/(((^https?:(?:\/\/)?)(?:[-;:&=\+\$,\w]+@)?[A-Za-z0-9.-]+|(?:www.|[-;:&=\+\$,\w]+@)[A-Za-z0-9.-]+)((?:\/[\+~%\/.\w-_]*)?\??(?:[-\+=&;%@.\w_]*)#?(?:[\w]*))?)$/g);;
}

/**
 * 格式化金额
 * s:金额
 * n:保留位数
 */
function formatDecimal(s, n) {
	n = n > 0 && n <= 20 ? n : 2;  
	s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";  
	var l = s.split(".")[0].split("").reverse(),  
	r = s.split(".")[1];  
	t = "";  
	for(i = 0; i < l.length; i ++ ) {  
		t += l[i]; //+ ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");  
	}  
	return Number(t.split("").reverse().join("") + "." + r);
}


/**
 * 数据解码
 * @return {}
 */
String.prototype.decode = function() {
	return decodeURIComponent(this);
}
/**
 * 数据解码
 * @return {}
 */
String.prototype.encode = function() {
	return encodeURIComponent(this);
}

/**
 * 格式化
 */
String.prototype.format = function() {
	if (arguments.length == 0)
		return null;
	var str = this;
	for (var i = 0; i < arguments.length; i++) {
		var re = new RegExp('\\{' + i + '\\}', 'gm');
		str = str.replace(re, arguments[i]);
	}
	return str;
}

/**
 * 格式化成时间,当前Number 数据需是毫秒,否则计算结果有误
 * @param {} fmt
 * @return {}
 */
Number.prototype.toTime = function(fmt) {
	var _this = this;
	var _oldFmt = fmt;
	fmt = fmt.replace(/[^d|h|m|s]|(^-)/g, "-").replace(/(-){2,}/g, "-").replace(/(^-)|(-$)/g, '');
	var newArys = fmt.split('-');
	if (newArys.length == 0)
		throw 'invalid expression:[' + fmt + ']!';
	var result = "", to_ss = 1000, to_mm = to_ss * 60, to_hh = to_mm * 60, to_dd = to_hh * 24;

	var o = {};
	for (var i = 0; i < newArys.length; i++) {
		var key = newArys[i];
		if (key == 'dd' || key == 'd')
			o["d+"] = parseInt(_this / to_dd, 10);
		else if (key == 'hh' || key == 'h') {
			var hh = parseInt(_this / to_hh, 10);
			o["h+"] = i == 0 ? hh : hh % 24;
		} else if (key == 'mm' || key == 'm') {
			var mm = parseInt(_this / to_mm, 10);
			o["m+"] = i == 0 ? mm : mm % 60;
		} else if (key == 'ss' || key == 's') {
			var ss = parseInt(_this / to_ss, 10);
			o["s+"] = i == 0 ? ss : ss % 60;
		}
	}
	for (var k in o)
		if (new RegExp("(" + k + ")").test(fmt))
			_oldFmt = _oldFmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k])
							.substr(("" + o[k]).length)));
	return _oldFmt;
}

/**
 * 实例化日期
 * @param {} dateTxt
 */
Date.parse = function(dateTxt) {
	if (typeof dateTxt == "undefined" || dateTxt == null)
		return "";
	if (dateTxt instanceof Date)
		return dateTxt;
	/**
	 * 三个正则的意思: 1>替换所有非数字或非中划线的字符为中划线 2>替换连续2个以上的中划线为1个中划线 3>替换收尾的中划线为空字符
	 */
	dateTxt = dateTxt.replace(/\D|(^-)/g, "-").replace(/(-){2,}/g, "-").replace(/(^-)|(-$)/g, '');
	var newArys = dateTxt.split('-');
	if (newArys.length == 0)
		return;
	// i=1 表示日期(dd)部分从1开始,其他的月，小时，分，秒，毫秒都是从0开始
	for (var i = newArys.length; i < 7; i++)
		newArys.push(i == 1 ? 1 : 0);
	// 实例化一个新的 Date
	return new Date(newArys[0], --newArys[1], newArys[2], newArys[3], newArys[4], newArys[5],
			newArys[6]
	);
}

/**
 * 截取 Date
 * @param {} date
 * @return {}
 */
Date.prototype.trunc = function(fmt) {
	if (!fmt || fmt == "")
		fmt = "yyyy-MM-dd";
	return Date.parse(this.format(fmt));
}

/**
 * 格式化
 */
Date.prototype.format = Date.prototype.format || function(fmt) {
	var DAY_MAP = ["日", "一", "二", "三", "四", "五", "六"];
	var o = {
		"M+" : this.getMonth() + 1, // 月份
		"d+" : this.getDate(), // 日
		"h+" : this.getHours(), // 小时
		"m+" : this.getMinutes(), // 分
		"s+" : this.getSeconds(), // 秒
		"q+" : Math.floor((this.getMonth() + 3) / 3), // 季度
		"S" : this.getMilliseconds(),// 毫秒
		"E" : DAY_MAP[this.getDay()]
	};
	if (/(y+)/.test(fmt))
		fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	for (var k in o)
		if (new RegExp("(" + k + ")").test(fmt))
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k])
							.substr(("" + o[k]).length)));
	return fmt;
}

/**
 * 小时运算
 * @param hours 小时数 负数为减,正数为加
 */
Date.prototype.addHours = Date.prototype.addHours || function(hours) {
	var dt = new Date();
	dt.setTime(this.getTime() + (hours * 60 * 60 * 1000));
	return dt;
}

/**
 * 日期运算
 * @param days 天数 负数为减,正数为加
 */
Date.prototype.addDays = Date.prototype.addDays || function(days) {
	return this.addHours(days * 24);
}

/**
 * 月运算
 */
Date.prototype.addMonth = function(month) {
	var dt = new Date();
	dt.setTime(this.getTime());
	var month = dt.getMonth() + month;
	if (month > 11) {
		month = 0;
		dt.setFullYear(dt.getFullYear() + 1);
	}
	dt.setMonth(month);
	return dt;
}

/**
 * 计算两个时间相差小时数
 * @param {} beginDate
 * @param {} endDate
 * @return {}
 */
Date.prototype.differHours = function(dt) {
	if (!dt)
		return;
	var offsetTime = this.getTime() - dt.getTime();
	return parseInt(Math.abs((offsetTime / 1000) / 3600), 10);
}

/**
 * 计算两个时间相差天数
 * @param {} beginDate
 * @param {} endDate
 * @param {} isTruncDate 是否仅截取日期
 * @return {}
 */
Date.prototype.differDays = function(dt, isTruncDate) {
	if (!dt)
		return;
	if (!isTruncDate)
		isTruncDate = false;
	if (!(dt instanceof Date))
		return;
	return parseInt(this.trunc().differHours(dt.trunc(), 10) / 24);
}

/**
 * 获取当前日期月份第一天
 */
Date.prototype.getMonthFirstDate = function() {
	var beginDate = new Date(this);
	beginDate.setDate(1);
	return beginDate;
}

/**
 * 获取当前日期月份最后一天
 */
Date.prototype.getMonthLastDate = function() {
	var monthFirstDate = this.getMonthFirstDate();
	return monthFirstDate.addMonth(1).addDays(-1);
}

/**
 * 数组去重
 * @return {}
 */
Array.prototype.unique = function() {
	var res = [];
	var json = {};
	for (var i = 0; i < this.length; i++) {
		if (!json[this[i]]) {
			res.push(this[i]);
			json[this[i]] = 1;
		}
	}
	return res;
}
Array.prototype.uniqueByPropName = function(propName) {
	var res = [];
	var json = {};
	for (var i = 0; i < this.length; i++) {
		if (!json[this[i][propName]]) {
			res.push(this[i]);
			json[this[i][propName]] = 1;
		}
	}
	return res;
}

/**
 * 转换成属性数组
 * @param {} propName
 * @return {}
 */
Array.prototype.toPropArray = function(propName) {
	var res = [];
	for (var i = 0; i < this.length; i++)
		res.push(this[i][propName]);
	return res;
}

;
(function($) {
	/**
	* 构建URL
	* @param {}  url
	* @return {}
	*/
	$.url = function(url) {
		var curWwwPath = window.document.location.href;
		var pathName = window.document.location.pathname.replace(/\/+/, "/");
		var pos = curWwwPath.indexOf(pathName);
		var localhostPaht = curWwwPath.substring(0, pos);
		var pathList = pathName.split("/");
		var basePath = "/" + pathName.startsWith("/") ? pathList[1] : pathList[0];
		return (localhostPaht + "/" + basePath) + url;
	}
	
	/**
	 * js获取项目根路径
	 */
	$.getRootPath = function() {
		var curWwwPath = window.document.location.href;
		var pathName = window.document.location.pathname.replace(/\/+/, "/");
		var pos = curWwwPath.indexOf(pathName);
		var localhostPaht = curWwwPath.substring(0, pos);
		var pathList = pathName.split("/");
		var basePath = "/" + pathName.startsWith("/") ? pathList[1] : pathList[0];
		return (localhostPaht + "/" + basePath);
	}
	
	$.getQueryString = function(name) { 
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)","i");
		var r = window.location.search.substr(1).match(reg);
		if (r!=null)
			return unescape(r[2]); 
		return null;
	} 

	/**
	 * 获取请求参数
	 * @param name
	 * @returns
	 */
	$.urlParam = function(name) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
		var r = window.location.search.substr(1).match(reg);
		if (r != null)
			return unescape(r[2]);
		return null;
	}

	/**
	 * 打印日志
	 * @param {} msg
	 */
	$.print = function(msg) {
		console.log(msg);
	}

	/**
	 * 获取下个页面的url
	 */
	$.toNext = function() {
		var nextURL = jQuery.urlParam("nextURL");
		if (nextURL == null)
			nextURL = jQuery.url("/");
		window.location.href = nextURL.decode();
	}

	/**
	 * 判断是否是微信浏览器
	 * @return {}
	 */
	$.isWechat = function() {
		var ua = navigator.userAgent.toLowerCase();
		return ua.match(/MicroMessenger/i) == "micromessenger";
	}

	/**
	 * 判断是否是丁丁浏览器
	 * @return {}
	 */
	$.isDingTalk = function() {
		var ua = navigator.userAgent.toLowerCase();
		return ua.indexOf("dingtalk") >= 0;
	}
	
	/**
	 * 获取域名
	 */
	$.getCodeRootPath = function() {
		var curWwwPath = window.document.location.href;
		var pathName = window.document.location.pathname;
		var pos = curWwwPath.indexOf(pathName);
		var localhostPaht = curWwwPath.substring(0, pos);
		return localhostPaht;
	}

	if (window.layer) {
		layer.config({
			extend : ["extend/layer.ext.js"/*, "skin/moon/style.min.css"*/],
			/*skin : "layer-ext-moon"*/
		});
	}

	$.ajaxSetup({
		"type" : "POST",
		"statusCode" : {
			0 : function(request, error) {
				if (error == "abort")
					return;
				window.top.layer.open({
					content : "连接服务器失败,请检查您的网络",
					btn : ['知道了'],
					shadeClose : true
				});
			},
			501 : function(request) {
				var redirectUrl = request.getResponseHeader("redirectUrl");
				if ($.trim(redirectUrl).length > 0) {
					window.top.layer.open({
						type : 2,
						title : false,
						closeBtn : false,
						shadeClose : true,
						shade : 0.8,
						area : ['800px', '500px'],
						scrollbar : false,
						content : redirectUrl
					});
				}
			},
			502 : function(request) {
				var message = request.getResponseHeader("message");
				if ($.trim(message).length > 0) {
					message = message.replaceAll("\\+", "%20");
					message = decodeURIComponent(message);
					window.top.layer.open({
						content : message,
						btn : ['知道了'],
						closeBtn : false,
						shadeClose : true
					});
				}
			}
		}
	});

	$(document).on("click", ".J_add_tab", function() {
		if (!window.top.contabs)
			return true;

		var href = $(this).attr("href");
		if ($.trim(href) == "")
			return true;

		var label = $(this).data("label");
		if (!label || $.trim(label) == "")
			label = $.trim($(this).text());

		var itemId = window.top.hex_sha1(href);
		var distinct = $(this).data("distinct");
		if (distinct == false)
			itemId += new Date().getTime();
		var contabs = window.top.contabs;
		contabs.addTabItem(itemId, label, href, true);
		contabs.activeTabItem(itemId);
		return false;
	});
})(jQuery);

function showConfirm(msg, submitFun) {
	window.top.layer.open({
		content : msg,
		btn : ['确认', '取消'],
		shadeClose : false,
		yes : function(index) {
			window.top.layer.close(index);
			submitFun();
		}
	});
}

/**
 * 带loading效果的Ajax请求
 * @param {} url
 * @param {} data
 * @param {} callback
 */
function ajaxLoading(url, data, callback) {
	var loadIndex;
	return $.ajax({
		url : url,
		data : data,
		beforeSend : function() {
			loadIndex = layer.load(1, {
				shade : [ 0.5, '#fff' ]
			});
		},
		success : callback,
		complete : function() {
			layer.close(loadIndex);
		}
	});
}


function removeArrayEmpty(arrs){
	if(!arrs instanceof Array || arrs.length == 0)
		return new Array();
	var filterArray = [];
	for(var i = 0; i < arrs.length; i++){
		var obj = arrs[i];
		if(obj['value'] != '')
			filterArray.push(obj);
	}
	return filterArray;
}

/**
 * 过滤对象里的空属性/null属性
 * @param obj
 * @returns {}
 */
function dealElement(obj){
    var param = {};
    if ( obj == null || obj == undefined || obj == "" ) return param;
    for ( var key in obj ){
        if ( obj[key] != null && obj[key] != undefined && obj[key] !== "" )
            param[key] = obj[key];
    }
    return param;
}
