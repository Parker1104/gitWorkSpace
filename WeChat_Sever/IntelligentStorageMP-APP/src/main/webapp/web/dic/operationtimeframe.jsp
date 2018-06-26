<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path;
	request.setAttribute("basePath", basePath);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title><spring:message code="operationtimeframe.title.text" /></title>
<%@ include file="../../common/head.jsp"%>
<%@ include file="../../common/theme.jsp"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/js/plug/zTree/css/demo.css" type="text/css" />

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
}
</style>
</head>
<body style="overflow-x: hidden">
	<div class="content" align="center" style="display: none;">
		<form
			action="${pageContext.request.contextPath}/operationTimeFrameController/selectRunTime.do"
			id="searchMemberCardForm" method="post">
			<div class="list">
				<ul>
					<li><spring:message code="operationtimeframe.runtimename" />：<input name="runtimename" id="runtimename"
						type="text" maxlength="40" /></li>
				</ul>
				<!-- <ul style="float:right;margin-right:-175px;">   
       <li style="margin-left: 50px;"><input type="button" id="selectRuntime" onclick="submitForm()" value="查询"  style="width: 88px; font-size: 14px; "/></li>
      </ul> -->
			</div>
		</form>
	</div>
	<div class="title" style="display: none;">
		<form
			action="${pageContext.request.contextPath}/operationTimeFrameController/saveOrUpdateRunTime.do"
			method="post">
			<table id="week" class="list"
				style="border-collapse: separate; border-spacing: 0px 10px;">
				<tr>
					<td width="255" colspan="3"><div align="center">
							<spring:message code="operationtimeframe.runtimename" />： <input type="text" id="runTimeName" name="runTimeName"
								maxlength="40" onblur="runTimeNameOnblue()" /><span style="color: red">*</span>
						</div></td>
				</tr>
				<tr style="display: none;"> 
					<td colspan="3"><div align="center">
							<input type="hidden" id="runtimeCode" name="runtimeCode" value="" />
						</div></td>
				</tr>
				<tr>
					<td><div align="center"><spring:message code="operationtimeframe.On.Monday" /></div></td>
					<td><div align="center" style="width: 255px;">
						<spring:message code="operationtimeframe.The.start.time" />： <input type="text" id="monStartRunTime"
								name="monStartRunTime" value="08:00" class="Wdate"
								class="validate[required]"
								onfocus="WdatePicker({dateFmt:'HH:mm',maxDate:'#F{$dp.$D(\'monEndRunTime\')}'})" /><span
								style="color: red">*</span>
						</div></td>
					<td><div align="center" style="width: 255px;">
							<spring:message code="operationtimeframe.The.end.of.time" />： <input type="text" id="monEndRunTime" name="monEndRunTime"
								value="22:00" class="Wdate" class="validate[required]"
								onfocus="WdatePicker({dateFmt:'HH:mm',minDate:'#F{$dp.$D(\'monStartRunTime\')}' })" /><span
								style="color: red">*</span>
						</div></td>
				</tr>
				<tr>
					<td><div align="center"><spring:message code="operationtimeframe.On.Tuesday" /></div></td>
					<td><div align="center">
							<spring:message code="operationtimeframe.The.start.time" />： <input type="text" id="tueStartRunTime"
								name="tueStartRunTime" value="08:00" class="Wdate"
								class="validate[required]"
								onfocus="WdatePicker({dateFmt:'HH:mm',maxDate:'#F{$dp.$D(\'tueEndRunTime\')}' })" /><span
								style="color: red">*</span>
						</div></td>
					<td><div align="center">
							<spring:message code="operationtimeframe.The.end.of.time" />： <input type="text" id="tueEndRunTime" name="tueEndRunTime"
								value="22:00" class="Wdate" class="validate[required]"
								onfocus="WdatePicker({dateFmt:'HH:mm',minDate:'#F{$dp.$D(\'tueStartRunTime\')}' })" /><span
								style="color: red">*</span>
						</div></td>
				</tr>
				<tr>
					<td><div align="center"><spring:message code="operationtimeframe.On.Wednesday" /></div></td>
					<td><div align="center">
							<spring:message code="operationtimeframe.The.start.time" />： <input type="text" id="wedStartRunTime"
								name="wedStartRunTime" value="08:00" class="Wdate"
								class="validate[required]"
								onfocus="WdatePicker({dateFmt:'HH:mm',maxDate:'#F{$dp.$D(\'wedEndRunTime\')}'})" /><span
								style="color: red">*</span>
						</div></td>
					<td><div align="center">
							<spring:message code="operationtimeframe.The.end.of.time" />： <input type="text" id="wedEndRunTime" name="wedEndRunTime"
								value="22:00" class="Wdate" class="validate[required]"
								onfocus="WdatePicker({dateFmt:'HH:mm',minDate:'#F{$dp.$D(\'wedStartRunTime\')}' })" /><span
								style="color: red">*</span>
						</div></td>
				</tr>
				<tr>
					<td><div align="center"><spring:message code="operationtimeframe.On.Thursday" /></div></td>
					<td><div align="center">
							<spring:message code="operationtimeframe.The.start.time" />： <input type="text" id="thursStartRunTime"
								name="thursStartRunTime" value="08:00" class="Wdate"
								class="validate[required]"
								onfocus="WdatePicker({dateFmt:'HH:mm',maxDate:'#F{$dp.$D(\'thursEndRunTime\')}'})" /><span
								style="color: red">*</span>
						</div></td>
					<td><div align="center">
							<spring:message code="operationtimeframe.The.end.of.time" />： <input type="text" id="thursEndRunTime"
								name="thursEndRunTime" value="22:00" class="Wdate"
								class="validate[required]"
								onfocus="WdatePicker({dateFmt:'HH:mm',minDate:'#F{$dp.$D(\'thursStartRunTime\')}' })" /><span
								style="color: red">*</span>
						</div></td>
				</tr>
				<tr>
					<td><div align="center"><spring:message code="operationtimeframe.On.Friday" /></div></td>
					<td><div align="center">
							<spring:message code="operationtimeframe.The.start.time" />： <input type="text" id="friStartRunTime"
								name="friStartRunTime" value="08:00" class="Wdate"
								class="validate[required]"
								onfocus="WdatePicker({dateFmt:'HH:mm',maxDate:'#F{$dp.$D(\'friEndRunTime\')}'})" /><span
								style="color: red">*</span>
						</div></td>
					<td><div align="center">
							<spring:message code="operationtimeframe.The.end.of.time" />： <input type="text" id="friEndRunTime" name="friEndRunTime"
								value="22:00" class="Wdate" class="validate[required]"
								onfocus="WdatePicker({dateFmt:'HH:mm',minDate:'#F{$dp.$D(\'friStartRunTime\')}' })" /><span
								style="color: red">*</span>
						</div></td>
				</tr>
				<tr>
					<td><div align="center"><spring:message code="operationtimeframe.On.Saturday" /></div></td>
					<td><div align="center">
							<spring:message code="operationtimeframe.The.start.time" />： <input type="text" id="satStartRunTime"
								name="satStartRunTime" value="08:00" class="Wdate"
								class="validate[required]"
								onfocus="WdatePicker({dateFmt:'HH:mm',maxDate:'#F{$dp.$D(\'satEndRunTime\')}'})" /><span
								style="color: red">*</span>
						</div></td>
					<td><div align="center">
							<spring:message code="operationtimeframe.The.end.of.time" />： <input type="text" id="satEndRunTime" name="satEndRunTime"
								value="22:00" class="Wdate" class="validate[required]"
								onfocus="WdatePicker({dateFmt:'HH:mm',minDate:'#F{$dp.$D(\'satStartRunTime\')}'})" /><span
								style="color: red">*</span>
						</div></td>
				</tr>
				<tr>
					<td><div align="center"><spring:message code="operationtimeframe.On.Sunday" /></div></td>
					<td><div align="center">
							<spring:message code="operationtimeframe.The.start.time" />： <input type="text" id="sunStartRunTime"
								name="sunStartRunTime" value="08:00" class="Wdate"
								class="validate[required]"
								onfocus="WdatePicker({dateFmt:'HH:mm',maxDate:'#F{$dp.$D(\'sunEndRunTime\')}'})" /><span
								style="color: red">*</span>
						</div></td>
					<td><div align="center">
							<spring:message code="operationtimeframe.The.end.of.time" />： <input type="text" id="sunEndRunTime" name="sunEndRunTime"
								value="22:00" class="Wdate" class="validate[required]"
								onfocus="WdatePicker({dateFmt:'HH:mm',minDate:'#F{$dp.$D(\'sunStartRunTime\')}'})" /><span
								style="color: red">*</span>
						</div></td>
				</tr>
			</table>
		</form>
	</div>

	<div class="tab">
		<table id="datatable"></table>
		<div id="pager2"></div>
	</div>
	<script type="text/javascript">
