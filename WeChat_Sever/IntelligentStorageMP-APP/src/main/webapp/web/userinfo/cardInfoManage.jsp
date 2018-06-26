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
    <title>卡箱关系管理</title>
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
    <form id="searchForm" action="${basePath}/cardInfo/list" method="post">
        <div class="list">
            <ul>
                <li>卡号：<input name="cardid" type="text"/></li>
                <li>柜号：<input name="displayname" type="text"/></li>
                <li>箱号：<input name="boxid" type="text"/></li>
                <li>状态：<select name="sync">
                    <option value="-1">请选择</option>
                    <option value="0">未开卡</option>
                    <option value="1">正常</option>
                    <option value="2">挂失</option>
                </select>
                </li>
            </ul>
        </div>
    </form>
</div>
<div id="showadd" class="list" style="display: none">
    <form id="modifyCode" action="${basePath}/cardInfo/add" method="post" >
        <ul>
            <li>卡号：<input id="cardid" name="cardid" onclick="read()"></li>
               <%--  <c:forEach var="c" items="${card}">
	                 <option value="${c.usercardid}" selected>${c.usercardid}</option>
	            </c:forEach>
            </select></li> --%>
            <li>发卡方式：<select id="zd" name="zd" onclick="read()">
               <option value="1">手动发卡</option>
               <option value="2">自动发卡</option>
            </select></li>
            <li>柜号：<select id="terminalid" name="terminalid" onchange="opt()">
                <c:forEach var="t" items="${terminal}">
	                 <option value="${t.terminalid}" selected>${t.displayname}</option>
	            </c:forEach>
            </select></li>
            <li>箱号：<select id="boxid" name="boxid">
                <c:forEach var="b" items="${box}">
	                 <option value="${b.boxid}" selected>${b.boxid}</option>
	            </c:forEach>
            </select></li>
        </ul>
    </form>
</div>
<div id="showupdate" class="list" style="display: none">
    <form id="modifyCodeupdate" action="${basePath}/cardInfo/update" method="post" >
        <ul>
            <li>卡号：<select id="cardid1" name="cardid">
                <c:forEach var="c" items="${card}">
	                 <option value="${c.usercardid}" selected>${c.usercardid}</option>
	            </c:forEach>
            </select></li>
            <li>柜号：<select id="terminalid1" name="terminalid" onchange="opt2()">
                <c:forEach var="t" items="${terminal}">
	                 <option value="${t.terminalid}" selected>${t.displayname}</option>
	            </c:forEach>
            </select></li>
            <li>箱号：<select id="boxid1" name="boxid">
                <c:forEach var="b" items="${box}">
	                 <option value="${b.boxid}" selected>${b.boxid}</option>
	            </c:forEach>
            </select></li>
        </ul>
    </form>
