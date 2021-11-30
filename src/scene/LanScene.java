package scene;

import camera.Camera;
import camera.MapInformation;
import camera.SmallMap;
import client.ClientClass;
import controller.ImageController;
import controller.SceneController;
import gameobj.*;
import map.Map;
import server.Server;
import utils.CommandSolver;
import utils.Delay;
import utils.Global;
import utils.Node;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import static utils.Global.*;
import static utils.Global.CAMERA_HEIGHT;

public class LanScene extends Scene{
    private ArrayList<GameObject> floorArr;
    private ArrayList<GameObject> spikeArr;
    private ArrayList<GameObject> wallArr;
    private ArrayList<GameObject> stairArr;
    private ArrayList<GameObject> smallWallArr;
    private Node[][] searchMap;

    private ArrayList<Player> players;
    private int playerCount = 0;
    private Delay deadDelay = new Delay(120);
    private ArrayList<Glow> glows;
    private Key key;
    private int keyNum;

    private Camera camera;
    private SmallMap smallMap;
    private int lastCommandCode = 0;
    private int playerNum;
    public LanScene(int num){
        this.playerNum = num;
    }
    @Override
    public void sceneBegin() {
//        connectLanArea();
        MapInformation.setMapInfo(0, 0, Global.MAP_WIDTH, Global.MAP_HEIGHT);
        floorArr = new ArrayList<>();
        spikeArr = new ArrayList<>();
        wallArr = new ArrayList<>();
        stairArr = new ArrayList<>();
        smallWallArr = new ArrayList<>();
        mapInit();
        searchMap = new Node[18][18];
        setSearchMap();

        deadDelay.loop();
        players = new ArrayList<>();
        players.add(new Player(playerNum, 832, 2112-128 ));
        players.get(playerCount++).setId(ClientClass.getInstance().getID());
        glows = new ArrayList<>();
        glows.add(new Glow(832, 2112, players.get(0)));
        key = new Key(1088, 448);

        camera = new Camera.Builder(Global.CAMERA_WIDTH, Global.CAMERA_HEIGHT)
                .setChaseObj(players.get(0), 20, 20).gen();

        //小地圖
        smallMap = new SmallMap(new Camera.Builder(MAP_WIDTH, MAP_HEIGHT)
                .setCameraWindowLocation(CAMERA_WIDTH + 32, CAMERA_HEIGHT - 320)//小視窗顯示的位置
                .setCameraStartLocation(64, 64).gen(),
                0.1, 0.1);

    }

    @Override
    public void sceneEnd() {

    }

    @Override
    public CommandSolver.MouseListener mouseListener() {
        return null;
    }

    @Override
    public CommandSolver.KeyListener keyListener() {
        return new CommandSolver.KeyListener() {
            @Override
            public void keyPressed(int commandCode, long trigTime) {
            }

            @Override
            public void keyReleased(int commandCode, long trigTime) {
                if (commandCode == Global.UP || commandCode == Global.DOWN
                        || commandCode == Global.LEFT || commandCode == Global.RIGHT) {
                    playerMove(commandCode);
                    lastCommandCode = commandCode;
                }
                if (commandCode == SPACE) {
                    glows.get(0).changeState();
                }
                if(commandCode == ESCAPE){
                    ArrayList<String> escapeInfo = new ArrayList<>();
                    escapeInfo.add(String.valueOf(ClientClass.getInstance().getID()));
                    ClientClass.getInstance().sent(NetEvent.PLAYER_DISCONNECT, escapeInfo);
                    ClientClass.getInstance().disConnect();
                    playerCount--;
                    System.exit(0);
                }
            }

            @Override
            public void keyTyped(char c, long trigTime) {
            }
        };

    }

