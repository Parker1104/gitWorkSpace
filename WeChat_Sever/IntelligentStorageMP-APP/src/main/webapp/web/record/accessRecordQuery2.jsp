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
    <title>存取记录查询</title>
     <%@ include file="../../common/head.jsp"  %>
     <%@ include file="../../common/theme.jsp" %>

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
    <p>存取记录查询</p>
</div> -->
<div class="content">
    <%-- <form id="searchMemberCardForm" action="${basePath}/accessRecord/list" method="post">
        <div class="list">
            <ul>
                <li>柜号：<input id="terminalid" name="terminalid" type="text"/></li>
                <li>箱编号：<input id="boxid" name="boxid" type="text"/></li>
                <li>状态：<select name="state">
                    <option value="">请选择</option>
                    <option value="0">在箱</option>
                    <option value="1">已取</option>
                </select></li>                
                <!-- <li>取件类型：<select name="type">
                    <option value="">请选择</option>
                    <option value="1">中途取</option>
                    <option value="2">正常取</option>
                    <option value="3">管理取</option>
                    <option value="4">超时取</option>
                    <option value="5">远程开箱取物</option>
                </select></li> -->
                <li>用户名称：<input name="username" type="text"/></li>
            </ul>
            <ul style="float:right;margin-right:-150px;">
                <li style="margin-left: 50px;">
                <input onclick="submitForm()" value="查询" type="button" style="width: 88px; font-size: 14px; "/>
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
    <form id="searchMemberCardForm" action="${basePath}/accessRecord/list2" method="post">
        <div class="list">
            <ul>
                <li>卡号：<input id="usercardid" name="usercardid" type="text"/></li>
                <li>柜号：<input id="displayname" name="displayname" type="text"/></li>
                <li>箱编号：<input id="dispalyname" name="dispalyname" type="text"/></li>
                <li>状态：<select name="state">
                    <option value="">请选择</option>
                    <option value="0">在箱</option>
                    <option value="1">已取</option>
                </select></li>
                <li>终端号：<input name="cashierno" type="text"/></li>
                <li>起始时间：<input id="date1" class="Wdate" name="startdate" type="text" class="validate[required]"
                     onFocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"></li>
                <li>结束时间：<input id="date2" class="Wdate" name="enddate" type="text" class="validate[required]"
                     onFocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"></li>                
                <li>取件类型：<select name="type">
                    <option value="">请选择</option>
                    <option value="1">中途取</option>
                    <option value="2">正常取</option>
                    <option value="3">管理取</option>
                    <option value="4">超时取</option>
                    <option value="5">远程开箱取物</option>
                    <option value="7">卡取消</option>
                </select></li>
                <li>用户名称：<input name="username" type="text"/></li>
            </ul>   
        </div>
    </form>
</div>
<div id="showCommoditys" class="list" style="display: none">
    <div class="tab" style="width: 98%;">
        <table id="detail"></table>
    </div>
