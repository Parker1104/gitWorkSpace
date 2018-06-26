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
    <title>管理员管理</title>
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
    <form id="searchMemberCardForm" action="${pageContext.request.contextPath}/managerController/list" method="post">
        <div class="list">
            <input id="commoditycategoryl1" name="commoditycategoryl1" type="hidden">
            <ul>
                <li>卡号：<input name="managercardid" type="text"/></li>
                <li>姓名：<input name="managername" type="text"/></li>
                <li>类型：<select name="managertype">
                    <option value="" selected>请选择</option>
                    <c:forEach var="m" items="${manager}">
	                   <option value="${m.dictcode}" selected>${m.dictname}</option>
	                </c:forEach>
                </select></li>
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
    <form id="modifyCode" action="${pageContext.request.contextPath}/managerController/insert" method="post" >
        <ul>
            <!-- <li>区域编码：<input id="areacode" name="areacode"/></li> -->
            <li>区域：<!-- <input id="cc" onclick="showCategory()" value="" readonly/> -->
            <select id="areacode" name="areacode">
	                  <c:forEach var="b" items="${area}">
	                     <option value="${b.areacode}" selected>${b.areaname}</option>
	                  </c:forEach>
               </select>
            </li>
            <li>卡号：<input id="managercardid" name="managercardid" onclick="selectone()"/></li>
            <li>姓名：<input id="managername" name="managername"/></li>
            <li>类型：<select id="managertype" name="managertype">
                  <c:forEach var="m" items="${manager}">
	                   <option value="${m.dictcode}" selected>${m.dictname}</option>
	              </c:forEach>
            </select></li>
            <li>性别：<select id="sex" name="sex">
                <option value="0">女</option>
                <option value="1">男</option>
            </select></li>
            <li>电话：<input id="telephone" name="telephone"/></li>
            <li>公司：<input id="company" name="company"/></li>
            <li>部门：<input id="department" name="department"/></li>
            <li>子部门：<input id="subdepartment" name="subdepartment"/></li>
            <li>密码：<input id="password" name="password" type="password"/></li>
            <li>状态：<select id="state" name="state">
                <option value="0">正常</option>
                <option value="1">停用</option>
            </select></li>
            <li>备注：<input id="memo" name="memo"/></li>
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
    	//$.fn.zTree.init($("#treeDemo"), setting, null);
        var tableWidth = $("#datatable").parent().innerWidth();
        $("#datatable").jqGrid({
            url:'${pageContext.request.contextPath}/managerController/list?areacode='+'${account.areacode}',
            datatype: "json",
            mtype: "POST",
            width: document.body.offsetWidth-60,
            height: window.screen.availHeight-320,
            //autowidth:true,
            editable: false,
            shrinkToFit:true,
            colNames:['部门','班级','描述','区域编码','区域','卡号','姓名','性别','类型','类型','状态','电话','密码','公司','有效期','创建人'], 
            colModel:[
                {name:"department",index:"department",align:'center',width:90,sortable:false,hidden:true},
                {name:"subdepartment",index:"subdepartment",align:'center',width:90,sortable:false,hidden:true},
                {name:"memo",index:"memo",align:'center',width:90,sortable:false,hidden:true},
                {name:"areacode",index:"areacode",align:'center',width:90,sortable:false,hidden:true},
                {name:"areaEntity.areaname",index:"areaname",align:'center',width:90,sortable:false},
                {name:"managercardid",index:"managercardid",align:'center',width:90,sortable:false},
                {name:"managername",index:'managername',align:'center',width:100,sortable:false},
                {name:"sex",index:"sex",align:'center',width:90,sortable:false,formatter:function(el,options,rowData){
                    if(el==0){return "女";}else{return "男";};
                }},
                {name:"managertype",index:"managertype",align:'center',width:80,sortable:false,hidden:true},
				{name : "dictname",index : "dictname",align : 'center',width : 100,sortable : false,formatter : function(el,options,rowData) {															
							 return rowData.dictEntity.dictname;																
				}},
                {name:"state",index:"state",align:'center',width:90,sortable:false,formatter:function(el,options,rowData){
                    if(el==0){return "正常";}else{return "停用";};
                }},
                {name:"telephone",index:'telephone',align:'center',width:70,sortable:false},
                {name:"password",index:'password',align:'center',width:70,sortable:false,hidden:true},
                {name:"company",index:'company',align:'left',width:100,sortable:false,formatter:function(el,options,rowData){
                    return el;
                }},
                {name:"endDate",index:'endDate',align:'center',width:100,sortable:false,formatter:function(el,options,rowData){
                	var t = rowData.makedate;
                    var d=	new Date();
                    d.setTime(t);
                    var s=d.format('yyyy-MM-dd');
                	return s;
                }},
                {name:"accountname",index:"accountname",align:'center',width:50,sortable:false,hidden:true},
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
    //清空空件内容  
    function Emptying(){
		document.getElementById("areacode").value = 0;
		document.getElementById("managercardid").value = '';
		document.getElementById("managername").value = 0;
		document.getElementById("managertype").value = 0;
		document.getElementsByName("sex").value = 0;
		document.getElementById("telephone").value = '';
		document.getElementById("company").value = '';
		document.getElementById("department").value = '';
		document.getElementById("subdepartment").value = '';
		document.getElementById("password").value = '';
		document.getElementById("state").value = 0;
		document.getElementById("memo").value = '';
    };
    function list(title,row){//弹出查看窗口
        var col=$("#datatable").jqGrid('getRowData',row);
        //$('#cc').val(col.areaname);
        $('#areacode').val(col.areaname);
        $("#areacode").attr("disabled","disabled");
        $('#managercardid').val(col.managercardid);
        $("input[name='managercardid']").removeAttr("onblur");
        $('#managername').val(col.managername);
        $('#managertype').val(col.managertype);
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
        	Emptying();
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
            	  var managercardid = document.getElementById("managercardid").value;
            	  var managername = document.getElementById("managername").value;
            	  var password = document.getElementById("password").value;
            	  if(managercardid==""){
            		  showMsg("卡号不能为空");
            		  return false;
            	  }else if(managername==""){
            		  showMsg("姓名不能为空");
            		  return false;
            	  }else if(password==""){
            		  showMsg("密码不能为空");
            		  return false;
            	  }else{
                  $('#modifyCode').ajaxSubmit({
                       success: function (msg) {
                    	    showMsg(eval("("+msg+")").msg);
                    	    Emptying();
                    	    $("#datatable").trigger('reloadGrid');
                         },
                     });
                 return true;
            	}
             },
            cancelValue: '关闭',
            cancel: function () {
            	Emptying();
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
            $('#areacode').val(col.areacode);          
            $('#managercardid').val(col.managercardid);
            $('#managercardid').attr("readonly",true);
            $("input[name='managercardid']").removeAttr("onblur");
            $('#managername').val(col.managername);
            //$('#managertype').value(col.dictname);
            $('#managertype').val(col.managertype);
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
            title: '修改',
            content: $('#showadd'),
            okValue: '确定',
            ok: function () {
                    $('#modifyCode').ajaxSubmit({
                        success: function (msg) {
                        	showMsg(eval("("+msg+")").msg);
                        	Emptying();
                        	$("#datatable").trigger('reloadGrid');
                        }
                    });
                    return true;
            },
            cancelValue: '关闭',
            cancel: function () {
            	Emptying();
            }
        });
        dialogCode.width(600);
        dialogCode.showModal();
    	}
    };
    var openDialog4Deleting = function(row) {
    	var ids = $("#datatable").jqGrid("getGridParam", "selarrrow");
    	if(ids.length>1){
    		showMsg("无法删除多行");
    	}else {
        	var dialogCode = dialog({
                title: '提示',
                content: '您确认想要删除吗？',
                okValue: '确定',
                ok: function () {
                    this.title('提交中…');
                    for(var i=0;i<ids.length;i++){
                    	var col=$("#datatable").jqGrid('getRowData',ids[i]);
                    	$.ajax({
                            type: "POST",
                            url: "${pageContext.request.contextPath}/managerController/delete?managercardid="+col.managercardid+"&areacode="+col.areacode,
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
        	dialogCode.width(150);
        	dialogCode.showModal();
		}

    };

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
				url:"${pageContext.request.contextPath}/managerController/select",
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

