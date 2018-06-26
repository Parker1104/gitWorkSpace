<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>智能存取终端管理平台</title>
        <!-- CSS -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/bootstrap/css/bootstrap.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/font-awesome/css/font-awesome.min.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/form-elements.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
        <link rel="shortcut icon" href="${pageContext.request.contextPath}/assets/ico/favicon.png">
        <link rel="apple-touch-icon-precomposed" sizes="144x144" href="${pageContext.request.contextPath}/assets/ico/apple-touch-icon-144-precomposed.png">
        <link rel="apple-touch-icon-precomposed" sizes="114x114" href="${pageContext.request.contextPath}/assets/ico/apple-touch-icon-114-precomposed.png">
        <link rel="apple-touch-icon-precomposed" sizes="72x72" href="${pageContext.request.contextPath}/assets/ico/apple-touch-icon-72-precomposed.png">
        <link rel="apple-touch-icon-precomposed" href="${pageContext.request.contextPath}/assets/ico/apple-touch-icon-57-precomposed.png">
    </head>
    <body>
        <!-- Top content -->
        <div class="top-content">	
            <div class="inner-bg">
                <div class="container">
                    <div class="row">
                        <div align=center class="col-sm-8 col-sm-offset-2 text">
                            <table>
                                <tr>
                                   <td style="float:right; margin-right:40px;">
                                      <img alt="" src="${pageContext.request.contextPath}/images/logo.png">
                                   </td>
                                   <td>
                                     <p lang="header">智能存取终端管理平台</p>
                                   </td>
                                </tr>                                
                            </table>
                            <div class="description">
                                 <!--<p> 校园版  </p>-->
                            	<p><label id="versionName"></label></p>        	
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-6 col-sm-offset-3 form-box">
                        	<div class="form-top">
                        		<div class="form-top-left">
                        			<h3>系统登录</h3>
                            		<p>请输入用户名和密码:</p>
                        		</div>
                        		<div class="form-top-right">
                        			<i class="fa fa-key"></i>
                        		</div>
                            </div>
                            <div class="form-bottom">
			                    <form id="formid" role="form" action="${pageContext.request.contextPath}/loginController/login.do" method="post" class="login-form">
			                    	<div class="form-group">
			                    		<label class="sr-only" for="form-username">用户名:</label>
			                        	<input type="text" name="accountcode" placeholder="用户名" class="form-username form-control" id="accountcode">
			                        </div>
			                        <div class="form-group">
			                        	<label class="sr-only" for="form-password">密码:</label>
			                        	<input type="password" name="password" placeholder="密码" class="form-password form-control" id="password">
			                        </div>
			                        <button type="submit" class="btn"  >登录</button>
			                    </form>
		                    </div>
                        </div>
                    </div>
 					<div class="row">
                        <!--<div class="col-sm-6 col-sm-offset-3 social-login">   -->
                         <div class="description">
                        	<p>版权所有 © 杭州东城电子有限公司Version <label id="versionNumber"></label></p>
                    	</div>
                	</div>
            </div>
            
        </div>

        </div>
        <!-- Javascript -->
        <script src="${pageContext.request.contextPath}/assets/js/jquery-1.11.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/bootstrap/js/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/jquery.backstretch.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/scripts.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/plug/artDialog/js/dialog-plus.js"></script>
        <script type="text/javascript">
           $(function(){ 
             /*  $.ajax({
					type : "POST",
					url : "${pageContext.request.contextPath}/systemMessageController/selectSystemMessage.do", 
					success : function(msg) {
						systemMessagelist = eval("(" + msg + ")");
						document.getElementById("versionName").innerText = systemMessagelist.rows[systemMessagelist.rows.length-1].memo;
						document.getElementById("versionNumber").innerText = systemMessagelist.rows[systemMessagelist.rows.length-1].softwareversion;
					}
			    }); */
			});
           
           
         /*   function checkUser(){
        	   var accountcode = document.getElementById("accountcode").value;
        	   var password = document.getElementById("password").value;
        	   if(accountcode == ""){
        		 showMsg("用户名不能为空");
        	     return false;
        	   }
        	   if(password == ""){
        		  showMsg("密码不能为空");
        	      return false;
        	   }
        	   $.ajax({
					type : "POST",
					url : "${pageContext.request.contextPath}/checkUser", 
					data:{accountcode:accountcode,password:password},
					success : function(msg) {
						if(eval("("+msg+")").msg == "false"){
							showMsg("用户名或密码错误！");
						}else{
							document.getElementById("formid").submit();
						}
					}
			    });       	  
        	} */
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
        <!-- 
        [if lt IE 10]>
            <script src="assets/js/placeholder.js"></script>
        [endif]
		 -->
    </body>

</html>