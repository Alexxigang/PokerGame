<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
   String path = request.getContextPath();
   String socketPath = request.getServerName()+":"+request.getServerPort()+path+"/";
   String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
	+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>websocket聊天房间实现</title>
</head>
<body>
    <div id="welcome">welcome</div><br/>         
           <input type="hidden" id="socketPath" value="<%=socketPath%>">
           <input name="ishost" type="hidden" id="ishost" value="${ishost }"/>
           <input name="userId" type="text" id="userId" value="${userId }"/>
           <input name="roomName" type="hidden" id="roomName" value="${roomName }"/>
           <label id="host"></label>
           <div id="Shuffle_deck"></div>
           <label id="roomsize"></label>
           请输入你要发送的用户id<input id="toId" name="toId" type="text"><br/>
    <input id="messageText" name="message" type="text"/>
    <button onclick="sendtoController()">发送消息</button>
    <hr/>
    <button onclick="closeWebSocket()">关闭WebSocket连接</button>
    <hr/>
    <div id="message"></div>

<script type="text/javascript" src="<%=basePath%>js/jquery-1.8.3.min.js"></script>
<script type="text/javascript">
    //document.getElementById('fromId').value=document.getElementById('userId').value;
    var websocket = null;
    var userId=$('#userId').val();//获取从登录页面添加到session中的userId
    welcomeInnerHTML(userId);//添加欢迎用户显示出来
    var roomName=$('#roomName').val();//获取从登录页面添加到session中的房间号
    var ishost=$('#ishost').val();//从创建房间页面中获取是否为房主的标记
    console.log(roomName+ishost);
    if(ishost=="1"){
    	console.log("房主");
    	document.getElementById('host').innerHTML += '房主'+ '<br/>';
    	//Shuffle_deck
    	document.getElementById('host').innerHTML += '<button onclick="Shuffle_deck()">房主请洗牌</button>'+ '<br/>';
    }
    var roomsize='${sessionScope.roomsize}';
    //document.getElementById('ishost').innerHTML = roomsize+1+ '<br/>';
    console.log(roomName);
    var socketPath=document.getElementById('socketPath').value;
    //判断当前浏览器是否支持WebSocket
    if ('WebSocket' in window) {
        //websocket = new WebSocket("ws://"+socketPath+"/ws");
        websocket = new WebSocket("ws://"+socketPath+"ws.action");
        console.log("=========WebSocket");
    }else if('MozWebSocket' in window ){
        //alert('当前浏览器 Not support websocket')
    	websocket = new MozWebSocket("ws://"+socketPath+"/ws.action");
    	console.log("=========MozWebSocket");
    }else {
    	websocket = new SocJs("http://"+socketPath+"/ws/sockjs.action");
    	console.log("========SockJs");
    }
    console.log("ws://"+socketPath+"/ws.action?userId="+userId+"roomName="+roomName);
    //连接发生错误的回调方法
    websocket.onerror = function () {
        setMessageInnerHTML("WebSocket连接发生错误");
    };

    //连接成功建立的回调方法
    websocket.onopen = function (event) {
        setMessageInnerHTML("WebSocket连接成功");
    }

    //接收到消息的回调方法
    websocket.onmessage = function (event) {
    	console.log('client recieved  a message',event);
    	if(isJsonString(event.data)){
    		console.log("this is a json");
    		var data = JSON.parse(event.data);//将数据解析成JSON形式,把json转换为js对象格式
        	//var data=$.parseJSON(event.data);
    		//var data=JSON.stringify(event.data);
    		console.log(data);
    		if(isJsonString(event.data)){
    			console.log("是json格式");
    		}
        	console.log("websocket,收到一条消息：",data[0]["rank"]);
        	sendMessageInnerHTML(data);
    	}
    	else{
    		console.log("this is not a json");
    		console.log("websocket,收到一条消息：",event.data);
        	sendMessageInnerHTML(event.data);
    	}
    }

    //连接关闭的回调方法
    websocket.onclose = function () {
        setMessageInnerHTML("WebSocket连接关闭");
    }

    //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
    window.onbeforeunload = function () {
        closeWebSocket();
    }

    //将消息显示在网页上
    function setMessageInnerHTML(innerHTML) {
        document.getElementById('message').innerHTML += innerHTML+ '<br/>';
    }
    function welcomeInnerHTML(innerHTML){
    	 document.getElementById('welcome').innerHTML += ','+innerHTML+ '<br/>';
    }
    function sendMessageInnerHTML(innerHTML){
    	//getJsonValue(innerHTML,"messageText") 
    	if(isObject(innerHTML)){
    		//document.getElementById('message').innerHTML +=innerHTML["fromId"]+'发来一条消息:'+ innerHTML["messageText"] + '<br/>'+innerHTML["messageDate"]+'<br/>';
        	document.getElementById('message').innerHTML +=innerHTML[0]["rank"]["score"]+switchshortname(innerHTML[0]["suit"]["shortName"]);
    		console.log(innerHTML[0]["rank"]+switchshortname(innerHTML[0]["suit"]["shortName"]));
    	}
    	if(isString(innerHTML)){
    		//如果是字符串，则输出字符串
    		document.getElementById('message').innerHTML +=innerHTML+'<br/>';
    		console.log(innerHTML);
    	}
    }
    //关闭WebSocket连接
    function closeWebSocket() {
        websocket.close();
    }

    //发送消息
    function send() {
    	
    	
    	//对象为空
    	if(websocket==undefined||websocket==null){
    		alert("你的连接已丢失，请退出聊天重新进入");
    		return;
    	}
        var message = document.getElementById('messageText').value;
        if(message==null){
        	return;
        }else{
        	 //发送
            var data = {};//新建data对象，并规定属性名与相应的值
                    //data['userId']=document.getElementById('userId').value;
                    data['fromId'] = userId;
                    data['roomName'] = roomName;
                    //data['fromName'] = sendName;
                    data['toId'] = document.getElementById('toId').value;
                    data['messageText']=document.getElementById('messageText').value;
                    //data['messageText'] = $(".contactDivTrue_right_input").val();
                    websocket.send(JSON.stringify(data));//将对象封装成JSON后发送至服务器
        }
        //websocket.send(message);
    }
    function isJsonString(str) {
    	//判断是否为json格式
            try {
            	
                if (typeof JSON.parse(str) == "object") {
                    return true;
                }
            } catch(e) {
            }
            return false;
           
        }
    function isString(str){
    	//判断是否为String格式
        try {
        	
            if (typeof str == "string") {
                return true;
            }
        } catch(e) {
        }
        return false;
    }
    function isObject(str){
    	//判断是否为String格式
        try {
        	
            if (typeof str == "object") {
                return true;
            }
        } catch(e) {
        }
        return false;
    }
    function Shuffle_deck(){
    	if(parseInt(roomsize)<3){
    		alert("房间人数未满，请等待房间人满之后洗牌,房间人数为:"+roomsize.toString());
    		console.log(roomsize.toString());
    	}else{
    		$.post("<%=basePath%>/ShuffleDeck.action",function(data){
   			 //alert(data);
   			 if(data=="1"){
   				 alert("洗牌成功");
   			 }else{
   				 alert("洗牌失败");
   			 }
   			}); 
    	}
    }
    function sendtoController(){
    	var message=$('#messageText').val();
    	var toId=$('#toId').val();
    	var data={
    			msg:message,
    			roomname:roomName,
    			toid:toId
    	};
    	//data['roomName']=roomName;
    	$.post("<%=basePath%>sendmessage.action",data,function(data){
  			 //alert(data);
  			 if(data=="1"){
  				 alert("发送成功");
  			 }
  			}); 
    }
    function switchshortname(shortName){//将花色的缩写转变为对应图形
    	switch (shortName)
        {
            case 'S': return "♠";
            case 'H': return "♥";
            case 'C': return "♣";
            case 'D': return "♦";
            //default: return Character.toString(shortName);
        }
    }
</script>
</body>
</html>