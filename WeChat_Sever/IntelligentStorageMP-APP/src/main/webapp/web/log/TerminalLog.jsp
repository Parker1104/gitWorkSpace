<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path;
	request.setAttribute("basePath", basePath);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title><spring:message code="TerminalLog.title.text" /></title>
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
<body>
	<div class="content" align="center" style="display: none;">
		<!-- 查询操作日志 -->
		<form id="searchMemberCardForm"
			action="${pageContext.request.contextPath}/terminalLogController/selectBox.do"
			method="post">
			<div class="list">
				<ul>
					<li id="s"><spring:message code="TerminalLog.open" />：<select name="open" id="open">
					        <option value="" selected="selected"><spring:message code="TerminalLog.prompt1.information" /></option>
							<option value="0"><spring:message code="TerminalLog.prompt2.information" /></option>
							<option value="1"><spring:message code="TerminalLog.prompt3.information" /></option>
					</select></li>
				</ul>
				<!-- <ul style="float:right;margin-right:-175px;">   				
				<li style="margin-left: 50px;"><input onclick="submitForm()"
				   value="查询" type="button" style="width: 88px; font-size: 14px;" ></li>
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
				if (document.activeElement.id == "open") {
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
							/* var a = '${account.areacode}'; */
							var tableWidth = $("#datatable").parent()
									.innerWidth() + 25;
							$("#datatable")
									.jqGrid(
											{
												url : '${pageContext.request.contextPath}/terminalLogController/selectBox.do',
												datatype : "json",
												mtype : "POST",
												width : document.body.offsetWidth - 60,
												height : window.screen.availHeight - 325,
												//autowidth:true,
												editable : false,
												shrinkToFit : true,
												colNames : [ $.i18n.prop('TerminalLog.col1.text'),$.i18n.prop('TerminalLog.col2.text'),
													         $.i18n.prop('TerminalLog.col3.text'),$.i18n.prop('TerminalLog.col4.text'),
													         $.i18n.prop('TerminalLog.col5.text'),$.i18n.prop('TerminalLog.col6.text'),
													         $.i18n.prop('TerminalLog.col7.text'),$.i18n.prop('TerminalLog.col8.text'),
													         $.i18n.prop('TerminalLog.col9.text') ],
												colModel : [
														{
															name : "terminalID",
															index : "terminalID",
															align : 'center',
															width : 180,
															sortable : false,
															hidden : true
														},
														{
															name : "boxID",
															index : "boxID",
															align : 'center',
															width : 180,
															sortable : false,
															hidden : true
														},
														{
															name : "boxtypecode",
															index : "boxtypecode",
															align : 'center',
															width : 180,
															sortable : false
														},
														{
															name : "dispalyname",
															index : "dispalyname",
															align : 'center',
															width : 180,
															sortable : false
														},
														{
															name : "oneBoxMoreCard",
															index : "oneBoxMoreCard",
															align : 'center',
															width : 180,
															sortable : false,
															hidden : true
														},
														{
															name : "fixedBox",
															index : "fixedBox",
															align : 'center',
															width : 180,
															sortable : false,
															hidden : true
														},
														{
															name : "article",
															index : "article",
															align : 'center',
															width : 180,
															sortable : false,
															formatter : function(
																	el,
																	options,
																	rowData) {
																var article = rowData.article;
																if (article == 0) {
																	article = "<spring:message code='TerminalLog.prompt4.information'/>";
																	return article;
																} else if (article == 1) {
																	article = "<spring:message code='TerminalLog.prompt5.information'/>";
																	return article;
																} else if (article == 2) {
																	article = "<spring:message code='TerminalLog.prompt6.information'/>";
																	return article;
																}
															}
														},
														{
															name : "open",
															index : "open",
															align : 'center',
															width : 180,
															sortable : false,
															formatter : function(
																	el,
																	options,
																	rowData) {
																var open = rowData.open;
																if (open == 0) {
																	open = "<spring:message code='TerminalLog.prompt2.information'/>";
																	return open;
																} else if (open == 1) {
																	open = "<spring:message code='TerminalLog.prompt3.information'/>";
																	return open;
																}
															}
														},
														{
															name : "status",
															index : "status",
															align : 'center',
															width : 180,
															sortable : false,
															formatter : function(
																	el,
																	options,
																	rowData) {
																var status = rowData.status;
																if (status == 0) {
																	status = "<spring:message code='TerminalLog.prompt4.information'/>";
																	return status;
																} else if (status == 1) {
																	status = "<spring:message code='TerminalLog.prompt5.information'/>";
																	return status;
																} else if (status == 2) {
																	status = "<spring:message code='TerminalLog.prompt6.information'/>";
																	return status;
																}

															}
														}, ],
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
												sortname : 'terminalID',
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
								searchfunc : searchTerminalLog, // (1) 点击添加按钮，则调用addMakeCard方法  
								searchtext : $.i18n.prop("btn.search"),
								alerttext : $.i18n.prop("param.minrow")
							// (2) 当未选中任何行而点击编辑、删除、查看按钮时，弹出的提示信息  
							});
						});
		//查询数据 
		var searchTerminalLog = function(row) {
			var dialogCode = dialog({
				title : $.i18n.prop('btn.search'),
				content : $('#searchMemberCardForm'),
				okValue : $.i18n.prop('btn.ok'),
				ok : function() {
					var open = document.getElementById("open").value;
					$
							.ajax({
								type : "POST",
								url : "${pageContext.request.contextPath}/terminalLogController/selectBox.do",
								data : {
									open : open
								},
								success : function(msg) {
									showMsg(msg.msg);
									submitForm();
									/* $('#searchMemberCardForm').clearForm(true); */
									 document.getElementById("open").value = "";
								}
							});
				},
				cancelValue : $.i18n.prop('btn.cancel'),
				cancel : function() {
					/* $('#searchMemberCardForm').clearForm(true); */
					document.getElementById("open").value = "";
				}
			});
			dialogCode.width(450);
			dialogCode.showModal();
		};
		//删除 
		/* function delRepository(accountcode) {
			alert(accountcode);
			var d = dialog({
				title : '提示',
				content : '您确认想要删除吗？',
				okValue : '确定',
				ok : function() {
					this.title('提交中…');
					$
							.ajax({
								type : "POST",
								url : "${pageContext.request.contextPath}/operateLogInquireController/delectOperationLogInpquire.do",
								data : {
									accountcode : accountcode
								},
								success : function(msg) {
									d.close();
									d = null;
									d = dialog({
										content : msg.msg
									});
									d.width(150);
									d.show();
									$("#datatable").trigger('reloadGrid');
									setTimeout(function() {
										d.close().remove();
									}, 2500);

								}
							});
				},
				cancelValue : '取消',
				cancel : function() {
				}
			});
			d.width(150);
			d.show();
		} */

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
