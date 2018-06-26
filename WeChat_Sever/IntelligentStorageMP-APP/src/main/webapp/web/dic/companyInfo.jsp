<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path;
	request.setAttribute("basePath", basePath);
%>
<head>
<title><spring:message code="companyInfo.title.text" /></title>
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
	<div class="title" align="center" style="display: none;">
		<form id="searchMemberCardForm"
			action="${pageContext.request.contextPath}/companyInfoController/selectCompany.do"
			method="post">
			<div class="list">
				<ul>
					<li><spring:message code="companyInfo.companyname" />：<input type="text" id="companyname"
						name="companyname" /></li>
				</ul>
				<!-- <ul style="float:right;margin-right:-150px;">   
		     <li style="margin-left: 50px;"><input type="button" id="selectCompanyName" name="selectCompanyName" value="查询" style="width: 88px; font-size: 14px;" onclick="submitForm()"/></li> 			
           </ul> -->
			</div>
		</form>
	</div>
	<div id="ha" align="center" style="display: none;">
		<form action="" id="comp_1" method="post">
			<div class="list">
				<ul>
					<li style="width: 305px;"><spring:message code="companyInfo.companycode" />：<input name="companyCode"
						id="companyCode" type="text" value="" maxlength="20"
						onblur="companyInfoOnblue()" onkeyup="value=value.replace(/[^\a-\z\A-\Z0-9]/g,'')"
						onpaste="value=value.replace(/[^\a-\z\A-\Z0-9]/g,'')"
						oncontextmenu="value=value.replace(/[^\a-\z\A-\Z0-9]/g,'')"/><span style="color: red">*</span></li>
					<li style="width: 305px;"><spring:message code="companyInfo.companyname" />：<input name="companyName"
						id="companyName" type="text" maxlength="40" onblur="companynameOnblue()" /><span
						style="color: red">*</span></li>
					<li style="width: 305px;"><spring:message code="companyInfo.companyshortname" />：<input name="companyShortName"
						id="companyShortName" type="text" maxlength="64" /><span
						style="color: red">*</span></li>
					<li><spring:message code="companyInfo.companywebsite" />：<input name="companyWebsite" id="companyWebsite"
						type="text" maxlength="100" /></li>
					<li><spring:message code="companyInfo.companyemali" />：<input name="companyEmali" id="companyEmali"
						type="text" maxlength="100" /></li>
					<li><spring:message code="companyInfo.companytelphone" />：<input name="companyTelphone" id="companyTelphone"
						type="text" maxlength="32" /></li>
					<li><spring:message code="companyInfo.companyfax" />：<input name="companyFax" id="companyFax" type="text"
						maxlength="32" /></li>
					<li><spring:message code="companyInfo.companypostcode" />：<input name="companyPostCode" id="companyPostCode"
						type="text" maxlength="11"/></li>
					<li><spring:message code="companyInfo.companylinkname" />：<input name="companyLinkName" id="companyLinkName"
						type="text" maxlength="64" /></li>
					<li><spring:message code="companyInfo.companywelcome" />：<input name="companyWelcome" id="companyWelcome"
						type="text" maxlength="40" /></li>
					<li><spring:message code="companyInfo.memo" />：
					<!-- <input name="memo" id="memo" type="text" maxlength="255" /> -->
					<textarea  name="memo" id="memo"  cols="40" rows="3"  style="width: 200px;
	                  resize:none; border: 1px solid #ccc; border-radius: 3px;"></textarea> 	
					</li>
				</ul>
			</div>
		</form>
	</div>
	<!-- 控制table的显示和隐藏 -->
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
				var e = event || window.event
						|| arguments.callee.caller.arguments[0];
				if (e && e.keyCode == 13) { // enter 键
					if (document.activeElement.id == "companyname") {
						$(".ui-dialog-autofocus").trigger("click");
						return false;
					}
				}
			}
		};
		//查询是否有存在的信息  
		function companyInfoOnblue() {
			var companycode = document.getElementById("companyCode").value;
			$
					.ajax({
						type : "POST",
						datatype : "json",
						data : {
							companycode : companycode
						},
						url : "${pageContext.request.contextPath}/companyInfoController/selectCompanyCode.do",
						success : function(msg) {
							/* showMsg(eval("("+msg+")").msg); */
							if (msg == "null") {
								return false;
							} else {
								var dataObj = eval("(" + msg + ")");
								if (companycode == dataObj.companycode) {
									showMsg("<spring:message code='companyInfo.prompt1.information' />"); 
									document.getElementById("companyCode").value = "";
									return false;
								}
							}

						},
					});

		}
		//查询是否有存在的信息  
		function companynameOnblue() {
			var companyname = document.getElementById("companyName").value;
			$
					.ajax({
						type : "POST",
						datatype : "json",
						data : {
							companyname : companyname
						},
						url : "${pageContext.request.contextPath}/companyInfoController/selectCompanyName.do",
						success : function(msg) {
							/* showMsg(eval("("+msg+")").msg); */
							if (msg == "[]") {
								return false;
							} else {
								var dataObj = eval("(" + msg + ")");
								if (companyname == dataObj[0].companyname) {
									showMsg("<spring:message code='companyInfo.prompt2.information' />"); 
									document.getElementById("companyName").value = "";
									return false;
								}
							}

						},
					});

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
												url : '${pageContext.request.contextPath}/companyInfoController/selectCompany.do',
												datatype : "json",
												mtype : "POST",
												width : document.body.offsetWidth - 60,
												height : window.screen.availHeight - 325,
												//autowidth:true,
												editable : false,
												shrinkToFit : true,
												colNames : [ $.i18n.prop('companyInfo.table.col.text.codeing'),
															 $.i18n.prop('companyInfo.table.col.text.name'),
													         $.i18n.prop('companyInfo.table.col.text.linkname'),
															 $.i18n.prop('companyInfo.table.col.text.ContactPerson'),
													         $.i18n.prop('companyInfo.table.col.text.Referred'),
															 $.i18n.prop('companyInfo.table.col.text.url'),
													         $.i18n.prop('companyInfo.table.col.text.telphone'), 
													         $.i18n.prop('companyInfo.table.col.text.fax'),
															 $.i18n.prop('companyInfo.table.col.text.postcode'),
														     $.i18n.prop('companyInfo.table.col.text.welcome'),
															 $.i18n.prop('companyInfo.table.col.text.memo') ],
												colModel : [
														{
															name : "companycode",
															index : "companycode",
															align : 'center',
															width : 100,
															sortable : false
															/* hidden : true */
														},
														{
															name : "companyname",
															index : 'companyname',
															align : 'center',
															width : 100,
															sortable : false
														},
														{
															name : "companylinkname",
															index : "companylinkname",
															align : 'center',
															width : 100,
															sortable : false,
															formatter : function(
																	el,
																	options,
																	rowData) {
																if (rowData.companylinkname == 'null'
																		|| rowData.companylinkname == null) {
																	return "";
																} else {
																	return rowData.companylinkname;
																}

															}
														},
														{
															name : "companyshortname",
															index : "companyshortname",
															align : 'center',
															width : 100,
															sortable : false
														},
														{
															name : "companywebsite",
															index : "companywebsite",
															align : 'center',
															width : 100,
															sortable : false
														},
														{
															name : "companyemali",
															index : "companyemali",
															align : 'center',
															width : 100,
															/* hidden : true, */
															sortable : false
														},
														{
															name : "companytelphone",
															index : "companytelphone",
															align : 'center',
															width : 100,
															sortable : false
														},
														{
															name : "companyfax",
															index : 'companyfax',
															align : 'center',
															width : 100,
															sortable : false
														},
														{
															name : "companypostcode",
															index : 'companypostcode',
															align : 'center',
															width : 100,
															sortable : false
														},
														{
															name : "companywelcome",
															index : 'companywelcome',
															align : 'center',
															width : 100,
															sortable : false,
															formatter : function(
																	el,
																	options,
																	rowData) {
																if (rowData.companywelcome == 'null'
																		|| rowData.companywelcome == null) {
																	return "";
																} else {
																	return rowData.companywelcome;
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
												    var d= '<input type="button" value="删除" onclick="deleteCompany(\''+rowData.companycode+'\')" style="width:30px;float:right;margin-right:10px;"/>';
												    var u= '<input type="button" value="修改" onclick="addMakeCard(\'修改类型\',\'修改\','+ options.rowId +')" style="width:30px;float:right;margin-right:10px;"/>';
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
												sortname : 'companycode',
												sortorder : 'desc',
												toppager : true,
												viewrecords : true,
												loadComplete : function() {
													var myGrid = $("#datatable");
													$("#cb_" + myGrid[0].id)
															.hide();
												},
												ondblClickRow : function(companycode) {
													RowData = jQuery(this)
															.jqGrid(
																	"getRowData",
																	companycode);
													if (RowData.companycode) {
														selectMakeCard("<spring:message code='account.table.col13.text' />",
																companycode);
													}
												},
											});
							$("#datatable").jqGrid("navGrid", "#pager2", {
								cloneToTop : true,
								/* search : false, */
								/* edit:false, */
								addfunc : addMakeCard, // (1) 点击添加按钮，则调用addMakeCard方法  
								addtext : $.i18n.prop("btn.add"),
								editfunc : updateMakeCard, // (2) 点击添加按钮，则调用addMakeCard方法  
								edittext : $.i18n.prop("btn.update"),
								delfunc : deleteCompany, // (3) 点击添加按钮，则调用deleteCompany方法   
								deltext : $.i18n.prop("btn.delete"),
								searchfunc : searchCompany, // (4) 点击添加按钮，则调用searchCompany方法  
								searchtext : $.i18n.prop("btn.search"),
								alerttext : $.i18n.prop("param.minrow")
							// (4) 当未选中任何行而点击编辑、删除、查看按钮时，弹出的提示信息  
							});
						});
		//查询数据 
		var searchCompany = function(row) {
			var dialogCode = dialog({
				title : $.i18n.prop('btn.search'),
				content : $('#searchMemberCardForm'),
				okValue : $.i18n.prop('btn.ok'),
				ok : function() {
					$
							.ajax({
								type : "POST",
								url : "${pageContext.request.contextPath}/companyInfoController/selectCompany.do",
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
			dialogCode.width(350);
			dialogCode.showModal();
		};
		//删除数据 
		function deleteCompany(companyId) {
			//var ids = $("#datatable").jqGrid('getGridParam','selarrrow');
			var ids = "";//new Array();
			$
					.each(
							companyId,
							function(i, n) { //把formarray转换为json
								if ((n != undefined && n != "")) {
									//ids.push($("#datatable").getRowData(n).rolecode);
									ids += (ids == "" ? $("#datatable")
											.getRowData(n).companycode
											: ","
													+ $("#datatable")
															.getRowData(n).companycode);
								}
							});
			if (ids.length > 1) {
				showMsg("无法多行删除");
			} else {
				var dialogCode = dialog({
					title : $.i18n.prop('tip.title'),
					content :  $.i18n.prop('tip.confirm.delete'),
					okValue : $.i18n.prop('btn.ok'),
					ok : function() {
						this.title($.i18n.prop('tip.loading'));
						$
								.ajax({
									type : "post",
									url : "${pageContext.request.contextPath}/companyInfoController/delectCompany.do",
									data : {
										"ids" : ids
									},
									dataType : 'json',//JSON.stringify(
									success : function(msg) {
										$("#datatable").trigger('reloadGrid');
										showMsg("<spring:message code='companyInfo.prompt3.information' />");
									},
									error : function(XMLHttpRequest, textStatus,
											errorThrown) {
										showMsg("<spring:message code='companyInfo.prompt4.information' />");
									}
								});
						return true;
					},
					cancelValue : $.i18n.prop('btn.cancel'),
					cancel : function() {
					}
				});
				dialogCode.width(200);
				dialogCode.showModal();
			}
		};
		var dialogCode = null;
		//新增 
		var addMakeCard = function(row) { //弹出修改和添加原因的窗口
			document.getElementById("companyCode").disabled = false;
			document.getElementById("companyName").disabled = false;
			document.getElementById("companyLinkName").disabled = false;
			document.getElementById("companyShortName").disabled = false;
			document.getElementById("companyWebsite").disabled = false;
			document.getElementById("companyEmali").disabled = false;
			document.getElementById("companyTelphone").disabled = false;
			document.getElementById("companyFax").disabled = false;
			document.getElementById("companyPostCode").disabled = false;
			document.getElementById("companyWelcome").disabled = false;
			document.getElementById("memo").disabled = false;
			dialogCode = dialog({
				title :  $.i18n.prop('btn.add'),
				content : $('#comp_1'),
				okValue : $.i18n.prop('btn.ok'),
				ok : function() {
					if ($("#companyCode").val() == '') {
						showMsg("<spring:message code='companyInfo.prompt5.information' />");
						return false;
					} else if ($("#companyName").val() == '') {
						showMsg("<spring:message code='companyInfo.prompt6.information' />");
						return false;
					} else if ($("#companyShortName").val() == '') {
						showMsg("<spring:message code='companyInfo.prompt7.information' />");
						return false;
					} else {
						$
								.ajax({
									type : "POST",
									url : "${pageContext.request.contextPath}/companyInfoController/saveOrUpdateCompany.do",
									data : {
										companycode : $("#companyCode").val(),
										companyname : $("#companyName").val(),
										companyshortname : $("#companyShortName")
												.val(),
										companywebsite : $("#companyWebsite").val(),
										companyemali : $("#companyEmali").val(),
										companytelphone : $("#companyTelphone")
												.val(),
										companyfax : $("#companyFax").val(),
										companypostcode : $("#companyPostCode")
												.val(),
										companylinkname : $("#companyLinkName")
												.val(),
										companywelcome : $("#companyWelcome").val(),
										memo : $("#memo").val()
									},
									success : function(msg) {
										showMsg(eval("(" + msg + ")").msg);
										submitForm();
										$('#comp_1').clearForm(true);
									}
								});
					}
				},
				cancelValue : $.i18n.prop('btn.cancel'),
				cancel : function() {
					$('#comp_1').clearForm(true);
				}
			});
			dialogCode.width(450);
			dialogCode.height(450);
			dialogCode.showModal();
		};
        //修改 
		var updateMakeCard = function(row) { //弹出修改和添加原因的窗口
			document.getElementById("companyCode").disabled = true;
			document.getElementById("companyName").disabled = false;
			document.getElementById("companyLinkName").disabled = false;
			document.getElementById("companyShortName").disabled = false;
			document.getElementById("companyWebsite").disabled = false;
			document.getElementById("companyEmali").disabled = false;
			document.getElementById("companyTelphone").disabled = false;
			document.getElementById("companyFax").disabled = false;
			document.getElementById("companyPostCode").disabled = false;
			document.getElementById("companyWelcome").disabled = false;
			document.getElementById("memo").disabled = false;
			if (row > 0) {//说明是修改
				var col = $("#datatable").jqGrid('getRowData', row);
				$("#companyCode").val(col.companycode);
				$("#companyName").val(col.companyname);
				if (col.companylinkname == null || col.companylinkname == '') {
					$("#companyLinkName").val();
				} else {
					$("#companyLinkName").val(col.companylinkname);
				}
				$("#companyShortName").val(col.companyshortname);
				$("#companyWebsite").val(col.companywebsite);
				$("#companyEmali").val(col.companyemali);
				$("#companyTelphone").val(col.companytelphone);
				$("#companyFax").val(col.companyfax);
				$("#companyPostCode").val(col.companypostcode);
				if (col.companywelcome == null || col.companywelcome == '') {
					$("#companyWelcome").val();
				} else {
					$("#companyWelcome").val(col.companywelcome);
				}
				$("#memo").val(col.memo);
			}
			dialogCode = dialog({
				title :$.i18n.prop('btn.update'),
				content : $('#comp_1'),
				okValue : $.i18n.prop('btn.ok'),
				ok : function() {
					$
							.ajax({
								type : "POST",
								url : "${pageContext.request.contextPath}/companyInfoController/saveOrUpdateCompany.do",
								data : {
									companycode : $("#companyCode").val(),
									companyname : $("#companyName").val(),
									companyshortname : $("#companyShortName")
											.val(),
									companywebsite : $("#companyWebsite").val(),
									companyemali : $("#companyEmali").val(),
									companytelphone : $("#companyTelphone")
											.val(),
									companyfax : $("#companyFax").val(),
									companypostcode : $("#companyPostCode")
											.val(),
									companylinkname : $("#companyLinkName")
											.val(),
									companywelcome : $("#companyWelcome").val(),
									memo : $("#memo").val()
								},
								success : function(msg) {
									showMsg(eval("(" + msg + ")").msg);
									submitForm();
									$('#comp_1').clearForm(true);
								}
							});
				},
				cancelValue : $.i18n.prop('btn.cancel'),
				cancel : function() {
					$('#comp_1').clearForm(true);
				}
			});
			dialogCode.width(450);
			dialogCode.height(450);
			dialogCode.showModal();
		};
		//查看窗口
		function selectMakeCard(title, row) {
			document.getElementById("companyCode").disabled = true;
			document.getElementById("companyName").disabled = true;
			document.getElementById("companyLinkName").disabled = true;
			document.getElementById("companyShortName").disabled = true;
			document.getElementById("companyWebsite").disabled = true;
			document.getElementById("companyEmali").disabled = true;
			document.getElementById("companyTelphone").disabled = true;
			document.getElementById("companyFax").disabled = true;
			document.getElementById("companyPostCode").disabled = true;
			document.getElementById("companyWelcome").disabled = true;
			document.getElementById("memo").disabled = true;
			/* alert(row); */
			var col = $("#datatable").jqGrid('getRowData', row);
			$("#companyCode").val(col.companycode);
			$("#companyName").val(col.companyname);
			if (col.companylinkname == null || col.companylinkname == '') {
				$("#companyLinkName").val();
			} else {
				$("#companyLinkName").val(col.companylinkname);
			}
			$("#companyShortName").val(col.companyshortname);
			$("#companyWebsite").val(col.companywebsite);
			$("#companyEmali").val(col.companyemali);
			$("#companyTelphone").val(col.companytelphone);
			$("#companyFax").val(col.companyfax);
			$("#companyPostCode").val(col.companypostcode);
			if (col.companywelcome == null || col.companywelcome == '') {
				$("#companyWelcome").val();
			} else {
				$("#companyWelcome").val(col.companywelcome);
			}
			$("#memo").val(col.memo);
			dialogCode = dialog({
				title : title,
				content : $('#comp_1'),
				cancelValue : $.i18n.prop('btn.cancel'),
				cancel : function() {
					$('#comp_1').clearForm(true);
				}
			});
			dialogCode.width(450);
			dialogCode.height(450);
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