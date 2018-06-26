<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path;
	request.setAttribute("basePath", basePath);
%>
<head>
<title>操作日志查询</title>
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
	<div class="content" style="display: none;">
		<!-- 查询操作日志 -->
		<form id="searchMemberCardForm"
			action="${pageContext.request.contextPath}/operateLogInquireController/selectOperationLogInpquire"
			method="post">
			<div class="list">
				<ul>
					<li id="s"><spring:message code="operateLogInquire.date" />：<input
						name="date_1" id="date_1" class="Wdate" class="validate[required]"
						onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'date_2\')}'})" />
						- <input name="date_2" id="date_2" class="Wdate"
						class="validate[required]"
						onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'date_1\')}'})" />
					</li>
				</ul>
			</div>
		</form>
	</div>

	<!-- 显示操作日志 -->
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
				if (document.activeElement.id == "date_1") {
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
		function formToJson(id) {//把form表单的所有数据变成json对象
			var formArray = $('#' + id).serializeArray();//数组里面首先放入充值记录表的内容
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
									.innerWidth();
							$("#datatable")
									.jqGrid(
											{
												url : '${pageContext.request.contextPath}/operateLogInquireController/selectOperationLogInpquire',
												datatype : "json",
												mtype : "POST",
												width : document.body.offsetWidth - 60,
												height : window.screen.availHeight - 325,
												//autowidth:true,
												editable : false,
												shrinkToFit : true,
												colNames : [
														$.i18n
																.prop('operateLogInquire.col1.text'),
														$.i18n
																.prop('operateLogInquire.col2.text'),
														$.i18n
																.prop('operateLogInquire.col3.text'),
														'',
														$.i18n
																.prop('operateLogInquire.col4.text') ],
												colModel : [
														{
															name : "modlename",
															index : "modlename",
															align : 'center',
															width : 100,
															sortable : false
														},
														{
															name : "description",
															index : "description",
															align : 'center',
															width : 350,
															sortable : false
														},
														{
															name : "date",
															index : 'date',
															align : 'center',
															width : 150,
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
														},
														{
															name : "accountcode",
															index : "accountcode",
															align : 'center',
															width : 100,
															sortable : false,
															hidden : true,
															formatter : function(
																	el,
																	options,
																	rowData) {
																return rowData.accountEntity.accountcode;
															}
														},
														{
															name : "accountname",
															index : "accountname",
															align : 'center',
															width : 100,
															sortable : false,
															formatter : function(
																	el,
																	options,
																	rowData) {
																return rowData.accountEntity.accountname;
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
												sortname : 'accountcode',
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
								searchfunc : searchBoxType, // (2) 点击添加按钮，则调用addMakeCard方法  
								searchtext : $.i18n.prop("btn.search"),
								alerttext : $.i18n.prop("param.minrow")
							// (4) 当未选中任何行而点击编辑、删除、查看按钮时，弹出的提示信息  
							});
						});

		//查询数据 
		var searchBoxType = function(row) {
			var dialogCode = dialog({
				title : '<spring:message code="operateLogInquire.prompt1.information" />',
				content : $('#searchMemberCardForm'),
				okValue : $.i18n.prop('btn.ok'),
				ok : function() {
					var date_1 = document.getElementById("date_1").value;
					var date_2 = document.getElementById("date_2").value;
					$
							.ajax({
								type : "POST",
								url : "${pageContext.request.contextPath}/operateLogInquireController/selectOperationLogInpquire",
								data : {
									date_1 : date_1,
									date_2 : date_2
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
			dialogCode.width(500);
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
