<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
    request.setAttribute("basePath",basePath);
%>
<!DOCTYPE HTML>
<html>
<head>
    <title>箱属性设置</title>
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
    <p>查询条件</p>
</div> -->
<div class="content">
    <%-- <form id="searchMemberCardForm" action="${pageContext.request.contextPath}/boxController/list" method="post">
        <div class="list">
            <ul>
                <li>柜号：<input name="displayname"/></li>
                <li>箱编号：<input name="boxid"/></li>
                <li>箱类型：<select name="boxtypecode">
                      <option value="">请选择</option>
                        <c:forEach var ="b" items="${box}">
                            <option value="${b.boxtypecode}" selected>${b.boxtypename}</option>
                        </c:forEach>
                </select></li>
                <li>箱门状态：<select name="status">
                    <option value="">请选择</option>
                    <option value="0">正常</option>
                    <option value="1">锁定</option>
                    <option value="2">故障</option>
                </select></li>
                <li>物品状态：<select name="article">
                    <option value="">请选择</option>
                    <option value="0">正常</option>
                    <option value="1">锁定</option>
                    <option value="2">故障</option>
                </select></li>
            </ul>
            <ul style="float:right;margin-right:-150px;">   
                <li style="margin-left: 50px;">
                <input onclick="submitForm()" value="查询" type="button" style="width: 88px; font-size: 14px; "/>
                </li>
            </ul>
        </div>
    </form> --%>
    <div class="tab">
        <table id="datatable"></table>
        <div id="pager2"></div>
    </div>
</div>
<div id="showsearch" class="list" style="display: none">
    <form id="searchMemberCardForm" action="${pageContext.request.contextPath}/boxController/list" method="post">
        <div class="list">
            <ul>
                <li>柜号：<input name="displayname"/></li>
                <li>箱编号：<input name="boxid"/></li>
                <li>箱类型：<select name="boxtypecode">
                      <option value="" selected="selected">请选择</option>
                        <c:forEach var ="b" items="${box}">
                            <option value="${b.boxtypecode}" >${b.boxtypename}</option>
                        </c:forEach>
                </select></li>
                <li>箱门状态：<select name="status">
                    <option value="" selected="selected">请选择</option>
                    <option value="0">正常</option>
                    <option value="1">锁定</option>
                    <option value="2">故障</option>
                </select></li>
                <li>物品状态：<select name="article">
                    <option value="">请选择</option>
                    <option value="0">正常</option>
                    <option value="1">锁定</option>
                    <option value="2">故障</option>
                </select></li>
            </ul>
        </div>
    </form>
</div>
<div id="showadd" class="list" style="display: none">
    <form id="modifyCode" action="${pageContext.request.contextPath}/boxController/insert" method="post" >
        <ul>
            <li>设备号：<select id="terminalid" name="terminalid">
                      <option value="">请选择</option>
                        <c:forEach var ="b" items="${terminal}">
                            <option value="${b.terminalid}" selected>${b.terminalid}</option>
                        </c:forEach>
            </select></li>
            <li>箱编号：<input id="boxid" name="boxid" type="number" maxlength="11"/></li>
            <li>箱类型：<select id="boxtypecode" name="boxtypecode">
                      <option value="">请选择</option>
                        <c:forEach var ="b" items="${box}">
                            <option value="${b.boxtypecode}" selected>${b.boxtypename}</option>
                        </c:forEach>
            </select></li>
            <li>箱门名称：<input id="dispalyname" name="dispalyname" maxlength="10"/></li>
            <li style="float:right;margin-right:360px;">一箱多卡：<input id="yes" name="oneboxmorecard" type="radio" value=1 style="height:16px;width:30px;" />是<input id="no" name="oneboxmorecard" type="radio" value=0 style="height:16px;width:30px;" checked="checked" />否</li>
            <li>固定箱门：<select id="fixedbox" name="fixedbox">
                      <option value="0">不限制</option>
                      <option value="1">一箱一卡</option>
                      <option value="2">其它</option>
            </select></li> 
        </ul>
    </form>
</div>
<div id="showupdate" class="list" style="display: none">
    <form id="modifyCodeupdate" action="${pageContext.request.contextPath}/boxController/update" method="post">
        <ul>
            <li>设备号：<select id="terminal" name="terminalid">
                      <option value="">请选择</option>
                        <c:forEach var ="b" items="${terminal}">
                            <option value="${b.terminalid}" selected>${b.displayname}</option>
                        </c:forEach>
            </select></li>           
            <li>箱类型：<select id="boxtypecode" name="boxtypecode">
                      <option value="">请选择</option>
                        <c:forEach var ="b" items="${box}">
                            <option value="${b.boxtypecode}" selected>${b.boxtypename}</option>
                        </c:forEach>
            </select></li>           
            <li style="float:right;margin-right:360px;">一箱多卡：<input id="yes" name="oneboxmorecard" type="radio" value=1 style="height:16px;width:30px;" />是<input id="no" name="oneboxmorecard" type="radio" value=0 style="height:16px;width:30px;" checked="checked" />否</li>
            <li>固定箱门：<select id="fixedbox" name="fixedbox">
                      <option value="0">不限制</option>
                      <option value="1">一箱一卡</option>
                      <option value="2">其它</option>
            </select></li> 
        </ul>
    </form>
