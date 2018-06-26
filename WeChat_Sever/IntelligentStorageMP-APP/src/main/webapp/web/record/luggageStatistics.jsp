<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>行李寄存柜统计</title>
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
    <p>行李寄存柜统计</p>
</div> -->
	<div class="content" align="center" style="display: none;">
		<form action="" id="searchMemberCardForm" method="post">
			<div class="list">
				<ul>
					<li>收费类型：<select name="masterID" id="masterID">
							<option value="" selected="selected">现金支付</option>
							<option value="">微信支付</option>
							<option value="">支付宝支付</option>				
					</select></li>
					<li>起始时间：<input name="" id="" type="text" /></li>
					<li>结束时间：<input name="" id="" type="text" /></li>
					<li>收银终端号：<input name="" id="" type="text" /></li>
				</ul>
			</div>
		</form>
	</div>
 <div class="tab">
        <table id="datatable"></table>
        <div id="pager2"></div>
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
    	$(window).resize(
				function() {
					$("#datatable").setGridWidth(
							$(window).width() - 50);
				});
        var tableWidth = $("#datatable").parent().innerWidth();
        $("#datatable").jqGrid({
            url:'',
            datatype: "json",
            mtype: "POST",
			width : document.body.offsetWidth - 60,
			height : window.screen.availHeight - 325,
            autowidth:true,
            editable: false,
            footerrow:true, 
            shrinkToFit:true,
            colNames:['序号','收费类型','起始时间','结束时间','收银终端号','收费额','退费额','结余金额','备注'],  
            colModel:[
                {name:"rechargenumber",index:"rechargenumber",align:'center',width:200,sortable:false},
                {name:"",index:'',align:'center',width:200,sortable:false},
                {name:"",index:"",align:'center',width:200,sortable:false},
                {name:"",index:"",align:'center',width:200,sortable:false},
                {name:"",index:"",align:'center',width:200,sortable:false},
                {name:"",index:"",align:'center',width:200,sortable:false},
                {name:"",index:'',align:'center',width:200,sortable:false},
                {name:"",index:'',align:'center',width:200,sortable:false},
                {name:"",index:'',align:'center',width:200,sortable:false}
                
            ],
            sortable: false,
            rowNum:10,
            rownumbers:false,
            multiselect: true,
            multiboxonly:true,	
            jsonReader: {
                repeatitems : false,
                id:"0"
            },
            pager: '#pager2',
            rowList:[10,15,20],
            sortname: 'rechargenumber',
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
        var sumValue = $(this).getCol('origin',false,'sum');//统计
        $(this).footerData('set','origin',"合计："+sumValue);
    });
   var dialogCode=null;
   var selectMakeCard =  function(row){//弹出修改和添加原因的窗口
	   var dialogCode = dialog({
			title : '查询', 
			content : $('#searchMemberCardForm'),
			okValue : "确定",
			ok : function() {
				/* var ？ = document.getElementById("").value; */
				$
						.ajax({
							type : "POST",
							url : "",
							data : {  },
							success : function(msg) {
								showMsg(msg.msg);
								submitForm();
								$('#searchMemberCardForm').clearForm(true);
							}
						});
			},
			cancelValue : '关闭',
			cancel : function() { 
				$('#searchMemberCardForm').clearForm(true);
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

