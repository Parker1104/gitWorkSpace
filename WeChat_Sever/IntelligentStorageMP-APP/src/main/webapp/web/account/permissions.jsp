<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>权限设置功能</title>
     <%@ include file="../../common/head.jsp"  %>
     <%@ include file="../../common/theme.jsp" %>
    <link rel="stylesheet" href="../../js/plug/zTree/css/demo.css" type="text/css">

<style>
</style>
</head>
<body>

<div class="title">
    <p>权限设置功能</p>
</div>
<div class="content">
  
	<!-- 显示权限-->
    <div class="tab">
        <table id="datatable" style="width:90%"></table>
        <div id="pager2"></div>
    </div>
</div>

<script type="text/javascript">
    $(document).ready(function(){
        var tableWidth = $("#datatable").parent().innerWidth()+25;
        $("#datatable").jqGrid({
            url:'',
            datatype: "json",
            mtype: "POST",
            width: tableWidth,
            height: 350,
            autowidth:true,
            editable: false,
            shrinkToFit:true,
            colNames:['权限名称','操作描述','',''],
            colModel:[
                {name:"moduleName",index:"moduleName",align:'center',width:100,sortable:false},
                {name:"operateDescribe",index:"operateDescribe",align:'center',width:400,sortable:false},
                {name:"operateTime",index:'operateTime',align:'left',width:100,sortable:false},
                {name:"console",index:"console",align:'center',width:100,sortable:false},
      
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
            viewrecords: true
        });

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
