<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>用户信息查询</title>
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
</style>
</head>
<body>
<div class="title">
    <p>用户信息查询</p>
</div>
<div class="content">
    <form id="searchMemberCardForm" action="${ctx}/stock/Search" method="post">
        <div class="list">
            <input id="commoditycategoryl1" name="commoditycategoryl1" type="hidden">
            <ul>
                <li id="s">门店类型：<select onchange="setStoreType(this.value)" id="stylestore" name="stylestore">
                    <option value="" selected>请选择</option>
                    <option value="1">加盟</option>
                    <option value="2">直营</option>
                    <option value="3">托管</option>
                    <option value="4">合作</option>
                </select></li>
                <li>
                    门店：<select class="newinput" name="storenumber" onchange="setStore()" id="store">
                    <option value="">请选择</option>
                    <c:if test="${null!=store}">
                        <c:forEach var ="store" items="${store}">
                            <option name="${store.stylestore}" value="<c:out value='${store.storenumber}'/>" <c:if test="${store.storenumber==staff.storenumber}">selected</c:if>><c:out value='${store.storenumber} ${store.name}'/></option>
                        </c:forEach>
                    </c:if>
                </select>
                </li>
                <li id="listoretype">
                    仓库：<select class="validate[required]"
                               onchange="javascript:$('#repositoryname').val($('#repositoryid option[value=\''+$(this).val()+'\']').text());"
                               onclick="javascript:if($('#store').val()==''){showMsg('请先选择门店!');}" name="repositorynumber" id="repositoryid">
                    <option value="">请选择</option>
                </select>
                </li>
                <li>商品状态：<select name="thestate">
                
                    <option value="">请选择</option>
                    <option value="0">禁用</option>
                    <option value="1">启用</option>
                </select></li>
                
                <li style="margin-left: 50px;">
                <input onclick="submitForm()" value="查询" type="button" style="width: 88px; font-size: 14px; "/>
                </li>
            </ul>
        </div>
    </form>
    <div class="tab">
        <table id="datatable"></table>
        <div id="pager2"></div>
    </div>
</div>
<script type="text/javascript">
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
            colNames:['柜号','箱号','箱编号','箱类型','固定箱门','电话','传真','邮编','欢迎词','操作'],
            colModel:[
                {name:"commoditynumber",index:"commoditynumber",align:'center',width:90,sortable:false},
                {name:"name",index:'name',align:'left',width:180,sortable:false},
                {name:"number",index:"number",align:'center',width:50,sortable:false,hidden:false},
                {name:"categoryname",index:"categoryname",align:'center',width:120,sortable:false,formatter:function(el,options,rowData){
                    return '['+rowData.number+']'+el;
                }},
                {name:"volume",ikey:true,index:"thesize",frozen:true,align:'left',width:90,sortable:false},
                {name:"origin",index:"origin",align:'left',width:90,sortable:false},
                {name:"packagename",index:'packagename',align:'center',width:50,sortable:false},
                {name:"storename",index:'storename',align:'left',width:100,sortable:false,formatter:function(el,options,rowData){
                    return '['+rowData.storenumber+']'+el;
                }},
                {name:"repositoryname",index:'repositoryname',align:'left',width:100,sortable:false,formatter:function(el,options,rowData){
                    return '['+rowData.repositorynumber+']'+el;
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
            pager: '#pager2',
            rowList:[10,15,20],
            sortname: 'rechargenumber',
            sortorder: 'desc',
            viewrecords: true
        });
    });
    function delRepository(num){
        var d = dialog({
            title: '提示',
            content: '您确认想要删除吗？',
            okValue: '确定',
            ok: function () {
                this.title('提交中…');
                $.ajax({
                    type: "POST",
                    url: "${ctx}/announcement/deleteType?id="+num,
                    success: function(msg){
                        d.close();
                        d=null;
                        d = dialog({
                            content:msg.msg
                        });
                        d.width(150);
                        d.show();
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

