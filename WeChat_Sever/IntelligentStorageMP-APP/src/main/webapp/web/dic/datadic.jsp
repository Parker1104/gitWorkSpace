<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title><spring:message code='datadic.title.text'/></title>
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
	<div class="content" align="center" style="display: none;">
		<form
			action="${pageContext.request.contextPath}/datadicController/selectDatadic.do"
			id="searchMemberCardForm" method="post">
			<div class="list">
				<ul>
					<li><spring:message code="datadic.dictname" />：<input
						name="dictname" id="dictname" type="text" /></li>
				</ul>
				<!-- <ul style="float:right;margin-right:-110px;">   
       <li style="margin-left: 50px;"><input type="button" id="selectDicttype" onclick="submitForm()" value="查询"  style="width: 88px; font-size: 14px; "/></li>
      </ul> -->
			</div>
		</form>
	</div>
	<div class="title" align="center" style="display: none;">
		<form action="" id="modifyCode" method="post">
			<div class="list">
				<ul>
					<li style="width: 305px;"><spring:message
							code="datadic.dicttypecode" />：<input type="text"
						id="dictTypeCode" name="dictTypeCode" maxlength="20"
						onkeyup="value=value.replace(/[^\a-\z\A-\Z0-9]/g,'')"
						onpaste="value=value.replace(/[^\a-\z\A-\Z0-9]/g,'')"
						oncontextmenu="value=value.replace(/[^\a-\z\A-\Z0-9]/g,'')" /><span
						style="color: red">*</span></li>
					<li style="width: 305px;"><spring:message
							code="datadic.dicttypename" />：<input type="text"
						id="dictTypeName" name="dictTypeName" maxlength="64" /><span
						style="color: red">*</span></li>
					<li style="width: 310px;"><spring:message
							code="datadic.dictcode" />： <input type="text" id="dictCode"
						name="dictCode" onblur="datadicOnblue()"
						onkeyup="value=value.replace(/[^\a-\z\A-\Z0-9]/g,'')"
						onpaste="value=value.replace(/[^\a-\z\A-\Z0-9]/g,'')"
						oncontextmenu="value=value.replace(/[^\a-\z\A-\Z0-9]/g,'')" /> <span
						style="color: red">*</span></li>
					<li style="width: 305px;"><spring:message
							code="datadic.dictname" />： <input type="text" id="dictName"
						name="dictName" maxlength="64" /><span style="color: red">*</span></li>
					<li><spring:message code="datadic.memo" />：
					<!-- <input type="text" id="memo" name="memo" maxlength="255"> -->
					<textarea  name="memo" id="memo"  cols="40" rows="3"  style="width: 200px;
	                 resize:none;  border: 1px solid #ccc; border-radius: 3px;"></textarea> 
					</li>
				</ul>
			</div>
		</form>
	</div>
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
				if (document.activeElement.id == "dictname") {
					$(".ui-dialog-autofocus").trigger("click");
					return false;
				}
			}
		};

		function datadicOnblue() {
			var dicttypecode = document.getElementById("dictTypeCode").value;
			var dictcode = document.getElementById("dictCode").value;
			$
					.ajax({
						type : "POST",
						datatype : "json",
						data : {
							dicttypecode : dicttypecode,
							dictcode : dictcode
						},
						url : "${pageContext.request.contextPath}/datadicController/selectDatadicCode.do",
						success : function(msg) {
							if (msg == "null") {
								return false;
							} else {
								var dataObj = eval("(" + msg + ")");
								if ($("#dictTypeCode").val() == dataObj.dicttypecode
										&& $("#dictCode").val() == dataObj.dictcode) {
									showMsg("<spring:message code='datadic.prompt1.information' />");
									document.getElementById("dictTypeCode").value = "";
									document.getElementById("dictCode").value = "";
									return false;
								}
							}
						}
					});

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
												url : '${pageContext.request.contextPath}/datadicController/selectDatadic.do',
												datatype : "json",
												mtype : "POST",
												width : document.body.offsetWidth - 60,
												height : window.screen.availHeight - 325,
												//autowidth:true,
												editable : false,
												shrinkToFit : true,
												colNames : [
														$.i18n
																.prop('datadic.table.col.text.CategoryNumber'),
														$.i18n
																.prop('datadic.table.col.text.CategoryName'),
														$.i18n
																.prop('datadic.table.col.text.SubclassNumber'),
														$.i18n
																.prop('datadic.table.col.text.SubclassName'),
														$.i18n
																.prop('datadic.table.col.text.memo') ],
												colModel : [ {
													name : "dicttypecode",
													index : "dicttypecode",
													align : 'center',
													width : 150,
													sortable : false
												}, {
													name : "dicttypename",
													index : "dicttypename",
													align : 'center',
													width : 150,
													sortable : false
												}, {
													name : "dictcode",
													index : "dictcode",
													align : 'center',
													width : 150,
													sortable : false
												}, {
													name : "dictname",
													index : "dictname",
													align : 'center',
													width : 150,
													sortable : false
												}, {
													name : "memo",
													index : "memo",
													align : 'center',
													width : 150,
													sortable : false
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
												sortname : 'dicttypecode',
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
													if (RowData.dicttypecode) {
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
								addfunc : addMakeCard,
								addtext : $.i18n.prop("btn.add"),
								editfunc : updata,
								edittext : $.i18n.prop("btn.update"),
								delfunc : deleteDict,
								deltext : $.i18n.prop("btn.delete"),
								searchfunc : searchDict,
								searchtext : $.i18n.prop("btn.search"),
								alerttext : $.i18n.prop("param.minrow")
							});

						});
		var dialogCode = null;
		//查询数据 
		var searchDict = function(row) {
			var dialogCode = dialog({
				title : $.i18n.prop('btn.search'),
				content : $('#searchMemberCardForm'),
				okValue : $.i18n.prop('btn.ok'),
				ok : function() {
					var dictname = document.getElementById("dictname").value;
					$
							.ajax({
								type : "POST",
								url : "${pageContext.request.contextPath}/datadicController/selectDatadic.do",
								data : {
									dictname : dictname
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
			dialogCode.width(350);
			dialogCode.showModal();
		};
		//删除数据 
		var deleteDict = function(row) {
		var col = $("#datatable").jqGrid('getRowData', row);
		var ids = jQuery("#datatable").jqGrid('getGridParam', 'selarrrow');
		if (ids.length > 1) {
			showMsg("无法多行删除");
		} else {
			var dialogCode = dialog({
				title : $.i18n.prop('tip.title'),
				content : $.i18n.prop('tip.confirm.delete'),
				okValue : $.i18n.prop('btn.ok'),
				ok : function() {
					this.title($.i18n.prop('tip.loading'));
					$
							.ajax({
								type : "post",
								url : "${pageContext.request.contextPath}/datadicController/delectDatadic.do",
								data : {
									dicttypecode : col.dicttypecode,
									dictcode : col.dictcode
								},
								dataType : 'json',//JSON.stringify(
								success : function(msg) {
									$("#datatable").trigger('reloadGrid');
									showMsg("<spring:message code='datadic.prompt2.information' />");
								},
								error : function(XMLHttpRequest, textStatus,
										errorThrown) {
									showMsg("<spring:message code='datadic.prompt3.information' />");
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
		//添加    
		var addMakeCard = function(row) { //弹出添加窗口 修改窗口  
			document.getElementById("dictTypeCode").disabled = false;
			document.getElementById("dictTypeName").disabled = false;
			document.getElementById("dictCode").disabled = false;
			document.getElementById("dictName").disabled = false;
			document.getElementById("memo").disabled = false;
			dialogCode = dialog({
				title : $.i18n.prop('btn.add'),
				content : $('#modifyCode'),
				okValue : $.i18n.prop('btn.ok'),
				ok : function() {
					if ($("#dictTypeCode").val() == '') {
						showMsg("<spring:message code='datadic.prompt4.information' />");
						return false;
					} else if ($("#dictTypeName").val() == '') {
						showMsg("<spring:message code='datadic.prompt5.information' />");
						return false;
					} else if ($("#dictCode").val() == '') {
						showMsg("<spring:message code='datadic.prompt5.information' />");
						return false;
					} else if ($("#dictName").val() == '') {
						showMsg("<spring:message code='datadic.prompt7.information' />");
						return false;
					} else {
						$
								.ajax({
									type : "POST",
									url : "${pageContext.request.contextPath}/datadicController/saveOrUpdateDatadic.do",
									data : {
										dictTypeCode : $("#dictTypeCode").val(),
										dictTypeName : $("#dictTypeName").val(),
										dictCode : $("#dictCode").val(),
										dictName : $("#dictName").val(),
										memo : $("#memo").val()
									},
									success : function(data) {
										showMsg(eval("(" + data + ")").msg);
										submitForm();
										$('#modifyCode').clearForm(true);
									},
								});
					}
				},

				cancelValue : $.i18n.prop('btn.cancel'),
				cancel : function() {
					$('#modifyCode').clearForm(false);
				}
			});

			dialogCode.width(400);
			dialogCode.height(230);
			dialogCode.showModal();
		}
		//修改     
		var dialogCode = null;
		var updata = function(row) { //弹出添加窗口 修改窗口 
			document.getElementById("dictTypeCode").disabled = true;
			document.getElementById("dictTypeName").disabled = false;
			document.getElementById("dictCode").disabled = true;
			document.getElementById("dictName").disabled = false;
			document.getElementById("memo").disabled = false;
			if (row > 0) {
				var col = $("#datatable").jqGrid('getRowData', row);
				$("#dictTypeCode").val(col.dicttypecode);
				$("#dictTypeName").val(col.dicttypename);
				$("#dictCode").val(col.dictcode);
				$("#dictName").val(col.dictname);
				$("#memo").val(col.memo);
			}
			dialogCode = dialog({
				title : $.i18n.prop('btn.update'),
				content : $('#modifyCode'),
				okValue : $.i18n.prop('btn.ok'),
				ok : function() {
					$
							.ajax({
								type : "POST",
								url : "${pageContext.request.contextPath}/datadicController/saveOrUpdateDatadic.do",
								data : {
									dictTypeCode : $("#dictTypeCode").val(),
									dictTypeName : $("#dictTypeName").val(),
									dictCode : $("#dictCode").val(),
									dictName : $("#dictName").val(),
									memo : $("#memo").val()
								},
								success : function(data) {
									showMsg(eval("(" + data + ")").msg);
									submitForm();
									$('#modifyCode').clearForm(true);
								},
							});
				},
				cancelValue : $.i18n.prop('btn.cancel'),
				cancel : function() {
					$('#modifyCode').clearForm(false);
				}
			});

			dialogCode.width(400);
			dialogCode.height(230);
			dialogCode.showModal();
		}
		//查看窗口
		function selectMakeCard(title, row) {
			document.getElementById("dictTypeCode").disabled = true;
			document.getElementById("dictTypeName").disabled = true;
			document.getElementById("dictCode").disabled = true;
			document.getElementById("dictName").disabled = true;
			document.getElementById("memo").disabled = true;
			/* alert(row); */
			var col = $("#datatable").jqGrid('getRowData', row);
			$("#dictTypeCode").val(col.dicttypecode);
			$("#dictTypeName").val(col.dicttypename);
			$("#dictCode").val(col.dictcode);
			$("#dictName").val(col.dictname);
			$("#memo").val(col.memo);
			dialogCode = dialog({
				title : title,
				content : $('#modifyCode'),
				cancelValue : $.i18n.prop('btn.cancel'),
				cancel : function() {
					$('#modifyCode').clearForm(true);
				}
			});
			dialogCode.width(450);
			dialogCode.height(230);
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