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
    <title>订单记录查询</title>
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
<body>
<!-- <div class="title">
    <p>存取记录查询</p>
</div> -->
<div class="content">
    <div class="tab">
        <table id="datatable" style="width:90%"></table>
        <div id="pager2"></div>
    </div>
</div>
<div id="showsearch" class="list" style="display: none">
    <form id="searchMemberCardForm" action="${pageContext.request.contextPath}/payStatus/list" method="post">
        <div class="list">
            <ul>
                <li>柜号：<input id="terminalno" name="terminalno" type="text"/></li>
                <li>箱号：<input id="boxno" name="boxno" type="text"/></li>
                <li>箱门状态：<select name="openstatus">
                    <option value="">请选择</option>
                    <option value="0">存开箱成功</option>
                    <option value="1">存开箱失败</option>
                    <option value="2">取开箱成功</option>
                    <option value="3">取开箱失败</option>
                </select></li>
                <li>开始时间：<input id="date1" class="Wdate" name="starttime" type="text" class="validate[required]"
                     onFocus="WdatePicker({dateFmt:'yyyy-MM-dd 00:00:00'})"></li>
                <li>结束时间：<input id="date2" class="Wdate" name="overtime" type="text" class="validate[required]"
                     onFocus="WdatePicker({dateFmt:'yyyy-MM-dd 00:00:00'})"></li>
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
            url:'${pageContext.request.contextPath}/payStatus/list',
            datatype: "json",
            mtype: "POST",
            width: document.body.offsetWidth-60,
            height:window.screen.availHeight-320,
            //autowidth:true,
            editable: false,
            //footerrow:true, //汇总
            shrinkToFit:true,
            colNames:['柜号','箱号','箱门开关状态','下单时间','存时间','订单号','设备号','权限开箱'],
            colModel:[
				{name:"remark",index:"terminalname",align:'center',width:50,sortable:false,
					formatter:function(el,options,rowData){
						var value = rowData.remark+"号柜";
						return  value ;
					}
				},   
                {name:"boxno",index:"boxno",align:'center',width:50,sortable:false,},
                {name:"openstatus",index:"openstatus",align:'center',width:50,sortable:false,
                 formatter:"select",editoptions:{value:"0:存开箱成功;1:存开箱失败;2:取开箱成功;3:取开箱失败;4:管理员开箱成功"}
                },
                {name:"storeintime",index:"storeintime",align:'center',width:100,sortable:false,formatter:function(el,options,rowData){
                	if(el==null){
                		return "空 ";
                	}
                    var t = rowData.storeintime;
                    var d=	new Date();
                    d.setTime(t);
                    var s=d.format('yyyy-MM-dd HH:mm:ss');
                	return s;
                }},
                {name:"ordertime",index:"ordertime",align:'center',width:100,sortable:false,formatter:function(el,options,rowData){
                    var t = rowData.ordertime;
                    var d=	new Date();
                    d.setTime(t);
                    var s=d.format('yyyy-MM-dd HH:mm:ss');
                	return s;
                }},
                {name:"packageid",index:"packageid",align:'center',width:100,sortable:false},
                {name:"terminalno",index:"terminalno",align:'center',width:70,sortable:false,},
                {name:"openstatus",index:"openstatus",align:'center',width:50,sortable:false,
                	formatter:function(el,options,rowData) {
          				if(el==1){
          					var buttonOpen = '<button class="button_delete" type="button" onclick="deleteRecord(\''
          							+rowData.terminalno
          							+ '\',\''
          							+rowData.remark
          							+ '\',\''
          							+rowData.boxno
          							+ '\')">删除记录</button>';
          						return buttonOpen;
          				}else if(el==3){
          					var buttonClose = '<button class="button" type="button" onclick="openBox(\''
      							+rowData.terminalno
      							+ '\',\''
      							+rowData.remark
      							+ '\',\''
      							+rowData.boxno
      							+ '\')">删除记录</button>';
          					return buttonClose;
          				}else{
          					var value='无';
          					return value;
          				}
          			}
                },
            ],
            sortable: false,
            rowNum:10,
            rownumbers:false,
            multiselect: true,
            multiboxonly:true,  
            beforeSelectRow: beforeSelectRow,//js方法  
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
            deltext:"删除"
        });
        /* var sumValue = $(this).getCol('origin',false,'sum');//统计
        $(this).footerData('set','origin',"合计："+sumValue); */
        /*  $("#datatable").navButtonAdd('#pager2',{
        	cloneToTop:true,
        	caption:"导出Excel", 
        	buttonicon:"ui-icon-excel", 
        	onClickButton: function(){
        		alert("导出excel");
        		var title = "属性"; 
        		getXlsFromTbl('datatable', 'div_list', title, false);
        	}, 
        	position:"last" 
        }); */
        
        
		/*清箱 */
        $("#datatable").navButtonAdd('#pager2',{
        	cloneToTop:true,
        	caption:"清箱", 
        	buttonicon:"ui-icon-excel", 
        	onClickButton: function(){
        		var d = dialog({
                    title: '提示',
                    content: '您确认想要清除所有箱子吗？',
                    okValue: '确定',
                    ok: function () {
                  	  $.ajax({
                            type: "POST",
                            url: "${pageContext.request.contextPath}/payStatus/clearBox",
                            success: function(res){
                            	if(res==1){
                            		showMsg("清理成功！");
                            	}else if(res==2){
                            		showMsg("无在箱记录！");
                            	}else{
                            		showMsg("清理失败！");
                            	}
                          	  	
                            }
                        });
                    },
                    cancelValue: '取消',
                    cancel: function () {}
                });
                d.width(180);
                d.show();
        	}, 
        	position:"last" 
        });
        /* $("#datatable").jqGrid('inlineNav', "#pager2"); */    
    });
    
    
	/*存开箱记录清楚 */
    function deleteRecord(terminalno,remark,boxno){
          var d = dialog({
              title: '提示',
              content: '您确认想要删除'+remark+'号柜'+boxno+'号箱这条记录吗？',
              okValue: '确定',
              ok: function () {
            	  $.ajax({
                      type: "POST",
                      url: "${pageContext.request.contextPath}/payStatus/deleteDepositRecord?terminalNo="+terminalno+"&boxNo="+boxno,
                      success: function(res){
                    	  console.log(res);
                    	  showMsg(eval("("+res+")").msg);
                    	  $("#datatable").trigger('reloadGrid');
                      }
                  });
              },
              cancelValue: '取消',
              cancel: function () {}
          });
          d.width(180);
          d.show();
  		}
    
  	/*取开箱记录清楚 */
    function openBox(terminalno,remark,boxno){
          var d = dialog({
              title: '提示',
              content: '您确认想删除'+remark+"号柜"+boxno+'号箱这条记录吗？',
              okValue: '确定',
              ok: function () {
            	  $.ajax({
                      type: "POST",
                      url: "${pageContext.request.contextPath}/payStatus/deleteTakenRecord?terminalNo="+terminalno+"&boxNo="+boxno,
                      success: function(res){
                    	  console.log(res);
                    	  showMsg(eval("("+res+")").msg);
                    	  $("#datatable").trigger('reloadGrid');
                      }
                  });
              },
              cancelValue: '取消',
              cancel: function () {}
          });
          d.width(180);
          d.show();
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
    var openDialog4Searching = function(title,okValue,row) {
    	dialogCode = dialog({
            title: '查询',
            content: $('#showsearch'),
            okValue: '查询',
            ok: function () {
            	submitForm()
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
        }, 1200);
    }
    
    function beforeSelectRow()  
    {  
        $("#datatable").jqGrid('resetSelection');  
        return(true);  
    }  

</script>
</body>
</html>
