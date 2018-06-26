<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title><spring:message code="password.title.text" /></title>
<%@ include file="../../common/head.jsp"%>
<%@ include file="../../common/theme.jsp"%>
<link rel="stylesheet" href="../../js/plug/zTree/css/demo.css"
	type="text/css"> 

<style>
     .list .btn_another input { width: 88px !important; height: 24px !important; border-radius: 3px; margin-bottom: 10px;  background: #74a3dc; border: none; cursor: pointer; font-family: "微软雅黑"; color: #fff; font-size: 14px;  }
    .list .btn_another input:hover { background-color: #189ad8; }
    .confirm { width: 100% !important; float:left; }
    .confirm input { margin-left: 40px;}
    .list li { height: 35px;}
    .bottom-input { width: 70%; float: right; margin-top: 10px;}
    .bottom-input li { width: 320px; float: right; text-align: right;}
    .bottom-input li input { width: 200px; height: 24px; border: 1px solid #ccc; border-radius: 3px;}
    .fm-button {padding: .0em 1.4em .2em 1.3em;}
    #mima{
    width: 30%;
    height: 50%;
    top: 20%;
    float:left;
    margin-left: 35%;
    margin-top:100px;
    }
</style>
</head>
<body style="overflow-x:hidden">
<div id="mima" align="center">
	    <form id="modifyCode" action="${pageContext.request.contextPath}/passwordController/saveOrUpdateAccount.do" method="post">	
	    <div class="list"  align="center">	
		  <ul>
			<li><spring:message code="password.newpassword" />：<input type="password" id="newpassword" name="newpassword" /></li>
			<li><spring:message code="password.confirmpassword" />：<input type="password" id="confirmpassword" name="confirmpassword"/></li>			
			</ul>
		  <ul style="float:right;margin-right:0px;">      
	         <li style="margin-left: 50px;">	              
	              <input type="button" value="<spring:message code='password.prompt1.information' />" onclick="updataAccountpass()" style="width: 88px; font-size: 15px; "/>
	          </li>
          </ul>
		</div>
	</form>
</div>

<script type="text/javascript">
//修改 
function updataAccountpass() { 
	if($("#newpassword").val() == $("#confirmpassword").val()){
		$.ajax({
			type : "POST",
			url : "${pageContext.request.contextPath}/passwordController/saveOrUpdateAccount.do",
			data : {
				newpassword : $("#newpassword").val(),	
				confirmpassword : $("#confirmpassword").val()
				},
		   success : function(msg) {
					top.location='${pageContext.request.contextPath}'; 
			},
		});
    }else {
    	showMsg("<spring:message code='password.prompt2.information' />"); 
 }
};
function showMsg(themsg) {  //显示提示信息，1s后自动关闭
	var d = dialog({
		content : themsg
	});
	d.show();
	setTimeout(function() {
		d.close().remove();
	}, 1000);
};
</script>
</body>
</html>