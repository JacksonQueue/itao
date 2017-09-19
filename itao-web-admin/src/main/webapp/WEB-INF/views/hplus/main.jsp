<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>叶子网络</title>
</head>
<body class="fixed-sidebar full-height-layout gray-bg" style="overflow: hidden">
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
								<img alt="image" class="img-circle" style="height: 64px" src="${resPath }/img/sys_logo.jpg" />
							</div>
							<a data-toggle="dropdown" class="dropdown-toggle" href="#">
								<div class="clear">
									<div class="block m-t-xs">
										<strong class="font-bold">LeafNetwork<b class="caret"></b></strong>
									</div>
								</div>
							</a>
							<ul class="dropdown-menu animated fadeInRight m-t-xs">
								<li><a class="J_menuItem" href="javascript:;">个人资料</a></li>
								<li><a class="J_menuItem" href="javascript:;">修改密码</a></li>
								<li class="divider"></li>
								<li><a href="javascript:;">安全退出</a></li>
							</ul>
						</div>
					</li>
					<cjt:userMenuTag />
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
				<a href="javascript:;" class="roll-nav roll-right J_tabExit"><i
					class="fa fa-sign-out"></i> 退出</a>
			</div>
			<div class="row J_mainContent" id="content-main"></div>
		</div>
		<!--右侧部分结束-->
	</div>
</html>