</div>

<script type="text/javascript">
$(document).ready(function () {	  
	  $("#terminal").select2();	 
});
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
    	var a = '${account.areacode}';
        var tableWidth = $("#datatable").parent().innerWidth();
        $("#datatable").jqGrid({
            url:'${pageContext.request.contextPath}/boxController/list?areacode='+a,
            datatype: "json",
            mtype: "POST",
            width: document.body.offsetWidth-60,
            height: window.screen.availHeight-320,
            //autowidth:true,
            editable: true,
            shrinkToFit:true,
            colNames:['设备号 ','柜号','箱号','箱门名称','箱类型编码','箱类型名称','一箱多卡','固定箱门','物品状态','开关状态','箱门状态',],
            colModel:[
                {name:"terminalid",index:"terminalid",align:'center',width:90,sortable:false,hidden:true},
                {name:"terminalEntity.displayname",index:"displayname",align:'center',width:90,sortable:false},
                {name:"boxid",index:'boxid',align:'left',width:100,sortable:false},
                {name:"dispalyname",index:"dispalyname",frozen:true,align:'left',width:90,sortable:false},
                {name:"boxtypecode",index:"boxtypecode",align:'center',width:50,sortable:false,hidden:true},
                {name:"boxSizeEntity.boxtypename",index:"boxtypename",align:'center',width:50,sortable:false,hidden:false},              
                {name:"oneboxmorecard",index:"oneboxmorecard",align:'center',width:90,sortable:false,formatter:function(el,options,rowData){
                    if(el==1){return '是';}else{return '否';}
                }},
                {name:"fixedbox",index:'fixedbox',align:'center',width:80,sortable:false,formatter:function(el,options,rowData){
                    if(el==0){return '不限制';}else if(el==1){return '一箱一卡';}else{return '其他限制';}
                }},
                {name:"article",index:'article',align:'center',width:70,sortable:false,formatter:function(el,options,rowData){
                    if(el==0){return "无物";}else if(el==1){return "有物";}else{return "有物";}
                }},
                {name:"open",index:'open',align:'center',width:100,sortable:false,formatter:function(el,options,rowData){
                	if(el==0){return "开启";}else if(el==1){return "关闭";}else{return "故障";}
                }},
                {name:"status",index:"status",align:'center',width:90,sortable:false,formatter:function(el,options,rowData){
                    if(el==0){return "正常";}else if(el==1){return "锁定";}else{return "故障";}
                }},
                /* {name:"",index:'',align:'center',width:70,formatter:function(el,options,rowData){
                        var d= '<input type="button" value="删除" onclick="delRepository(\''+rowData.terminalid+'\',\''+rowData.boxid+'\')" style="width:30px;float:right;margin-right:5px;"/>';
                        var u= '<input type="button" value="修改" onclick="addMakeCard(\'修改类型\',\'修改\','+ options.rowId +')" style="width:30px;float:right;margin-right:10px;"/>';
                        return u+d;
                }} */
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
	  	    	if(RowData.terminalid){
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
            edittext:"修改",
            delfunc : openDialog4Deleting,  // (3) 点击添加按钮，则调用openDialog4Deleting方法  
            deltext:"删除",
            alerttext : "请选择需要操作的数据行!"   // (4) 当未选中任何行而点击编辑、删除、查看按钮时，弹出的提示信息  
        });
        $("#datatable").navButtonAdd('#pager2',{
					cloneToTop : true,
					caption : "批量修改",
					buttonicon : "ui-icon-pencil",
					onClickButton : function() {						
					   addMakeCard("修改","确定");
					},
					position : "last"
				});
    });
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
    	$('#no').attr('checked',true);
        dialogCode = dialog({
            title: '新增',
            content: $('#showadd'),
            okValue: '新增',
            ok: function () {
            	var boxid = document.getElementById("boxid").value;
            	var dispalyname = document.getElementById("dispalyname").value;
            	if(boxid=="" || boxid==null){
            		showMsg("箱编号不能为空");
            		return false;
            	}else if(dispalyname=="" || dispalyname==null){
            		showMsg("箱门名称不能为空");
            		return false;
            	}else{
                  $('#modifyCode').ajaxSubmit({
                       success: function (msg) {
                    	    showMsg(eval("("+msg+")").msg);
                            submitForm();
                            $('#modifyCode').clearForm(true);
                         },
                     });
                 return true;
            	}
             },
            cancelValue: '关闭',
            cancel: function () {
                $('#modifyCode').clearForm(true);
            }
        });
        dialogCode.width(600);
        dialogCode.showModal();
    };  
    var openDialog4Updating = function openDialog4Updating(row) { // (6) 接受选中行的id为参数  
    	var ids = $("#datatable").jqGrid("getGridParam", "selarrrow");
        if(ids.length>1){
        	showMsg("请选择一行数据");
        }else{
    	if(row>0){//说明是修改
            var col=$("#datatable").jqGrid('getRowData',row);
            $('#id').val(col.id);
            $('#terminalid').attr("value",col.terminalid);
            $("#terminalid").attr("disabled","disabled");
            $('#boxid').val(col.boxid);
            $("#boxid").attr("disabled","disabled");
            $('#boxtypecode').val(col.boxtypecode);
            $('#dispalyname').val(col.dispalyname);
            var a = col.oneboxmorecard;
            if(a==1){
            	$('#yes').attr('checked',true);
            }else{
            	$('#no').attr('checked',true);
            }
            var f = col.fixedbox;
            if(f=="不限制"){
            	$('#fixedbox').val(0);
            }else if(f=="一箱一卡"){
            	$('#fixedbox').val(1);
            }else{
            	$('#fixedbox').val(2);
            }
        }

        dialogCode = dialog({
            title: "修改",
            content: $('#showadd'),
            okValue: "确定",
            ok: function () {
                    $('#modifyCode').ajaxSubmit({
                        success: function (msg) {
                        	showMsg(eval("("+msg+")").msg);
                            submitForm();
                            $('#modifyCode').clearForm(true);
                            $('input,select,textarea',$('form[id="modifyCode"]')).removeAttr("readonly");
                            $('input,select,textarea',$('form[id="modifyCode"]')).removeAttr("disabled");
                        }
                    });
                   return true;
            },
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
    }; 
    var openDialog4Deleting = function(row) { // (8) 接受选中行的id为参数
    	var ids = $("#datatable").jqGrid("getGridParam", "selarrrow");
    	//var col=$("#datatable").jqGrid('getRowData',row);
    	var d = dialog({
            title: '提示',
            content: '您确认想要删除吗？',
            okValue: '确定',
            ok: function () {
                this.title('提交中…');
                if(ids.length>0){
                 for(var i = 0;i<ids.length;i++){
                    var col=$("#datatable").jqGrid('getRowData',ids[i]);     
                $.ajax({
                    type: "POST",
                    url: "${pageContext.request.contextPath}/boxController/delete?terminalid="+col.terminalid+"&boxid="+col.boxid,
                    success: function(msg){
                        showMsg(eval("("+msg+")").msg);
                        $("#datatable").trigger('reloadGrid');
                    }
                 });
                }
               }
            },
            cancelValue: '取消',
            cancel: function () {}
        });
        d.width(150);
        d.show();
    };  
    function delRepository(num,id){
        var d = dialog({
            title: '提示',
            content: '您确认想要删除吗？',
            okValue: '确定',
            ok: function () {
                this.title('提交中…');
                $.ajax({
                    type: "POST",
                    url: "${pageContext.request.contextPath}/boxController/delete?terminalid="+num+"&boxid="+id,
                    success: function(msg){
                    	showMsg(eval("("+msg+")").msg);
                    	$("#datatable").trigger('reloadGrid');
                        setTimeout(function () {
                            d.close().remove();
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
    function addMakeCard(title,okValue){//弹出修改和添加原因的窗口              
        dialogCode = dialog({
            title: title,
            content: $('#showupdate'),
            okValue: okValue,
            ok: function () {
                    $('#modifyCodeupdate').ajaxSubmit({
                        success: function (msg) {
                        	showMsg(eval("("+msg+")").msg);                           
                        	$("#datatable").trigger('reloadGrid');
                        	submitForm();
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
    function list(title,row){//弹出查看窗口
        var col=$("#datatable").jqGrid('getRowData',row);
        $('#id').val(col.id);
        $('#terminalid').attr("value",col.terminalid);
        $("#terminalid").attr("disabled","disabled");
        $('#boxid').val(col.boxid);
        $('#boxtypecode').val(col.boxtypecode);
        $('#dispalyname').val(col.dispalyname);
        $('#dispalyname').attr('readonly',true);
        $("#boxtypecode").attr("disabled","disabled");
        $('#boxid').attr('readonly',true);
        var a = col.oneboxmorecard;
        if(a==1){
        	$('#yes').attr('checked',true);
        }else{
        	$('#no').attr('checked',true);
        }
        $("#yes").attr("disabled","disabled");
        $("#no").attr("disabled","disabled");
        var f = col.fixedbox;
        if(f=="不限制"){
        	$('#fixedbox').val(0);
        }else if(f=="一箱一卡"){
        	$('#fixedbox').val(1);
        }else{
        	$('#fixedbox').val(2);
        }
        $("#fixedbox").attr("disabled","disabled");
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

