<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path;
	request.setAttribute("basePath", basePath);
%>
<head>
    <title>收银统计</title>
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
    td#datatable_toppager_center {display: none;}
</style>
</head>
<body style="overflow-x: hidden">
<!-- <div class="title">
    <p>收银统计</p>
</div> -->
	<div id="showsearch" class="list" style="display: none">
    <form action="${basePath}/cashierStatisticalController/list" id="searchMemberCardForm" method="post">
			<div class="list">
				<ul>
					<li>收银员：<input name="accountname" id="accountname" type="text" /></li>
					<li>起始时间：<input id="date1" class="Wdate" name="startdate" type="text" class="validate[required]"
                     onFocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"></li>
                    <li>结束时间：<input id="date2" class="Wdate" name="enddate" type="text" class="validate[required]"
                     onFocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"></li>
					<li>终端号：<input name="cashierno" id="cashierno" type="text" /></li>
				</ul>
			</div>
		</form>
</div>
 <div id="div_list" class="tab">
        <table id="datatable"></table>
        <div id="pager2"></div>
 </div>
 <div id ="foot" style="width:91%; margin-top: 10px;">
       <h5 id="h5height" style=" width: 105%; height: 30px; border: 1px solid #cdedff; background: #fff; border-top: none; line-height: 30px; text-align: left; float:left; margin-left: 2.5%; ">
            <span>合计</span>
            <span>总收费额：<label id="money">${money}</label></span>
            <span>总退费额：<label id="realmoney">${realmoney}</label></span>
            <span>总结余额：<label id="balance">${balance}</label></span>
       </h5>
</div>
<script type="text/javascript">
/* 查询 当摁下 enter键后执行 button 点击事件 */
document.onkeydown = function(event) {
	var e = event || window.event
			|| arguments.callee.caller.arguments[0];
	if (e && e.keyCode == 13) { // enter 键
		if (document.activeElement.id == " ") {
			$(".ui-dialog-autofocus").trigger("click");
			return false;
		}
	}
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
    		$("#datatable").setGridWidth($(window).width() - 50);
    	});
        var tableWidth = $("#datatable").parent().innerWidth();
        $("#datatable").jqGrid({
            url:'${basePath}/cashierStatisticalController/list',
            datatype: "json",
            mtype: "POST",
			width : document.body.offsetWidth - 60,
			height : window.screen.availHeight - 400,
            //autowidth:true,
            editable: false,
            shrinkToFit:true,
            colNames:['','收银员','收银终端号','时间','实收额','押金','实退额','退费额','结余金额','备注'],
            colModel:[
                {name:"makeopcode",index:'makeopcode',align:'center',width:200,sortable:false,hidden:true},
                {name:"accountname",index:'accountname',align:'center',width:200,sortable:false},              
                {name:"cashierno",index:"cashierno",align:'center',width:200,sortable:false},
                {name:"storeintime",index:"storeintime",align:'center',width:200,sortable:false,formatter:function(el,options,rowData){
                    var t = rowData.storeintime;
                    var d =	new Date();
                    d.setTime(t);
                    var s=d.format('yyyy-MM-dd');
                	return s;
                }},
                {name:"money",index:"money",align:'center',width:200,sortable:false},//实收额
                {name:"realmoney",index:'realmoney',align:'center',width:200,sortable:false},//押金
                {name:"takemoney",index:"takemoney",align:'center',width:200,sortable:false},//应退额
                {name:"takerealmoney",index:"takerealmoney",align:'center',width:200,hidden:true,sortable:false,formatter:function(el,options,rowData){
                    var a = rowData.money-rowData.takemoney;
                    return a;
                }},               
                {name:"balance",index:'balance',align:'center',width:200,sortable:false,formatter:function(el,options,rowData){
                    var a = rowData.money+rowData.takemoney;
                    return a;
                }},
                {name:"memo",index:'memo',align:'center',width:200,sortable:false}
                
            ],
            sortable: false,
            rowNum:10,
            rownumbers:true,
            multiselect: false,
            multiboxonly:true,	
            jsonReader: {
                repeatitems : false,
                id:"0"
            },
            pager: '#pager2',
            rowList:[10,15,20],
            sortname: 'usercardid',
            sortorder: 'desc',
            toppager : true,
            viewrecords: true,           
            loadComplete:function(){
				var myGrid = $("#datatable");
				$("#cb_"+myGrid[0].id).hide();
			},
            ondblClickRow:function(rowid){
	  	    	RowData= jQuery(this).jqGrid("getRowData", rowid);
	  	    	if(RowData.rechargenumber){
	  	    		selectMakeCard("查看",rowid);            	
               }
            },
        });
		$("#datatable").jqGrid("navGrid", "#pager2", {
			cloneToTop : true,
			add : false,
			del : false,
			edit : false,
			searchfunc : selectMakeCard, // (1) 点击添加按钮  查询 
			searchtext : "查询", 
			alerttext : "请选择需要操作的数据行!" // (2) 当未选中任何行而点击编辑、删除、查看按钮时，弹出的提示信息  
		});
		$("#datatable").navButtonAdd('#pager2',{
        	cloneToTop:true,
        	caption:"导出Excel", 
        	buttonicon:"ui-icon-excel", 
        	onClickButton: function(){
        		alert("导出excel");
        		var title = "属性"; 
        		getXlsFromTbl('datatable', 'div_list', title, true);
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
   var dialogCode=null;
   var selectMakeCard =  function(row){
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
       dialogCode.width(350);
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

