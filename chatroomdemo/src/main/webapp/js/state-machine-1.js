var state = new Object();

state.cardCount=0;//计算当前出牌数的变量
state.unstart = 0;//"游戏未开始";
state.start = 1;//"游戏开始";
state.callPlay=2;//叫牌方询问状态
state.call = 3;//叫牌状态
state.playPos=4;//出牌方询问状态
state.ownCard = 5;//出牌状态
state.gameover = 6;//出牌结束状态;
//state.continueGame = 7;//"继续游戏";
state.currentState  = 0;//置当前状态为游戏未开始;


function onMessage(message)
{
	console.log(message.data);
	console.log("当前状态："+state.currentState);
	if(isCallEnd(message)){
		//FinallyContract=getContract(message);//获取最终定约
		//state.currentState=state.playPos;//置游戏状态为打牌
		state.currentState=state.call;
		//alert("叫牌结束");
		console.log("叫牌结束");
	}
    switch(state.currentState){//根据当前游戏状态对服务器消息进行合适的解析
        case state.unstart:
            myPosition=getPlayerPosition(message);//将服务器发回的玩家位置进行保存
            state.currentState = state.start;//状态变为游戏开始
			break;
        case state.start:
            MyCardGroup=getDeck(message);//将玩家手牌保存至全局变量
			getAddress(MyCardGroup);//调用显示函数显示手牌
            state.currentState=state.callPlay;//置游戏状态为叫牌
			initGame();//初始化游戏相关变量
			break;
		case state.callPlay:
			position=getPlayerPosition(message);//获取当前叫牌方的位置
			console.log("当前方叫牌位置:"+position);//测试
			state.currentState=state.call;
			break;
        case state.call:
			if(!CallContract){//叫牌未结束时
				CallContract=isCallEnd(message);//检测服务器是否发来叫牌结束消息
				console.log("是否结束叫牌:"+CallContract);
				if(!CallContract){
					console.log("如果没有结束叫牌:"+CallContract);
					nowCallContract=getCallContract(message);//获取前玩家的叫品保存至全局变量
					console.log("叫牌类型："+nowCallContract.type+"叫牌的花色："+nowCallContract.suit+"叫牌的阶次："+nowCallContract.rank)
					showCallContract();//将叫牌显示到页面中
					state.currentState=state.callPlay;//置状态为获取叫牌方位置
					//在这里，如果第四个pass叫出之后，又转到callplay状态，无法判断是否结束叫牌
				}
			}else{
				FinallyContract=getContract(message);//获取最终定约
				//showCallContract();//将定约显示到页面中
				state.currentState=state.playPos;//置游戏状态为打牌
				handleCallEnd();//将叫牌按钮隐藏,并将定约显示在桌面上
				CallContract=true;//置叫牌结束标志
			}
             break;
		case state.playPos://出牌方询问状态
			
			if(state.cardCount==1&&ViewCardGroup==null){//若刚刚出完第一张牌则获取明手手牌
				ViewCardGroup=getDeck(message);
				console.log("获得明牌");
				playView(FinallyContract.banker);
			}/*else {
			position=getPlayerPosition(message);//获取当前出牌方位置,注:后台是先发card再发playerposition
			console.log("获得出牌方位置");
			state.currentState=state.ownCard;//置状态为出牌
			}*/
			else{
				console.log("获得打出的牌");
				nowCard=getCard(message);
				state.cardCount++;
				console.log("获得打出的牌");
				
			}
			if(ViewCardGroup!=null){//如果明牌已亮出
				console.log("明牌已亮出");
				state.currentState=state.ownCard;//置状态为出牌
			}	
			//state.currentState=state.ownCard;//置状态为出牌
			break;
        case state.ownCard://出牌状态
			//nowCard=getCard(message);
			console.log("获得出牌人位置");
			position=getPlayerPosition(message);//获取当前出牌方位置
			showCard(nowCard,position);//将玩家发出的牌显示到桌面上
			console.log(position);
			console.log("出过的牌数:"+state.cardCount);
			//state.cardCount++;
			if(state.cardCount==52){
				state.currentState=state.gameover;
				console.log("gemeover");
				finish=true;
			}else{
				state.currentState=state.playPos;
			};
			break;
        case state.gameover:
        	console.log("gameover2");
            break;
		default:

    }
}

function initGame(){
	state.cardCount=0;//置出牌数为0
	CallContract=false;//置叫牌结束标志为false
	ViewCardGroup=null;
}
