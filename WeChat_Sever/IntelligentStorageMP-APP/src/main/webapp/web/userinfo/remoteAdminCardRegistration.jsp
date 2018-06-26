<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
    request.setAttribute("basePath",basePath);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>远程登记管理卡</title>
     <%@ include file="../../common/head.jsp"  %>
     <%@ include file="../../common/theme.jsp" %>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/js/plug/zTree/css/demo.css" type="text/css">

    <style>
    .list .btn_another input { width: 88px !important; height: 24px !important; border-radius: 3px; margin-bottom: 10px;  background: #74a3dc; border: none; cursor: pointer; font-family: "微软雅黑"; color: #fff; font-size: 14px;  }
    .list .btn_another input:hover { background-color: #189ad8; }
    .confirm { width: 100% !important; float:left; }
    .confirm input { margin-left: 40px;}
    .list li { height: 35px;}
    .bottom-input { width: 70%; float: right; margin-top: 10px;}
    .bottom-input li { width: 320px; float: right; text-align: right;}
    .bottom-input li input { width: 200px; height: 24px; border: 1px solid #ccc; border-radius: 3px;}
    td#datatable_toppager_center {
    display: none;
}
</style>
</head>
<body>
<!-- <div class="title">
    <p>远程登记管理卡</p>
</div> -->
<div class="content">
    <%-- <form id="searchMemberCardForm" action="${basePath}/remoteAdmin/selectAdmin" method="post">
        <div class="list">
            <ul>
                <li>管理卡号：<input name="managercardid" type="text"/></li>
                <li>姓名：<input name="managername" type="text"/></li>
                <li style="margin-left: 50px;">
	                <input onclick="submitForm()" value="查询" type="button" style="width: 88px; font-size: 14px; "/>
	                <!-- <input name="button" onclick="addMakeCard('绑定箱门','绑定',0)" value="绑定箱门" type="button" style="width: 88px; font-size: 14px; "/> -->
                </li>
            </ul>
        </div>
    </form> --%>
    <div class="tab">
        <table id="datatable" style="width:90%"></table>
        <div id="pager2"></div>
    </div>
</div>
<div id="showsearch" class="list" style="display: none">
    <form id="searchMemberCardForm" action="${basePath}/remoteAdmin/selectAdmin" method="post">
        <div class="list">
            <ul>
                <li>管理卡号：<input name="managercardid" type="text"/></li>
                <li>姓名：<input name="managername" type="text"/></li>
            </ul>
        </div>
    </form>
</div>
<div id="showadd" class="list" style="display: none">
    <form id="modifyCode" action="${basePath}/remoteAdmin/add" method="post" >
        <ul>
            <li>管理卡号：<input id="managercardid" name="cardid"/></li>
            <li>姓名：<input id="managername" name="managername" /></li>
            <li>柜号：<select id="terminalid" name="terminalid" onchange="opt()">
                <c:forEach var="t" items="${terminal}">
	                 <option value="${t.terminalid}" selected>${t.displayname}</option>
	            </c:forEach>
            </select></li>
            <%-- <li>箱号：<select id="boxid" name="boxid">
                <c:forEach var="b" items="${box}">
	                 <option value="${b.boxid}" selected>${b.dispalyname}</option>
	            </c:forEach>
            </select></li> --%>
        </ul>
    </form>
</div>
<script type="text/javascript">
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
        var tableWidth = $("#datatable").parent().innerWidth()+25;
        $("#datatable").jqGrid({
            url:'${basePath}/remoteAdmin/selectAdmin',
            datatype: "json",
            mtype: "POST",
            width: document.body.offsetWidth-60,
            height: window.screen.availHeight-320,
            //autowidth:true,
            editable: false,
            shrinkToFit:true,
            colNames:['','区域','卡号','姓名','类型','状态','性别','电话','公司','有效期','创建人'],
            colModel:[
                {name:"areaEntity.areacode",index:"areaEntity.areacode",align:'center',width:90,sortable:false,hidden:true},
                {name:"areaEntity.areaname",index:"areaEntity.areaname",align:'center',width:90,sortable:false},
                {name:"managercardid",index:"managercardid",align:'center',width:90,sortable:false},
                {name:"managername",index:'managername',align:'left',width:100,sortable:false},
                {name:"managertype",index:"managertype",align:'center',width:80,sortable:false,formatter:function(el,options,rowData){
                	if(el==0){return "管理员";}else if(el==1){return "维修员";}else if(el==2){return "清洁员";}else{return "经理卡"}
                }},
                {name:"state",index:"state",align:'center',width:70,sortable:false,formatter:function(el,options,rowData){
                    if(el==0){return "正常";}else{return "停用";}
                }},
                {name:"sex",index:"sex",align:'center',width:50,sortable:false,formatter:function(el,options,rowData){
                    if(el==0){return "女";}else{return "男";}
                }},
                {name:"telephone",index:'telephone',align:'center',width:70,sortable:false},
                {name:"company",index:'company',align:'left',width:70,sortable:false,formatter:function(el,options,rowData){
                    return el;
                }},
                {name:"endDate",index:'endDate',align:'left',width:70,sortable:false,formatter:function(el,options,rowData){
                	var t = rowData.makedate;
                    var d=	new Date();
                    d.setTime(t);
                    var s=d.format('yyyy-MM-dd');
                	return s;
                }},
                {name:"accountname",index:"accountname",align:'center',width:50,sortable:false,hidden:true},
                /* {name:"",index:'',align:'',width:70,sortable:false,formatter:function(el,options,rowData){
                	var u= '<input type="button" value="绑定箱门" onclick="addMakeCard(\'绑定箱门\',\'保存\','+ options.rowId +')" style="width:55px;float:right;margin-right:15px;"/>';
                   return u;
               }}, */
            ],
            sortable: false,
            rowNum:10,
            rownumbers:false,
            multiselect: true,
            jsonReader: {
                repeatitems : false,
                id:"0"
            },
            pager: '#pager2',
            rowList:[10,15,20],
            sortname: 'rechargenumber',
            sortorder: 'desc',
            viewrecords: true,
            toppager:true,
            multiboxonly:true,
            loadComplete:function(){
				var myGrid = $("#datatable");
				$("#cb_"+myGrid[0].id).hide();
			},
        });
        $("#datatable").jqGrid("navGrid", "#pager2", { 
        	cloneToTop:true,
        	add:false,
        	del:false,
        	searchfunc:openDialog4Searching,
        	searchtext:"查询",
            editfunc : openDialog4Updating, // (2) 点击添加按钮，则调用openDialog4Updating方法  
            edittext:"绑定箱门",
            alerttext : "请选择需要操作的数据行!"   // (4) 当未选中任何行而点击编辑、删除、查看按钮时，弹出的提示信息  
        });
    });
    
    function opt(){
    	var id = $("#terminalid").val(); 
    	$.ajax({
            type: "POST",
            url: "${basePath}/cardInfo/selectByid?terminalid="+id,
            success: function(msg){ 
            	var myobj=(eval("(" + msg + ")").msg);
            	$("#boxid").html("");
            	$.each(myobj,function(i,val){
            		$("#boxid").append("<option value="+val.boxid+">"+val.dispalyname+"</option>");
            	})
            }
        });
    }
    var openDialog4Searching = function() {
    	dialogCode = dialog({
            title: "查询",
            content: $('#showsearch'),
            okValue: "查询",
            ok: function () {
                    $('#searchMemberCardForm').ajaxSubmit({
                    	error: function(){
                    		alert("操作失败");
                    	},
                        success: function (msg) {
                        	submitForm();
                        }
                    });
                    return true;
            },
            cancelValue: '关闭',
            cancel: function () {
                $('#modifyCode').clearForm(false);
            }
        });
        dialogCode.width(600);
        dialogCode.showModal();
    }
    var openDialog4Updating = function(row) {
    	var id = $("#terminalid").val(); 
    	$.ajax({
            type: "POST",
            url: "${basePath}/cardInfo/selectByid?terminalid="+id,
            success: function(msg){ 
            	var myobj=(eval("(" + msg + ")").msg);
            	$("#boxid").html("");
            	$.each(myobj,function(i,val){
            		$("#boxid").append("<option value="+val.boxid+">"+val.dispalyname+"</option>");
            	})
            }
        });
    	if(row>0){//说明是修改
            var col=$("#datatable").jqGrid('getRowData',row);
            $('#managercardid').val(col.managercardid);
            $('#managername').val(col.managername);
        }

        dialogCode = dialog({
            title: "绑定箱门",
            content: $('#showadd'),
            okValue: "确定",
            ok: function () {
            	var usercardid = $("#managercardid").val();
            	$.ajax({
					type: "POST",
			        url: "${basePath}/cardInfo/selectByCardid?cardid="+usercardid,
			        success: function(msg){
			        	if(eval("("+msg+")").msg=="true"){
			        		$('#modifyCode').ajaxSubmit({
				        	 error: function(){
			                     alert("操作失败");
			                   },
			        		 success:function(msg){
			        			 showMsg(eval("("+msg+")").msg);
			                     submitForm();
			                 }});
			                 submitForm();
			                 return true;
			        	}else{
			        		showMsg("卡号已经绑定");
			        	}
			        }
            	});
            },
            cancelValue: '关闭',
            cancel: function () {
                $('#modifyCode').clearForm(false);
            }
        });
        dialogCode.width(600);
        dialogCode.showModal();
    }   
    function openDialog4Searching () {
    	dialogCode = dialog({
            title: "查询",
            content: $('#showsearch'),
            okValue: "查询",
            ok: function () {
                    $('#searchMemberCardForm').ajaxSubmit({
                    	error: function(){
                    		alert("操作失败");
                    	},
                        success: function (msg) {
                        	submitForm();
                        }
                    });
                    return true;
            },
            cancelValue: '关闭',
            cancel: function () {
                $('#modifyCode').clearForm(false);
            }
        });
        dialogCode.width(600);
        dialogCode.showModal();
    };
    
    var dialogCode=null;
    function addMakeCard(title,okValue,row){//弹出修改和添加的窗口
        if(row>0){//说明是修改
            var col=$("#datatable").jqGrid('getRowData',row);
            $('#managercardid').val(col.managercardid);
            $('#managername').val(col.managername);
        }

        dialogCode = dialog({
            title: title,
            content: $('#showadd'),
            okValue: okValue,
            ok: function () {
                $('#modifyCode').ajaxSubmit({success:function(msg){
                	showMsg(eval("("+msg+")").msg);
                    submitForm();
                }});
                submitForm();
                return true;
            },
            cancelValue: '关闭',
            cancel: function () {
                $('#modifyCode').clearForm(false);
            }
        });
        dialogCode.width(600);
        dialogCode.showModal();
    }
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