    @Override
    public void paint(Graphics g) {
        camera.start(g);
//        closeView(g);
        floorArr.forEach(a -> a.paint(g));
        spikeArr.forEach(a->a.paint(g));
        wallArr.forEach(a -> a.paint(g));
        stairArr.forEach(a -> a.paint(g));

        glows.forEach(a -> a.paint(g));
        players.forEach(a -> a.paint(g));

        if(key!= null && key.getState() == GadGetObject.State.DISCOVERED) {
            key.paint(g);
        }
        camera.end(g);

        smallMap.start(g);
        smallWallArr.forEach(a -> a.paint(g));
        if(key != null && smallMap.isCollision(key)){
            smallMap.paint(g, key, key.getSmallMapImage(), 128, 128);
        }
        players.forEach(a ->{
            if (smallMap.isCollision(a)) {
                smallMap.paint(g, a, a.getSmallImage(), 128, 128);
            }
        });



        smallMap.end(g);


    }

    @Override
    public void update() {
//        players.forEach(g->{
//            System.out.print(g.isGetKey()+ " ,");
//        });
//        System.out.println("-----------------------------");
        camera.update();
        //傳送自己資訊給別人
        ArrayList<String> playerInfo = new ArrayList<>();
        playerInfo.add(players.get(0).painter().centerX()+"");
        playerInfo.add(players.get(0).painter().centerY()+"");
        playerInfo.add(players.get(0).getDirection()+"");
        playerInfo.add(players.get(0).getNum()+"");
        ClientClass.getInstance().sent(NetEvent.PLAYER_CONNECT, playerInfo);
        ClientClass.getInstance().sent(NetEvent.PLAYER_MOVE, playerInfo);
        //解析資訊
        receivePacket();
        ArrayList<String> keyInfo = new ArrayList<>();

        if(isServer){
            for(int i=0; i<players.size(); i++) {
                if (!players.get(i).isGetKey()) {
                    if(key == null) {
                        key = new Key(floorArr.get(random(0, floorArr.size() - 1)).collider().centerX()
                                , floorArr.get(random(0, floorArr.size() - 1)).collider().centerY());
                        break;
                    }
                }
            }
            if(key != null) {
                RandomKey();
                keyInfo.add(key.collider().centerX()+"");
                keyInfo.add(key.collider().centerY()+"");
                keyInfo.add(key.getState().ordinal()+"");
                ClientClass.getInstance().sent(NetEvent.KEY, keyInfo);

            }
        }
        if(key!= null){
            key.update();
            for(int i=0; i<glows.size(); i++){
                if(glows.get(i).isCollisionCenter(key)){
                    key.changeState(GadGetObject.State.DISCOVERED);
                    break;
                }else{
                    key.changeState(GadGetObject.State.UNDISCOVERED);
                }
            }

            for(int i=0; i<players.size(); i++){
                if(players.get(i).isCollisionCenter(key)){
                    key = null;
                    players.get(i).getKey();
                    ArrayList<String> getKeyStr = new ArrayList<>();
                    getKeyStr.add(players.get(i).getId()+"");
                    ClientClass.getInstance().sent(NetEvent.PLAYER_GET_KEY, getKeyStr);
                    break;
                }
            }
        }


        wallArr.forEach(g -> {
            if (g.isCollisionCenter(players.get(0))) {
                players.get(0).unmovable();
            }
        });
        if(deadDelay.count() && players.get(0).getState() != Player.PlayerState.ALIVE){
//            SceneController.getInstance().change(new TransitionScene(6));
        }
        spikeArr.forEach(g ->{
            if (g.isCollisionCenter(players.get(0))) {
//                players.get(0).changeState(Player.PlayerState.DEAD);
            }
        });
        stairArr.forEach(g -> {
            if(g.isCollisionCenter(players.get(0)) && players.get(0).keyCondition()) {
                //過關 需要吃到鑰匙
                SceneController.getInstance().change(new MenuScene());
            }
        });
    }
    public void mapInit() {
        floorArr = Map.genFloor("/Map1for2players.bmp", "/Map1for2players.txt", "floor0");
        floorArr.addAll(Map.genFloor("/Map1for2players.bmp", "/Map1for2players.txt", "floor_wall_down"));
        floorArr.addAll(Map.genFloor("/Map1for2players.bmp", "/Map1for2players.txt", "floor_wall_down_up"));
        floorArr.addAll(Map.genFloor("/Map1for2players.bmp", "/Map1for2players.txt", "floor_wall_left"));
        floorArr.addAll(Map.genFloor("/Map1for2players.bmp", "/Map1for2players.txt", "floor_wall_left_down"));
        floorArr.addAll(Map.genFloor("/Map1for2players.bmp", "/Map1for2players.txt", "floor_wall_left_up"));
        floorArr.addAll(Map.genFloor("/Map1for2players.bmp", "/Map1for2players.txt", "floor_wall_leftx3"));
        floorArr.addAll(Map.genFloor("/Map1for2players.bmp", "/Map1for2players.txt", "floor_wall_right"));
        floorArr.addAll(Map.genFloor("/Map1for2players.bmp", "/Map1for2players.txt", "floor_wall_right_down"));
        floorArr.addAll(Map.genFloor("/Map1for2players.bmp", "/Map1for2players.txt", "floor_wall_right_left"));
        floorArr.addAll(Map.genFloor("/Map1for2players.bmp", "/Map1for2players.txt", "floor_wall_right_up"));
        floorArr.addAll(Map.genFloor("/Map1for2players.bmp", "/Map1for2players.txt", "floor_wall_rightx3"));
        floorArr.addAll(Map.genFloor("/Map1for2players.bmp", "/Map1for2players.txt", "floor_wall_up"));


        spikeArr.addAll(Map.genSpike("/Map1for2players.bmp", "/Map1for2players.txt", "spike0"));
        stairArr = Map.genStair("/Map1for2players.bmp", "/Map1for2players.txt", "stair0_down");
        wallArr = Map.genWall("/Map1for2players.bmp", "/Map1for2players.txt", "brownwall");
        smallWallArr = Map.genWall("/Map19x19.bmp", "/Map19x19.txt","wall_smallmap");
    }

