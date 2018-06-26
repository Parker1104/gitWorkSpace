<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title><spring:message code="callThePoliceLog.title.text" /></title>
<%@ include file="../../common/head.jsp"%>
<%@ include file="../../common/theme.jsp"%>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/js/plug/zTree/css/demo.css"
	type="text/css">

<style>
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

td#datatable_toppager_center {
	display: none;
}
</style>
</head>
<body style="overflow-x: hidden">
	<div class="content" align="center" style="display: none;">
		<!-- 查询操作日志 -->
		<form id="searchMemberCardForm"
			action="${pageContext.request.contextPath}/callThePoliceLogController/selectTerminalAlarmDiary.do"
			method="post">
			<div class="list">
				<ul>
					<li><spring:message code="callThePoliceLog.alarmcode" />：<input name="alarmcode" id="alarmcode_1" type="text" /></li>
				</ul>
				<!-- <ul style="float:right;margin-right:-175px;">   				
				<li style="margin-left: 50px;"><input onclick="submitForm()"
				   value="查询" type="button" id="selectAlarmcode" style="width: 88px; font-size: 14px;" /></li>
			 </ul> -->
			</div>
		</form>
	</div>
	<div class="tab">
		<table id="datatable"></table>
		<div id="pager2"></div>
	</div>
	<script type="text/javascript">
		/* 查询 当摁下 enter键后执行 button 点击事件 */
		document.onkeydown = function(event) {
			var e = event || window.event
					|| arguments.callee.caller.arguments[0];
			if (e && e.keyCode == 13) { // enter 键
				if (document.activeElement.id == "alarmcode_1") {
					$(".ui-dialog-autofocus").trigger("click");
					return false;
				}
			}
		};
		function submitForm() {
			$("#datatable").jqGrid('setGridParam', {
				postData : formToJson("searchMemberCardForm"),
				page : 1
			}).trigger("reloadGrid");
		}
		function formToJson(boxtypecode) {//把form表单的所有数据变成json对象
			var formArray = $('#' + boxtypecode).serializeArray();//数组里面首先放入充值记录表的内容
			var formJson = new Object();
			$.each(formArray, function(i, n) {//把formarray转换为json
				if ((n.name != undefined && n.name != "")) {
					formJson[n.name] = n.value;
				}
			});
			return formJson;
		}
		$(document)
				.ready(
						function() {
							var tableWidth = $("#datatable").parent()
									.innerWidth() + 25;
							$("#datatable")
									.jqGrid(
											{
												url : '${pageContext.request.contextPath}/callThePoliceLogController/selectTerminalAlarmDiary.do',
												datatype : "json",
												mtype : "POST",
												width : document.body.offsetWidth - 60,
												height : window.screen.availHeight - 325,
												//autowidth:true,
												editable : false,
												shrinkToFit : true,
												colNames : [ $.i18n.prop('callThePoliceLog.table.col1.text'),$.i18n.prop('callThePoliceLog.table.col2.text'),
													         $.i18n.prop('callThePoliceLog.table.col3.text'), $.i18n.prop('callThePoliceLog.table.col4.text')],
												colModel : [ {
													name : "alarmcode",
													index : "alarmcode",
													align : 'center',
													width : 150,
													sortable : false
												}, {
													name : "handlopcode",
													index : "handlopcode",
													align : 'center',
													width : 150,
													sortable : false
												}, {
													name : "handlecontent",
													index : "handlecontent",
													align : 'center',
													width : 150,
													sortable : false
												}, {
													name : "handleresult",
													index : "handleresult",
													align : 'center',
													width : 150,
													sortable : false
												},
												/* {name:"",index:'',align:'center',width:150,sortable:false,formatter:function(el,options,rowData){
												var d = '<input type="button" value="删除" onclick="deleteTerminalAlarmDiary(\''+rowData.alarmcode+'\')" style="width:50px;float:right;margin-right:10px;"/>';
												return d;
												}}, */
												],
												sortable : false,
												rowNum : 10,
												rownumbers : false,
												multiselect : true,
												multiboxonly : true,
												jsonReader : {
													repeatitems : false,
													id : "0"
												},
												pager : '#pager2',
												rowList : [ 10, 15, 20 ],
												sortname : 'alarmcode',
												sortorder : 'desc',
												toppager : true,
												viewrecords : true,
												loadComplete : function() {
													var myGrid = $("#datatable");
													$("#cb_" + myGrid[0].id)
															.hide();
												},

											});
							$("#datatable").jqGrid("navGrid", "#pager2", {
								cloneToTop : true,
								/* search : false, */
								add : false,
								del : false,
								edit : false,
								searchfunc : searchCallthe, // (1) 点击添加按钮，则调用deleteTerminalAlarmDiary方法  
								searchtext : $.i18n.prop("btn.search"),
								alerttext : $.i18n.prop("param.minrow")
							// (3) 当未选中任何行而点击编辑、删除、查看按钮时，弹出的提示信息  
							});
						});
		//查询数据 
		var searchCallthe = function(row) {
			var dialogCode = dialog({
				title : $.i18n.prop('btn.search'),
				content : $('#searchMemberCardForm'),
				okValue : $.i18n.prop('btn.ok'),
				ok : function() {
					var alarmcode = document.getElementById("alarmcode_1").value;
					$
							.ajax({
								type : "POST",
								url : "${pageContext.request.contextPath}/callThePoliceLogController/selectTerminalAlarmDiary.do",
								data : {
									alarmcode : alarmcode
								},
								success : function(msg) {
									showMsg(msg.msg);
									submitForm();
									$('#searchMemberCardForm').clearForm(true);
								}
							});
				},
				cancelValue : $.i18n.prop('btn.cancel'),
				cancel : function() {
					$('#searchMemberCardForm').clearForm(true);
				}
			});
			dialogCode.width(450);
			dialogCode.showModal();
		};
		//删除数据 
		/* 		var deleteTerminalAlarmDiary = function(row) {
		 var col = $("#datatable").jqGrid('getRowData', row);
		 var num = col.alarmcode;
		 var d = dialog({
		 title : '提示',
		 content : '您确认想要删除吗？',
		 okValue : '确定',
		 ok : function() {
		 this.title('提交中…');
		 $
		 .ajax({
		 type : "post",
		 url : "${pageContext.request.contextPath}/callThePoliceLogController/delectTerminalAlarmDiary.do?id="
		 + num,
		 success : function(msg) {
		 showMsg(eval("(" + msg + ")").msg);
		 $("#datatable").trigger('reloadGrid');
		 setTimeout(function() {
		 }, 2500);

		 }
		 });
		 return true;
		 },
		 cancelValue : '取消',
		 cancel : function() {
		 }
		 });
		 d.width(200);
		 d.showModal();
		 }; */

		function showMsg(themsg) {//显示提示信息，1s后自动关闭
			var d = dialog({
				content : themsg
			});
			d.show();
			setTimeout(function() {
				d.close().remove();
			}, 1000);
		}
	</script>
</body>
</html>
