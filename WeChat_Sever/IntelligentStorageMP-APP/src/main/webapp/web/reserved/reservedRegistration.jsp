<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path;
	request.setAttribute("basePath", basePath);
%>
<!DOCTYPE html>
<html>
<head>
<title>箱体预留登记</title>
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

td#datatable_toppager_center {
	display: none;
}
</style>
</head>
<body>
	<object ID="CardReader"
		CLASSID="CLSID:099B4B05-14CE-4DE6-83F4-9082E9A9327C"
		CODEBASE="DcdzCRX.CAB#version=1,0,0,1" width=0 height=0></object>
	<div class="content">
		<div class="tab">
			<table id="datatable" style="width: 90%"></table>
			<div id="pager2"></div>
		</div>
	</div>
	<div id="showsearch" class="list" style="display: none">
		<form id="searchForm" action="${basePath}/userController/user"
			method="post">
			<div class="list">
				<ul>
					<li>卡号：<input name="usercardid" type="text" /></li>
					<li>姓名：<input name="username" type="text" /></li>
					<!-- 可能要改成绑卡状态  -->
					<li>状态：<select name="state">
							<option value="">请选择</option>
							<option value="0">正常</option>
							<option value="1">停用</option>
					</select></li>
				</ul>
			</div>
		</form>
	</div>
	<div id="showadd" class="list" style="display: none">
		<form id="modifyCode" action="" method="post">
			<ul>
				<li id="cold">卡号：<input id="usercardid" name="usercardid"
					type="text" onclick="readCard()" /></li>
				<li id="use">用户名：<input id="username" name="username"
					type="text" /></li>
				<li id="idc">证件号：<input id="idcode" name="idcode" type="text" /></li>
				<li id="dis">柜 号：<select id="displayname" name="displayname" onchange="selectBoxDisplayname(0)">
						<option value="">请选择</option>
						<c:forEach var="t" items="${TerminalEntity}">
							<option value="<c:out value='${t.displayname}'/>">
								<c:out value='${t.displayname}' /></option>
						</c:forEach>
				</select></li>
				<li id="box">箱 号：
				<select id="boxid" name="boxid" >
						<option value="" selected="selected">请选择</option>
				</select>
				<!-- <input id="boxid" name="boxid" type="text" /> --></li>
				<li id="eff">有效天数：<input id="effectivedays" name="effectivedays"
					type="text" maxlength="3" oninput="txtBoxKeyDown()" value="0"
					onkeyup="this.value=this.value.replace(/[^0-9]/g,'')"
					onafterpaste="this.value=this.value.replace(/[^0-9]/g,'')" /></li>
				<li id="end">截至日期：<input id="enddate" name="enddate" type="text"
					class="Wdate" class="validate[required]" onBlur="theBoxKeyDown()"
					onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" /></li>
				<li id="bin">绑定状态：<input id="binding" name="binding" type="text" /></li>
			</ul>
		</form>
	</div>
	<div id="selectShowadd" class="list" style="display: none">
		<form id="selectModifyCode" action="" method="post">
			<ul>
				<li>卡号：<input id="usercardid1" name="usercardid" type="text" onclick="selectone()" /></li>
				<li>用户名：<input id="username1" name="username" type="text" /></li>
				<li>证件号：<input id="idcode1" name="idcode" type="text" /></li>
				<li>绑定状态：<select id="binding1" name="binding">
						<option value="">请选择</option>
						<option value="0">未绑</option>
						<option value="1">已绑</option>

				</select></li>
			</ul>
		</form>
	</div>
	<script type="text/javascript">
    function readCard(){
    	var axCardReader = document.getElementById('CardReader');
		if(axCardReader == null){
			alert("创建组件失败")
		}else{
			axCardReader.AXRCC_Open();
			var cardid = axCardReader.AXRCC_readCardID();
			document.getElementById('usercardid').value = cardid;
			axCardReader.AXRCC_Close();
		}
    };
	var format = function(time, format) {
		var t = new Date(time);
		var tf = function(i) {
			return (i < 10 ? '0' : '') + i
		};
		return format.replace(/yyyy|MM|dd|HH|mm|ss/g, function(a) {
			switch (a) {
			case 'yyyy':
				return tf(t.getFullYear());
				break;
			case 'MM':
				return tf(t.getMonth() + 1);
				break;
			case 'mm':
				return tf(t.getMinutes());
				break;
			case 'dd':
				return tf(t.getDate());
				break;
			case 'HH':
				return tf(t.getHours());
				break;
			case 'ss':
				return tf(t.getSeconds());
				break;
			}
		})
	};
	function txtBoxKeyDown() {
		//获取到文本框输入的值 
		var effectivedays = document.getElementById("effectivedays").value;
		var re = /^[0-9]+.?[0-9]*$/;
		if (!re.test(effectivedays)) {
			document.getElementById("enddate").value = "";
			return false;
		}
		var curDate = new Date();
		var preDate = new Date(curDate.getTime() + 86400
				* ((parseInt(effectivedays)) * 1000)); //前一天
		var systemTime = format(preDate, 'yyyy-MM-dd HH:mm:ss')
		document.getElementById("enddate").value = systemTime;

	};
	function theBoxKeyDown() {
		//获取到文本框输入的值 
		var enddate = document.getElementById("enddate").value;
		var curDate = new Date();
		var preDate = new Date(curDate.getTime()); //前一天
		var newpreDate = format(preDate, 'yyyy-MM-dd HH:mm:ss')

		var date1 = new Date(newpreDate)
		var date2 = new Date(enddate)

		var s1 = date1.getTime();
		var s2 = date2.getTime();
		var total = (s2 - s1) / 1000;
		var day = parseInt((total + 60) / (24 * 60 * 60)); //计算整数天数
		document.getElementById("effectivedays").value = day;

	};
	function selectBoxDisplayname(AddAmend){
		 var displayname = document.getElementById("displayname").value;  

		    $.ajax({
		        type: "POST",
		        url: "${basePath}/reservedRegistration/selectBoxDisplayname",
		        data:{
		        	displayname : displayname
		        },
		        success: function(msg){
					if (msg != "[]") {
						
						var dataObj = eval("(" + msg + ")");
						if(AddAmend == 0){
							$("#boxid").empty();
						}
						for(var i = 0; i < dataObj.length; i++){
							$("#boxid").append('<option value="' +dataObj[i].boxid+ '">'+ dataObj[i].dispalyname+ '</option>');
						}
		        		  
		        	}   	      	
		        }
		    });

	};
	
	
	function submitForm() {
		$("#datatable").jqGrid('setGridParam', {
			postData : formToJson("selectModifyCode"),
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
						var tableWidth = $("#datatable").parent()
								.innerWidth() + 25;
						$("#datatable")
								.jqGrid(
										{
											url : "${pageContext.request.contextPath}/reservedRegistration/selectTiedBox",
											datatype : "json",
											mtype : "POST",
											width : document.body.offsetWidth - 60,
											height : window.screen.availHeight - 325,
											//autowidth:true,
											editable : false,
											shrinkToFit : true,
											colNames : [ '用户卡号', '用户姓名',
													'证件号', '绑定时间', '截止时间',
													'柜号', '箱号', '绑定状态',
													'操作' ],
											colModel : [
													{
														name : "usercardid",
														index : 'usercardid',
														align : 'center',
														width : 70,
														sortable : false
													},
													{
														name : "username",
														index : "username",
														align : 'center',
														width : 70,
														sortable : false
													},

													{
														name : "idcode",
														index : 'idcode',
														align : 'center',
														width : 90,
														sortable : false
													},
													{
														name : "makedate",
														index : 'makedate',
														align : 'center',
														width : 100,
														sortable : false,
														formatter : function(
																el,
																options,
																rowData) {
															var t = rowData.makedate;
															if (t == ""
																	|| t == null) {
																return "";
															}
															var d = new Date();
															d.setTime(t);
															var s = d
																	.format('yyyy-MM-dd HH:mm:ss');
															return s;
														}
													},
													{
														name : "enddate",
														index : 'enddate',
														align : 'center',
														width : 100,
														sortable : false,
														formatter : function(
																el,
																options,
																rowData) {
															var t = rowData.endDate;
															if (t == ""
																	|| t == null) {
																return "";
															}
															var d = new Date();
															d.setTime(t);
															var s = d
																	.format('yyyy-MM-dd HH:mm:ss');
															return s;
														}
													},
													{
														name : "displayname",
														index : 'displayname',
														align : 'center',
														width : 90,
														sortable : false
													},
													{
														name : "boxid",
														index : 'boxid',
														align : 'center',
														width : 90,
														sortable : false
													},
													{
														name : "binding",
														index : 'binding',
														align : 'center',
														width : 90,
														sortable : false,
														formatter : function(
																el,
																options,
																rowData) {
															var t = rowData.binding;
															if (t == 1) {
																return "已绑";
															} else {
																return "无";
															}
														}
													},
													{
														name : "",
														index : '',
														align : 'center',
														width : 70,
														sortable : false,
														formatter : function(
																el,
																options,
																rowData) {
															var d = '<input type="button" value="解除预留" onclick="relieve('
																	+ rowData.usercardid
																	+ ','
																	+ rowData.makedate
																	+ ','
																	+ rowData.endDate
																	+ ','
																	+ rowData.boxid
																	+ ','
																	+ rowData.displayname
																	+ ')" style="width:80px;float:right;margin-right:5px;"/>';
															return d;
														}
													}, ],
											sortable : false,
											rowNum : 10,
											rownumbers : false,
											multiselect : true,
											jsonReader : {
												repeatitems : false,
												id : "0"
											},
											pager : '#pager2',
											rowList : [ 10, 15, 20, 500 ],
											sortname : 'usercardid',
											sortorder : 'desc',
											toppager : true,
											viewrecords : true,
											multiboxonly : true,
											loadComplete : function() {
												var myGrid = $("#datatable");
												$("#cb_" + myGrid[0].id)
														.hide();
											},
											/* ondblClickRow : function(rowid) {
												RowData = jQuery(this).jqGrid("getRowData",rowid);
												if (RowData.usercardid) {
													list('查看',rowid);
												}
											}, */
										});
						$("#datatable").jqGrid("navGrid", "#pager2", {
							cloneToTop : true,
							/* search : false, */
							/* add : false, */
							del : false,
							edit : false,
							addfunc : reserved, // (1) 点击添加按钮
							addtext : '箱体预留',
							searchfunc : openDialog4Searching, // (2) 点击添加按钮，则调用addMakeCard方法  
							searchtext : $.i18n.prop("btn.search"),
							alerttext : "请选择需要操作的数据行!" // (3) 当未选中任何行而点击编辑、删除、查看按钮时，弹出的提示信息  
						});
					});
	var relieve = function relieve(usercardid,makedate,endDate,boxid,displayname) {   //弹出修改和添加原因的窗口
	var dialogCode = dialog({
			title : '预留箱解除', 
			content : '您确定想要解除预留箱子吗？', 
			okValue : '确定',
			ok : function() {
				$
						.ajax({
							type : "POST",
							url : "${pageContext.request.contextPath}/reservedRegistration/relieve",
							data : {
								usercardid : usercardid,
								makedate : makedate,
								boxid : boxid,
								enddate : endDate,
								displayname : displayname
							},
							success : function(msg) {
								showMsg(eval("(" + msg + ")").msg);
								submitForm();

							},
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
	var dialogCode = null;
	var reserved = function reserved(row) { //弹出修改和添加原因的窗口
		var ids = jQuery("#datatable").jqGrid('getGridParam', 'selarrrow');
		if (ids.length == 0) {
			document.getElementById("dis").style.display = "block";
			document.getElementById("cold").style.display = "block";
			document.getElementById("idc").style.display = "block";
			document.getElementById("use").style.display = "block";
			document.getElementById("box").style.display = "block";
			document.getElementById("eff").style.display = "block";
			document.getElementById("end").style.display = "block";
			document.getElementById("bin").style.display = "none";
			var headline = "临时用户登记";
			DisplayPage(headline, row, ids);
		} else {
			document.getElementById("cold").style.display = "none";
			document.getElementById("bin").style.display = "none";
			document.getElementById("idc").style.display = "none";
			document.getElementById("use").style.display = "none";
			document.getElementById("dis").style.display = "block";
			document.getElementById("box").style.display = "block";
			document.getElementById("eff").style.display = "block";
			document.getElementById("end").style.display = "block";

			var headline = "用户登记";
			DisplayPage(headline, row, ids);
		}
	};

	function DisplayPage(headline, row, ids) {
		dialogCode = dialog({
			title : headline,
			content : $('#showadd'),
			okValue : $.i18n.prop('btn.ok'),
			ok : function() {
				var usercardid = document.getElementById("usercardid").value;
				if (usercardid == "" || usercardid == null) {
					var col = $("#datatable").jqGrid('getRowData', row);
					usercardid = col[ids - 1].usercardid;
				}
				var displayname = document.getElementById("displayname").value
				var boxid = document.getElementById("boxid").value
				var effectivedays = document
						.getElementById("effectivedays").value
				var enddate = document.getElementById("enddate").value
				$
						.ajax({
							type : "POST",
							url : "${pageContext.request.contextPath}/reservedRegistration/addUser",
							data : {
								usercardid : usercardid,
								displayname : displayname,
								boxid : boxid,
								effectivedays : effectivedays,
								enddate : enddate
							},
							success : function(msg) {
								showMsg(eval("(" + msg + ")").msg);
								submitForm();
								location.href = location.href;

							},
						});
			},
			cancelValue : $.i18n.prop('btn.cancel'),
			cancel : function() {

			}
		});
		dialogCode.width(450);
		dialogCode.showModal();
	};
	//查询  
	var openDialog4Searching = function() {
		document.getElementById("box").style.display = "none";
		document.getElementById("eff").style.display = "none";
		document.getElementById("end").style.display = "none";
		document.getElementById("dis").style.display = "none";
		document.getElementById("cold").style.display = "block";
		document.getElementById("idc").style.display = "block";
		document.getElementById("use").style.display = "block";
		document.getElementById("bin").style.display = "block";
		dialogCode = dialog({
			title : "查询",
			content : $('#selectShowadd'),
			okValue : $.i18n.prop('btn.ok'),
			ok : function() {
				var usercardid = document.getElementById("usercardid1").value;
				var username = document.getElementById("username1").value
				var idcode = document.getElementById("idcode1").value
				var binding = document.getElementById("binding1").value
				$
						.ajax({
							type : "POST",
							url : "${pageContext.request.contextPath}/reservedRegistration/selectTiedBox",
							data : {
								usercardid : usercardid,
								username : username,
								idcode : idcode,
								binding : binding
							},
							success : function(msg) {
								submitForm();
								//$("#datatable").trigger('reloadGrid');
								$('#selectShowadd').clearForm(true);
							},
						});
			},
			cancelValue : $.i18n.prop('btn.cancel'),
			cancel : function() {

			}
		});
		dialogCode.width(450);
		dialogCode.showModal();
	};

	$(function() {
		$(document).keypress(function(e) {
			if (e.keyCode == 13) {
				submitForm();
				$('#searchForm').clearForm(true);
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
	};
</script>
</body>
</html>