    public void closeView(Graphics g) {
        if (glows.get(0).getState() == Glow.GlowState.ON) {
            wallArr.forEach(a -> {
                if (glows.get(0).isCollisionCenter(a)) {
                    a.paint(g);
                }
            });
            floorArr.forEach(a ->{
                if(glows.get(0).isCollisionCenter(a) || players.get(0).isCollisionCenter(a)){
                    a.paint(g);
                }
            });
            stairArr.forEach(a ->{
                if(glows.get(0).isCollisionCenter(a) || players.get(0).isCollisionCenter(a)){
                    a.paint(g);
                }
            });
            spikeArr.forEach(a ->{
                if(glows.get(0).isCollisionCenter(a) || players.get(0).isCollisionCenter(a)){
                    a.paint(g);
                }
            });
        }
    }


    public void setSearchMap() {
        for (int i = 0; i < 18; i++) {
            for (int j = 0; j < 18; j++) {
                int x = j * 128 + 64;
                int y = i * 128 + 64;
                searchMap[i][j] = new Node(x, y);
                searchMap[i][j].isObstacle = true;
                for(int k=0; k<floorArr.size(); k++){
                    if(floorArr.get(k).collider().centerX()==x
                            &&floorArr.get(k).collider().centerY() == y) {
                        searchMap[i][j].isObstacle = false;
                    }
                }
                for(int k=0; k<spikeArr.size(); k++){
                    if(spikeArr.get(k).collider().centerX() == x
                            && spikeArr.get(k).collider().centerY() == y) {
                        searchMap[i][j].isObstacle = false;
                    }
                }

            }
        }
    }

    public void printMap() {
        System.out.println("--------------------地圖資訊--------------------------");
        for (int i = 0; i < searchMap.length; i++) {
            for (int j = 0; j < searchMap[i].length; j++) {
                System.out.println(searchMap[i][j].x + ", " + searchMap[i][j].y + "," + searchMap[i][j].isObstacle);
            }
        }
    }

