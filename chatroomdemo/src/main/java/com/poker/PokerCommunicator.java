package com.poker;
/**
 * poker game 通信类，包装了网页客户端与后台的通信
 * @author 陶荆杰
 */

import java.io.IOException;
//import javax.websocket.RemoteEndpoint.Basic;
//import javax.websocket.Session;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import com.google.gson.GsonBuilder;

import bridge.domain.CallContract;
import bridge.domain.Card;
import bridge.domain.Deck;
import bridge.domain.PlayerPosition;
import bridge.domain.utils.BridgeHelper;


public class PokerCommunicator {
	private WebSocketSession sender;
	public PokerCommunicator(WebSocketSession session) {
		/**
		 * 构造方法，需传入与客户通信的session
		 */
		this.sender=session;
	}
	
	public void send(Deck deck)  throws IOException{
		/**
		 * 将玩家手牌信息发给该玩家的方法，这里先转换为json格式再发送
		 */
		//sender.sendMessage(new TextMessage(BridgeHelper.deckToPBNHand(deck)));
		String decktoJson=new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create().toJson(deck);
		sender.sendMessage(new TextMessage(decktoJson));
	}
	public boolean sendtoUser(WebSocketSession session,Deck deck) throws IOException {
		/**
		 * 将玩家手牌信息发送给指定玩家
		 */
		if (session == null) return false;
       
        //WebSocketSession session = userSocketSessionMap.get(uid);
        if (session != null && session.isOpen()) {
            session.sendMessage(new TextMessage(BridgeHelper.deckToPBNHand(deck)));
        }
        return true;
	}
	public void send(PlayerPosition position) throws IOException {
		/**
		 * 将当前行动玩家方信息发给玩家的方法
		 */
		sender.sendMessage(new TextMessage(position.getFullName().substring(0, 1)));
	}
	public void send(Card card) throws IOException{//向对应玩家发送其他玩家出的牌的方法
		sender.sendMessage(new TextMessage(card.getSuit().getShortName()+card.getRank().toString()));
	}
	
	public void send(CallContract contract)  throws IOException{
		/**
		 * 发送玩家叫品的方法，这里先转换为json格式再传参
		 */
		String contracttoJson=new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create().toJson(contract);
		sender.sendMessage(new TextMessage(contracttoJson));
	}
	

	public void send(String message) throws IOException{
		/**
		 * 发送牌局信息以外的消息时使用的方法
		 */
		sender.sendMessage(new TextMessage(message));
	}
	
}
