<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path;
	request.setAttribute("basePath", basePath);
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>违规记录查询</title>
<%@ include file="../../common/head.jsp"%>
<%@ include file="../../common/theme.jsp"%>
<style>
td#datatable_toppager_center {
	display: none;
}
</style>
</head>
<body style="overflow-x: hidden">
	<div id="showadd" class="list" style="display: none;"></div>
	<div class="content" align="center" style="display: none;">
		<form id="searchMemberCardForm"
			action="${pageContext.request.contextPath}/punishmentRecord/selectpunishmentRecordpage.do"
			method="post">
			<div class="list">
				<ul>
					<li>用户卡号：<input type="text" name="usercardid" id="usercardid" /></li>
					<li>用户名：<input type="text" name="username" id="username" /></li>
					<li>箱编号：<input type="text" name="boxid" id="boxid" /></li>
					<li>违规查询：<select id="punishstate" name="punishstate"><option value="0" selected="selected"> 正常     </option> <option value="1"> 违规     </option></select>    </li>
				</ul>
			</div>
		</form>
	</div>
	<!-- table的显示成 -->
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
				if (document.activeElement.id == "punishstate") {
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
		};
		function formToJson(boxtypecode) {//把form表单的所有数据变成json对象
			var formArray = $('#' + boxtypecode).serializeArray();//数组里面首先放入充值记录表的内容
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
									.innerWidth() + 25;
							$("#datatable")
									.jqGrid(
											{
												url : '${pageContext.request.contextPath}/punishmentRecord/selectpunishmentRecordpage.do',
												datatype : "json",
												mtype : "POST",
												width : document.body.offsetWidth - 60,
												height : window.screen.availHeight - 325,
												//autowidth:true,
												editable : false,
												shrinkToFit : true,
												colNames : [ '终端ID','用户卡号','用户名称','柜号', '箱编号',  
														 '存物时间', '结束时间', '应收金额', '实收金额', 
														 '违规状态','创建人', '创建时间', '操作' ],
												colModel : [
														{
															name : "terminalid",
															index : "terminalid",
															align : 'center',
															width : 180,
															sortable : false,
															hidden : true
														},
														{
															name : "usercardid",
															index : "usercardid",
															align : 'center',
															width : 180,
															sortable : false
														},
														{
															name : "username",
															index : "username",
															align : 'center',
															width : 180,
															sortable : false,
															formatter : function(
																	el,
																	options,
																	rowData) {
																if(rowData.storeInRecordEntity.username == null || rowData.storeInRecordEntity.username == ""){
																	return "";
																}else {
																	return rowData.storeInRecordEntity.username;	
																}
																			
															}
														},
														{
															name : "displayname",
															index : "displayname",
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
															name : "storeintime",
															index : "storeintime",
															align : 'center',
															width : 180,
															sortable : false,
															formatter : function(
																	el,
																	options,
																	rowData) {
																var t = rowData.storeintime;
																var d = new Date();
																d.setTime(t);
																var storeintime = d
																		.Format("yyyy-MM-dd  hh:mm:ss");

																if (storeintime != "NaN-aN-aN aN:aN:aN") {
																	return storeintime;
																} else {
																	return "";
																}
															}
														},
														{
															name : "enddate",
															index : "enddate",
															align : 'center',
															width : 180,
															sortable : false,
															formatter : function(
																	el,
																	options,
																	rowData) {
																var t = rowData.enddate;
																var d = new Date();
																d.setTime(t);
																var enddate = d
																		.Format("yyyy-MM-dd  hh:mm:ss");

																if (enddate != "NaN-aN-aN aN:aN:aN") {
																	return enddate;
																} else {
																	return "";
																}

															}
														},
														{
															name : "money",
															index : "money",
															align : 'center',
															width : 180,
															sortable : false,
															hidden:true
														},
														{
															name : "realmoney",
															index : "realmoney",
															align : 'center',
															width : 180,
															sortable : false,
															hidden:true
														},
														{
															name : "punishstate",
															index : "punishstate",
															align : 'center',
															width : 180,
															sortable : false,formatter:function(el,options,rowData){
											                    if(el==0){return '正常';}else{return '违规';} 
											                }
														},
														{
															name : "makeopcode",
															index : "makeopcode",
															align : 'center',
															width : 180,
															sortable : false,
															hidden : true
														},
														{
															name : "makedate",
															index : "makedate",
															align : 'center',
															width : 180,
															sortable : false,
															formatter : function(
																	el,
																	options,
																	rowData) {
																var t = rowData.makedate;
																var d = new Date();
																d.setTime(t);
																var makedate = d
																		.Format("yyyy-MM-dd  hh:mm:ss");
																if (makedate != "NaN-aN-aN aN:aN:aN") {
																	return makedate;
																} else {
																	return "";
																}

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
																if(rowData.punishstate == '0'){
																	return "";
																}else{
																	var u = '<input type="button" value="解禁" onclick="updateMakeCard(\''
																		+ rowData.terminalid
																		+ '\',\''
																		+ rowData.boxid
																		+ '\',\''
																		+ rowData.usercardid
																		+ '\',\''
																		+ rowData.storeintime
																		+ '\',\''
																		+ rowData.enddate
																		+ '\',\''
																		+ rowData.storeInRecordEntity.username
																		+ '\',\''
																		+ rowData.punishstate
																		+ '\')" style="width:50px;float:right;margin-right:10px;"/>';
																return u;	
																}

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
												sortname : 'boxtypecode',
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
													if (RowData.boxtypecode) {
														selectMakeCard(
																"<spring:message code='account.table.col13.text' />",
																rowid);
													}
												},
											});
							$("#datatable").jqGrid("navGrid", "#pager2", {
								cloneToTop : true,
								/* search : false, */
								add : false,
								del : false,
								edit : false,
								searchfunc : searchBoxType, // (1) 点击添加按钮，则调用deleteTerminalAlarmDiary方法  
								searchtext : $.i18n.prop("btn.search"),
								alerttext : $.i18n.prop("param.minrow")
							});
						});
		//查询数据
		var dialogCode = "";
		var searchBoxType = function(row) {
			var dialogCode = dialog({
				title : $.i18n.prop('btn.search'),
				content : $('#searchMemberCardForm'),
				okValue : $.i18n.prop('btn.ok'),
				ok : function() {
					var usercardid = document.getElementById("usercardid").value;
					var username = document.getElementById("username").value;
					var boxid = document.getElementById("boxid").value;
					var punishstate = document.getElementById("punishstate").value;
					$
							.ajax({
								type : "POST",
								url : "${pageContext.request.contextPath}/punishmentRecord/selectpunishmentRecordpage.do", 
								data : {
									usercardid : usercardid,
									username : username,
									boxid : boxid,
									punishstate : punishstate
								},
								success : function(msg) {
									if (msg != null) {
										showMsg("查询成功");
									}
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
		//添加 修改 箱体类型 
		function updateMakeCard(terminalid, boxid, usercardid, storeintime,
				enddate,username,punishstate) {
			var dialogCode = dialog({
				title : $.i18n.prop('tip.title'), 
				content : "是否要解禁   箱号:"+boxid+" 违规状态",  
				okValue : $.i18n.prop('btn.ok'), 
				ok : function() {
					if(terminalid == null || terminalid  == ""){
						showMsg("解禁失败"); 
						return true;
					}else if (boxid == null || boxid  == "") {
						showMsg("解禁失败"); 
						return true;
					}else if (usercardid == null || usercardid  == "") {
						showMsg("解禁失败"); 
						return false;
					}else if (enddate == null || enddate  == "") {
						showMsg("解禁失败"); 
						return true;
					}else if (punishstate == null || punishstate  == "" || punishstate == 0) {
						showMsg("无法为正常状态解禁");  
						return true;
					}else{
						$
						.ajax({
							type : "POST",
							url : "${pageContext.request.contextPath}/punishmentRecord/saveOrUpdatePunishmentRecord.do",
							data : {
								terminalid : terminalid,
								boxid : boxid,
								usercardid : usercardid,
								storeintime : storeintime,
								enddate : enddate
							},
							success : function(msg) {
								showMsg(eval("(" + msg + ")").msg);
								submitForm();		
								location.href = location.href;
							},
						});	
					}

				},
				cancelValue : $.i18n.prop('btn.cancel'),
				cancel : function() {
				}
			});
			dialogCode.width(250);
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