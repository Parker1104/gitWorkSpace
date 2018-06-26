<%@ page language="java"  import="java.util.*" contentType="text/html; charset=utf-8"
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
    <title>员工绑定时间</title>
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
<object ID="CardReader" CLASSID= "CLSID:099B4B05-14CE-4DE6-83F4-9082E9A9327C"
		CODEBASE="DcdzCRX.CAB#version=1,0,0,1" width=0 height=0></object>
<!-- <div class="title">
    <p>查询条件</p>
</div> -->
<div class="content">   
    <div class="tab">
        <table id="datatable"></table>
        <div id="pager2"></div>
    </div>
</div>
<div id="win" style="display: none" class="menuContent" >
    <ul id="treeDemo" class="ztree classify" style="width: 200px;height: 300px"></ul>
</div>
<div id="showsearch" class="list" style="display: none">
    <form id="searchMemberCardForm" action="${basePath}/userAndRuntime/list" method="post">
        <div class="list">           
            <ul>
                <li>卡号：<input name="usercardid" type="text"/></li>                               
            </ul>
        </div>
    </form>
</div>
<div id="showadd" class="list" style="display: none">
    <form id="modifyCode" action="${basePath}/userAndRuntime/insert" method="post" >
        <ul>
            <li>姓名
            <select id="usercardid" name="usercardid">
	                  <c:forEach var="b" items="${user}">
	                     <option value="${b.usercardid}" selected>${b.usercardid} ${b.username}</option>
	                  </c:forEach>
               </select>
            </li>
            <li>运行时段：<select id="runtimecode" name="runtimecode">
                  <c:forEach var="m" items="${runtime}">
	                   <option value="${m.runtimecode}" selected>${m.runtimename}</option>
	              </c:forEach>
            </select></li>
            <li>备注：<input id="memo" name="memo"/></li>
        </ul>
    </form>
</div>
<div id="showupdate" class="list" style="display: none">
    <form id="modifyCodeupdate" action="${basePath}/userAndRuntime/update" method="post" >
        <ul>
            <li style="display:none;"><input id="id" name="id"/></li>
            <li>姓名
            <select id="usercardid2" name="usercardid">
	                  <c:forEach var="b" items="${user}">
	                     <option value="${b.usercardid}" selected>${b.usercardid} ${b.username}</option>
	                  </c:forEach>
               </select>
            </li>
            <li>运行时段：<select id="runtimecode2" name="runtimecode">
                  <c:forEach var="m" items="${runtime}">
	                   <option value="${m.runtimecode}" selected>${m.runtimename}</option>
	              </c:forEach>
            </select></li>
            <li>备注：<input id="memo2" name="memo"/></li>
        </ul>
    </form>
