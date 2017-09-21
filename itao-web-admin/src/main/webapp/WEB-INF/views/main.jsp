<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/inc/hplus/tld.inc"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/WEB-INF/inc/hplus/meta.inc"%>
<title>叶子网络</title>
<%@include file="/WEB-INF/inc/hplus/common_link.inc"%>
<script type="text/javascript" src="${resPath}/hplus/js/metisMenu/jquery.metisMenu.js"></script>
<script type="text/javascript" src="${resPath}/hplus/js/slimscroll/jquery.slimscroll.min.js"></script>
<script type="text/javascript" src="${resPath}/hplus/js/hplus.min.js"></script>
<script type="text/javascript" src="${resPath}/hplus/js/contabs.js"></script>
<script type="text/javascript" src="${resPath}/hplus/js/pace/pace.min.js"></script>
<script type="text/javascript" src="${resPath}/hplus/js/sha1.min.js"></script>
<script type="text/javascript">
	//防止免登后嵌套页面.
	if (self != top && top.layer) {
		var index = top.layer.getFrameIndex(window.name);
		top.layer.close(index);
	}
</script>
</head>
<body class="fixed-sidebar full-height-layout gray-bg"
	style="overflow: hidden">
	<div id="wrapper">
		<!--左侧导航开始-->
		<nav class="navbar-default navbar-static-side" role="navigation">
			<div class="nav-close">
				<i class="fa fa-times-circle"></i>
			</div>
			<div class="sidebar-collapse">
				<ul class="nav" id="side-menu">
					<li class="nav-header">
						<div class="dropdown profile-element" style="text-align: center;">
							<div>
								<img alt="image" class="img-circle" style="height: 64px"
									src="${resPath }/hplus/img/sys_logo.jpg" />
							</div>
							<a data-toggle="dropdown" class="dropdown-toggle" href="#">
								<div class="clear">
									<div class="block m-t-xs">
										<strong class="font-bold">LeafNetwork<b class="caret"></b></strong>
									</div>
								</div>
							</a>
							<ul class="dropdown-menu animated fadeInRight m-t-xs">
								<li><a class="J_menuItem" href="sys/userInfo.htm">个人资料</a></li>
								<li><a class="J_menuItem" href="sys/changePage.htm">修改密码</a></li>
								<li class="divider"></li>
								<li><a href="<c:url value="/auth/logout.htm" />">安全退出</a></li>
							</ul>
						</div>
					</li>
					<!-- <cjt:userMenuTag /> -->
				</ul>
			</div>
		</nav>
		<!--左侧导航结束-->
		<!--右侧部分开始-->
		<div id="page-wrapper" class="gray-bg dashbard-1">
			<div class="row content-tabs">
				<button class="roll-nav roll-left J_tabLeft navbar-minimalize"
					style="background-color: #1ab394; border-color: #1ab394; color: white;">
					<i class="fa fa-bars"></i>
				</button>
				<button class="roll-nav roll-left J_tabLeft" style="left: 40px;">
					<i class="fa fa-backward"></i>
				</button>
				<nav class="page-tabs J_menuTabs" style="margin-left: 80px;">
					<div class="page-tabs-content"></div>
				</nav>
				<button class="roll-nav roll-right J_tabRight">
					<i class="fa fa-forward"></i>
				</button>
				<div class="btn-group roll-nav roll-right">
					<button class="dropdown J_tabClose" data-toggle="dropdown">
						关闭操作<span class="caret"></span>
					</button>
					<ul role="menu" class="dropdown-menu dropdown-menu-right">
						<li class="J_tabShowActive"><a>定位当前选项卡</a></li>
						<li class="divider"></li>
						<li class="J_tabCloseAll"><a>关闭全部选项卡</a></li>
						<li class="J_tabCloseOther"><a>关闭其他选项卡</a></li>
					</ul>
				</div>
				<a href="<c:url value="/auth/logout.htm" />"
					class="roll-nav roll-right J_tabExit"><i class="fa fa-sign-out"></i>
					退出</a>
			</div>
			<div class="row J_mainContent" id="content-main"></div>
		</div>
		<!--右侧部分结束-->
	</div>
</body>
</html>