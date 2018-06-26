<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path;
	request.setAttribute("basePath", basePath);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title><spring:message code="role.title.text" /></title>
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

td#datatable_toppager_center {
	display: none;
}
</style>
</head>
<body style="overflow-x: hidden">
	<div class="content">
		<!-- 添加新的角色数据 -->
		<div id="roleInfo" align="center" style="display: none;">
			<form id="modifyCode" method="post"
				action="${pageContext.request.contextPath}/roleAction/insertRole.do">
				<div class="list" align="center">
					<ul>
						<li><spring:message code="role.rolename" />：<input
							id="rolenameadd" name="rolenameadd" type="text" value=""
							maxlength="20" onblur="roleNameOnblue()" /></li>  
						<li><spring:message code="role.memo" />：	
						<textarea  name="memo" id="memo"  cols="40" rows="3"  style="width: 200px;
	                     resize:none;  border: 1px solid #ccc; border-radius: 3px;"></textarea> 
						<!-- <input id="memo" name="memo" type="text" value="" maxlength="40" /></li>  -->
						<li style="display: none;"><INPUT type="hidden" id="rolecode" name="rolecode" /></li>
						<%-- <li><input type="submit" value="<spring:message code="btn.submit" />"  style="width: 60px; font-size: 14px; " /></li> --%>
					</ul>
				</div>
			</form>
		</div>
		<div id="roleInfo_1" align="center" style="display: none;">
			<form id="searchForm"
				action="${pageContext.request.contextPath}/roleAction/selectRole.do"
				method="post">
				<div class="list" align="center">
					<ul>
						<li><spring:message code="role.search.tip" />：<input
							type="text" name="rolename" id="rolename" maxlength="20" /></li>
					</ul>
					<%-- <ul style="float:right;margin-right:-175px;">   
				<li style="margin-left: 50px;"> <input type="button" name="selectRoleName" value='<spring:message code="btn.search" />'
								onclick="submitFormSearch()" style="width: 88px; font-size: 14px; "/></li>
		   </ul> --%>
				</div>
			</form>
		</div>
	</div>
	<!-- 控制表格 的显示 -->
	<div class="tab">
		<table id="datatable"></table>
		<div id="pager2"></div>
	</div>
	<div id="setRole" style="display: none;">
		<FORM id="fromtijiao"
			action="${pageContext.request.contextPath}/menuRightsController/insertRightsController.do"
			method="post">
			<ul id="treeDemo" class="ztree"
				style="margin-left: 40px; margin-top: 15px; width: 400px; height: 440px;"></ul>
		</FORM>
	</div>
	<div id="daying" style="display: none;"></div>
	<script type="text/javascript">
		/* 查询 当摁下 enter键后执行 button ID为selectButton 点击事件 */
		document.onkeydown = function(event) {
			var e = event || window.event
					|| arguments.callee.caller.arguments[0];
			if (e && e.keyCode == 13) { // enter 键           	
				if (document.activeElement.id == "rolename") {
					$(".ui-dialog-autofocus").trigger("click");
					return false;
				}
			}
		};
		//判断输入的值是否存在 
		function roleNameOnblue() {
			var rolenameadd = document.getElementById("rolenameadd").value; 
			$
					.ajax({
						type : "POST",
						datatype : "json",
						data : {
							rolenameadd : rolenameadd
						},
						url : "${pageContext.request.contextPath}/roleAction/selectRoleName.do", 
						success : function(msg) {
							/* showMsg(eval("(" + msg + ")").msg); */
							if (msg == "[]") {
								return false;
							} else { 
								var dataObj = eval("(" + msg + ")"); 
								if (rolenameadd == dataObj[0].rolename) {
									showMsg("<spring:message code='role.prompt1.information' />");  
									document.getElementById("rolenameadd").value = "";
									return false;
								}	
							}
						},
					});
		};
		//查询
		function submitFormSearch() {
			$("#datatable").jqGrid('setGridParam', {
				postData : formToJson("searchForm"),
				page : 1
			}).trigger("reloadGrid");
		}

		function formToJson(rolecode) {//把form表单的所有数据变成json对象
			var formArray = $('#' + rolecode).serializeArray();//数组里面首先放入充值记录表的内容
			var formJson = new Object();
			$.each(formArray, function(i, n) {//把formarray转换为json
				if ((n.name != undefined && n.name != "")) {
					formJson[n.name] = n.value;
				}
			});
			return formJson;
		}

		//添加权限 
		function saveAblty(rolename, rolecode) {
			var roleTree = $.fn.zTree.getZTreeObj("treeDemo");
			roleTree.checkAllNodes(false);//取消所有的勾选
			//roleTree.expandAll(false);//关闭所有的节点
			//查询用户的菜单权限然后打印到ztree上 
			$
					.ajax({
						type : "post",
						url : "${pageContext.request.contextPath}/menuRightsController/selectRightsController.do",
						data : {
							rolecode : rolecode
						},
						success : function(data) {
							if (data.success) {
								$.each($.parseJSON(data.data),
										function(i, n) {
											var node = roleTree.getNodeByParam(
													"id", n);
											roleTree.checkNode(node, true,
													false);
										});
							} else {
								alert(data.msg);
							}
						},
						error : function() {
							alert($.i18n.prop('ajax.post.error'));
						}
					});
			//判断如果是取到值了说明用户有权限 就做修改权限的操作 
			var d = dialog({
				title : rolename + ' ' + $.i18n.prop('role.dlg.title'),
				content : $('#setRole'),
				okValue : $.i18n.prop('btn.save'),
				ok : function() {
					var ids = "";
					var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
					var nodes = treeObj.getCheckedNodes(true);
					if (nodes) {
						$.each(nodes, function(i, n) {
							ids += "," + n.id;
						});
					}
					$
							.ajax({
								type : "POST",
								dataType : "json",
								url : "${pageContext.request.contextPath}/menuRightsController/insertRightsController.do",
								data : {
									rolecode : rolecode,
									ids : ids
								},
								success : function(data) {
									if (data.success) {
										$.i18n.prop("tip.success");
									}
								},
							});

				},
				cancelValue : $.i18n.prop('btn.cancel'),
				cancel : function() {
				}
			});
			d.width(550);
			d.height(500);
			d.showModal();
		}

		var win;
		$(document)
				.ready(
						function() {
							$
									.ajax({
										type : "post",
										url : "${pageContext.request.contextPath}/menuAction/getmenulist.do",
										dataType : 'json',
										success : function(zNodes) {
											$.fn.zTree.init($("#treeDemo"),
													setting, zNodes);
										},
										error : function(XMLHttpRequest,
												textStatus, errorThrown) {
										}
									});
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
												url : "${pageContext.request.contextPath}/roleAction/selectRole.do",
												datatype : "json",
												mtype : "POST",
												width : document.body.offsetWidth - 60,
												height : window.screen.availHeight - 325,
												//autowidth:true,
												editable : false,
												shrinkToFit : true,
												colNames : [
													$.i18n
													.prop('role.table.col.text.coding'),
											$.i18n
													.prop('role.table.col.text.appellation'),
											$.i18n
													.prop('role.table.col.text.remark'),
											$.i18n
													.prop('role.table.col.text.operation') ],
									colModel : [
											{
												name : "rolecode",
												index : "rolecode",
												align : 'center',
												width : 300,
												sortable : true,
												hidden:true
											},
											{
												name : "rolename",
												index : 'rolename',
												align : 'center',
												width : 300,
												sortable : true
											},
											{
												name : "memo",
												index : "memo",
												align : 'center',
												width : 300,
												sortable : false
											},
											{
												name : "",
												index : '',
												align : 'center',
												width : 80,
												sortable : false,
												formatter : function(
															el,
																	options,
																	rowData) {
																var d = '<input type="button" value="<spring:message code='role.prompt4.information' />" onclick="saveAblty(\''
																		+ rowData.rolename
																		+ '\',\''
																		+ rowData.rolecode
																		+ '\')" style="width:80px;float:right;margin-right:30px;"/> ';
																/* alert(rowData.rolecode); */
																return d;
															}
														} ],
												sortable : false,
												rowNum : 10,
												rownumbers : false,
												multiselect : true,
												multiboxonly : true,
												pager : '#pager2',
												rowList : [ 10, 15, 20 ],
												toppager : true,
												sortname : 'roleCode',
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
													if (RowData.rolecode) {
														selectRole('<spring:message code="btn.view" />', rowid);
													}
												},
											});

							$("#datatable").jqGrid("navGrid", "#pager2", {
								cloneToTop : true,
								/* search : false, */
								addfunc : addMakeCard, // (1) 点击添加按钮，则调用addMakeCard方法  
								addtext : $.i18n.prop("btn.add"),
								editfunc : updateRole, // (2) 点击添加按钮，则调用updateRole方法  
								edittext : $.i18n.prop("btn.update"),
								delfunc : deleteRole, // (3) 点击添加按钮，则调用deleteRole方法  
								deltext : $.i18n.prop("btn.delete"),
								searchfunc : searchRole, // (4) 点击添加按钮，则调用searchRole方法  
								searchtext : $.i18n.prop("btn.search"),
								alerttext : $.i18n.prop("param.minrow")
							// (5) 当未选中任何行而点击编辑、删除、查看按钮时，弹出的提示信息  
							});

						});
		//查询数据 
		var dialogCode = "";
		var searchRole = function(row) {
			var dialogCode = dialog({
				title : $.i18n.prop('btn.search'),
				content : $('#searchForm'),
				okValue : $.i18n.prop('btn.ok'),
				ok : function() {
					$
							.ajax({
								type : "POST",
								url : "${pageContext.request.contextPath}/roleAction/selectRole.do",
								success : function(msg) {
									showMsg($.i18n.prop('tip.success'));
									submitFormSearch();
									$('#searchForm').clearForm(true);
								}
							});
				},
				cancelValue : $.i18n.prop('btn.cancel'),
				cancel : function() {
					$('#searchForm').clearForm(true);
				}
			});
			dialogCode.width(400);
			dialogCode.showModal();
		};
		//添加    
		var addMakeCard = function(row) { //弹出添加窗口 修改窗口   
			document.getElementById("rolecode").disabled = false;
			document.getElementById("rolenameadd").disabled = false;
			document.getElementById("memo").disabled = false;
			$('#formAddHandlingFee').clearForm(true);
			dialogCode = dialog({
				title : $.i18n.prop('role.dlg.add'),
				content : $('#roleInfo'),
				okValue : $.i18n.prop('btn.ok'),
				ok : function() {
					if ($("#rolenameadd").val() == '') {
						var dd = dialog({
							content : '<spring:message code="role.prompt2.information" />',
							quickClose : true
						}).show();
						setTimeout(function() {
							dd.close().remove();
						}, 1000);
						return false;
					} else {
						$
								.ajax({
									type : "POST",
									url : "${pageContext.request.contextPath}/roleAction/insertRole.do",
									data : {
										rolenameadd : $("#rolenameadd").val(),
										memo : $("#memo").val()
									},
									success : function(data) {
										showMsg(eval("(" + data + ")").msg);
										submitFormSearch();
										$('#roleInfo').clearForm(true);

									},
								});
					}
				},
				cancelValue : $.i18n.prop('btn.cancel'),
				cancel : function() {
					$('#roleInfo').clearForm(true);
				}
			});

		    dialogCode.width(400);
			dialogCode.height(130);
			dialogCode.showModal();
		}
		//显示更新数据
		var updateRole = function(row) { //弹出添加窗口 修改窗口  
			document.getElementById("rolecode").disabled = true;
			document.getElementById("rolenameadd").disabled = false;
			document.getElementById("memo").disabled = false;
			if (row > 0) {
				var col = $("#datatable").jqGrid('getRowData', row);
				$('#rolecode').val(col.rolecode);
				$('#rolenameadd').val(col.rolename);
				$('#memo').val(col.memo);
			}
			dialogCode = dialog({
				title : $.i18n.prop('btn.update'),
				content : $('#roleInfo'),
				okValue : $.i18n.prop('btn.ok'),
				ok : function() {
					$
							.ajax({
								type : "POST",
								url : "${pageContext.request.contextPath}/roleAction/insertRole.do",
								data : {
									rolecode : $("#rolecode").val(),
									rolenameadd : $("#rolenameadd").val(),
									memo : $("#memo").val()
								},
								success : function(data) {
									showMsg(eval("(" + data + ")").msg);
									submitFormSearch();
									$('#roleInfo').clearForm(true);

								},
							});
				},
				cancelValue : $.i18n.prop('btn.cancel'),
				cancel : function() {
					$('#roleInfo').clearForm(true);
				}
			});

		    dialogCode.width(400);
			dialogCode.height(130);
			dialogCode.showModal();
		}
	//删除数据 
	var deleteRole = function(row) {
		var col = $("#datatable").jqGrid('getRowData', row);
		var ids = jQuery("#datatable").jqGrid('getGridParam', 'selarrrow');
		if (ids.length > 1) {
			showMsg("无法多行删除");
		} else {
			var rolecode = col.rolecode
			var dialogCode = dialog({
				title : $.i18n.prop('tip.title'),
				content : $.i18n.prop('tip.confirm.delete'),
				okValue : $.i18n.prop('btn.ok'),
				ok : function() {
					this.title($.i18n.prop('tip.loading'));
					$
							.ajax({
								type : "post",
								url : "${pageContext.request.contextPath}/roleAction/selectAccountRoleCode.do",
								data : {
									rolecode : rolecode
								},
								dataType : 'json',//JSON.stringify(
								success : function(msg) {
									if (msg == "" || msg == null) {
										$
												.ajax({
													type : "post",
													url : "${pageContext.request.contextPath}/roleAction/delectRole.do",
													data : {
														rolecode : rolecode
													},
													dataType : 'json',//JSON.stringify(
													success : function(msg) {
														$("#datatable")
																.trigger(
																		'reloadGrid');
														showMsg($.i18n
																.prop('tip.success'));
													},
													error : function(
															XMLHttpRequest,
															textStatus,
															errorThrown) {
														showMsg($.i18n
																.prop('tip.faul'));
													}
												});
									} else {
											showMsg("<spring:message code='role.prompt3.information' />")
											return true;
										}
									},
								});
					},
					cancelValue : $.i18n.prop('btn.cancel'),
					cancel : function() {
					}
				});
				dialogCode.width(200);
				dialogCode.showModal();
			}
		};

		function selectRole(title, row) { //弹出查看窗口
			document.getElementById("rolecode").disabled = true;
			document.getElementById("rolenameadd").disabled = true;
			document.getElementById("memo").disabled = true;
			var col = $("#datatable").jqGrid('getRowData', row);
			$('#rolecode').val(col.rolecode);
			$('#rolenameadd').val(col.rolename);
			$('#memo').val(col.memo);
			dialogCode = dialog({
				title : title,
				content : $('#roleInfo'),
				cancelValue : $.i18n.prop('btn.cancel'),
				cancel : function() {
					$('#modifyCode').clearForm(true);
				}
			});
			dialogCode.width(400);
			dialogCode.height(130);
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