</div>
<script type="text/javascript">
/* var d=null;
var zTree=null;
function showCategory(){
    d = dialog({
        title: '选择区域',
        width:400,
        hight:500,
        content: document.getElementById('win'),
        id: 'EF893L',
        okValue: '确定',
        ok: function () {
            $('#areacode').val('');
            zTree = $.fn.zTree.getZTreeObj("treeDemo"),
                    nodes = zTree.getSelectedNodes(),
                    treeNode = nodes[0];
            alert(nodes[0].areacode);
            if (nodes.length > 0) {
            	alert(treeNode.areacode);
                $('#areacode').val(treeNode.areacode);
                $('#cc').val(treeNode.areaname);
                return true;
            }
            testText('',"请选择二级区域！",'cc');
            return false;
        }
    });
    d.height(320);
    d.show(document.getElementById('cc'));
}
var setting = {
    view: {
        selectedMulti: false
    },
    async: {
        enable: true,
        url: "${basePath}/managerController/areaList"
    },
    edit: {
        enable: true,
        showRemoveBtn: false,
        showRenameBtn: false
    },
    data: {
        keep: {
            parent:true,
            leaf:true
        },
        key: {
            name: "areaname"
        },
        simpleData: {
            enable: true,
            idKey: "areacode",
            pIdKey: "areacode"
        }
    },
    callback: {
        onAsyncSuccess: function(){
            var __zTree = $.fn.zTree.getZTreeObj("treeDemo");
            __zTree.expandAll(true);
        },
        onClick: function(){
            var __zTree = $.fn.zTree.getZTreeObj("treeDemo");
            var nodes = __zTree.getSelectedNodes();
            var code = nodes[0].areaname;
       //     alert(code);
        }
    }
}; */
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
    	//$.fn.zTree.init($("#treeDemo"), setting, null);
        var tableWidth = $("#datatable").parent().innerWidth();
        $("#datatable").jqGrid({
            url:'${basePath}/userAndRuntime/list',
            datatype: "json",
            mtype: "POST",
            width: document.body.offsetWidth-60,
            height: window.screen.availHeight-320,
            //autowidth:true,
            editable: false,
            shrinkToFit:true,
            colNames:['','卡号','姓名','','工作时段','备注'],
            colModel:[
                {name:"id",index:"id",align:'center',width:90,sortable:false,hidden:true},
                {name:"usercardid",index:"usercardid",align:'center',width:120,sortable:false},
                {name:"userEntity.username",index:"name",align:'center',width:100,sortable:false},
                {name:"runtimecode",index:"runtimecode",align:'center',width:100,sortable:false,hidden:true},
                {name:"runTimeEntity.runtimename",index:"runtimename",align:'center',width:100,sortable:false},
                {name:"memo",index:"memo",align:'center',width:100,sortable:false},
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
            toppager:true,
            sortname: 'rechargenumber',
            sortorder: 'desc',
            viewrecords: true,
            multiboxonly:true,
            loadComplete:function(){
				var myGrid = $("#datatable");
				$("#cb_"+myGrid[0].id).hide();
			},
            ondblClickRow:function(rowid){
	  	    	RowData= jQuery(this).jqGrid("getRowData", rowid);
	  	    	if(RowData.managercardid){
	  	    		list('查看',rowid);            	
               }
            },
        });
        $("#datatable").jqGrid("navGrid", "#pager2", { 
        	cloneToTop:true,
        	searchfunc:openDialog4Searching,
        	searchtext:"查询",
            addfunc : openDialog4Adding,    // (1) 点击添加按钮，则调用openDialog4Adding方法  
            addtext:"新增",
            editfunc : openDialog4Updating, // (2) 点击添加按钮，则调用openDialog4Updating方法  
            edittext:"修改",
            delfunc : openDialog4Deleting,  // (3) 点击添加按钮，则调用openDialog4Deleting方法  
            deltext:"删除",
            alerttext : "请选择需要操作的数据行!"   // (4) 当未选中任何行而点击编辑、删除、查看按钮时，弹出的提示信息  
        });
    });
    function list(title,row){//弹出查看窗口
        var col=$("#datatable").jqGrid('getRowData',row);
        //$('#cc').val(col.areaname);
        $('#areacode').val(col.areaname);
        $("#areacode").attr("disabled","disabled");
        $('#managercardid').val(col.managercardid);
        $("input[name='managercardid']").removeAttr("onblur");
        $('#managername').val(col.managername);
        var m = col.managertype;
        if(m=="管理员"){
        	$('#managertype').val(0);
        }else if(m=="维修员"){
        	$('#managertype').val(1);
        }else{
        	$('#managertype').val(2);
        }
        $("#managertype").attr("disabled","disabled");
        var sex = col.sex;
        if(sex=="女"){
        	$('#sex').val(0);
        }else{
        	$('#sex').val(1);
        }
        $("#sex").attr("disabled","disabled");
        $('#telephone').val(col.telephone);
        $('#company').val(col.company);
        $('#department').val(col.department);
        $('#subdepartment').val(col.subdepartment);
        $('#password').val(col.password);
        var s = col.state;
        if(s=="正常"){
        	$('#state').val(0);
        }else{
        	$('#state').val(1);
        }
        $("#state").attr("disabled","disabled");
        $('#memo').val(col.memo);
        $('input,select,textarea',$('form[id="modifyCode"]')).attr('readonly',true);
    dialogCode = dialog({
        title: title,
        content: $('#showadd'),
        cancelValue: '关闭',
        cancel: function () {
        	$('#modifyCode').clearForm(true);
        	$('input,select,textarea',$('form[id="modifyCode"]')).removeAttr("readonly");
        	$('input,select,textarea',$('form[id="modifyCode"]')).removeAttr("disabled");
        }
    });
    dialogCode.width(600);
    dialogCode.showModal();
}
    var openDialog4Searching = function(row) {
        dialogCode = dialog({
            title: '查询',
            content: $('#showsearch'),
            okValue: '查询',
            ok: function () {               
            	submitForm();
             },
            cancelValue: '关闭',
            cancel: function () {
                $('#modifyCode').clearForm(true);
            }
        });
        dialogCode.width(600);
        dialogCode.showModal();
    };
    var openDialog4Adding = function(title,okValue,row) {
        dialogCode = dialog({
            title: '新增',
            content: $('#showadd'),
            okValue: '保存',
            ok: function () {
                  $('#modifyCode').ajaxSubmit({
                       success: function (msg) {
                    	    showMsg(eval("("+msg+")").msg);
                            $('#modifyCode').clearForm(true);
                            $("#datatable").trigger('reloadGrid');
                         },
                     });
                 return true;
             },
            cancelValue: '关闭',
            cancel: function () {
                $('#modifyCode').clearForm(true);
            }
        });
        dialogCode.width(600);
        dialogCode.showModal();
    };
    var openDialog4Updating = function(row) {
    	var ids = $("#datatable").jqGrid("getGridParam", "selarrrow");
    	if(ids.length>1){
    		showMsg("请选择一行数据");
    	}else{
    	if(row>0){//说明是修改
            var col=$("#datatable").jqGrid('getRowData',row);
            $('#id').val(col.id);          
            $('#usercardid2').val(col.usercardid);
            $('#runtimecode2').val(col.runtimecode);           
            $('#memo2').val(col.memo);           
        }
        dialogCode = dialog({
            title: '修改',
            content: $('#showupdate'),
            okValue: '确定',
            ok: function () {
                    $('#modifyCodeupdate').ajaxSubmit({
                        success: function (msg) {
                        	showMsg(eval("("+msg+")").msg);
                        	$('#modifyCode').clearForm(true);
                        	$('input,select,textarea',$('form[id="modifyCode"]')).removeAttr("readonly");
                        	$("#datatable").trigger('reloadGrid');
                        }
                    });
                    return true;
            },
            cancelValue: '关闭',
            cancel: function () {
                $('#modifyCode').clearForm(true);
                $('input,select,textarea',$('form[id="modifyCode"]')).removeAttr("readonly");
            }
        });
        dialogCode.width(600);
        dialogCode.showModal();
    	}
    };
    var openDialog4Deleting = function(row) {
    	var ids = $("#datatable").jqGrid("getGridParam", "selarrrow");
    	var d = dialog({
            title: '提示',
            content: '您确认想要删除吗？',
            okValue: '确定',
            ok: function () {
                this.title('提交中…');
                for(var i=0;i<ids.length;i++){
                	var col=$("#datatable").jqGrid('getRowData',ids[i]);
                	$.ajax({
                        type: "POST",
                        url: "${basePath}/userAndRuntime/delete?id="+col.id,
                        success: function(msg){
                        	showMsg(eval("("+msg+")").msg);
                            $("#datatable").trigger('reloadGrid');
                            setTimeout(function () {
                            }, 2500);
                        }
                    });
                }
            },
            cancelValue: '取消',
            cancel: function () {}
        });
        d.width(150);
        d.show();
    };
    function delRepository(id,num){
        var d = dialog({
            title: '提示',
            content: '您确认想要删除吗？',
            okValue: '确定',
            ok: function () {
                this.title('提交中…');
                $.ajax({
                    type: "POST",
                    url: "${basePath}/managerController/delete?managercardid="+id+"&areacode="+num,
                    success: function(msg){
                    	showMsg(eval("("+msg+")").msg);
                        $("#datatable").trigger('reloadGrid');
                        setTimeout(function () {
                        }, 2500);
                    }
                });
            },
            cancelValue: '取消',
            cancel: function () {}
        });
        d.width(150);
        d.show();
    }
    var dialogCode=null;
    function addMakeCard(title,okValue,row){//弹出修改和添加的窗口
        if(row>0){//说明是修改
            var col=$("#datatable").jqGrid('getRowData',row);
            $('#cc').val(col.areaname);
            $('#managercardid').val(col.managercardid);
            $('#managercardid').attr("readonly",true);
            $('#managername').val(col.managername);
            var m = col.managertype;
            if(m=="管理员"){
            	$('#managertype').val(0);
            }else if(m=="维修员"){
            	$('#managertype').val(1);
            }else{
            	$('#managertype').val(2);
            }
            var sex = col.sex;
            if(sex=="女"){
            	$('#sex').val(0);
            }else{
            	$('#sex').val(1);
            }
            $('#telephone').val(col.telephone);
            $('#company').val(col.company);
            $('#department').val(col.department);
            $('#subdepartment').val(col.subdepartment);
            $('#password').val(col.password);
            var s = col.state;
            if(s=="正常"){
            	$('#state').val(0);
            }else{
            	$('#state').val(1);
            }
            $('#memo').val(col.memo);   
        }
        dialogCode = dialog({
            title: title,
            content: $('#showadd'),
            okValue: okValue,
            ok: function () {
                    $('#modifyCode').ajaxSubmit({
                        success: function (msg) {
                        	showMsg(eval("("+msg+")").msg);
                        	$("#datatable").trigger('reloadGrid');
                        }
                    });
                    return true;
            },
            cancelValue: '关闭',
            cancel: function () {
                $('#modifyCode').clearForm(true);
            }
        });
        dialogCode.width(600);
        dialogCode.showModal();
    }
    function selectone(){
    	var axCardReader = document.getElementById('CardReader');
		if(axCardReader == null){
			alert("创建组件失败")
		}else{
			axCardReader.AXRCC_Open();
			var cardid = axCardReader.AXRCC_readCardID();
			document.getElementById('managercardid').value = cardid;
			axCardReader.AXRCC_Close();
		}
    	var managercardid = document.getElementById("managercardid").value;   	
    	if(managercardid==""){
    		showMsg("请输入卡号");
    	}else{   		
	    	$.ajax({
				type:"POST",
				url:"${basePath}/managerController/select",
				data:{
					areacode:$("#areacode").val(),									
					managercardid:managercardid,
				},
				success:function(msg) {
					showMsg(eval("("+msg+")").msg);
					if(eval("("+msg+")").msg=="卡号已经存在"){
					  $('#managercardid').val("");
					}
				},
			});
        }
    }
    $(function(){    	
    	$(document).keypress(function (e) {
            if (e.keyCode == 13){
            	submitForm();
            	$('#searchMemberCardForm').clearForm(true);
            setTimeout(function () {
            	dialogCode.close().remove();
            }, 100);
            }
         })
    });
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

