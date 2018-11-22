package com.poker;

import com.alex.chatroom.controller.StateManager;
import bridge.domain.BridgeGame;
import bridge.domain.CallContract;
import bridge.domain.Contract;
import bridge.domain.PlayerPosition;
import bridge.domain.utils.BridgeHelper;
import com.ddsjava.DDSConnect;
import com.ddsjava.DDSException;
import com.ddsjava.dto.BestCard;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 *用于提供AI接口的类，继承于com.Poker.User，使用统一的对外通信接口
 *
 */
public class AiUser extends User {

    private DDSConnect ddsConnect;

    public void startDDSConnnect() {
        ddsConnect = new DDSConnect();
    }

	public AiUser(String userId) {//使用唯一id（房间内唯一）构造AiUser
		super(userId, null);
		ddsConnect = new DDSConnect();
	}

	@Override
	public void receive(Object message) throws IOException {
		/*
		 * 之后将传入的message进行响应解析，可使用房间的getManager()获取状态机,
		 * 使用状态机的getNow方法获取游戏状态，根据游戏状态解析message的类型，状态包含以下情况
		 * 	public static final int BEFORE_START=0;//游戏未开始状态
			public static final int CALLING=1;//叫牌状态
			public static final int PLAYING=2;//打牌状态
			public static final int END=3;//牌打完的状态，此时可接受重新开始消息
		 * 进行合适处理获取Ai的响应结果，使用GameParser类的静态方法fitMessage方法将应
		 * 发回的对象转为String，再使用PokerManager的handleMessage方法发回状态机处理（若不需Ai响应则不发消息）
		 */
		PokerRoom room = getRoom();
        PlayerPosition myPosition = room.findPosition(getUserId());
		if(!myPosition.equals(message)) return;//若不轮到该ai行动则不响应
		StateManager manager = room.getManager();
		int now = manager.getNow();
		if (now == StateManager.BEFORE_START) {
		    // Do nothing.
        } else if (now == StateManager.CALLING) {
            BridgeGame game = manager.getBridgegame();
            String pbnCode = BridgeHelper.toPBN(game);
            try {
                List<Contract> contracts = ddsConnect.calcMakableContracts(pbnCode);
                List<Contract> myContracts = contracts.stream().filter(contract -> contract.getPlayerPosition().equals(myPosition)).collect(Collectors.toList());
                myContracts.sort((c1, c2) -> Integer.compare(c2.getValue(), c1.getValue()));
                CallContract myContract = new CallContract(myContracts.get(0).getShortString());
                int maxCallIndex=manager.findHighestCall();
                if (maxCallIndex<0 || myContract.getValue() > manager.getCallcontractList().get(maxCallIndex).getValue()) {
                    String strMsg = GameParser.fitMessage(myContract);
                    manager.handleMessage(this, strMsg);
                }else {
                	String strMsg=GameParser.fitMessage(new CallContract(CallContract.PASS, myPosition));
                	manager.handleMessage(this, strMsg);
                }
            } catch (DDSException e) {
                // Do nothing.
            }
        } else if (now == StateManager.PLAYING) {
            BridgeGame game = manager.getBridgegame();
            try {
                BestCard bestCard = ddsConnect.solveBoardPBNBestCard(game);
                String strMsg = GameParser.fitMessage(bestCard.getCard());
                manager.handleMessage(this, strMsg);
            } catch (DDSException e) {
                e.printStackTrace();
            }
        } else {
		    // Do nothing.
        }
	}
}
