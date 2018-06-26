<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
    request.setAttribute("basePath",basePath);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>柜属性设置</title>
<%@ include file="../../common/head.jsp"%>
<%@ include file="../../common/theme.jsp"%>
<link rel="stylesheet" href="${basePath}/js/plug/zTree/css/demo.css"
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
	<!-- <div class="title">
    <p>查询条件</p>
</div> -->
	<div class="content">
		<%-- <form id="searchMemberCardForm" action="${basePath}/terminalController/list" method="get">
        <div class="list">
            <ul>
                <li>柜号：<input name="terminalid" type="text"/></li>
                <li>区域编码：<select class="newinput" name="areacode">
                    <option value="">请选择</option>             
                        <c:forEach var ="area" items="${area}">
                            <option value="<c:out value='${area.areacode}'/>" selected><c:out value='${area.areacode} ${area.areaname}'/></option>
                        </c:forEach>
                </select></li>
                <li>网络状态：<select name="networkstate">
                    <option value="">请选择</option>             
                    <option value="0">连接</option>
                    <option value="1">断开</option>   
                </select></li>
                <li>注册标识：<select name="registerflag">
                    <option value="">请选择</option>             
                    <option value="0">未注册</option>
                    <option value="1">已注册</option>   
                </select></li>
                <li>终端状态：<select name="runstatus">
                    <option value="">请选择</option>             
                    <option value="0">正常</option>
                    <option value="1">锁定</option>
                    <option value="2">故障</option>  
                </select></li>
                <!-- <li>安装时间：<input id="makedate" class="Wdate" name="makedate" type="text" class="validate[required]"
                     onFocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"></li> -->
            </ul>
            <ul style="float:right;margin-right:-150px;">   
                <li style="margin-left: 50px;"><input onclick="submitForm()" value="查询" type="button" style="width: 88px; font-size: 14px; "/>
                </li>
            </ul>
            <li style="border-bottom: 1px solid #cccccc;width: 96%;margin-left: 20px;height:10px;"></li>
        </div>
    </form> --%>
	</div>
	<div id="div_list" class="tab">
		<table id="datatable"></table>
		<div id="pager2"></div>
	</div>
	<div id="showsearch" class="list" style="display: none">
		<form id="searchMemberCardForm"
			action="${basePath}/terminalController/list" method="get">
			<div class="list">
				<ul>
					<li>柜号：<input name="displayname" type="text" /></li>
					<%-- <li>区域编码：<select class="newinput" name="areacode">
                    <option value="">请选择</option>             
                        <c:forEach var ="area" items="${area}">
                            <option value="<c:out value='${area.areacode}'/>" selected><c:out value='${area.areacode} ${area.areaname}'/></option>
                        </c:forEach>
                </select></li> --%>
					<li>网络状态：<select name="networkstate">
							<option value="" selected="selected">请选择</option>
							<option value="0">连接</option>
							<option value="1">断开</option>
					</select></li>
					<!-- <li>注册标识：<select name="registerflag">
                    <option value="">请选择</option>             
                    <option value="0">未注册</option>
                    <option value="1">已注册</option>   
                </select></li> -->
					<li>终端状态：<select name="runstatus">
							<option value="" selected="selected">请选择</option>
							<option value="0">正常</option>
							<option value="1">锁定</option>
							<option value="2">故障</option>
					</select></li>
					<li>安装时间：<input id="date" class="Wdate" name="date"
						type="text" class="validate[required]"
						onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"></li>
				</ul>
			</div>
		</form>
	</div>
	<div id="showadd" class="list" style="display: none">
		<form id="modifyCode" action="${basePath}/terminalController/add"
			method="post">
			<ul>
				<li>设备号：<input id="terminalid" name="terminalid"
					onblur="selectid()" maxlength="24" /></li>
				<li>区域编码：<select class="newinput" name="areacode" id="areacode">
						<c:forEach var="area" items="${area}">
							<option value="<c:out value='${area.areacode}'/>" selected><c:out
									value='${area.areacode} ${area.areaname}' /></option>
						</c:forEach>
				</select></li>

				<li>业务类型编码：<select id="businesscode" name="businesscode">
						<c:forEach var="b" items="${business}">
							<option value="${b.businesscode}" selected>${b.businesscode}
								${b.businessname}</option>
						</c:forEach>
				</select></li>
				<%-- <li>配置参数名称：<select id="configname" name="configname">
	                  <c:forEach var="b" items="${business}">
	                     <option value="${b.configname}" selected>${b.configname}</option>
	                  </c:forEach>
               </select></li> --%>
				<li>显示柜号：<input id="displayname" name="displayname"
					type="number" min="1" maxlength="24" /></li>
			
				<li>编号生成开关：<input id="boxnumberswitch1" name="boxnumberswitch" type="radio" value="0" style="width: 20px; height: 15px;" />开
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input id="boxnumberswitch2" name="boxnumberswitch" type="radio" checked="checked" value="1" style="width: 20px; height: 15px;" />关
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</li>
				<li id="box" style="display: none;">起始箱号：<input id="boxcode" name="boxcode" type="text" onkeyup="this.value=this.value.replace(/\D/g,'')"/></li>
				<!-- 编号生成方式              默认箱号   区域箱号   -->
				<li id="num" style="display: none;">编号生成方式：<select id="numbering" name="numbering">
						<option value="0" selected="selected">默认编号</option>
						<option value="1">区域编号</option>
				</select></li>
				<li>安装地址：<input id="address" name="address" maxlength="50" /></li>
				<li>箱数量：<input id="totalboxnumber" name="totalboxnumber"
					type="number" min="1" max="99999"
					onkeyup="this.value=this.value.replace(/\D/g,'')" /></li>
				<li>IP地址：<input id="ipaddr" name="ipaddr" /></li>
				<li>Mac地址：<input id="macaddress" name="macaddress" /></li>
				
				

				
				<li>终端状态：
					    <select id="runstatus" name="runstatus">
						
								<option value="0">正常</option>
								<option value="1">锁定</option>
								<option value="2">故障</option>
						</select>  
						
						<%-- <select id="runstatus" name="runstatus">
						
								   <option value="-1"></option>
									<c:forEach var="runstatusList" items="${runstatusList}">
										<option value="<c:out value='${runstatusList.keys}'/>" selected>
										                <c:out value='${runstatusList.values}' />
										</option>
									</c:forEach>
						</select>  --%>
						
						
				</li>
				<!-- <li>网络状态：<input id="networkstate" name="networkstate" value="0" disabled/></li> -->
				<!-- <li>软件版本号：<input id="version" name="version" maxlength="24"/></li>
                     <li>注册标识：<select id="registerflag" name="registerflag">
			                <option value="0">未注册</option>
			                <option value="1">已注册</option>
                     </select></li> -->
 
               <li>appKey：
			         <select class="newinput" name="appkey" id="appkey">
			            <option value="-1"></option>
						<c:forEach var="appkeylist" items="${appkeylist}">
							<option value="<c:out value='${appkeylist.userId}'/>" selected>
							                <c:out value='${appkeylist.userName}' />
							</option>
						</c:forEach>
				      </select>
				</li>		
			   
 
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
							/* function validateIP(str){
							    return !!str.match(/^((25[0-5]|2[0-4]\d|[01]?\d\d?)($|(?!\.$)\.)){4}$/);
							}
							$("#ipaddr").blur(function(){
							    var m = validateIP($(this).val());
							    if(m==false){
							    	showMsg("IP不合法");
							    	$("#ipaddr").val("");
							    }
							});  */
							$(window).resize(
									function() {
										$("#datatable").setGridWidth(
												$(window).width() - 50);
									});
							var a = '${account.areacode}';
							var tableWidth = $("#datatable").parent()
									.innerWidth();
							$("#datatable")
									.jqGrid(
											{
												url : '${basePath}/terminalController/list?areacode='
														+ a,
												datatype : "json",
												mtype : "POST",
												width : document.body.offsetWidth - 60,
												height : window.screen.availHeight - 320,
												//autowidth:true,
												editable : true,
												shrinkToFit : true,
												colNames : [ '终端号', '业务编号 ', '柜号',
														'总箱数', '业务类型', '业务名称',
														'区域', 'IP地址', '终端状态',
														'mac地址', '安装地址',
														'网络状态', '注册标识', '安装时间',
														'版本', '箱编号', '编号规则','appKey' ],
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
															editable : true
															,formatter : function(
																	el,
																	options,
																	rowData) {
																if (el == 0) {
																	return '正常';
																} else if (el == 1) {
																	return '锁定';
																} else {
																	return '故障';
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
																	return '连接';
																} else {
																	return '断开';
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
																	return '未注册';
																} else {
																	return '已注册';
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
																	return '默认编号';
																} else {
																	return '区域编号';
																}
															}
														},
 
														{
															name : "appkey",
															index : "appkey",
															align : 'center',
															width : 40,
															sortable : false
														},
														
												/* {name:"",index:'',align:'center',width:70,formatter:function(el,options,rowData){
												        var d= '<input type="button" value="删除" onclick="delRepository(\''+rowData.terminalid+'\')" style="width:30px;float:right;margin-right:10px;"/>';
												        //var u= '[<a href="javascript:void(0)" onclick="addMakeCard(\'修改类型\',\'修改\','+ options.rowId +')">修改</a>] ';
												        var u= '<input type="button" value="修改" onclick="addMakeCard(\'修改类型\',\'修改\','+ options.rowId +')" style="width:30px;float:right;margin-right:10px;"/>';
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
														list('查看', rowid);
													}
												},
											});
							$("#datatable").jqGrid("navGrid", "#pager2", {
								cloneToTop : true,
					        	add:false,
					        	
								searchfunc : openDialog4Searching,
								searchtext : "查询",
								addfunc : openDialog4Adding, // (1) 点击添加按钮，则调用openDialog4Adding方法  
								addtext : "新增",
								editfunc : openDialog4Updating, // (2) 点击添加按钮，则调用openDialog4Updating方法  
								edittext : "修改",
								delfunc : openDialog4Deleting, // (3) 点击添加按钮，则调用openDialog4Deleting方法  
								deltext : "删除",
								alerttext : "请选择需要操作的数据行!" // (4) 当未选中任何行而点击编辑、删除、查看按钮时，弹出的提示信息  
							});
							$("#datatable").navButtonAdd(
									'#pager2',
									{
										cloneToTop : true,
										caption : "导出Excel",
										buttonicon : "ui-icon-excel",
										onClickButton : function() {
											alert("导出excel");
											var title = "属性";
											getXlsFromTbl('datatable',
													'div_list', title, false);
										},
										position : "last"
									});
							/* $("#datatable").jqGrid('inlineNav', "#pager2"); */
						});
		function getXlsFromTbl(inTblId, inTblContainerId, title, rownumbers) {
			try {
				var allStr = "";
				var curStr = "";
				//alert("getXlsFromTbl");  
				if (inTblId != null && inTblId != "" && inTblId != "null") {
					curStr = getTblData($('#' + inTblId), $('#'
							+ inTblContainerId), rownumbers);
				}
				if (curStr != null) {
					allStr += curStr;
				} else {
					alert("你要导出的表不存在！");
					return;
				}
				var fileName = getExcelFileName(title);
				doFileExport(fileName, allStr);
			} catch (e) {
				alert("导出发生异常:" + e.name + "->" + e.description + "!");
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
						//alert($(element).text());  
						//取得每行的列数  
						var j = index + 1;
						var content = $(element).text();
						//alert(j + "|" + content);  
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
				alert(inTbl + "不存在!");
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
		var openDialog4Adding = function(title, okValue, row) {
			dialogCode = dialog({
				title : '新增',
				content : $('#showadd'),
				okValue : '新增',
				ok : function() {
					var displayname = document.getElementById("displayname").value;
					var totalboxnumber = document.getElementById("totalboxnumber").value;
					var terminalid = document.getElementById("terminalid").value;
					var macaddress = document.getElementById("macaddress").value;
					var chkObjs = $('input[name="boxnumberswitch"]:checked').val(); 
					if (terminalid == null || terminalid == "") {
						showMsg("设备号不能为空");
						return false;
					}else if (displayname == null || displayname == "") {
						showMsg("显示柜号不能为空");
						return false;
					}else if (chkObjs == null || chkObjs == "") {
						showMsg("编号生成开关不能为空");
						return false;
					}else if (totalboxnumber == null || totalboxnumber == "") {
						showMsg("箱数量不能为空");
						return false;
					}else {
						$('#modifyCode').ajaxSubmit({
							success : function(msg) {
								showMsg(eval("(" + msg + ")").msg);
								submitForm();
								$('#modifyCode').clearForm(true);
							},
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
		var openDialog4Searching = function(title, okValue, row) {
			dialogCode = dialog({
				title : '查询',
				content : $('#showsearch'),
				okValue : '查询',
				ok : function() {
					submitForm();
				},
				cancelValue : '关闭',
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
				showMsg("请选择一行数据!");
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
				if(col.runstatus == "正常"){
					$("#runstatus").attr("value", 0);
				}else if (col.runstatus == "锁定") {
					$("#runstatus").attr("value", 1);
				}else{
					$("#runstatus").attr("value", 2);
				}
				//$('#networkstate').val(col.networkstate);
				$("#networkstate").attr("value", col.networkstate);
				$('#boxcode').val(col.boxcode);
				$('#numbering').val(col.numbering);
				$('#version').val(col.version);
				
				$('#appkey').val(col.appkey);
 
				
				///alert(" areacode="+col.areacode+"  businesscode="+col.businesscode+" runstatus="+col.runstatus+" appkey="+col.appkey);
				
				var a = col.registerflag;
				if (a == "未注册") {
					$('#registerflag').val(0);
				} else {
					$('#registerflag').val(1);
				}
				dialogCode = dialog({
					title : "修改",
					content : $('#showadd'),
					okValue : "确定",
					ok : function() {
						var displayname = document
								.getElementById("displayname").value;
						var totalboxnumber = document
								.getElementById("totalboxnumber").value;
						var terminalid = document.getElementById("terminalid").value;
						var macaddress = document.getElementById("macaddress").value;
						var chkObjs = $('input[name="boxnumberswitch"]:checked').val(); 
						if (terminalid == null || terminalid == "") {
							showMsg("设备号不能为空");
							return false;
						} else if (displayname == null || displayname == "") {
							showMsg("显示柜号不能为空");
							return false;
						} else if (chkObjs == null || chkObjs == "") {
							showMsg("编号生成开关不能为空");
							return false;
						} else if (totalboxnumber == null
								|| totalboxnumber == "") {
							showMsg("箱数量不能为空");
							return false;
						} else {
							$('#modifyCode').ajaxSubmit(
									{
										success : function(msg) {
											showMsg(eval("(" + msg + ")").msg);
											submitForm();
										/* 	$('#modifyCode').clearForm(true);
											$('input,select,textarea',
													$('form[id="modifyCode"]'))
													.removeAttr("readonly"); */
											document.getElementById("terminalid").value = '';
											document.getElementById("areacode").value = '';
											document.getElementById("businesscode").value = '';
											//document.getElementById("configname").value = '';
											document.getElementById("displayname").value = '';
										 
											
											
											
											document.getElementById("address").value = '';
											document.getElementById("totalboxnumber").value = '';
											document.getElementById("ipaddr").value = '';
											document.getElementById("macaddress").value = '';
											document.getElementById("runstatus").value = '';
											//document.getElementById("networkstate").value = '0';
											
											
											document.getElementById("boxcode").value = '';
											document.getElementById("numbering").value = '';
											//document.getElementById("version").value = '';
											document.getElementById("appkey").value = '';
											
										
										}
									});
							return true;
						}
					},
					cancelValue : '关闭',
					cancel : function() {
						/* $('#modifyCode').clearForm(true);
						$('input,select,textarea', $('form[id="modifyCode"]'))
								.removeAttr("readonly"); */
								
						document.getElementById("terminalid").value = '';
						document.getElementById("areacode").value = '';
						document.getElementById("businesscode").value = '';
						//document.getElementById("configname").value = '0';
						document.getElementById("displayname").value = '';
					 
						
						
						
						document.getElementById("address").value = '';
						document.getElementById("totalboxnumber").value = '';
						document.getElementById("ipaddr").value = '';
						document.getElementById("macaddress").value = '';
						document.getElementById("runstatus").value = '';
						//document.getElementById("networkstate").value = '0';
						
						
						document.getElementById("boxcode").value = '';
						document.getElementById("numbering").value = '';
						//document.getElementById("version").value = '';
						document.getElementById("appkey").value = '';
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
				title : '提示',
				content : '您确认想要删除吗？',
				okValue : '确定',
				ok : function() {
					this.title('提交中…');
					if (ids.length > 0) {
						for (var i = 0; i < ids.length; i++) {
							var col = $("#datatable").jqGrid('getRowData',
									ids[i]);
							$
									.ajax({
										type : "POST",
										url : "${basePath}/terminalController/delete?id="
												+ col.terminalid,
										success : function(msg) {
											d.close();
											showMsg(eval("(" + msg + ")").msg);
											$("#datatable").trigger(
													'reloadGrid');
										}
									});
						}
					}
					return false;
				},
				cancelValue : '取消',
				cancel : function() {
				}
			});
			d.width(150);
			d.show();
		};
		function delRepository(num) {
			var d = dialog({
				title : '提示',
				content : '您确认想要删除吗？',
				okValue : '确定',
				ok : function() {
					this.title('提交中…');
					$
							.ajax({
								type : "POST",
								url : "${basePath}/terminalController/delete?id="
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
				cancelValue : '取消',
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
				if (a == "未注册") {
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
							$('#modifyCode').clearForm(true);
						}
					});
					return true;
				},
				cancelValue : '关闭',
				cancel : function() {
					$('#modifyCode').clearForm(true);
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
			if (a == "未注册") {
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
				cancelValue : '关闭',
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
				showMsg("请填写设备号");
			} else {
				$.ajax({
					type : "POST",
					url : "${basePath}/terminalController/select",
					data : {
						terminalid : terminalid,
					},
					success : function(msg) {
						showMsg(eval("(" + msg + ")").msg);
						if (eval("(" + msg + ")").msg == "设备号已存在") {
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

