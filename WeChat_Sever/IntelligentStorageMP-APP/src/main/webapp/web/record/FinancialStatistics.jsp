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
    <title>财务统计</title>
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
	.button{
		color:white;
		border:1px solid #000000 ; 
		background-color:#A19F4F;
	}
	
	.button_delete{
		color:white;
		border:1px solid #000000 ; 
		background-color:red;
	}
</style>
</head>
<body onload="terminalQuery()">
	<div class="content">
	    <div class="tab">
	        <table id="datatable" style="width:90%"></table>
	        <div id="pager2"></div>
	    </div>
	</div>
	<div id="showsearch" class="list" style="display: none">
	    <form id="searchMemberCardForm"  method="post">
	        <div class="list">
	            <ul>
	                <li>柜号：
		                <select name="terminalId" id="terminalId">
		                <option value="26"  >查询所有柜子</option>
	                	</select>
                	</li>
	                <li>开始时间：<input id="date1" class="Wdate" name="startTime" type="text" class="validate[required]"
	                     onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"></li>
	                <li>结束时间：<input id="date2" class="Wdate" name="overTime" type="text" class="validate[required]"
	                     onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"></li>
	            </ul>   
	        </div>
	    </form>
	</div>
	<script type="text/javascript">
	
	
	//获取设备号
	function terminalQuery(){
		  var i =0;
		  $.ajax({
	         type: "POST",
	         url: "${pageContext.request.contextPath}/financialStatistics/terminalQuery",
	         success: function(res){
	         	var obj = JSON.parse(res);
	         	/* var reg = /[^:]*:([^:]*)/;
	         	var temp = "";  */
	         	var termianlNameArr = new Array();
	         	var termianlNoArr = new Array();
	         	var j = 0;
	         	function sortNum(a,b){
	         		return a.terminalName - b.terminalName;
	         	}
	         	console.log(obj.sort(sortNum));
	         	
	         	for(var h=0;h<obj.length;h++){
	         		var val = obj[h];
	         		console.log(val);
	         		$("#terminalId").append("<option value='"+val.terminalNo+"'>"+val.terminalName+"号柜"+"</option>"); 
	         	} 
	         }
		  });
	}
	
    var viewGrid = null;
    $(document).ready(function(){
    	var url = null;
        var tableWidth = $("#datatable").parent().innerWidth()+25;
        $("#datatable").jqGrid({
            url:'${pageContext.request.contextPath}/financialStatistics/financialRecord',
            datatype: "json",
            mtype: "POST",
            width: document.body.offsetWidth-60,
            height:window.screen.availHeight-320,
            editable: false,
            shrinkToFit:true,
            colNames:['柜号','开始时间','结束时间','微信实收','投币机实收','总金额'],
            colModel:[
                {name:"terminalId",index:"terminalno",align:'center',width:100,sortable:false,},
                {name:"startTime",index:"startTime",align:'center',width:100,sortable:false,},
                {name:"overTime",index:"overTime",align:'center',width:100,sortable:false,},
                {name:"wxMoney",index:"wxMoney",align:'center',width:100,sortable:false,},
                {name:"coinMoney",index:"coinMoney",align:'center',width:100,sortable:false,},
                {name:"countMoney",index:"countMoney",align:'center',width:100,sortable:false,},
            ],
            sortable: false,
            rowNum:20,
            rownumbers:false,
            multiselect: false,
            multiboxonly:false,  
            beforeSelectRow: beforeSelectRow,//js方法
            jsonReader: {
                repeatitems : false,
                id:"0"
            },
            pager: '#pager2',
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
    var openDialog4Searching = function(title,okValue,row) {
    	
    	dialogCode = dialog({
            title: '查询',
            content: $('#showsearch'),
            okValue: '查询',
            ok: function () {
            	var current_time = $('#date1').val();
            	var stop_time = $('#date2').val();
				if(current_time==null||current_time==""||stop_time==null||stop_time==""){
					showMsg("查询日期不能为空！");
				}else{
	            	var a = new Date(current_time);
	                var b = new Date(stop_time);
	                var   adate=a.getTime() ;
	                var   bdate=b.getTime() ;
	                var ab=(bdate-adate)/1000;
	                console.log(ab);
	            	if(!CompareDate(current_time,stop_time)){
	            		if(ab>31*24*60*60){
	            			showMsg("查询期限不能超过31天！");
	            			return false;
	            		}else{
	            			submitForm();
	            		}
	            	}else{
	            		showMsg("查询日期不合法，结束时间不能小于开始时间！");
	            		return false;
	            	}
            	}
            },
            cancelValue: '关闭',
            cancel: function () {
            	$('#searchMemberCardForm').clearForm(true);
            }
        });
    	dialogCode.width(600);
        dialogCode.showModal();
    };

    function toLoadInput(id,bill){
        $('#'+id+' input[name]').each(function(i,n){//为所有select选项赋值
            if(bill[n.name]!=null){
                n.value=bill[n.name];
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
        var formArray=$('#'+id).serializeArray();//数组里面首先放入内容
        var formJson=new Object();
        $.each(formArray,function(i,n){//把formarray转换为json
            if((n.name!=undefined&&n.name!="")){
                formJson[n.name] = n.value;	
            }
        });
        return formJson;
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
    
    function beforeSelectRow()  
    {  
        $("#datatable").jqGrid('resetSelection');  
        return(true);  
    }  

    function CompareDate(d1,d2)
    {
      return ((new Date(d1.replace(/-/g,"\/"))) > (new Date(d2.replace(/-/g,"\/"))));
    }

    
</script>
</body>
</html>