    public void playerMove(int commandCode) {
        if (lastCommandCode != commandCode) {
            glows.get(0).setDirection(commandCode);
            players.get(0).setDirection(commandCode);
            glows.get(0).update();
        }
        if (lastCommandCode == commandCode) {
            glows.get(0).setDirection(NO_DIR);
            glows.get(0).update();
            players.get(0).setDirection(commandCode);
            players.get(0).update();
        }
    }
    public void RandomKey(){
        int random = random(0, floorArr.size()-1);
        key.setGivenXY(floorArr.get(random).collider().centerX()
                , floorArr.get(random).collider().centerY());
    }

    public void connectLanArea(){
        Scanner sc = new Scanner(System.in);

        System.out.println("創建伺服器 => 1, 連接其他伺服器 => 2");
        int opt = sc.nextInt();
        switch (opt) {
            case 1:
                Global.isServer = true;
                Server.instance().create(12345);
                Server.instance().start();
                System.out.println(Server.instance().getLocalAddress()[0]);
                try {
                    ClientClass.getInstance().connect("127.0.0.1", 12345); // ("SERVER端IP", "SERVER端PORT")
                } catch (IOException ex) {
                    Logger.getLogger(LanScene.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case 2:
                System.out.println("請輸入主伺服器IP:");
                try {
                    ClientClass.getInstance().connect(sc.next(), 12345); // ("SERVER端IP", "SERVER端PORT")
                } catch (IOException ex) {
                    Logger.getLogger(LanScene.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
        }

    }
    public void receivePacket(){
        ClientClass.getInstance().consume((int serialNum, int commandCode, ArrayList<String> str) -> {
            if (serialNum == players.get(0).getId()) {
                return;
            }
            switch (commandCode) {
                case NetEvent.PLAYER_CONNECT:
                    boolean isBorn = false;
                    for (int i = 1; i < players.size(); i++) {
                        if (players.get(i).getId() == serialNum) {
                            isBorn = true;
                            break;
                        }
                    }
                    if (!isBorn) {
                        players.add(new Player(Integer.parseInt(str.get(3))
                                ,Integer.parseInt(str.get(0)), Integer.parseInt(str.get(1))));
                        glows.add(new Glow(Integer.parseInt(str.get(0)), Integer.parseInt(str.get(1)),players.get(playerCount)));
                        players.get(playerCount++).setId(serialNum);

                    }
                    break;
                case NetEvent.PLAYER_MOVE:
                    for (int i = 1; i < players.size(); i++) {
                        if (players.get(i).getId() == serialNum) {
                            glows.get(i).setDirection(Integer.parseInt(str.get(2)));
                            glows.get(i).update();
                            players.get(i).resetPosition(Integer.parseInt(str.get(0)), Integer.parseInt(str.get(1))
                                    ,Global.UNIT,Global.UNIT);
                            players.get(i).setDirection(Integer.parseInt(str.get(2)));
                        }
                    }
                    break;
                case NetEvent.PLAYER_DISCONNECT:
                    for(int i=0; i<players.size(); i++){
                        if(players.get(i).getId() == Integer.valueOf(str.get(0))){
                            players.remove(i);
                            playerCount--;
                        }
                    }
                    break;
                case NetEvent.KEY:
                    if(key != null) {
                        key.resetPosition(Integer.parseInt(str.get(0)), Integer.parseInt(str.get(1))
                                ,Global.UNIT,Global.UNIT);
                        key.changeState(GadGetObject.State.values()[Integer.parseInt(str.get(2))]);
                    }
                    break;
                case NetEvent.PLAYER_GET_KEY:
                    players.forEach(g->{
                        if(g.getId() == Integer.valueOf(str.get(0))){
                            g.getKey();
                        }
                    });
            }
        });
    }


}