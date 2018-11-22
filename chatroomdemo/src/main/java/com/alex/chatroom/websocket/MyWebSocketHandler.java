package com.alex.chatroom.websocket;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import com.alex.chatroom.pojo.Message;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.poker.PokerCommunicator;
import com.poker.PokerRoom;
import com.poker.RoomManager;
import com.poker.User;

import bridge.domain.CallContract;
import bridge.domain.Card;
import bridge.domain.Contract;
import bridge.domain.Deck;
import bridge.domain.PlayerPosition;
import bridge.domain.Rank;
import bridge.domain.Suit;
import bridge.domain.Trick;
import bridge.domain.Trump;

/*
 * 这个处理器的@Component注解就是告诉Spring将这个类的对象注入到IOC容器中，
 * 这样在MyWebSocketConfig中才可以通过@Autowired将其自动装载，进而使用。
 */
@Component
@Service
public class MyWebSocketHandler implements WebSocketHandler {

	//@Autowired
    //private youandmeService youandmeService;
	//在线用户的SOCKETsession(存储了所有的通信通道）
  //用来保存房间、用户,会话三者。使用双层Map实现对应关系。
    public  static final Map<String, Map<String, WebSocketSession>> roomUserMap = new HashMap<>(3);

    //将玩家位置和玩家userId放入positionUserMap,之所以为LinkedHashMap，是因为要能够按照添加顺序顺序输出
    public static final Map<String, PlayerPosition> userPositionMap=new LinkedHashMap<String, PlayerPosition>();
    //将每个玩家的用户名和该玩家的牌堆放到userDeckMap中去
    public static final Map<String,Deck> userDeckMap=new HashMap<String,Deck>(); 
    
    //将每个房间的定约保存在roomContractMap
    public static final Map<String,Contract> roomContractMap=new HashMap<String,Contract>();
    //将每个房间的将牌保存在roomTrumpMap中
    public static final Map<String,Trump> roomTrumpMap=new HashMap<String, Trump>();
    //这里对应的是只有一个房间，如果有多个房间还要将该list作为roomCallcontractMap的值来存储
    //存储玩家发送的叫品
   // public static final List<CallContract> callcontractList=new ArrayList<>();
    //将与房间对应的每一墩的牌堆放入roomTrckMap中
    public static final Map<String,Trick> roomTrickMap=new LinkedHashMap<String,Trick>(); 
    /*此处定义与用户绑定的私有属性*/
    // private 
     public static final Map<String, User> userSocketSessionMap=new HashMap<>();//修改原静态map为id与User对应map，防止重复id
    
