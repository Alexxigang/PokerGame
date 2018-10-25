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
    
 deckOfCard.java:定义四个方位的玩家，洗牌并将每个玩家的牌进行排序
 
chatroom.java里边的ShuffleDeck方法：房主点击洗牌跳转到该方法中
 @RequestMapping("ShuffleDeck")
	@ResponseBody
	public String ShuffleDeck(HttpServletRequest request) throws IOException {
   
  }
chatroom.java里边的CallContract方法：叫牌
 @RequestMapping("CallContract")
	@ResponseBody
	public String CallContract(HttpServletRequest request) throws IOException {
   
  }

PlayGame.java中的start方法：玩家开始游戏前的准备,确定好将牌，定约，庄家,其中庄家在定约中有定义
  @RequestMapping("start")
	@ResponseBody
	public String start(HttpServletRequest request) {
  
  }
  
PlayGame.java中的play方法：四个玩家打牌，通过发牌跳转到该方法，将数据传递到后台
  @RequestMapping("play")
	@ResponseBody
	public String play(HttpServletRequest request) {
  
  }
  
utils包里的MapSessionControll.java中的sendDeck方法：/将不同位置的玩家的牌发给对应的玩家,并将对应的牌堆放入到userDeckMap中去

对陶荆杰的PokerCommunicator.java的改动：将javax.websocket.Session换成了spring的websocketsession,sendtext方法改为了sendmessage()方法，并且里边的参数改为了TextMessage格式，实际上没有影响
##更加详细的解释见注释
