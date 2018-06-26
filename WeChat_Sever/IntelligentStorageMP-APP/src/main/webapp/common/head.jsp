<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.8.3.min.js"></script>
<%--<script type="text/javascript" src="${pageContext.request.contextPath}/js/plug/artDialog/js/dialog.js"></script>--%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/plug/artDialog/js/dialog-plus.js"></script>
<script type="text/ecmascript" src="${pageContext.request.contextPath}/js/plug/grid/js/i18n/grid.locale-cn.js"></script>
<script type="text/ecmascript" src="${pageContext.request.contextPath}/js/plug/grid/js/jquery.jqGrid.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/plug/jquery.form.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/plug/validationEngine/js/languages/jquery.validationEngine-zh_CN.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/plug/validationEngine/js/jquery.validationEngine.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/plug/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/plug/zTree/js/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/thrid/laypage/laypage.js"></script>
<script src="${pageContext.request.contextPath}/js/thrid/select2/select2.min.js"></script>
<script src="${pageContext.request.contextPath}/js/date.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.i18n.properties.js"></script>
<script type="text/javascript">
	loadProperties();  
    function loadProperties() {
    	var lan = navigator.language || navigator.userLanguage;
    	var arrStr = document.cookie.split(";");
    	for (var i = 0; i < arrStr.length; i++) {
    	    var temp = arrStr[i].split("=");
    	    if (temp[0] == 'Language') {
    	        lan = unescape(temp[1]);
    	    }
    	}
    	//alert(getCookie("Language"));
    	function getCookie(name)
    	{
	    	var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
	    	if(arr=document.cookie.match(reg))
	    		return unescape(arr[2]);
	    	else
	    		return null;
    	}
         $.i18n.properties({
             name:'message',
             path:'${pageContext.request.contextPath}/messages/',
             mode:'map',
             language: lan
         });
     }
</script>