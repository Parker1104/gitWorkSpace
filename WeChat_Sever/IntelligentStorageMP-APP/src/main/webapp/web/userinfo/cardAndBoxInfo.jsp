<%@ page language="java" contentType="text/html; charset=UTF-8"
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
    <title>柜属性设置</title>
     <%@ include file="../../common/head.jsp"  %>
     <%@ include file="../../common/theme.jsp" %>
    <link rel="stylesheet" href="${basePath}/js/plug/zTree/css/demo.css" type="text/css">

    <style>
    .list .btn_another input { width: 88px !important; height: 24px !important; border-radius: 3px; margin-bottom: 10px;  background: #74a3dc; border: none; cursor: pointer; font-family: "微软雅黑"; color: #fff; font-size: 14px;  }
    .list .btn_another input:hover { background-color: #189ad8; }
    .confirm { width: 100% !important; float:left; }
    .confirm input { margin-left: 40px;}
    .list li { height: 35px;}
    .bottom-input { width: 70%; float: right; margin-top: 10px;}
    .bottom-input li { width: 320px; float: right; text-align: right;}
    .bottom-input li input { width: 200px; height: 24px; border: 1px solid #ccc; border-radius: 3px;}
    .fm-button {padding: .0em 1.4em .2em 1.3em;}
    td#datatable_toppager_center {
    display: none;
}
</style>
</head>
<body>
<div id="div_list" class="tab">
        <table id="datatable"></table>
        <div id="pager2"></div>
</div>
<div id="showsearch" class="list" style="display: none">
    <form id="searchMemberCardForm" action="${basePath}/cardAndBoxInfo/list" method="get">
        <div class="list">
            <ul>
                <li>工号：<input name="idcode" type="text"/></li> 
                <li>卡号：<input name="usercardid" type="text"/></li>
                <li>姓名：<input name="username" type="text"/></li>                                      
            </ul>
        </div>
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
         $(window).resize(function(){   
              $("#datatable").setGridWidth($(window).width()-50);
         });
        var tableWidth = $("#datatable").parent().innerWidth();
        $("#datatable").jqGrid({
            url:'${basePath}/cardAndBoxInfo/list',
            datatype: "json",
            mtype: "POST",
            width: document.body.offsetWidth-60,
            height: window.screen.availHeight-320,
            //autowidth:true,
            editable: true,
            shrinkToFit:true,
            colNames:['工号','卡号','姓名','柜号','箱号'],
            colModel:[
                {name:"idcode",index:"idcode",align:'center',width:70,sortable:false,editable:true},
                {name:"usercardid",index:"usercardid",align:'center',width:70,sortable:false,editable:true},
                {name:"username",index:"username",align:'center',width:70,sortable:false,editable:true},
                {name:"displayname",index:'displayname',align:'center',width:50,sortable:false,editable:true},
                {name:"boxid",index:'boxid',align:'center',width:50,sortable:false,editable:true},
            ],
            sortable: false,
            rowNum:10,
            rownumbers:false,
            multiselect: false,
            jsonReader: {
                repeatitems : false,
                id:"0"
            },
            pager: '#pager2',
            rowList:[10,15,20],
            toppager:true,
            sortname: 'terminalid',
            sortorder: 'desc',
            viewrecords: true,
            multiboxonly:true,                     
        });
        $("#datatable").jqGrid("navGrid", "#pager2", { 
        	cloneToTop:true,
        	add:false,
        	edit:false,
        	del:false,
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
        		var title = "卡箱对应信息"; 
        		getXlsFromTbl('datatable', 'div_list', title, false);
        	}, 
        	position:"last" 
        });
        /* $("#datatable").jqGrid('inlineNav', "#pager2"); */    
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
    var openDialog4Adding = function(title,okValue,row) {  
       
    };
    var openDialog4Searching = function(title,okValue,row) {
    	dialogCode = dialog({
            title: '查询',
            content: $('#showsearch'),
            okValue: '查询',
            ok: function () {
            	submitForm();
            },
            cancelValue: '关闭',
            cancel: function () {
            	$('#searchMemberCardForm').clearForm(true);
            }
        });
        dialogCode.width(600);
        dialogCode.showModal();
    };
    var openDialog4Updating = function(row) {
    	
    };
    var openDialog4Deleting = function(row) { // (8) 接受选中行的id为参数  
    	
    };           
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

