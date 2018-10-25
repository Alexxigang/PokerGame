package com.alex.chatroom.controller;

import java.io.IOException;
import java.util.Dictionary;
import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.socket.WebSocketSession;

import com.alex.chatroom.pojo.Message;
import com.alex.chatroom.utils.MapSessionControll;
import com.alex.chatroom.websocket.MyWebSocketHandler;
import com.google.gson.Gson;
import com.poker.PokerCommunicator;

import bridge.domain.BridgeGame;
import bridge.domain.Card;
import bridge.domain.Contract;
import bridge.domain.Deck;
import bridge.domain.PlayerPosition;
import bridge.domain.Trump;

@Controller
public class PlayGame {

	@Autowired
	private MyWebSocketHandler socketHandler;
	private MapSessionControll mapsessioncontroll;

	private BridgeGame bridgegame;

	@RequestMapping("start")
	@ResponseBody
	public String start(HttpServletRequest request) {
		// 玩家开始游戏前的准备,确定好将牌，定约，庄家,其中庄家在定约中有定义
		String roomName = request.getParameter("roomname");
		// 将前台传入的数据转化为Trump对象 ->Trump
		Trump trump = new Gson().fromJson(request.getParameter("trump"), Trump.class);
		Contract contract = new Gson().fromJson(request.getParameter("contract"), Contract.class);
		socketHandler.roomTrumpMap.put(roomName, trump);
		socketHandler.roomContractMap.put(roomName, contract);
		return "1";
	}

	@RequestMapping("play")
	@ResponseBody
	public String play(HttpServletRequest request) throws IOException {

		String userId = request.getParameter("userid");
		String roomName = request.getParameter("roomname");
		String contractShortStr = socketHandler.roomContractMap.get(roomName).getShortString();
		PlayerPosition playerposition = socketHandler.userPositionMap.get(userId);
		Card card=new Gson().fromJson(request.getParameter("card"), Card.class);
		Deck deck = socketHandler.userDeckMap.get(userId);
		WebSocketSession session=socketHandler.userSocketSessionMap.get(userId);
		//获取用户会话
		PokerCommunicator pokercommunicator=new PokerCommunicator(session);
		//将玩家打出的牌发给所有玩家
		pokercommunicator.send(card);
		Dictionary<PlayerPosition, Deck> getGameState = new Hashtable<PlayerPosition, Deck>();
		// 根据玩家位置和该玩家的纸牌创建牌桌状态
		getGameState.put(playerposition, deck);
		bridgegame = new BridgeGame(getGameState, contractShortStr);
		//获取下一个出牌人的位置，用于提醒下一个玩家出牌
		PlayerPosition nextplayer=bridgegame.playCard(card, playerposition);
		return "1";
	}
}