    /**
     * 在此刷新页面就相当于断开WebSocket连接,原本在静态变量userSocketSessionMap中的
     * WebSocketSession会变成关闭状态(close)，但是刷新后的第二次连接服务器创建的
     * 新WebSocketSession(open状态)又不会加入到userSocketSessionMap中,所以这样就无法发送消息
     * 因此应当在关闭连接这个切面增加去除userSocketSessionMap中当前处于close状态的WebSocketSession，
     * 让新创建的WebSocketSession(open状态)可以加入到userSocketSessionMap中
     * @param webSocketSession
     * @param closeStatus
     * @throws Exception
     */
    //客户端断开连接后会执行afterConnectionClosed()，这时需要将与客户端对应的WebSocketSession从userSocketSessionMap中移除
	@Override
	public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
		// TODO Auto-generated method stub
		 System.out.println("WebSocket:"+webSocketSession.getHandshakeAttributes().get("uid")+"close connection");
		//将用户数据从房间中清理掉
	        exitRoom(webSocketSession);
	        
	}

	//握手实现连接后会执行afterConnectionEstablished()方法，这个方法就是将握手连接后为与客户端实现通信而建立的WebSocketSession
	//加入到静态变量userSocketSessionMap中。
	//握手实现连接后
	@Override
	public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
		// TODO Auto-generated method stub
		 String uid = (String) webSocketSession.getHandshakeAttributes().get("uid");//检测用户名是否已出现
	     User user = new User(uid, webSocketSession);
	     if (userSocketSessionMap.get(uid) == null) {
	            userSocketSessionMap.put(uid, user);
	     }
	        //取得房间名
	        String roomName= (String) webSocketSession.getHandshakeAttributes().get("roomName");
	        //根据对应房间名查找房间
	        PokerRoom room=RoomManager.findRoom(roomName);
	        boolean success=room.add(user);//将用户加入房间
	       //检测是否添加成功
            if(!success) {
            	System.out.println("加入失败");
            	webSocketSession.close();
            }else {//未满时将玩家位置发给客户端
            	//webSocketSession.sendMessage(new TextMessage(userPositionMap.get(uid)));
            	PokerCommunicator communicator=new PokerCommunicator(webSocketSession);
            	webSocketSession.getHandshakeAttributes().put("room", room);//将房间加session参数中
            	PlayerPosition position=room.findPosition(user.getUserId());
            	communicator.send(position);
            	
            }
            System.out.println("当前房间在线人数为:"+room.size()+"人");
	        
	}
    /*
     * 
     * 在前端在用js调用websocket.send()时候，会调用该方法 
     *  客户端一有消息发送至服务器就会自动执行handleMessage()方法
    */
	//Message msg=new Gson().fromJson(webSocketMessage.getPayload().toString(),Message.class);
	//将JSON形式的数据解析成Message对象
	//发送信息前的处理
	@Override
	public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {
		// TODO Auto-generated method stub
		 //if(webSocketMessage.getPayload()==0)return;

	      //得到Socket通道中的数据并转化为Message对象  ->pojo.Message
			String message=webSocketMessage.getPayload().toString();
			System.out.println(webSocketSession.getId());
			if(message.equals("java.nio.HeapByteBuffer[pos=0 lim=0 cap=0]")){
				/*byte[] bs = new byte[1];
				bs[0] = 'i';
				ByteBuffer byteBuffer = ByteBuffer.wrap(bs);
				PingMessage pingMessage = new PingMessage(byteBuffer);
				webSocketSession.sendMessage(pingMessage);*/
				System.out.println("已收到一个pong包：【" + message + "】");
			}else {//消息提交给对应房间处理
				((PokerRoom)(webSocketSession.getHandshakeAttributes().get("room"))).handelMessage(webSocketSession, message);
				
			}
	        
	}

	@Override
	public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {
		// TODO Auto-generated method stub
		//记录日志，准备关闭连接
		System.out.println("websocket异常断开："+webSocketSession.getId()+"已经关闭");
		if(webSocketSession.isOpen()) {
			webSocketSession.close();
		}
		//将用户数据从房间中清理掉
		    exitRoom(webSocketSession);
	        System.out.println("websocketsession.getId():"+webSocketSession.getId());
		//userSocketSessionMap.remove(webSocketSession.getId());
	}

	@Override
	public boolean supportsPartialMessages() {
		// TODO Auto-generated method stub
		return false;
	}
	
	//发送消息测试
	 public boolean sendMessageTest(String roomName,String uid, TextMessage message)
	            throws IOException {
	    	if (roomName == null || uid == null) return false;
	        if (roomUserMap.get(roomName) == null) return false;
	        WebSocketSession session = roomUserMap.get(roomName).get(uid);
	        //WebSocketSession session = userSocketSessionMap.get(uid);
	        if (session != null && session.isOpen()) {
	            session.sendMessage(message);
	        }
	        return true;
	    }
	
	//发送给指定用户信息的实现
    public boolean sendMessageToUser(String roomName,String uid, TextMessage message)
            throws IOException {
    	if (roomName == null || uid == null) return false;
        if (roomUserMap.get(roomName) == null) return false;
        WebSocketSession session = roomUserMap.get(roomName).get(uid);
        //WebSocketSession session = userSocketSessionMap.get(uid);
        if (session != null && session.isOpen()) {
            session.sendMessage(message);
        }
        return true;
    }
    
  //发送信息的实现
    public boolean sendMessageToAll(String roomName,final TextMessage message)
            throws IOException {
    	if (roomName == null) return false;
        if (roomUserMap.get(roomName) == null) return false;
        boolean allSendSuccess = true;
        Collection<WebSocketSession> sessions = roomUserMap.get(roomName).values();
        for (WebSocketSession session : sessions) {
            try {
                if (session.isOpen()) {
                    session.sendMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
                allSendSuccess = false;
            }
        }

        return allSendSuccess;  
    }

    public void exitRoom(WebSocketSession webSocketSession) {
		Iterator<Map.Entry<String,User>> iterator = userSocketSessionMap.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<String,User> entry = iterator.next();
            if(entry.getValue().getSession().getHandshakeAttributes().get("uid")==webSocketSession.getHandshakeAttributes().get("uid")){
                User user=userSocketSessionMap.get(webSocketSession.getHandshakeAttributes().get("uid"));
                PokerRoom room=user.getRoom();//根据该用户获取所在房间
                if(!(user.getUserId().equals(room.getHostId()))){//如果该玩家不是房主
                	room.getPlayers().remove(user);
                	System.out.println("remove players");
                	room.removePositionMap(user);//将该玩家从positionMap中移除
                	System.out.println("remove users");
                }else {//若是房主，还要移除userMap中对应hostId的键值对，移除roomMap对应roomId的键值对来销毁该房间，
                	//因为新进来的房主会调用createroom方法来查找roomMap中的roomId存不存在
                	//还有一个问题，因为该房间是以hostId构造的，所以要不要将该hostId置空，也可以不变，后面新进来的房主还是会设置该房间hostId
                	 //取得房间名
        	        String roomName= (String) webSocketSession.getHandshakeAttributes().get("roomName");
        	        RoomManager.removeMap(user, roomName);//将该房主和房间从对应map中移除;
        	        room.getPlayers().remove(user);
        	        System.out.println("remove players");
                	room.removePositionMap(user);//将该玩家从positionMap中移除
                	System.out.println("remove playerpoition");
                }
                iterator.remove();//改为该remove不会抛出异常，同步modCount和expectedModCount
            	//userSocketSessionMap.remove(webSocketSession.getHandshakeAttributes().get("uid"));
                System.out.println("WebSocket in staticMap:" + webSocketSession.getHandshakeAttributes().get("uid") + "removed");
            }
	}
}
    
}
