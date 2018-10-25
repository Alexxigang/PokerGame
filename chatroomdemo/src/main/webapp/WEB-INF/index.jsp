<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
   String path = request.getContextPath();
   String socketPath = request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>Java后端WebSocket的Tomcat实现</title>
</head>
<body>
    Welcome<br/><input id="text" type="text"/>
    <button onclick="send()">发送消息</button>
    <hr/>
    <button onclick="closeWebSocket()">关闭WebSocket连接</button>
    <hr/>
    <div id="message"></div>
</body>

<script type="text/javascript">
    var websocket = null;
    //判断当前浏览器是否支持WebSocket
    if ('WebSocket' in window) {
        websocket = new WebSocket("ws://"+socketPath+"/ws");
        consol.log("=========WebSocket");
    }
    else if('MozWebSocket' in window ){
        //alert('当前浏览器 Not support websocket')
    	websocket = new MozWebSocket("ws://"+socketPath+"/ws");
    	consol.log("=========MozWebSocket");
    }else {
    	websocket = new SocJs("http://"+socketPath+"/ws/sockjs");
    	console.log("========SockJs");
    }
    console.log("ws://"+socketPath+"/ws");
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
    	//var data = JSON.parse(event.data);//将数据解析成JSON形式
    	var data=$.parseJSON(event.data);
    	console.log("websocket,收到一条消息：",data);
        setMessageInnerHTML(event.data);
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
        document.getElementById('message').innerHTML += innerHTML + '<br/>';
    }

    //关闭WebSocket连接
    function closeWebSocket() {
        websocket.close();
    }

    //发送消息
    function send() {
    	//对象为空
    	if(websocket==undefined||websocket==nul){
    		alert("你的连接已丢失，请退出聊天重新进入");
    		return;
    	}
        var message = document.getElementById('text').value;
        if(message==null){
        	return;
        }else{
        	 //发送
            var data = {};//新建data对象，并规定属性名与相应的值
                    data['fromId'] = sendUid;
                    data['fromName'] = sendName;
                    data['toId'] = to;
                    data['messageText'] = $(".contactDivTrue_right_input").val();
                    webSocket.send(JSON.stringify(data));//将对象封装成JSON后发送至服务器
        }
        //websocket.send(message);
    }
    /*
    //发送
    var data = {};//新建data对象，并规定属性名与相应的值
            data['fromId'] = sendUid;
            data['fromName'] = sendName;
            data['toId'] = to;
            data['messageText'] = $(".contactDivTrue_right_input").val();
            webSocket.send(JSON.stringify(data));//将对象封装成JSON后发送至服务器
            
    //接收
    var message = JSON.parse(event.data);//将数据解析成JSON形式
    
    */
</script>
</html>