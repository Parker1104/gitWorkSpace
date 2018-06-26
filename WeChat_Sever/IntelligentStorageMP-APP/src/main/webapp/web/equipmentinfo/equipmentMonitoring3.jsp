<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>设备监控</title>
<%@ include file="../../common/head.jsp"%>
<%@ include file="../../common/theme.jsp"%>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/js/plug/zTree/css/demo.css"
	type="text/css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/js/plug/zTree/css/zTreeStyle/zTreeStyle.css"
	type="text/css" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/plug/zTree/js/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/plug/zTree/js/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/module/commodity/category.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/plug/zTree/js/jquery.ztree.exhide-3.5.js"></script>

<style type="text/css">
#zTreeDemoBackground_1 {
	position: absolute;
	margin-top: 30px;
	width: 100%;
	height: 95%;
}

#zTreeDemoBackground_2 {
	position: absolute;
	margin-top: 5px;
	height: 25px;
	width: 50%;
	left: 25%;
}

#zTreeDemoBackground_3 {
	position: absolute;
	margin-top: 30px;
	left: 36%;
	width: 50%;
	height: 95%;
}

#zTreeDemoBackground_4 {
	position: absolute;
	margin-top: 30px;
	width: 630px;
	height: 90.3%;
	left: 29%;
	background-color: #FFFFFF;
	border: 1px solid #617775;
}

#zTreeDemoBackground_5 {
	position: absolute;
	width: 627px;
	height: 20%;
	background-color: #FFFFFF;
	border: 0px solid #617775;
}

#zTreeDemoBackground_6 {
	position: absolute;
	width: 627px;
	height: 75%;
	background-color: #FFFFFF;
	top: 21%;
	border: 0px solid #617775;
}

#zTreeDemoBackground_7 {
	position: absolute;
	width: 628px;
	height: 12%;
	background-color: #FFFFFF;
	border: 0px solid #617775;
}

#zTreeDemoBackground_8 {
	position: absolute;
	width: 627px;
	height: 85%;
	background-color: #FFFFFF;
	/* left: 25%; */
	top: 10.5%;
	border: 0px solid #617775;
}

#zTreeDemoBackground_10 {
	position: absolute;
	width: 450px;
	background-color: #FFFFFF;
	border: 0px solid #617775;
}

#zTreeDemoBackground_9 {
	position: absolute;
	width: 627px;
	height: 12%;
	top: 91%;
	background-color: #FFFFFF;
	border: 0px solid #617775;
}

div#rMenu {
	position: absolute;
	visibility: hidden;
	top: 0;
	background-color: #231444;
	text-align: left;
	padding: 1px;
}

div#rMenu ul li {
	margin: 1px 0;
	padding: 0 10px;
	cursor: pointer;
	text-align: center;
	font-weight: 500;
	font-size: 13px;
	list-style: none outside none;
	background-color: #F0F0F0;
}

div#rMenus {
	position: absolute;
	visibility: hidden;
	top: 0;
	background-color: #231444;
	text-align: left;
	padding: 1px;
}

div#rMenus  ul li {
	margin: 1px 0;
	padding: 0 10px;
	cursor: pointer;
	text-align: center;
	font-weight: 500;
	font-size: 13px;
	list-style: none outside none;
	background-color: #F0F0F0;
}

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

.graph {
	width: 450px;
	border: 1px solid #2B423B;
	height: 25px;
	border-radius: 3px;
}

#bar {
	display: block;
	background: #0E6DB0;
	float: left;
	height: 100%;
	text-align: center;
	border-radius: 5px
}

#bar_1 {
	display: block;
	background: #0E6DB0;
	float: left;
	height: 100%;
	text-align: center;
	border-radius: 5px
}

