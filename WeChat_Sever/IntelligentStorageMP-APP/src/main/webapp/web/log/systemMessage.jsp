<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title><spring:message code="systemMessage.title.text" /></title>
<%@ include file="../../common/head.jsp"%>
<%@ include file="../../common/theme.jsp"%>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/js/plug/zTree/css/demo.css"
	type="text/css" />
<style>
td#datatable_toppager_center {
	display: none;
}
</style>
</head>
<body>

	<div class="tab">
		<table id="datatable" style="width: 90%"></table>
		<div id="pager2"></div>
	</div>

	<script type="text/javascript">
		$(document)
				.ready(
						function() {
							var tableWidth = $("#datatable").parent()
									.innerWidth();
							$("#datatable")
									.jqGrid(
											{
												url : '${pageContext.request.contextPath}/systemMessageController/selectSystemMessage.do',
												datatype : "json",
												mtype : "POST",
												width : document.body.offsetWidth - 60,
												height : window.screen.availHeight - 325,
												//autowidth:true,
												editable : false,
												shrinkToFit : true,
												colNames : [ '', $.i18n.prop('systemMessage.col1.text'), $.i18n.prop('systemMessage.col2.text'),
													         $.i18n.prop('systemMessage.col3.text') ],
												colModel : [
														{
															name : "systemid",
															index : "systemid",
															align : 'center',
															width : 100,
															sortable : false,
															hidden : true
														},
														{
															name : "lastmodifytime",
															index : "lastmodifytime",
															align : 'center',
															width : 100,
															sortable : false,
															formatter : function(
																	el,
																	options,
																	rowData) {
																var t = rowData.lastmodifytime;
																var d = new Date();
																d.setTime(t);
																var s = d
																		.Format("yyyy-MM-dd  hh:mm:ss");
																return s;
															}
														},
														{
															name : "softwareversion",
															index : "softwareversion",
															align : 'center',
															width : 100,
															sortable : false
														},
														{
															name : "updatecontent",
															index : 'updatecontent',
															align : 'center',
															width : 400,
															sortable : false
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
												sortname : 'systemid',
												sortorder : 'desc',
												toppager : true,
												viewrecords : true,
												loadComplete : function() {
													var myGrid = $("#datatable");
													$("#cb_" + myGrid[0].id)
															.hide();
												},
											});
							/* $("#datatable").jqGrid("navGrid", "#pager2", {
								cloneToTop : true,
								search : false,
								add:false, 			
							    edit:false, 
								addfunc : delectSystemMessage, // (1) 点击添加按钮，则调用addMakeCard方法  
								addtext : "新增",
								editfunc : delectSystemMessage, // (2) 点击添加按钮，则调用addMakeCard方法  
								edittext : "修改",
								delfunc : delectSystemMessage, // (3) 点击添加按钮，则调用delRepository方法   
								deltext : "删除",
								alerttext : "请选择需要操作的数据行!" // (4) 当未选中任何行而点击编辑、删除、查看按钮时，弹出的提示信息  
							}); */
						});
	</script>
</body>
</html>