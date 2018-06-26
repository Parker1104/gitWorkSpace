<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
    request.setAttribute("basePath",basePath);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>远程设备控制</title>
<%@ include file="../../common/head.jsp"%>
<%@ include file="../../common/theme.jsp"%>
</head>
<body>
	<div class="content">	   
			<ul id="myTab1">
			  <li><input style="font-size:14px;" id="tab" type="button" value="远程开箱" onclick="openBox()"/></li>
			  <li><input style="font-size:14px;" id="tabb" type="button" value="远程清箱" onclick="clearBox()"/></li>
			  <li><input style="font-size:14px;" id="tabb3" type="button" value="远程锁定" onclick="lockBox()"/></li>
			  <li><input style="font-size:14px;" id="tabb4" type="button" value="远程解锁" onclick="unlockBox()"/></li>
			</ul>
			<form id="searchMemberCardForm" action="${basePath}/remoteDevice/list" method="post">
			 <div class="list" style="float:left;margin-top:30px;">
              <ul>
                <li>柜号:<input type="text" name="displayname"/></li>
                <li>箱编号:<input type="text" name="boxid" style="width: 90px;"/>-<input type="text" name="businesscode" style="width: 90px;"/>
                <input onclick="submitForm()" value="查询" type="button" style="width: 70px;float:right;margin-right:-30%;"/>
                <!-- <input id="kx" type="button" value="开箱" onclick="" style="width: 70px;float:right;margin-right:-180px;"/> -->
                </li>
              </ul>
             </div>
            </form>
		<div id="myTabContent2">
			<div id="tab_content11" class="tab" style="margin-top:10px;">
				<table id="datatable3"></table>
                <div id="pager4"></div>
			</div>
			<!-- <div class="tab" id="tab_content22"
				style="display: none;margin-top:10px;">
				<table id="datatable"></table>
                <div id="pager2"></div>
			</div>
			<div class="tab" id="tab_content33"
				style="display: none;margin-top:10px;">
				<table id="datatable2"></table>
                <div id="pager3"></div>
			</div> -->
		</div>
	</div>
	<div id="showadd" class="list" style="display: none">
    <form id="modifyCode" action="${ctx}/announcement/addType" method="post">
        <ul>
            <li>规则名称：<input style="width:200px;" id="name" name="name"/></li>
            <li>卡号类型：<select>
               <option>无</option>
               <option>IC卡</option>
               <option>ID卡</option>
            </select></li>
        </ul>
    </form>
    </div>
