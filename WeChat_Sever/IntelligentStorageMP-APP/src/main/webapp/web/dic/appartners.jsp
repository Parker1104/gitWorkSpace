<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
	<title><spring:message code='datadic.title.text'/></title>
	<%@ include file="../../common/head.jsp"%>
	<%@ include file="../../common/theme.jsp"%>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/js/plug/zTree/css/demo.css" type="text/css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/common/listes.css" type="text/css">
</head>

<body style="overflow-x: hidden">

	<div class="content" align="center" style="display: none;">
		<form action="${pageContext.request.contextPath}/apppartnerController/selects.do" id="searchMemberCardForm" method="post">
			<div class="list">
				<ul>
					<li>
						<spring:message code="apppartner.page.userName" />：
						<input name="userName" id="userName" type="text" />
					</li>
				</ul>
			</div>
		</form>
	</div>
	
	<div class="title" align="center" style="display: none;">
		<form action="" id="modifyCode" method="post">
			<div class="list">
				<ul>
					<li style="width: 305px;">
						  <spring:message code="apppartner.page.userId" />：
						  <input type="text" id="userId" name="userId" maxlength="20" />
						   <span style="color: red">*</span>
				    </li>
					<li style="width: 305px;">
				     	  <spring:message code="apppartner.page.userName" />：
				     	  <input type="text" id="userNames" name="userNames" maxlength="64" />
				     	  <span style="color: red">*</span>
				     </li>
					 <li style="width: 310px;">
					       <spring:message code="apppartner.page.userKey" />：
					       <input type="text" id="userKey" name="userKey"  />
						   <span style="color: red">*</span></li>
					 <li><spring:message code="apppartner.page.alarmNoticeUrls" />：
						  <input  type="text" name="alarmNoticeUrls" id="alarmNoticeUrls"  >
		                  </textarea> 
					 </li>
				</ul>
			</div>
		</form>
	</div>
	
	
	<div class="tab">
		<table id="datatable"></table>
		<div id="pager2"></div>
	</div>
	
	
	
   <div id="showCommoditys" class="list" style="display: none">
		<div style="width: 98%;">
			<table id="detail"></table>
			<div id="pager3"></div>
		</div>
	</div>
	
	
	
	
	<script type="text/javascript">
	
	$(document).ready(
			function() {
				$(window).resize(
				   function() {  $("#datatable").setGridWidth( $(window).width() - 50);
			    });
				var tableWidth = $("#datatable").parent().innerWidth();
				
				$("#datatable").jqGrid( {
						url : '${pageContext.request.contextPath}/apppartnerController/selects.do',
						datatype : "json",
						mtype : "POST",
						width : document.body.offsetWidth - 60,
						height : window.screen.availHeight - 325,
						//autowidth:true,
						editable : false,
						shrinkToFit : true,
						colNames : [
								$.i18n.prop('apppartner.table.col.text.userId'),
								$.i18n.prop('apppartner.table.col.text.userName'),
								$.i18n.prop('apppartner.table.col.text.userKey'),
								$.i18n.prop('apppartner.table.col.text.alarmNoticeUrls')
								,$.i18n.prop('apppartner.text.operation') 
								],
						colModel : [ {
							name : "userId",
							index : "userId",
							align : 'center',
							width : 150,
							sortable : false
						}, {
							name : "userName",
							index : "userName",
							align : 'center',
							width : 150,
							sortable : false
						}, {
							name : "userKey",
							index : "userKey",
							align : 'center',
							width : 150,
							sortable : false
						},   {
							name : "alarmnoticeurls",
							index : "alarmnoticeurls",
							align : 'center',
							width : 150,
							sortable : false
						} ,{
							name : "",
							index : '',
							align : 'center',
							width : 200,
							sortable : false
							// formatter : function( el, options, rowData) {
							//	var d = '<input type="button" value="<spring:message code='apppartner.page.option.information' />" onclick="viewDetail(\''
							//			//+ rowData.masterid
							//			+25
							//			+ '\')" style="width:80px;float:right;margin-right:10px;"/>';
							//	return d;
							// }
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
						sortname : 'userId',
						sortorder : 'desc',
						toppager : true,
						viewrecords : true,
						loadComplete : function() {
							var myGrid = $("#datatable");
							$("#cb_" + myGrid[0].id).hide();
						},
						ondblClickRow : function(rowid) {
							RowData = jQuery(this).jqGrid( "getRowData", rowid);
							if (RowData.userId) {
								selectMakeCard( "<spring:message code='account.table.col13.text' />", rowid);
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
	

	
	var viewGrid = null;
	function viewDetail(masterid) {
		$.ajax({
			type : "POST",
			url : "${pageContext.request.contextPath}/feeScaleController/selectProgramFeescale",
			data : {
				masterid : masterid
			},
			success : function(msg) {
				showMsg(eval("(" + msg + ")").msg);
				if (msg != '[]') {
					viewGrid = showCommodity(
							'detail',
							'${pageContext.request.contextPath}/feeScaleController/selectProgram?masterid=' + masterid, 
						    800,
							function(data) { return data.rows; }
					).showCol('validperiod').showCol( 'memo');
					openWin('<spring:message code="feeScale.prompt19.information" />', 'showCommoditys', 825);
				} else {
					document.getElementById("masterID").value = masterid;
					document.getElementById("masterID").disabled = true;
					dialogCode = dialog({
						title : $.i18n.prop('btn.add'),
						content : $('#fee_1'),
						okValue : $.i18n.prop('btn.ok'),
						ok : function() {
							var masterID = document.getElementById("masterID").value;
							var boxTypeCode = document.getElementById("boxTypeCode").value;
							var feeValue = document.getElementById("feeValue").value;
							var discountRate = document.getElementById("discountRate").value;
							if (masterID == '' || masterID == null) {
								showMsg("<spring:message code='feeScale.PaymentDetail.prompt11.information'/>");
								return false;
							} else if (boxTypeCode == '' || boxTypeCode == null) {
								showMsg("<spring:message code='feeScale.PaymentDetail.prompt12.information'/>");
								return false;
							} else if (feeValue == '' || feeValue == null) {
								showMsg("<spring:message code='feeScale.PaymentDetail.prompt13.information'/>");
								return false;
							} else if (discountRate == '' || discountRate == null) {
								showMsg("<spring:message code='feeScale.PaymentDetail.prompt14.information'/>");
								return false;
							} else {
								$.ajax({
											type : "POST",
											url : "${pageContext.request.contextPath}/feeScaleController/saveOrUpdateProgramElement",
											data : {
												masterid : $("#masterID").val(),
												detailid : $("#detailID").val(),
												boxtypecode : $("#boxTypeCode").val(),
												feevalue : $("#feeValue").val(),
												discountrate : $("#discountRate").val(),
												memo : $("#memo").val()
											},
											success : function(data) {
												var dataObj = eval("(" + data + ")");
												if (dataObj.msg == "<spring:message code='feeScale.prompt20.information'/>") {
													showMsg("<spring:message code='feeScale.prompt20.information'/>");
												} else {
													showMsg("<spring:message code='feeScale.prompt21.information'/>");
												}
											},
										});
							}
						},
						cancelValue : $.i18n.prop('btn.cancel'),
						cancel : function() {
							/* $('#fee_1').clearForm(true); */
						}
					});
					dialogCode.width(400);
					dialogCode.height(200);
					dialogCode.showModal();
				}
			},
		});
	};
	
	function openWin(title, id, width) {
		var dia = dialog({
			title : title,
			content : $('#' + id),
			cancelValue : $.i18n.prop('btn.cancel'),
			cancel : function() {
				// return setFormStores();
				location.href = location.href;
			}
		});
		dia.width(width);
		dia.height(450);
		dia.showModal();
	}
	
	
	function showCommodity(id, url, tableWidth, fun) {//初始化商品列表,当url为空表示把选择的商品赋值到该表格
		return $("#" + id).jqGrid( {
			url : url,
			datatype : "json",
			mtype : "POST",
			width : document.body.offsetWidth - 550,
			height : window.screen.availHeight - 550,
			//autowidth:true,
			editable : false,
			shrinkToFit : true,
			colNames : [ $.i18n.prop('feeScale.PaymentDetail.table.col.text.masterid'), $.i18n.prop('feeScale.PaymentDetail.table.col.text.detailid'),
			             $.i18n.prop('feeScale.PaymentDetail.table.col.text.boxtypecode'), $.i18n.prop('feeScale.PaymentDetail.table.col.text.boxtypename'),
                         $.i18n.prop('feeScale.PaymentDetail.table.col.text.feevalue'),$.i18n.prop('feeScale.PaymentDetail.table.col.text.discountrate'),
                         $.i18n.prop('feeScale.PaymentDetail.table.col.text.memo'), $.i18n.prop('feeScale.PaymentDetail.table.col.text.operation')
                      ],
			colModel : [
					{
						name : "masterid",
						index : 'masterid',
						align : 'center',
						width : 100,
						sortable : false,
						hidden : true
					},
					{
						name : "detailid",
						index : 'detailid',
						align : 'center',
						width : 100,
						sortable : false,
						hidden : true
					},
					{
						name : "boxtypecode",
						index : 'boxtypecode',
						align : 'center',
						width : 100,
						sortable : false,
						hidden : true
					},
					{
						name : "boxtypename",
						index : "boxtypename",
						align : 'center',
						width : 100,
						sortable : false,
						formatter : function(el, options,
								rowData) {
							return rowData.boxSizeEntity.boxtypename;
						}
					},
					{
						name : "feevalue",
						index : 'feevalue',
						align : 'center',
						width : 100,
						sortable : false
					},
					{
						name : "discountrate",
						index : 'discountrate',
						align : 'center',
						width : 100,
						sortable : false
					},
					{
						name : "memo",
						index : 'memo',
						align : 'center',
						width : 100,
						sortable : false
					},
					{
						name : "",
						index : '',
						align : 'center',
						width : 150,
						sortable : false,
						formatter : function(el, options, rowData) {
							var a = '<input type="button" value="<spring:message code='feeScale.PaymentDetail.prompt16.information' />" onclick="addMakeCard(\''
									+ rowData.masterid
									+ '\')" style="width:25%;float:right;margin-right:5%;"/>';
							var u = '<input type="button" value="<spring:message code='feeScale.PaymentDetail.prompt17.information' />" onclick="updateMakeCard(\''
									+ rowData.masterid
									+ '\',\''
									+ rowData.detailid
									+ '\')" style="width:25%;float:right;margin-right:5%;"/>';
							var d = '<input type="button" value="<spring:message code='feeScale.PaymentDetail.prompt18.information' />" onclick="delectProgram(\''
									+ rowData.masterid
									+ '\',\''
									+ rowData.detailid
									+ '\')" style="width:25%;float:right;margin-right:5%;"/>';
							return a + u + d;
						}
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
			pager : '#pager3',
			rowList : [ 10, 15, 20 ],
			sortname : 'masterid',
			sortorder : 'desc',
			/* toppager : true, */
			viewrecords : true,
			loadComplete : function() {
				var myGrid = $("#" + id);
				$("#cb_" + myGrid[0].id).hide();
			},
			ondblClickRow : function(rowid) {
				RowData = jQuery(this).jqGrid("getRowData", rowid);
				if (RowData.masterid) {
					selectMakeCard( "<spring:message code='account.table.col13.text' />", rowid);
				}
			},

		});
	}
	
	
	
	
	
	var dialogCode = null;
	//查询数据 
	var searchDict = function(row) {
		var dialogCode = dialog({
			title : $.i18n.prop('btn.search'),
			content : $('#searchMemberCardForm'),
			okValue : $.i18n.prop('btn.ok'),
			ok : function() {
				var userName = document.getElementById("userName").value;
				$.ajax({
						type : "POST",
						url : "${pageContext.request.contextPath}/apppartnerController/selects.do",
						data : {
							userName : userName
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
	

	 function submitForm() {
			$("#datatable").jqGrid('setGridParam', {
				postData : formToJson("searchMemberCardForm"),
				page : 1
			}).trigger("reloadGrid");
	 };
	
	//添加    
	var addMakeCard = function(row) { //弹出添加窗口 修改窗口  
		document.getElementById("userId").disabled = false;
		document.getElementById("userNames").disabled = false;
		document.getElementById("userKey").disabled = false;
		document.getElementById("alarmNoticeUrls").disabled = false;

		dialogCode = dialog({
			title : $.i18n.prop('btn.add'),
			content : $('#modifyCode'),
			okValue : $.i18n.prop('btn.ok'),
			ok : function() {
				
				//alert("   userId="+$("#userId").val()+"   userNames="+$("#userNames").val()+"  userKey="+$("#userKey").val());
				
				if ($("#userId").val() == '') {
					showMsg("<spring:message code='apppartner.page.userId.information' />");
					return false;
				} else if ($("#userNames").val() == '') {
					showMsg("<spring:message code='apppartner.page.userName.information' />");
					return false;
				} else if ($("#userKey").val() == '') {
					showMsg("<spring:message code='apppartner.page.userKey.information' />");
					return false;
				} else {
					$.ajax({
						type : "POST",
						url : "${pageContext.request.contextPath}/apppartnerController/saveOrUpdates.do",
						data : {
							userId : $("#userId").val(),
							userName : $("#userNames").val(),
							userKey : $("#userKey").val(),
							alarmNoticeUrls : $("#alarmNoticeUrls").val()
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
		document.getElementById("userId").disabled = true;
		//document.getElementById("userName").disabled = false;
		//document.getElementById("userKey").disabled = true;
		//document.getElementById("alarmNoticeUrls").disabled = false;

		if (row > 0) {
			var col = $("#datatable").jqGrid('getRowData', row);
			$("#userId").val(col.userId);
			$("#userNames").val(col.userName);
			$("#userKey").val(col.userKey);
			$("#alarmNoticeUrls").val(col.alarmnoticeurls);
		}
		
		dialogCode = dialog({
			title : $.i18n.prop('btn.update'),
			content : $('#modifyCode'),
			okValue : $.i18n.prop('btn.ok'),
			ok : function() {
				$.ajax({
					type : "POST",
					url : "${pageContext.request.contextPath}/apppartnerController/saveOrUpdates.do",
					data : {
						userId : $("#userId").val(),
						userName : $("#userNames").val(),
						userKey : $("#userKey").val(),
						alarmNoticeUrls : $("#alarmNoticeUrls").val() 
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

	
	
	//删除数据 
	var deleteDict = function(row) {
			var col = $("#datatable").jqGrid('getRowData', row);
			var ids = jQuery("#datatable").jqGrid('getGridParam', 'selarrrow');
			//alert("============ids=====ids="+ids);
			if (ids.length > 1) {
				showMsg("无法多行删除");
			} else {
				var dialogCode = dialog({
					title : $.i18n.prop('tip.title'),
					content : $.i18n.prop('tip.confirm.delete'),
					okValue : $.i18n.prop('btn.ok'),
					ok : function() {
						this.title($.i18n.prop('tip.loading'));
						$.ajax({
							type : "post",
							url : "${pageContext.request.contextPath}/apppartnerController/delectes.do",
							data : {
								userId : col.userId
							},
							dataType : 'json', 
							success : function(data) {
                                //if(!data.success){
                                   // alert("--------------"+data.msg);	
                                //}
								$("#datatable").trigger('reloadGrid');
								showMsg(data.msg);
							},
							error : function(XMLHttpRequest, textStatus, errorThrown) {
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
	
	//查看窗口
	function selectMakeCard(title, row) {
			document.getElementById("userId").disabled = true;
			document.getElementById("userName").disabled = true;
			document.getElementById("userKey").disabled = true;
			document.getElementById("alarmNoticeUrls").disabled = true;
 
			/* alert(row); */
			var col = $("#datatable").jqGrid('getRowData', row);
			$("#userId").val(col.userId);
			$("#userNames").val(col.userName);
			$("#userKey").val(col.userKey);
			$("#alarmNoticeUrls").val(col.alarmnoticeurls);
 
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
  ///===========================================================
		
	
		/* 查询 当摁下 enter键后执行 button 点击事件 */
		document.onkeydown = function(event) {
			var e = event || window.event || arguments.callee.caller.arguments[0];
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
			$.ajax({
					type : "POST",
					datatype : "json",
					data : {
						dicttypecode : dicttypecode,
						dictcode : dictcode
					},
					url : "${pageContext.request.contextPath}/apppartnerController/selectDatadicCode.do",
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


	

	</script>
</body>
</html>









