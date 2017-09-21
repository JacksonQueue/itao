function checkParam() {
	if ($.trim($('#username').val()) == '') {
		layer.msg('请输入用户名！');
		return false;
	} else if ($.trim($('#password').val()) == '') {
		layer.msg('请输入密码！');
		return false;
	} else if ($.trim($('#captcha').val()) == '') {
		layer.msg('请输入验证码！');
		return false;
	}
	return true;
}

function clearCallback() {
	$("#password").val("");
	$("#captcha").val("");
	genSecCodeImg();
}

function submitFun() {
	if (!checkParam())
		return;

	var param = {
		"username" : $("#username").val(),
		"password" : $("#password").val(),
		"secCode" : $("#captcha").val(),
	};
	var url = $.url("/auth/checkUser.do");
	ajaxLoading(url, param, function(data) {
		if (data.status == false) {
			layer.msg(data.message);
			clearCallback();
			return;
		}
		var returnURL = $.getQueryString("nextURL");
		if ($.trim(returnURL) == "")
			returnURL = $.url("/");
		window.location.href = returnURL;
	});
}

function keydownSubmit(e) {
	if ($(".bac").length == 0) {
		if (!e)
			e = window.event;
		if ((e.keyCode || e.which) == 13) {
			submitFun();
		}
	}
}

$(function() {
	// 提交表单
	$('#loginsubmit').click(submitFun);
	$(".key").keydown(keydownSubmit);
});