</div>
<script type="text/javascript">
    var viewGrid = null;
    $(document).ready(function(){
        var tableWidth = $("#datatable").parent().innerWidth()+25;
        $("#datatable").jqGrid({
            url:'${basePath}/accessRecord/list2',
            datatype: "json",
            mtype: "POST",
            width: document.body.offsetWidth-60,
            height:window.screen.availHeight-320,
            //autowidth:true,
            editable: false,
            //footerrow:true, //汇总
            shrinkToFit:true,
            colNames:['卡号','终端号','箱编号','柜号','箱号','存物时间','用户类型','终端号','名称',/* '证件类型','证件号','有效日期', */'实收金额','找零金额','记录状态','取物时间','取件类型','操作'],
            colModel:[
                {name:"usercardid",index:"usercardid",align:'center',width:50,sortable:false},
                {name:"terminalid",index:"terminalid",align:'center',width:50,sortable:false,hidden:true},
                {name:"boxid",index:"boxid",align:'center',width:50,sortable:false,hidden:true},
                {name:"displayname",index:"displayname",align:'center',width:50,sortable:false},
                {name:"dispalyname",index:"boxid",align:'center',width:50,sortable:false},
                {name:"storeintime",index:"storeintime",align:'center',width:100,sortable:false,formatter:function(el,options,rowData){
                    var t = rowData.storeintime;
                    var d=	new Date();
                    d.setTime(t);
                    var s=d.format('yyyy-MM-dd HH:mm:ss');
                	return s;
                }},
                {name:"usertype",index:'usertype',align:'center',width:60,sortable:false,formatter:function(el,options,rowData){
                    if(el==0){return "普通用户";}else if(el==1){return "贵宾用户";}else{return "黑名单用户";}
                }},  
                {name:"cashierno",index:"cashierno",align:'center',width:50,sortable:false,hidden:true},
                {name:"username",index:"username",align:'center',width:50,sortable:false},
               /* {name:"idtype",index:"idtype",align:'center',width:60,sortable:false,formatter:function(el,options,rowData){
                    if(el==0){return "身份证";}else if(el==1){return "驾驶证";}else{return "学生证";}
                }},
                {name:"idcode",index:"idcode",align:'left',width:90,sortable:false}, */
                /* {name:"effectivedays",index:"effectivedays",align:'left',width:90,sortable:false,formatter:function(el,options,rowData){
                    var t = rowData.effectivedays;
                    var d =	new Date();
                    d.setTime(t);
                    var s=d.format('yyyy-MM-dd HH:mm:ss');
                	return s;
                }}, */
                {name:"money",index:'money',align:'center',width:70,sortable:false },
                {name:"realmoney",index:'realmoney',align:'left',width:70,sortable:false,hidden:true},
                {name:"state",index:'state',align:'center',width:50,sortable:false,formatter:function(el,options,rowData){
                    if(el==0){return "在箱";}else{return "已取";}
                }},
                {name:"taketime",index:'taketime',align:'center',width:100,sortable:false,formatter:function(el,options,rowData){
                	if(rowData.taketime == null){
                    	return "";
                    }else{
                    	var t = rowData.taketime;
	                    var d=	new Date();
	                    d.setTime(t);
	                    var s=d.format('yyyy-MM-dd HH:mm:ss');
	                	return s;
                    }
                }},
                {name:"type",index:'type',align:'center',width:50,sortable:false,formatter:function(el,options,rowData){
                	if(rowData.type == null){
                		return "";
                	}else{
                		var t = rowData.type;
                    	if(t==1){return '中途取';}else if(t==2){return '正常取';}else if(t==3){return '管理取';}else if(t==4){return '超时取';}else if(t==7){return '卡取消';}else{return '远程开箱取物';}
                	}
                }},
                {name:"",index:'',align:'center',width:40,sortable:false,formatter:function(el,options,rowData){
                	 //var u = '[<a href="javascript:void(0)" onclick="viewDetail(\''+ rowData.terminalid +'\',\''+rowData.boxid+'\',\''+rowData.usercardid+'\')">查看</a>]';
                	 var u= '<input type="button" value="查看" onclick="viewDetail(\''+ rowData.terminalid +'\',\''+rowData.boxid+'\',\''+rowData.usercardid+'\',\''+rowData.storeintime+'\',\''+rowData.taketime+'\')" style="width:30px;float:right;margin-right:5px;""/>';
                	 /* var u= '<input type="button" value="故障锁定" onclick="FaultLocking(\''+ rowData.terminalid +'\',\''+rowData.boxid+'\',\''+rowData.usercardid+'\',\''+rowData.storeintime+'\',\''+rowData.taketime+'\')" style="width:30px;float:right;margin-right:5px;""/>'; */
                	 return u;
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
            rowList:[10,15,20],
            sortname: 'rechargenumber',
            sortorder: 'desc',
            viewrecords: true,
            toppager:true,
            /* ondblClickRow:function(rowid){
	  	    	RowData= jQuery(this).jqGrid("getRowData", rowid);
	  	    	if(RowData.type=="中途取"){
	  	    		viewDetail();            	
               }
            }, */
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
        /* var sumValue = $(this).getCol('origin',false,'sum');//统计
        $(this).footerData('set','origin',"合计："+sumValue); */
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
    var openDialog4Adding = function(title,okValue,row) {};
    var openDialog4Updating = function(row) {};
    var openDialog4Deleting = function(row) {};
    function setTheGridHeight(id){
        var total=$('#'+id).jqGrid('getDataIDs');
        var tableLength=total.length
        if(tableLength>0 && tableLength<11){
            $('#'+id).jqGrid('setGridHeight',tableLength*40);
        }else if(tableLength==0){
            $('#'+id).jqGrid('setGridHeight',40);
        }else{
            $('#'+id).jqGrid('setGridHeight',240);
        }
    }

    function toLoadInput(id,bill){
        $('#'+id+' input[name]').each(function(i,n){//为所有select选项赋值
            if(bill[n.name]!=null){
                n.value=bill[n.name];
            }
        });
    }

    function showCommodity(id,url,tableWidth,fun){//初始化商品列表,当url为空表示把选择的商品赋值到该表格
        return $("#"+id).jqGrid({
            datatype: "json",
            width: tableWidth,
            height: 260,
            editable: false,
            url:url,
            colNames:['卡号','终端号','箱编号','存物时间','应收金额','实收金额','取物时间','取件类型'],
            colModel:[
                {name:"usercardid",index:"usercardid",align:'center',width:50,sortable:false},
                {name:"terminalid",index:"terminalid",align:'center',width:50,sortable:false},
                {name:"boxid",index:"boxid",align:'center',width:50,sortable:false},
                {name:"storeintime",index:"storeintime",align:'center',width:90,sortable:false,formatter:function(el,options,rowData){
                    var t = rowData.storeintime;
                    var d=	new Date();
                    d.setTime(t);
                    var s=d.format('yyyy-MM-dd HH:mm:ss');
                	return s;
                }},
                {name:"money",index:'money',align:'center',width:70,sortable:false,hidden:true},
                {name:"realmoney",index:'realmoney',align:'left',width:70,sortable:false,formatter:function(el,options,rowData){
                    return el;
                },hidden:true},               
                {name:"taketime",index:'taketime',align:'center',width:100,sortable:false,formatter:function(el,options,rowData){
                    if(rowData.taketime != null){
	                	var t = rowData.taketime;
	                    var d=	new Date();
	                    d.setTime(t);
	                    var s=d.format('yyyy-MM-dd HH:mm:ss');
	                	return s;
                    }else{return "";}
                }},
                {name:"type",index:'type',align:'center',width:70,sortable:false,formatter:function(el,options,rowData){
                	if(rowData.type != null){
                	var t = rowData.type;
                	if(t==1){return '中途取';}else if(t==2){return '正常取';}else if(t==3){return '管理取';}else if(t==4){return '超时取';}else{return '远程开箱取物';}
                	}else{return "";}
                }},
            ],
            sortable: false,
            rownumbers:true,
            jsonReader: {
                repeatitems : false,
                id:"0"
            },
            sortorder: 'desc',
            viewrecords: true,
            loadComplete: function(data){
                if(data != undefined && data!=null ){
                    $("#"+id).jqGrid('clearGridData');
                    $("#"+id).jqGrid('addRowData',1,fun(data),'last');
                }
                setTheGridHeight(id);
            }
        });
    }
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
    function viewDetail(id,ids,number,storeintime,taketime){
        //var col=$("#datatable").jqGrid('getRowData',row);
	    var d =	new Date();
	    d.setTime(storeintime);
	    var s1 = d.format('yyyy-MM-dd HH:mm:ss');
	    var t =	new Date();
	    t.setTime(taketime);
	    var s2 = d.format('yyyy-MM-dd HH:mm:ss');
        if(viewGrid==null){
            viewGrid=showCommodity('detail','${basePath}/accessRecord/midwayList?terminalid='+id+'&boxid='+ids+'&usercardid='+number+'&memo='+s1+'&cashierNo='+s2,800,function(data){
                //toLoadInput('loss_form',data.bill);
                return data.rows;
            }).showCol('validperiod').showCol('memo');
        }else{
            viewGrid.setGridParam({
                url:'${basePath}/accessRecord/midwayList?terminalid='+id+'&&boxid='+ids+'&usercardid='+number+'&memo='+s1+'&cashierNo='+s2
            }).trigger('reloadGrid');
        }
        openWin('中途取物记录','showCommoditys',850);
    }
    function openWin(title,id,width){
        var dia=dialog({
            title:title,
            content:$('#'+id),
            cancelValue: '关闭',
            cancel: function () {
                // return setFormStores();
            }
        });
        dia.width(width);
        dia.height(350);
        dia.showModal();
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
