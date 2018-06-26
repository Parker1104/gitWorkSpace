<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title><spring:message code="area.title" /></title>
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
	src="${pageContext.request.contextPath}/js/plug/zTree/js/jquery.ztree.exhide-3.5.js"></script>

<style type="text/css">
#zTreeDemoBackground_1 {
	position: absolute;
	margin-top: 30px;
	width: 100%;
	height: 95%;
}

#zTreeDemoBackground_2 {
	position: absolute;
	margin-top: 5px;
	height: 25px;
	width: 50%;
	left: 25%;
}

#zTreeDemoBackground_3 {
	position: absolute;
	margin-top: 30px;
	left: 36%;
	width: 50%;
	height: 95%;
}

div#rMenu {
	position: absolute;
	visibility: hidden;
	top: 0;
	background-color: #231444;
	text-align: left;
	padding: 1px;
}

div#rMenu ul li {
	margin: 1px 0;
	padding: 0 10px;
	cursor: pointer;
	text-align: center;
	font-weight: 500;
	font-size: 13px;
	list-style: none outside none;
	background-color: #F0F0F0;
}
</style>
</head>
<body>
	<script type="text/javascript">
		var setting = {
			view : {
				selectedMulti : false,
				showIcon : true
			},
			edit : {
				enable : true
			},
			data : {
				keep : {
					parent : true,
					leaf : false
				},
				simpleData : {
					enable : true
				}
			},
			callback : {
				onClick : zTreeOnClick,
				onRightClick : OnRightClick
			}
		};
		/*  */
		function OnRightClick(event, treeId, treeNode) {
			if (!treeNode && event.target.tagName.toLowerCase() != "button"
					&& $(event.target).parents("a").length == 0) {
				zTree.cancelSelectedNode();
				showRMenu("root", event.clientX, event.clientY);
			} else if (treeNode && !treeNode.noR) {
				zTree.selectNode(treeNode);
				showRMenu("node", event.clientX, event.clientY);
			}
		}

		function showRMenu(type, x, y) {
			$("#rMenu ul").show();
			rMenu.css({
				"top" : y + "px",
				"left" : x + "px",
				"visibility" : "visible"
			});
			$("body").bind("mousedown", onBodyMouseDown);
		}
		function hideRMenu() {
			if (rMenu)
				rMenu.css({
					"visibility" : "hidden"
				});
			$("body").unbind("mousedown", onBodyMouseDown);
		}
		function onBodyMouseDown(event) {
			if (!(event.target.id == "rMenu" || $(event.target).parents(
					"#rMenu").length > 0)) {
				rMenu.css({
					"visibility" : "hidden"
				});
			}
		}
		function zTreeOnClick(event, treeId, treeNode) {
			document.getElementById("id2").disabled = true;
			document.getElementById("department").disabled = true;
			document.getElementById("memo").disabled = true;
			document.getElementById("areaNameFu").disabled = true;
			
		$.ajax({
					type : "post",
					url : "${pageContext.request.contextPath}/department/selectbyid",
					data : {id : treeNode.id},
					dataType : 'json',//JSON.stringify(
					success : function(msg) {
					    //alert(msg.areacode);
						$("#id2").val(msg.id);
						$("#department").val(msg.department);
						$("#memo").val(msg.memo);
						$.ajax({
							type : "post",
							url : "${pageContext.request.contextPath}/department/selectDepartmentFu",
							data : {id : msg.id},
							dataType : 'json',//JSON.stringify(
							success : function(msg) {
								$("#areaNameFu").val(msg.department);

							},
						});
					},
			});

			dialogCode = dialog({
				title : "<spring:message code='account.table.col13.text' />",
				content : $('#selectarea'),
				cancelValue : $.i18n.prop('btn.cancel'),
				cancel : function() {
					$('#selectarea').clearForm(true);
				}
			});
			dialogCode.width(450);
			/* dialogCode.height(300); */
			dialogCode.showModal();

		};
		function add(e) {
			$('#modifyForm').resetForm();
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			var nodes = zTree.getSelectedNodes();
			var treeNode = nodes[0];

			if (treeNode == null || treeNode == '') {
				var dd = dialog({
					content : '<spring:message code="account.table.col19.text"/>',
					quickClose : true
				}).show();
				setTimeout(function() {
					dd.close().remove();
				}, 1000);
				return false;				
			} else if (treeNode.level >= 9) {
				var dd = dialog({
					content : '<spring:message code="area.prompt1.information"/>',
					quickClose : true
				}).show();
				setTimeout(function() {
					dd.close().remove();
				}, 1000);
				return false;
			} else if (treeNode.level == null || treeNode.level == '') {
				if (treeNode) {
					$("#pId").val(treeNode.id);
				} else {
					$("#pId").val(0)
				}
				win = dialog({
					title : $.i18n.prop('area.btn.add'),
					content : $('#areaInfo'),
					/* id : 'EF893Create', */
					okValue : $.i18n.prop('btn.ok'),
					ok : function() {
						var name = document.getElementById("name").value;
						var memo = document.getElementById("memo").value;
						if (name == "" || name == null) {
							showMsg("<spring:message code='area.prompt2.information'/>");
							return false;
						}
						$.ajax({
							type : "POST",
							url : "${pageContext.request.contextPath}/department/saveDepartmentName",
							data : {
									pId : $("#pId").val(),
									id : $("#id").val(),
									name : $("#name").val(),
									memo : $("#memo").val()
								},
								success : function(msg) {
									showMsg(eval("(" + msg + ")").msg);
									location.href = location.href;
									$('#areaInfo').clearForm(true);
								}
						});
					},
					cancelValue : $.i18n.prop('btn.cancel'),
					cancel : function() {
						$('#areaInfo').clearForm(true);
					}
				});
				win.width(340);
				win.showModal();
			} else {
				if (treeNode) {
					$("#pId").val(treeNode.id);
				} else {
					$("#pId").val(0)
				}
				win = dialog({
					title : $.i18n.prop('area.btn.add'),
					content : $('#areaInfo'),
					/* id : 'EF893Create', */
					okValue : $.i18n.prop('btn.ok'),
					ok : function() {
						var name = document.getElementById("name").value;
						var memo = document.getElementById("memo").value;
						if (name == "" || name == null) {
							showMsg("<spring:message code='area.prompt2.information'/>");
							return false;
						}
						$
								.ajax({
									type : "POST",
									url : "${pageContext.request.contextPath}/department/saveDepartmentName",
									data : {
										pId : $("#pId").val(),
										id : $("#id").val(),
										name : $("#name").val(),
										memo : $("#memo").val()
									},
									success : function(msg) {
										showMsg(eval("(" + msg + ")").msg);
										location.href = location.href;
										$('#areaInfo').clearForm(true);
									}
								});
					},
					cancelValue : $.i18n.prop('btn.cancel'),
					cancel : function() {
						$('#areaInfo').clearForm(true);
					}
				});
				win.width(340);
				win.showModal();
			}
		};

		$('#modifyForm').ajaxForm({
				dataType : "json",
				beforeSerialize : function() {
					if ($('#name').val() == '') {
						showMsg($.i18n.prop("param.notempty").replace("%s",$.i18n.prop('role.tip.rolename')));
							$('#name').select();
							return false;
						  }
						return true;
					},
					success : function(data) {
						if (data.success) {
							var zTree = $.fn.zTree.getZTreeObj("treeDemo");
								var nodes = zTree.getSelectedNodes();
								var treeNode = nodes[0];
								if (treeNode) {
									var tmpobj = $.parseJSON(data.data);
									var updatenode = zTree.getNodeByParam("id", tmpobj.id);
									if (updatenode) {
										updatenode.name = tmpobj.name;
										zTree.updateNode(updatenode);
									} else {
										treeNode = zTree.addNodes(treeNode,
												{
													id : tmpobj.id,
													pId : tmpobj.pId,
													open : true,
													isParent : true,
													name : tmpobj.name
												});
										}
									}
									if (treeNode) {
										//zTree.editName(treeNode[0]);
										showMsg(eval("(" + data + ")").msg);
									} else {
										showMsg("<spring:message code='area.prompt3.information'/>");
									}
									location.href = location.href;
								} else {
									showMsg(eval("(" + data + ")").msg);
								}
								win.close();
							}

						});
		function edit() {
			var zTree = $.fn.zTree.getZTreeObj("treeDemo"), nodes = zTree
					.getSelectedNodes(), treeNode = nodes[0];
			if (nodes.length == 0) {
				showMsg($.i18n.prop("menuright.tip.select"));
				/* location.href = location.href; */
				return false;
			}
			//
			$('#modifyForm').resetForm();
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			var nodes = zTree.getSelectedNodes();
			var treeNode = nodes[0];
			if (treeNode) {
				$("#id").val(treeNode.id);
				$("#pId").val(treeNode.id.substr(0, treeNode.id.length - 2));
				var indexpos = treeNode.name.indexOf("(");
				if (indexpos == -1) {
					$("#name").val(treeNode.name);
				} else {
					$("#name").val(treeNode.name.substring(0, indexpos));
					$("#memo").val(treeNode.name.substring(indexpos + 1, treeNode.name.indexOf(")")));
				}				
			}

			win = dialog({
				title : $.i18n.prop("area.btn.edit"),
				content : $('#areaInfo'),
				/* id : 'EF893Edit' */
				okValue : $.i18n.prop('btn.ok'),
				ok : function() {
					$
							.ajax({
								type : "POST",
								url : "${pageContext.request.contextPath}/department/saveDepartmentName",
								data : {
									pId : $("#pId").val(),
									id : $("#id").val(),
									name : $("#name").val(),
									memo : $("#memo").val()
								},
								success : function(msg) {
									showMsg(eval("(" + msg + ")").msg);
									location.href = location.href;
									$('#areaInfo').clearForm(true);
								}
							});
				},
				cancelValue : $.i18n.prop('btn.cancel'),
				cancel : function() {
					$('#areaInfo').clearForm(true);
				}
			});
			win.width(340);
			win.showModal();
		};
		function remove(e) {
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			var nodes = zTree.getSelectedNodes();
			var treeNode = nodes[0];
			if (nodes.length == 0) {
				showMsg($.i18n.prop("menuright.tip.select"));
				return;
			}
			/* if(prompt($.i18n.prop("tip.confirm.delete2")+treeNode.name)){} */
			var d = dialog({
				title : $.i18n.prop('tip.title'),
				content : $.i18n.prop("tip.confirm.delete2") + treeNode.name,
				okValue : $.i18n.prop('btn.ok'),
				ok : function() {
					this.title($.i18n.prop('tip.loading'));
					$
							.ajax({
								type : "post",
								url : "${pageContext.request.contextPath}/department/removeNode",
								data : {
									"id" : treeNode.id
								},
								dataType : 'json',//JSON.stringify(
								success : function(data) {
									/* alert(data); */
									if (data.msg == "<spring:message code='area.prompt4.information'/>") {
										//var callbackFlag = $("#callbackTrigger").attr("checked");					
										zTree.removeNode(treeNode, false);
										location.href = location.href;
									} else {
										if (data.msg == "<spring:message code='area.prompt5.information'/>") {
											showMsg("<spring:message code='area.prompt5.information'/>");
											location.href = location.href;
										} else {
											showMsg("<spring:message code='area.prompt6.information'/>");
											/* location.href = location.href; */
											return false;
										}

									}
								},
								error : function(XMLHttpRequest, textStatus,
										errorThrown) {
									showMsg(data.msg);
								}
							});
				},
				cancelValue : $.i18n.prop('btn.cancel'),
				cancel : function() {
				}
			});
			d.width(200);
			d.showModal();
		};
		var zTree, rMenu;
		$(document).ready(
			function() {
					$.ajax({
						type : "post",
						url : "${pageContext.request.contextPath}/department/findAll",
						dataType : 'json',//JSON.stringify(
						success : function(zNodes) {
						$.fn.zTree.init($("#treeDemo"),setting, zNodes);
								zTree = $.fn.zTree.getZTreeObj("treeDemo");
								rMenu = $("#rMenu");
						},
						error : function(XMLHttpRequest,textStatus, errorThrown) {
						}
						});
							/* $("#addParent").bind("click", {isParent:true}, add);
							$("#edit").bind("click", edit); 
							$("#remove").bind("click", remove); */

				});
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

	<div class="content_wrap">
		<%-- <p><spring:message code="area.title"/></p> --%>
		<div id="zTreeDemoBackground_1">
			<ul id="treeDemo" class="ztree"
				style="margin-left: 50px; margin-top: 0px; width: 93%; height: 95%;">
			</ul>
		</div>
		<div id="" class="list" style="display: none;">
			<!-- <div class="list" align="center"></div> -->
			<form id="selectarea" action="">
				<ul
					style="margin-left: 10px; margin-top: 0px; background-color: white; width: 350px; height: 250px;">
					<li style="list-style-type: none; width: 425px; height: 50px;"><spring:message
							code="area.information" /></li>
					<li style="list-style-type: none; width: 425px; height: 50px;"><spring:message
							code="area.areacode" />：<input type="text" id="id2" name=""
						value="" /></li>
					<li style="list-style-type: none; width: 425px; height: 50px;"><spring:message
							code="area.areaname" />：<input type="text" id="department" name=""
						value="" /></li>
					<li style="list-style-type: none; width: 425px; height: 50px;"><spring:message
							code="area.areashortname" />：<input type="text"
						id="memo" name="" value="" /></li>
					<li style="list-style-type: none; width: 400px; height: 50px;"><spring:message
							code="area.areanamefu" />：<input type="text" id="areaNameFu"
						name="" value="" /></li>
				</ul>
			</form>
		</div>
		<%--<div id="zTreeDemoBackground_2">
			<ul>
				<li><h1><spring:message code="role.table.col4.text"/>：</h1><br/></li>
				<li style="list-style-type: none;"><input type="button"
					id="addParent" onclick="return false;"
					value="<spring:message code="area.btn.add"/>" /> <input
					type="button" id="edit" onclick="return false;"
					value="<spring:message code="area.btn.edit"/>" /> <input
					type="button" id="remove" onclick="return false;"
					value="<spring:message code="area.btn.del"/>" /></li>
			</ul>
		</div> --%>
		<div id="rMenu">
			<ul>
				<li id="addParent" onclick="add();"><spring:message
						code="area.btn.add" /></li>
				<li id="edit" onclick="edit();"><spring:message
						code="area.btn.edit" /></li>
				<li id="remove" onclick="remove();"><spring:message
						code="area.btn.del" /></li>
			</ul>
		</div>
	</div>

	<div id="areaInfo" align="center" style="display: none;">
		<form id="modifyForm" method="post"
			action="${pageContext.request.contextPath}/department/saveDepartmentName">
			<div class="list" align="center">
				<ul>
					<li style="width: 305px;">部门名称：
						<input id="name" name="name" type="text" value="" maxlength="32" /><span
						style="color: red">*</span></li>
					<li>备注： <input
						id="memo" name="memo" type="text" value=""
						maxlength="32" /></li>
					<li style="display: none;"><input type="hidden" id="id"
						name="id" /></li>
					<li style="display: none;"><input type="hidden" id="pId"
						name="pId" /></li>
					<%-- <input type="submit" value='<spring:message code="btn.submit" />' /> --%>
				</ul>
			</div>
		</form>
	</div>
</body>
</html>