#barNum {
	position: absolute;
}
</style>
</head>
<body>
	<div class="content_wrap">
				<div id="zTreeDemoBackground_1">
					<ul id="treeDemo" class="ztree"
						style="margin-left: 50px; margin-top: 0px; width: 25%; height: 95%;">
					</ul>
				</div>
				<div id="zTreeDemoBackground_4">
							<!-- 主要写查询  -->
							<div id="zTreeDemoBackground_5">
								<table width="628" style="background: #9DDBFB" border="1">
									<tr height="36">
										<td colspan="3" align="center">
										    <label id="xiangMen" style="font-size: 20px; color: blue;"></label>
										</td>
									</tr>
									<tr height="28">
										<td width="20"></td>
										<td width="200">
										      <label id="ArkNumber"></label>
										</td>
										<td width="398">
										      <label id="ScopeOfArk"></label>
										</td>
				
									</tr>
									<tr height="28">
										<td></td>
										<td>
										     <label id="NumberOfCases"></label>
										</td>
										<td>
										     <label id="BoxRange"></label>
										</td>
									</tr>
									<tr height="28">
										<td></td>
										<td>
										       <label id="usableNumber"></label>
										</td>
										<td>
										       <label id="occupyNumber"></label>
										</td>
									</tr>
								</table>
							</div>
							<!-- 主要显示柜子信息以及操作  -->
 
				</div>
				<div id="rMenu">
					<ul>
						<li id="opendirtyBox" onclick="opendirtyBoxId();">一键打开已用箱门</li>
						<li id="AkeyOpensOverdueBox" onclick="AkeyOpensOverdueBox();">一键打开过期箱门</li>
						<li id="AkeyOpensFreeBox" onclick="AkeyOpensFreeBox();">一键打开空闲箱门</li>
						<li id="AkeyOpensAllBox" onclick="AkeyOpensAllBox();">一键打开全部箱门</li>
						<!-- <li id="AkeyOpensTimeoutBox" onclick="AkeyOpensTimeoutBox();">一键打开超时箱门</li>
						<li id="AkeyOpensFaultBox" onclick="AkeyOpensFaultBox();">一键打开故障箱门</li> 
						<li id="OpensBox" onclick="OpensBox();">测试开箱</li>   -->
					</ul>
				</div>
	</div>
 
	<script type="text/javascript">
		var setting = {
			view : {
				selectedMulti : false,
				showIcon : true
			},
			edit : {
				enable : true
			},
			data : {
				keep : {
					parent : true,
					leaf : false
				},
				simpleData : {
					enable : true
				}
			},
			callback : {
				onClick : zTreeOnClick,
				onRightClick : OnRightClick
			}
		};
		/*  */
		function OnRightClick(event, treeId, treeNode) {
			if (!treeNode && event.target.tagName.toLowerCase() != "button" && $(event.target).parents("a").length == 0) {
				zTree.cancelSelectedNode();
				showRMenu("root", event.clientX, event.clientY);
			} else if (treeNode && !treeNode.noR) {
				zTree.selectNode(treeNode);
				showRMenu("node", event.clientX, event.clientY);
			}
		}

		function showRMenu(type, x, y) {
			$("#rMenu ul").show();
			rMenu.css({
				"top" : y + "px",
				"left" : x + "px",
				"visibility" : "visible"
			});
			$("body").bind("mousedown", onBodyMouseDown);
		}
		function hideRMenu() {
			if (rMenu)
				rMenu.css({
					"visibility" : "hidden"
				});
			$("body").unbind("mousedown", onBodyMouseDown);
		}
		function onBodyMouseDown(event) {
			if (!(event.target.id == "rMenu" || $(event.target).parents( "#rMenu").length > 0)) {
				rMenu.css({
					"visibility" : "hidden"
				});
			}
		}
		
		var zTree, rMenu;
		$(document).ready(
			function() {
				$.ajax({
					type : "post",
					url : "${pageContext.request.contextPath}/equipmentMonitoring/findAll.do",
					//data : {"ids": ids},
					dataType : 'json',//JSON.stringify(
					success : function(zNodes) {
						$.fn.zTree.init($("#treeDemo"),setting, zNodes);
						zTree = $.fn.zTree.getZTreeObj("treeDemo");
						rMenu = $("#rMenu");
					},
					error : function(XMLHttpRequest,
							textStatus, errorThrown) {
					}
				});

          }
	   );
 
 
	  function zTreeOnClick(event, treeId, treeNode) {
			document.getElementById("NumberOfCases").innerText = "";
			document.getElementById("BoxRange").innerText = "";
			document.getElementById("ArkNumber").innerText = "";
			document.getElementById("ScopeOfArk").innerText = "";
			document.getElementById("usableNumber").innerText = "";
			document.getElementById("occupyNumber").innerText = "";
			/* 名称 */
			document.getElementById("xiangMen").innerText = treeNode.name + " 箱门使用情况";
			$.ajax({
				type : "post",
				url : "${pageContext.request.contextPath}/equipmentMonitoring/selsctTerminalEntityOrBox.do",
				data : {
					areacode : treeNode.id
				},
				dataType : 'json',
				success : function(msgBoxTotalCount) {
					if (msgBoxTotalCount != null) {
						/* 箱子数量   */
						document.getElementById("NumberOfCases").innerText = "  箱门总数： " + msgBoxTotalCount.length;
						/* 箱子范围    */
						document.getElementById("BoxRange").innerText = "起始箱号： " + msgBoxTotalCount[0].dispalyname
								        + " - 截止箱号： " + msgBoxTotalCount[msgBoxTotalCount.length - 1].dispalyname;
					}
				},
			});
		};


		function showMsg(themsg) { //显示提示信息，1s后自动关闭
			var d = dialog({
				content : themsg
			});
			d.show();
			setTimeout(function() {
				d.close().remove();
			}, 1000);
		};
	</script>

</body>
</html>