/* 查询 当摁下 enter键后执行 button 点击事件 */ 
document.onkeydown=function(event){
            var e = event || window.event || arguments.callee.caller.arguments[0];           
	             if(e && e.keyCode==13){   // enter 键            	 
	            	 if (document.activeElement.id == "runtimename"){
						 $(".ui-dialog-autofocus").trigger("click");
	            		 return false;
	            	 }        
	        }
}; 
//判断输入的值是否存在 
function runTimeNameOnblue() {
	var runTimename = document.getElementById("runTimeName").value; 
	$
			.ajax({
				type : "POST",
				datatype : "json",
				data : {
					runtimename : runTimename
				},
				url : "${pageContext.request.contextPath}/operationTimeFrameController/selectRunTimeName.do", 
				success : function(msg) {
					/* showMsg(eval("(" + msg + ")").msg); */
					if (msg == "[]") {
						return false;
					} else { 
						var dataObj = eval("(" + msg + ")"); 
						if (runTimename == dataObj[0].runtimename) {
							showMsg("<spring:message code='operationtimeframe.prompt1.information' />"); 
							document.getElementById("runTimeName").value = "";
							return false;
						}	
					}
				},
			});
};

function submitForm(){
    $("#datatable").jqGrid('setGridParam',{
        postData:formToJson("searchMemberCardForm"),
        page:1
    }).trigger("reloadGrid");
}
function formToJson(id){//把form表单的所有数据变成json对象
    var formArray=$('#'+id).serializeArray();//数组里面首先放入充值记录表的内容
    var formJson=new Object();
    $.each(formArray,function(i,n){//把formarray转换为json
        if((n.name!=undefined&&n.name!="")){
            formJson[n.name] = n.value;
        }
    });
    return formJson;
}
    $(document).ready(function(){
		$(window).resize(function() {
			$("#datatable").setGridWidth($(window).width()-50);
		});
        var tableWidth = $("#datatable").parent().innerWidth();
        $("#datatable").jqGrid({
            url:'${pageContext.request.contextPath}/operationTimeFrameController/selectRunTime.do',
            datatype: "json",
            mtype: "POST",
            width: document.body.offsetWidth-60,
            height: window.screen.availHeight-325,
            //autowidth:true,
            editable: false,
            shrinkToFit:true,
            colNames:[$.i18n.prop('operationtimeframe.table.col.text.runtimecode'),$.i18n.prop('operationtimeframe.table.col.text.runtimename'),
                         $.i18n.prop('operationtimeframe.table.col.text.On.Monday'),
            	$.i18n.prop('operationtimeframe.table.col.text.On.Tuesday'),
            	$.i18n.prop('operationtimeframe.table.col.text.On.Wednesday'),
            	$.i18n.prop('operationtimeframe.table.col.text.On.Thursday'),
            	$.i18n.prop('operationtimeframe.table.col.text.On.Friday'),
            	$.i18n.prop('operationtimeframe.table.col.text.On.Saturday'),
            	$.i18n.prop('operationtimeframe.table.col8.text.On.Sunday')],
            colModel:[
            	{name:"runtimecode",index:"runtimecode",align:'center',sortable:false,hidden:true}, 
                {name:"runtimename",index:"runtimename",align:'center',sortable:false},
                {name:"Monday",index:"Monday",align:'center',width:100,sortable:false,formatter:function(el,options,rowData){
                    return rowData.monstartruntime+'-'+rowData.monendruntime;
                 }},
                {name:"Tuesday",index:"Tuesday",align:'center',width:100,sortable:false,formatter:function(el,options,rowData){
                    return rowData.tuestartruntime+'-'+rowData.tueendruntime;
                }},
                {name:"Wednesday",index:'Wednesday',align:'center',width:100,sortable:false,formatter:function(el,options,rowData){
                	return rowData.wedstartruntime+'-'+rowData.wedendruntime;
                }},
                {name:"Thursday",index:"Thursday",align:'center',width:100,sortable:false,formatter:function(el,options,rowData){
                	return rowData.thursstartruntime+'-'+rowData.thursendruntime;
                }},
                {name:"Friday",index:"Friday",align:'center',width:100,sortable:false,formatter:function(el,options,rowData){
                	return rowData.fristartruntime+'-'+rowData.friendruntime;
                }},
                {name:"Saturday",index:"Saturday",align:'center',width:100,sortable:false,formatter:function(el,options,rowData){
                	return rowData.satstartruntime+'-'+rowData.satendruntime;
                }},
                {name:"Sunday",index:"Sunday",align:'center',width:100,sortable:false,formatter:function(el,options,rowData){
                	return rowData.sunstartruntime+'-'+rowData.sunendruntime;
                }},
                /* {name:"",index:'',align:'center',width:80,formatter:function(el,options,rowData){
                    var d= '<input type="button" value="删除" onclick="deleteRunTime(\''+rowData.runtimecode+'\')" style="width:30px;float:right;margin-right:10px;"/>';
                    var u= '<input type="button" value="修改" onclick="addMakeCard(\'修改类型\',\'修改\','+ options.rowId +')" style="width:30px;float:right;margin-right:10px;"/>';
                    return u+d;
                }} */
            ],
            sortable: false,
            rowNum:10,
            rownumbers:false,
            multiselect: true,
            multiboxonly:true,	
            jsonReader: {
                repeatitems : false,
                id:"0"
            },
            pager: '#pager2',
            rowList:[10,15,20],
            sortname: 'runtimecode',
            sortorder: 'desc',
            toppager : true,
            viewrecords: true,
			loadComplete:function(){
				var myGrid = $("#datatable");
				$("#cb_"+myGrid[0].id).hide();
			},
            ondblClickRow:function(rowid){
	  	    	RowData= jQuery(this).jqGrid("getRowData", rowid);
	  	    	if(RowData.runtimecode){
	  	    		selectMakeCard("<spring:message code='account.table.col13.text' />",rowid);            	
               }
            },
        });
        $("#datatable").jqGrid("navGrid", "#pager2", {
			cloneToTop : true,
			/* add:false, */
			/* search : false, */
			/* edit:false, */
			addfunc : addMakeCard, // (1) 点击添加按钮
			addtext : $.i18n.prop("btn.add"),
			delfunc : deleteRunTime, // (2) 点击删除按钮 
			deltext : $.i18n.prop("btn.delete"),
			editfunc : updateMakeCard, // (3) 点击修改按钮
			edittext : $.i18n.prop("btn.update"),
			searchfunc : searchRunTime, // (4) 点击查询按钮   
			searchtext : $.i18n.prop("btn.search"), 
			alerttext : $.i18n.prop("param.minrow") // (5) 当未选中任何行而点击编辑、删除、查看按钮时，弹出的提示信息  
		});
    });  
    //查询数据 
    var searchRunTime = function(row) {  
    	var dialogCode = dialog({
            title: $.i18n.prop('btn.search'), 
            content:  $('#searchMemberCardForm'),
            okValue: $.i18n.prop('btn.ok'), 
            ok: function () {
                $.ajax({
                    type: "POST",
                    url : "${pageContext.request.contextPath}/operationTimeFrameController/selectRunTime.do",
                    success: function(msg){
                    	showMsg($.i18n.prop('tip.success'));
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
        dialogCode.width(350);
        dialogCode.showModal();
    }; 
    
	//删除数据 				
    var deleteRunTime = function(row) {  
    	var col = $("#datatable").jqGrid('getRowData', row);
		var ids = jQuery("#datatable").jqGrid('getGridParam', 'selarrrow');
		if (ids.length > 1) {
			showMsg("无法多行删除");
		} else {
	    	$.ajax({
				type : "POST",
				url : "${pageContext.request.contextPath}/operationTimeFrameController/selectRunTimeCold.do", 
				data : {runtimecode:col.runtimecode},
				success : function(msg) {
					/* showMsg(eval("(" + msg + ")").msg); */
					if(msg == '[]'){
						var dialogCode = dialog({
				            title: $.i18n.prop('tip.title'),
				            content: $.i18n.prop('tip.confirm.delete'),
				            okValue: $.i18n.prop('btn.ok'), 
				            ok: function () {
				                this.title($.i18n.prop('tip.loading'));
				                $.ajax({
				                    type: "POST",
				                    url : "${pageContext.request.contextPath}/operationTimeFrameController/delectRunTime.do?id="+col.runtimecode,
				                    success: function(msg){
				                    	showMsg(eval("("+msg+")").msg);
				                        $("#datatable").trigger('reloadGrid');
				                        setTimeout(function () {
				                        }, 2500);
	
				                    }
				                });
				            },
				            cancelValue: $.i18n.prop('btn.cancel'),
				            cancel: function () {}
				        });
				        dialogCode.width(150);
				        dialogCode.showModal();
					  }else {
						showMsg("<spring:message code='operationtimeframe.prompt17.information' />");
						return false;
					}					
				}
			});
	    }
    }; 
    //添加  修改 
    var dialogCode=null;
    var addMakeCard =  function addMakeCard(row){ //弹出修改和添加原因的窗口
		document.getElementById("runtimeCode").disabled =false;
		document.getElementById("runTimeName").disabled =false;
		document.getElementById("monStartRunTime").disabled =false;			
		document.getElementById("monEndRunTime").disabled =false;
		document.getElementById("tueStartRunTime").disabled =false;
		document.getElementById("tueEndRunTime").disabled =false;
		document.getElementById("wedStartRunTime").disabled =false;
		document.getElementById("wedEndRunTime").disabled =false;			
		document.getElementById("thursStartRunTime").disabled =false;
		document.getElementById("thursEndRunTime").disabled =false;	
		document.getElementById("friStartRunTime").disabled =false;
		document.getElementById("friEndRunTime").disabled =false;
		document.getElementById("satStartRunTime").disabled =false;			
		document.getElementById("satEndRunTime").disabled =false;
		document.getElementById("sunStartRunTime").disabled =false;	
		document.getElementById("sunEndRunTime").disabled =false;
        dialogCode = dialog({
            title: $.i18n.prop('btn.add'), 
            content: $('#week'),
            okValue: $.i18n.prop('btn.ok'), 
            ok: function () {
    			var runTimeName = document.getElementById("runTimeName").value;
    			var monStartRunTime = document.getElementById("monStartRunTime").value;
    			var monEndRunTime = document.getElementById("monEndRunTime").value;	
    			var tueStartRunTime = document.getElementById("tueStartRunTime").value;		
    			var tueEndRunTime = document.getElementById("tueEndRunTime").value;
    			var wedStartRunTime = document.getElementById("wedStartRunTime").value;
    			var wedEndRunTime = document.getElementById("wedEndRunTime").value;
    			var thursStartRunTime = document.getElementById("thursStartRunTime").value;
    			var thursEndRunTime = document.getElementById("thursEndRunTime").value;
    			var friStartRunTime = document.getElementById("friStartRunTime").value;
    			var friEndRunTime = document.getElementById("friEndRunTime").value;
    			var satStartRunTime = document.getElementById("satStartRunTime").value;
    			var satEndRunTime = document.getElementById("satEndRunTime").value;
       			var sunStartRunTime  = document.getElementById("sunStartRunTime").value;
    			var sunEndRunTime = document.getElementById("sunEndRunTime").value;
            	if (runTimeName == '' || runTimeName==null) {
                    showMsg("<spring:message code='operationtimeframe.prompt2.information' />"); 
    		        return false;
				}else if (monStartRunTime == '' || monStartRunTime==null) {
                    showMsg("<spring:message code='operationtimeframe.prompt3.information' />");
    		        return false;
				}else if (monEndRunTime == '' || monEndRunTime==null) {
                    showMsg("<spring:message code='operationtimeframe.prompt4.information' />"); 
    		        return false;
				}else if (tueStartRunTime == '' || tueStartRunTime==null) {
                    showMsg("<spring:message code='operationtimeframe.prompt5.information' />");
    		        return false;
				}else if (tueEndRunTime == '' || tueEndRunTime==null) {
                    showMsg("<spring:message code='operationtimeframe.prompt6.information' />");
    		        return false;
				}else if (wedStartRunTime == '' || wedStartRunTime==null) {
                    showMsg("<spring:message code='operationtimeframe.prompt7.information' />");
    		        return false;
				}else if (wedEndRunTime == '' || wedEndRunTime==null) {
                    showMsg("<spring:message code='operationtimeframe.prompt8.information' />"); 
    		        return false;
				}else if (thursStartRunTime == '' || thursStartRunTime==null) {
                    showMsg("<spring:message code='operationtimeframe.prompt9.information' />");
    		        return false;
				}else if (thursEndRunTime == '' || thursEndRunTime==null) {
                    showMsg("<spring:message code='operationtimeframe.prompt10.information' />");
    		        return false;
				}else if (friStartRunTime == '' || friStartRunTime==null) {
                    showMsg("<spring:message code='operationtimeframe.prompt11.information' />");
    		        return false;
				}else if (friEndRunTime == '' || friEndRunTime==null) {
                    showMsg("<spring:message code='operationtimeframe.prompt12.information' />");
    		        return false;
				}else if (satStartRunTime == '' || satStartRunTime==null) {
                    showMsg("<spring:message code='operationtimeframe.prompt13.information' />");
    		        return false;
				}else if (satEndRunTime == '' || satEndRunTime==null) {
                    showMsg("<spring:message code='operationtimeframe.prompt14.information' />");
    		        return false;
				}else if (sunStartRunTime == '' || sunStartRunTime==null) {
                    showMsg("<spring:message code='operationtimeframe.prompt15.information' />");
    		        return false;
				}else if (sunEndRunTime == '' || sunEndRunTime==null) {
                    showMsg("<spring:message code='operationtimeframe.prompt16.information' />");
    		        return false;
				}else {
	            	$.ajax({
						type : "POST",
						url : "${pageContext.request.contextPath}/operationTimeFrameController/saveOrUpdateRunTime.do",  
						data : {	
							runtimeCode:$('#runtimeCode').val(),
				            runTimeName :$("#runTimeName").val(),       
				            monStartRunTime:$("#monStartRunTime").val(),   
				            monEndRunTime:$("#monEndRunTime").val(),     
				            tueStartRunTime:$("#tueStartRunTime").val(),     
				            tueEndRunTime:$("#tueEndRunTime").val(),    
				            wedStartRunTime :$("#wedStartRunTime").val(), 
				            wedEndRunTime:$("#wedEndRunTime").val(),        
				            thursStartRunTime:$("#thursStartRunTime").val(),   
				            thursEndRunTime:$("#thursEndRunTime").val(),      
				            friStartRunTime :$("#friStartRunTime").val(),    
				            friEndRunTime:$("#friEndRunTime").val(),        
				            satStartRunTime:$("#satStartRunTime").val(),     
				            satEndRunTime:$("#satEndRunTime").val(),       
				            sunStartRunTime:$("#sunStartRunTime").val(),     
				            sunEndRunTime:$("#sunEndRunTime").val()
						},
						success : function(msg) {
							showMsg(eval("("+msg+")").msg);
							submitForm();
			        		document.getElementById("runtimeCode").value = "";
			        		document.getElementById("runTimeName").value = "";
			        		document.getElementById("monStartRunTime").value = "08:00";		
			        		document.getElementById("monEndRunTime").value = "22:00";
			        		document.getElementById("tueStartRunTime").value = "08:00";
			        		document.getElementById("tueEndRunTime").value = "22:00";
			        		document.getElementById("wedStartRunTime").value = "08:00";
			        		document.getElementById("wedEndRunTime").value = "22:00";		
			        		document.getElementById("thursStartRunTime").value = "08:00";
			        		document.getElementById("thursEndRunTime").value = "22:00";	
			        		document.getElementById("friStartRunTime").value = "08:00";
			        		document.getElementById("friEndRunTime").value = "22:00";
			        		document.getElementById("satStartRunTime").value = "08:00";		
			        		document.getElementById("satEndRunTime").value = "22:00";
			        		document.getElementById("sunStartRunTime").value = "08:00";
			        		document.getElementById("sunEndRunTime").value = "22:00"; 
						},
					}); 
				}
            },
            cancelValue: $.i18n.prop('btn.cancel'),
            cancel: function () {
        		document.getElementById("runtimeCode").value = "";
        		document.getElementById("runTimeName").value = "";
        		document.getElementById("monStartRunTime").value = "08:00";		
        		document.getElementById("monEndRunTime").value = "22:00";
        		document.getElementById("tueStartRunTime").value = "08:00";
        		document.getElementById("tueEndRunTime").value = "22:00";
        		document.getElementById("wedStartRunTime").value = "08:00";
        		document.getElementById("wedEndRunTime").value = "22:00";		
        		document.getElementById("thursStartRunTime").value = "08:00";
        		document.getElementById("thursEndRunTime").value = "22:00";	
        		document.getElementById("friStartRunTime").value = "08:00";
        		document.getElementById("friEndRunTime").value = "22:00";
        		document.getElementById("satStartRunTime").value = "08:00";		
        		document.getElementById("satEndRunTime").value = "22:00";
        		document.getElementById("sunStartRunTime").value = "08:00";
        		document.getElementById("sunEndRunTime").value = "22:00";        
            }
        });
        dialogCode.width(600);
        dialogCode.showModal();
    };
    var updateMakeCard =  function addMakeCard(row){  //弹出修改和添加原因的窗口
		document.getElementById("runtimeCode").disabled =true;
		document.getElementById("runTimeName").disabled =false;
		document.getElementById("monStartRunTime").disabled =false;			
		document.getElementById("monEndRunTime").disabled =false;
		document.getElementById("tueStartRunTime").disabled =false;
		document.getElementById("tueEndRunTime").disabled =false;
		document.getElementById("wedStartRunTime").disabled =false;
		document.getElementById("wedEndRunTime").disabled =false;			
		document.getElementById("thursStartRunTime").disabled =false;
		document.getElementById("thursEndRunTime").disabled =false;	
		document.getElementById("friStartRunTime").disabled =false;
		document.getElementById("friEndRunTime").disabled =false;
		document.getElementById("satStartRunTime").disabled =false;			
		document.getElementById("satEndRunTime").disabled =false;
		document.getElementById("sunStartRunTime").disabled =false;	
		document.getElementById("sunEndRunTime").disabled =false;
    	if(row>0){//说明是修改  	
    		var rowData=$("#datatable").jqGrid('getRowData',row);	
    		$('#runtimeCode').val(rowData.runtimecode);
	        $("#runTimeName").val(rowData.runtimename);
	        $("#monStartRunTime").val(rowData.Monday.substring(0,5));
	 	    $("#monEndRunTime").val(rowData.Monday.substring(6,11)); 
	        $("#tueStartRunTime").val(rowData.Tuesday.substring(0,5));    
	        $("#tueEndRunTime").val(rowData.Tuesday.substring(6,11));    
	        $("#wedStartRunTime").val(rowData.Wednesday.substring(0,5)); 
	        $("#wedEndRunTime").val(rowData.Wednesday.substring(6,11));        
	        $("#thursStartRunTime").val(rowData.Thursday.substring(0,5));   
	        $("#thursEndRunTime").val(rowData.Thursday.substring(6,11));      
	        $("#friStartRunTime").val(rowData.Friday.substring(0,5));     
	        $("#friEndRunTime").val(rowData.Friday.substring(6,11));        
	        $("#satStartRunTime").val(rowData.Saturday.substring(0,5));     
	        $("#satEndRunTime").val(rowData.Saturday.substring(6,11));       
	        $("#sunStartRunTime").val(rowData.Sunday.substring(0,5));      
	        $("#sunEndRunTime").val(rowData.Sunday.substring(6,11));			
		} 
        dialogCode = dialog({
            title: $.i18n.prop('btn.update'),
            content: $('#week'),
            okValue: $.i18n.prop('btn.ok'), 
            ok: function () {
            	$.ajax({
					type : "POST",
					url : "${pageContext.request.contextPath}/operationTimeFrameController/saveOrUpdateRunTime.do",  
					data : {	
						runtimeCode:$('#runtimeCode').val(),
			            runTimeName :$("#runTimeName").val(),       
			            monStartRunTime:$("#monStartRunTime").val(),   
			            monEndRunTime:$("#monEndRunTime").val(),     
			            tueStartRunTime:$("#tueStartRunTime").val(),     
			            tueEndRunTime:$("#tueEndRunTime").val(),    
			            wedStartRunTime :$("#wedStartRunTime").val(), 
			            wedEndRunTime:$("#wedEndRunTime").val(),        
			            thursStartRunTime:$("#thursStartRunTime").val(),   
			            thursEndRunTime:$("#thursEndRunTime").val(),      
			            friStartRunTime :$("#friStartRunTime").val(),    
			            friEndRunTime:$("#friEndRunTime").val(),        
			            satStartRunTime:$("#satStartRunTime").val(),     
			            satEndRunTime:$("#satEndRunTime").val(),       
			            sunStartRunTime:$("#sunStartRunTime").val(),     
			            sunEndRunTime:$("#sunEndRunTime").val()
					},
					success : function(msg) {
						showMsg(eval("("+msg+")").msg);
						submitForm();
		        		document.getElementById("runtimeCode").value = "";
		        		document.getElementById("runTimeName").value = "";
		        		document.getElementById("monStartRunTime").value = "08:00";		
		        		document.getElementById("monEndRunTime").value = "22:00";
		        		document.getElementById("tueStartRunTime").value = "08:00";
		        		document.getElementById("tueEndRunTime").value = "22:00";
		        		document.getElementById("wedStartRunTime").value = "08:00";
		        		document.getElementById("wedEndRunTime").value = "22:00";		
		        		document.getElementById("thursStartRunTime").value = "08:00";
		        		document.getElementById("thursEndRunTime").value = "22:00";	
		        		document.getElementById("friStartRunTime").value = "08:00";
		        		document.getElementById("friEndRunTime").value = "22:00";
		        		document.getElementById("satStartRunTime").value = "08:00";		
		        		document.getElementById("satEndRunTime").value = "22:00";
		        		document.getElementById("sunStartRunTime").value = "08:00";
		        		document.getElementById("sunEndRunTime").value = "22:00"; 
					},
				}); 
            },
            cancelValue: $.i18n.prop('btn.cancel'),
            cancel: function () {
        		document.getElementById("runtimeCode").value = "";
        		document.getElementById("runTimeName").value = "";
        		document.getElementById("monStartRunTime").value = "08:00";		
        		document.getElementById("monEndRunTime").value = "22:00";
        		document.getElementById("tueStartRunTime").value = "08:00";
        		document.getElementById("tueEndRunTime").value = "22:00";
        		document.getElementById("wedStartRunTime").value = "08:00";
        		document.getElementById("wedEndRunTime").value = "22:00";		
        		document.getElementById("thursStartRunTime").value = "08:00";
        		document.getElementById("thursEndRunTime").value = "22:00";	
        		document.getElementById("friStartRunTime").value = "08:00";
        		document.getElementById("friEndRunTime").value = "22:00";
        		document.getElementById("satStartRunTime").value = "08:00";		
        		document.getElementById("satEndRunTime").value = "22:00";
        		document.getElementById("sunStartRunTime").value = "08:00";
        		document.getElementById("sunEndRunTime").value = "22:00";   
            }
        });
        dialogCode.width(600);
        dialogCode.showModal();
    };
    //查看窗口
	function selectMakeCard(title, row) { 
		document.getElementById("runtimeCode").disabled =true;
		document.getElementById("runTimeName").disabled =true;
		document.getElementById("monStartRunTime").disabled =true;			
		document.getElementById("monEndRunTime").disabled =true;
		document.getElementById("tueStartRunTime").disabled =true;
		document.getElementById("tueEndRunTime").disabled =true;
		document.getElementById("wedStartRunTime").disabled =true;
		document.getElementById("wedEndRunTime").disabled =true;			
		document.getElementById("thursStartRunTime").disabled =true;
		document.getElementById("thursEndRunTime").disabled =true;	
		document.getElementById("friStartRunTime").disabled =true;
		document.getElementById("friEndRunTime").disabled =true;
		document.getElementById("satStartRunTime").disabled =true;			
		document.getElementById("satEndRunTime").disabled =true;
		document.getElementById("sunStartRunTime").disabled =true;	
		document.getElementById("sunEndRunTime").disabled =true;
		var rowData=$("#datatable").jqGrid('getRowData',row);	
		$('#runtimeCode').val(rowData.runtimecode);
        $("#runTimeName").val(rowData.runtimename);
        $("#monStartRunTime").val(rowData.Monday.substring(0,5));
 	    $("#monEndRunTime").val(rowData.Monday.substring(6,11)); 
        $("#tueStartRunTime").val(rowData.Tuesday.substring(0,5));    
        $("#tueEndRunTime").val(rowData.Tuesday.substring(6,11));    
        $("#wedStartRunTime").val(rowData.Wednesday.substring(0,5)); 
        $("#wedEndRunTime").val(rowData.Wednesday.substring(6,11));        
        $("#thursStartRunTime").val(rowData.Thursday.substring(0,5));   
        $("#thursEndRunTime").val(rowData.Thursday.substring(6,11));      
        $("#friStartRunTime").val(rowData.Friday.substring(0,5));     
        $("#friEndRunTime").val(rowData.Friday.substring(6,11));        
        $("#satStartRunTime").val(rowData.Saturday.substring(0,5));     
        $("#satEndRunTime").val(rowData.Saturday.substring(6,11));       
        $("#sunStartRunTime").val(rowData.Sunday.substring(0,5));      
        $("#sunEndRunTime").val(rowData.Sunday.substring(6,11));
		dialogCode = dialog({
			title : title,
			content : $('#week'),
			cancelValue : $.i18n.prop('btn.cancel'),
			cancel : function() {
        		document.getElementById("runtimeCode").value = "";
        		document.getElementById("runTimeName").value = "";
        		document.getElementById("monStartRunTime").value = "08:00";		
        		document.getElementById("monEndRunTime").value = "22:00";
        		document.getElementById("tueStartRunTime").value = "08:00";
        		document.getElementById("tueEndRunTime").value = "22:00";
        		document.getElementById("wedStartRunTime").value = "08:00";
        		document.getElementById("wedEndRunTime").value = "22:00";		
        		document.getElementById("thursStartRunTime").value = "08:00";
        		document.getElementById("thursEndRunTime").value = "22:00";	
        		document.getElementById("friStartRunTime").value = "08:00";
        		document.getElementById("friEndRunTime").value = "22:00";
        		document.getElementById("satStartRunTime").value = "08:00";		
        		document.getElementById("satEndRunTime").value = "22:00";
        		document.getElementById("sunStartRunTime").value = "08:00";
        		document.getElementById("sunEndRunTime").value = "22:00"; 
			}
		});
		dialogCode.width(600);
		dialogCode.showModal();
	};
    function showMsg(themsg){//显示提示信息，1s后自动关闭
         var d = dialog({
            content:themsg
        });
        d.show();
        setTimeout(function () {
            d.close().remove();
        }, 1000);
    }
</script>
</body>
</html>