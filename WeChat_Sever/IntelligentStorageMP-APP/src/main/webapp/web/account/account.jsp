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
<title><spring:message code="account.title.text" /></title>
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
	src="${pageContext.request.contextPath}/js/module/commodity/role.js"></script>
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
/* 	#Ins {top: 10%;left: 10%;width: 20%;height: 30%;}
	#dd {font-family: "宋体";font-size: 18px;position: 固定;height: 100%;width: 100%;float: right;} */
td#datatable_toppager_center {
	display: none;
}
</style>
</head>
<body style="overflow-x: hidden">

	<!-- 增加数据成 -->
	<div align="center" id="Ins" style="display: none;">
		<form id="formAddHandlingFee" action="" method="post">
			<div class="list" id="accountdict">
				<ul>
					<li style="width: 418px;"><spring:message
							code="account.accountcode" />：<input name="accountcode"
						id="accountcode" type="text" value="" maxlength="20"
						onblur="accountOnblue()"
						onkeyup="value=value.replace(/[^\a-\z\A-\Z0-9]/g,'')"
						onpaste="value=value.replace(/[^\a-\z\A-\Z0-9]/g,'')"
						oncontextmenu="value=value.replace(/[^\a-\z\A-\Z0-9]/g,'')" /><span
						style="color: red">*（<spring:message
								code="account.table.col10.text" />0000）
					</span></li>
					<li style="width: 305px;"><spring:message
							code="account.accountname" />：<input name="accountname"
						id="accountname" type="text" value="" maxlength="64" /><span
						style="color: red">*</span></li>
					<li id="pass"><input name="password" id="password"
						type="hidden" value="0000" maxlength="20" /></li>
					<li style="width: 305px;"><spring:message
							code="account.rolecode" />：<select id="rolecode" name="rolecode">
							<option value="" selected="selected" ><spring:message
									code="account.prompt2.information" /></option>
							<c:forEach var="r" items="${role}">
								<option value="<c:out value='${r.rolecode}'/>">
									<c:out value='${r.rolename}' /></option>
							</c:forEach>
					</select><span style="color: red">*</span></li>
					<li style="width: 305px;"><spring:message
							code="account.areacode" />：<select id="areacode_1"
						name="areacode_1">
							<option value="" selected><spring:message
									code="account.table.col11.text" /></option>
							<c:forEach var="area" items="${area}">
								<option value="<c:out value='${area.areacode}'/>"><c:out
										value='${area.areaname}（${area.areashortname}）' /></option>
							</c:forEach>
					</select><span style="color: red">*</span></li>
					<li style="width: 426px;"><spring:message code="account.cashierflag" />： <input type="radio"
						name="cashierflag" id="cashierflag" value="1"
						style="height: 14px; width: 30px;" /><spring:message code="account.table.col22.text" /> &nbsp;&nbsp;&nbsp; <input
						type="radio" name="cashierflag" id="cashierflag_1" value="0"
						checked="checked" style="height: 14px; width: 30px;" /><spring:message code="account.table.col23.text" />
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span
						style="color: red">*<spring:message code="account.prompt12.information" /></span>
					</li>
					<li><spring:message code="account.address" />：<input
						name="address" id="address" type="text" maxlength="100" /></li>
					<li><spring:message code="account.telephone" />：<input
						name="telephone" id="telephone" type="text" maxlength="11"
						onkeyup="this.value=this.value.replace(/\D/g,'')"
						onafterpaste="this.value=this.value.replace(/\D/g,'')" /></li>
					<li>Email：<input name="email" id="email" type="text"
						maxlength="50" /></li>
					<li><spring:message code="account.memo" />：
					<textarea  name="memo" id="memo"  cols="40" rows="3"  style="width: 200px;
	                resize:none;   border: 1px solid #ccc; border-radius: 3px;"></textarea> 
					<!-- <input name="memo" id="memo" type="text" maxlength="255" /></li> -->
				</ul>
			</div>
		</form>
	</div>
	<div class="content" align="center" style="display: none;">
		<form id="selectaccountcode"
			action="${pageContext.request.contextPath}/accountManagementAction/selectAccountManagement.do"
			method="post">
			<div class="list" align="center">
				<ul>
					<li><spring:message code="account.accountname" />：<input
						name="accountname" id="accountname_1" type="text" value=""
						maxlength="64" /></li>
				</ul>
				<!-- <ul style="float:right;margin-right:-175px;">   			
 					<li style="margin-left: 50px;"><input type="button" value="查询" id="selectButton"
								onclick="submitForm()" style="width: 88px; font-size: 14px; "/></li>
				</ul> -->
			</div>
		</form>
	</div>
	<div style="display: none;">
		<input type="text" value="${account.areacode}" id="areacode_2">
	</div>
	<div style="display: none;">
		<input type="button" value="<spring:message code="btn.search" />"
			id="selectButton" onclick="searchaccount" />
	</div>
	<div class="tab">
		<table id="datatable"></table>
		<div id="pager2"></div>
	</div>

	<script type="text/javascript">
		/* 查询 当摁下 enter键后执行 button */
		document.onkeydown = function(event) {
			var e = event || window.event
					|| arguments.callee.caller.arguments[0];
			if (e && e.keyCode == 13) { // enter 键
				if (document.activeElement.id == "accountname_1") {
					$(".ui-dialog-autofocus").trigger("click");
					return false;
				}
			}
		};

		//判断输入的值是否存在 
		function accountOnblue() {
			var accountcode = document.getElementById("accountcode").value;
			$
					.ajax({
						type : "POST",
						datatype : "json",
						data : {
							accountcode : accountcode
						},
						url : "${pageContext.request.contextPath}/accountManagementAction/selectAccount.do",
						success : function(msg) {
							/* showMsg(eval("(" + msg + ")").msg); */
							if (msg == "null") {
								return false;
							} else {
								var dataObj = eval("(" + msg + ")");
								if (accountcode == dataObj.accountcode) {
									showMsg('<spring:message code="account.prompt3.information" />');
									document.getElementById("accountcode").value = "";
									return false;
								}
							}
						},
					});

		};

		function submitForm() {
			$("#datatable").jqGrid('setGridParam', {
				postData : formToJson("selectaccountcode"),
				page : 1
			}).trigger("reloadGrid");
		};
		function formToJson(accountcode) { //把form表单的所有数据变成json对象
			var formArray = $('#' + accountcode).serializeArray(); //数组里面首先放入充值记录表的内容
			var formJson = new Object();
			$.each(formArray, function(i, n) { //把formarray转换为json
				if ((n.name != undefined && n.name != "")) {
					formJson[n.name] = n.value;
				}
			});
			return formJson;
		};
		/* var area = document.getElementById("areacode_2").value; 
		var areacode = area.substr(0,2) */
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
												url : '${pageContext.request.contextPath}/accountManagementAction/selectAccountManagement.do',
												datatype : "json",
												mtype : "POST",
												width : document.body.offsetWidth - 60,
												height : window.screen.availHeight - 325,
												//autowidth:true,
												editable : false,
												shrinkToFit : true,
												colNames : [
														$.i18n
																.prop('account.table.col.text.accountcode'),
														$.i18n
																.prop('account.table.col.text.accountname'),
														$.i18n
																.prop('account.table.col.text.rolecode'),
														$.i18n
																.prop('account.table.col.text.login'),
														$.i18n
																.prop('account.table.col.text.teleohone'),
														$.i18n
																.prop('account.table.col.text.areacode'),
														$.i18n
																.prop('account.table.col.text.password'),
														$.i18n
																.prop('account.table.col.text.addree'),
														'Email',
														$.i18n
																.prop('account.table.col.text.memo'),
														'',
														'',
														$.i18n
																.prop('account.table.col.text.operation') ],
												colModel : [
														{
															name : "accountcode",
															index : "accountcode",
															align : 'center',
															width : 100,
															sortable : false,
															editable : true
														},
														{
															name : "accountname",
															index : "accountname",
															align : 'center',
															width : 100,
															sortable : false,
															editable : true
														},
														{
															name : "rolename",
															index : "rolename",
															align : 'center',
															width : 100,
															sortable : false,
															editable : true,
															formatter : function(
																	el,
																	options,
																	rowData) {
																return rowData.roleEntity.rolename;
															}
														},
														{
															name : "cashierflag",
															index : "cashierflag",
															align : 'center',
															width : 100,
															sortable : false,
															editable : true,
															formatter : function(
																	el,
																	options,
																	rowData) {
																if (rowData.cashierflag == 1) {
																	return '<spring:message code="account.prompt13.information" />';
																} else {
																	return '<spring:message code="account.prompt14.information" />';
																}

															}
														},
														{
															name : "telephone",
															index : "telephone",
															align : 'center',
															width : 120,
															sortable : false,
															editable : true
														},
														{
															name : "areaname",
															index : "areaname",
															align : 'center',
															width : 100,
															sortable : false,
															editable : true,
															formatter : function(
																	el,
																	options,
																	rowData) {
																return rowData.areaEntity.areaname;
															}
														},
														{
															name : "password",
															index : "password",
															align : 'center',
															width : 100,
															sortable : false,
															hidden : true,
															editable : true
														},
														{
															name : "address",
															index : "address",
															align : 'center',
															width : 150,
															sortable : false,
															editable : true
														},
														{
															name : "email",
															index : "email",
															align : 'center',
															width : 150,
															sortable : false,
															editable : true
														},
														{
															name : "memo",
															index : "memo",
															align : 'center',
															width : 150,
															sortable : false,
															editable : true
														},
														{
															name : "rolecode",
															index : "rolecode",
															align : 'center',
															width : 100,
															sortable : false,
															hidden : true,
															editable : true,
															formatter : function(
																	el,
																	options,
																	rowData) {
																return rowData.roleEntity.rolecode;
															}
														},
														{
															name : "areacode",
															index : "areacode",
															align : 'center',
															width : 100,
															sortable : false,
															hidden : true,
															editable : true,
															formatter : function(
																	el,
																	options,
																	rowData) {
																return rowData.areaEntity.areacode;
															}
														},
														{
															name : "",
															index : '',
															align : 'center',
															width : 100,
															formatter : function(
																	el,
																	options,
																	rowData) {
																var d = '<input type="button" value="<spring:message code="account.prompt1.information" />" onclick="initialize(\''
																		+ rowData.accountcode
																		+ '\',\''
																		+ rowData.accountname
																		+ '\',\''
																		+ rowData.areaEntity.areacode
																		+ '\')" style="width:80px;float:right;margin-right:10px;"/>';
																return d;
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
												toppager : true,
												sortname : 'rechargenumber',
												sortorder : 'desc',
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
													if (RowData.accountcode) {
														selectAccountManagement(
																"<spring:message code='account.prompt4.information' />",
																rowid);
													}
												},
											});
							$("#datatable").jqGrid("navGrid", "#pager2", {
								cloneToTop : true,
								/* search : false, */
								addfunc : addMakeCard, // (1) 点击添加按钮，则调用addMakeCard方法  
								addtext : $.i18n.prop("btn.add"),
								editfunc : updateaccount, // (2) 点击添加按钮，则调用addMakeCard方法  
								edittext : $.i18n.prop("btn.update"),
								delfunc : openDialog4Deleting, // (3) 点击添加按钮，则调用openDialog4Deleting方法   
								deltext : $.i18n.prop("btn.delete"),
								searchfunc : searchaccount,
								searchtext : $.i18n.prop("btn.search"),
								alerttext : $.i18n.prop("param.minrow")
							// (4) 当未选中任何行而点击编辑、删除、查看按钮时，弹出的提示信息  
							});

						});
		//查询数据 
		var dialogCode = "";
		var searchaccount = function(row) {
			var dialogCode = dialog({
				title : $.i18n.prop('btn.search'),
				content : $('#selectaccountcode'),
				okValue : $.i18n.prop('btn.ok'),
				ok : function() {
					var accountname = document.getElementById("accountname_1").value;
					$
							.ajax({
								type : "POST",
								url : "${pageContext.request.contextPath}/accountManagementAction/selectAccountManagement.do",
								data : {
									accountname : accountname
								},
								success : function(msg) {
									showMsg("<spring:message code='account.prompt15.information' />");
									submitForm();
									$('#selectaccountcode').clearForm(true);
								}
							});
				},
				cancelValue : $.i18n.prop('btn.cancel'),
				cancel : function() {
					$('#selectaccountcode').clearForm(true);
				}
			});
			dialogCode.width(350);
			dialogCode.showModal();
		};
		//初始化 
		function initialize(accountcode, accountname, areacode) {
			var dialogCode = dialog({
				title : $.i18n.prop('tip.title'),
				content : '<spring:message code="account.prompt5.information"/> '
						+ accountname
						+ ' <spring:message code="account.prompt6.information" />',
				okValue : $.i18n.prop('btn.ok'),
				ok : function() {
					$
							.ajax({
								url : "${pageContext.request.contextPath}/passwordController/initializeAccountPassword.do",
								data : {
									accountcode : accountcode,
									areacode : areacode
								},
								success : function(msg) {
									showMsg(eval("(" + msg + ")").msg);
									/* top.location='${pageContext.request.contextPath}'; */
								},
							});
				},
				cancelValue : $.i18n.prop('btn.cancel'),
				cancel : function() {
				}
			});
			dialogCode.width(250);
			dialogCode.showModal();
		};
		//删除 
		var openDialog4Deleting = function(row) {
			var col = $("#datatable").jqGrid('getRowData', row);
			var ids = jQuery("#datatable").jqGrid('getGridParam', 'selarrrow');
			if(ids.length>1){
				showMsg("<spring:message code='account.prompt17.information'/>");
			}else {
				var dialogCode = dialog({
					title : $.i18n.prop('tip.title'),
					content : $.i18n.prop('tip.confirm.delete'),
					okValue : $.i18n.prop('btn.ok'),
					ok : function() {
						/* this.title($.i18n.prop('tip.loading')); */
						$
								.ajax({
									type : "POST",
									url : "${pageContext.request.contextPath}/accountManagementAction/delectAccountManagement.do",
									data : {
										id : col.accountcode
									},
									success : function(msg) {
										showMsg(eval("(" + msg + ")").msg);
										$("#datatable").trigger('reloadGrid');
										setTimeout(function() {
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
			}
		};
		//更新数据
		var updateaccount = function(row) {
			document.getElementById("accountname").disabled = false;
			document.getElementById("rolecode").disabled = false;
			document.getElementById("areacode_1").disabled = false;
			document.getElementById("cashierflag").disabled = false;
			document.getElementById("cashierflag_1").disabled = false;
			document.getElementById("address").disabled = false;
			document.getElementById("telephone").disabled = false;
			document.getElementById("email").disabled = false;
			document.getElementById("memo").disabled = false;
			document.getElementById("accountcode").disabled = true;
			document.getElementById("pass").style.display = "none";
			if (row > 0) {
				var col = $("#datatable").jqGrid('getRowData', row);
				$("#accountcode").val(col.accountcode);
				$("#accountname").val(col.accountname);
				/* alert(col.accountname); */
				$("#password").val(col.password);
				$("#rolecode").attr("value", col.rolecode);
				$("#areacode_1").attr("value", col.areacode);
				if (col.cashierflag == "<spring:message code='account.table.col22.text'/>") {
					$("#cashierflag").attr("checked", true);
				} else {
					$("#cashierflag_1").attr("checked", true);
				}
				$("#address").val(col.address);
				$("#telephone").val(col.telephone);
				$("#email").val(col.email);
				$("#memo").val(col.memo)
			}
			dialogCode = dialog({
				title : $.i18n.prop('btn.update'),
				content : $('#formAddHandlingFee'),
				okValue : $.i18n.prop('btn.ok'),
				ok : function() {
					/* alert($("input[name='cashierflag']:checked").val()); */
					$
							.ajax({
								type : "POST",
								url : "${pageContext.request.contextPath}/accountManagementAction/saveOrUpdateAccountManagement.do",
								data : {
									accountcode : $("#accountcode").val(),
									accountname : $("#accountname").val(),
									password : $("#password").val(),
									rolecode : $("#rolecode").val(),
									areacode : $("#areacode_1").val(),
									cashierflag : $(
											"input[name='cashierflag']:checked")
											.val(),
									address : $("#address").val(),
									telephone : $("#telephone").val(),
									email : $("#email").val(),
									memo : $("#memo").val()
								},
								success : function(msg) {
									showMsg(eval("(" + msg + ")").msg);
									submitForm();
									document.getElementById("accountcode").value = '';
									document.getElementById("accountname").value = '';
									document.getElementById("rolecode").value = '';
									document.getElementById("areacode_1").value = '';
									document.getElementsByName("cashierflag").value = 0;
									document.getElementById("address").value = '';
									document.getElementById("telephone").value = '';
									document.getElementById("email").value = '';
									document.getElementById("memo").value = '';
								},
							});
				},
				cancelValue : $.i18n.prop('btn.cancel'),
				cancel : function() {
					document.getElementById("accountcode").value = '';
					document.getElementById("accountname").value = '';
					document.getElementById("rolecode").value = '';
					document.getElementById("areacode_1").value = '';
					document.getElementsByName("cashierflag").value = 0;
					document.getElementById("address").value = '';
					document.getElementById("telephone").value = '';
					document.getElementById("email").value = '';
					document.getElementById("memo").value = '';
				}
			});
			dialogCode.width(480);
			dialogCode.height(380);
			dialogCode.showModal();
		};

		//添加    
		var addMakeCard = function(row) { //弹出添加窗口 修改窗口   	
			document.getElementById("accountcode").disabled = false;
			document.getElementById("accountname").disabled = false;
			document.getElementById("rolecode").disabled = false;
			document.getElementById("areacode_1").disabled = false;
			document.getElementById("cashierflag").disabled = false;
			document.getElementById("cashierflag_1").disabled = false;
			document.getElementById("address").disabled = false;
			document.getElementById("telephone").disabled = false;
			document.getElementById("email").disabled = false;
			document.getElementById("memo").disabled = false;
			document.getElementById("pass").style.display = "none";
			dialogCode = dialog({
				title : $.i18n.prop('btn.add'),
				content : $('#formAddHandlingFee'),
				okValue : $.i18n.prop('btn.ok'),
				ok : function() {
					var accountcode = document.getElementById("accountcode").value;
					var accountname = document.getElementById("accountname").value;
					var rolecode = document.getElementById("rolecode").value;
					var areacode_1 = document.getElementById("areacode_1").value;
					var cashierflag = $("input[name='cashierflag']:checked")
							.val();
					if (accountcode == '' || accountcode == null) {
						showMsg("<spring:message code='account.prompt7.information'/>");
						return false;
					} else if (accountname == '' || accountname == null) {
						showMsg("<spring:message code='account.prompt8.information'/>");
						return false;
					} else if (rolecode == '' || rolecode == null) {
						showMsg("<spring:message code='account.prompt9.information'/>");
						return false;
					} else if (areacode_1 == '' || areacode_1 == null) {
						showMsg("<spring:message code='account.prompt10.information.text'/>");
						return false;
					} else if (cashierflag == '' || cashierflag == null) {
						showMsg("<spring:message code='account.prompt16.information'/>");
						return false;
					} else {
						//判断权限 如果输入的区域大于或等于登录人的区域就不让他添加 
						/* var areacode = '${account.areacode}';
						if (areacode.length >= areacode_1.length) {
							showMsg("<spring:message code='account.prompt11.information'/>");
							return false;
						} else { } */
							$
									.ajax({
										type : "POST",
										url : "${pageContext.request.contextPath}/accountManagementAction/saveOrUpdateAccountManagement.do",
										data : {
											accountcode : $("#accountcode")
													.val(),
											accountname : $("#accountname")
													.val(),
											password : $("#password").val(),
											rolecode : $("#rolecode").val(),
											areacode : $("#areacode_1").val(),
											cashierflag : $(
													"input[name='cashierflag']:checked")
													.val(),
											address : $("#address").val(),
											telephone : $("#telephone").val(),
											email : $("#email").val(),
											memo : $("#memo").val()
										},
										success : function(msg) {
											showMsg(eval("(" + msg + ")").msg);
											submitForm();
											document
													.getElementById("accountcode").value = '';
											document
													.getElementById("accountname").value = '';
											document.getElementById("rolecode").value = '';
											document
													.getElementById("areacode_1").value = '';
											document
													.getElementsByName("cashierflag").value = 0;
											document.getElementById("address").value = '';
											document
													.getElementById("telephone").value = '';
											document.getElementById("email").value = '';
											document.getElementById("memo").value = '';
											/* location.href = location.href; */
										},
									});
					}
				},
				cancelValue : $.i18n.prop('btn.cancel'),
				cancel : function() {
					/* location.href = location.href;  */
					document.getElementById("accountcode").value = '';
					document.getElementById("accountname").value = '';
					document.getElementById("rolecode").value = '';
					document.getElementById("areacode_1").value = '';
					document.getElementsByName("cashierflag").value = 0;
					document.getElementById("address").value = '';
					document.getElementById("telephone").value = '';
					document.getElementById("email").value = '';
					document.getElementById("memo").value = '';
				}
			});

			dialogCode.width(480);
			dialogCode.height(380);
			dialogCode.showModal();
		};

		//查看窗口
		function selectAccountManagement(title, row) {
			document.getElementById("accountcode").disabled = true;
			document.getElementById("accountname").disabled = true;
			document.getElementById("rolecode").disabled = true;
			document.getElementById("areacode_1").disabled = true;
			document.getElementById("areacode_1").disabled = true;
			document.getElementById("cashierflag").disabled = true;
			document.getElementById("cashierflag_1").disabled = true;
			document.getElementById("address").disabled = true;
			document.getElementById("telephone").disabled = true;
			document.getElementById("email").disabled = true;
			document.getElementById("memo").disabled = true;
			document.getElementById("pass").style.display = "none";
			/* alert(row); */
			var col = $("#datatable").jqGrid('getRowData', row);
			$("#accountcode").val(col.accountcode);
			$("#accountname").val(col.accountname);
			$("#password").val(col.password);
			$("#rolecode").attr("value", col.rolecode);
			$("#areacode_1").attr("value", col.areacode);
			if (col.cashierflag == "<spring:message code='account.table.col22.text'/>") {
				$("#cashierflag").attr("checked", true);
			} else {
				$("#cashierflag_1").attr("checked", true);
			}
			$("#address").val(col.address);
			$("#telephone").val(col.telephone);
			$("#email").val(col.email);
			$("#memo").val(col.memo)
			dialogCode = dialog({
				title : title,
				content : $('#formAddHandlingFee'),
				cancelValue : $.i18n.prop('btn.cancel'),
				cancel : function() {
					$('#formAddHandlingFee').clearForm(true);
				}
			});

			dialogCode.width(480);
			dialogCode.height(380);
			dialogCode.showModal();
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