<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
<%@ include file="../../common/head.jsp"%>
<%@ include file="../../common/theme.jsp"%>
</head>
<body>
	<div class="content">
	    <div>
			<ul id="myTab1">
			  <li><input style="font-size:14px;" id="tab" type="button" value="收费标准管理"/></li>
			  <li><input style="font-size:14px;" id="tabb" type="button" value="卡片转换规则"/></li>
			  <li><input style="font-size:14px;" id="tabb3" type="button" value="运营时段设置"/></li>
			</ul>
	    </div>
		<div id="myTabContent2">
			<div class="tab" id="tab_content11" style="margin-top:30px;">
				<table border="1" cellSpacing="0" cellPadding="0" Style="width:780px;height:234px;">
				  <tbody>
				    <tr>
					    <td rowSpan="2" colSpan="1" align="center"><select id="id" name="name" onChange="table()">
					          <option value="1">收费模式一（小时）</option>
					          <option value="2">收费模式二（时段）</option>
					          <option value="3">收费模式三</option>
					         </select>
					    </td>
				        <td rowSpan="1" colSpan="4" align="center">类型</td>
				    </tr>
				    <tr>
				        <td align="center">小</td>
				        <td align="center">中</td>
				        <td align="center">大</td>
				        <td align="center">超大</td>
				    </tr>
				    <tr id="tr">
				        <td align="center" rowSpan="1" colSpan="1">每<input type="text" style="width:50px;"/>小时</td>
				        <td align="center" rowSpan="1" colSpan="1"><input type="text" style="width:50px;"/>角</td>
				        <td align="center" rowSpan="1" colSpan="1"><input type="text" style="width:50px;"/>角</td>
				        <td align="center" rowSpan="1" colSpan="1"><input type="text" style="width:50px;"/>角</td>
				        <td align="center" rowSpan="1" colSpan="1"><input type="text" style="width:50px;"/>角</td>
				    </tr>
				    <tr id="tr21" style="display:none;">
				        <td align="center" rowSpan="1" colSpan="1"><input type="text" style="width:50px;"/>-<input type="text" style="width:50px;"/>时段</td>
				        <td align="center" rowSpan="1" colSpan="1"><input type="text" style="width:50px;"/>角</td>
				        <td align="center" rowSpan="1" colSpan="1"><input type="text" style="width:50px;"/>角</td>
				        <td align="center" rowSpan="1" colSpan="1"><input type="text" style="width:50px;"/>角</td>
				        <td align="center" rowSpan="1" colSpan="1"><input type="text" style="width:50px;"/>角</td>
				    </tr>
				    <tr id="tr22" style="display:none;">
				        <td align="center" rowSpan="1" colSpan="1"><input type="text" style="width:50px;"/>-<input type="text" style="width:50px;"/>时段</td>
				        <td align="center" rowSpan="1" colSpan="1"><input type="text" style="width:50px;"/>角</td>
				        <td align="center" rowSpan="1" colSpan="1"><input type="text" style="width:50px;"/>角</td>
				        <td align="center" rowSpan="1" colSpan="1"><input type="text" style="width:50px;"/>角</td>
				        <td align="center" rowSpan="1" colSpan="1"><input type="text" style="width:50px;"/>角</td>
				    </tr>
				    <tr id="tr23" style="display:none;">
				        <td align="center" rowSpan="1" colSpan="1"><input type="text" style="width:50px;"/>-<input type="text" style="width:50px;"/>时段</td>
				        <td align="center" rowSpan="1" colSpan="1"><input type="text" style="width:50px;"/>角</td>
				        <td align="center" rowSpan="1" colSpan="1"><input type="text" style="width:50px;"/>角</td>
				        <td align="center" rowSpan="1" colSpan="1"><input type="text" style="width:50px;"/>角</td>
				        <td align="center" rowSpan="1" colSpan="1"><input type="text" style="width:50px;"/>角</td>
				    </tr>
				    <tr id="tr31" style="display:none;">
				        <td align="center" rowSpan="1" colSpan="1"><input type="text" style="width:50px;"/>-<input type="text" style="width:50px;"/>小时</td>
				        <td align="center" rowSpan="1" colSpan="1"><input type="text" style="width:50px;"/>角</td>
				        <td align="center" rowSpan="1" colSpan="1"><input type="text" style="width:50px;"/>角</td>
				        <td align="center" rowSpan="1" colSpan="1"><input type="text" style="width:50px;"/>角</td>
				        <td align="center" rowSpan="1" colSpan="1"><input type="text" style="width:50px;"/>角</td>
				    </tr>
				    <tr id="tr32" style="display:none;">
				        <td align="center" rowSpan="1" colSpan="1">之后每<input type="text" style="width:50px;"/>分钟</td>
				        <td align="center" rowSpan="1" colSpan="1"><input type="text" style="width:50px;"/>角</td>
				        <td align="center" rowSpan="1" colSpan="1"><input type="text" style="width:50px;"/>角</td>
				        <td align="center" rowSpan="1" colSpan="1"><input type="text" style="width:50px;"/>角</td>
				        <td align="center" rowSpan="1" colSpan="1"><input type="text" style="width:50px;"/>角</td>
				    </tr>
				    <tr>
				        <td align="center" rowSpan="1" colSpan="1">押金<input type="text" style="width:50px;"/>角</td>
				        <td align="center" rowSpan="1" colSpan="3">退款操作时限<input type="text" style="width:50px;"/>分钟</td>
				        <td align="center" rowSpan="1" colSpan="1"><input type="submit" value="确定" style="width:40px;"/></td>
				    </tr>
				  </tbody>
				</table>
			</div>
			<div class="tab" id="tab_content22"
				style="display: none;margin-top:30px;">
				<li style="margin-right:332px;margin-top:-30px;float: right;"><input name="button" onclick="addMakeCard('添加类型','添加',0)" value="添加" type="button"/></li>
				<table id="datatable"></table>
                <div id="pager2"></div>
			</div>
			<div class="tab" id="tab_content33"
				style="display: none;margin-top:30px;">
				<li style="margin-right:332px;margin-top:-30px;float: right;"><input name="button" onclick="addMakeCard('添加类型','添加',0)" value="添加" type="button"/></li>
				<table id="datatable2"></table>
                <div id="pager3"></div>
			</div>
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
	$("#tabb").click(function() {
		$("#tab_content22").show();
		$('#tab_content11').hide();
		$('#tab_content33').hide();
	});
	$("#tabb3").click(function() {
		$("#tab_content33").show();
		$('#tab_content11').hide();
		$('#tab_content22').hide();
	});
	$("#tab").click(function() {
		$("#tab_content11").show();
		$('#tab_content33').hide();
		$('#tab_content22').hide();
	});
	function table(){
		var a = $('#id').val();
		if(a=="1"){
			$('#tr21,#tr22,#tr23,#tr31,#tr32').hide();
			$('#tr').show();
		}else if(a=="2"){
			$('#tr,#tr31,#tr32').hide();
			$('#tr21,#tr22,#tr23').show();
		}else{
			$('#tr21,#tr22,#tr23,#tr').hide();
			$('#tr31,#tr32').show();
		}
	}
	$(document).ready(function(){
        var tableWidth = $("#datatable").parent().innerWidth();
        $("#datatable").jqGrid({
            url:'',
            datatype: "json",
            mtype: "POST",
            width: tableWidth,
            height: 350,
            autowidth:true,
            editable: false,
            shrinkToFit:true,
            colNames:['规则名称','卡号类型','卡号长度','截取起始位置','卡片规则','十进制规则','操作'],
            colModel:[
                {name:"commoditynumber",index:"commoditynumber",align:'center',width:90,sortable:false},
                {name:"name",index:'name',align:'left',width:180,sortable:false},
                {name:"number",index:"number",align:'center',width:80,sortable:false,hidden:false},
                {name:"categoryname",index:"categoryname",align:'center',width:120,sortable:false,formatter:function(el,options,rowData){
                    return '['+rowData.number+']'+el;
                }},
                {name:"volume",ikey:true,index:"thesize",frozen:true,align:'left',width:90,sortable:false},
                {name:"origin",index:"origin",align:'left',width:90,sortable:false},
                {name:"",index:'',align:'center',width:70,formatter:function(el,options,rowData){
                        var d= '[<a href="javascript:void(0)" onclick="delRepository(\''+rowData.id+'\')">删除</a>] ';
                        var u= '[<a href="javascript:void(0)" onclick="addMakeCard(\'修改类型\',\'修改\','+ options.rowId +')">修改</a>] ';
                        return u+d;
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
        $("#datatable").jqGrid("navGrid", "#pager2", {  
            addfunc :  addMakeCard,   // (1) 点击添加按钮，则调用openDialog4Adding方法  
            editfunc : addMakeCard, // (2) 点击编辑按钮，则调用openDialog4Updating方法  
            delfunc : openDialog4Deleting,  // (3) 点击删除按钮，则调用openDialog4Deleting方法  
            alerttext : "请选择需要操作的数据行!"  // (4) 当未选中任何行而点击编辑、删除、查看按钮时，弹出的提示信息  
        });
        $("#datatable").navButtonAdd('#pager2',{ 
        	caption:"导出Excel", 
        	buttonicon:"ui-icon-excel", 
        	onClickButton: function(){
        		alert("导出excel"); 
        	}, 
        	position:"last" 
        });
       /*  var openDialog4Adding = function() {  
        	
        };  
        var openDialog4Updating = function(rowid) { // (6) 接受选中行的id为参数  
            
        };  */ 
        var openDialog4Deleting = function(rowid) { // (8) 接受选中行的id为参数  
            
        };
    });
	$(document).ready(function(){
        var tableWidth = $("#datatable").parent().innerWidth();
        $("#datatable2").jqGrid({
            url:'',
            datatype: "json",
            mtype: "POST",
            width: tableWidth,
            height: 350,
            autowidth:true,
            editable: false,
            shrinkToFit:true,
            colNames:['时段名称','星期一','星期二','星期三','星期四','星期五','星期六','星期日','操作'],
            colModel:[
                {name:"commoditynumber",index:"commoditynumber",align:'center',width:90,sortable:false},
                {name:"name",index:'name',align:'left',width:100,sortable:false},
                {name:"number",index:"number",align:'center',width:80,sortable:false,hidden:false},
                {name:"categoryname",index:"categoryname",align:'center',width:120,sortable:false,formatter:function(el,options,rowData){
                    return '['+rowData.number+']'+el;
                }},
                {name:"volume",ikey:true,index:"thesize",frozen:true,align:'left',width:90,sortable:false},
                {name:"origin",index:"origin",align:'left',width:90,sortable:false},
                {name:"packagename",index:'packagename',align:'center',width:80,sortable:false},
                {name:"storename",index:'storename',align:'left',width:100,sortable:false,formatter:function(el,options,rowData){
                    return '['+rowData.storenumber+']'+el;
                }},
                {name:"",index:'',align:'center',width:70,formatter:function(el,options,rowData){
                        var d= '[<a href="javascript:void(0)" onclick="delRepository(\''+rowData.id+'\')">删除</a>] ';
                        var u= '[<a href="javascript:void(0)" onclick="addMakeCard(\'修改类型\',\'修改\','+ options.rowId +')">修改</a>] ';
                        return u+d;
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
    });
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
                if(row>0){
                    $('#modifyCode').ajaxSubmit({
                        success: function (msg) {
                            showMsg(msg.msg);
                            submitForm();
                            $('#name').val('');
                            $('#id').val('');
                        }
                    });
                    return true;
                }else{
                var name = document.getElementById("name").value;
                $.ajax({
                    type: "POST",
                    url: "${ctx}/announcement/panduan",
                    data:{name:name},
                    success: function(put) {
                        if (put.put == 1) {
                            $('#modifyCode').ajaxSubmit({
                                success: function (msg) {
                                    showMsg(msg.msg);
                                    submitForm();
                                    $('#name').val('');
                                    $('#id').val('');
                                }
                            });
                            return true;
                        } else if (put.put == 0) {
                            showMsg("公告类型已存在");
                            $('#name').val('');
                            return false;
                        }
                    }
                });
                }
            },
            cancelValue: '关闭',
            cancel: function () {
                $('#modifyCode').clearForm(false);
                $('#number').val('');
                $('#name').val('');
                $('#id').val('');
            }
        });
        dialogCode.width(600);
        dialogCode.showModal();
    }
</script>
</html>