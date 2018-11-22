<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body background="pkimage/12345.jpeg"
style=" background-repeat:no-repeat ;
background-size:100% 100%;
background-attachment: fixed;text-align:center;padding-top:15%;">
<div  style="background-color:rgba(255,255,255,0.8);width: 500px; height: 200px;margin:auto;">
 <b> <font size="10">欢迎登陆桥牌系统</font></b> <br/><br/><br/>
   <form id="login_form" action="<%=basePath%>/createOrenterRoom.action" method="post">
           请输入你的用户id<input id="userId" name="userId" type="text"><br/><br/>
           <input type="hidden" id="fromId" name="fromId">
           <button type="submit" id="login" >登录</button>
    </form>
   </div>

<script type="text/javascript" src="<%=basePath%>js/jquery-1.8.3.min.js"></script>
<script type="text/javascript">
     function loginfunc(){
    	 var userId=$('#userId').val();
    	 var data={
    		"userId":userId 
    	 };
				$.post("<%=basePath%>logintest.action",data,function(data){
					
					if(data=="1"){
						alert("登录成功！"+data);
					}else{
						alert("登录失败！"+data);
					}
					<!--window.location.reload();-->
				});   
						
			}
</script>
</body>
</html>