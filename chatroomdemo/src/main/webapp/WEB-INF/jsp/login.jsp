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
<body>
    Welcome<br/>
    <form id="login_form" action="<%=basePath%>/createOrenterRoom.action" method="post">
           请输入你的用户id<input id="userId" name="userId" type="text"><br/>
           <input type="hidden" id="fromId" name="fromId">
           <button type="submit" id="login" >登录</button>
    </form>
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