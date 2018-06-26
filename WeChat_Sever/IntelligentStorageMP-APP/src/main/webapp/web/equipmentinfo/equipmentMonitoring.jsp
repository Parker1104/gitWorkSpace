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
			if (!treeNode && event.target.tagName.toLowerCase() != "button"
					&& $(event.target).parents("a").length == 0) {
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
			if (!(event.target.id == "rMenu" || $(event.target).parents(
					"#rMenu").length > 0)) {
				rMenu.css({
					"visibility" : "hidden"
				});
			}
		}
		var a = 0;
		function go(totalcount) {
			var j = 100 / totalcount;
			a++;
			var i = totalcount - a;

			document.getElementById("bar_1").style.width = parseFloat(document
					.getElementById("bar_1").style.width)
					+ j + "%";
			var schedule = parseInt(document.getElementById("bar_1").style.width);
			if (i = 0) {
				document.getElementById("bar_1").value = "进度" + 100 + "%";
				a = 0;
			} else {
				document.getElementById("bar_1").value = "进度" + schedule + "%";
			}

			if (document.getElementById("bar_1").style.width == "100%") {
				window.clearInterval(bar_1);
			}
		};

		function goopenbox(count) {
			var j = 100 / count;
			a++;
			var i = count - a;
			document.getElementById("bar").style.width = parseFloat(document
					.getElementById("bar").style.width)
					+ j + "%";
			var schedule = parseInt(document.getElementById("bar").style.width);
			if (i = 0) {
				document.getElementById("bar").value = "进度" + 100 + "%";
				a = 0;
			} else {
				document.getElementById("bar").value = "进度" + schedule + "%";
			}

			if (document.getElementById("bar").style.width == "100%") {
				window.clearInterval(bar);
			}
		};

		//开箱 
		var dialogCode = "";
		var terminallist;
		function openBox(index) {
			var totalcount = terminallist.length;
			if (index >= totalcount)
				return;
			/* alert("index:" + index + ", count:" +totalcount + "terminal id:" + terminallist[index].terminalid + 
					"boxid:" + terminallist[index].boxid); */
			document.getElementById("lockerId").innerText = "";
			document.getElementById("lockerId").innerText = " 柜号  "
					+ terminallist[index].displayname;
			document.getElementById("boxId").innerText = "";
			document.getElementById("boxId").innerText = " 箱门号  "
					+ terminallist[index].boxid;
			terminalid = terminallist[index].terminalid
			queryTankno(terminalid);
			$
					.ajax({
						type : "POST",
						url : "${pageContext.request.contextPath}/equipmentMonitoring/opendirtyBox.do",
						data : {
							terminalid : terminallist[index].terminalid,
							"boxid" : terminallist[index].boxid
						},
						async : false,
						success : function(result) {
							go(totalcount);
							//延时1秒
							setTimeout(function() {
								openBox(++index)
							}, 1000);

						},
						error : function(XMLHttpRequest, textStatus,
								errorThrown) {
							go(totalcount);
							//延时1秒
							setTimeout(function() {
								openBox(++index)
							}, 1000);
						}
					});

		};
		//开所有箱子
		function openArk(index) {
			var totalcount = terminallist.length;
			if (index >= totalcount)
				return;

			document.getElementById("lockerId").innerText = "";
			document.getElementById("lockerId").innerText = " 柜号  "
					+ terminallist[index].displayname;

			document.getElementById("boxId").innerText = "";
			document.getElementById("boxId").innerText = " 柜总数  " + totalcount;
			$
					.ajax({
						type : "POST",
						url : "${pageContext.request.contextPath}/equipmentMonitoring/OpenAllBox.do",
						data : {
							terminalid : terminallist[index].terminalid,
							"boxid" : 0
						},
						async : false,
						success : function(result) {
							go(totalcount);
							//延时5秒
							setTimeout(function() {
								openArk(++index)
							}, 5000);

						},
						error : function(XMLHttpRequest, textStatus,
								errorThrown) {
							go(totalcount);
							//延时5秒
							setTimeout(function() {
								openArk(++index)
							}, 5000);
						}
					});

		};

		//空闲箱  
		function openLeisure(index) {
			var totalcount = terminallist.length;
			if (index >= totalcount)
				return;
			document.getElementById("lockerId").innerText = "";
			document.getElementById("lockerId").innerText = " 柜号  "
					+ terminallist[index].displayname;

			document.getElementById("boxId").innerText = "";
			document.getElementById("boxId").innerText = " 柜总数  " + totalcount;
			$
					.ajax({
						type : "POST",
						url : "${pageContext.request.contextPath}/equipmentMonitoring/openLeisureBox.do",
						data : {
							terminalid : terminallist[index].terminalid,
							"boxid" : 0
						},
						async : false,
						success : function(result) {
							go(totalcount);
							//延时5秒
							setTimeout(function() {
								openLeisure(++index)
							}, 5000);

						},
						error : function(XMLHttpRequest, textStatus,
								errorThrown) {
							go(totalcount);
							//延时5秒
							setTimeout(function() {
								openLeisure(++index)
							}, 5000);
						}
					});

		};
		//过期箱 
		function openOverdueBox(index) {
			var totalcount = terminallist.length;
			if (index >= totalcount)
				return;
			document.getElementById("lockerId").innerText = "";
			document.getElementById("lockerId").innerText = " 柜号  "
					+ terminallist[index].displayname;

			document.getElementById("boxId").innerText = "";
			document.getElementById("boxId").innerText = " 柜总数  " + totalcount;
			$
					.ajax({
						type : "POST",
						url : "${pageContext.request.contextPath}/equipmentMonitoring/openOverdueBox.do",
						data : {
							terminalid : terminallist[index].terminalid,
							"boxid" : 0
						},
						async : false,
						success : function(result) {
							go(totalcount);
							//延时5秒
							setTimeout(function() {
								openOverdueBox(++index)
							}, 5000);

						},
						error : function(XMLHttpRequest, textStatus,
								errorThrown) {
							go(totalcount);
							//延时5秒
							setTimeout(function() {
								openOverdueBox(++index)
							}, 5000);
						}
					});

		};
		//已用箱 
		function openUsedBox(index) {
			var totalcount = terminallist.length;
			if (index >= totalcount)
				return;
			document.getElementById("lockerId").innerText = "";
			document.getElementById("lockerId").innerText = " 柜号  "
					+ terminallist[index].displayname;

			document.getElementById("boxId").innerText = "";
			document.getElementById("boxId").innerText = " 柜总数  " + totalcount;

			$
					.ajax({
						type : "POST",
						url : "${pageContext.request.contextPath}/equipmentMonitoring/openUsedBox.do",
						data : {
							terminalid : terminallist[index].terminalid,
							"boxid" : 0
						},
						async : false,
						success : function(result) {
							go(totalcount);
							//延时5秒
							setTimeout(function() {
								openUsedBox(++index)
							}, 5000);

						},
						error : function(XMLHttpRequest, textStatus,
								errorThrown) {
							go(totalcount);
							//延时5秒
							setTimeout(function() {
								openUsedBox(++index)
							}, 5000);
						}
					});

		};
		//已用箱门 
		function opendirtyBoxId() {
			var dialogCode = dialog({
				title : "清理箱门",
				content : $('#zTreeDemoBackground_10'),
				okValue : $.i18n.prop('btn.ok'),
				ok : function() {
					document.getElementById("bar_1").style.width = "0%";
					opendirtyBoxIdByAreaCode();
					return false;
				},
				cancelValue : $.i18n.prop('btn.cancel'),
				cancel : function() {
					document.getElementById("bar_1").style.width = "0%";
					$('#boxId').empty();
					$('#lockerId').empty();
				}
			});
			dialogCode.width(480);
			dialogCode.height(100);
			dialogCode.showModal();
		};

		function opendirtyBoxIdByAreaCode() {

			var zTree = $.fn.zTree.getZTreeObj("treeDemo"), nodes = zTree
					.getSelectedNodes(), treeNode = nodes[0];
			if (nodes.length == 0) {
				showMsg($.i18n.prop("menuright.tip.select"));
				/* location.href = location.href; */
				return false;
			}

			$
					.ajax({
						type : "POST",
						url : "${pageContext.request.contextPath}/equipmentMonitoring/selectAllFreeBox.do",
						data : {
							areacode : treeNode.id
						},
						async : false,
						success : function(msg) {
							if (msg == "" || msg == "[]") {
								showMsg("该区域不存在设备.");
							} else {
								terminallist = eval("(" + msg + ")");
								openUsedBox(0);
							}
						},
					});
		};

		/* 根据终端ID查询所有信息  */
		function queryTankno(terminalid) {
			$
					.ajax({
						url : "${pageContext.request.contextPath}/equipmentMonitoring/queryTankno.do",
						data : {
							terminalid : terminalid
						},
						async : false,
						success : function(msg) {
							var dataObj = eval("(" + msg + ")");
							document.getElementById("lockerId").innerText = "";
							document.getElementById("lockerId").innerText = " 柜号  "
									+ dataObj[0].displayname;
						},
					});
		};

		function selectAllOverdueBox() {

			var zTree = $.fn.zTree.getZTreeObj("treeDemo"), nodes = zTree
					.getSelectedNodes(), treeNode = nodes[0];
			if (nodes.length == 0) {
				showMsg($.i18n.prop("menuright.tip.select"));
				/* location.href = location.href; */
				return false;
			}

			$
					.ajax({
						type : "POST",
						url : "${pageContext.request.contextPath}/equipmentMonitoring/selectAllFreeBox.do",
						data : {
							areacode : treeNode.id
						},
						async : false,
						success : function(msg) {
							if (msg == "" || msg == "[]") {
								showMsg("该区域不存在设备.");
							} else {
								terminallist = eval("(" + msg + ")");
								openOverdueBox(0);
							}
						},
					});
		};
		/* 一键打开过期箱门 */
		function AkeyOpensOverdueBox() {
			var dialogCode = dialog({
				title : "打开过期箱门 ",
				content : $('#zTreeDemoBackground_10'),
				okValue : $.i18n.prop('btn.ok'),
				ok : function() {
					document.getElementById("bar_1").style.width = "0%";
					selectAllOverdueBox();
					return false;
				},
				cancelValue : $.i18n.prop('btn.cancel'),
				cancel : function() {
					document.getElementById("bar_1").style.width = "0%";
					$('#boxId').empty();
					$('#lockerId').empty();
				}
			});
			dialogCode.width(480);
			dialogCode.height(100);
			dialogCode.showModal();
		};
		/* 一键打开故障箱门  */
		function AkeyOpensFaultBox() {

			var dialogCode = dialog({
				title : "打开故障箱门 ",
				content : $('#zTreeDemoBackground_10'),
				okValue : $.i18n.prop('btn.ok'),
				ok : function() {
					/* queryTerminalInfoByAreaCode(); */
					return false;
				},
				cancelValue : $.i18n.prop('btn.cancel'),
				cancel : function() {
					document.getElementById("bar_1").style.width = "0%";
					$('#boxId').empty();
					$('#lockerId').empty();
				}
			});
			dialogCode.width(480);
			dialogCode.height(100);
			dialogCode.showModal();

		};

		function selectAllFreeBox() {
			var zTree = $.fn.zTree.getZTreeObj("treeDemo"), nodes = zTree
					.getSelectedNodes(), treeNode = nodes[0];
			if (nodes.length == 0) {
				showMsg($.i18n.prop("menuright.tip.select"));
				/* location.href = location.href; */
				return false;
			}

			$
					.ajax({
						type : "POST",
						url : "${pageContext.request.contextPath}/equipmentMonitoring/selectAllFreeBox.do",
						data : {
							areacode : treeNode.id
						},
						async : false,
						success : function(msg) {
							if (msg == "" || msg == "[]") {
								showMsg("该区域不存在设备.");
							} else {
								terminallist = eval("(" + msg + ")");
								openLeisure(0);
							}
						},
					});
		};

		/* 一键打开空闲箱门  */
		function AkeyOpensFreeBox() {
			var dialogCode = dialog({
				title : "打开空闲箱门",
				content : $('#zTreeDemoBackground_10'),
				okValue : $.i18n.prop('btn.ok'),
				ok : function() {
					document.getElementById("bar_1").style.width = "0%";
					selectAllFreeBox();
					return false;
				},
				cancelValue : $.i18n.prop('btn.cancel'),
				cancel : function() {
					document.getElementById("bar_1").style.width = "0%";
					$('#boxId').empty();
					$('#lockerId').empty();
				}
			});
			dialogCode.width(480);
			dialogCode.height(100);
			dialogCode.showModal();
		};
		function selectFreeBox() {

		};
		/* 一键打开全部箱门 */
		function AkeyOpensAllBox() {
			var dialogCode = dialog({
				title : "开全部箱门",
				content : $('#zTreeDemoBackground_10'),
				okValue : $.i18n.prop('btn.ok'),
				ok : function() {
					document.getElementById("bar_1").style.width = "0%";
					selectAllBox()
					return false;
				},
				cancelValue : $.i18n.prop('btn.cancel'),
				cancel : function() {
					document.getElementById("bar_1").style.width = "0%";
					$('#boxId').empty();
					$('#lockerId').empty();
				}
			});
			dialogCode.width(480);
			dialogCode.height(100);
			dialogCode.showModal();
		}
		function selectAllBox() {

			var zTree = $.fn.zTree.getZTreeObj("treeDemo"), nodes = zTree
					.getSelectedNodes(), treeNode = nodes[0];
			if (nodes.length == 0) {
				showMsg($.i18n.prop("menuright.tip.select"));
				/* location.href = location.href; */
				return false;
			}

			$
					.ajax({
						type : "POST",
						url : "${pageContext.request.contextPath}/equipmentMonitoring/selsctTerminalEntity.do",
						data : {
							areacode : treeNode.id
						},
						async : false,
						success : function(msg) {
							if (msg == "" || msg == "[]") {
								showMsg("该区域不存在设备.");
							} else {
								terminallist = eval("(" + msg + ")");
								openArk(0);
							}
						},
					});
		};
		/* 查看窗口 */
		function zTreeOnClick(event, treeId, treeNode) {
			document.getElementById("NumberOfCases").innerText = "";
			document.getElementById("BoxRange").innerText = "";
			document.getElementById("ArkNumber").innerText = "";
			document.getElementById("ScopeOfArk").innerText = "";
			document.getElementById("usableNumber").innerText = "";
			document.getElementById("occupyNumber").innerText = "";
			$("#zTreeDemoBackground_8").empty();
			$("#zTreeDemoBackground_7").empty();
			/* 名称 */
			document.getElementById("xiangMen").innerText = treeNode.name
					+ " 箱门使用情况";
			$
					.ajax({
						type : "post",
						url : "${pageContext.request.contextPath}/equipmentMonitoring/selsctTerminalEntityOrBox.do",
						data : {
							areacode : treeNode.id
						},
						dataType : 'json',
						success : function(msgBoxTotalCount) {

							if (msgBoxTotalCount != null) {
								/* 箱子数量   */
								document.getElementById("NumberOfCases").innerText = "  箱门总数： "
										+ msgBoxTotalCount.length;
								/* 箱子范围    */
								document.getElementById("BoxRange").innerText = "起始箱号： "
										+ msgBoxTotalCount[0].dispalyname
										+ " - 截止箱号： "
										+ msgBoxTotalCount[msgBoxTotalCount.length - 1].dispalyname;

							}
							//柜信息  
							$
									.ajax({
										url : "${pageContext.request.contextPath}/equipmentMonitoring/selsctTerminalEntity.do",
										type : "post",
										dataType : 'json',
										data : {
											areacode : treeNode.id
										},
										success : function(msg) {
											if (msg.length > 0) {
												$("#zTreeDemoBackground_7")
														.empty();
												/* 柜子数量  */
												document
														.getElementById("ArkNumber").innerText = "  柜子数量： "
														+ msg.length;
												document
														.getElementById("ScopeOfArk").innerText = "起始柜号： "
														+ msg[0].displayname
														+ "  -  截止柜号： "
														+ msg[msg.length - 1].displayname;

												var html = "";
												html = "<ul><li style='width: 600px; height: 24px;'>"
												html += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;请选择你要查询的柜号：   <select id='tankNo' name='tankNo' onchange='tankNo()'  style='width: 90px; height: 24px;'>"
												html += "'<option value='"+0+"'>请选择柜子</option>"
												for (var i = 0; i < msg.length; i++) {
													html += "'<option value='"+msg[i].terminalid+"'>"
															+ msg[i].displayname
															+ "</option>"
												}
												html += "</select'> </li></ul>"
												//html += "<input type='submit' value='挂失' onclick='missBoxTeWay()' style='width:45px; background-color: #74A3DC;margin-left: 15px; border: none;border-radius: 3px;'></li></ul>"
												$("#zTreeDemoBackground_7")
														.append(html);

											}
											$
													.ajax({
														url : "${pageContext.request.contextPath}/equipmentMonitoring/selsctTerminalEntity.do",
														type : "post",
														dataType : 'json',
														data : {
															areacode : treeNode.id
														},
														success : function(msg) {
															if (msg.length > 0) {
																//在箱记录查询 	
																$
																		.ajax({
																			url : "${pageContext.request.contextPath}/equipmentMonitoring/selsctBeBoxCount.do",
																			type : "post",
																			dataType : 'json',
																			data : {
																				areacode : treeNode.id
																			},
																			success : function(
																					msg) {
																				if (msg != "[]") {
																					document
																							.getElementById("usableNumber").innerText = "  可用箱数： "
																							+ (msgBoxTotalCount.length - msg.msg);
																					document
																							.getElementById("occupyNumber").innerText = "已用箱数： "
																							+ msg.msg;

																				}
																			},
																		});
															}
														},
													});
										},
									});
						},
					});
		};
		function tankNo() {

			var zTree = $.fn.zTree.getZTreeObj("treeDemo"), nodes = zTree
					.getSelectedNodes(), treeNode = nodes[0];
			if (nodes.length == 0) {
				showMsg($.i18n.prop("menuright.tip.select"));
				/* location.href = location.href; */
				return false;
			}
			var options = $("#tankNo option:selected");
			$
					.ajax({
						url : "${pageContext.request.contextPath}/equipmentMonitoring/selsctallBox.do?number=Math.random()",
						data : {
							tankNo : options.val()
						},
						success : function(msg) {
							$("#zTreeDemoBackground_8").empty();
							$("#kaixiangs").empty();
							$("#kaixiang").empty();
							$("#unlockBox").empty();
							$("#lockBox").empty();
							$("#clearBox").empty();
							//写对柜子操作的条件 
							$("#kaixiang")
									.append(
											'<option selected="selected"  value="0">请选择</option>');
							$("#kaixiangs")
									.append(
											'<option selected="selected"  value="0">请选择</option>');
							$("#unlockBox")
									.append(
											'<option selected="selected"  value="0">请选择</option>');
							$("#lockBox")
									.append(
											'<option selected="selected"  value="0">请选择</option>');
							$("#clearBox")
									.append(
											'<option selected="selected"  value="0">请选择</option>');
							if (msg != "[]") {
								var dataObj = eval("(" + msg + ")");
								//改兰色区域内容 

								/* 名称 */
								document.getElementById("xiangMen").innerText = treeNode.name
										+ " 箱门使用情况";
								/* 箱子数量   */
								document.getElementById("NumberOfCases").innerText = "  箱门总数： "
										+ dataObj.length;
								/* 箱子范围    */
								document.getElementById("BoxRange").innerText = "起始箱号： "
										+ dataObj[0].dispalyname
										+ " - 截止箱号： "
										+ dataObj[dataObj.length - 1].dispalyname;
								/* 柜子数量  */
								document.getElementById("ArkNumber").innerText = "  柜子数量： " + 1;
								/* 设备id */
								document.getElementById("terId").value = dataObj[0].terminalid;
								document.getElementById("lockBox_terId").value = dataObj[0].terminalid;
								document.getElementById("clearBox_terId").value = dataObj[0].terminalid;
								document.getElementById("unlockBox_terId").value = dataObj[0].terminalid;
								var z = 0;
								var s = 0;
								var g = 0;
								var c = 0;
								var html = "";
								html = "<table border='0' cellspacing='0' cellpadding='0'>";
								/*     html += "<tr><td  align='center' colspan='6' > 箱状态  </td></tr>";  */
								html += "<tr>";
								for (var i = 0; i < dataObj.length; i++) {

									if (dataObj[i].status == 0) {
										z++;
										html += "<td height='45px'>"
												+ "<input type='Button' id='"+dataObj[i].boxid+"' value='"+dataObj[i].dispalyname+"'style='width:60px; height:40px; font-size: 10px; background-color: #85AC3E;'>"
												+ "</td>"
									} else if (dataObj[i].status == 1) {
										s++;
										html += "<td width='46px' height='45px'>"
												+ "<input type='Button' id='"+dataObj[i].boxid+"'  value='"+dataObj[i].dispalyname+"'style='width:60px; height:40px; font-size: 10px; background-color: #F2AC24;'>"
												+ "</td>"
									} else if (dataObj[i].status == 2) {
										g++;
										html += "<td width='46px' height='45px'>"
												+ "<input type='Button' id='"+dataObj[i].boxid+"' value='"+dataObj[i].dispalyname+"'style='width:60px; height:40px; font-size: 10px; background-color: red;'>"
												+ "</td>"
									} else if (dataObj[i].status == 3) {
										c++;
										html += "<td width='46px' height='45px'>"
												+ "<input type='Button' id='"+dataObj[i].boxid+"' value='"+dataObj[i].dispalyname+"'style='width:60px; height:40px; font-size: 10px; background-color: #1496DE;'>"
												+ "</td>"
									}

									//写对柜子操作的条件 
									$("#kaixiang").append(
											'<option value="' +dataObj[i].boxid+ '">'
													+ dataObj[i].dispalyname
													+ '</option>');
									$("#kaixiangs").append(
											'<option value="' +dataObj[i].boxid+ '">'
													+ dataObj[i].dispalyname
													+ '</option>');
									$("#unlockBox").append(
											'<option value="' +dataObj[i].boxid+ '">'
													+ dataObj[i].dispalyname
													+ '</option>');
									$("#lockBox").append(
											'<option value="' +dataObj[i].boxid+ '">'
													+ dataObj[i].dispalyname
													+ '</option>');
									$("#clearBox").append(
											'<option value="' +dataObj[i].boxid+ '">'
													+ dataObj[i].dispalyname
													+ '</option>');

									if (((i + 1) % 8) == 0) {
										html += "</tr>";
									}
								}
								html += "</tr>";
								html += "</table>";
								$("#zTreeDemoBackground_8").append(html);

								document.getElementById("usableNumber").innerText = "  可用箱数： "
										+ z;

								document.getElementById("zc").value = '正常 : '
										+ z;
								document.getElementById("sd").value = '锁定 : '
										+ s;
								document.getElementById("gz").value = '故障 : '
										+ g;
								document.getElementById("cs").value = '超时 : '
										+ c;
								selsctBoxState(dataObj[0].terminalid);

							}

						},
					});
		}
		//在箱 数据 
		function selsctBoxState(terminalid) {
			$
					.ajax({
						url : "${pageContext.request.contextPath}/equipmentMonitoring/selsctBoxState.do",
						type : "post",
						dataType : 'json',
						data : {
							terminalid : terminalid
						},
						success : function(msg) {
							if (msg != "[]") {
								//var dataObj = eval("(" + msg + ")");
								var e = 0;
								for (var i = 0; i < msg.length; i++) {
									e++;
									$("#" + msg[i].boxid).css("background",
											"#813507");

								}
								document.getElementById("occupyNumber").innerText = "已用箱数： "
										+ e;
								document.getElementById("zx").value = '在箱: '
										+ e;
								selsctTimeoutBox(terminalid);
							}

						},
					});
		}
		//超时 数据  
		function selsctTimeoutBox(terminalid) {
			$
					.ajax({
						url : "${pageContext.request.contextPath}/equipmentMonitoring/selsctTimeoutBox.do",
						type : "post",
						dataType : 'json',
						data : {
							terminalid : terminalid
						},
						success : function(msg) {
							if (msg != "[]") {
								//var dataObj = eval("(" + msg + ")");
								var c = 0;
								for (var i = 0; i < msg.length; i++) {
									c++;
									$("#" + msg[i].boxid).css("background",
											"#1496DE");

								}
								document.getElementById("cs").value = '超时: '
										+ c;
							}

						},
					});
		}
		function quzhi(boxid) {
			var kaixiang = parseInt(document.getElementById("kaixiang").value);
			var kaixiangs = parseInt(document.getElementById("kaixiangs").value);
			var terId = document.getElementById("terId").value;
			if (kaixiang != 0 & kaixiangs != 0) {
				if (kaixiang < kaixiangs) {
					outOfTeWayopen(terId, kaixiang, kaixiangs, 1)
				} else if (kaixiang == kaixiangs) {
					outOfTeWayopen(terId, kaixiang, kaixiangs, 1)
				} else if (kaixiang > kaixiangs) {
					showMsg("范围有误请重新输入");
					return false;
				}

			} else {
				if (kaixiang != 0 & kaixiangs == 0) {
					outOfTeWayopen(terId, kaixiang, kaixiang, 1)
				} else if (kaixiangs != 0 & kaixiang == 0) {
					outOfTeWayopen(terId, kaixiangs, kaixiangs, 1)
				}
			}
			var dialogCode = dialog({
				title : "开箱",
				content : $('#zTreeDemoBackground_10'),
				cancelValue : $.i18n.prop('btn.cancel'),
				cancel : function() {
					document.getElementById("bar").style.width = "0%";
					$("#boxId").empty();
					$("#result").empty();
				}
			});
			dialogCode.width(480);
			dialogCode.height(100);
			dialogCode.showModal();
		}
		//测试开箱 
		function OpensBox() {
			$
					.ajax({
						url : "http://192.168.8.46:8080/IntelligentStorageMP-APP/equipmentMonitoring/???",
						type : "post",
						dataType : 'json',
						data : {
							//APP存  取要传的参数 
							userCardID : "", //读者卡号
							userName : "", //学生名称 
							className : "", //班级 
							displayName : 1001, //柜子名称 
							//做区域查询剩余箱信息       传这个参数  
							areaCode : "", //区域编号
							//做个人存取记录查询           传这个参数  
							userCardID : "", //读者卡号
							//查询柜子剩余箱信息            传这个参数  
							displayName : 1001, //柜子名称 

						},
						success : function(msg) {
							showMsg(msg.msg);
						},
					});
		}
		/* 开箱 */
		function outOfTeWay() {
			var dialogCode = dialog({
				title : "开箱",
				content : $('#OutOfThe'),
				okValue : $.i18n.prop('btn.ok'),
				ok : function() {
					$("#zTreeDemoBackground_10").empty();
					var html = "";
					html = " <p align='center' style='font-size: 15px;width:450px'>是否要开启箱门 </p>"
					html += "<label id='boxId' style='width:150px; height:30px;'></label><label id='result'  style='width:150px; height:30px;'></label>";
					html += "<div class='graph' ><input type='text'  id='bar' disabled='disabled' style='width:0%;'></div>";
					$("#zTreeDemoBackground_10").append(html);
					quzhi(0);

				},
				cancelValue : $.i18n.prop('btn.cancel'),
				cancel : function() {
				}
			});
			dialogCode.width(400);
			dialogCode.showModal();

		};

		function outOfTeWayopen(terId, boxid, EndBoxNo, count) {
			$
					.ajax({
						url : "${pageContext.request.contextPath}/equipmentMonitoring/openBox.do",
						data : {
							terId : terId,
							startBoxNo : boxid,
							EndBoxNo : EndBoxNo
						},
						success : function(msg) {
							var dataObj = eval("(" + msg + ")");
							if (EndBoxNo == null || EndBoxNo == '') {
								document.getElementById("boxId").innerText = "";
								document.getElementById("boxId").innerText = "开箱数量：  "
										+ (boxid - 0);
							} else {
								document.getElementById("boxId").innerText = "";
								document.getElementById("boxId").innerText = "开箱数量：  "
										+ (EndBoxNo - boxid + 1);
							}
							document.getElementById("result").innerText = "";
							document.getElementById("result").innerText = "开启结果 ："
									+ dataObj.msg;
							goopenbox(count);
							boxid++;
							/* if (count >= boxid) {
								setTimeout(function() {
									outOfTeWayopen(terId, boxid, count)
								}, 5000);

							} */
						},
						error : function(XMLHttpRequest, textStatus,
								errorThrown) {
							goopenbox(count);
							/* boxid++;
							if (count >= boxid) {
								setTimeout(function() {
									outOfTeWayopen(terId, boxid, count)
								}, 5000);
							} */
						}
					});
		};

		/* 锁箱 */
		function lockBoxTeWay() {
			var dialogCode = dialog({
				title : "锁箱",
				content : $('#lockBoxFrom'),
				okValue : $.i18n.prop('btn.ok'),
				ok : function() {
					var lockBox = document.getElementById("lockBox").value;
					var terId = document.getElementById("lockBox_terId").value;
					if (lockBox == "" || lockBox == null) {
						showMsg("输入不完整");
						return false;
					} else {
						$
								.ajax({
									url : "${pageContext.request.contextPath}/equipmentMonitoring/lockBox.do",
									data : {
										terId : terId,
										lockBox : lockBox,
									},
									success : function(msg) {
										showMsg(eval("(" + msg + ")").msg);
										tankNo();
										/* top.location='${pageContext.request.contextPath}'; */
									},
								});
					}
				},
				cancelValue : $.i18n.prop('btn.cancel'),
				cancel : function() {

				}
			});
			dialogCode.width(350);
			dialogCode.showModal();
		};

		/* 解锁 */
		function unlockBoxTeWay() {
			var dialogCode = dialog({
				title : "解锁",
				content : $('#unlockBoxFrom'),
				okValue : $.i18n.prop('btn.ok'),
				ok : function() {
					var unlockBox = document.getElementById("unlockBox").value;
					var terId = document.getElementById("unlockBox_terId").value;
					if (unlockBox == "" || unlockBox == null) {
						showMsg("输入不完整");
						return false;
					} else {
						$
								.ajax({
									url : "${pageContext.request.contextPath}/equipmentMonitoring/unlockBox.do",
									data : {
										terId : terId,
										unlockBox : unlockBox,
									},
									success : function(msg) {
										showMsg(eval("(" + msg + ")").msg);
										tankNo();
										/* top.location='${pageContext.request.contextPath}'; */
									},
								});
					}
				},
				cancelValue : $.i18n.prop('btn.cancel'),
				cancel : function() {
				}
			});
			dialogCode.width(350);
			dialogCode.showModal();

		};
		/* 挂失 */
		function missBoxTeWay() {
			var dialogCode = dialog({
				title : "挂失",
				content : '您确认想要挂失所有的未退卡吗？',
				okValue : $.i18n.prop('btn.ok'),
				ok : function() {					
					$.ajax({
						url : "${pageContext.request.contextPath}/equipmentMonitoring/missBox.do",
						data : {},
						success : function(msg) {
							showMsg(eval("(" + msg + ")").msg);							
						},
					 });
				},
				cancelValue : $.i18n.prop('btn.cancel'),
				cancel : function() {
				}
			});
			dialogCode.width(350);
			dialogCode.showModal();

		};
		/* 清箱  */
		function clearBoxTeWay() {
			var dialogCode = dialog({
				title : "清箱",
				content : $('#clearBoxFrom'),
				okValue : $.i18n.prop('btn.ok'),
				ok : function() {
					var clearBox = document.getElementById("clearBox").value;
					var terId = document.getElementById("clearBox_terId").value;
					if (clearBox == "" || clearBox == null) {
						showMsg("输入不完整");
						return false;
					} else {
						$
								.ajax({
									url : "${pageContext.request.contextPath}/equipmentMonitoring/clearBox.do",
									data : {
										terId : terId,
										clearBox : clearBox,
									},
									success : function(msg) {
										showMsg(eval("(" + msg + ")").msg);
										tankNo();
										/* top.location='${pageContext.request.contextPath}'; */
									},
								});
					}
				},
				cancelValue : $.i18n.prop('btn.cancel'),
				cancel : function() {
				}
			});
			dialogCode.width(350);
			dialogCode.showModal();
		};

		$('#modifyForm')
				.ajaxForm(
						{
							dataType : "json",
							beforeSerialize : function() {
								if ($('#name').val() == '') {
									showMsg($.i18n
											.prop("param.notempty")
											.replace(
													"%s",
													$.i18n
															.prop('role.tip.rolename')));
									$('#name').select();
									return false;
								}
								return true;
							},
							success : function(data) {
								if (data.success) {
									var zTree = $.fn.zTree
											.getZTreeObj("treeDemo");
									var nodes = zTree.getSelectedNodes();
									var treeNode = nodes[0];
									if (treeNode) {
										var tmpobj = $.parseJSON(data.data);
										var updatenode = zTree.getNodeByParam(
												"id", tmpobj.id);
										if (updatenode) {
											updatenode.name = tmpobj.name;
											zTree.updateNode(updatenode);
										} else {
											treeNode = zTree.addNodes(treeNode,
													{
														id : tmpobj.id,
														pId : tmpobj.pId,
														open : true,
														isParent : true,
														name : tmpobj.name
													});
										}
									}
									if (treeNode) {
										//zTree.editName(treeNode[0]);
										showMsg(eval("(" + data + ")").msg);
									} else {
										showMsg("<spring:message code='area.prompt3.information'/>");
									}
									location.href = location.href;
								} else {
									showMsg(eval("(" + data + ")").msg);
								}
								win.close();
							}

						});

		var zTree, rMenu;
		$(document)
				.ready(
						function() {
							$
									.ajax({
										type : "post",
										/*带区域 控制   ${pageContext.request.contextPath}/equipmentMonitoring/findAll.do */
										/* ${pageContext.request.contextPath}/areaAction/findAll.do */
										url : "${pageContext.request.contextPath}/equipmentMonitoring/findAll.do",
										//data : {"ids": ids},
										dataType : 'json',//JSON.stringify(
										success : function(zNodes) {
											$.fn.zTree.init($("#treeDemo"),
													setting, zNodes);
											zTree = $.fn.zTree
													.getZTreeObj("treeDemo");
											rMenu = $("#rMenu");
										},
										error : function(XMLHttpRequest,
												textStatus, errorThrown) {
										}
									});

						});

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

	<div class="content_wrap">
		<%-- <p><spring:message code="area.title"/></p> --%>
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
						<td colspan="3" align="center"><label id="xiangMen"
							style="font-size: 20px; color: blue;"></label></td>
					</tr>
					<tr height="28">
						<td width="20"></td>
						<td width="200"><label id="ArkNumber"></label></td>
						<td width="398"><label id="ScopeOfArk"></label></td>

					</tr>
					<tr height="28">
						<td></td>
						<td><label id="NumberOfCases"></label></td>
						<td><label id="BoxRange"></label></td>
					</tr>
					<tr height="28">
						<td></td>
						<td><label id="usableNumber"></label></td>
						<td><label id="occupyNumber"></label></td>
					</tr>
				</table>
			</div>
			<!-- 主要显示柜子信息以及操作  -->
			<div id="zTreeDemoBackground_6">
				<div id="zTreeDemoBackground_7"></div>
				<form action="" id="selectarea" method="post">
					<div id="zTreeDemoBackground_8"></div>
					<div id="zTreeDemoBackground_10" style="display: none;">

						<p align='center' style='font-size: 15px; width: 450px'>是否要打开当前区域下的箱门
						</p>
						<label id="lockerId" style="width: 150px; height: 30px;"></label><label
							id="boxId" style="width: 150px; height: 30px;"></label>
						<div class="graph" align="left">
							<input type="text" id="bar_1" disabled="disabled"
								style="width: 1%;">
						</div>
					</div>
				</form>

				<div id="zTreeDemoBackground_9" class="list">
					<table width="600">
						<tr>
							<td align="center"><input type='Button' value='正常' id="zc"
								style='width: 100px; background-color: #85AC3E;'> <input
								type='Button' value='在箱' id="zx"
								style='width: 100px; background-color: #813507;'> <input
								type='Button' value='锁定' id="sd"
								style='width: 100px; background-color: #F2AC24;'> <input
								type='Button' value='故障' id="gz"
								style='width: 100px; background-color: red;'> <input
								type='Button' value='超时' id="cs"
								style='width: 100px; background-color: #1496DE;'></td>
						</tr>
					</table>

				</div>

			</div>
			<!-- 开箱  -->
			<div id="OutOfThe" class="list" style="display: none;">
				<form id="OutOfTheFrom" action="">
					<ul>
						<li style="display: none;">设备id：<input type="text" id="terId">
							<!-- <li>起始箱编号：<input type="text" id="kaixiang" value="1" style="width: 88px;height: 24px" maxlength="60" onkeyup="this.value=this.value.replace(/\D/g,'')"> 
					  — 截止箱编号：<input type="text" id="kaixiangs" style="width: 88px;height: 24px" maxlength="60" onkeyup="this.value=this.value.replace(/\D/g,'')">  </li> -->
						<li>起始箱编号：<select name="kaixiang" id="kaixiang"
							style="width: 88px; height: 24px">
								<option value="" selected="selected">请选择</option>
						</select> - 截止箱编号：<select name="kaixiangs" id="kaixiangs"
							style="width: 88px; height: 24px">
								<option value="" selected="selected">请选择</option>
						</select>
						</li>
					</ul>
				</form>
			</div>
			<!-- 锁箱  -->
			<div id="lockBoxFrom" class="list" style="display: none;">
				<form id="" action="">
					<ul>
						<li style="display: none;">设备id：<input type="text"
							id="lockBox_terId"> <!-- <li>箱编号：<input type="text" id="lockBox" maxlength="60" onkeyup="this.value=this.value.replace(/\D/g,'')"> -->
						<li>箱编号：<select name="lockBox" id="lockBox">
								<option value="" selected="selected">请选择</option>
						</select>
						</li>
					</ul>
				</form>
			</div>
			<div id="unlockBoxFrom" class="list" style="display: none;">
				<form id="" action="">
					<ul>
						<li style="display: none;">设备id：<input type="text"
							id="unlockBox_terId"></li>
						<!-- <li>箱编号：<input type="text" id="unlockBox" maxlength="60"  onkeyup="this.value=this.value.replace(/\D/g,'')"> </li> -->
						<li>箱编号：<select name="unlockBox" id="unlockBox">
								<option value="" selected="selected">请选择</option>
						</select>
						</li>
					</ul>
				</form>
			</div>
			<div id="clearBoxFrom" class="list" style="display: none;">
				<form id="" action="">
					<ul>
						<li style="display: none;">设备id：<input type="text"
							id="clearBox_terId"> <!-- <li>箱编号：<input type="text" id="clearBox" maxlength="60" onkeyup="this.value=this.value.replace(/\D/g,'')"> -->
						<li>箱编号：<select name="clearBox" id="clearBox">
								<option value="" selected="selected">请选择</option>
						</select>
						</li>
					</ul>
				</form>
			</div>
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
	<div id="areaInfo" align="center" style="display: none;">
		<form id="modifyForm" method="post"
			action="${pageContext.request.contextPath}/areaAction/saveAreaName.do">
			<div class="list" align="center">
				<ul>
					<li style="width: 305px;"><spring:message code="area.areaname" />：
						<input id="name" name="name" type="text" value="" maxlength="32" /><span
						style="color: red">*</span></li>
					<li><spring:message code="area.shortname" />： <input
						id="areashortname" name="areashortname" type="text" value=""
						maxlength="32" /></li>
					<li style="display: none;"><input type="hidden" id="id"
						name="id" /></li>
					<li style="display: none;"><input type="hidden" id="pId"
						name="pId" /></li>
					<%-- <input type="submit" value='<spring:message code="btn.submit" />' /> --%>
				</ul>
			</div>
		</form>
	</div>
</body>
</html>