</body>
<script type="text/javascript">
function submitForm(){
    $("#datatable3").jqGrid('setGridParam',{
        postData:formToJson("searchMemberCardForm"),
        page:1
    }).trigger("reloadGrid");
}
function formToJson(id){//把form表单的所有数据变成json对象
    var formArray=$('#'+id).serializeArray();//数组里面首先放入记录表的内容
    var formJson=new Object();
    $.each(formArray,function(i,n){//把formarray转换为json
        if((n.name!=undefined&&n.name!="")){
            formJson[n.name] = n.value;
        }
    });
    return formJson;
}
/*
	$("#tabb").click(function() {
		$('#tab_content22').show();
		$('#tab_content11').hide();
		$('#kx').hide();
		$('#tab_content33').hide();
	});
	$("#tabb3").click(function() {
		$('#tab_content33').show();
		$('#tab_content11').hide();
		$('#kx').hide();
		$('#tab_content22').hide();
	});
	$("#tab").click(function() {
		$('#tab_content11').show();
		$('#kx').show();
		$('#tab_content33').hide();
		$('#tab_content22').hide();
	});
	$(document).ready(function(){
        var tableWidth = $("#datatable").parent().innerWidth();
        $("#datatable").jqGrid({
            url:'${basePath}/remoteDevice/list',
            datatype: "json",
            mtype: "POST",
            width: tableWidth,
            height: 350,
            autowidth:true,
            editable: true,
            shrinkToFit:true,
            colNames:['箱编号','柜号','箱号','操作'],//清箱
            colModel:[
                {name:"boxid",index:"boxid",align:'center',width:220,sortable:false},
                {name:"displayname",index:'displayname',align:'left',width:220,sortable:false},
                {name:"dispalyname",index:"dispalyname",align:'center',width:220,sortable:false},
                {name:"",index:'',align:'center',width:100,formatter:function(el,options,rowData){
                        var d= '[<a href="javascript:void(0)" onclick="delRepository(\''+rowData.id+'\')">清箱</a>] ';                        
                        return d;
                }}
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
	 $(document).ready(function(){
        var tableWidth = $("#datatable2").parent().innerWidth();
        $("#datatable2").jqGrid({
            url:'${basePath}/remoteDevice/list',
            datatype: "json",
            mtype: "POST",
            width: tableWidth,
            height: 350,
            autowidth:true,
            editable: false,
            shrinkToFit:true,
            colNames:['箱编号','柜号','箱号','操作状态','操作'],//锁定
            colModel:[
                {name:"boxid",index:"boxid",align:'center',width:220,sortable:false},
                {name:"displayname",index:'displayname',align:'left',width:220,sortable:false},
                {name:"dispalyname",index:"dispalyname",align:'center',width:220,sortable:false},
                {name:"status",index:"status",align:'center',width:120,sortable:false,formatter:function(el,options,rowData){
                	var s = rowData.status;
                	if(s==0){return '正常';}else if(s==1){return '锁定';}else{return '故障';}
                }},
                {name:"",index:'',align:'center',width:70,formatter:function(el,options,rowData){
                        var d= '[<a href="javascript:void(0)" onclick="delRepository(\''+rowData.id+'\')">锁定</a>] ';
                        return d;
                }}
            ],
            sortable: false,
            rowNum:10,
            rownumbers:false,
            multiselect: true,
            jsonReader: {
                repeatitems : false,
                id:"0"
            },
            pager: '#pager3',
            rowList:[10,15,20],
            sortname: 'rechargenumber',
            sortorder: 'desc',
            viewrecords: true
        });
    }); */
	$(document).ready(function(){
        //var tableWidth = $("#datatable3").parent().innerWidth();
        $("#datatable3").jqGrid({
            url:'${basePath}/remoteDevice/list',
            datatype: "json",
            mtype: "POST",
            width: document.body.offsetWidth-60,
            height: 350,
            //autowidth:true,
            editable: true,
            shrinkToFit:true,
            colNames:['','箱编号','柜号','箱号',"状态",'操作'],//开箱 
            colModel:[
                {name:"terminalid",index:"terminalid",align:'center',width:140,sortable:false,hidden:true},
                {name:"boxid",index:"boxid",align:'center',width:140,sortable:false},
                {name:"displayname",index:'displayname',align:'left',width:100,sortable:false},
                {name:"dispalyname",index:"dispalyname",align:'center',width:100,sortable:false},
                {name:"open",index:"open",align:'center',width:50,sortable:false,formatter:function(el,options,rowData){
                	if(el==1){return '<img src="${basePath}/images/open.png"/>';}
                	else{return '<img src="${basePath}/images/door.png"/>';}              	
                }},
                {name:"",index:'',align:'center',width:70,formatter:function(el,options,rowData){
                        //var d= '[<a href="javascript:void(0)" onclick="open(\''+rowData.boxid+'\')">开箱</a>] ';
                        var d= '<input type="button" value="开箱" onclick="openBox()" style="width:30px;float:right;margin-right:70px;"/>';
                        return d;
                }}
            ],
            sortable: false,
            rowNum:10,
            rownumbers:false,
            multiselect: true,
            jsonReader: {
                repeatitems : false,
                id:"0"
            },
            pager: '#pager4',
            rowList:[10,15,20],
            sortname: 'rechargenumber',
            sortorder: 'desc',
            viewrecords: true
        });
    });
	function openBox(){
	  //var col=$("#datatable3").jqGrid('getRowData',row);
		var rows=$("#datatable3").jqGrid('getGridParam','selarrrow');
		if(rows.length>0){
        var d = dialog({
            title: '提示',
            content: '您确认想要开这'+rows.length+'个箱吗？',
            okValue: '确定',
            ok: function () {
                this.title('提交中…');              
                for(var i = 0;i<rows.length;i++){
                	var col=$("#datatable3").jqGrid('getRowData',rows[i]);
                $.ajax({
                    type: "POST",
                    url: "${basePath}/remoteDevice/openBox?terId="+col.terminalid+"&boxId="+col.boxid,
                    success: function(msg){
                    	showMsg(eval("("+msg+")").msg);
                    	submitForm();
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
		}else{showMsg("没有选中数据！");}
    }
	function clearBox(){
		  //var col=$("#datatable3").jqGrid('getRowData',row);
			var rows=$("#datatable3").jqGrid('getGridParam','selarrrow');
			if(rows.length>0){
	        var d = dialog({
	            title: '提示',
	            content: '您确认想要清箱吗？',
	            okValue: '确定',
	            ok: function () {
	                this.title('提交中…');              
	                for(var i = 0;i<rows.length;i++){
	                	var col=$("#datatable3").jqGrid('getRowData',rows[i]);
	                $.ajax({
	                    type: "POST",
	                    url: "${basePath}/remoteDevice/clearBox?terId="+col.terminalid+"&boxId="+col.boxid,
	                    success: function(msg){
	                    	showMsg(eval("("+msg+")").msg);
	                    	submitForm();
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
			}else{showMsg("没有选中数据！");}
	    }
	function lockBox(){
		var rows=$("#datatable3").jqGrid('getGridParam','selarrrow');
		if(rows.length>0){
        var d = dialog({
            title: '提示',
            content: '您确认想要锁定吗？',
            okValue: '确定',
            ok: function () {
                this.title('提交中…');
                for(var i=0;i<rows.length;i++){
                	var col=$("#datatable3").jqGrid('getRowData',rows[i]);
                $.ajax({
                    type: "POST",
                    url: "${basePath}/remoteDevice/lockBox?terId="+col.terminalid+"&boxId="+col.boxid,
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
		}else{showMsg("没有选中数据！");}
    }
	function unlockBox(){
		var rows=$("#datatable3").jqGrid('getGridParam','selarrrow');
		if(rows.length>0){
        var d = dialog({
            title: '提示',
            content: '您确认想要解除锁定吗？',
            okValue: '确定',
            ok: function () {
                this.title('提交中…');
                for(var i=0;i<rows.length;i++){
                	var col=$("#datatable3").jqGrid('getRowData',rows[i]);
                $.ajax({
                    type: "POST",
                    url: "${basePath}/remoteDevice/unlockBox?terId="+col.terminalid+"&boxId="+col.boxid,
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
		}else{showMsg("没有选中数据！");}
    }
	var dialogCode=null;
    function addMakeCard(title,okValue,row){//弹出修改和添加原因的窗口
        if(row>0){//说明是修改
            var col=$("#datatable").jqGrid('getRowData',row);
            $('#name').val(col.name);
            $('#id').val(col.id);
            $('#isenable').val(col.isenable);
        }
        dialogCode = dialog({
            title: title,
            content: $('#showadd'),
            okValue: okValue,
            ok: function () {
                    $('#modifyCode').ajaxSubmit({
                        success: function (msg) {
                            showMsg(msg.msg);
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
</html>