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

<link rel="stylesheet" href="${pageContext.request.contextPath}/js/plug/zTree/css/demo.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/js/plug/zTree/css/zTreeStyle/zTreeStyle.css" type="text/css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/js/plug/zTree/js/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/plug/zTree/js/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/module/commodity/category.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/plug/zTree/js/jquery.ztree.exhide-3.5.js"></script>
 
 
 
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
			document.getElementById("bar_1").style.width = parseFloat(document .getElementById("bar_1").style.width) + j + "%";
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
			document.getElementById("bar").style.width = parseFloat(document.getElementById("bar").style.width)+ j + "%";
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

			document.getElementById("lockerId").innerText = "";
			document.getElementById("lockerId").innerText = " 柜号  "+ terminallist[index].displayname;
			document.getElementById("boxId").innerText = "";
			document.getElementById("boxId").innerText = " 箱门号  "+ terminallist[index].boxid;
			terminalid = terminallist[index].terminalid
			queryTankno(terminalid);
			$.ajax({
						type : "POST",
						url : "${pageContext.request.contextPath}/weixinMenu/opendirtyBox.do",
						data : {
							terminalid : terminallist[index].terminalid,
							"boxid" : terminallist[index].boxid
						},
						async : false,
						success : function(result) {
							go(totalcount);
							//延时1秒
							setTimeout(function() {openBox(++index)
							}, 1000);

						},
						error : function(XMLHttpRequest, textStatus,errorThrown) {
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
			document.getElementById("lockerId").innerText = " 柜号  " + terminallist[index].displayname;

			document.getElementById("boxId").innerText = "";
			document.getElementById("boxId").innerText = " 柜总数  " + totalcount;
			$.ajax({
						type : "POST",
						url : "${pageContext.request.contextPath}/weixinMenu/OpenAllBox.do",
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
			document.getElementById("lockerId").innerText = " 柜号  " + terminallist[index].displayname;

			document.getElementById("boxId").innerText = "";
			document.getElementById("boxId").innerText = " 柜总数  " + totalcount;
			$.ajax({
						type : "POST",
						url : "${pageContext.request.contextPath}/weixinMenu/openLeisureBox.do",
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
			document.getElementById("lockerId").innerText = " 柜号  " + terminallist[index].displayname;

			document.getElementById("boxId").innerText = "";
			document.getElementById("boxId").innerText = " 柜总数  " + totalcount;
			$.ajax({
						type : "POST",
						url : "${pageContext.request.contextPath}/weixinMenu/openOverdueBox.do",
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
			document.getElementById("lockerId").innerText = " 柜号  " + terminallist[index].displayname;

			document.getElementById("boxId").innerText = "";
			document.getElementById("boxId").innerText = " 柜总数  " + totalcount;

			$.ajax({
						type : "POST",
						url : "${pageContext.request.contextPath}/weixinMenu/openUsedBox.do",
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

			var zTree = $.fn.zTree.getZTreeObj("treeDemo"), nodes = zTree.getSelectedNodes(), treeNode = nodes[0];
			if (nodes.length == 0) {
				showMsg($.i18n.prop("menuright.tip.select"));
				/* location.href = location.href; */
				return false;
			}

			$.ajax({
						type : "POST",
						url : "${pageContext.request.contextPath}/weixinMenu/selectAllFreeBox.do",
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
			$.ajax({
						url : "${pageContext.request.contextPath}/weixinMenu/queryTankno.do",
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

			$.ajax({
						type : "POST",
						url : "${pageContext.request.contextPath}/weixinMenu/selectAllFreeBox.do",
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
			var zTree = $.fn.zTree.getZTreeObj("treeDemo"), nodes = zTree.getSelectedNodes(), treeNode = nodes[0];
			if (nodes.length == 0) {
				showMsg($.i18n.prop("menuright.tip.select"));
				/* location.href = location.href; */
				return false;
			}
			$.ajax({
						type : "POST",
						url : "${pageContext.request.contextPath}/weixinMenu/selectAllFreeBox.do",
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
			var zTree = $.fn.zTree.getZTreeObj("treeDemo"), nodes = zTree.getSelectedNodes(), treeNode = nodes[0];
			if (nodes.length == 0) {
				showMsg($.i18n.prop("menuright.tip.select"));
				/* location.href = location.href; */
				return false;
			}
			$.ajax({
						type : "POST",
						url : "${pageContext.request.contextPath}/weixinMenu/selsctTerminalEntity.do",
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
		    $("#zTreeDemoBackground_8").empty();
			$("#zTreeDemoBackground_7").empty();
		    var codes=treeNode.id;
			//柜信息  
			$.ajax({
						url : "${pageContext.request.contextPath}/weixinMenu/selectWxmenuEntity.do",
						type : "post",
						dataType : 'json',
						data : {
							 codes : codes
						},
						success : function(msg) {
 
							var codes=msg.codes;
							var contentes=msg.contentes;
					 
								var html = "";
								html = "<ul><li  >"
								//html += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;请选择你要查询的柜号：   <select id='tankNo' name='tankNo' onchange='tankNo()'  style='width: 90px; height: 24px;'>"
							    html += "<input type='submit' value='修改' onclick= 'outOfTeWay()'  >"
 
							    html += " <form id=OutOfTheFrom    > "
							    html += "      <textarea cols=50 rows=10 id=contentes name=contentes> "+contentes+"  </textarea>      "
							    html += "      <input  value="+codes+"    type=hidden   id=codes name=codes   />      "
							    html += "  </form>  "
							    html += " </li></ul>"
 								$("#zTreeDemoBackground_7") .append(html);
						 
						},
					});
		};
		/*  修改      */
		function outOfTeWay() {
			var codes =  document.getElementById("codes").value ;
			var contentes =  document.getElementById("contentes").value ;
			if(confirm("确认要修改吗!")){ 
				$.ajax({
					url : "${pageContext.request.contextPath}/weixinMenu/updateWxmenuEntity.do",
					data : {
						codes : codes,
						contentes : contentes 
					},
					success : function(msg) {
						 alert("修改成功!");
					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {
					 
					}
				});
			} 
		}
		
 
		function tankNo() {
  
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
			$.ajax({
						url : "http://192.168.8.46:8080/IntelligentStorageMP-APP/weixinMenu/???",
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


		function outOfTeWayopen(terId, boxid, EndBoxNo, count) {
			$.ajax({
						url : "${pageContext.request.contextPath}/weixinMenu/openBox.do",
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
						$.ajax({
									url : "${pageContext.request.contextPath}/weixinMenu/unlockBox.do",
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
						url : "${pageContext.request.contextPath}/weixinMenu/missBox.do",
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
						$.ajax({
									url : "${pageContext.request.contextPath}/weixinMenu/clearBox.do",
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

		$('#modifyForm').ajaxForm(
						{
							dataType : "json",
							beforeSerialize : function() {
								if ($('#name').val() == '') {
									showMsg($.i18n .prop("param.notempty")
											.replace( "%s", $.i18n .prop('role.tip.rolename')));
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
		$(document).ready(
				function() {
					$.ajax({
						type : "post",
						url : "${pageContext.request.contextPath}/weixinMenu/findAll.do",
						dataType : 'json',
						success : function(zNodes) {
							$.fn.zTree.init(  $("#treeDemo"), setting, zNodes  );
							zTree = $.fn.zTree .getZTreeObj("treeDemo");
							rMenu = $("#rMenu");
						},
						error : function(XMLHttpRequest,
								textStatus, errorThrown) {
						}
					});
	            }
		);

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
 
		<div id="zTreeDemoBackground_1">
			<ul id="treeDemo" class="ztree"
				style="margin-left: 50px; margin-top: 0px; width: 25%; height: 95%;">
			</ul>
		</div>
		<div id="zTreeDemoBackground_4">
 
			 <div id="zTreeDemoBackground_7"></div>
 
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