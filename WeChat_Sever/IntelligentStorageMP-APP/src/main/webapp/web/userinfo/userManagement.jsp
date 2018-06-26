<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
    request.setAttribute("basePath",basePath);
%>
<!DOCTYPE html>
<html>
<head>
    <title>用户管理</title>
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
    <p>用户管理</p>
</div> -->
<div class="content">
    <div class="tab">
        <table id="datatable" style="width:90%"></table>
        <div id="pager2"></div>
    </div>
</div>
<div id="showsearch" class="list" style="display: none">
    <form id="searchForm" action="${pageContext.request.contextPath}/userController/user" method="post">
        <div class="list">
            <ul>
                <li>卡号：<input name="usercardid" type="text"/></li>
                <li>姓名：<input name="username" type="text"/></li>
                <li id="s">类型：<select name="usertype">
                    <option value="" selected>请选择</option>
                    <option value="0">微信用户</option>
	                <option value="1">贵宾用户</option>
	                <option value="9">黑名单用户</option>
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
    <form id="modifyCode" action="${pageContext.request.contextPath}/userController/userSave.do" method="post" >
        <ul>
            <li>卡号：<input id="usercardid" name="usercardid" type="text" maxlength="24" onclick="readCard()"/></li>
            <li>姓名：<input id="username" name="username" type="text" maxlength="32"/></li>
            <li>类型：<select id="usertype" name="usertype">
                <option value="0">微信用户</option>
                <option value="1">贵宾用户</option>
                <option value="9">黑名单用户</option>
            </select></li>
            <li>性别：<select id="sex" name="sex">
                <option value="0">女</option>
                <option value="1">男</option>
            </select></li>
            <li>证件类型：<select id="idtype" name="idtype">
                <option value="0">微信认证</option>
                <option value="1">驾驶证</option>
                <option value="2">学生证</option>
            </select></li>
            <li>证件号：<input id="idcode" name="idcode" type="text" maxlength="32"/></li>
            <li>手机号：<input id="telephone" name="telephone" type="text" maxlength="20"/></li>
            <li>学校名称：<input id="company" name="company" type="text" maxlength="32"/></li>
            <!-- <li>部门名称：<input id="department" name="department" type="text" maxlength="32"/></li> -->
            <%-- <li>专业名称：<select id="department" name="department">
                    <option value="" selected>请选择</option>
                    <c:forEach var="d" items="${dept}">
	                   <option value="${d.department}" selected>${d.department}</option>
	                </c:forEach>
                </select></li> --%>
            <li>专业名称：<input id="department" name="department" type="text" maxlength="32"/></li>
            <li>班级：<input id="subdepartment" name="subdepartment" type="text" maxlength="32"/></li>
            <li>邮箱：<input id="address" name="address" type="text" maxlength="50"/></li>
            <li>密码：<input id="password" name="password" type="password" type="text" maxlength="24"/></li>
            <!-- <li>有效期：<input id="enddate" name="enddate" class="Wdate" onFocus="WdatePicker()" type="text"/></li> -->
            <li>状态：<select id="state" name="state">
                <option value="0">正常</option>
                <option value="1">停用</option>
            </select></li>
            <li>折扣率：<input id="discountrate" name="discountrate" type="number" min="0" max="100"/></li>
            <!-- <li>创建时间：<input id="makedate" name="makedate" type="text"/></li> -->
            <li>备注：<input id="memo" name="memo" type="text"/></li>          
            <!-- <li>备注：<textarea rows="3" cols="26" style="resize:none"></textarea></li> -->
        </ul>
    </form>
