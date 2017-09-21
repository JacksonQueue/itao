<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/inc/hplus/tld.inc"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/WEB-INF/inc/hplus/meta.inc"%>
<title>叶子网络</title>
<%@include file="/WEB-INF/inc/hplus/common_link.inc"%>
<link rel="stylesheet" href="${resPath}/css/login/login.css">
<script type="text/javascript" src="${resPath}/js/login/login.js"></script>
<script type="text/javascript" charset="utf-8">
	function genSecCodeImg() {
		var IMG_SEC_CODE = "#secCodeImg";
		var oldSrc = $(IMG_SEC_CODE).attr("src");
		var newSrc = oldSrc.split("?")[0] + "?_r=" + Math.random();
		$(IMG_SEC_CODE).attr("src", newSrc);
	}
</script>
<style type="text/css">
.black {
	color: black;
}

.kapacha {
	height: 35px;
	margin-top: 15px;
}
</style>
</head>
<body class="signin">
	<div id="" class="signinpanel">
		<div class="row">
			<div class="col-sm-offset-6 col-sm-6">
				<form method="post">
					<h4 class="no-margins">用户登录：</h4>
					<p class="m-t-md">欢迎使用精品汇后台管理系统。</p>
					<input id="username" name="userName" type="text" placeholder="用户名"
						class="form-control uname key"> <input id="password"
						name="password" type="password" placeholder="密码"
						class="form-control pword key">
					<div class="input-group m-b">
						<input id="captcha" name="captcha" type="text" placeholder="验证码"
							class="black form-control seccode m-b key"> <span
							class="input-group-btn"><img id="secCodeImg"
							title="点击刷新验证码." onclick="genSecCodeImg()"
							src="./genSecCode_LONGIN_GEN_SEC_CODE.do" class="kapacha"></span>
					</div>
					<button type="button" id="loginsubmit"
						class="btn btn-success btn-block">登录</button>
				</form>
			</div>
		</div>
	</div>
</body>
</html>