<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title><spring:message code="EquipmentRecord.title.text" /></title>
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
		<form id="searchMemberCardForm"
			action="${pageContext.request.contextPath}/equipmentRecordController/selectEquipmentRecord.do"
			method="post">
			<div class="list">
				<ul>
					<li id="s">操作类型：<select name="type" id="type">
							<option value="" selected="selected">请选择</option>
							<option value="1">开箱</option>
							<option value="2">清箱</option>
							<option value="3">锁箱</option>
							<option value="4">解锁</option>			
					</select></li>
					<!-- <li>日期：<input name="date" id="date" type="text" /></li>-->
				</ul>
				<!-- <ul style="float:right;margin-right:-175px;">   				
				<li style="margin-left: 50px;"><input onclick="submitForm()"
				   value="查询" type="button" style="width: 88px; font-size: 14px;" /></li>
			 </ul> -->
			</div>
		</form>
	</div>


	<div class="tab">
		<table id="datatable" style="width: 90%"></table>
		<div id="pager2"></div>
	</div>
	<script type="text/javascript">
		/* 查询 当摁下 enter键后执行 button 点击事件 */
		document.onkeydown = function(event) {
			var e = event || window.event
					|| arguments.callee.caller.arguments[0];
			if (e && e.keyCode == 13) { // enter 键
				if (document.activeElement.id == "type") {
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
												url : '${pageContext.request.contextPath}/equipmentRecordController/selectEquipmentRecord.do',
												datatype : "json",
												mtype : "POST",
												width : document.body.offsetWidth - 60,
												height : window.screen.availHeight - 325,
												//autowidth:true,
												editable : false,
												shrinkToFit : true,
												colNames : [ $.i18n.prop('EquipmentRecord.table.col1.text'),  $.i18n.prop('EquipmentRecord.table.col2.text'),
													         $.i18n.prop('EquipmentRecord.table.col3.text'),  $.i18n.prop('EquipmentRecord.table.col4.text'),  
													         $.i18n.prop('EquipmentRecord.table.col5.text') ],
												colModel : [

														{
															name : "terminalid",
															index : "terminalid",
															align : 'center',
															width : 180,
															sortable : false
														},
														{
															name : "boxid",
															index : "boxid",
															align : 'center',
															width : 180,
															sortable : false
														},
														{
															name : "content",
															index : "content",
															align : 'center',
															width : 180,
															sortable : false
														},
														{
															name : "accountcode",
															index : "accountcode",
															align : 'center',
															width : 180,
															sortable : false,
															formatter : function(
																	el,
																	options,
																	rowData) {
																return rowData.accountEntity.accountname;
															}
														},
														{
															name : "date",
															index : 'date',
															align : 'center',
															width : 180,
															sortable : false,
															formatter : function(
																	el,
																	options,
																	rowData) {
																var t = rowData.date;
																var d = new Date();
																d.setTime(t);
																var s = d
																		.Format("yyyy-MM-dd  hh:mm:ss");
																return s;
															}
														} ],
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
												sortname : 'terminalid',
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
								searchfunc : searchEquipment, // (1) 点击添加按钮，则调用addMakeCard方法  
								searchtext : $.i18n.prop("btn.search"),
								alerttext : $.i18n.prop("param.minrow")
							// (3) 当未选中任何行而点击编辑、删除、查看按钮时，弹出的提示信息  							
							});
						});

		//查询 
		var searchEquipment = function(row) {
			var dialogCode = dialog({
				title : $.i18n.prop('btn.search'),
				content : $('#searchMemberCardForm'),
				okValue : $.i18n.prop('btn.ok'),
				ok : function() {
					var type = document.getElementById("type").value;
					$
							.ajax({
								type : "POST",
								url : "${pageContext.request.contextPath}/equipmentRecordController/selectEquipmentRecord.do",
								data : {type:type},
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
