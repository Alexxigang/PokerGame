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
			<title>gametest</title>
			<script type="text/javascript" src="<%=basePath%>js/jquery-1.8.3.min.js"></script>
			<script type="text/javascript" src="<%=basePath%>js/communicator.js"></script>
			<script type="text/javascript" src="<%=basePath%>js/desk.js"></script>
			<script type="text/javascript" src="<%=basePath%>js/commonVar.js"></script>
			<script type="text/javascript" src="<%=basePath%>js/state-machine-1.js"></script>
			<script type="text/javascript">
				//设置路径
				socketurl="ws://"+"<%=socketPath%>"+"ws.action";
			</script>
	</head>
	<body>
	<div id="container" style="width:1300px">
	
	   <!-- <div id="header" style="background-color:#FFA500;height:150px;width:1300px" border:3px solid #000>
	        <!--  个人信息  给分</div>-->
	
	    <div id="player3" style="background-color:#54B854;height:130px;width:1300px" border:3px solid #000>
	        <!--  最上方玩家的牌-->
	     <td></td><img src="" id = "player20" style='display:block' border=0></td>
	        <td></td><img src="" id = "player21" style='display:block' border=0></td>
	        <td></td><img src="" id = "player22" style='display:block' border=0></td>
	        <td></td><img src="" id = "player23" style='display:block' border=0></td>
	        <td></td><img src="" id = "player24" style='display:block' border=0></td>
	        <td></td><img src="" id = "player25" style='display:block' border=0></td>
	        <td></td><img src="" id = "player26" style='display:block' border=0></td>
	        <td></td><img src="" id = "player27" style='display:block' border=0></td>
	        <td></td><img src="" id = "player28" style='display:block' border=0></td>
	        <td></td><img src="" id = "player29" style='display:block' border=0></td>
	        <td></td><img src="" id = "player210" style='display:block' border=0></td>
	        <td></td><img src="" id = "player211" style='display:block' border=0></td>
	        <td></td><img src="" id = "player212" style='display:block' border=0></td>
	
	    </div>
	<div>
	         <div id="player2" style="background-color:#54B854;height:520px;width:340px;float:left;" border:3px solid #000>
	        <!--  左边玩家的牌-->
	             <table>
	                 <tr>
	                     <td><img src="" id = "player10" style='display:block;CURSOR:pointer' onclick="picked(this.src,this.id)" border=0></td>
	                     <td><img src="" id = "player11" style='display:block;CURSOR:pointer' onclick="picked(this.src,this.id)" border=0></td>
	                 <td><img src="" id = "player12" style='display:block;CURSOR:pointer' onclick="picked(this.src,this.id)" border=0></td>
	                 <td><img src="" id = "player13" style='display:block;CURSOR:pointer' onclick="picked(this.src,this.id)" border=0></td>
	             </tr><tr>
	                 <td><img src="" id = "player14" style='display:block;CURSOR:pointer' onclick="picked(this.src,this.id)" border=0></td>
	                 <td><img src="" id = "player15" style='display:block;CURSOR:pointer' onclick="picked(this.src,this.id)" border=0></td>
	                 <td><img src="" id = "player16" style='display:block;CURSOR:pointer' onclick="picked(this.src,this.id)" border=0></td>
	                 <td><img src="" id = "player17" style='display:block;CURSOR:pointer' onclick="picked(this.src,this.id)" border=0></td>
	             </tr><tr>
	                 <td><img src="" id = "player18" style='display:block;CURSOR:pointer' onclick="picked(this.src,this.id)" border=0></td>
	                 <td><img src="" id = "player19" style='display:block;CURSOR:pointer' onclick="picked(this.src,this.id)" border=0></td>
	                 <td><img src="" id = "player110" style='display:block;CURSOR:pointer' onclick="picked(this.src,this.id)" border=0></td>
	                 <td><img src="" id = "player111" style='display:block;CURSOR:pointer' onclick="picked(this.src,this.id)" border=0></td>
	             </tr>
	                 <tr>
	                     <td>
	                         <img src="" id = "player112" style='display:block;CURSOR:pointer' onclick="picked(this.src,this.id)" border=0>
	                     </td>
	                 </tr>
	             </table>
	
	        </div>
	
	        <div id="center1" style="background-color:#54B854;height:520px;width:620px;float:left;" border:3px solid #000>
	        <!-- 中间部分-->
	
	
	
	            <div id="btn0"  >
	                <button type="button" id="bbb" onClick="sendMessage('start');$(this).hide();">test</button>
	           <button type="button" id="btn1" onClick="getAddress(MyCardGroup)">发牌</button>
	           <button type="button" id="btn2" value="callpass" onclick="call(this.value)">PASS</button>
	            <button type="button" id="btn3" onclick="nextCall(this.value)" value="num1">1</button>
	            <button type="button" id="btn4" onclick="nextCall(this.value)" value="num2">2</button>
	            <button type="button" id="btn5" onclick="nextCall(this.value)" value="num3">3</button>
	            <button type="button" id="btn6" onclick="nextCall(this.value)" value="num4">4</button>
	            <button type="button" id="btn7" onclick="nextCall(this.value)" value="num5">5</button>
	            <button type="button" id="btn8" onclick="nextCall(this.value)" value="num6">6</button>
	            <button type="button" id="btn9" onclick="nextCall(this.value)" value="num7">7</button>
	                <br>
	            </div>
	            <br>
	            <button type="button" id="btn10" style="display:none" onclick="call(this.value)" value="D">方块</button>
	            <button type="button" id="btn11" style="display:none" onclick="call(this.value)" value="H">红桃</button>
	            <button type="button" id="btn12" style="display:none" onclick="call(this.value)" value="S">黑桃</button>
	            <button type="button" id="btn13" style="display:none" onclick="call(this.value)" value="C">梅花</button>
	            <button type="button" id="btn14" style="display:none" onclick="call(this.value)" value="NT">NT</button>
	            <div id="color"   >
	
	            </div>
	            <div id="callcontract">
	            
	            </div>
	            <div id="Message"  >
	
	            </div>
	            <button type="button" onclick="toggle()">出牌</button>
	            <br>
	            <table>
	                <tr><td><img src="" id = "player4N" style='display:block' border=0></td>
	            <td><img src="" id = "player4E" style='display:block' border=0></td>
	            </tr><tr>
	            <td><img src="" id = "player4W" style='display:block' border=0></td>
	            <td><img src="" id = "player4S" style='display:block' border=0></td>
	            </tr>
	            </table>
	        </div>
	
	        <div id="player4" style="background-color:#54B854;height:520px;width:340px;float:left;" border:3px solid #000>
	        <!--  右边玩家的牌-->
	            <table>
	                <tr>
	                    <td><img src="" id = "player30" style='display:block;CURSOR:pointer' onclick="picked(this.src,this.id)" border=0></td>
	                    <td><img src="" id = "player31" style='display:block;CURSOR:pointer' onclick="picked(this.src,this.id)" border=0></td>
	                    <td><img src="" id = "player32" style='display:block;CURSOR:pointer' onclick="picked(this.src,this.id)" border=0></td>
	                <td><img src="" id = "player33" style='display:block;CURSOR:pointer' onclick="picked(this.src,this.id)" border=0></td>
	                 </tr><tr>
	                     <td><img src="" id = "player34" style='display:block;CURSOR:pointer' onclick="picked(this.src,this.id)" border=0></td>
	                <td><img src="" id = "player35" style='display:block;CURSOR:pointer' onclick="picked(this.src,this.id)" border=0></td>
	                    <td><img src="" id = "player36" style='display:block;CURSOR:pointer' onclick="picked(this.src,this.id)" border=0></td>
	                <td><img src="" id = "player37" style='display:block;CURSOR:pointer' onclick="picked(this.src,this.id)" border=0></td>
	                </tr><tr>
	                    <td><img src="" id = "player38" style='display:block;CURSOR:pointer' onclick="picked(this.src,this.id)" border=0></td>
	                <td><img src="" id = "player39" style='display:block;CURSOR:pointer' onclick="picked(this.src,this.id)" border=0></td>
	                <td><img src="" id = "player310" style='display:block;CURSOR:pointer' onclick="picked(this.src,this.id)" border=0></td>
	                <td><img src="" id = "player311" style='display:block;CURSOR:pointer' onclick="picked(this.src,this.id)" border=0></td>
	                </tr>
	                <tr>
	                    <td>
	                        <img src="" id = "player312" style='display:block;CURSOR:pointer' onclick="picked(this.src,this.id)" border=0>
	                    </td>
	                </tr>
	            </table>
	        </div>
	    </div>
	
	    <div id="player1" style="background-color:#54B854;clear:both;text-align:center;height:130px;width:1300px" border:3px solid #000>
	        <!--  最下方玩家的牌-->
	        <table><tr>
	            <!--  <td><img src="style/img/方块3.jpg" id = "player00" style='display:block;CURSOR:pointer' onclick="picked(this.src,this.id)" border=0></td>-->
	            <td><img src="" id = "player00" style='display:block;CURSOR:pointer' onclick="picked(this.src,this.id)" border=0></td>
	            <td><img src="" id = "player01" style='display:block;CURSOR:pointer' onclick="picked(this.src,this.id)" border=0></td>
	            <td><img src="" id = "player02" style='display:block;CURSOR:pointer' onclick="picked(this.src,this.id)" border=0></td>
	            <td><img src="" id = "player03" style='display:block;CURSOR:pointer' onclick="picked(this.src,this.id)" border=0></td>
	            <td><img src="" id = "player04" style='display:block;CURSOR:pointer' onclick="picked(this.src,this.id)" border=0></td>
	            <td><img src="" id = "player05" style='display:block;CURSOR:pointer' onclick="picked(this.src,this.id)" border=0></td>
	            <td><img src="" id = "player06" style='display:block;CURSOR:pointer' onclick="picked(this.src,this.id)" border=0></td>
	            <td><img src="" id = "player07" style='display:block;CURSOR:pointer' onclick="picked(this.src,this.id)" border=0></td>
	            <td><img src="" id = "player08" style='display:block;CURSOR:pointer' onclick="picked(this.src,this.id)" border=0></td>
	            <td><img src="" id = "player09" style='display:block;CURSOR:pointer' onclick="picked(this.src,this.id)" border=0></td>
	            <td><img src="" id = "player010" style='display:block;CURSOR:pointer' onclick="picked(this.src,this.id)" border=0></td>
	            <td><img src="" id = "player011" style='display:block;CURSOR:pointer' onclick="picked(this.src,this.id)" border=0></td>
	            <td><img src="" id = "player012" style='display:block;CURSOR:pointer' onclick="picked(this.src,this.id)" border=0></td>
	
	        </tr></table>
	    </div>
	
	</div>

		<!-- 测试部分 -->
		<script type="text/javascript">
			initConnect(onMessage);
			//sendMessage("test");
		</script>
	</body>
</html>