</div>
<div id="showupdate" class="list" style="display: none">
    <form id="modifyCodeupdate" action="${pageContext.request.contextPath}/userController/userUpdate.do" method="post" >
        <ul>
            <li>卡号：<input id="usercardid1" name="usercardid" type="text" maxlength="24"/></li>
            <li>姓名：<input id="username1" name="username" type="text" maxlength="32"/></li>
            <li>类型：<select id="usertype1" name="usertype">
                <option value="0">微信用户</option>
                <option value="1">贵宾用户</option>
                <option value="9">黑名单用户</option>
            </select></li>
            <li>性别：<select id="sex1" name="sex">
                <option value="0">女</option>
                <option value="1">男</option>
            </select></li>
            <li>证件类型：<select id="idtype1" name="idtype">
                <option value="0">微信认证</option>
                <option value="1">驾驶证</option>
                <option value="2">学生证</option>
            </select></li>
            <li>证件号：<input id="idcode1" name="idcode" type="text" maxlength="32"/></li>
            <li>手机号：<input id="telephone1" name="telephone" type="text"/></li>
            <li>学校名称：<input id="company1" name="company" type="text" maxlength="32"/></li>
            <li>专业名称：<input id="department1" name="department" type="text" maxlength="32"/></li>
            <li>微信：<input id="subdepartment1" name="subdepartment" type="text" maxlength="32"/></li>
            <li>邮箱：<input id="address1" name="address" type="text" maxlength="50"/></li>
            <li id="ps">密码：<input id="password1" name="password" type="password" maxlength="24"/></li>
            <!-- <li>有效期：<input id="enddate" name="enddate" class="Wdate" onFocus="WdatePicker()" type="text"/></li> -->
            <li>状态：<select id="state1" name="state">
                <option value="0">正常</option>
                <option value="1">停用</option>
            </select></li>
            <li>折扣率：<input id="discountrate1" name="discountrate" type="number" min="0" max="100"/></li>
            <!-- <li>创建时间：<input id="makedate" name="makedate" type="text"/></li> -->
            <li>备注：<input id="memo1" name="memo" type="text"/></li>
        </ul>
    </form>