</div>
<script type="text/javascript">
window.onload=function(){
	var axCardReader = document.getElementById('CardReader');
	if(axCardReader == null){
		alert("创建组件失败")
	}else{
		var id = $("#cardid").val();
		if(id=="" || id==null){
			axCardReader.AXRCC_Open();
			var cardid = axCardReader.AXRCC_readCardID();
			document.getElementById('cardid').value = cardid;
			//axCardReader.AXRCC_Close();
		}		
	}
}
function read(){
	var id = $("#zd").val();	
    if(id=="1"){
    	var axCardReader = document.getElementById('CardReader');
		if(axCardReader == null){
			alert("创建组件失败")
		}else{
			//axCardReader.AXRCC_Open();
			var cardid = axCardReader.AXRCC_readCardID();
			document.getElementById('cardid').value = cardid;
			//axCardReader.AXRCC_Close();
		}
    }else{
		var axCardReader = document.getElementById('CardReader');
		if(axCardReader == null){
			alert("创建组件失败")
		}else{
			//axCardReader.AXRCC_Open();
			var cardid = axCardReader.AXRCC_readCardID();
			document.getElementById('cardid').value = cardid;
			//axCardReader.AXRCC_Close();
			setTimeout("read()",1000);
			var cardid = $("#cardid").val();
			if(cardid != ""){
				$.ajax({
					type: "POST",
			        url: "${basePath}/cardInfo/selectByCardid?cardid="+cardid,
			        success: function(msg){ 
			        	if(eval("("+msg+")").msg=="true"){
			        		var terminalid=$("#terminalid").val();
			        		//var boxid=$("#boxid").val();
			        		var boxid = document.getElementById('boxid').value;
			        		if(boxid != ""){
			        			alert(boxid);
				        		$.ajax({
				        			type: "POST",
							        url: "${basePath}/cardInfo/add",
							        data:{cardid:cardid,terminalid:terminalid,boxid:boxid},							       
							        success: function(msg){
										showMsg(eval("("+msg+")").msg);
										$("#datatable").trigger('reloadGrid');
										var index = $("#boxid").get(0).selectedIndex;
				                    	$("#boxid").get(0).selectedIndex=index+1;
					                }
				        		});
			        		}else{
			        			showMsg("该柜号下的箱子绑定完毕");
			        		}
			        	}else{
			        		showMsg("卡号已绑定");
			        	}
			        }
				});
			}else{
				//showMsg("请输入卡号");
			}
		}
    }
}
function opt(){
	var id = $("#terminalid").val(); 
	$.ajax({
        type: "POST",
        url: "${basePath}/cardInfo/selectByid?terminalid="+id,
        success: function(msg){ 
        	var myobj=(eval("(" + msg + ")").msg);
        	$("#boxid").html("");
        	$.each(myobj,function(i,val){
        		$("#boxid").append("<option value="+val.boxid+">"+val.boxid+"</option>");
        	})
        }
    });
}
function opt2(){
	var id = $("#terminalid1").val(); 
	$.ajax({
        type: "POST",
        url: "${basePath}/cardInfo/selectByid?terminalid="+id,
        success: function(msg){ 
        	var myobj=(eval("(" + msg + ")").msg);
        	$("#boxid1").html("");
        	$.each(myobj,function(i,val){
        		$("#boxid1").append("<option value="+val.boxid+">"+val.boxid+"</option>");
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
            url:'${basePath}/cardInfo/list',
            datatype: "json",
            mtype: "POST",
            width: document.body.offsetWidth-60,
            height: window.screen.availHeight-340,
            //autowidth:true,
            editable: false,
            shrinkToFit:true,
            colNames:['卡号','柜号','箱号','','显示名称','创建人','状态','制卡时间'],
            colModel:[
                {name:"cardid",index:'cardid',align:'left',width:100,sortable:false},
                {name:"terminalEntity.displayname",index:"terminalid",align:'center',width:50,sortable:false},
                {name:"boxid",index:"boxid",align:'center',width:80,sortable:false},
                {name:"terminalid",index:"terminalid",align:'center',width:80,sortable:false,hidden:true},
                {name:"boxEntity.dispalyname",index:"boxid",align:'center',width:80,sortable:false},
                {name:"accountEntity.accountname",index:'makeopcode',align:'center',width:90,sortable:false},
                {name:"sync",index:'sync',align:'center',width:70,sortable:false,formatter:function(el,options,rowData){
                    var t = rowData.sync;
                	if(t==1){
                		return '正常';
                	}else if(t==2){
                		return '挂失';
                	}else{return '未开卡';}
                }},
                {name:"makedate",index:'makedate',align:'center',width:100,sortable:false,formatter:function(el,options,rowData){
                    var t = rowData.makedate;
                    var d=	new Date();
                    d.setTime(t);
                    var s=d.format('yyyy-MM-dd HH:mm:ss');
                	return s;
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
            sortname: 'cardid',
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
        	edit:false,
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
     var t;
    function card(){
    	var axCardReader = document.getElementById('CardReader');
		if(axCardReader == null){
			alert("创建组件失败")
		}else{
			var id = $("#cardid").val();
			var cardid = axCardReader.AXRCC_readCardID();
			if(id=="" || id==null){				
				//axCardReader.AXRCC_Open();				
				document.getElementById('cardid').value = cardid;
				//axCardReader.AXRCC_Close();
				t = setTimeout("card()",1000);
			}else{
				if(cardid=="" || cardid==null){
					t = setTimeout("card()",1000);
				}else{
					document.getElementById('cardid').value = cardid;
					//axCardReader.AXRCC_Close();
					t = setTimeout("card()",1000);
				}
			}				
	     }		
	 }
    var dialogCode=null;
    var openDialog4Adding = function(title,okValue) {
    	var id = $("#terminalid").val(); 
    	$.ajax({
            type: "POST",
            url: "${basePath}/cardInfo/selectByid?terminalid="+id,
            success: function(msg){ 
            	var myobj=(eval("(" + msg + ")").msg);
            	$("#boxid").html("");
            	$.each(myobj,function(i,val){
            		$("#boxid").append("<option value="+val.boxid+">"+val.boxid+"</option>");
            	})
            }
        });
    	card();
        dialogCode = dialog({
            title: "新增",
            content: $('#showadd'),
            okValue: "保存",
            ok: function () {
            	    var cardid = document.getElementById("cardid").value;
            	    var terminalid = document.getElementById("terminalid").value;
            	    var boxid = document.getElementById("boxid").value;
            	    if(terminalid==""){
            	    	showMsg("请选择柜号");
            	    	return false;
            	    }else if(boxid==""){
            	    	showMsg("请选择箱号");
            	    	return false;
            	    }else if(cardid==""){
            	    	showMsg("请输入卡号");
            	    	return false;
            	    }else{
                    $('#modifyCode').ajaxSubmit({
                    	error: function(){
                    		alert("操作失败");
                    	},
                        success: function (msg) {
                        	showMsg(eval("("+msg+")").msg);
                        	$("#datatable").trigger('reloadGrid');
                        	submitForm();
                        	//$('#modifyCode').clearForm(true);
                        }
                    });
                    return true;
            	}
            },
            cancelValue: '关闭',
            cancel: function () {
                $('#modifyCode').clearForm(true);
                //clearTimeout(t);
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
	            var id = col.terminalid; 
	        	$.ajax({
	                type: "POST",
	                url: "${basePath}/cardInfo/selectByid?terminalid="+id,
	                success: function(msg){ 
	                	var myobj=(eval("(" + msg + ")").msg);
	                	$("#boxid1").html("");
	                	$.each(myobj,function(i,val){
	                		$("#boxid1").append("<option value="+val.boxid+">"+val.dispalyname+"</option>");
	                	})
	                }
	            });
	            $('#cardid1').val(col.usercardid);
	            $('#cardid1').attr("readonly",true);
	            $('#terminalid1').attr("value",col.terminalid);
	            $('#boxid1').val(col.dispalyname);
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
                    url: "${basePath}/cardInfo/delete?id="+col.cardid+"&terminalid="+col.terminalid+"&boxid="+col.boxid,
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
                    url: "${basePath}/userController/userDelete.do?id="+num,
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
