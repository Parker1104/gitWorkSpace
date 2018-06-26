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
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title><spring:message code="boxType.title.text" /></title>
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
	<div id="showadd" class="list" style="display: none;">
		<form id="modifyCode" action="" method="post">
			<ul>
				<li style="width: 309px;"><spring:message code="boxType.boxTypeName" />：<input type="text"
					id="boxTypeName" name="boxTypeName" value="" maxlength="32" onblur="boxTypeNameOnblue()"/>
					<span style="color: red">*</span></li>
				<li style="width: 418px;"><spring:message code="boxType.kuan" />：<input type="text" id="kuan"
					name="kuan" value=""
					onkeyup='this.value=this.value.replace(/\D/gi,"")' /><span
					style="color: red">* <spring:message code="boxType.prompt1.information" /></span></li>
				<li style="width: 418px;"><spring:message code="boxType.gao" />：<input type="text" id="gao"
					name="gao" value=""
					onkeyup='this.value=this.value.replace(/\D/gi,"")' /><span
					style="color: red">* <spring:message code="boxType.prompt1.information" /></span></li>
				<li style="width: 418px;"><spring:message code="boxType.shen" />：<input type="text" id="shen"
					name="shen" value=""
					onkeyup='this.value=this.value.replace(/\D/gi,"")' /><span
					style="color: red">* <spring:message code="boxType.prompt1.information" /></span></li>
				<li>押金：<input type="text" id="beizhu" name="beizhu" />
				<%-- <spring:message code="boxType.beizhu" />：
				<textarea  name="beizhu" id="beizhu"  cols="40" rows="3"  style="width: 200px;
	                 resize:none;  border: 1px solid #ccc; border-radius: 3px;"></textarea>  --%>
				</li>
				<li style="display: none;"><input type="hidden" id="boxTypeCode" name="boxTypeCode" /></li>
			</ul>
		</form>
	</div>
	<div class="content" align="center" style="display: none;">
		<form id="searchMemberCardForm"
			action="${pageContext.request.contextPath}/boxTypeController/selectBoxType.do"
			method="post">
			<div class="list">
				<ul>
					<li><spring:message code="boxType.boxTypeName" />：<input type="text" name="boxtypename"
						id="boxtypename" maxlength="32" /></li>
				</ul>
				<!-- <ul style="float:right;margin-right:-110px;">   
	      <li style="margin-left: 50px;"><input type="button" id="selectBoxtype" value="查询" onclick="submitForm()" style="width: 88px; font-size: 14px; "/></li>
      </ul> -->
			</div>
		</form>
	</div>
	<!-- table的显示成 -->
	<div class="tab">
		<table id="datatable"></table>
		<div id="pager2"></div>
	</div>

	<script type="text/javascript">
	/* 查询 当摁下 enter键后执行 button ID为selectButton 点击事件 */ 
	document.onkeydown=function(event){
	            var e = event || window.event || arguments.callee.caller.arguments[0];           
		             if(e && e.keyCode==13){   // enter 键
		            	 if (document.activeElement.id == "boxtypename"){
							 $(".ui-dialog-autofocus").trigger("click");
		            		 return false;
		            	 }        
		        }
	}; 
	//判断输入的值是否存在 
	function boxTypeNameOnblue() {
		var boxTypeName = document.getElementById("boxTypeName").value;
		$
				.ajax({
					type : "POST",
					datatype : "json",
					data : {
						boxTypeName : boxTypeName
					},
					url : "${pageContext.request.contextPath}/boxTypeController/selectBoxTypeName.do",
					success : function(msg) {
						/* showMsg(eval("(" + msg + ")").msg); */
						if (msg == "[]") {
							return false;
						} else {
							var dataObj = eval("(" + msg + ")");
							if (boxTypeName == dataObj[0].boxtypename) {
								showMsg("<spring:message code='boxType.prompt6.information' />"); 
								document.getElementById("boxTypeName").value = "";
								return false;
							}	
						}
					},
				});

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
		$(document).ready(function() {
			$(window).resize(function() {
				$("#datatable").setGridWidth($(window).width()-50);
			});
				var tableWidth = $("#datatable").parent().innerWidth()+25;
					$("#datatable").jqGrid({
							url : '${pageContext.request.contextPath}/boxTypeController/selectBoxType.do', 
					        datatype: "json",
					        mtype: "POST",
							width : document.body.offsetWidth - 60,
							height : window.screen.availHeight - 325,
				            //autowidth:true,
					        editable: false, 
					        shrinkToFit:true,
							colNames : [ $.i18n.prop('boxType.table.col.text。boxtypecode'), $.i18n.prop('boxType.table.col.text.boxtypename'), $.i18n.prop('boxType.table.col.text.width'),$.i18n.prop('boxType.table.col.text.height'), $.i18n.prop('boxType.table.col.text.depth'),$.i18n.prop('boxType.table.col.text.memo')/* $.i18n.prop('boxType.table.col5.text') */ ],   
							colModel : [
								{name : "boxtypecode",index : "boxtypecode",align : 'center',width : 180,sortable : false,hidden:true},
								{name : "boxtypename",index : "boxtypename",align : 'center',width : 180,sortable : false},
								{name : "width",index : "width",align : 'center',width : 180,sortable : false},
								{name : "height",index : "height",align : 'center',width : 180,sortable : false},
								{name : "depth",index : "depth",align : 'center',width : 180,sortable : false},
								{name : "memo",index : "memo",align : 'center',width : 180,sortable : false/* ,hidden:true */},
								/* {name : "",index : '',align : 'center',width : 100,formatter : function(el,options,rowData) {
				                    var d= '<input type="button" value="删除" onclick="deleteBoxType(\''+rowData.boxtypecode+'\')" style="width:50px;float:right;margin-right:10px;"/>';
				                    var u= '<input type="button" value="修改" onclick="addMakeCard(\'修改类型\',\'修改\','+ options.rowId +')" style="width:50px;float:right;margin-right:10px;"/>';
				                    return u+d;
										}} */ ],
												sortable : false,
												rowNum : 10,
												rownumbers : false,
												multiselect : true,
												multiboxonly:true,												
												jsonReader : {
													repeatitems : false,
													id : "0"
												},
												pager : '#pager2',
												rowList : [ 10, 15, 20 ],
												toppager:true,
												sortname : 'boxtypecode',
												sortorder : 'desc',
												viewrecords : true,
												loadComplete:function(){
													var myGrid = $("#datatable");
													$("#cb_"+myGrid[0].id).hide();
												},
									            ondblClickRow:function(rowid){
										  	    	RowData= jQuery(this).jqGrid("getRowData", rowid);
										  	    	if(RowData.boxtypecode){
										  	    		selectMakeCard("<spring:message code='account.table.col13.text' />",rowid);            	
									               }
									            },
											});
							$("#datatable").jqGrid("navGrid", "#pager2", {
								cloneToTop : true,
								/* search : false, */
							    /* edit:false, */
								addfunc : addMakeCard, // (1) 点击添加按钮，则调用addMakeCard方法  
								addtext : $.i18n.prop("btn.add"),
								editfunc : updateMakeCard, // (2) 点击添加按钮，则调用updateMakeCard方法  
								edittext :  $.i18n.prop("btn.update"),
								delfunc : deleteBoxType, // (3) 点击添加按钮，则调用deleteBoxType方法   
								deltext : $.i18n.prop("btn.delete"),
								searchfunc : searchBoxType, // (4) 点击添加按钮，则调用searchBoxType方法   
								searchtext : $.i18n.prop("btn.search"),
								alerttext : $.i18n.prop("param.minrow") // (5) 当未选中任何行而点击编辑、删除、查看按钮时，弹出的提示信息  
							});
						});
		//查询数据 
		var searchBoxType = function(row) { 
			var dialogCode = dialog({
		        title: $.i18n.prop('btn.search'), 
		        content:  $('#searchMemberCardForm'),
		        okValue: $.i18n.prop('btn.ok'), 
		        ok: function () {
 					var boxtypename = document.getElementById("boxtypename").value;
		            $.ajax({
		                type: "POST",
		                url : "${pageContext.request.contextPath}/boxTypeController/selectBoxType.do",
		                data : {boxtypename :boxtypename}, 
		                success: function(msg){
							showMsg(msg.msg);
							submitForm();
		                	$('#searchMemberCardForm').clearForm(true); 
		                }
		            }); 
		        
		        },
		    cancelValue: $.i18n.prop('btn.cancel'),
		    cancel: function () {
		    	 $('#searchMemberCardForm').clearForm(true);    
		    }
		    });
			dialogCode.width(450);
			dialogCode.showModal();
		};

		//删除数据 
		var dialogCode = null;
		var deleteBoxType = function(row) {
			var col = $("#datatable").jqGrid('getRowData', row);
			var num = col.boxtypecode;
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
									type : "POST",
									url : "${pageContext.request.contextPath}/boxTypeController/delectBoxType.do?id="
											+ num,
									success : function(msg) {
										showMsg(eval("(" + msg + ")").msg);
										$("#datatable").trigger('reloadGrid');
										setTimeout(function() {
										}, 2500);

									}
								});
					},
					cancelValue : $.i18n.prop('btn.cancel'),
					cancel : function() {
					}
				});
				dialogCode.width(150);
				dialogCode.showModal();
			}
		};
		//添加 修改 箱体类型 
		var updateMakeCard = function(row) {//弹出添加窗口 修改窗口   
			document.getElementById("boxTypeName").disabled = false;
			document.getElementById("kuan").disabled = false;
			document.getElementById("gao").disabled = false;
			document.getElementById("shen").disabled = false;
			document.getElementById("beizhu").disabled = false;
			//alert(title);
			if (row > 0) { //说明是修改  
				var col = $("#datatable").jqGrid('getRowData', row);
				$("#boxTypeCode").val(col.boxtypecode);
				$("#boxTypeName").val(col.boxtypename);
				$("#kuan").val(col.width);
				$("#gao").val(col.height);
				$("#shen").val(col.depth);
				$("#beizhu").val(col.memo);
			}
			dialogCode = dialog({
				title : $.i18n.prop('btn.update'),
				content : $('#showadd'),
				okValue : $.i18n.prop('btn.ok'),
				ok : function() {
					$
							.ajax({
								type : "POST",
								url : "${pageContext.request.contextPath}/boxTypeController/saveOrUpdateBoxType.do",
								data : {
									boxtypecode : $("#boxTypeCode").val(),
									boxtypename : $("#boxTypeName").val(),
									kuan : $("#kuan").val(),
									gao : $("#gao").val(),
									shen : $("#shen").val(),
									beizhu : $("#beizhu").val()
								},
								success : function(msg) {
									showMsg(eval("(" + msg + ")").msg);
									submitForm();
									$('#modifyCode').clearForm(true);
								},
							});
				},
				cancelValue : $.i18n.prop('btn.cancel'),
				cancel : function() {
					$('#modifyCode').clearForm(true);
				}
			});

			dialogCode.width(500);
			dialogCode.height(230);
			dialogCode.showModal();
		};

		//添加 修改 箱体类型 
		var dialogCode = null;
		function addMakeCard(title, okValue, row) {//弹出添加窗口 修改窗口    
			document.getElementById("boxTypeName").disabled = false;
			document.getElementById("kuan").disabled = false;
			document.getElementById("gao").disabled = false;
			document.getElementById("shen").disabled = false;
			document.getElementById("beizhu").disabled = false;
			dialogCode = dialog({
				title : $.i18n.prop('role.dlg.add'),
				content : $('#showadd'),
				okValue : $.i18n.prop('btn.ok'),
				ok : function() {
					if ($("#boxTypeName").val() == ''
							|| $("#boxTypeName").val() == null) {
						showMsg("<spring:message code='boxType.prompt2.information' />");
						return false;
					} else if ($("#kuan").val() == ''
							|| $("#kuan").val() == null
							|| $("#kuan").val() > 200) {
						showMsg("<spring:message code='boxType.prompt3.information' />");
						return false;
					} else if ($("#gao").val() == '' || $("#gao").val() == null
							|| $("#gao").val() > 200) {
						showMsg("<spring:message code='boxType.prompt4.information' />");
						return false;
					} else if ($("#shen").val() == ''
							|| $("#shen").val() == null
							|| $("#shen").val() > 200) {
						showMsg("<spring:message code='boxType.prompt5.information' />");
						return false;
					} else {
						$
								.ajax({
									type : "POST",
									url : "${pageContext.request.contextPath}/boxTypeController/saveOrUpdateBoxType.do",
									data : {
										boxtypename : $("#boxTypeName").val(),
										kuan : $("#kuan").val(),
										gao : $("#gao").val(),
										shen : $("#shen").val(),
										beizhu : $("#beizhu").val()
									},
									success : function(msg) {
										showMsg(eval("(" + msg + ")").msg);
										submitForm();
										$('#modifyCode').clearForm(true);
									},
								});
					}
				},
				cancelValue : $.i18n.prop('btn.cancel'),
				cancel : function() {
					$('#modifyCode').clearForm(true);
				}
			});

			dialogCode.width(500);
			dialogCode.height(230);
			dialogCode.showModal();
		};
		//查看窗口
		function selectMakeCard(title, row) {
			document.getElementById("boxTypeName").disabled = true;
			document.getElementById("kuan").disabled = true;
			document.getElementById("gao").disabled = true;
			document.getElementById("shen").disabled = true;
			document.getElementById("beizhu").disabled = true;
			/* alert(row); */
			var col = $("#datatable").jqGrid('getRowData', row);
			$("#boxTypeCode").val(col.boxtypecode);
			$("#boxTypeName").val(col.boxtypename);
			$("#kuan").val(col.width);
			$("#gao").val(col.height);
			$("#shen").val(col.depth);
			$("#beizhu").val(col.memo);
			dialogCode = dialog({
				title : title,
				content : $('#showadd'),
				cancelValue : $.i18n.prop('btn.cancel'),
				cancel : function() {
					$('#modifyCode').clearForm(true);
				}
			});
			dialogCode.width(500);
			dialogCode.height(230);
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