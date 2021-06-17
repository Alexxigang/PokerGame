# PokerGame
洗牌，发牌，叫牌以及打牌模块（主要参考controller包和util包）

这里在MyWebSocketSession.java中定义了6个静态map
   
   //保存在线用户的会话通道
   public static final Map<String, WebSocketSession> userSocketSessionMap;
  
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
    //存储玩家发送的叫品
    public static final List<CallContract> callcontractList=new ArrayList<>();
    //将与房间对应的每一墩的牌堆放入roomTrckMap中
    public static final Map<String,Trick> roomTrickMap=new LinkedHashMap<String,Trick>(); 
    先把房间名规定为"武大"
    
deckOfCard.java:定义四个方位的玩家，洗牌并将每个玩家的牌进行排序
 
chatroom.java里边的ShuffleDeck方法：房主点击洗牌跳转到该方法中
 
 @RequestMapping("ShuffleDeck")
	
	@ResponseBody
	public String ShuffleDeck(HttpServletRequest request) throws IOException {
	deckOfCard deckofcards = new deckOfCard();// 按顺序初始化牌， 洗牌，给每个玩家的牌进行排序
	//将不同位置的玩家的牌发个对应的玩家,并将对应的牌堆放入到userDeckMap中去
        mapsessioncontroll.sendDeck(mapSessions, deckofcards);
   
  }
chatroom.java里边的CallContract方法：叫牌
 
 @RequestMapping("CallContract")
	
	@ResponseBody
	public String CallContract(HttpServletRequest request) throws IOException {
	        //从前台获取叫品，判断是否为是指向叫品，通过pokercomunicator方法发送该叫品，这里可以通过参数构造该叫品
		String calltype=request.getParameter("calltype");
		//将叫品放入callcontractList
		socketHandler.callcontractList.add(callcontract);
		//发送该叫品显示给玩家
		pokercommunicator.send(callcontract);
		mapsessioncontroll.findendOfCall())//该方法查找有没有连续出现三次非实质性叫品
		//如果出现连续三次非实质性叫品，则通知玩家叫品结束，并将最高叫品发给玩家（最后出的叫品即为最高叫品）
   
  }

PlayGame.java中的start方法：玩家开始游戏前的准备,确定好将牌，定约，庄家,其中庄家在定约中有定义
 
 @RequestMapping("start")
	
	@ResponseBody
	public String start(HttpServletRequest request) {
	//将从前台获取的定约利用Gson的方法转换为contract类
	Contract contract = new Gson().fromJson(request.getParameter("contract"), Contract.class);
	//将定约放入对应的静态map中
	socketHandler.roomContractMap.put(roomName, contract);
  
  }
  
PlayGame.java中的play方法：四个玩家打牌，通过发牌跳转到该方法，将数据传递到后台
  
  @RequestMapping("play")
	
	@ResponseBody
	public String play(HttpServletRequest request) {
	        // 根据玩家位置和该玩家的纸牌创建牌桌状态
		getGameState.put(playerposition, deck);
		//判断是否为第一个出牌的，如果是第一个出牌的，则把明手的牌发给所有玩家
		if(bridgegame.getCardsRemaining()==0) {
		}
		String stringCard=request.getParameter("card");//获取前台的json字符串
		Card card=GameParser.getCard(stringCard);//将card的就送字符串类型解析为card类
		//将玩家打出的牌发给所有玩家，这里把send方法里边把card类解析成json格式
		pokercommunicator.send(card);
		//判断一墩有没有打完，如果打完，则找到赢家，发送给所有玩家，该赢家即为下一个出牌人，
		//在bridgegame中的playCard方法实现了该功能，该方法先判断一墩是否结束，如果未结束，则直接返回邻接的下一个位置，
		//如果一墩的牌数为4，则找到赢家，下一个玩家位置即为赢家位置
		PlayerPosition nextplayer=bridgegame.playCard(card, playerposition);
		pokercommunicator.send(nextplayer);
		//最终将玩家打出去的牌从玩家手中移除
		deck.removeCard(card);
		
  
  }
  
utils包里的MapSessionControll.java中的sendDeck方法：/将不同位置的玩家的牌发给对应的玩家,并将对应的牌堆放入到userDeckMap中去

对组长的PokerCommunicator.java的改动：将javax.websocket.Session换成了spring的websocketsession,sendtext方法改为了sendmessage()方法，并且里边的参数改为了TextMessage格式，实际上没有影响，里边还是把相应的类解析成json格式之后发送给玩家
String contracttoJson=new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create().toJson(contract);

更加详细的解释见代码注释
