
/*
var setting = {//角色菜单
    view: {
        selectedMulti: false
    },
    async: {
        enable: true,
        //url: "/role/manager/getRolePermissions",
        dataFilter:function(treeId,parentNode,responseData){
            //if(responseData.)
            console.log(responseData);
            if(responseData.length>0){
                $.each(zNodes,fun)
            }else{
                return zNodes;
            }
        }
    },
    check: {
        enable: true,
        nocheckInherit: true
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
            name: "name"
        },
        simpleData: {
            enable: true,
            idKey: "number",
            pIdKey: "parentnum"
        }
    },
    callback: {
         onAsyncSuccess: function(){
         var __zTree = $.fn.zTree.getZTreeObj("treeDemo");
         __zTree.expandAll(true);
         },
        onClick: function(){
            var __zTree = $.fn.zTree.getZTreeObj("treeDemo");
            var nodes = __zTree.getSelectedNodes();
            var code = nodes[0].id;
        },
        onCheck: function(event, treeId, treeNode) {//ztree的选中事件
            setRoleCode(treeNode,treeNode.checked,false);
            //alert(treeNode.tId + ", " + treeNode.name + "," + treeNode.checked);
        },
        beforeCheck:function(treeId, treeNode){
            console.log(treeNode);
        }
    }
};*/

var setting = {
		view: {
			selectedMulti: false,
			showIcon:true
		},
	    check: {
	        enable: true,
	        nocheckInherit: true
	    },
	    edit: {
	        enable: true,
	        showRemoveBtn: false,
	        showRenameBtn: false
	    },
		data: {
			keep: {
				parent:true,
				leaf:false
			},
			simpleData: {
				enable: true
			}
		}
	};