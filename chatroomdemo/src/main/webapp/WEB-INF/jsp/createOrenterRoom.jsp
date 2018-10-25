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
    <div id="welcome">welcome</div><br/>         
    <form id="room_form" action="<%=basePath%>/chat.action" method="post">
    <input type="hidden" value=${userId } name="userId"/>
    <label id="choose room">请选择创建房间或者进入已有的房间</label>
    <select id="roomchoice" name="roomchoice" onclick="change()">
    <!-- 如果是创建房间，则标记位1，如果是进入已有房间，则标记位0 -->
    			<option value="1" >创建房间</option>
    			<option value="0">进入已有房间</option>
    </select>
    <p id="choice"></p>
    <input id="roomName" name="roomName" type="text"><br/>
           <input type="hidden" id="fromId" name="fromId">
           <button type="submit" id="login" >进入房间主页</button>
    </form>
</body>
<script type="text/javascript">
   var userId='${sessionScope.userId}';//获取从登录页面添加到session中的userId
   welcomeInnerHTML(userId);//添加欢迎用户显示出来
   function welcomeInnerHTML(innerHTML){
  	 document.getElementById('welcome').innerHTML += ','+innerHTML+ '<br/>';
  }
   function change(){
	   var choice=document.getElementById('roomchoice').value;
	   if(choice=="1"){
		   document.getElementById('choice').innerHTML = '请输入房间名进行创建'+ '<br/>';
	   }else{
		   document.getElementById('choice').innerHTML = '请输入房间名进入'+ '<br/>';   
	   }
   }
   
</script>
</html>