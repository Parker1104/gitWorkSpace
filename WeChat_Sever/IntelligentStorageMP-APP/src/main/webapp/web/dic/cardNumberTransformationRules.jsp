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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><spring:message code="cardNumberTransformationRules.title.text" /></title>
<%@ include file="../../common/head.jsp"%>
<%@ include file="../../common/theme.jsp"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/js/plug/zTree/css/demo.css"
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

#kk {
	display: none;
	text-align: center;
}

td#datatable_toppager_center {
	display: none;
}
</style>
</head>
<body style="overflow-x: hidden">
	<div id="win" style="display: none" class="menuContent">
		<ul id="treeDemo" class="ztree classify"
			style="width: 200px; height: 300px"></ul>
	</div>
	<div class="content" align="center" style="display: none;">
		<form
			action="${pageContext.request.contextPath}/cardNumberTransformationRulesController/selectCardTransRule.do"
			id="searchMemberCardForm" method="post">
			<div class="list">
				<ul>
					<li><spring:message code="cardNumberTransformationRules.transrulename" />：<input name="transrulename" id="transrulename_1"
						type="text" /></li>
				</ul>
				<!-- <ul style="float:right;margin-right:-175px;">   
       <li style="margin-left: 50px;"><input type="button" id="selectTransrule" onclick="submitForm()" value="查询"  style="width: 88px; font-size: 14px; "/></li>
      </ul> -->
			</div>
		</form>
	</div>
	<div id="kk" align="center">
		<form id="kaleixing"
			action="${pageContext.request.contextPath}/cardNumberTransformationRulesController/selectCardTransRule.do"
			method="post">
			<div class="list">
				<ul>
					<li style="width: 305px;"><spring:message code="cardNumberTransformationRules.transrulename" />：<input name="transRuleName"
						id="transRuleName" type="text" maxlength="32" onblur="transrulenameOnblue()" /><span      
						style="color: red">*</span></li>
					<li style="width: 305px;"><spring:message code="cardNumberTransformationRules.cardtype" />：<select name="cardType"
						id="cardType">
							<option value=""><spring:message code="account.table.col11.text" /></option>
							<c:forEach var="d" items="${datadic}">
								<option value="<c:out value='${d.dictcode}'/>">
									<c:out value='${d.dictname}' /></option>
							</c:forEach>
					</select><span style="color: red">*</span></li>
					<li style="width: 305px;"><spring:message code="cardNumberTransformationRules.Sorting.type" />： <input type="radio"
						name="cardRule" id="cardRule" value="1" checked="checked"
						style="height: 16px; width: 30px;" /><spring:message code="cardNumberTransformationRules.cardrule" />
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input type="radio" name="cardRule"
						id="cardRule_2" value="2" style="height: 16px; width: 30px;" /><spring:message code="cardNumberTransformationRules.cardrule_1" />
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span
						style="color: red">*</span>
					</li>
					<li style="width: 305px;"><spring:message code="cardNumberTransformationRules.According.to.the.rules" />： <input type="radio"
						name="decimalism" id="decimalism" value="1" checked="checked"
						style="height: 16px; width: 30px;" /><spring:message code="cardNumberTransformationRules.decimalism" /> &nbsp;&nbsp; <input
						type="radio" name="decimalism" id="decimalism_2" value="2"
						style="height: 16px; width: 30px;" /><spring:message code="cardNumberTransformationRules.decimalism_2" />
						&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red">*</span>
					</li>
					<li style="width: 305px;"><spring:message code="cardNumberTransformationRules.cardlen" />：<input name="cardLen"
						id="cardLen" type="text" maxlength="20"
						onkeyup='this.value=this.value.replace(/\D/gi,"")' /><span
						style="color: red">*</span></li>
					<li style="width: 305px;"><spring:message code="cardNumberTransformationRules.startsubstr" />：<input name="startSubStr"
						id="startSubStr" type="text" maxlength="20" onfocus="qingkong(this)"
						onblur="likai(this)"
						onkeyup='this.value=this.value.replace(/\D/gi,"")' /><span
						style="color: red">*</span></li>
					<li><spring:message code="cardNumberTransformationRules.memo" />：
					<!-- <input name="memo" id="memo" type="text" maxlength="255" /> -->
					<textarea  name="memo" id="memo"  cols="40" rows="3"  style="width: 200px;
	                 resize:none;  border: 1px solid #ccc; border-radius: 3px;"></textarea> 
					</li>
					<li style="display: none;"><input name="transRuleCode" id="transRuleCode" 
						type="hidden" /></li>
				</ul>
			</div>
		</form>
	</div>
	<!-- table   -->
	<div class="tab">
		<table id="datatable"></table>
		<div id="pager2"></div>
	</div>
	<script type="text/javascript">
		/* 查询 当摁下 enter键后执行 button ID为selectButton 点击事件 */
		document.onkeydown = function(event) {
			var e = event || window.event
					|| arguments.callee.caller.arguments[0];
			if (e && e.keyCode == 13) { // enter 键
				if (document.activeElement.id == "transrulename_1") { 
					$(".ui-dialog-autofocus").trigger("click");
					return false;
				}

			}
		};
		//判断输入的值是否存在 
		function transrulenameOnblue() {
			var transrulename = document.getElementById("transRuleName").value; 
			$
					.ajax({
						type : "POST",
						datatype : "json",
						data : {
							transrulename : transrulename
						},
						url : "${pageContext.request.contextPath}/cardNumberTransformationRulesController/selectCardTransRuleName.do", 
						success : function(msg) {
							/* showMsg(eval("(" + msg + ")").msg); */
							if (msg == "[]") {
								return false;
							} else { 
								var dataObj = eval("(" + msg + ")"); 
								if (transrulename == dataObj[0].transrulename) {
									showMsg("<spring:message code='cardNumberTransformationRules.prompt1.information' />"); 
									document.getElementById("transRuleName").value = "";
									return false;
								}	
							}
						},
					});
		}
		/* text 的控制  */
		var value = document.getElementById('startSubStr');
		function qingkong(inputObj) {
			if (inputObj.value == "<spring:message code='cardNumberTransformationRules.prompt2.information' />") {
				inputObj.value = "";
			}
		}
		function likai(inputObj) {
			if (inputObj.value == '') {
				inputObj.value = "1";
			} else if (document.getElementById("startSubStr").value > document
					.getElementById("cardLen").value) {
				inputObj.value = "<spring:message code='cardNumberTransformationRules.prompt3.information' />";
			}
		}
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
		var win;
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
												url : '${pageContext.request.contextPath}/cardNumberTransformationRulesController/selectCardTransRule.do',
												datatype : "json",
												mtype : "POST",
												width : document.body.offsetWidth - 60,
												height : window.screen.availHeight - 325,
												//autowidth:true,
												editable : false,
												shrinkToFit : true,
												colNames : [ $.i18n.prop('cardNumberTransformationRules.table.col.text.transrulecode'),	$.i18n.prop('cardNumberTransformationRules.table.col.text.transrulename'), 
												             '',	$.i18n.prop('cardNumberTransformationRules.table.col.text.cartype'), $.i18n.prop('cardNumberTransformationRules.table.col.text.According.to.the.rules'), 
													$.i18n.prop('cardNumberTransformationRules.table.col.text.cardlen'),$.i18n.prop('cardNumberTransformationRules.table.col.text.startsubstr'),
													$.i18n.prop('cardNumberTransformationRules.table.col.text.Sorting.type'),$.i18n.prop('cardNumberTransformationRules.table.col.text.memo') ],
												colModel : [
														{
															name : "transrulecode",
															index : "transrulecode",
															align : 'center',
															width : 100,
															sortable : false,
															hidden : true
														},
														{
															name : "transrulename",
															index : 'transrulename',
															align : 'center',
															width : 100,
															sortable : false
														},
														{
															name : "cardtype",
															index : 'cardtype',
															align : 'center',
															width : 100,
															sortable : false,
															hidden : true
														},
														{
															name : "dictname",
															index : "dictname",
															align : 'center',
															width : 100,
															sortable : false,
															formatter : function(
																	el,
																	options,
																	rowData) {															
																	 return rowData.dictEntity.dictname;																
															}
														},
														{
															name : "Decimalism",
															index : "Decimalism",
															align : 'center',
															width : 100,
															sortable : false,
															formatter : function(
																	el,
																	options,
																	rowData) {
																var card = rowData.decimalism;
																if (card == 1) {
																	Decimalism = "<spring:message code='cardNumberTransformationRules.prompt10.information' />";
																	return Decimalism;
																} else if (card == 2) {
																	Decimalism = "<spring:message code='cardNumberTransformationRules.prompt11.information' />";
																	return Decimalism;
																}

															}
														},
														{
															name : "cardlen",
															index : "cardlen",
															align : 'center',
															width : 100,
															sortable : false
														},
														{
															name : "startsubstr",
															index : 'startsubstr',
															align : 'center',
															width : 100,
															sortable : false
														},
														{
															name : "cardRole",
															index : 'cardRole',
															align : 'center',
															width : 100,
															sortable : false,
															formatter : function(
																	el,
																	options,
																	rowData) {
																var card = rowData.cardrule;
																if (card == 1) {
																	cardRole = "<spring:message code='cardNumberTransformationRules.prompt12.information' />";
																	return cardRole;
																} else if (card == 2) {
																	cardRole = "<spring:message code='cardNumberTransformationRules.prompt13.information' />";
																	return cardRole;
																}
															}
														}, {
															name : "memo",
															index : 'memo',
															align : 'center',
															width : 100,
															sortable : false
														},
												/* {name:"",index:'',align:'center',width:90,formatter:function(el,options,rowData){
												    var d= '<input type="button" value="删除" onclick="delectCardTransRule(\''+rowData.transrulecode+'\')" style="width:40px;float:right;margin-right:10px;"/>';
												    var u= '<input type="button" value="修改" onclick="addMakeCard(\'修改类型\',\'修改\','+ options.rowId +')" style="width:40px;float:right;margin-right:10px;"/>';
												    return u+d;
												}} */
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
												sortname : 'transrulecode',
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
													if (RowData.transrulecode) {
														selectMakeCard("<spring:message code='account.table.col13.text' />",
																rowid);
													}
												},
											});
							$("#datatable").jqGrid("navGrid", "#pager2", {
								cloneToTop : true,
								/* search : false, */
								/* edit:false, */
								addfunc : addMakeCard, // (1) 点击添加按钮，则调用addMakeCard方法  
								addtext : $.i18n.prop("btn.add"),
								editfunc : updateMakeCard, // (2) 点击修改按钮，则调用addMakeCard方法   
								edittext : $.i18n.prop("btn.update"),
								delfunc : openDialog4Deleting, // (3) 点击添加按钮，则调用openDialog4Deleting方法   
								deltext : $.i18n.prop("btn.delete"),
								searchfunc : searchCardNumber, // (4) 点击添加按钮，则调用searchCardNumber方法   
								searchtext : $.i18n.prop("btn.search"),
								alerttext : $.i18n.prop("param.minrow")
							// (5) 当未选中任何行而点击编辑、删除、查看按钮时，弹出的提示信息  
							});
						});
		//查询数据 
		var searchCardNumber = function(row) {
			var dialogCode = dialog({
				title : $.i18n.prop('btn.search'),
				content : $('#searchMemberCardForm'),
				okValue : $.i18n.prop('btn.ok'),
				ok : function() {
					var transrulename = document.getElementById("transrulename_1").value;
					$
							.ajax({
								type : "POST",
								url : "${pageContext.request.contextPath}/cardNumberTransformationRulesController/selectCardTransRule.do",
								data : {
									transrulename : transrulename
								},
								success : function(msg) {
									showMsg($.i18n.prop('tip.success'));
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
			dialogCode.width(400);
			dialogCode.showModal();
		};
		//删除数据 

		var dialogCode = null;
		var openDialog4Deleting = function(row) {
			var col = $("#datatable").jqGrid('getRowData', row);
			//判断收否有引用 
			var transrulename = document.getElementById("transRuleName").value;
			var ids = jQuery("#datatable").jqGrid('getGridParam', 'selarrrow');
			if (ids.length > 1) {
				showMsg("无法多行删除");
			} else {
				$.ajax({
					type : "POST",
					url : "${pageContext.request.contextPath}/cardNumberTransformationRulesController/delect.do",
					data : {
						transrulecode : col.transrulecode
					},
					success : function(msg) {
						/* showMsg(eval("(" + msg + ")").msg); */
						if (msg == '[]') {
							var dialogCode = dialog({
								title : $.i18n.prop('tip.title'),
								content : $.i18n
										.prop('tip.confirm.delete'),
								okValue : $.i18n.prop('btn.ok'),
								ok : function() {
									this.title($.i18n
											.prop('tip.loading'));
									$
											.ajax({
												type : "POST",
												url : "${pageContext.request.contextPath}/cardNumberTransformationRulesController/delectCardTransRules.do?id="
														+ col.transrulecode,
												success : function(msg) {
													showMsg(eval("("
															+ msg + ")").msg);
													$("#datatable")
															.trigger(
																	'reloadGrid');
													setTimeout(
															function() {
															}, 2500);

												}
											});
								},
								cancelValue : $.i18n.prop('btn.cancel'),
								cancel : function() {
								}
							});
							dialogCode.width(150);
							dialogCode.showModal();
						} else {
							showMsg("<spring:message code='cardNumberTransformationRules.prompt14.information' />");
							return false;
						}
					}
				});
			}
		};

		//新增  修改 
		var dialogCode = null;
		var addMakeCard = function(row) {//弹出修改和添加原因的窗口 
			document.getElementById("transRuleCode").disabled = false;
			document.getElementById("transRuleName").disabled = false;
			document.getElementById("cardType").disabled = false;
			document.getElementById("cardLen").disabled = false;
			document.getElementById("cardRule").disabled = false;
			document.getElementById("cardRule_2").disabled = false;
			document.getElementById("decimalism").disabled = false;
			document.getElementById("decimalism_2").disabled = false;
			document.getElementById("startSubStr").disabled = false;
			document.getElementById("memo").disabled = false;
			dialogCode = dialog({
				title : $.i18n.prop('btn.add'),
				content : $('#kaleixing'),
				okValue : $.i18n.prop('btn.ok'),
				ok : function() {
					var transRuleName = document
							.getElementById("transRuleName").value;
					var cardType = document.getElementById("cardType").value;
					var cardLen = document.getElementById("cardLen").value;
					var startSubStr = document.getElementById("startSubStr").value;
					if (transRuleName == '' || transRuleName == null) {
						showMsg("<spring:message code='cardNumberTransformationRules.prompt4.information' />");
						return false;
					} else if (cardType == '' || cardType == null) {
						showMsg("<spring:message code='cardNumberTransformationRules.prompt5.information' />");
						return false;
					} else if ($("input[name='cardRule']:checked").val() == ''
							|| $("input[name='cardRule']:checked").val() == null) {
						showMsg("<spring:message code='cardNumberTransformationRules.prompt6.information' />");
						return false;
					} else if ($("input[name='decimalism']:checked").val() == ''
							|| $("input[name='decimalism']:checked").val() == null) {
						showMsg("<spring:message code='cardNumberTransformationRules.prompt7.information' />");
						return false;
					} else if (cardLen == '' || cardLen == null) {
						showMsg("<spring:message code='cardNumberTransformationRules.prompt8.information' />");
						return false;
					} else if (startSubStr == '' || startSubStr == null) {
						showMsg("<spring:message code='cardNumberTransformationRules.prompt9.information' />");
						return false;
					} else {
						$
								.ajax({
									type : "POST",
									url : "${pageContext.request.contextPath}/cardNumberTransformationRulesController/saveOrUpdateCardTransRule.do",
									data : {
										transRuleCode : $("#transRuleCode")
												.val(),
										transRuleName : $("#transRuleName")
												.val(),
										cardType : $("#cardType").val(),
										decimalism : $(
												"input[name='decimalism']:checked")
												.val(),
										cardLen : $("#cardLen").val(),
										startSubStr : $("#startSubStr").val(),
										cardRule : $(
												"input[name='cardRule']:checked")
												.val(),
										memo : $("#memo").val()
									},
									success : function(data) {
										showMsg(eval("(" + data + ")").msg);
										submitForm();
										document
												.getElementById("transRuleCode").value = '';
										document
												.getElementById("transRuleName").value = '';
										document.getElementById("cardType").value = '';
										document.getElementById("cardLen").value = '';
										document.getElementsByName("cardRule").value = 1;
										document
												.getElementsByName("decimalism").value = 1;
										document.getElementById("startSubStr").value = '';
										document.getElementById("memo").value = '';
									},
								});
					}
				},
				cancelValue : $.i18n.prop('btn.cancel'),
				cancel : function() {
					document.getElementById("transRuleCode").value = '';
					document.getElementById("transRuleName").value = '';
					document.getElementById("cardType").value = '';
					document.getElementById("cardLen").value = '';
					document.getElementsByName("cardRule").value = 1;
					document.getElementsByName("decimalism").value = 1;
					document.getElementById("startSubStr").value = '';
					document.getElementById("memo").value = '';

				}
			});
			dialogCode.width(450);
			dialogCode.height(320);
			dialogCode.showModal();
		}
		//修改 
		var dialogCode = null;
		var updateMakeCard = function(row) {//弹出修改和添加原因的窗口
			document.getElementById("transRuleCode").disabled = true;
			document.getElementById("transRuleName").disabled = false;
			document.getElementById("cardType").disabled = false;
			document.getElementById("cardLen").disabled = false;
			document.getElementById("cardRule").disabled = false;
			document.getElementById("cardRule_2").disabled = false;
			document.getElementById("decimalism").disabled = false;
			document.getElementById("decimalism_2").disabled = false;
			document.getElementById("startSubStr").disabled = false;
			document.getElementById("memo").disabled = false;
			if (row > 0) {
				var col = $("#datatable").jqGrid('getRowData', row);
				$("#transRuleCode").val(col.transrulecode);
				$("#transRuleName").val(col.transrulename);
				$("#cardType").val(col.cardtype);
				/* alert(col.Decimalism); */
				if (col.Decimalism == "<spring:message code='cardNumberTransformationRules.prompt10.information' />") {
					$("#decimalism").attr("checked", true);
				} else {
					$("#decimalism_2").attr("checked", true);
				}
				$("#cardLen").val(col.cardlen);
				$("#startSubStr").val(col.startsubstr);
				/* alert(col.cardRole); */
				if (col.cardRole == "<spring:message code='cardNumberTransformationRules.prompt12.information' />") {
					$("#cardRule").attr("checked", true);
				} else {
					$("#cardRule_2").attr("checked", true);
				}
				$("#memo").val(col.memo);
			}
			dialogCode = dialog({
				title : $.i18n.prop('btn.update'),
				content : $('#kaleixing'),
				okValue : $.i18n.prop('btn.ok'),
				ok : function() {
					$
							.ajax({
								type : "POST",
								url : "${pageContext.request.contextPath}/cardNumberTransformationRulesController/saveOrUpdateCardTransRule.do",
								data : {
									transRuleCode : $("#transRuleCode").val(),
									transRuleName : $("#transRuleName").val(),
									cardType : $("#cardType").val(),
									decimalism : $(
											"input[name='decimalism']:checked")
											.val(),
									cardLen : $("#cardLen").val(),
									startSubStr : $("#startSubStr").val(),
									cardRule : $(
											"input[name='cardRule']:checked")
											.val(),
									memo : $("#memo").val()
								},
								success : function(data) {
									showMsg(eval("(" + data + ")").msg);
									submitForm();
									/* $('#kaleixing').clearForm(true); */
									document.getElementById("transRuleCode").value = '';
									document.getElementById("transRuleName").value = '';
									document.getElementById("cardType").value = '';
									document.getElementById("cardLen").value = '';
									document.getElementsByName("cardRule").value = 1;
									document.getElementsByName("decimalism").value = 1;
									document.getElementById("startSubStr").value = '';
									document.getElementById("memo").value = '';
								},

							});
				},
				cancelValue : $.i18n.prop('btn.cancel'),
				cancel : function() {
					/* $('#kaleixing').clearForm(true); */
					document.getElementById("transRuleCode").value = '';
					document.getElementById("transRuleName").value = '';
					document.getElementById("cardType").value = '';
					document.getElementById("cardLen").value = '';
					document.getElementsByName("cardRule").value = 1;
					document.getElementsByName("decimalism").value = 1;
					document.getElementById("startSubStr").value = '';
					document.getElementById("memo").value = '';
				}
			});
			dialogCode.width(450);
			dialogCode.height(320);
			dialogCode.showModal();
		}
		//查看窗口
		function selectMakeCard(title, row) {
			document.getElementById("transRuleCode").disabled = true;
			document.getElementById("transRuleName").disabled = true;
			document.getElementById("cardType").disabled = true;
			document.getElementById("cardLen").disabled = true;
			document.getElementById("cardRule").disabled = true;
			document.getElementById("cardRule_2").disabled = true;
			document.getElementById("decimalism").disabled = true;
			document.getElementById("decimalism_2").disabled = true;
			document.getElementById("startSubStr").disabled = true;
			document.getElementById("memo").disabled = true;
			/* alert(row); */
			var col = $("#datatable").jqGrid('getRowData', row);
			$("#transRuleCode").val(col.transrulecode);
			$("#transRuleName").val(col.transrulename);
			$("#cardType").val(col.cardtype);
			/* alert(col.Decimalism); */
			if (col.Decimalism == "<spring:message code='cardNumberTransformationRules.prompt10.information' />") {
				$("#decimalism").attr("checked", true);
			} else {
				$("#decimalism_2").attr("checked", true);
			}
			$("#cardLen").val(col.cardlen);
			$("#startSubStr").val(col.startsubstr);
			/* alert(col.cardRole); */
			if (col.cardRole == "<spring:message code='cardNumberTransformationRules.prompt12.information' />") {
				$("#cardRule").attr("checked", true);
			} else {
				$("#cardRule_2").attr("checked", true);
			}
			$("#memo").val(col.memo);
			dialogCode = dialog({
				title : "<spring:message code='account.table.col13.text' />",
				content : $('#kaleixing'),
				cancelValue : $.i18n.prop('btn.cancel'),
				cancel : function() {
					$('#kaleixing').clearForm(true);
				}
			});
			dialogCode.width(450);
			dialogCode.height(320);
			dialogCode.showModal();
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