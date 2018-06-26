<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
<title>东城电子</title>
<%@ include file="../../common/head.jsp"%>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/web/main.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/js/plug/artDialog/css/ui-dialog.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/js/thrid/laypage/skin/laypage.css"
	type="text/css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/js/plug/grid/css/ui.jqgrid.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/js/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/font-awesome.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/index.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/skins/_all-skins.css">
<%-- <script src="${pageContext.request.contextPath}/js/thrid/jQuery-1.10.2.js"></script> --%>
<script
	src="${pageContext.request.contextPath}/js/jquery/jQuery-2.2.0.min.js"></script>
<script
	src="${pageContext.request.contextPath}/js/bootstrap/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/js/index.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap/js/respond.js"></script>
<style type="text/css">
.list .btn_another input {
	width: 88px !important;
	height: 24px !important;
	border-radius: 3px;
	margin-bottom: 10px;
	background: #74a3dc;
	border: none;
	cursor: pointer;
	font-family: "微软雅黑";
	color: #fff;
	font-size: 14px;
}

.list .btn_another input:hover {
	background-color: #189ad8;
}

.confirm {
	width: 100% !important;
	float: left;
}

.confirm input {
	margin-left: 40px;
}

.list li {
	height: 35px;
}

.bottom-input {
	width: 70%;
	float: right;
	margin-top: 10px;
}

.bottom-input li {
	width: 320px;
	float: right;
	text-align: right;
}

.bottom-input li input {
	width: 200px;
	height: 24px;
	border: 1px solid #ccc;
	border-radius: 3px;
}

