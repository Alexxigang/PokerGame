package com.alex.chatroom.utils;

import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import com.alex.chatroom.websocket.MyWebSocketHandler;
import com.poker.PokerCommunicator;

import bridge.domain.PlayerPosition;

@Component
public class MapSessionControll {

	MyWebSocketHandler socketHandler=new MyWebSocketHandler();
	
	public void sendDeck(Map<String, WebSocketSession> mapSessions,deckOfCard deckofcards) throws IOException {
		 //将不同位置的玩家的牌发个对应的玩家,并将对应的牌堆放入到userDeckMap中去
        for (Map.Entry<String, WebSocketSession> mapsession : mapSessions.entrySet()) {
			if(socketHandler.userPositionMap.get(mapsession.getKey())==PlayerPosition.NORTH) {
				//如果用户名对应的位置为北,将对应北方的牌发送给房间第一个用户
				PokerCommunicator pokercommunicator=new PokerCommunicator(mapsession.getValue());
				pokercommunicator.sendtoUser(mapsession.getValue(), deckofcards.deckN);
				socketHandler.userDeckMap.put(mapsession.getKey(), deckofcards.deckN);
			}
			if(socketHandler.userPositionMap.get(mapsession.getKey())==PlayerPosition.EAST) {
				//如果用户名对应的位置为东,将对应东方的牌发送给房间第一个用户
				PokerCommunicator pokercommunicator=new PokerCommunicator(mapsession.getValue());
				pokercommunicator.sendtoUser(mapsession.getValue(), deckofcards.deckE);
				socketHandler.userDeckMap.put(mapsession.getKey(), deckofcards.deckE);
			}
			if(socketHandler.userPositionMap.get(mapsession.getKey())==PlayerPosition.SOUTH) {
				//如果用户名对应的位置为南,将对应南方的牌发送给房间第一个用户
				PokerCommunicator pokercommunicator=new PokerCommunicator(mapsession.getValue());
				pokercommunicator.sendtoUser(mapsession.getValue(), deckofcards.deckS);
				socketHandler.userDeckMap.put(mapsession.getKey(), deckofcards.deckS);
			}
			if(socketHandler.userPositionMap.get(mapsession.getKey())==PlayerPosition.WEST) {
				//如果用户名对应的位置为西,将对应西方的牌发送给房间第一个用户
				PokerCommunicator pokercommunicator=new PokerCommunicator(mapsession.getValue());
				pokercommunicator.sendtoUser(mapsession.getValue(), deckofcards.deckW);
				socketHandler.userDeckMap.put(mapsession.getKey(), deckofcards.deckW);
			}
		}
		
	}
}
