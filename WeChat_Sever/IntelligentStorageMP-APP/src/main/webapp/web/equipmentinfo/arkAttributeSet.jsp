<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
    request.setAttribute("basePath",basePath);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title><spring:message code="arkAttributeSet.title.text" /></title>
<%@ include file="../../common/head.jsp"%>
<%@ include file="../../common/theme.jsp"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/js/plug/zTree/css/demo.css"
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
	<div class="content">

	</div>
	<div id="div_list" class="tab">
		<table id="datatable"></table>
		<div id="pager2"></div>
	</div>
	<div id="showsearch" class="list" style="display: none">
		<form id="searchMemberCardForm"
			action="${pageContext.request.contextPath}/terminalController/list" method="get">
			<div class="list">
				<ul>
				    <li><spring:message	code="arkAttributeSet.areacode" />：<input name="areacode" type="text" /></li>
					<li><spring:message	code="arkAttributeSet.displayname" />：<input name="displayname" type="text" /></li>
					<li><spring:message	code="arkAttributeSet.networkstate" />：<select name="networkstate">
							<option value="" selected="selected"><spring:message code="arkAttributeSet.prompt1.information" /></option>
							<option value="0"><spring:message code="arkAttributeSet.prompt2.information" /></option>
							<option value="1"><spring:message code="arkAttributeSet.prompt3.information" /></option>
					</select></li>
					<li><spring:message	code="arkAttributeSet.runstatus" />：<select name="runstatus">
							<option value="" selected="selected"><spring:message code="arkAttributeSet.prompt1.information" /></option>
							<option value="0"><spring:message code="arkAttributeSet.prompt4.information" /></option>
							<option value="1"><spring:message code="arkAttributeSet.prompt5.information" /></option>
							<option value="2"><spring:message code="arkAttributeSet.prompt6.information" /></option>
					</select></li>
					<li><spring:message	code="arkAttributeSet.date" />：<input id="date" class="Wdate" name="date"
						type="text" class="validate[required]"
						onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"></li>
				</ul>
			</div>
		</form>
	</div>
	<div id="showadd" class="list" style="display: none">
		<form id="modifyCode" action="${pageContext.request.contextPath}/terminalController/add"
			method="post">
			<ul>
				<li><spring:message	code="arkAttributeSet.terminalid" />：<input id="terminalid" name="terminalid"
					onblur="selectid()" maxlength="24" /></li>
				<li><spring:message	code="arkAttributeSet.areacode" />：<select class="newinput" name="areacode" id="areacode">
						<c:forEach var="area" items="${area}">
							<option value="<c:out value='${area.areacode}'/>" selected><c:out
									value='${area.areacode} ${area.areaname}' /></option>
						</c:forEach>
				</select></li>
				<li><spring:message	code="arkAttributeSet.businesscode" />：<select id="businesscode" name="businesscode">
						<c:forEach var="b" items="${business}">
							<option value="${b.businesscode}" selected>${b.businesscode}
								${b.businessname}</option>
						</c:forEach>
				</select></li>
				<li><spring:message	code="arkAttributeSet.displayname" />：<input id="displayname" name="displayname"
					type="number" min="1" maxlength="24" /></li>
			
				<li>
				     <spring:message	code="arkAttributeSet.boxnumberswitch" />：
				     <input id="boxnumberswitch1" name="boxnumberswitch" type="radio" value="0" style="width: 20px; height: 15px;" />
				     
				     <spring:message code="arkAttributeSet.prompt21.information" />
					        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				  	 <input id="boxnumberswitch2" name="boxnumberswitch" type="radio" checked="checked" value="1" style="width: 20px; height: 15px;" />
					       <spring:message code="arkAttributeSet.prompt22.information" />
					        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</li>
				<li id="box" style="display: none;">
					<spring:message	code="arkAttributeSet.boxcode" />：
					<input id="boxcode" name="boxcode" type="text" onkeyup="this.value=this.value.replace(/\D/g,'')"/>
					<!--  目前最大编号为(<input id="boxcodemax" name="boxcodemax" style="width:70px" type="text" />) -->
				</li>
				<!-- 编号生成方式              默认箱号   区域箱号   -->
				<li id="num" style="display: none;"><spring:message	code="arkAttributeSet.numbering" />：<select id="numbering" name="numbering">
						<option value="0" selected="selected"><spring:message code="arkAttributeSet.numberingone" /></option>
						<option value="1"><spring:message code="arkAttributeSet.numberingtwo" /></option>
				</select></li>
				<li><spring:message	code="arkAttributeSet.address" />：<input id="address" name="address" maxlength="50" /></li>
				<li><spring:message	code="arkAttributeSet.totalboxnumber" />：<input id="totalboxnumber" name="totalboxnumber"
					type="number" min="1" max="99999"
					onkeyup="this.value=this.value.replace(/\D/g,'')" /></li>
				<li><spring:message	code="arkAttributeSet.ipaddr" />：<input id="ipaddr" name="ipaddr" /></li>
				<li><spring:message	code="arkAttributeSet.macaddress" />：<input id="macaddress" name="macaddress" /></li>
				<li><spring:message	code="arkAttributeSet.runstatus" />：<select id="runstatus" name="runstatus">
						<option value="0"><spring:message code="arkAttributeSet.prompt4.information" /></option>
						<option value="1"><spring:message code="arkAttributeSet.prompt5.information" /></option>
						<option value="2"><spring:message code="arkAttributeSet.prompt6.information" /></option>
				</select></li>
				
			<!--     <li>appKey：
			         <select class="newinput" name="appkey" id="appkey">
			            <option value=""></option>
						<c:forEach var="appkeylist" items="${appkeylist}">
							<option value="<c:out value='${appkeylist.userId}'/>" selected>
							                <c:out value='${appkeylist.userName}' />
							</option>
						</c:forEach>
				      </select>
				</li>	 -->
				
				
			</ul>
		</form>
	</div>
	<script type="text/javascript">

		$(document).ready(function() {
			$('input[type=radio][name=boxnumberswitch]').change(function() {

				if (this.value == 0) {
					$("#box").show();
					$("#num").show();

				} else if (this.value == 1) {
					$("#box").hide();
					$("#num").hide();

				}
			});
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

		$(document)
				.ready(
						function() {
							$(window).resize(
									function() {
										$("#datatable").setGridWidth(
												$(window).width() - 50);
									});
							var a = '${account.areacode}';
							var tableWidth = $("#datatable").parent().innerWidth();
							$("#datatable")
									.jqGrid(
											{
												//url : '${pageContext.request.contextPath}/terminalController/list?areacode=' + a,
											    url : '${pageContext.request.contextPath}/terminalController/list?',
												datatype : "json",
												mtype : "POST",
												width : document.body.offsetWidth - 60,
												height : window.screen.availHeight - 320,
												//autowidth:true,
												editable : true,
												shrinkToFit : true,
												colNames : [ $.i18n.prop('arkAttributeSet.table.col.text.terminalid'), $.i18n.prop('arkAttributeSet.table.col.text.businesscode'), 
												             $.i18n.prop('arkAttributeSet.table.col.text.displayname'), $.i18n.prop('arkAttributeSet.table.col.text.totalboxnumber'), 
												             $.i18n.prop('arkAttributeSet.table.col.text.businessname'), $.i18n.prop('arkAttributeSet.table.col.text.businessname'),
												             $.i18n.prop('arkAttributeSet.table.col.text.areaname'), $.i18n.prop('arkAttributeSet.table.col.text.ipaddr'),
												             $.i18n.prop('arkAttributeSet.table.col.text.runstatus'), $.i18n.prop('arkAttributeSet.table.col.text.macaddress'),
												             $.i18n.prop('arkAttributeSet.table.col.text.address'), $.i18n.prop('arkAttributeSet.table.col.text.networkstate'),
												             $.i18n.prop('arkAttributeSet.table.col.text.registerflag'), $.i18n.prop('arkAttributeSet.table.col.text.makedate'),
												             $.i18n.prop('arkAttributeSet.table.col.text.version'), $.i18n.prop('arkAttributeSet.table.col.text.boxcode'),
												             $.i18n.prop('arkAttributeSet.table.col.text.numbering')
												             ,'boxcodemax' 
												             ],
												colModel : [
														{
															name : "terminalid",
															index : "terminalid",
															align : 'center',
															width : 40,
															sortable : false,
															editable : true,
															hidden : true
														},
														{
															name : "businesscode",
															index : "businesscode",
															align : 'center',
															width : 40,
															sortable : false,
															editable : true,
															hidden : true
														},
														{
															name : "displayname",
															index : "displayname",
															align : 'center',
															width : 40,
															sortable : false,
															editable : true
														},
														{
															name : "totalboxnumber",
															index : 'totalboxnumber',
															align : 'center',
															width : 40,
															sortable : false,
															editable : true
														},
														{
															name : "businessEntity.businessname",
															index : "businessname",
															align : 'center',
															width : 60,
															sortable : false,
															formatter : function(
																	el,
																	options,
																	rowData) {
																return el;
															}
														},
														{
															name : "areacode",
															index : 'areacode',
															align : 'center',
															width : 40,
															sortable : false,
															hidden : true
														},
														{
															name : "areaEntity.areaname",
															index : "areaname",
															align : 'center',
															width : 40,
															sortable : false,
															editable : true
														},
														{
															name : "ipaddr",
															index : "ipaddr",
															align : 'center',
															width : 50,
															sortable : false,
															hidden : false,
															editable : true
														},
														{
															name : "runstatus",
															index : 'runstatus',
															align : 'center',
															width : 40,
															sortable : false,
															editable : true,
															formatter : function(
																	el,
																	options,
																	rowData) {
																if (el == 0) {
																	return '<spring:message code="arkAttributeSet.prompt4.information" />';
																} else if (el == 1) {
																	return '<spring:message code="arkAttributeSet.prompt5.information" />';
																} else {
																	return '<spring:message code="arkAttributeSet.prompt6.information" />';
																}
															}
														},
														{
															name : "macaddress",
															index : "macaddress",
															align : 'center',
															width : 40,
															sortable : false,
															editable : true,
															hidden : true
														},
														{
															name : "address",
															index : "address",
															align : 'center',
															width : 60,
															sortable : false
														},
														{
															name : "networkstate",
															index : 'networkstate',
															align : 'center',
															width : 40,
															sortable : false,
															editable : true,
															formatter : function(
																	el,
																	options,
																	rowData) {
																if (el == 0) {
																	return '<spring:message code="arkAttributeSet.prompt2.information" />';
																} else {
																	return '<spring:message code="arkAttributeSet.prompt3.information" />';
																}
															}
														},
														{
															name : "registerflag",
															index : 'registerflag',
															align : 'center',
															width : 40,
															sortable : false,
															hidden : true,
															editable : true,
															formatter : function(
																	el,
																	options,
																	rowData) {
																if (el == 0) {
																	return '<spring:message code="arkAttributeSet.prompt7.information" />';
																} else {
																	return '<spring:message code="arkAttributeSet.prompt8.information" />';
																}
															}
														},
														{
															name : "makedate",
															index : "makedate",
															align : 'center',
															width : 60,
															sortable : false,
															editable : true,
															formatter : function(
																	el,
																	options,
																	rowData) {
																var t = rowData.makedate;
																var d = new Date();
																d.setTime(t);
																var s = d
																		.format('yyyy-MM-dd HH:mm:ss');
																return s;
															}
														},
														{
															name : "version",
															index : "version",
															align : 'center',
															width : 40,
															sortable : false,
															hidden : true
														},
														{
															name : "boxcode",
															index : "boxcode",
															align : 'center',
															width : 40,
															sortable : false
														},
														{
															name : "numbering",
															index : "numbering",
															align : 'center',
															width : 40,
															sortable : false,
															formatter : function(
																	el,
																	options,
																	rowData) {
																if (el == 0) {
																	return '<spring:message code="arkAttributeSet.numberingone" />';
																} else {
																	return '<spring:message code="arkAttributeSet.numberingtwo" />';
																}
															}
														},{
															name : "boxcodemax",
															index : "boxcodemax",
															align : 'center',
															width : 150,
															sortable : false,
															hidden : false
														}
														
													/* 	,{
															name : "appkey",
															index : "appkey",
															align : 'center',
															width : 40,
															sortable : false
														},{
															name : "appkeyname",
															index : "appkeyname",
															align : 'center',
															width : 40,
															sortable : false
														} */
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
												sortname : 'terminalid',
												sortorder : 'desc',
												viewrecords : true,
												multiboxonly : true,
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
													if (RowData.terminalid) {
														list("<spring:message code='btn.view' />", rowid);
													}
												},
											});
							$("#datatable").jqGrid("navGrid", "#pager2", {
								cloneToTop : true,
								searchfunc : openDialog4Searching,
								searchtext : $.i18n.prop("btn.search"),
							    addfunc : openDialog4Adding, // (1) 点击添加按钮，则调用openDialog4Adding方法  
								addtext :  $.i18n.prop("btn.add"), 
								editfunc : openDialog4Updating, // (2) 点击添加按钮，则调用openDialog4Updating方法  
								edittext :  $.i18n.prop("btn.update"),
								delfunc : openDialog4Deleting, // (3) 点击添加按钮，则调用openDialog4Deleting方法  
								deltext :$.i18n.prop("btn.delete"),
								alerttext : $.i18n.prop("param.minrow") // (4) 当未选中任何行而点击编辑、删除、查看按钮时，弹出的提示信息  
							});
							$("#datatable").navButtonAdd(
									'#pager2',
									{
										cloneToTop : true,
										caption : "<spring:message code='btn.derivation' />",
										buttonicon : "ui-icon-excel",
										onClickButton : function() {
											alert("<spring:message code='btn.derivation' />");
											var title = "<spring:message code='tip.attribute' />";
											getXlsFromTbl('datatable',
													'div_list', title, false);
										},
										position : "last"
									});
						});
		function getXlsFromTbl(inTblId, inTblContainerId, title, rownumbers) {
			try {
				var allStr = "";
				var curStr = "";
				if (inTblId != null && inTblId != "" && inTblId != "null") {
					curStr = getTblData($('#' + inTblId), $('#'
							+ inTblContainerId), rownumbers);
				}
				if (curStr != null) {
					allStr += curStr;
				} else {
					alert("<spring:message code='param.derivation.errorone' />");
					return;
				}
				var fileName = getExcelFileName(title);
				doFileExport(fileName, allStr);
			} catch (e) {
				alert("<spring:message code='param.derivation.errortwo' />" + e.name + "->" + e.description + "!");
			}
		}
		function getTblData(curTbl, curTblContainer, rownumbers) {

			var outStr = "";
			if (curTbl != null) {
				var rowdata = curTbl.getRowData();
				var Lenr = 1;

				for (i = 0; i < Lenr; i++) {
					//var Lenc = curTbl.rows(i).cells.length;  
					var th;
					if (rownumbers == true) {
						th = curTblContainer.find('TH:not(:first-child)');
					} else {
						th = curTblContainer.find('TH');
					}
					th.each(function(index, element) {
		
						//取得每行的列数  
						var j = index + 1;
						var content = $(element).text();
						outStr += content + "\t";
						//赋值  

					});
					outStr += "\r\n";
				}
				var tmp = "";
				for (i = 0; i < rowdata.length; i++) {
					var row = eval(rowdata[i]);
					for (each in row) {
						outStr += row[each] + "\t";
					}
					outStr += "\r\n";
				}

			} else {
				outStr = null;
				alert(inTbl + "<spring:message code='param.derivation.errorthree' />");
			}
			return outStr;
		}
		function getExcelFileName(title) {
			var d = new Date();
			var curYear = d.getYear();
			var curMonth = "" + (d.getMonth() + 1);
			var curDate = "" + d.getDate();
			var curHour = "" + d.getHours();
			var curMinute = "" + d.getMinutes();
			var curSecond = "" + d.getSeconds();
			if (curMonth.length == 1) {
				curMonth = "0" + curMonth;
			}
			if (curDate.length == 1) {
				curDate = "0" + curDate;
			}
			if (curHour.length == 1) {
				curHour = "0" + curHour;
			}
			if (curMinute.length == 1) {
				curMinute = "0" + curMinute;
			}
			if (curSecond.length == 1) {
				curSecond = "0" + curSecond;
			}
			var fileName = title + "_" + curYear + curMonth + curDate + "_"
					+ curHour + curMinute + curSecond + ".csv";
			alert(fileName);
			return fileName;
		}
		function doFileExport(inName, inStr) {
			var xlsWin = null;
			if (!!document.all("HideFrm")) {
				xlsWin = HideFrm;
			} else {
				var width = 6;
				var height = 4;
				var openPara = "left=" + (window.screen.width / 2 - width / 2)
						+ ",top=" + (window.screen.height / 2 - height / 2)
						+ ",scrollbars=no,width=" + width + ",height=" + height;
				xlsWin = window.open("", "_blank", openPara);
			}
			xlsWin.document.write(inStr);
			xlsWin.document.close();
			xlsWin.document.execCommand('Saveas', true, inName);
			xlsWin.close();
		}
		function initialization(){
			document.getElementById("terminalid").value = "";
			document.getElementById("areacode").value = "00";
			document.getElementById("businesscode").value = "1";
			document.getElementById("displayname").value = "";
			document.getElementById("boxnumberswitch1").value = "0";
			document.getElementById("boxnumberswitch2").value = "1";
			document.getElementById("boxcode").value = "1";
			document.getElementById("numbering").value = "1";
			document.getElementById("address").value = "";
			document.getElementById("totalboxnumber").value = "";
			document.getElementById("ipaddr").value = "";
			document.getElementById("macaddress").value = "";
			document.getElementById("runstatus").value = "0";
		}
		var openDialog4Adding = function(title, okValue, row) {
			dialogCode = dialog({
				title : $.i18n.prop('btn.add'),
				content : $('#showadd'),
				okValue : $.i18n.prop('btn.ok'),
				ok : function() {
					var displayname = document.getElementById("displayname").value;
					var totalboxnumber = document.getElementById("totalboxnumber").value;
					var terminalid = document.getElementById("terminalid").value;
					var macaddress = document.getElementById("macaddress").value;
					var chkObjs = $('input[name="boxnumberswitch"]:checked').val(); 
					if (terminalid == null || terminalid == "") {
						showMsg("<spring:message code='arkAttributeSet.prompt14.information' />");
						return false;
					}else if (displayname == null || displayname == "") {
						showMsg("<spring:message code='arkAttributeSet.prompt15.information' />");
						return false;
					}else if (chkObjs == null || chkObjs == "") {
						showMsg("<spring:message code='arkAttributeSet.prompt16.information' />");
						return false;
					}else if (totalboxnumber == null || totalboxnumber == "") {
						showMsg("<spring:message code='arkAttributeSet.prompt17.information' />");
						return false;
					}else {
						$('#modifyCode').ajaxSubmit({
							success : function(msg) {
								showMsg(eval("(" + msg + ")").msg);
								submitForm();
								//$('#modifyCode').clearForm(true);
								initialization();
							},
						});
						return true;
					}
				},
				cancelValue :  $.i18n.prop('btn.cancel'),
				cancel : function() {
					//$('#modifyCode').clearForm(true);
					initialization();
				}
			});
			dialogCode.width(600);
			dialogCode.showModal();
		};
		var openDialog4Searching = function(title, okValue, row) {
			dialogCode = dialog({
				title :  $.i18n.prop('btn.search'),
				content : $('#showsearch'),
				okValue : $.i18n.prop('btn.ok'),
				ok : function() {
					submitForm();
				},
				cancelValue : $.i18n.prop('btn.cancel'),
				cancel : function() {
					$('#searchMemberCardForm').clearForm(true);
				}
			});
			dialogCode.width(600);
			dialogCode.showModal();
		};

		var openDialog4Updating = function(row) {
			var ids = $("#datatable").jqGrid("getGridParam", "selarrrow");
			if (ids.length > 1) {
				showMsg("<spring:message code='arkAttributeSet.prompt18.information' />");
			} else {
				var col = $("#datatable").jqGrid('getRowData', row);
				$('#terminalid').val(col.terminalid);
				$('#terminalid').attr("readonly", true);
				$("input[name='terminalid']").removeAttr("onblur");
				$('#areacode').val(col.areacode);
				$('#businesscode').val(col.businesscode);
				$('#configname').val(col.configname);
				$('#displayname').val(col.displayname);
				$('#address').val(col.address);
				$('#totalboxnumber').val(col.totalboxnumber);
				$('#totalboxnumber').attr("readonly", true);
				$('#ipaddr').val(col.ipaddr);
				$('#macaddress').val(col.macaddress);
				//$('#runstatus').val(col.runstatus);
				if(col.runstatus == '<spring:message code="arkAttributeSet.prompt4.information" />'){
					$("#runstatus").attr("value", 0);
				}else if (col.runstatus == '<spring:message code="arkAttributeSet.prompt5.information" />') {
					$("#runstatus").attr("value", 1);
				}else{
					$("#runstatus").attr("value", 2);
				}
				//$('#networkstate').val(col.networkstate);
				if (col.networkstate == '<spring:message code="arkAttributeSet.prompt2.information" />') {
					$("#networkstate").attr("value", 0);
				} else {
					$("#networkstate").attr("value", 1); 
				}
				$('#boxcode').val(col.boxcode);
				$('#boxcodemax').val(col.boxcodemax); 
				
				//$('#numbering').val(col.numbering);
				if (col.numbering == '<spring:message code="arkAttributeSet.numberingone" />') {
					$("#numbering").attr("value", 0);
				} else {
					$("#numbering").attr("value", 1);
				}
				$('#version').val(col.version);
				var a = col.registerflag;
				if (a == "<spring:message code='arkAttributeSet.prompt7.information' />") {
					$('#registerflag').val(0);
				} else {
					$('#registerflag').val(1);
				}
				dialogCode = dialog({
					title : $.i18n.prop('btn.update'),
					content : $('#showadd'),
					okValue :  $.i18n.prop('btn.ok'),
					ok : function() {
						var displayname = document
								.getElementById("displayname").value;
						var totalboxnumber = document
								.getElementById("totalboxnumber").value;
						var terminalid = document.getElementById("terminalid").value;
						var macaddress = document.getElementById("macaddress").value;
						var chkObjs = $('input[name="boxnumberswitch"]:checked').val(); 
						if (terminalid == null || terminalid == "") {
							showMsg("<spring:message code='arkAttributeSet.prompt14.information' />");
							return false;
						} else if (displayname == null || displayname == "") {
							showMsg("<spring:message code='arkAttributeSet.prompt15.information' />");
							return false;
						} else if (chkObjs == null || chkObjs == "") {
							showMsg("<spring:message code='arkAttributeSet.prompt16.information' />");
							return false;
						} else if (totalboxnumber == null
								|| totalboxnumber == "") {
							showMsg("<spring:message code='arkAttributeSet.prompt17.information' />");
							return false;
						} else {
							$('#modifyCode').ajaxSubmit(
									{
										success : function(msg) {
											showMsg(eval("(" + msg + ")").msg);
											submitForm();
											initialization();
											
										}
									});
							return true;
						}
					},
					cancelValue :$.i18n.prop('btn.cancel'),
					cancel : function() {
						/* $('#modifyCode').clearForm(true);
						$('input,select,textarea', $('form[id="modifyCode"]')).removeAttr("readonly"); */
						initialization();
					}
				});
				dialogCode.width(600);
				dialogCode.showModal();
			}
		};
		var openDialog4Deleting = function(row) { // (8) 接受选中行的id为参数  
			//var col=$("#datatable").jqGrid('getRowData',row);
			var ids = $("#datatable").jqGrid("getGridParam", "selarrrow");
			var d = dialog({
				title : $.i18n.prop('tip.title'),
				content : $.i18n.prop('tip.confirm.delete'),
				okValue : $.i18n.prop('btn.ok'),
				ok : function() {
					this.title($.i18n.prop('tip.loading'));
					if (ids.length > 0) {
						for (var i = 0; i < ids.length; i++) {
							var col = $("#datatable").jqGrid('getRowData',
									ids[i]);
							$
									.ajax({
										type : "POST",
										url : "${pageContext.request.contextPath}/terminalController/delete?id="
												+ col.terminalid,
										success : function(msg) {
											d.close();
											showMsg(eval("(" + msg + ")").msg);
											$("#datatable").trigger('reloadGrid');
										}
									});
						}
					}
					return false;
				},
				cancelValue : $.i18n.prop('btn.cancel'),
				cancel : function() {
				}
			});
			d.width(150);
			d.show();
		};
		function delRepository(num) {
			var d = dialog({
				title : $.i18n.prop('tip.title'),
				content : $.i18n.prop('tip.confirm.delete'),
				okValue : $.i18n.prop('btn.ok'),
				ok : function() {
					this.title($.i18n.prop('tip.loading'));
					$
							.ajax({
								type : "POST",
								url : "${pageContext.request.contextPath}/terminalController/delete?id="
										+ num,
								success : function(msg) {
									d.close();
									d = null;
									d = dialog({
										content : eval("(" + msg + ")").msg
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
				cancelValue : $.i18n.prop('btn.cancel'),
				cancel : function() {
				}
			});
			d.width(150);
			d.show();
		}
		var dialogCode = null;
		function addMakeCard(title, okValue, row) {//弹出修改和添加原因的窗口
			if (row > 0) {//说明是修改
				var col = $("#datatable").jqGrid('getRowData', row);
				$('#terminalid').val(col.terminalid);
				$('#terminalid').attr("readonly", true);
				$('#areacode').val(col.areacode);
				$('#businesscode').val(col.businesscode);
				$('#configname').val(col.configname);
				$('#displayname').val(col.displayname);
				$('#address').val(col.address);
				$('#totalboxnumber').val(col.totalboxnumber);
				$('#totalboxnumber').attr("readonly", true);
				$('#ipaddr').val(col.ipaddr);
				$('#macaddress').val(col.macaddress);
				$('#runstatus').val(col.runstatus);
				$('#networkstate').val(col.networkstate);
				$('#version').val(col.version);
				$('#boxcode').val(col.boxcode);
				$('#numbering').val(col.numbering);
				var a = col.registerflag;
				if (a == "<spring:message code='arkAttributeSet.prompt7.information' />") {
					$('#registerflag').val(0);
				} else {
					$('#registerflag').val(1);
				}
			}
			dialogCode = dialog({
				title : title,
				content : $('#showadd'),
				okValue : okValue,
				ok : function() {
					$('#modifyCode').ajaxSubmit({
						success : function(msg) {
							showMsg(eval("(" + msg + ")").msg);
							submitForm();
							//$('#modifyCode').clearForm(true);
							initialization();
						}
					});
					return true;
				},
				cancelValue : $.i18n.prop('btn.cancel'),
				cancel : function() {
					//$('#modifyCode').clearForm(true);
					initialization();
				}
			});
			dialogCode.width(600);
			dialogCode.showModal();
		}
		function list(title, row) {//弹出查看窗口
			var col = $("#datatable").jqGrid('getRowData', row);
			$('#terminalid').val(col.terminalid);
			$("input[name='terminalid']").removeAttr("onblur");
			$('#areacode').val(col.areacode);
			$("#areacode").attr("disabled", "disabled");
			$('#businesscode').val(col.businesscode);
			$("#businesscode").attr("disabled", "disabled");
			$('#configname').val(col.configname);
			$('#displayname').val(col.displayname);
			$('#address').val(col.address);
			$('#totalboxnumber').val(col.totalboxnumber);
			$('#ipaddr').val(col.ipaddr);
			$('#macaddress').val(col.macaddress);
			$('#runstatus').val(col.runstatus);
			$("#runstatus").attr("disabled", "disabled");
			$('#networkstate').val(col.networkstate);
			$("#networkstate").attr("disabled", "disabled");
			$('#boxcode').val(col.boxcode);
			$('#numbering').val(col.numbering);
			$('#version').val(col.version);
			var a = col.registerflag;
			if (a == "<spring:message code='arkAttributeSet.prompt7.information' />") {
				$('#registerflag').val(0);
			} else {
				$('#registerflag').val(1);
			}
			$("#registerflag").attr("disabled", "disabled");
			$('input,select,textarea', $('form[id="modifyCode"]')).attr(
					'readonly', true);
			dialogCode = dialog({
				title : title,
				content : $('#showadd'),
				cancelValue :  $.i18n.prop('btn.cancel'),
				cancel : function() {
					$('#modifyCode').clearForm(true);
					$('input,select,textarea', $('form[id="modifyCode"]'))
							.removeAttr("readonly");
					$('input,select,textarea', $('form[id="modifyCode"]'))
							.removeAttr("disabled");
				}
			});
			dialogCode.width(600);
			dialogCode.showModal();
		}
		function selectid() {
			var terminalid = document.getElementById("terminalid").value;
			if (terminalid == "") {
				showMsg("<spring:message code='arkAttributeSet.prompt19.information' />");
			} else {
				$.ajax({
					type : "POST",
					url : "${pageContext.request.contextPath}/terminalController/select",
					data : {
						terminalid : terminalid,
					},
					success : function(msg) {
						showMsg(eval("(" + msg + ")").msg);
						if (eval("(" + msg + ")").msg == "<spring:message code='arkAttributeSet.prompt20.information' />") {
							$('#terminalid').attr("value", "");
							return false;
						}
					},
				});
			}
		}
		$(function() {
			$(document).keypress(function(e) {
				if (e.keyCode == 13) {
					submitForm();
					$('#searchMemberCardForm').clearForm(true);
					setTimeout(function() {
						dialogCode.close().remove();
					}, 100);
				}
			})
		});
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