</div>
<script type="text/javascript">
function submitForm(){
    $("#datatable").jqGrid('setGridParam',{
        postData:formToJson("searchForm"),
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
            url:'${pageContext.request.contextPath}/userController/user',
            datatype: "json",
            mtype: "POST",
			width : document.body.offsetWidth - 60,
			height : window.screen.availHeight - 325,
            //autowidth:true,
            editable: false,
            shrinkToFit:true,
            colNames:['OpenID','用户昵称','类型','性别','省份','城市','状态','最后操作时间'],
            colModel:[
                {name:"usercardid",index:'usercardid',align:'center',width:70,sortable:false},
                {name:"nickname",index:"nickname",align:'center',width:70,sortable:false},
                {name:"usertype",index:"usertype",align:'center',width:70,sortable:false,formatter:function(el,options,rowData){
                	var user = rowData.usertype;
                	if(user==0){return "微信用户";}else if(user==1){return "贵宾用户";}else if(user==9){return "黑名单用户";}
                }},
                {name:"sex",index:"sex",align:'center',width:70,sortable:false,formatter:function(el,options,rowData){
                	var sex = rowData.sex;
                	if(sex==0){return "女";}else{return "男";}
                }},
                {name:"province",index:'province',align:'center',width:70,sortable:false},
                {name:"city",index:'city',align:'center',width:70,sortable:false},
                {name:"state",index:"state",align:'center',width:70,sortable:false,formatter:function(el,options,rowData){
                	var state = rowData.state;
                	if(state==0){return "正常";}else{return "停用";}
                }},
                {name:"lastmodifytime",index:'lastmodifytime',align:'center',width:70,sortable:false,formatter:function(el,options,rowData){
                	var lastmodifytime = rowData.lastmodifytime;
                	console.log(lastmodifytime);
                	var date = new Date(lastmodifytime);
               	  	return date.format("yyyy-MM-dd hh:mm:ss");
                }},
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
            rowList:[10,15,20,500],
            sortname: 'usercardid',
            sortorder: 'desc',
            toppager:true,
            viewrecords: true,
            multiboxonly:true,
            loadComplete:function(){
				var myGrid = $("#datatable");
				$("#cb_"+myGrid[0].id).hide();
			},
            ondblClickRow:function(rowid){
	  	    	RowData= jQuery(this).jqGrid("getRowData", rowid);
	  	    	if(RowData.usercardid){
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
        $("#datatable").navButtonAdd('#pager2',{
        	cloneToTop:true,
        	caption:"导出Excel", 
        	buttonicon:"ui-icon-excel", 
        	onClickButton: function(){
        		alert("导出excel");
        		var title = "属性"; 
        		getXlsFromTbl('datatable', 'div_list', title, false);
        	}, 
        	position:"last" 
        });
    });
    function readCard(){
    	var axCardReader = document.getElementById('CardReader');
		if(axCardReader == null){
			alert("创建组件失败")
		}else{
			axCardReader.AXRCC_Open();
			var cardid = axCardReader.AXRCC_readCardID();
			document.getElementById('usercardid').value = cardid;
			axCardReader.AXRCC_Close();
		}
    }
    //清空空件内容  
    function addEmptying(){
		document.getElementById("usercardid").value = '';
		document.getElementById("username").value = '';
		document.getElementById("usertype").value = 0;
		document.getElementById("sex").value = 0;
		document.getElementsByName("idtype").value = 0;
		document.getElementById("idcode").value = '';
		document.getElementById("telephone").value = '';
		document.getElementById("company").value = '';
		document.getElementById("department").value = '';
		document.getElementById("subdepartment").value = '';
		document.getElementById("address").value = '';
		document.getElementById("password").value = '';
		document.getElementById("state").value = 0;
		document.getElementById("discountrate").value = '';
		document.getElementById("memo").value = '';
    };
    function updatEmptying(){
		document.getElementById("usercardid1").value = '';
		document.getElementById("username1").value = '';
		document.getElementById("usertype1").value = 0;
		document.getElementById("sex1").value = 0;
		document.getElementsByName("idtype1").value = 0;
		document.getElementById("idcode1").value = '';
		document.getElementById("telephone1").value = '';
		document.getElementById("company1").value = '';
		document.getElementById("department1").value = '';
		document.getElementById("subdepartment1").value = '';
		document.getElementById("address1").value = '';
		document.getElementById("password1").value = '';
		document.getElementById("state1").value = 0;
		document.getElementById("discountrate1").value = '';
		document.getElementById("memo1").value = '';
    };
    
    var dialogCode=null;
    var openDialog4Adding = function(title,okValue) {
        dialogCode = dialog({
            title: "新增",
            content: $('#showadd'),
            okValue: "保存",
            ok: function () {
            	    var usercardid = document.getElementById("usercardid").value;
            	    var username = document.getElementById("username").value;
            	    if(usercardid==""){
            	    	showMsg("卡号不能为空");
            	    	return false;
            	    }else if(username==""){
            	    	showMsg("姓名不能为空");
            	    	return false;
            	    }else{
                    $('#modifyCode').ajaxSubmit({
                    	error: function(){
                    		alert("操作失败");
                    	},
                        success: function (msg) {
                        	showMsg(eval("("+msg+")").msg);
                        	$("#datatable").trigger('reloadGrid');
                        	//$('#modifyCode').clearForm(true);
            	            addEmptying();
                        }
                    });
                    return true;
            	}
            },
            cancelValue: '关闭',
            cancel: function () {
                //$('#modifyCode').clearForm(true);
            	addEmptying();
            }
        });
        dialogCode.width(600);
        dialogCode.showModal();
    };
    var openDialog4Searching = function(title,okValue) {
    	dialogCode = dialog({
            title: "查询",
            content: $('#showsearch'),
            okValue: "查询",
            ok: function () {
                    $('#searchForm').ajaxSubmit({
                    	error: function(){
                    		alert("操作失败");
                    	},
                        success: function (msg) {
                        	submitForm();
                        	$('#searchForm').clearForm(true);
                        }
                    });
                    return true;
            },
            cancelValue: '关闭',
            cancel: function () {
                $('#searchForm').clearForm(true);
            }
        });
        dialogCode.width(400);
        dialogCode.showModal();
    };
    var openDialog4Updating = function(row) {
    	var ids = $("#datatable").jqGrid("getGridParam", "selarrrow");
    	if(ids.length>1){
    		showMsg("请选择一行数据！");
    	}else{
	    	if(row>0){//说明是修改
	            var col=$("#datatable").jqGrid('getRowData',row);
	            $('#usercardid1').val(col.usercardid);
	            $('#usercardid1').attr("readonly",true);
	            $('#username1').val(col.username);
	            $('#usertype1').val(col.usertype);
	            $('#sex1').val(col.sex);
	            $('#idtype1').val(col.idtype);
	            $('#idcode1').val(col.idcode);
	            $('#state1').val(col.state);
	            $('#telephone1').val(col.telephone);
	            $('#department1').val(col.department);
	            $('#company1').val(col.company);
	            $('#subdepartment1').val(col.subdepartment);
	            $('#address1').val(col.address);
	            $('#password1').val(col.password);
	            $('#ps').attr("hidden",false);
	            if(col.state == "正常"){
	            	$('#state1').val(0);
	            }else {
	            	$('#state1').val(1);
				}       
	            $('#discountrate1').val(col.discountrate);
	            $('#memo1').val(col.memo);
	        }
	
	        dialogCode = dialog({
	            title: "修改",
	            content: $('#showupdate'),
	            okValue: "确定",
	            ok: function () {
	                    $('#modifyCodeupdate').ajaxSubmit({
	                    	error: function(){
	                    		alert("操作失败");
	                    	},
	                        success: function (msg) {
	                        	showMsg(eval("("+msg+")").msg);
	                        	$("#datatable").trigger('reloadGrid');
	                        	updatEmptying();
	                        }
	                    });
	                    return true;
	            },
	            cancelValue: '关闭',
	            cancel: function () {
	            	updatEmptying();
	            }
	        });
	        dialogCode.width(600);
	        dialogCode.showModal();
    	}
    };
    var openDialog4Deleting = function(rowid) {
    	var col=$("#datatable").jqGrid('getRowData',rowid);
		var ids = jQuery("#datatable").jqGrid('getGridParam', 'selarrrow');
		if(ids.length>1){
			showMsg("无法删除多行");
		}else {
	    	var dialogCode = dialog({
	            title: '提示',
	            content: '您确认想要删除吗？',
	            okValue: '确定',
	            ok: function () {
	                this.title('提交中…');
	                $.ajax({
	                    type: "POST",
	                    url: "${pageContext.request.contextPath}/userController/userDelete.do?id="+col.usercardid,
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
	    	dialogCode.width(150);
	    	dialogCode.showModal();
		}
    }; 

    function list(title,row){//弹出查看窗口
        var col=$("#datatable").jqGrid('getRowData',row);
        $('#usercardid1').val(col.usercardid);
        $('#usercardid1').attr("readonly",true);
        $('#username1').val(col.username);
        $('#usertype1').val(col.usertype);
        $("#usertype1").attr("disabled","disabled");
        $('#sex1').val(col.sex);
        $("#sex1").attr("disabled","disabled");
        $('#idtype1').val(col.idtype);
        $("#idtype1").attr("disabled","disabled");
        $('#idcode1').val(col.idcode);
        $('#state1').val(col.state);
        $("#state1").attr("disabled","disabled");
        $('#telephone1').val(col.telephone);
        $('#department1').val(col.department);
        $('#company1').val(col.company);
        $('#subdepartment1').val(col.subdepartment);
        $('#address1').val(col.address);
        $('#ps').attr("hidden",true);
        if(col.state == "正常"){
        	$('#state1').val(0);
        }else {
        	$('#state1').val(1);
		}    
        $('#discountrate1').val(col.discountrate);
        $('#memo1').val(col.memo);
        $('input,select,textarea',$('form[id="modifyCodeupdate"]')).attr('readonly',true);
    dialogCode = dialog({
        title: title,
        content: $('#showupdate'),
        cancelValue: '关闭',
        cancel: function () {
        	$('#modifyCode').clearForm(true);
        	$('input,select,textarea',$('form[id="modifyCodeupdate"]')).removeAttr("readonly");
        	$('input,select,textarea',$('form[id="modifyCodeupdate"]')).removeAttr("disabled");
        }
    });
    dialogCode.width(600);
    dialogCode.showModal();
 }
    $(function(){    	
    	$(document).keypress(function (e) {
            if (e.keyCode == 13){
            	submitForm();
            	$('#searchForm').clearForm(true);
            setTimeout(function () {
            	dialogCode.close().remove();
            }, 100);
            }
         })
    });
    function getXlsFromTbl(inTblId, inTblContainerId, title, rownumbers) {  
        try {  
            var allStr = "";  
            var curStr = "";  
            //alert("getXlsFromTbl");  
            if (inTblId != null && inTblId != "" && inTblId != "null") {  
                curStr = getTblData($('#' + inTblId), $('#' + inTblContainerId), rownumbers);  
            }  
            if (curStr != null) {  
                allStr += curStr;  
            }  
            else {  
                alert("你要导出的表不存在！");  
                return;  
            }  
            var fileName = getExcelFileName(title);  
            doFileExport(fileName, allStr);  
        }  
        catch (e) {  
            alert("导出发生异常:" + e.name + "->" + e.description + "!");  
        }  
    }  
    function getTblData(curTbl, curTblContainer, rownumbers) {  
      
        var outStr = "";  
        if (curTbl != null) {  
            var rowdata = curTbl.getRowData();  
            var Lenr = 1;  
      
            for (i = 0; i < Lenr; i++) {  
                //var Lenc = curTbl.rows(i).cells.length;  
                var th;  
                if (rownumbers == true) {  
                    th = curTblContainer.find('TH:not(:first-child)');  
                }  
                else {  
                    th = curTblContainer.find('TH');  
                }  
                th.each(function(index, element) {  
                    //alert($(element).text());  
                    //取得每行的列数  
                    var j = index + 1;  
                    var content = $(element).text();  
                    //alert(j + "|" + content);  
                    outStr += content + "\t";  
                    //赋值  
      
                });  
                outStr += "\r\n";  
            }  
            var tmp = "";  
            for (i = 0; i < rowdata.length; i++) {  
                var row = eval(rowdata[i]);  
                for (each in row) {  
                    outStr += row[each] + "\t";  
                }  
                outStr += "\r\n";  
            }  
      
      
        }  
        else {  
            outStr = null;  
            alert(inTbl + "不存在!");  
        }  
        return outStr;  
    }  
    function getExcelFileName(title) {  
        var d = new Date();  
        var curYear = d.getYear();  
        var curMonth = "" + (d.getMonth() + 1);  
        var curDate = "" + d.getDate();  
        var curHour = "" + d.getHours();  
        var curMinute = "" + d.getMinutes();  
        var curSecond = "" + d.getSeconds();  
        if (curMonth.length == 1) {  
            curMonth = "0" + curMonth;  
        }  
        if (curDate.length == 1) {  
            curDate = "0" + curDate;  
        }  
        if (curHour.length == 1) {  
            curHour = "0" + curHour;  
        }  
        if (curMinute.length == 1) {  
            curMinute = "0" + curMinute;  
        }  
        if (curSecond.length == 1) {  
            curSecond = "0" + curSecond;  
        }  
        var fileName = title + "_" + curYear + curMonth + curDate + "_"  
                + curHour + curMinute + curSecond + ".csv";  
        alert(fileName);   
        return fileName;  
    }  
    function doFileExport(inName, inStr) {  
        var xlsWin = null;  
        if (!!document.all("HideFrm")) {  
            xlsWin = HideFrm;  
        }  
        else {  
            var width = 6;  
            var height = 4;  
            var openPara = "left=" + (window.screen.width / 2 - width / 2)  
                    + ",top=" + (window.screen.height / 2 - height / 2)  
                    + ",scrollbars=no,width=" + width + ",height=" + height;  
            xlsWin = window.open("", "_blank", openPara);  
        }  
        xlsWin.document.write(inStr);  
        xlsWin.document.close();  
        xlsWin.document.execCommand('Saveas', true, inName);  
        xlsWin.close();  
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
