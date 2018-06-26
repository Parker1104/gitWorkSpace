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
    <title>箱体预留登记</title>
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
                    <option value="0">普通用户</option>
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
<div id="showupdate" class="list" style="display: none">
    <form id="modifyCodeupdate" action="${pageContext.request.contextPath}/boxReservationRegistration/update.do" method="post" >
        <ul>
            <li>卡号：<input id="usercardid1" name="usercardid" type="text" maxlength="24"/></li>
            <li>姓名：<input id="username1" name="username" type="text" maxlength="32"/></li>                      
            <li>证件号：<input id="idcode1" name="idcode" type="text" maxlength="32"/></li>                       
            <li>状态：<select id="state1" name="state">
                <option value="0">正常</option>
                <option value="1">停用</option>
            </select></li>
            <li>柜号：<select id="terminalid" name="terminalid" onchange="opt()">
                <c:forEach var="t" items="${terminal}">
	                 <option value="${t.terminalid}" selected>${t.displayname}</option>
	            </c:forEach>
            </select></li>
            <li>箱号：<select id="boxid" name="boxid">
                <c:forEach var="b" items="${box}">
	                 <option value="${b.boxid}" selected>${b.dispalyname}</option>
	            </c:forEach>
            </select></li>           
        </ul>
    </form>
</div>
<script type="text/javascript">
function opt(){
	var id = $("#terminalid").val(); 
	$.ajax({
        type: "POST",
        url: "${pageContext.request.contextPath}/cardInfo/selectByid?terminalid="+id,
        success: function(msg){ 
        	var myobj=(eval("(" + msg + ")").msg);
        	$("#boxid").html("");
        	$.each(myobj,function(i,val){
        		$("#boxid").append("<option value="+val.boxid+">"+val.dispalyname+"</option>");
        	})
        }
    });
}
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
            width: document.body.offsetWidth-60,
            height: window.screen.availHeight-320,
            //autowidth:true,
            editable: false,
            shrinkToFit:true,
            colNames:['用户卡号','用户姓名','类型','性别','证件类型','','状态','电话','','','','','','','','有效期',],
            colModel:[
                {name:"usercardid",index:'usercardid',align:'left',width:100,sortable:false},
                {name:"username",index:"username",align:'center',width:50,sortable:false},
                {name:"usertype",index:"usertype",align:'center',width:80,sortable:false,formatter:function(el,options,rowData){
                	var user = rowData.usertype;
                	if(user==0){return "普通用户";}else if(user==1){return "贵宾用户";}else if(user==9){return "黑名单用户";}
                }},
                {name:"sex",index:"sex",align:'center',width:50,sortable:false,formatter:function(el,options,rowData){
                	var sex = rowData.sex;
                	if(sex==0){return "女";}else{return "男";}
                }},
                {name:"idtype",index:"idtype",frozen:true,align:'center',width:90,sortable:false,formatter:function(el,options,rowData){
                	var type = rowData.idtype;
                	if(type==0){return "身份证";}else if(type==1){return "驾驶证";}else{return "学生证";}
                }},
                {name:"idcode",index:'idcode',align:'center',width:90,sortable:false,hidden:true},
                {name:"state",index:"state",align:'center',width:90,sortable:false,formatter:function(el,options,rowData){
                	var state = rowData.state;
                	if(state==0){return "正常";}else{return "停用";}
                }},
                {name:"telephone",index:'telephone',align:'center',width:90,sortable:false},
                {name:"company",index:'company',align:'center',width:90,sortable:false,hidden:true},
                {name:"department",index:'department',align:'center',width:90,sortable:false,hidden:true},
                {name:"subdepartment",index:'subdepartment',align:'center',width:90,sortable:false,hidden:true},
                {name:"address",index:'address',align:'center',width:90,sortable:false,hidden:true},
                {name:"password",index:'password',align:'center',width:90,sortable:false,hidden:true},
                {name:"discountrate",index:'discountrate',align:'center',width:90,sortable:false,hidden:true},
                {name:"memo",index:'memo',align:'center',width:90,sortable:false,hidden:true},
                {name:"enddate",index:'enddate',align:'center',width:100,sortable:false,formatter:function(el,options,rowData){
                    var t = rowData.enddate;
                    var d=	new Date();
                    d.setTime(t);
                    var s=d.format('yyyy-MM-dd HH:mm:ss');
                	return s;
                }},
                /* {name:"",index:'',align:'center',width:70,sortable:false,formatter:function(el,options,rowData){
                   var u= '<input type="button" value="修改" onclick="addMakeCard(\'设置\',\'保存\','+ options.rowId +')" style="width:30px;float:right;margin-right:15px;"/>';
                   var d= '<input type="button" value="删除" onclick="delRepository('+ rowData.usercardid +')" style="width:30px;float:right;margin-right:10px;"/>';
                   return u+d;
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
        	add:false,
        	del:false,
        	searchfunc:openDialog4Searching,
        	searchtext:"查询",
            addfunc : openDialog4Adding,    // (1) 点击添加按钮，则调用openDialog4Adding方法  
            addtext:"新增",
            editfunc : openDialog4Updating, // (2) 点击添加按钮，则调用openDialog4Updating方法  
            edittext:"箱体预留登记",
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
    var dialogCode=null;
    var openDialog4Adding = function(title,okValue) {
        
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
        dialogCode.width(600);
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
	            $('#idcode1').val(col.idcode);
	            $('#state1').val(col.state);	           
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
	                        	$('#modifyCode').clearForm(true);
	                        	$('input,select,textarea',$('form[id="modifyCodeupdate"]')).removeAttr("readonly");
	                        }
	                    });
	                    return true;
	            },
	            cancelValue: '关闭',
	            cancel: function () {
	                $('#modifyCode').clearForm(true);
	                $('input,select,textarea',$('form[id="modifyCodeupdate"]')).removeAttr("readonly");
	            }
	        });
	        dialogCode.width(600);
	        dialogCode.showModal();
    	}
    };
    var openDialog4Deleting = function(rowid) {
    	var col=$("#datatable").jqGrid('getRowData',rowid);
    	var d = dialog({
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
        d.width(150);
        d.show();
    }; 
    function delRepository(num){
        var d = dialog({
            title: '提示',
            content: '您确认想要删除吗？',
            okValue: '确定',
            ok: function () {
                this.title('提交中…');
                $.ajax({
                    type: "POST",
                    url: "${pageContext.request.contextPath}/userController/userDelete.do?id="+num,
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
    function addMakeCard(title,okValue,row){//弹出修改和添加原因的窗口
        if(row>0){//说明是修改
            var col=$("#datatable").jqGrid('getRowData',row);
            $('#usercardid').val(col.usercardid);
            $('#usercardid').attr("readonly",true);
            $('#username').val(col.username);
            $('#usertype').val(col.usertype);
            $('#sex').val(col.sex);
            $('#idtype').val(col.idtype);
            $('#idcode').val(col.idcode);
            $('#state').val(col.state);
            $('#telephone').val(col.telephone);
            $('#department').val(col.department);
            $('#company').val(col.company);
            $('#subdepartment').val(col.subdepartment);
            $('#address').val(col.address);
            $('#password').val(col.password);
            $('#discountrate').val(col.discountrate);
            $('#memo').val(col.memo);
        }

        dialogCode = dialog({
            title: title,
            content: $('#showadd'),
            okValue: okValue,
            ok: function () {
                    $('#modifyCode').ajaxSubmit({
                    	error: function(){
                    		alert("操作失败");
                    	},
                        success: function (msg) {
                        	showMsg(eval("("+msg+")").msg);
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
