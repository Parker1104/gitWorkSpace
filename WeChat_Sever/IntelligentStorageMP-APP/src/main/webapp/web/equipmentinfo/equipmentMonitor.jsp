<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%-- <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> --%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
    request.setAttribute("basePath",basePath);
%>
<html>
<head>
    <title>设备监控</title>
    <link href="${basePath}/css/web/main.css" rel="stylesheet" type="text/css">
    <link rel="stylesheet" type="text/css" href="${basePath}/js/ui/css/jquery-ui.css" />
    <script type="text/javascript" src="${basePath}/js/jquery-1.8.3.min.js"></script>
    <link rel="stylesheet" type="text/css" href="${basePath}/js/plug/artDialog/css/ui-dialog.css">
    <script type="text/javascript" src="${basePath}/js/plug/artDialog/js/dialog-plus.js"></script>
    <link rel="stylesheet" href="${basePath}/js/plug/zTree/css/zTreeStyle/zTreeStyle.css" type="text/css" />
    <script type="text/javascript" src="${basePath}/js/plug/zTree/js/jquery.ztree.core-3.5.min.js"></script>
    <script type="text/javascript" src="${basePath}/js/plug/zTree/js/jquery.ztree.excheck-3.5.js"></script>
    <script type="text/javascript" src="${basePath}/js/plug/jquery.form.js"></script>
    <link rel="stylesheet" type="text/css" href="${basePath}/js/plug/grid/css/ui.jqgrid.css" />
    <script type="text/ecmascript" src="${basePath}/js/plug/grid/js/jquery.jqGrid.js"></script>
    <script type="text/ecmascript" src="${basePath}/js/plug/grid/js/i18n/grid.locale-cn.js"></script>
    <script type="text/javascript" src="${basePath}/js/module/commodity/category.js"></script>
    <script type="text/javascript" src="${basePath}/js/module/commodity/role.js"></script>
</head>
<body>
<div class="title">
    <p>设备监控</p>
</div>

<div class="content">
    <!-- <div class="but" style="width: 90%;">
        <input  onclick="addRole();" name="button" value="新增" type="button">
    </div> -->
</div>
<div class="tab" style="width: 90%;">
    <table id="datatable"></table>
</div>
<div id="pager2"></div>
<div class="alert" id="roleWin" style="display: none">
    <form id="roleform" action="/role/manager/saveRole" method="post">
        <table cellpadding="5">
            <tr>
                <td height="40" align="right">名称：</td>
                <td><input maxlength="20" type="text" id="name"  name="name" style="width: 200px; height: 24px; border: 1px solid #ccc; border-radius: 3px;" />
                    <input maxlength="20" type="hidden" id="createname" name="createname" />
                </td>
            </tr>
            <tr>
                <td height="40" align="right">描述：</td>
                <td><input maxlength="50" type="text" id="description"  name="description" style="width: 200px; height: 24px; border: 1px solid #ccc; border-radius: 3px;"  /></td>
            </tr>
            <input type="hidden" name="id" id="id" />
        </table>
        <input type="button" class="btn" value="保存" onclick="submitForm()">
    </form>
</div>

<div id="setRole" style="display: none;">
    <ul id="treeDemo" class="ztree" style="margin-left: 40px; margin-top: 15px;width: 400px; height: 440px;"></ul>
</div>
<script type="text/javascript">
    var tableGrid=null;
    var roleTree=null;   
    var have='';
    var Nodes='';
    function setRole(areaname,id){
       // roleTree.reAsyncChildNodes(null, "refresh");
        //console.log(roleTree.getCheckedNodes(true));
        //roleTree.checkAllNodes(false);//取消所有的勾选
        //roleTree.expandAll(false);//关闭所有的节点
        $.ajax({
            type: "POST",
            url: "${basePath}/terminalMonitor/selectZtree?areacode="+id,
            success: function(data){
                Nodes = data;
            },
            error:function(){
            }
        });
        var d = dialog({
            title: areaname+'设备',
            content: $('#setRole'),
            okValue: '保存',
            ok: function () {                                         
            },
            cancelValue: '取消',
            cancel: function () {}
        });
        d.width(550);
        d.height(500);
        d.showModal();
    }
    $(document).ready(function(){

        roleTree=$.fn.zTree.init($("#treeDemo"), setting,Nodes);
        var gridWidth=$("#datatable").parent().innerWidth();
        tableGrid=$("#datatable").jqGrid({
            url:'${basePath}/terminalMonitor/list',
            datatype: "json",
            postData:{'stylestore':1},
            mtype: "POST",
            width: gridWidth,
            height: 350,
            editable: false,
            colNames:['区域编码','区域名称','区域简称','操作'],
            colModel:[
                {name:"areacode",index:"areacode",frozen:true,width:10,sortable:false},
                {name:"areaname",index:"areaname",align:'left',width:50,sortable:false},
                {name:"areashortname",index:'areashortname',align:'left',width:30,sortable:false},
                {name:"",index:'',align:'center',width:30,sortable:false,formatter:function(el, options, rowData){
                    var s= '[<a href="javascript:void(0)" onclick="setRole(\''+rowData.areaname+'\',\''+ rowData.areacode +'\')">查看设备</a>]';
                    return s;
                }}
            ],
            rowNum:10,
            //rownumbers:true,
            //multiselect: false,
            jsonReader: {
                repeatitems : false,
                id:"0"
            },
            pager: '#pager2',
            rowList:[10,15,20],
            //sortname: 'storenumber',
            sortorder: 'desc',
            viewrecords: true
        });

        $('#roleList > li').click( function () {
            $('#roleList > li').removeClass("selected");
            $(this).addClass("selected");

        });       
    }); 
    var setting = {//类别
    	    view: {
    	        selectedMulti: false
    	    },
    	    async: {
    	        enable: true,
    	        url: "${basePath}/terminalMonitor/ztree",
    	        dataFilter:function(treeId,parentNode,responseData){
    	            responseData[0]['open']=true;
    	            return responseData;
    	        }
    	    }, 
    	    edit: {
    	        enable: true,
    	        showRemoveBtn: false,
    	        showRenameBtn: false
    	    },
    	    data: {
    	        keep: {
    	            parent:true,
    	            leaf:true
    	        },
    	        key: {
    	            name: "boxid"
    	        },
    	        simpleData: {
    	            enable: true,
    	            idKey: "boxid",
    	            pIdKey: "terminalid",
    	            //rootPId:"displayname"
    	        }
    	    },
    	    callback: {
    	        onClick: function(){
    	            var __zTree = $.fn.zTree.getZTreeObj("treeDemo");
    	            var nodes = __zTree.getSelectedNodes();
    	            var code = nodes[0].id;
//    	                alert(code)
    	        }
    	    }
    	};
</script>
</body>
</html>