.fm-button {
	padding: .0em 1.4em .2em 1.3em;
}
</style>
</head>
<body class="hold-transition skin-blue sidebar-mini"
	style="overflow: hidden;">
	<div id="ajax-loader"
		style="cursor: progress; position: fixed; top: -50%; left: -50%; width: 200%; height: 200%; background: #fff; z-index: 10000; overflow: hidden;">
		<img src="${pageContext.request.contextPath}/img/ajax-loader.gif"
			style="position: absolute; top: 0; left: 0; right: 0; bottom: 0; margin: auto;" />
	</div>
	<div class="wrapper">
		<!--头部信息-->
		<header class="main-header">
			<a href="http://www.hzdongcheng.com" target="_blank" class="logo">
				<span class="logo-mini">LR</span> <span class="logo-lg"><strong><spring:message
							code="compname" /></strong></span>
			</a>
			<nav class="navbar navbar-static-top">
				<a class="sidebar-toggle"> <span class="sr-only">Toggle
						navigation</span>
				</a>
				<div class="navbar-custom-menu">
					<ul class="nav navbar-nav">
						<li class="dropdown messages-menu"><a href="#"
							class="dropdown-toggle" data-toggle="dropdown"> <i
								class="fa fa-envelope-o "></i> <span class="label label-success">0</span>
						</a></li>
						<li class="dropdown notifications-menu"><a href="#"
							class="dropdown-toggle" data-toggle="dropdown"> <i
								class="fa fa-bell-o"></i> <span class="label label-warning">${article}</span>
						</a></li>
						<li class="dropdown tasks-menu"><a href="#"
							class="dropdown-toggle" data-toggle="dropdown"> <i
								class="fa fa-flag-o"></i> <span class="label label-danger">0</span>
						</a></li>
						<li class="dropdown user user-menu"><a href="#"
							class="dropdown-toggle" data-toggle="dropdown"> <img
								src="${pageContext.request.contextPath}/img/user2-160x160.jpg"
								class="user-image" alt="User Image"> <span
								class="hidden-xs">${account.accountname}</span>
						</a>
							<ul class="dropdown-menu pull-right">
								<!-- <li class="divider"></li> -->
								<!-- <li id="switch" onclick="switchLanguage()"><a><i
										class="fa fa-bullhorn"></i>切换语言</a></li>  -->
								<li id="pass" onclick="updateaccountpass()"><a><i
										class="fa fa-unlock "></i>修改密码</a></li>
								<li><a href="${pageContext.request.contextPath}/"><i
										class="ace-icon fa fa-power-off"></i>安全退出</a></li>
							</ul></li>
					</ul>
				</div>
			</nav>
			<div style="display: none;" class="content">
				<form id="addpass"
					action="${pageContext.request.contextPath}/passwordController/saveOrUpdateAccount.do">
					<div class="list" align="center">
						<ul>
							<li>新密码：<input type="password" id="newpassword"
								name="newpassword" value="" /></li>
							<li>确认密码：<input type="password" id="confirmpassword"
								name="confirmpassword" value="" /></li>
						</ul>
					</div>
				</form>
			</div>
			<div style="display: none;">
				<form id="switchLanguage"
					action="${pageContext.request.contextPath}/passwordController/saveOrUpdateAccount.do">
					<div class="list" align="center">
						<ul>
							<li>语言：<select name="switch_1" id="switch_1">
									<option value="" selected="selected">请选择</option>
									<option value="1">Chinese</option>
									<option value="2">English</option>
							</select></li>
							<li><p align="center"
									style="margin-left: 65px; font-family: fantasy; color: red;">成功后需要从新登录</p></li>
						</ul>
					</div>
				</form>
			</div>
		</header>

		<!--左边导航-->
		<div class="main-sidebar">
			<div class="sidebar">
				<div class="user-panel">
					<div class="pull-left image">
						<img
							src="${pageContext.request.contextPath}/img/user2-160x160.jpg"
							class="img-circle" alt="User Image">
					</div>
					<div class="pull-left info">
						<p>${account.accountname}</p>
						<a><i class="fa fa-circle text-success"></i>在线</a>
					</div>
				</div>
				<!-- <form action="#" method="get" class="sidebar-form">
                    <div class="input-group">
                        <input type="text" name="q" class="form-control" placeholder="Search...">
                        <span class="input-group-btn">
                            <a class="btn btn-flat"><i class="fa fa-search"></i></a>
                        </span>
                    </div>
                </form> -->
				<ul class="sidebar-menu" id="sidebar-menu">
					<!--<li class="header">导航菜单</li>-->
				</ul>
			</div>
		</div>
		<!--中间内容-->
		<div id="content-wrapper" class="content-wrapper">
			<div class="content-tabs">
				<button class="roll-nav roll-left tabLeft">
					<i class="fa fa-backward"></i>
				</button>
				<nav class="page-tabs menuTabs">
					<div class="page-tabs-content" style="margin-left: 0px;">
						<a href="${pageContext.request.contextPath}/myadmin.do"
							class="menuTab active" data-id="/Home/Default">欢迎首页</a>
						<!-- <a href="javascript:;" class="menuTab" data-id="/Home/About" style="padding-right: 15px;">平台介绍</a> -->
						<!-- <a href="javascript:;" class="menuTab" data-id="/SystemManage/Organize/Index">机构管理 <i class="fa fa-remove"></i></a>
                        <a href="javascript:;" class="menuTab" data-id="/SystemManage/Role/Index">角色管理 <i class="fa fa-remove"></i></a>
                        <a href="javascript:;" class="menuTab" data-id="/SystemManage/Duty/Index">岗位管理 <i class="fa fa-remove"></i></a>
                        <a href="javascript:;" class="menuTab" data-id="/SystemManage/User/Index">用户管理 <i class="fa fa-remove"></i></a> -->
					</div>
				</nav>
				<button class="roll-nav roll-right tabRight">
					<i class="fa fa-forward" style="margin-left: 3px;"></i>
				</button>
				<div class="btn-group roll-nav roll-right">
					<button class="dropdown tabClose" data-toggle="dropdown">
						页签操作<i class="fa fa-caret-down" style="padding-left: 3px;"></i>
					</button>
					<ul class="dropdown-menu dropdown-menu-right">
						<li><a class="tabReload" href="javascript:void(0);">刷新当前</a></li>
						<li><a class="tabCloseCurrent" href="javascript:void(0);">关闭当前</a></li>
						<li><a class="tabCloseAll" href="javascript:void(0);">全部关闭</a></li>
						<li><a class="tabCloseOther" href="javascript:void(0);">除此之外全部关闭</a></li>
					</ul>
				</div>
				<button class="roll-nav roll-right fullscreen">
					<i class="fa fa-arrows-alt"></i>
				</button>
			</div>
			<div class="content-iframe" style="overflow: hidden;">
				<div class="mainContent" id="content-main"
					style="margin: 10px; margin-bottom: 0px; padding: 0;">
					<iframe class="LRADMS_iframe" width="100%" height="100%"
						src="${pageContext.request.contextPath}/default"
						frameborder="0" data-id="default.html"></iframe>
				</div>
			</div>
		</div>

	</div>
	<script type="text/javascript">
		/* $("#pass").click(function(){
		 document.getElementById("updatepass").style.display="block";
		}); */
		function switchLanguage() {
			dialogCode = dialog({
				title : "切换语言",
				content : $('#switchLanguage'),
				okValue : "确认",
				ok : function() {
					var switch_1 = document.getElementById("switch_1").value;
					/* alert(switch_1); */
					if (switch_1 == "0") {
						showMsg("请重新选择");
						return false;
					} else {
						if (switch_1 == "1") {
							/* alert(switch_1); */
							$
									.ajax({
										type : "POST",
										url : "${pageContext.request.contextPath}/switchController/language.do?switch_1="
												+ switch_1,
										success : function(data) {
											top.location = '${pageContext.request.contextPath}';
										},
									});

						} else if (switch_1 == "2") {
							/* alert(switch_1); */
							$
									.ajax({
										type : "POST",
										url : "${pageContext.request.contextPath}/switchController/language.do?switch_1="
												+ switch_1,
										success : function(data) {
											top.location = '${pageContext.request.contextPath}';
										},
									});
						}
					}
				},
				cancelValue : '取消',
				cancel : function() {
					document.getElementById("switch").value = "0";
				}
			});
			dialogCode.width(350);
			dialogCode.showModal();
		}
		function updateaccountpass() {
			dialogCode = dialog({
				title : "修改密码",
				content : $('#addpass'),
				okValue : "确认",
				ok : function() {
					if ($("#newpassword").val() == $("#confirmpassword").val()) {
						$
								.ajax({
									type : "POST",
									url : "${pageContext.request.contextPath}/passwordController/saveOrUpdateAccount.do",
									data : {
										confirmpassword : $("#confirmpassword")
												.val()
									},
									success : function(data) {
										top.location = '${pageContext.request.contextPath}/';
									},
								});
					} else {
						showMsg("你输入的密码前后不一致");
						document.getElementById("newpassword").value = "";
						document.getElementById("confirmpassword").value = "";
					}
				},
				cancelValue : '取消',
				cancel : function() {
					/* $('#addpass').clearForm(true); */
					document.getElementById("newpassword").value = "";
					document.getElementById("confirmpassword").value = "";
				}
			});

			dialogCode.width(350);
			dialogCode.showModal();
		}
		function showMsg(themsg) { //显示提示信息，1s后自动关闭
			var d = dialog({
				content : themsg
			});
			d.show();
			setTimeout(function() {
				d.close().remove();
			}, 2000);
		};
	</script>
</body>
</html>