<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path;
	request.setAttribute("basePath", basePath);
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title><spring:message code="feeScale.title.text" /></title>
<%@ include file="../../common/head.jsp"%>
<%@ include file="../../common/theme.jsp"%>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/js/plug/zTree/css/demo.css"
	type="text/css" />
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

td#detail_toppager_center {
	display: none;
}
</style>
</head>
<body style="overflow-x: hidden">
	<!-- <div style="margin-left: 15px;">
  <input type="button" value="添加收费信息" onclick="addPaymentMaster()" style="width: 100px; font-size: 14px; " />
</div> -->
	<div class="content" align="center" style="display: none;">
		<form id="searchMemberCardForm"
			action="${pageContext.request.contextPath}/feeScaleController/selectProgramElement"
			method="post">
			<div class="list">
				<ul>
					<%-- <li><spring:message code="feeScale.PaymentMaster.ZchargeCode" />：<input
						type="text" name="chargecode" id="chargecode" /></li>
						<spring:message
							code="feeScale.PaymentMaster.ZusedState" />： <select
						name="usedState" id="usedState" onblur="ZusedStateOnblue()">
							<option value=""><spring:message
									code="account.table.col11.text" /></option>
							<c:forEach var="d" items="${datadic2}">
								<option value="<c:out value='${d.dictcode}'/>">
									<c:out value='${d.dictname}' /></option>
							</c:forEach>
					</select></li> --%>
					<li>
					<spring:message
							code="feeScale.PaymentMaster.ZchargeCode" />：<select
						name="chargecode" id="chargecode">
							<option value=""><spring:message
									code="account.table.col11.text" /></option>
							<c:forEach var="d" items="${datadic1}">
								<option value="<c:out value='${d.dictcode}'/>">
									<c:out value='${d.dictname}' /></option>
							</c:forEach>
					</select>
					</li>
				</ul>
				<!-- <ul style="float:right;margin-right:-150px;">   
				   <li style="margin-left: 50px;"><input type="button" id="selectBoxtype" value="查询" onclick="submitForm()" style="width: 88px; font-size: 14px; " />	</li>
			       <li style="margin-left: 50px;"> <input type="button" value="添加收费信息" onclick="addMakeCard_1()" style="width: 100px; font-size: 14px; " /></li>
			    </ul>	 -->
			</div>
		</form>
	</div>
	<div class="cost" style="display: none;" align="center">
		<form action="" id="fee_1" method="post">
			<div class="list" align="center">
				<ul>
					<li style="width: 305px; display: none;"><spring:message
							code="feeScale.PaymentDetail.masterID" />：<select
						name="masterID" id="masterID">
							<option value="" selected="selected"><spring:message
									code="account.table.col11.text" /></option>
							<c:forEach var="p" items="${PaymentMaster}">
								<option value="${p.masterid}">
									<spring:message code="feeScale.PaymentMaster.ZchargeCode" />${p.chargecode}</option>
							</c:forEach>
					</select><span style="color: red">*</span></li>
					<li style="width: 305px;"><spring:message
							code="feeScale.PaymentDetail.boxTypeCode" />：<select
						class="boxTypeCode" name="boxTypeCode" id="boxTypeCode">
							<option value="" selected="selected"><spring:message
									code="account.table.col11.text" /></option>
							<c:forEach var="b" items="${BoxType}">
								<option value="${b.boxtypecode}">${b.boxtypename}</option>
							</c:forEach>
					</select><span style="color: red">*</span></li>
					<li style="width: 305px;"><spring:message
							code="feeScale.PaymentDetail.feeValue" />： <input type="text"
						name="feeValue" id="feeValue"
						onkeyup="if(isNaN(value))execCommand('undo')"
						onafterpaste="if(isNaN(value))execCommand('undo')" /><span
						style="color: red">*</span></li>
					<li style="width: 305px;"><spring:message
							code="feeScale.PaymentDetail.discountRate" />： <input
						name="discountRate" id="discountRate" type="text" value="100" /><span
						style="color: red">*</span></li>
					<li><spring:message code="feeScale.PaymentDetail.memo" />：
					 <!-- <input name="memo" id="memo" type="text" /> -->
					   <textarea  name="memo" id="memo"  cols="40" rows="3"  style="width: 200px;
	                    resize:none;  border: 1px solid #ccc; border-radius: 3px;"></textarea> 
					</li>
					<li style="display: none;"><input name="detailID"
						id="detailID" type="hidden" /></li>
				</ul>
			</div>
		</form>
	</div>
	<div class="content" style="display: none;">
		<form
			action="${pageContext.request.contextPath}/feeScaleController/saveOrUpdatePaymentMaster"
			id="fee_2" method="post">
			<div class="list">
				<ul>
					<li style="width: 305px;"><spring:message
							code="feeScale.PaymentMaster.ZchargeCode" />：<select
						name="ZchargeCode" id="ZchargeCode">
							<option value=""><spring:message
									code="account.table.col11.text" /></option>
							<c:forEach var="d" items="${datadic1}">
								<option value="<c:out value='${d.dictcode}'/>">
									<c:out value='${d.dictname}' /></option>
							</c:forEach>
					</select><span style="color: red">*</span></li>
					<!-- <li style="width: 305px;"> 应付费用： <input type="text" name="ZfeeValue" id="ZfeeValue"  maxlength="5" /></li>	 -->
					<li style="width: 305px;"><spring:message
							code="feeScale.PaymentMaster.ZusedState" />： <select
						name="ZusedState" id="ZusedState" onblur="ZusedStateOnblue()">
							<option value=""><spring:message
									code="account.table.col11.text" /></option>
							<c:forEach var="d" items="${datadic2}">
								<option value="<c:out value='${d.dictcode}'/>">
									<c:out value='${d.dictname}' /></option>
							</c:forEach>
					</select><span style="color: red">*</span></li>
					<li style="width: 305px;"><spring:message
							code="feeScale.PaymentMaster.ZstartTime" />：<input type="text"
						name="ZstartTime" id="ZstartTime" value="0" /><span
						style="color: red">*</span></li>
					<li style="width: 381px;"><spring:message
							code="feeScale.PaymentMaster.ZendTime" />： <input type="text"
						name="ZendTime" id="ZendTime" value="" onkeyup="this.value=this.value.replace(/\D/g,'')"
						onafterpaste="this.value=this.value.replace(/\D/g,'')" maxlength="4"/>
						<span style="color: red">*<spring:message code="btn.prompting" /></span></li>
					<li style="width: 377px;"><spring:message
							code="feeScale.PaymentMaster.ZtimeValue" />：<input
						name="ZtimeValue" id="ZtimeValue" type="text"
						onkeyup="this.value=this.value.replace(/\D/g,'')"
						onafterpaste="this.value=this.value.replace(/\D/g,'')" /><span
						style="color: red">*<spring:message code="btn.prompting" /></span></li>
					<li style="width: 377px;"><spring:message
							code="feeScale.PaymentMaster.ZdiscountRate" />： <input
						name="ZdiscountRate" id="ZdiscountRate" type="text"
						onkeyup="this.value=this.value.replace(/\D/g,'')"
						onafterpaste="this.value=this.value.replace(/\D/g,'')" value="100" /><span
						style="color: red">*<spring:message code="btn.prompting" /></span></li>
					<li style="width: 310px;"><spring:message
							code="feeScale.PaymentMaster.Zmemo" />： 
							<!-- <input name="Zmemo" id="Zmemo" type="text" /> -->
						<textarea  name="Zmemo" id="Zmemo"  cols="40" rows="3"  style="width: 200px;
	                    resize:none; border: 1px solid #ccc; border-radius: 3px;"></textarea> 
						<span style="color: red">*</span></li>
					<li style="display: none;"><input name="ZmasterID"
						id="ZmasterID" type="hidden" /></li>
					<li style="display: none;"><input name="ZorderNumber"
						id="ZorderNumber" type="text" /></li>
				</ul>
			</div>
		</form>
	</div>
	<div class="tab">
		<table id="datatable"></table>
		<div id="pager2"></div>
	</div>
	<div id="showCommoditys" class="list" style="display: none">
		<div style="width: 98%;">
			<table id="detail"></table>
			<div id="pager3"></div>
		</div>
	</div>
	<script type="text/javascript">
		/* 查询 当摁下 enter键后执行 button 点击事件 */
		document.onkeydown = function(event) {
			var e = event || window.event
					|| arguments.callee.caller.arguments[0];
			if (e && e.keyCode == 13) { // enter 键
				if (document.activeElement.id == "chargecode") {
					$(".ui-dialog-autofocus").trigger("click");
					return false;
				}
			}
		};
		//判断输入的值是否存在 
		function ZusedStateOnblue() {
			var chargeCode = document.getElementById("ZchargeCode").value;
			var usedState = document.getElementById("ZusedState").value;
			$
					.ajax({
						type : "POST",
						datatype : "json",
						data : {
							chargecode : chargeCode,
							usedstate : usedState
						},
						url : "${pageContext.request.contextPath}/feeScaleController/selectChargeCode",
						success : function(msg) {
							/* showMsg(eval("(" + msg + ")").msg); */
							if (msg == '[]') {
								document.getElementById("ZstartTime").value = "0";
								return false;
							} else {
								var dataObj = eval("(" + msg + ")");
								/* alert(chargeCode);alert(dataObj[0].chargecode); */
								if (dataObj == null || dataObj == "") {
									document.getElementById("ZstartTime").value = "0";
									return false;
								} else {
									var i = dataObj.length - 1;
									document.getElementById("ZstartTime").value = dataObj[i].endtime;
									return false;
								}
							}
						},
					});

		};
		function submitFormdetail() {
			$("#detail").jqGrid('setGridParam', {
				postData : formToJson("searchMemberCardForm"),
				page : 1
			}).trigger("reloadGrid");
		};
		function submitForm() {
			$("#datatable").jqGrid('setGridParam', {
				postData : formToJson("searchMemberCardForm"),
				page : 1
			}).trigger("reloadGrid");
		};
		function formToJson(id) {//把form表单的所有数据变成json对象
			var formArray = $('#' + id).serializeArray();//数组里面首先放入充值记录表的内容
			var formJson = new Object();
			$.each(formArray, function(i, n) {//把formarray转换为json
				if ((n.name != undefined && n.name != "")) {
					formJson[n.name] = n.value;
				}
			});
			return formJson;
		};
		$(document)
				.ready(
						function() {
							$(window).resize(
									function() {
										$("#datatable").setGridWidth(
												$(window).width() - 50);
									});
							var tableWidth = $("#datatable").parent()
									.innerWidth();
							$("#datatable")
									.jqGrid(
											{
												url : '${pageContext.request.contextPath}/feeScaleController/selectFeescalProgram',
												datatype : "json",
												mtype : "POST",
												width : document.body.offsetWidth - 60,
												height : window.screen.availHeight - 325,
												//autowidth:true,
												editable : false,
												shrinkToFit : true,
												colNames : [ $.i18n.prop('feeScale.PaymentMaster.table.col.text.masterid'), $.i18n.prop('feeScale.PaymentMaster.table.col.text.chargecode'),
													 $.i18n.prop('feeScale.PaymentMaster.table.col.text.chargecode'),  $.i18n.prop('feeScale.PaymentMaster.table.col.text.ordernumber'), 
													 $.i18n.prop('feeScale.PaymentMaster.table.col.text.starttime'),$.i18n.prop('feeScale.PaymentMaster.table.col.text.endtime'), 
													 $.i18n.prop('feeScale.PaymentMaster.table.col.text.usedstate'),$.i18n.prop('feeScale.PaymentMaster.table.col.text.usedstate'), 
													 $.i18n.prop('feeScale.PaymentMaster.table.col.text.timevalue'),$.i18n.prop('feeScale.PaymentMaster.table.col.text.discountrate'), 
													 $.i18n.prop('feeScale.PaymentMaster.table.col.text.makeopcode'),$.i18n.prop('feeScale.PaymentMaster.table.col.text.makedate'),  
													 $.i18n.prop('feeScale.PaymentMaster.table.col.text.lastmodifytime'),$.i18n.prop('feeScale.PaymentMaster.table.col.text.memo'),
													 $.i18n.prop('feeScale.PaymentMaster.table.col.text.operation') ],
												colModel : [
														{
															name : "masterid",
															index : 'masterid',
															align : 'center',
															width : 250,
															sortable : false,
															hidden : true
														},
														{
															name : "chargecode",
															index : 'chargecode',
															align : 'center',
															width : 200,
															sortable : false,
															hidden : true
														},
														{
															name : "dictname",
															index : "dictname",
															align : 'center',
															width : 250,
															sortable : false,
															formatter : function(
																	el,
																	options,
																	rowData) {
																return rowData.dictEntity.dictname;
															}
														},
														{
															name : "ordernumber",
															index : 'ordernumber',
															align : 'center',
															width : 200,
															sortable : false
														},
														{
															name : "starttime",
															index : 'starttime',
															align : 'center',
															width : 200,
															sortable : false
														},
														{
															name : "endtime",
															index : 'endtime',
															align : 'center',
															width : 200,
															sortable : false
														},
														{
															name : "usedstate",
															index : 'usedstate',
															align : 'center',
															width : 200,
															sortable : false,
															hidden : true,
														},
														{
															name : "dictname1",
															index : "dictname1",
															align : 'center',
															width : 250,
															sortable : false,
															formatter : function(
																	el,
																	options,
																	rowData) {
																return rowData.dictEntity1.dictname;
															}
														},
														{
															name : "timevalue",
															index : 'timevalue',
															align : 'center',
															width : 200,
															sortable : false
														},
														{
															name : "discountrate",
															index : 'discountrate',
															align : 'center',
															width : 200,
															sortable : false
														},
														{
															name : "makeopcode",
															index : 'makeopcode',
															align : 'center',
															width : 200,
															sortable : false
														},
														{
															name : "makedate",
															index : 'makedate',
															align : 'center',
															width : 200,
															sortable : false,
															hidden : true
															/* , formatter : function(
																	el,
																	options,
																	rowData) {
																var t = rowData.makedate;
																var d = new Date();
																d.setTime(t);
																var s = d
																		.Format("yyyy-MM-dd  hh:mm:ss");
																return s;
															} */
														},
														{
															name : "lastmodifytime",
															index : 'lastmodifytime',
															align : 'center',
															width : 200,
															sortable : false,
															hidden : true
															/* ,formatter : function(
																	el,
																	options,
																	rowData) {
																var t = rowData.lastmodifytime;
																var d = new Date();
																d.setTime(t);
																var s = d
																		.Format("yyyy-MM-dd  hh:mm:ss");
																return s;
															} */
														},
														{
															name : "memo",
															index : 'memo',
															align : 'center',
															width : 200,
															sortable : false
														},
														{
															name : "",
															index : '',
															align : 'center',
															width : 200,
															sortable : false,
															formatter : function(
																	el,
																	options,
																	rowData) {
																var d = '<input type="button" value="<spring:message code='feeScale.PaymentMaster.prompt15.information' />" onclick="viewDetail(\''
																		+ rowData.masterid
																		+ '\')" style="width:80px;float:right;margin-right:10px;"/>';
																return d;
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
												sortname : 'masterid',
												sortorder : 'desc',
												toppager : true,
												viewrecords : true,
												loadComplete : function() {
													var myGrid = $("#datatable");
													$("#cb_" + myGrid[0].id)
															.hide();
												},
												ondblClickRow : function(rowid) {
													RowData = jQuery(this)
															.jqGrid(
																	"getRowData",
																	rowid);
													if (RowData.masterid) {
														selectMakeCard(
																"<spring:message code='account.table.col13.text' />",
																rowid);
													}
												},
											});
							$("#datatable").jqGrid("navGrid", "#pager2", {
								cloneToTop : true,
								/* search : false, */
								/* edit:false, */
								addfunc : addPaymentMaster, // (1) 点击添加按钮，则调用addMakeCard方法    
								addtext : $.i18n.prop("btn.add"),
								editfunc : updatePaymentMaster, // (2) 点击添加按钮，则调用updateMakeCard方法  
								edittext : $.i18n.prop("btn.update"),
								delfunc : delectProgramElement, // (3) 点击添加按钮，则调用delRepository方法   
								deltext : $.i18n.prop("btn.delete"),
								searchfunc : searchProgramElement,
								searchtext : $.i18n.prop("btn.search"),
								alerttext : $.i18n.prop("param.minrow")
							// (5) 当未选中任何行而点击编辑、删除、查看按钮时，弹出的提示信息  
							});
						});

		function showCommodity(id, url, tableWidth, fun) {//初始化商品列表,当url为空表示把选择的商品赋值到该表格
			return $("#" + id)
					.jqGrid(
							{
								/*  url:'${pageContext.request.contextPath}/feeScaleController/selectProgram.do', */
								url : url,
								datatype : "json",
								mtype : "POST",
								width : document.body.offsetWidth - 550,
								height : window.screen.availHeight - 550,
								//autowidth:true,
								editable : false,
								shrinkToFit : true,
								colNames : [ $.i18n.prop('feeScale.PaymentDetail.table.col.text.masterid'), $.i18n.prop('feeScale.PaymentDetail.table.col.text.detailid'),
								             $.i18n.prop('feeScale.PaymentDetail.table.col.text.boxtypecode'), $.i18n.prop('feeScale.PaymentDetail.table.col.text.boxtypename'),
                                             $.i18n.prop('feeScale.PaymentDetail.table.col.text.feevalue'),$.i18n.prop('feeScale.PaymentDetail.table.col.text.discountrate'),
                                             $.i18n.prop('feeScale.PaymentDetail.table.col.text.memo'), $.i18n.prop('feeScale.PaymentDetail.table.col.text.operation') ],
								colModel : [
										{
											name : "masterid",
											index : 'masterid',
											align : 'center',
											width : 100,
											sortable : false,
											hidden : true
										},
										{
											name : "detailid",
											index : 'detailid',
											align : 'center',
											width : 100,
											sortable : false,
											hidden : true
										},
										{
											name : "boxtypecode",
											index : 'boxtypecode',
											align : 'center',
											width : 100,
											sortable : false,
											hidden : true
										},
										{
											name : "boxtypename",
											index : "boxtypename",
											align : 'center',
											width : 100,
											sortable : false,
											formatter : function(el, options,
													rowData) {
												return rowData.boxSizeEntity.boxtypename;
											}
										},
										{
											name : "feevalue",
											index : 'feevalue',
											align : 'center',
											width : 100,
											sortable : false
										},
										{
											name : "discountrate",
											index : 'discountrate',
											align : 'center',
											width : 100,
											sortable : false
										},
										{
											name : "memo",
											index : 'memo',
											align : 'center',
											width : 100,
											sortable : false
										},
										{
											name : "",
											index : '',
											align : 'center',
											width : 150,
											sortable : false,
											formatter : function(el, options,
													rowData) {
												var a = '<input type="button" value="<spring:message code='feeScale.PaymentDetail.prompt16.information' />" onclick="addMakeCard(\''
														+ rowData.masterid
														+ '\')" style="width:25%;float:right;margin-right:5%;"/>';
												var u = '<input type="button" value="<spring:message code='feeScale.PaymentDetail.prompt17.information' />" onclick="updateMakeCard(\''
														+ rowData.masterid
														+ '\',\''
														+ rowData.detailid
														+ '\')" style="width:25%;float:right;margin-right:5%;"/>';
												var d = '<input type="button" value="<spring:message code='feeScale.PaymentDetail.prompt18.information' />" onclick="delectProgram(\''
														+ rowData.masterid
														+ '\',\''
														+ rowData.detailid
														+ '\')" style="width:25%;float:right;margin-right:5%;"/>';
												return a + u + d;
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
								pager : '#pager3',
								rowList : [ 10, 15, 20 ],
								sortname : 'masterid',
								sortorder : 'desc',
								/* toppager : true, */
								viewrecords : true,
								loadComplete : function() {
									var myGrid = $("#" + id);
									$("#cb_" + myGrid[0].id).hide();
								},
								ondblClickRow : function(rowid) {
									RowData = jQuery(this).jqGrid("getRowData",
											rowid);
									if (RowData.masterid) {
										selectMakeCard(
												"<spring:message code='account.table.col13.text' />",
												rowid);
									}
								},

							});
		}
		var viewGrid = null;
		function viewDetail(masterid) {
			/* var col=$("#datatable").jqGrid('getRowData',row);  */
			/* alert(masterid); */
			$
					.ajax({
						type : "POST",
						url : "${pageContext.request.contextPath}/feeScaleController/selectProgramFeescale",
						data : {
							masterid : masterid
						},
						success : function(msg) {
							showMsg(eval("(" + msg + ")").msg);
							if (msg != '[]') {
								/* alert(".........."); */
								viewGrid = showCommodity(
										'detail',
										'${pageContext.request.contextPath}/feeScaleController/selectProgram?masterid='
												+ masterid, 800,
										function(data) {
											//toLoadInput('loss_form',data.bill);
											return data.rows;
										}).showCol('validperiod').showCol(
										'memo');

								openWin('<spring:message code="feeScale.prompt19.information" />', 'showCommoditys', 825);
							} else {
								/* alert(".........."); */
								document.getElementById("masterID").value = masterid;
								document.getElementById("masterID").disabled = true;
								dialogCode = dialog({
									title : $.i18n.prop('btn.add'),
									content : $('#fee_1'),
									okValue : $.i18n.prop('btn.ok'),
									ok : function() {
										var masterID = document
												.getElementById("masterID").value;
										var boxTypeCode = document
												.getElementById("boxTypeCode").value;
										var feeValue = document
												.getElementById("feeValue").value;
										var discountRate = document
												.getElementById("discountRate").value;
										if (masterID == '' || masterID == null) {
											showMsg("<spring:message code='feeScale.PaymentDetail.prompt11.information'/>");
											return false;
										} else if (boxTypeCode == ''
												|| boxTypeCode == null) {
											showMsg("<spring:message code='feeScale.PaymentDetail.prompt12.information'/>");
											return false;
										} else if (feeValue == ''
												|| feeValue == null) {
											showMsg("<spring:message code='feeScale.PaymentDetail.prompt13.information'/>");
											return false;
										} else if (discountRate == ''
												|| discountRate == null) {
											showMsg("<spring:message code='feeScale.PaymentDetail.prompt14.information'/>");
											return false;
										} else {
											$
													.ajax({
														type : "POST",
														url : "${pageContext.request.contextPath}/feeScaleController/saveOrUpdateProgramElement",
														data : {
															masterid : $(
																	"#masterID")
																	.val(),
															detailid : $(
																	"#detailID")
																	.val(),
															boxtypecode : $(
																	"#boxTypeCode")
																	.val(),
															feevalue : $(
																	"#feeValue")
																	.val(),
															discountrate : $(
																	"#discountRate")
																	.val(),
															memo : $("#memo")
																	.val()
														},
														success : function(data) {
															var dataObj = eval("("
																	+ data
																	+ ")");
															if (dataObj.msg == "<spring:message code='feeScale.prompt20.information'/>") {
																showMsg("<spring:message code='feeScale.prompt20.information'/>");
															} else {
																showMsg("<spring:message code='feeScale.prompt21.information'/>");
															}

														},
													});
										}
									},
									cancelValue : $.i18n.prop('btn.cancel'),
									cancel : function() {
										/* $('#fee_1').clearForm(true); */
									}
								});
								dialogCode.width(400);
								dialogCode.height(200);
								dialogCode.showModal();
							}

						},
					});

		};

		//查询数据 
		var searchProgramElement = function(row) {
			var dialogCode = dialog({
				title : $.i18n.prop('btn.search'),
				content : $('#searchMemberCardForm'),
				okValue : $.i18n.prop('btn.ok'),
				ok : function() {
					var chargecode = document.getElementById("chargecode").value;
					$
							.ajax({
								type : "POST",
								url : "${pageContext.request.contextPath}/feeScaleController/selectFeescalProgram",
								data : {
									chargecode : chargecode
								},
								success : function(msg) {
									showMsg($.i18n.prop('tip.success'));
									submitForm();
									document.getElementById("chargecode").value = "";
								}
							});
				},
				cancelValue : $.i18n.prop('btn.cancel'),
				cancel : function() {
					/* $('#searchMemberCardForm').clearForm(true); */
				}
			});
			dialogCode.width(350);
			dialogCode.showModal();
		};
		//删除副表信息
		function delectProgram(masterid, detailid) {
			var dialogCode = dialog({
				  title : $.i18n.prop('tip.title'),
			      content : $.i18n.prop('tip.confirm.delete'),
			      okValue : $.i18n.prop('btn.ok'),
			      ok : function() {
			      $.ajax({
						type : "POST",
						url : "${pageContext.request.contextPath}/feeScaleController/delectDetail",
						data : {
							masterid : masterid,
							detailid : detailid
						},
						success : function(msg) {
							$("#detail").trigger('reloadGrid');
							showMsg("<spring:message code='feeScale.prompt22.information'/>");
						}
					});
				},
				cancelValue : $.i18n.prop('btn.cancel'),
				cancel : function() {
					/* $('#searchMemberCardForm').clearForm(true); */
				}
			});
			dialogCode.width(350);
			dialogCode.showModal();
		};
		//删除数据 
		var delectProgramElement = function(row) {
			var col = $("#datatable").jqGrid('getRowData', row);
			//查看业务类型收费有引用 
			$
					.ajax({
						type : "POST",
						url : "${pageContext.request.contextPath}/feeScaleController/delect",
						data : {
							masterid : col.masterid
						},
						success : function(msg) {
							/* showMsg(eval("(" + msg + ")").msg); */
							if (msg == '[]') {
								$
										.ajax({
											type : "POST",
											url : "${pageContext.request.contextPath}/feeScaleController/selectProgramFeescale",
											data : {
												masterid : col.masterid
											},
											success : function(msg) {
												if (msg == '[]') {
													var dialogCode = dialog({
														title : $.i18n
																.prop('tip.title'),
														content : $.i18n
																.prop('tip.confirm.delete'),
														okValue : $.i18n
																.prop('btn.ok'),
														ok : function() {
															/* alert(col.masterid); */
															this
																	.title($.i18n
																			.prop('tip.loading'));
															$
																	.ajax({
																		type : "post",
																		url : "${pageContext.request.contextPath}/feeScaleController/delectProgramElement",
																		data : {
																			masterid : col.masterid
																		},
																		dataType : 'json',//JSON.stringify(
																		success : function(
																				msg) {
																			$(
																					"#datatable")
																					.trigger(
																							'reloadGrid');
																			showMsg("<spring:message code='feeScale.PaymentDetail.prompt1.information'/>");
																		},
																		error : function(
																				XMLHttpRequest,
																				textStatus,
																				errorThrown) {
																			showMsg("<spring:message code='feeScale.PaymentDetail.prompt2.information'/>");
																		}
																	});
															return true;
														},
														cancelValue : $.i18n
																.prop('btn.cancel'),
														cancel : function() {
														}
													});
													dialogCode.width(150);
													dialogCode.showModal();
												} else {
													showMsg("<spring:message code='feeScale.prompt23.information'/>"); 
													return false;
												}
											}
										});
							} else {
								showMsg("<spring:message code='feeScale.prompt24.information'/>"); 
								return false;
							}
						}
					});
		};
		//添加  数据  （主表） 
		var dialogCode = null;
		var addPaymentMaster = function(row) { //弹出修改和添加原因的窗口    
			document.getElementById("ZmasterID").disabled = false;
			document.getElementById("ZchargeCode").disabled = false;
			document.getElementById("ZstartTime").disabled = true;
			document.getElementById("ZendTime").disabled = false;
			document.getElementById("ZusedState").disabled = false;
			document.getElementById("ZtimeValue").disabled = false;
			document.getElementById("ZdiscountRate").disabled = false;
			document.getElementById("Zmemo").disabled = false;
			dialogCode = dialog({
				title : $.i18n.prop('btn.add'),
				content : $('#fee_2'),
				okValue : $.i18n.prop('btn.ok'),
				ok : function() {
					var ZchargeCode = document.getElementById("ZchargeCode").value;
					var ZstartTime = document.getElementById("ZstartTime").value;
					var ZendTime = document.getElementById("ZendTime").value;
					var ZusedState = document.getElementById("ZusedState").value;
					var ZtimeValue = document.getElementById("ZtimeValue").value;
					var ZdiscountRate = document
							.getElementById("ZdiscountRate").value;
					var Zmemo = document.getElementById("Zmemo").value;
					if (ZchargeCode == '' || ZchargeCode == null) {
						showMsg("<spring:message code='feeScale.PaymentMaster.prompt3.information'/>");
						return false;
					} else if (ZstartTime == '' || ZstartTime == null) {
						showMsg("<spring:message code='feeScale.PaymentMaster.prompt5.information'/>");
						return false;
					} else if (ZendTime == '' || ZendTime == null) {
						showMsg("<spring:message code='feeScale.PaymentMaster.prompt6.information'/>");
						return false;
					} else if (parseInt(ZendTime) < parseInt(ZstartTime)) {
						showMsg("<spring:message code='feeScale.PaymentMaster.prompt25.information'/>");
						return false;
					} else if (ZusedState == '' || ZusedState == null) {
						showMsg("<spring:message code='feeScale.PaymentMaster.prompt7.information'/>");
						return false;
					} else if (ZtimeValue == '' || ZtimeValue == null) {
						showMsg("<spring:message code='feeScale.PaymentMaster.prompt8.information'/>");
						return false;
					} else if (ZdiscountRate == '' || ZdiscountRate == null) {
						showMsg("<spring:message code='feeScale.PaymentMaster.prompt9.information'/>");
						return false;
					} else if (Zmemo == '' || Zmemo == null) {
						showMsg("<spring:message code='feeScale.PaymentMaster.prompt10.information'/>");
						return false;
					} else {
						$
								.ajax({
									type : "POST",
									url : "${pageContext.request.contextPath}/feeScaleController/saveOrUpdatePaymentMaster",
									data : {
										masterid : $("#ZmasterID").val(),
										chargecode : $("#ZchargeCode").val(),
										ordernumber : $("#ZorderNumber").val(),
										usedstate : $("#ZusedState").val(),
										timevalue : $("#ZtimeValue").val(),
										starttime : $("#ZstartTime").val(),
										endtime : $("#ZendTime").val(),
										discountrate : $("#ZdiscountRate")
												.val(),
										memo : $("#Zmemo").val()
									},
									success : function(data) {
										showMsg(eval("(" + data + ")").msg);
										submitForm();
										/* location.href = location.href; */
										document.getElementById("ZchargeCode").value = '';
										document.getElementById("ZstartTime").value = '0';
										document.getElementById("ZendTime").value = '';
										document.getElementById("ZusedState").value = '';
										document.getElementById("ZtimeValue").value = '';
										document
												.getElementById("ZdiscountRate").value = '100';
										document.getElementById("Zmemo").value = '';
										location.href = location.href;
										/* $('#fee_2').clearForm(true); */
									},
								});
					}
				},
				cancelValue : $.i18n.prop('btn.cancel'),
				cancel : function() {
					document.getElementById("ZchargeCode").value = '';
					document.getElementById("ZstartTime").value = '0';
					document.getElementById("ZendTime").value = '';
					document.getElementById("ZusedState").value = '';
					document.getElementById("ZtimeValue").value = '';
					document.getElementById("ZdiscountRate").value = '100';
					document.getElementById("Zmemo").value = '';
					/* $('#fee_2').clearForm(false);
					location.href = location.href; */
				}
			});
			dialogCode.width(450);
			dialogCode.height(300);
			dialogCode.showModal();
		};

		//修改 数据  （主表）
		var dialogCode = null;
		var updatePaymentMaster = function(row) { //弹出修改和添加原因的窗口
			document.getElementById("ZmasterID").disabled = true;
			document.getElementById("ZchargeCode").disabled = true;
			document.getElementById("ZstartTime").disabled = true;
			document.getElementById("ZendTime").disabled = true;
			document.getElementById("ZusedState").disabled = true;
			document.getElementById("ZtimeValue").disabled = false;
			document.getElementById("ZdiscountRate").disabled = false;
			document.getElementById("Zmemo").disabled = false;
			if (row > 0) {
				var col = $("#datatable").jqGrid('getRowData', row);
				$("#ZmasterID").val(col.masterid);
				$("#ZchargeCode").val(col.chargecode);
				$("#ZfeeValue").val(col.feevalue);
				$("#ZstartTime").val(col.starttime);
				$("#ZendTime").val(col.endtime);
				$("#ZusedState").val(col.usedstate);
				$("#ZtimeValue").val(col.timevalue);
				$("#ZdiscountRate").val(col.discountrate);
				$("#Zmemo").val(col.memo);
			}
			dialogCode = dialog({
				title : $.i18n.prop('btn.update'),
				content : $('#fee_2'),
				okValue : $.i18n.prop('btn.ok'),
				ok : function() {
					var ZchargeCode = document.getElementById("ZchargeCode").value;
					var ZstartTime = document.getElementById("ZstartTime").value;
					var ZendTime = document.getElementById("ZendTime").value;
					var ZusedState = document.getElementById("ZusedState").value;
					var ZtimeValue = document.getElementById("ZtimeValue").value;
					var ZdiscountRate = document
							.getElementById("ZdiscountRate").value;
					var Zmemo = document.getElementById("Zmemo").value;
					if (ZchargeCode == '' || ZchargeCode == null) {
						showMsg("<spring:message code='feeScale.PaymentMaster.prompt3.information'/>");
						return false;
					} else if (ZstartTime == '' || ZstartTime == null) {
						showMsg("<spring:message code='feeScale.PaymentMaster.prompt5.information'/>");
						return false;
					} else if (ZendTime == '' || ZendTime == null) {
						showMsg("<spring:message code='feeScale.PaymentMaster.prompt6.information'/>");
						return false;
					} else if (parseInt(ZendTime) < parseInt(ZstartTime)) {
						showMsg("<spring:message code='feeScale.PaymentMaster.prompt25.information'/>");
						return false;
					} else if (ZusedState == '' || ZusedState == null) {
						showMsg("<spring:message code='feeScale.PaymentMaster.prompt7.information'/>");
						return false;
					} else if (ZtimeValue == '' || ZtimeValue == null) {
						showMsg("<spring:message code='feeScale.PaymentMaster.prompt8.information'/>");
						return false;
					} else if (ZdiscountRate == '' || ZdiscountRate == null) {
						showMsg("<spring:message code='feeScale.PaymentMaster.prompt9.information'/>");
						return false;
					} else if (Zmemo == '' || Zmemo == null) {
						showMsg("<spring:message code='feeScale.PaymentMaster.prompt10.information'/>");
						return false;
					} else {
						$
								.ajax({
									type : "POST",
									url : "${pageContext.request.contextPath}/feeScaleController/saveOrUpdatePaymentMaster",
									data : {
										masterid : $("#ZmasterID").val(),
										chargecode : $("#ZchargeCode").val(),
										ordernumber : $("#ZorderNumber").val(),
										usedstate : $("#ZusedState").val(),
										timevalue : $("#ZtimeValue").val(),
										starttime : $("#ZstartTime").val(),
										endtime : $("#ZendTime").val(),
										discountrate : $("#ZdiscountRate")
												.val(),
										memo : $("#Zmemo").val()
									},
									success : function(data) {
										showMsg(eval("(" + data + ")").msg);
										submitForm();
										$('#fee_2').clearForm(true);
									},
								});
					}
				},
				cancelValue : $.i18n.prop('btn.cancel'),
				cancel : function() {
					$('#fee_2').clearForm(false);
				}
			});
			dialogCode.width(450);
			dialogCode.height(300);
			dialogCode.showModal();
		};
		//添加 数据  （从表） 
		var dialogCode = null;
		function addMakeCard(masterid) { //弹出修改和添加原因的窗口
			/* alert(masterid); */
			document.getElementById("masterID").disabled = true;
			document.getElementById("detailID").disabled = true;
			document.getElementById("boxTypeCode").disabled = false;
			document.getElementById("feeValue").disabled = false;
			document.getElementById("discountRate").disabled = false;
			document.getElementById("memo").disabled = false;
			document.getElementById("masterID").value = masterid;
			dialogCode = dialog({
				title : $.i18n.prop('btn.add'),
				content : $('#fee_1'),
				okValue : $.i18n.prop('btn.ok'),
				ok : function() {
					var masterID = document.getElementById("masterID").value;
					var boxTypeCode = document.getElementById("boxTypeCode").value;
					var feeValue = document.getElementById("feeValue").value;
					var discountRate = document.getElementById("discountRate").value;
					if (masterID == '' || masterID == null) {
						showMsg("<spring:message code='feeScale.PaymentDetail.prompt11.information'/>");
						return false;
					} else if (boxTypeCode == '' || boxTypeCode == null) {
						showMsg("<spring:message code='feeScale.PaymentDetail.prompt12.information'/>");
						return false;
					} else if (feeValue == '' || feeValue == null) {
						showMsg("<spring:message code='feeScale.PaymentDetail.prompt13.information'/>");
						return false;
					} else if (discountRate == '' || discountRate == null) {
						showMsg("<spring:message code='feeScale.PaymentDetail.prompt14.information'/>");
						return false;
					} else {
						$
								.ajax({
									type : "POST",
									url : "${pageContext.request.contextPath}/feeScaleController/saveOrUpdateProgramElement",
									data : {
										masterid : $("#masterID").val(),
										detailid : $("#detailID").val(),
										boxtypecode : $("#boxTypeCode").val(),
										feevalue : $("#feeValue").val(),
										discountrate : $("#discountRate").val(),
										memo : $("#memo").val()
									},
									success : function(data) {
										var dataObj = eval("(" + data + ")");
										if (dataObj.msg == "<spring:message code='feeScale.prompt20.information'/>") {
											showMsg("<spring:message code='feeScale.prompt20.information'/>");
										} else {
											showMsg("<spring:message code='feeScale.prompt21.information'/>");
										}
										/* location.href = location.href; */
										submitFormdetail();
										document.getElementById("masterID").value = '';
										document.getElementById("detailID").value = '';
										document.getElementById("boxTypeCode").value = '';
										document.getElementById("feeValue").value = '';
										document.getElementById("discountRate").value = '100';
										document.getElementById("memo").value = '';
									},
								});
					}
				},
				cancelValue : $.i18n.prop('btn.cancel'),
				cancel : function() {
					document.getElementById("masterID").value = '';
					document.getElementById("detailID").value = '';
					document.getElementById("boxTypeCode").value = '';
					document.getElementById("feeValue").value = '';
					document.getElementById("discountRate").value = '100';
					document.getElementById("memo").value = '';
					/* location.href = location.href; */
					/* $("#detail").trigger('reloadGrid'); */
				}
			});
			dialogCode.width(400);
			dialogCode.height(200);
			dialogCode.showModal();
		};
		//修改 数据  （从表） 
		var dialogCode = null;
		function updateMakeCard(masterid, detailid) { //弹出修改和添加原因的窗口
			/* alert(masterid);
			alert(detailid) */
			document.getElementById("masterID").disabled = true;
			document.getElementById("detailID").disabled = true;
			document.getElementById("boxTypeCode").disabled = true;
			document.getElementById("feeValue").disabled = false;
			document.getElementById("discountRate").disabled = false;
			document.getElementById("memo").disabled = false;
			document.getElementById("masterID").value = masterid;
			$
					.ajax({
						type : "POST",
						url : "${pageContext.request.contextPath}/feeScaleController/selectProgramElement",
						data : {
							masterid : masterid,
							detailid : detailid
						},
						success : function(data) {
							if (data == 'null') {
								document.getElementById("masterID").value = col.masterid;
								document.getElementById("detailID").value = '';
								document.getElementById("boxTypeCode").value = '';
								document.getElementById("feeValue").value = '';
								document.getElementById("discountRate").value = '100';
								document.getElementById("memo").value = '';
							} else {
								var dataObj = eval("(" + data + ")");
								document.getElementById("masterID").value = dataObj.masterid;
								document.getElementById("detailID").value = dataObj.detailid;
								document.getElementById("boxTypeCode").value = dataObj.boxtypecode;
								document.getElementById("feeValue").value = dataObj.feevalue;
								document.getElementById("discountRate").value = dataObj.discountrate;
								document.getElementById("memo").value = dataObj.memo;

							}
						},
					});
			dialogCode = dialog({
				title : $.i18n.prop('btn.update'),
				content : $('#fee_1'),
				okValue : $.i18n.prop('btn.ok'),
				ok : function() {
					var masterID = document.getElementById("masterID").value;
					var boxTypeCode = document.getElementById("boxTypeCode").value;
					var feeValue = document.getElementById("feeValue").value;
					var discountRate = document.getElementById("discountRate").value;
					if (masterID == '' || masterID == null) {
						showMsg("<spring:message code='feeScale.PaymentDetail.prompt11.information'/>");
						return false;
					} else if (boxTypeCode == '' || boxTypeCode == null) {
						showMsg("<spring:message code='feeScale.PaymentDetail.prompt12.information'/>");
						return false;
					} else if (feeValue == '' || feeValue == null) {
						showMsg("<spring:message code='feeScale.PaymentDetail.prompt13.information'/>");
						return false;
					} else if (discountRate == '' || discountRate == null) {
						showMsg("<spring:message code='feeScale.PaymentDetail.prompt14.information'/>");
						return false;
					} else {
						$
								.ajax({
									type : "POST",
									url : "${pageContext.request.contextPath}/feeScaleController/saveOrUpdateProgramElement",
									data : {
										masterid : $("#masterID").val(),
										detailid : $("#detailID").val(),
										boxtypecode : $("#boxTypeCode").val(),
										feevalue : $("#feeValue").val(),
										discountrate : $("#discountRate").val(),
										memo : $("#memo").val()
									},
									success : function(data) {
										var dataObj = eval("(" + data + ")");
										if (dataObj.msg == "<spring:message code='feeScale.prompt20.information'/>") {
											showMsg("<spring:message code='feeScale.prompt20.information'/>");
										} else {
											showMsg("<spring:message code='feeScale.prompt21.information'/>");
										}
										/* location.href = location.href; */
										submitFormdetail();
										document.getElementById("masterID").value = '';
										document.getElementById("detailID").value = '';
										document.getElementById("boxTypeCode").value = '';
										document.getElementById("feeValue").value = '';
										document.getElementById("discountRate").value = '100';
										document.getElementById("memo").value = '';
									},
								});
					}
				},
				cancelValue : $.i18n.prop('btn.cancel'),
				cancel : function() {
					/* location.href = location.href; */
					document.getElementById("masterID").value = '';
					document.getElementById("detailID").value = '';
					document.getElementById("boxTypeCode").value = '';
					document.getElementById("feeValue").value = '';
					document.getElementById("discountRate").value = '100';
					document.getElementById("memo").value = '';
				}
			});
			dialogCode.width(400);
			dialogCode.showModal();
		};
		//查看窗口
		function selectMakeCard(title, row) {
			document.getElementById("ZmasterID").disabled = true;
			document.getElementById("ZchargeCode").disabled = true;
			document.getElementById("ZstartTime").disabled = true;
			document.getElementById("ZendTime").disabled = true;
			document.getElementById("ZusedState").disabled = true;
			document.getElementById("ZtimeValue").disabled = true;
			document.getElementById("ZdiscountRate").disabled = true;
			document.getElementById("Zmemo").disabled = true;
			/* alert(row); */
			var col = $("#datatable").jqGrid('getRowData', row);
			$("#ZmasterID").val(col.masterid);
			$("#ZchargeCode").val(col.chargecode);
			$("#ZfeeValue").val(col.feevalue);
			$("#ZstartTime").val(col.starttime);
			$("#ZendTime").val(col.endtime);
			$("#ZusedState").val(col.usedstate);
			$("#ZtimeValue").val(col.timevalue);
			$("#ZdiscountRate").val(col.discountrate);
			$("#Zmemo").val(col.memo);
			dialogCode = dialog({
				title : title,
				content : $('#fee_2'),
				cancelValue : $.i18n.prop('btn.cancel'),
				cancel : function() {
					$('#fee_2').clearForm(true);
				}
			});
			dialogCode.width(450);
			dialogCode.height(300);
			dialogCode.showModal();
		};
		function openWin(title, id, width) {
			var dia = dialog({
				title : title,
				content : $('#' + id),
				cancelValue : $.i18n.prop('btn.cancel'),
				cancel : function() {
					// return setFormStores();
					location.href = location.href;
				}
			});
			dia.width(width);
			dia.height(450);
			dia.showModal();
		}
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