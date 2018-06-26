<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
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
<title>箱体预留查询</title>
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
		<form id="searchForm" action="" method="post">
			<div class="list">
				<ul>
					<li>班级名称：<input id="subdepartment1" name="subdepartment"
						type="text" maxlength="32" /></li>
					<li>状态：<select id="state1" name="state">
							<option value="">请选择</option>
							<option value="0">正常</option>
							<option value="1">停用</option>
					</select></li>
				</ul>
			</div>
		</form>
	</div>
	<div id="showadd" class="list" style="display: none">
		<form id="modifyCode"
			action="${basePath}/reservedClassRegistration/addClassRegistration"
			method="post">
			<ul>
				<!--        <li>终端编号：<input id="terminalid" name="terminalid" type="text" maxlength="24" onclick="readCard()"/></li>
<li>班级ID：<input id="gradeid" name="gradeid" type="text" maxlength="32"/></li> 
<li>班级名称：<input id="subdepartment" name="subdepartment" type="text" maxlength="32"/></li>-->
				<li>班级名称 ： <select id="subdepartment" name="subdepartment">
						<option value="" selected="selected">请选择</option>
						<c:forEach var="u" items="${UserEntity}">
							<option value="<c:out value='${u.address}'/>">
								<c:out value='${u.address}' /></option>
						</c:forEach>
				</select></li>
				<li>柜号：<select id="displayname" name="displayname"
					onchange="selectBox(0,0,0)">
						<option value="" selected="selected">请选择</option>
						<c:forEach var="t" items="${TerminalEntity}">
							<option value="<c:out value='${t.displayname}'/>">
								<c:out value='${t.displayname}' /></option>
						</c:forEach>
				</select></li>
				<li>箱编号：<select id="boxid" name="boxid">
						<option value="" selected="selected">请选择</option>
				</select></li>
				<!-- 			<li>箱编号：<input id="boxid" name="boxid" type="text"/></li>-->
				<li>状态：<select id="state" name="state">
						<option value="0">正常</option>
						<option value="1">停用</option>
				</select></li>
				<li>有效日期：<input id="enddate" name="enddate" type="text"
					class="Wdate" class="validate[required]"
					onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" /></li>
				<li>折扣率：<input id="discountrate" name="discountrate"
					type="number" min="0" max="100" /></li>
				<li>备注：<input id="memo" name="memo" type="text" /></li>
			</ul>
		</form>
	</div>
	<script type="text/javascript">
		function selectBox(standard, row, identifier) {

			var displayname = document.getElementById("displayname").value;

			$.ajax({
				type : "POST",
				url : "${basePath}/reservedClassRegistration/selectBox",
				data : {
					displayname : displayname
				},
				success : function(msg) {
					if (msg != "[]") {

						var dataObj = eval("(" + msg + ")");
						if (identifier == 0) {
							$("#boxid").empty();
						}
						if (standard == 1) {
							$("#boxid").empty();
							selectBoxName(row);
						}
						for (var i = 0; i < dataObj.length; i++) {
							$("#boxid").append(
									'<option value="' +dataObj[i].boxid+ '">'
											+ dataObj[i].dispalyname
											+ '</option>');
						}

					}
				}
			});

		};

		function selectBoxName(row) {

			var col = $("#datatable").jqGrid('getRowData', row);

			$.ajax({
				type : "POST",
				url : "${basePath}/reservedClassRegistration/selectBoxName",
				data : {
					terminalid : col.terminalid,
					boxid : col.boxid
				},
				success : function(msg) {
					if (msg != "[]") {
						$("#boxid").empty();
						var dataObj = eval("(" + msg + ")");
						$("#boxid").append(
								'<option value="' +dataObj.boxid+ '">'
										+ dataObj.dispalyname + '</option>');
						selectBox(0, 0, 1);

					}
				}
			});

		};

		function submitForm() {
			$("#datatable").jqGrid('setGridParam', {
				postData : formToJson("searchForm"),
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
												url : '${basePath}/reservedClassRegistration/selectClassRegistration',
												datatype : "json",
												mtype : "POST",
												width : document.body.offsetWidth - 60,
												height : window.screen.availHeight - 325,
												//autowidth:true,
												editable : false,
												shrinkToFit : true,
												colNames : [ '终端编号', '班级ID',
														'班级名称', '柜号', '箱号',
														'有效期', '状态', '折扣率%',
														'创建时间', '最后修改时间', '描述' ],
												colModel : [
														{
															name : "terminalid",
															index : 'terminalid',
															align : 'left',
															width : 70,
															sortable : false,
															hidden : true
														},
														{
															name : "gradeid",
															index : "gradeid",
															align : 'center',
															width : 70,
															sortable : false,
															hidden : true
														},
														{
															name : "subdepartment",
															index : "subdepartment",
															align : 'center',
															width : 70,
															sortable : false
														},
														{
															name : "displayname",
															index : "displayname",
															align : 'center',
															width : 50,
															sortable : false
														},
														{
															name : "boxid",
															index : "boxid",
															frozen : true,
															align : 'center',
															width : 90,
															sortable : false
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
																var t = rowData.enddate;
																var d = new Date();
																d.setTime(t);
																var s = d
																		.format('yyyy-MM-dd HH:mm:ss');
																return s;
															}
														},
														{
															name : "state",
															index : "state",
															align : 'center',
															width : 70,
															sortable : false,
															formatter : function(
																	el,
																	options,
																	rowData) {
																var state = rowData.state;
																if (state == 0) {
																	return "正常";
																} else {
																	return "停用";
																}
															}
														},
														{
															name : "discountrate",
															index : 'discountrate',
															align : 'center',
															width : 90,
															sortable : false,
															hidden : true
														},
														{
															name : "makedate",
															index : 'makedate',
															align : 'center',
															width : 90,
															sortable : false,
															hidden : true,
															formatter : function(
																	el,
																	options,
																	rowData) {
																var t = rowData.makeDate;
																var d = new Date();
																d.setTime(t);
																var s = d
																		.format('yyyy-MM-dd HH:mm:ss');
																return s;

															}
														},
														{
															name : "lastmodifytime",
															index : 'lastmodifytime',
															align : 'center',
															width : 90,
															sortable : false,
															hidden : true,
															formatter : function(
																	el,
																	options,
																	rowData) {
																var t = rowData.lastModifyTime;
																var d = new Date();
																d.setTime(t);
																var s = d
																		.format('yyyy-MM-dd HH:mm:ss');
																return s;
															}
														}, {
															name : "memo",
															index : 'memo',
															align : 'center',
															width : 110,
															sortable : false
														},

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
												rowList : [ 10, 15, 20, 500 ],
												sortname : 'terminalid',
												sortorder : 'desc',
												toppager : true,
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
								searchfunc : openDialog4Searching,
								searchtext : "查询",
								addfunc : openDialog4Adding, // (1) 点击添加按钮，则调用openDialog4Adding方法  
								addtext : "箱体预留",
								editfunc : openDialog4Updating, // (2) 点击添加按钮，则调用openDialog4Updating方法  
								edittext : "修改",
								delfunc : openDialog4Deleting, // (3) 点击添加按钮，则调用openDialog4Deleting方法  
								deltext : "删除",
								alerttext : "请选择需要操作的数据行!" // (4) 当未选中任何行而点击编辑、删除、查看按钮时，弹出的提示信息  
							});
						});
	    //清空空件内容  
	    function Emptying(){
			document.getElementById("subdepartment").value = '';
			document.getElementById("displayname").value = '';
			document.getElementById("boxid").value = '';
			document.getElementById("state").value = 0;
			document.getElementById("enddate").value = '';
			document.getElementById("discountrate").value = '';
			document.getElementById("memo").value = '';
	    };
		var dialogCode = null;
		var openDialog4Adding = function(title, okValue) {
			dialogCode = dialog({
				title : "新增",
				content : $('#showadd'),
				okValue : "保存",
				ok : function() {
					var subdepartment = document
							.getElementById("subdepartment").value;
					var boxid = document.getElementById("boxid").value;
					if (subdepartment == "") {
						showMsg("班级不能为空");
						return false;
					} else if (boxid == "") {
						showMsg("箱门编号不能为空");
						return false;
					} else {
						$('#modifyCode').ajaxSubmit({
							error : function() {
								alert("操作失败");
							},
							success : function(msg) {
								showMsg(eval("(" + msg + ")").msg);
								$("#datatable").trigger('reloadGrid');
								Emptying();
							}
						});
						return true;
					}
				},
				cancelValue : '关闭',
				cancel : function() {
					Emptying();
				}
			});
			dialogCode.width(450);
			dialogCode.showModal();
		};
		var openDialog4Searching = function(title, okValue) {
			dialogCode = dialog({
				title : "查询",
				content : $('#showsearch'),
				okValue : "查询",
				ok : function() {
					$('#searchForm').ajaxSubmit({
						error : function() {
							alert("操作失败");
						},
						success : function(msg) {
							submitForm();
							document.getElementById("subdepartment1").value = '';
							document.getElementById("state1").value = 0;
						}
					});
					return true;
				},
				cancelValue : '关闭',
				cancel : function() {
					document.getElementById("subdepartment1").value = '';
					document.getElementById("state1").value = 0;
				}
			});
			dialogCode.width(400);
			dialogCode.showModal();
		};
		var openDialog4Updating = function(row) {
			var ids = $("#datatable").jqGrid("getGridParam", "selarrrow");
			if (ids.length > 1) {
				showMsg("请选择一行数据！");
			} else {
				if (row > 0) {//说明是修改
					var col = $("#datatable").jqGrid('getRowData', row);
					$('#terminalid').val(col.terminalid);
					$('#gradeid').val(col.gradeid);
					// $('#subdepartment').val(col.subdepartment);
					$("#subdepartment").attr("value", col.subdepartment);
					$("#displayname").attr("value", col.displayname);
					$('#enddate').val(col.enddate);
					/*$('#boxid').val(col.boxid); */
					// $("#boxid").attr("value",col.boxid);
					selectBox(1, row);
					$('#discountrate').val(col.discountrate);
					$('#memo').val(col.memo);
					if (col.state == "正常") {
						$('#state').val(0);
					} else {
						$('#state').val(1);
					}
				}

				dialogCode = dialog({
					title : "修改",
					content : $('#showadd'),
					okValue : "确定",
					ok : function() {
						$('#modifyCode').ajaxSubmit({
							success : function(msg) {
								showMsg(eval("(" + msg + ")").msg);
								$("#datatable").trigger('reloadGrid');
								Emptying();
							}
						});
						return true;
					},
					cancelValue : '关闭',
					cancel : function() {
						Emptying();
					}
				});
				dialogCode.width(600);
				dialogCode.showModal();
			}
		};
		var openDialog4Deleting = function(rowid) {
			var col = $("#datatable").jqGrid('getRowData', rowid);
			var dialogCode = dialog({
				title : '提示',
				content : '您确认想要删除吗？',
				okValue : '确定',
				ok : function() {
					this.title('提交中…');
					$
							.ajax({
								type : "POST",
								url : "${basePath}/reservedClassRegistration/delectClassRegistration",
								data : {
									terminalid : col.terminalid,
									subdepartment : col.subdepartment
								},
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
			dialogCode.width(150);
			dialogCode.showModal();
		};

		function list(title, row) {//弹出查看窗口
			var col = $("#datatable").jqGrid('getRowData', row);
			$('#terminalid').val(col.terminalid);
			$('#gradeid').val(col.gradeid);
			$("#subdepartment").attr("value", col.subdepartment);
			$("#displayname").attr("value", col.displayname);
			//$('#displayname').val(col.displayname);
			$('#boxid').val(col.boxid);
			$('#discountrate').val(col.discountrate);
			$('#enddate').val(col.enddate);
			$('#memo').val(col.memo);
			if (col.state == "正常") {
				$('#state').val(0);
			} else {
				$('#state').val(1);
			}
			dialogCode = dialog({
				title : title,
				content : $('#showadd'),
				cancelValue : '关闭',
				cancel : function() {
					$('#modifyCode').clearForm(true);
				}
			});
			dialogCode.width(600);
			dialogCode.showModal();
		}
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
		}
	</script>
</body>
</html>