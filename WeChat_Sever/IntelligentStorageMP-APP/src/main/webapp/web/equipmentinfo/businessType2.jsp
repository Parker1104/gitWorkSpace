<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path;
	request.setAttribute("basePath", basePath);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>业务类型设置</title>
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

td#datatable_toppager_center {
	display: none;
}

.list tr input {
	border: 1px solid #cccccc;
	border-radius: 3px;
	text-indent: 5px;
	font-size: 12px;
}

.list table {
	border-right: 1px solid #804040;
	border-bottom: 1px solid #804040;
	border-collapse: collapse;
	border: 0px solid #ccc;
	font-size: 12px;
}

.list table td {
	border-left: 1px solid #804040;
	border-top: 1px solid #804040;
	border: 0px solid #ccc;
	font-size: 12px;
}

.list tr select {
	border: 1px solid #ccc;
	border-radius: 3px;
	font-size: 12px;
}
</style>
</head>
<body>
	<!-- <div class="title">
    <p>查询条件</p>
</div> -->
	<div class="content">
		<div class="tab">
			<table id="datatable"></table>
			<div id="pager2"></div>
		</div>
	</div>
	 <div id="showadd" style="display: none;">
		<form id="modifyCode" action="${basePath}/businessController/addNew"
			method="post">
			<table width="618" height="200" class="list" border="0"
				cellpadding="2" cellspacing="2" style="font-size: 12px;">
				<tr>
					<td width="100"><div align="right">业务类型名称：</div></td>
					<td width="452" colspan="2"><div align="left">
							<input id="businessname" name="businessname" />
						</div></td>
					
				</tr>
				
				<tr>
					<td><div align="right">收费功能：</div></td>
					<td colspan="2"><div align="left">
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input id="chargefuncswitch1"
								name="chargefuncswitch" type="radio" value="0"
								style="width: 20px; height: 15px;" />
							开&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input
								id="chargefuncswitch2" name="chargefuncswitch" type="radio"
								checked="checked" value="1" style="width: 20px; height: 15px;" />
							关
						</div></td>
				</tr>
				<tr id="sf" style="display: none;">
					<td height="28"><div align="right"></div></td>
					<td colspan="2"><div align="left">收费模式： <select id="chargemode" name="chargemode"
								style="width: 135px; height: 21px;">
								<c:forEach var="m" items="${master}">
									<option value="<c:out value='${m.dictcode}'/>">
										<c:out value='${m.dictname}' />
									</option>
								</c:forEach>
							</select>
						</div></td>
				</tr>
				
					<td height="20"><div align="right">备注：</div></td>
					<td colspan="2"><div align="left">
							<!-- <input id="memo" name="memo" type="text" /> -->
							<textarea  name="memo" id="memo"  cols="40" rows="3"  style="width: 200px;
	                        resize:none; border: 1px solid #ccc; border-radius: 3px;"></textarea> 
						</div></td>
				</tr>
			</table>
		</form>
	</div> 
	
	<div id="showupdate" class="list" style="display: none;">
		<form id="modifyCode2" action="${basePath}/businessController/updateNew"
			method="post">
			<table width="618" height="200" class="list" border="0"
				cellpadding="0" cellspacing="0" style="font-size: 12px;">
				<tr>
					<td width="100"><div align="right">业务类型名称：</div></td>
					<td width="452" colspan="2"><div align="left">
							<input id="businessname1" name="businessname" style="width:33%;"/>
						</div></td>
				</tr>
				 <tr style="display: none;">
					<td><div align="right">业务类型编码：</div></td>
					<td colspan="2"><div align="left">
							<input id="businesscode" name="businesscode" type="text" readonly />
						</div></td>
				</tr> 
				
				<tr>
					<td><div align="right">收费功能：</div></td>
					<td colspan="2"><div align="left">
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input id="chargefuncswitch3"
								name="chargefuncswitchs" type="radio" value="0"
								style="width: 15px; height: 15px;" />
							开&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input
								id="chargefuncswitch4" name="chargefuncswitchs" type="radio"
								checked="checked" value="1" style="width: 15px; height: 15px;" />
							关
						</div></td>
				</tr>
				<tr id="sf1" style="display: none;">
					<td height="28"><div align="right">收费模式：</div></td>
					<td colspan="2"><div align="left">
							 <select id="chargemode1" name="chargemode"
								style="width: 135px; height: 21px;">-
								<c:forEach var="m" items="${master}">
									<option value="<c:out value='${m.dictcode}'/>">
										<c:out value='${m.dictname}' />
									</option>
								</c:forEach>
							</select>
						</div></td>
				</tr>
				<tr>
					<td height="20"><div align="right">备注：</div></td>
					<td colspan="2"><div align="left">
							<!-- <input id="memo1" name="memo" type="text" /> -->
							<textarea  name="memo" id="memo1"  cols="40" rows="3"  style="width: 200px;
	                        resize:none; border: 1px solid #ccc; border-radius: 3px;"></textarea> 
						</div></td>
						
				</tr>
			</table>
		</form>
	</div>
	<script type="text/javascript">
		$(document).ready(function() {
			$('input[type=radio][name=chargefuncswitch]').change(function() {
				if (this.value == 0) {
					$("#sf").show();
				} else if (this.value == 1) {
					$("#sf").hide();
				}
			});
			$('input[type=radio][name=limitswitch]').change(function() {
				if (this.value == 0) {
					$("#xz").show();
					var lc = document.getElementById("limitcondition").value
					if (lc == "1") {
						$("#mf").show();
						$("#mf_0").show();
						$("#mf_1").show();
						$("#mf_2").show();
						$("#mf_3").show();
						$("#mf_4").show();
						$("#mf_5").show();
						$("#mf_6").show();
						$("#mf_7").show();
						document.getElementById("violationsLimit_1").disabled = true;
						document.getElementById("violationsLimit_2").disabled = true;
						document.getElementById("PromptTime").disabled = true;
						document.getElementById("timeout_text_1").disabled = true;
						document.getElementById("timeout_text_2").disabled = true;
						document.getElementById("timeout_text_3").disabled = true;
						document.getElementById("timeout_1").disabled = true;
						document.getElementById("timeout_2").disabled = true; 	
					} else if (lc == "2") {
						$("#mf").hide();
						$("#mf_0").hide();
						$("#mf_1").hide();
						$("#mf_2").hide();
						$("#mf_3").hide();
						$("#mf_4").hide();
						$("#mf_5").hide();
						$("#mf_6").hide();
						$("#mf_7").hide();
					}

				} else if (this.value == 1) {
					$("#xz").hide();
					$("#mf").hide();
					$("#mf_0").hide();
					$("#mf_1").hide();
					$("#mf_2").hide();
					$("#mf_3").hide();
					$("#mf_4").hide();
					$("#mf_5").hide();
					$("#mf_6").hide();
					$("#mf_7").hide();
				}
			});
			$('input[type=radio][name=limitswitchs]').change(function() {
				if (this.value == 0) {
					$("#xz1").show();
					var lc = document.getElementById("limitcondition1").value
					if (lc == "1") {
						$("#mf1").show();
						$("#mf2").show();
						$("#mf3").show();
						$("#mf4").show();
						$("#mf6").show();
						$("#mf7").show();
						$("#mf5").show();
						$("#mf8").show();
						$("#mf9").show();
					} else if (lc == "2") {
						$("#mf1").hide();
						$("#mf2").hide();
						$("#mf3").hide();
						$("#mf4").hide();
						$("#mf6").hide();
						$("#mf7").hide();
						$("#mf5").hide();
						$("#mf8").hide();
						$("#mf9").hide();
					}
				} else if (this.value == 1) {
					$("#xz1").hide();
					$("#mf1").hide();
					$("#mf2").hide();
					$("#mf3").hide();
					$("#mf4").hide();
					$("#mf5").hide();
					$("#mf6").hide();
					$("#mf7").hide();
					$("#mf8").hide();
					$("#mf9").hide();
				}
			});

		});
		function mode() {
			var lc = $("#limitcondition").val();
			var lcc = $("#limitcondition1").val();
			if (lc == "1") {
				$("#mf").show();
				$("#mf_0").show();
				$("#mf_1").show();
				$("#mf_2").show();
				$("#mf_3").show();
				$("#mf_4").show();
				$("#mf_5").show();
				$("#mf_6").show();
				$("#mf_7").show();
			} else if (lc == "2") {
				$("#mf").show();
				$("#mf_0").show();
				$("#mf_1").hide();
				$("#mf_2").hide();
				$("#mf_3").hide();
				$("#mf_4").hide();
				$("#mf_5").hide();
				$("#mf_6").hide();
				$("#mf_7").hide();
			}
		}
		function modes() {
			var lcc = $("#limitcondition1").val();
			if (lcc == "1") {
				$("#mf1").show();
				$("#mf2").show();
				$("#mf3").show();
				$("#mf4").show();
				$("#mf5").show();
				$("#mf6").show();
				$("#mf7").show();
				$("#mf8").show();
				$("#mf9").show();
			} else if (lcc == "2") {
				$("#mf1").show();
				$("#mf2").show();
				$("#mf3").hide();
				$("#mf4").hide();
				$("#mf5").hide();
				$("#mf6").hide();
				$("#mf7").hide();
				$("#mf8").hide();
				$("#mf9").hide();
			}
		}
		//增加  
		$("#violationsLimitCheckbox").click(function(){
		if($(this).is(':checked')){
			document.getElementById("violationsLimit_1").disabled = false;
			document.getElementById("violationsLimit_2").disabled = false;	
			document.getElementById("PromptTime").disabled = false;
		}else{
			document.getElementById("violationsLimit_1").disabled = true;
			document.getElementById("violationsLimit_2").disabled = true;
			document.getElementById("PromptTime").disabled = true;
		}
	   });
		$("#timeoutCheckbox").click(function(){
			if($(this).is(':checked')){
				document.getElementById("timeout_text_2").disabled = false;	
				document.getElementById("timeout_text_3").disabled = false;
				document.getElementById("timeout_1").disabled = false;
				document.getElementById("timeout_2").disabled = false;
			}else{
				document.getElementById("timeout_text_2").disabled = true;
				document.getElementById("timeout_text_3").disabled = true;
				document.getElementById("timeout_1").disabled = true;
				document.getElementById("timeout_2").disabled = true;
			}
		   });
		//修改
		$("#violationsLimitCheckbox1").click(function(){
		if($(this).is(':checked')){
			document.getElementById("violationsLimit_01").disabled = false;
			document.getElementById("violationsLimit_02").disabled = false;	
			document.getElementById("PromptTime1").disabled = false;
		}else{

			document.getElementById("violationsLimit_01").disabled = true;
			document.getElementById("violationsLimit_02").disabled = true;
			document.getElementById("PromptTime1").disabled = true;
		}
	   });
		$("#timeoutCheckbox1").click(function(){
			if($(this).is(':checked')){
				document.getElementById("timeout_text_02").disabled = false;	
				document.getElementById("timeout_text_03").disabled = false;
				//document.getElementById("timeout_01").disabled = false;
				document.getElementById("timeout_02").disabled = false;
			}else{
				document.getElementById("timeout_text_02").disabled = true;
				document.getElementById("timeout_text_03").disabled = true;
				//document.getElementById("timeout_01").disabled = true;
				document.getElementById("timeout_02").disabled = true;
			}
		   });
		
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
		$(document).ready(function() {
			var tableWidth = $("#datatable").parent().innerWidth();
			$("#datatable").jqGrid({
				url : '${basePath}/businessController/list',
				datatype : "json",
				mtype : "POST",
				width : document.body.offsetWidth - 60,
				height : window.screen.availHeight - 320,
				//autowidth:true,
				editable : false,
				shrinkToFit : true,
				colNames : [ '业务类型编码', '业务类型名称', '配置参数名称', '配置参数值', '备注' ],
				colModel : [ {
					name : "businesscode",
					index : "businesscode",
					align : 'center',
					width : 100,
					sortable : false
				}, {
					name : "businessname",
					index : 'businessname',
					align : 'center',
					width : 150,
					sortable : false,
					hidden : false
				}, {
					name : "configname",
					index : "configname",
					align : 'center',
					width : 120,
					sortable : false,
					hidden : true
				}, {
					name : "configvalue",
					index : "configvalue",
					align : 'center',
					width : 80,
					sortable : false,
					hidden : true,
					sformatter : function(el, options, rowData) {
						return el;
					}
				}, {
					name : "memo",
					index : "memo",
					align : 'left',
					width : 160,
					sortable : false,
					hidden : true
				},
				/* {name:"",index:'',align:'center',width:50,formatter:function(el,options,rowData){
				        //var d= '[<a href="javascript:void(0)" onclick="delRepository(\''+rowData.businesscode+'\',\''+rowData.configname+'\')">删除</a>] ';
				        //var u= '[<a href="javascript:void(0)" onclick="addMakeCard(\'修改类型\',\'修改\','+ options.rowId +')">修改</a>] ';
				        var d= '<input type="button" value="删除" onclick="delRepository(\''+rowData.businesscode+'\',\''+rowData.configname+'\')" style="width:30px;float:right;margin-right:25px;"/>';
				        var u= '<input type="button" value="修改" onclick="addMakeCard(\'修改类型\',\'修改\','+ options.rowId +')" style="width:30px;float:right;margin-right:65px;"/>';
				        return u+d;
				}} */
				],
				sortable : false,
				rowNum : 10,
				rownumbers : false,
				multiselect : true,
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
				multiboxonly : true,
				loadComplete : function() {
					var myGrid = $("#datatable");
					$("#cb_" + myGrid[0].id).hide();
				},
				ondblClickRow : function(rowid) {
					RowData = jQuery(this).jqGrid("getRowData", rowid);
					if (RowData.businesscode) {
						list('查看', RowData.businesscode);
					}
				},
			});
			$("#datatable").jqGrid("navGrid", "#pager2", {
				cloneToTop : true,
				search : false,
				addfunc : openDialog4Adding, // (1) 点击添加按钮，则调用openDialog4Adding方法  
				addtext : "新增",
				editfunc : openDialog4Updating, // (2) 点击添加按钮，则调用openDialog4Updating方法  
				edittext : "修改",
				delfunc : openDialog4Deleting, // (3) 点击添加按钮，则调用openDialog4Deleting方法  
				deltext : "删除",
				alerttext : "请选择需要操作的数据行!" // (4) 当未选中任何行而点击编辑、删除、查看按钮时，弹出的提示信息  
			});
		});
		var openDialog4Adding = function(title, okValue, row) {
			$("#validateidentities2").attr("checked", "checked");
			$("#onecardonebox2").attr("checked", "checked");
			$("#chargefuncswitch2").attr("checked", "checked");
			$("#limitswitch2").attr("checked", "checked");
			$("#sf").hide();
			$("#xz").hide();
			dialogCode = dialog({
				title : '新增',
				content : $('#showadd'),
				okValue : '新增',
				ok : function() {
					var bs = $("#businessname").val();//业务类型名称
					//var ft = $("#chargefuncswitch1").val();//收费功能
					var tb = $("#timebetween").val(); //存物间隔时间
					if (bs == null || bs == "") {
						showMsg("请填写业务类型名称！");
						return false;
					} /* else if (tb == null || tb == "") {
						showMsg("请填写存物间隔时间！");
						return false;
					} */ else {
						$('#modifyCode').ajaxSubmit({
							success : function(msg) {
								showMsg(eval("(" + msg + ")").msg);
								submitForm();
								$('#modifyCode').clearForm(true);
							}
						});
						return true;
					}
				},
				cancelValue : '关闭',
				cancel : function() {
					$('#modifyCode').clearForm(true);
				}
			});
			dialogCode.width(600);
			dialogCode.showModal();
		};
		var openDialog4Updating = function(row) { // (6) 接受选中行的id为参数  
			var col = $("#datatable").jqGrid('getRowData', row);
			$
					.ajax({
						type : "POST",
						url : "${basePath}/businessController/updateAll?code="
								+ col.businesscode,
						success : function(msg) {
							var myobj = (eval("(" + msg + ")").msg);
							$
									.each(
											myobj,
											function(i, val) {
												$('#businesscode').val(
														val.businesscode);
												$('#businesscode').attr(
														"readonly", true);
												$('#businessname1').val(
														val.businessname);
												$("#memo1").val(val.memo);
												if (val.configname == "OneCardOneBox"
														&& val.configvalue == 1) {
													$("#onecardonebox3").attr(
															"checked",
															"checked");
												} else if (val.configname == "OneCardOneBox"
														&& val.configvalue == 0) {
													$("#onecardonebox4").attr(
															"checked",
															"checked");
												} else if (val.configname == "RunTime") {
													$("#runtime1").attr(
															"value",
															val.configvalue);
												} else if (val.configname == "ValidateIdentities"
														&& val.configvalue == 1) {
													$("#validateidentities3")
															.attr("checked",
																	"checked");
												} else if (val.configname == "ValidateIdentities"
														&& val.configvalue == 0) {
													$("#validateidentities4")
															.attr("checked",
																	"checked");
												} else if (val.configname == "Rule") {
													$("#rule1").attr("value",
															val.configvalue);
												} else if (val.configname == "ChargeFuncSwitch") {
													if (val.configvalue == "0") {
														$("#chargefuncswitch3")
																.attr(
																		"checked",
																		"checked");
														$("#sf1").show();
													} else if (val.configvalue == "1"
															|| val.configvalue == "") {
														$("#chargefuncswitch4")
																.attr(
																		"checked",
																		"checked");
														$("#sf1").hide();
													} else {
														$("#chargefuncswitch4")
																.attr(
																		"checked",
																		"checked");
													}
												} else if (val.configname == "ChargeMode") {
													$("#chargemode1").attr(
															"value",
															val.configvalue);
												} else if (val.configname == "LimitSwitch") {
													if (val.configvalue == "0") {
														$("#limitswitch3")
																.attr(
																		"checked",
																		"checked");
														$("#xz1").show();
														//$("#mf1").show();
													} else if (val.configvalue == "1"
															|| val.configvalue == "") {
														$("#limitswitch4")
																.attr(
																		"checked",
																		"checked");
														$("#xz1").hide();
														$("#mf1").hide();
														$("#mf2").hide();
														$("#mf3").hide();
														$("#mf4").hide();
														$("#mf5").hide();
														$("#mf6").hide();
														$("#mf7").hide();
														$("#mf8").hide();
														$("#mf9").hide();
													} else {
														$("#limitswitch4")
																.attr(
																		"checked",
																		"checked");
													}
												} else if (val.configname == "Limitcondition") {
													$("#limitcondition1").attr(
															"value",
															val.configvalue);
													if (val.configvalue == "2") {
														$("#mf1").show();
														$("#mf2").show();
														$("#mf3").hide();
														$("#mf4").hide();
														$("#mf5").hide();
														$("#mf6").hide();
														$("#mf7").hide();
														$("#mf8").hide();
														$("#mf9").hide();
													} else if (val.configvalue == "1") {
														$("#mf1").show();
														$("#mf2").show();
														$("#mf3").show();
														$("#mf4").show();
														$("#mf5").show();
														$("#mf6").show();
														$("#mf7").show();
														$("#mf8").show();
														$("#mf9").show();
													} else {
														$("#mf1").hide();
														$("#mf2").hide();
														$("#mf3").hide();
														$("#mf4").hide();
														$("#mf5").hide();
														$("#mf6").hide();
														$("#mf7").hide();
														$("#mf8").hide();
														$("#mf9").hide();
													}
												} else if (val.configname == "ViolationsLimitCheckbox") {
													if (val.configvalue == 0) {
														document.getElementById("violationsLimit_01").disabled = true;
														document.getElementById("violationsLimit_02").disabled = true;

													} else {
														$("#violationsLimitCheckbox1").attr("checked", "checked"); 
														document.getElementById("violationsLimit_01").disabled = false;
														document.getElementById("violationsLimit_02").disabled = false;		
													}

												} else if (val.configname == "TimeBetween") {
													$('#timebetween1').val(
															val.configvalue);
												} else if (val.configname == "ViolationsLimit_1") {
													$('#violationsLimit_01')
															.val(
																	val.configvalue);
												} else if (val.configname == "ViolationsLimit_2") {
													$('#violationsLimit_02')
															.val(
																	val.configvalue);
												} else if (val.configname == "TimeoutCheckbox") {
													if (val.configvalue == 0) {									

													} else {
														document.getElementById("timeout_text_02").disabled = false;
														document.getElementById("timeout_text_03").disabled = false;
														document.getElementById("timeout_02").disabled = false; 
														$('#timeoutCheckbox1')
																.attr(
																		"checked",
																		"checked");
													}

												} else if (val.configname == "Timeout") {
													if (val.configvalue == 1) {
														$('#timeout_01').attr(
																"checked",
																"checked");
													} else {
														$('#timeout_02').attr(
																"checked",
																"checked");
													}
												} else if (val.configname == "Timeout_text_2") {
													$('#timeout_text_02').val(
															val.configvalue);
												} else if (val.configname == "Timeout_text_3") {
													$('#timeout_text_03').val(
															val.configvalue);
												} else if (val.configname == "NoneDoorLimit") {
													$('#noneDoorLimit1').val(
															val.configvalue);
												} else if (val.configname == "FreeTime") {
													$('#freetime1').val(
															val.configvalue);
												}else if (val.configname == "PromptTime") {
													$('#PromptTime1').val(
															val.configvalue);
												}else if (val.configname == "WeChatCheckbox") {
													if(val.configvalue == 1){	
														$("#WeChatCheckbox1").attr("checked","checked");
													}
												}else if (val.configname == "EmailCheckbox") {
													if(val.configvalue == 1){
													
														$("#EmailCheckbox1").attr("checked","checked");
													}
												}else if (val.configname == "SMSCheckbox") {
													if(val.configvalue == 1){
														
														$("#SMSCheckbox1").attr("checked","checked");
													}
												}
											});
							/* $('input,select,textarea',
									$('form[id="modifyCode2"]')).attr(
									'readonly', true);
							$('input,select,textarea',
									$('form[id="modifyCode2"]')).attr(
									'disabled', 'disabled'); */
						}
					});
			var d = dialog({
				title : "修改",
				content : $('#showupdate'),
				okValue : "确定",
				ok : function() {
					$('#modifyCode2').ajaxSubmit(
							{
								success : function(msg) {
									showMsg(eval("(" + msg + ")").msg);
									submitForm();
									$('#modifyCode2').clearForm(true);
									$('input,select,textarea',
											$('form[id="modifyCode2"]'))
											.removeAttr("readonly");
								}
							});
					return true;
				},
				cancelValue : '取消',
				cancel : function() {
					$('#modifyCode2').clearForm(true);
					$('input,select,textarea', $('form[id="modifyCode2"]'))
							.removeAttr("readonly");
				}
			});
			d.width(600);
			d.showModal();
		};
		var openDialog4Deleting = function(row) { // (8) 接受选中行的id为参数  
			var col = $("#datatable").jqGrid('getRowData', row);
			var d = dialog({
				title : '提示',
				content : '您确认想要删除吗？',
				okValue : '确定',
				ok : function() {
					this.title('提交中…');
					$.ajax({
						type : "POST",
						url : "${basePath}/businessController/delete?code="
								+ col.businesscode,
						success : function(msg) {
							showMsg(eval("(" + msg + ")").msg);
							$("#datatable").trigger('reloadGrid');
							setTimeout(function() {
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
		};
		function delRepository(code, name) {
			var d = dialog({
				title : '提示',
				content : '您确认想要删除吗？',
				okValue : '确定',
				ok : function() {
					this.title('提交中…');
					$.ajax({
						type : "POST",
						url : "${basePath}/businessController/delete?code="
								+ code,
						success : function(msg) {
							showMsg(eval("(" + msg + ")").msg);
							$("#datatable").trigger('reloadGrid');
							setTimeout(function() {
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
		}
		$(document).ready(function() {
			$('input[type=radio][name=chargefuncswitchs]').change(function() {
				if (this.value == 0) {
					$("#sf1").show();
				} else if (this.value == 1) {
					$("#sf1").hide();
				}
			});
			$('input[type=radio][name=limitswitchs]').change(function() {
				if (this.value == 0) {
					$("#xz1").show();
					$("#mf1").show();
				} else if (this.value == 1) {
					$("#xz1").hide();
					$("#mf1").hide();
				}
			});
		});
		var dialogCode = null;
		function addMakeCard(title, okValue, row) { //弹出修改和添加原因的窗口
			var col = $("#datatable").jqGrid('getRowData', row);
			$
					.ajax({
						type : "POST",
						url : "${basePath}/businessController/updateAll?code="
								+ col.businesscode,
						success : function(msg) {
							var myobj = (eval("(" + msg + ")").msg);
							$
									.each(
											myobj,
											function(i, val) {
												$('#businesscode').val(
														val.businesscode);
												$('#businesscode').attr(
														"readonly", true);
												$('#businessname1').val(
														val.businessname);
												$("#memo1").val(val.memo);
												if (val.configname == "OneCardOneBox"
														&& val.configvalue == 1) {
													$("#onecardonebox3").attr(
															"checked",
															"checked");
												} else if (val.configname == "OneCardOneBox"
														&& val.configvalue == 0) {
													$("#onecardonebox4").attr(
															"checked",
															"checked");
												} else if (val.configname == "RunTime") {
													$("#runtime1").attr(
															"value",
															val.configvalue);
												} else if (val.configname == "ValidateIdentities"
														&& val.configvalue == 1) {
													$("#validateidentities3")
															.attr("checked",
																	"checked");
												} else if (val.configname == "ValidateIdentities"
														&& val.configvalue == 0) {
													$("#validateidentities4")
															.attr("checked",
																	"checked");
												} else if (val.configname == "Rule") {
													$("#rule1").attr("value",
															val.configvalue);
												} else if (val.configname == "ChargeFuncSwitch") {
													if (val.configvalue == "0") {
														$("#chargefuncswitch3")
																.attr(
																		"checked",
																		"checked");
														$("#sf1").show();
													} else if (val.configvalue == "1"
															|| val.configvalue == "") {
														$("#chargefuncswitch4")
																.attr(
																		"checked",
																		"checked");
														$("#sf1").hide();
													} else {
														$("#chargefuncswitch4")
																.attr(
																		"checked",
																		"checked");
													}
												} else if (val.configname == "ChargeMode") {
													$("#chargemode1").attr(
															"value",
															val.configvalue);
												} else if (val.configname == "LimitSwitch") {
													if (val.configvalue == "0") {
														$("#limitswitch3")
																.attr(
																		"checked",
																		"checked");
														$("#xz1").show();
														//$("#mf1").show();
													} else if (val.configvalue == "1"
															|| val.configvalue == "") {
														$("#limitswitch4")
																.attr(
																		"checked",
																		"checked");
														$("#xz1").hide();
														$("#mf1").hide();
														$("#mf2").hide();
														$("#mf3").hide();
														$("#mf4").hide();
														$("#mf5").hide();
														$("#mf6").hide();
														$("#mf7").hide();
														$("#mf8").hide();
														$("#mf9").hide();
													} else {
														$("#limitswitch4")
																.attr(
																		"checked",
																		"checked");
													}
												} else if (val.configname == "Limitcondition") {
													$("#limitcondition1").attr(
															"value",
															val.configvalue);
													if (val.configvalue == "2") {
														$("#mf1").hide();
														$("#mf2").hide();
														$("#mf3").hide();
														$("#mf4").hide();
														$("#mf5").hide();
														$("#mf6").hide();
														$("#mf7").hide();
														$("#mf8").hide();
														$("#mf9").hide();
													} else if (val.configvalue == "1") {
														$("#mf1").show();
														$("#mf2").show();
														$("#mf3").show();
														$("#mf4").show();
														$("#mf5").show();
														$("#mf6").show();
														$("#mf7").show();
														$("#mf8").show();
														$("#mf9").show();
													} else {
														$("#mf1").hide();
														$("#mf2").hide();
														$("#mf3").hide();
														$("#mf4").hide();
														$("#mf5").hide();
														$("#mf6").hide();
														$("#mf7").hide();
														$("#mf8").hide();
														$("#mf9").hide();
													}
												} else if (val.configname == "ViolationsLimitCheckbox") {
													if (val.configvalue == 0) {
													} else {
														$(
																'#violationsLimitCheckbox1')
																.attr(
																		"checked",
																		"checked");
													}

												} else if (val.configname == "TimeBetween") {
													$('#timebetween1').val(
															val.configvalue);
												} else if (val.configname == "ViolationsLimit_1") {
													$('#violationsLimit_01')
															.val(
																	val.configvalue);
												} else if (val.configname == "ViolationsLimit_2") {
													$('#violationsLimit_02')
															.val(
																	val.configvalue);
												} else if (val.configname == "TimeoutCheckbox") {
													if (val.configvalue == 0) {
													} else {
														$('#timeoutCheckbox1')
																.attr(
																		"checked",
																		"checked");
													}

												} else if (val.configname == "Timeout") {
													if (val.configvalue == 1) {
														$('#timeout_01').attr(
																"checked",
																"checked");
													} else {
														$('#timeout_02').attr(
																"checked",
																"checked");
													}
												} else if (val.configname == "Timeout_text_2") {
													$('#timeout_text_02').val(
															val.configvalue);
												} else if (val.configname == "Timeout_text_3") {
													$('#timeout_text_03').val(
															val.configvalue);
												} else if (val.configname == "NoneDoorLimit") {
													$('#noneDoorLimit1').val(
															val.configvalue);
												} else if (val.configname == "FreeTime") {
													$('#freetime1').val(
															val.configvalue);
												}else if (val.configname == "PromptTime") {
													$('#PromptTime1').val(
															val.configvalue);
												}else if (val.configname == "WeChatCheckbox") {
													if(val.configvalue == 1){
														
														$("#WeChatCheckbox1").attr("checked","checked");
													}
												}else if (val.configname == "EmailCheckbox") {
													if(val.configvalue == 1){
													
														$("#EmailCheckbox1").attr("checked","checked");
													}
												}else if (val.configname == "SMSCheckbox") {
													if(val.configvalue == 1){
														
														$("#SMSCheckbox1").attr("checked","checked");
													}
												}
											});
							$('input,select,textarea',
									$('form[id="modifyCode2"]')).attr(
									'readonly', true);
							$('input,select,textarea',
									$('form[id="modifyCode2"]')).attr(
									'disabled', 'disabled');
						}
					});
			var d = dialog({
				title : title,
				content : $('#showupdate'),
				okValue : okValue,
				ok : function() {
					$('#modifyCode2').ajaxSubmit(
							{
								success : function(msg) {
									showMsg(eval("(" + msg + ")").msg);
									submitForm();
									$('#modifyCode2').clearForm(true);
									$('input,select,textarea',
											$('form[id="modifyCode2"]'))
											.removeAttr("readonly");
								}
							});
					return true;
				},
				cancelValue : '取消',
				cancel : function() {
					$('#modifyCode2').clearForm(true);
					$('input,select,textarea', $('form[id="modifyCode2"]'))
							.removeAttr("readonly");
				}
			});
			d.width(600);
			d.showModal();
		}
		function list(title, row) {//弹出修改和添加原因的窗口
			$
					.ajax({
						type : "POST",
						url : "${basePath}/businessController/updateAll?code="
								+ row,
						success : function(msg) {
							var myobj = (eval("(" + msg + ")").msg);
							$
									.each(
											myobj,
											function(i, val) {
												$('#businesscode').val(
														val.businesscode);
												$('#businesscode').attr(
														"readonly", true);
												$('#businessname1').val(
														val.businessname);
												$("#memo1").val(val.memo);
												if (val.configname == "OneCardOneBox"
														&& val.configvalue == 1) {
													$("#onecardonebox3").attr(
															"checked",
															"checked");
												} else if (val.configname == "OneCardOneBox"
														&& val.configvalue == 0) {
													$("#onecardonebox4").attr(
															"checked",
															"checked");
												} else if (val.configname == "RunTime") {
													$("#runtime1").attr(
															"value",
															val.configvalue);
												} else if (val.configname == "ValidateIdentities"
														&& val.configvalue == 1) {
													$("#validateidentities3")
															.attr("checked",
																	"checked");
												} else if (val.configname == "ValidateIdentities"
														&& val.configvalue == 0) {
													$("#validateidentities4")
															.attr("checked",
																	"checked");
												} else if (val.configname == "Rule") {
													$("#rule1").attr("value",
															val.configvalue);
												} else if (val.configname == "ChargeFuncSwitch") {
													if (val.configvalue == "0") {
														$("#chargefuncswitch3")
																.attr(
																		"checked",
																		"checked");
														$("#sf1").show();
													} else if (val.configvalue == "1"
															|| val.configvalue == "") {
														$("#chargefuncswitch4")
																.attr(
																		"checked",
																		"checked");
														$("#sf1").hide();
													} else {
														$("#chargefuncswitch4")
																.attr(
																		"checked",
																		"checked");
													}
												} else if (val.configname == "ChargeMode") {
													$("#chargemode1").attr(
															"value",
															val.configvalue);
												} else if (val.configname == "LimitSwitch") {
													if (val.configvalue == "0") {
														$("#limitswitch3")
																.attr(
																		"checked",
																		"checked");
														$("#xz1").show();
														//$("#mf1").show();
													} else if (val.configvalue == "1"
															|| val.configvalue == "") {
														$("#limitswitch4")
																.attr(
																		"checked",
																		"checked");
														$("#xz1").hide();
														$("#mf1").hide();
														$("#mf2").hide();
														$("#mf3").hide();
														$("#mf4").hide();
														$("#mf5").hide();
														$("#mf6").hide();
														$("#mf7").hide();
														$("#mf8").hide();
														$("#mf9").hide();
													} else {
														$("#limitswitch4")
																.attr(
																		"checked",
																		"checked");
													}
												} else if (val.configname == "Limitcondition") {
													$("#limitcondition1").attr(
															"value",
															val.configvalue);
													if (val.configvalue == "2") {
														$("#mf1").hide();
														$("#mf2").hide();
														$("#mf3").hide();
														$("#mf4").hide();
														$("#mf5").hide();
														$("#mf6").hide();
														$("#mf7").hide();
														$("#mf8").hide();
														$("#mf9").hide();
													} else if (val.configvalue == "1") {
														$("#mf1").show();
														$("#mf2").show();
														$("#mf3").show();
														$("#mf4").show();
														$("#mf5").show();
														$("#mf6").show();
														$("#mf7").show();
														$("#mf8").show();
														$("#mf9").show();
													} else {
														$("#mf1").hide();
														$("#mf2").hide();
														$("#mf3").hide();
														$("#mf4").hide();
														$("#mf5").hide();
														$("#mf6").hide();
														$("#mf7").hide();
														$("#mf8").hide();
														$("#mf9").hide();
													}
												} else if (val.configname == "ViolationsLimitCheckbox") {
													if (val.configvalue == 0) {
														/* document.getElementById("violationsLimitCheckbox").checked = false; */
														$("#mf3").hide();
													} else {
														/* document.getElementById("violationsLimitCheckbox").checked = true; */
														$(
																'#violationsLimitCheckbox1')
																.attr(
																		"checked",
																		"checked");
													}

												} else if (val.configname == "TimeBetween") {
													$('#timebetween1').val(
															val.configvalue);
												} else if (val.configname == "ViolationsLimit_1") {
													$('#violationsLimit_01')
															.val(
																	val.configvalue);
												} else if (val.configname == "ViolationsLimit_2") {
													$('#violationsLimit_02')
															.val(
																	val.configvalue);
												} else if (val.configname == "TimeoutCheckbox") {
													if (val.configvalue == 0) {
														/*  document.getElementById("timeoutCheckbox").checked = false; */
														$("#mf4").hide();
														$("#mf6").hide();
														$("#mf7").hide();
														$("#mf8").hide();
													} else {
														/* document.getElementById("timeoutCheckbox").checked = true; */
														$('#timeoutCheckbox1')
																.attr(
																		"checked",
																		"checked");
													}

												} else if (val.configname == "Timeout") {
													if (val.configvalue == 1) {
														$('#timeout_01').attr(
																"checked",
																"checked");
													} else {
														$('#timeout_02').attr(
																"checked",
																"checked");
													}
												} else if (val.configname == "Timeout_text_1") {
													/* document.getElementById("timeout_text_1").value = 'val.configvalue';  */
													$('#timeout_text_01').val(
															val.configvalue);
												} else if (val.configname == "Timeout_text_2") {
													/* document.getElementById("timeout_text_2").value = 'val.configvalue';  */
													$('#timeout_text_02').val(
															val.configvalue);
												} else if (val.configname == "Timeout_text_3") {
													$('#timeout_text_03').val(
															val.configvalue);
												} else if (val.configname == "NoneDoorLimit") {
													$('#noneDoorLimit1').val(
															val.configvalue);
												} else if (val.configname == "FreeTime") {
													$('#freetime1').val(
															val.configvalue);
												}else if (val.configname == "PromptTime") {
													$('#PromptTime1').val(
															val.configvalue);
												}else if (val.configname == "WeChatCheckbox") {
													if(val.configvalue == 1){
														
														$("#WeChatCheckbox1").attr("checked","checked");
													}
												}else if (val.configname == "EmailCheckbox") {
													if(val.configvalue == 1){
													
														$("#EmailCheckbox1").attr("checked","checked");
													}
												}else if (val.configname == "SMSCheckbox") {
													if(val.configvalue == 1){
														
														$("#SMSCheckbox1").attr("checked","checked");
													}
												}
											});
							$('input,select,textarea',
									$('form[id="modifyCode2"]')).attr(
									'readonly', true);
							$('input,select,textarea',
									$('form[id="modifyCode2"]')).attr(
									'disabled', 'disabled');
						}
					});
			var d = dialog({
				title : title,
				content : $('#showupdate'),
				okValue : '确定',
				cancelValue : '取消',
				cancel : function() {
					$('#modifyCode2').clearForm(true);
					$('input,select,textarea', $('form[id="modifyCode2"]'))
							.removeAttr("readonly");
					$('input,select,textarea', $('form[id="modifyCode2"]'))
							.removeAttr("disabled");
				}
			});
			d.width(600);
			d.showModal();
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