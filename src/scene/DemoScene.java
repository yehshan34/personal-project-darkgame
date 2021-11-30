package scene;

import camera.Camera;
import camera.MapInformation;
import camera.SmallMap;
import controller.AudioResourceController;
import controller.ImageController;
import controller.SceneController;
import gameobj.*;
import map.*;
import menu.Button;
import menu.MouseTriggerImpl;
import menu.Theme;
import utils.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import static utils.Global.*;
import static utils.Global.CAMERA_HEIGHT;

public class DemoScene extends Scene {
    private Button infoIcon;
    private ArrayList<GameObject> floorArr;
    private ArrayList<GameObject> spikeArr;
    private ArrayList<GameObject> wallArr;
    private ArrayList<GameObject> stairArr;
    private ArrayList<GameObject> smallWallArr;

    private Player player;
    private Delay deadDelay = new Delay(90);
    private Delay lightningOfFrequency = new Delay(60*15);
    private Delay oilLampOfFrequency = new Delay(60*30);
    //閃現時間
    private Delay delayPrint = new Delay(60*3);
    private Delay monsterDelay = new Delay(1);
    private Delay doubleAreaDelay = new Delay(60*5);
    private Glow glow;
    private Key key;
    private Fire fire;
    private Lightning lightning;
    private OilLamp oilLamp;

    private ArrayList<Monster> monsters;

    private Node[][] searchMap;

    private Camera camera;
    private Delay shakeCameraDelay;
    private SmallMap smallMap;

    //針對燈光、玩家移動做的變數判斷
    private int lastCommandCode = 0;
    @Override
    public void sceneBegin() {
        //資訊欄的資訊扭
        infoIcon = new Button(1080,10, Theme.get(4));
        infoIcon.setClickedActionPerformed((int x, int y) -> {
//            SceneController.getInstance().change(new DemoScene());
        });
        //地圖
        MapInformation.setMapInfo(0, 0, Global.MAP_WIDTH, Global.MAP_HEIGHT);
        floorArr = new ArrayList<>();
        spikeArr = new ArrayList<>();
        wallArr = new ArrayList<>();
        stairArr = new ArrayList<>();
        smallWallArr = new ArrayList<>();
        mapInit();
        //創建地圖資訊 怪物追蹤用
        searchMap = new Node[18][18];
        setSearchMap();

        //角色
        deadDelay.loop();
        monsterDelay.loop();
        lightningOfFrequency.loop();
        oilLampOfFrequency.loop();
        player = new Player(1, 448, 1216);
        glow = new Glow(448, 1216, player);
        key = new Key(1856, 960);
        fire = new Fire(576, 320,1);

        monsters = new ArrayList <>();
        monsters.add(new Monster(0, 1216, 1216,60));
        monsters.add(new Monster(1, 1088, 576,75));
        monsters.add(new Monster(2, 832, 1472,45));
        monsters.add(new Monster(3, 1856, 1216,45));
        //鏡頭
        camera = new Camera.Builder(Global.CAMERA_WIDTH, Global.CAMERA_HEIGHT)
                .setCameraStartLocation(300, 1500)
                .setChaseObj(player, 50, 50).gen();
        shakeCameraDelay = new Delay(75);
        //小地圖
        smallMap = new SmallMap(new Camera.Builder(MAP_WIDTH, MAP_HEIGHT)
                .setCameraWindowLocation(CAMERA_WIDTH + 36, CAMERA_HEIGHT - 330)//小視窗顯示的位置
                .setCameraStartLocation(64, 64).gen(),
                0.13, 0.13);
        //music
        AudioResourceController.getInstance().stop("/fire.wav");
        AudioResourceController.getInstance().play("/background.wav");

    }

    @Override
    public void sceneEnd() {

    }

    @Override
    public CommandSolver.MouseListener mouseListener() {
        return (MouseEvent e, CommandSolver.MouseState state, long trigTime) -> {
            MouseTriggerImpl.mouseTrig(infoIcon, e, state);
        };
    }

    @Override
    public CommandSolver.KeyListener keyListener() {
        return new CommandSolver.KeyListener() {
            @Override
            public void keyPressed(int commandCode, long trigTime) {
                if (commandCode == Global.UP || commandCode == Global.DOWN
                        || commandCode == Global.LEFT || commandCode == Global.RIGHT) {


                }
            }

            @Override
            public void keyReleased(int commandCode, long trigTime) {
                if (commandCode == Global.UP || commandCode == Global.DOWN
                        || commandCode == Global.LEFT || commandCode == Global.RIGHT) {
                    playerMove(commandCode);
                    lastCommandCode = commandCode;


                }
                if (commandCode == SPACE) {
                    glow.changeState();
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
//        floorArr.forEach(a -> a.paint(g));
//        spikeArr.forEach(a -> a.paint(g));
//        wallArr.forEach(a -> a.paint(g));
//        stairArr.forEach(a -> a.paint(g));
        if(lightning != null) {
            if (lightning.getState() == GadGetObject.State.GOTTEN && delayPrint.isPlaying()) {
                floorArr.forEach(a -> a.paint(g));
                spikeArr.forEach(a -> a.paint(g));
                wallArr.forEach(a -> a.paint(g));
                stairArr.forEach(a -> a.paint(g));
            }
            if (delayPrint.count()) {
                delayPrint.stop();
                lightning = null;
            }
        }

        closeView(g);

        glow.paint(g);
        if (key != null) {
            key.paint(g);
        }
        fire.paint(g);
        if(lightning != null){
            lightning.paint(g);
        }
        if(oilLamp!= null){
            oilLamp.paint(g);
        }
        monsters.forEach(a -> a.paint(g));
        player.paint(g);
        if(player.getState() == Player.PlayerState.DEAD){

        }
        camera.end(g);

        g.setColor(new Color(153,153,153));
        g.fillRect(1070,0,400,900);

//        g.drawImage(infoIcon,1355,30,50,50,null);
//        g.drawImage(gadgetInfoImg, 1085, 110, 340, 390, null);

        smallMap.start(g);
        smallWallArr.forEach(a -> a.paint(g));
        printFireRange(g);
        if (smallMap.isCollision(player)) {
            smallMap.paint(g, player, player.getSmallImage(), 128, 128);
        }
        if (key != null && smallMap.isCollision(key)) {
            smallMap.paint(g, key, key.getSmallMapImage(), 128, 128);
        }
        if (lightning != null && smallMap.isCollision(lightning)) {
            smallMap.paint(g, lightning, lightning.getSmallMapImage(), 128, 128);
        }
        if (oilLamp != null && smallMap.isCollision(oilLamp)) {
            smallMap.paint(g, oilLamp, oilLamp.getSmallMapImage(), 128, 128);
        }

        smallMap.end(g);
        if (player.keyCondition()) {
            g.drawImage(ImageController.getInstance().tryGet("/getkey_img.png"),
                    1100, 400, 1100 + 128, 400 + 128
                    , 128, 0, 128 + UNIT, UNIT, null);

        } else {
            g.drawImage(ImageController.getInstance().tryGet("/getkey_img.png"),
                    1100, 400, 1100 + 128, 400 + 128
                    , 0, 0, UNIT, UNIT, null);
        }

        infoIcon.paint(g);
    }

    @Override
    public void update() {
        System.out.println(player.collider().centerX() + "," + player.collider().centerY());

        if (shakeCameraDelay.isPlaying()) {
            camera.translateX(random(-5, 5));
            camera.translateY(random(-5, 5));
        }
        if (shakeCameraDelay.count()) {
            shakeCameraDelay.stop();
        }
        if (key != null) {
            floorRandomPosition(key);
            key.update();
            if (glow.isCollisionCenter(key)) {
                key.changeState(GadGetObject.State.DISCOVERED);
            } else {
                key.changeState(GadGetObject.State.UNDISCOVERED);
            }
            if (player.isCollisionCenter(key)) {
                AudioResourceController.getInstance().shot("/catchkey.wav");
                key = null;
                player.getKey();
            }
        }
        if(lightning == null && lightningOfFrequency.count()){
            lightning = new Lightning(floorArr.get(random(0, floorArr.size() - 1)).collider().centerX()
                    , floorArr.get(random(0, floorArr.size() - 1)).collider().centerY());
        }
        if(lightning!=null){
            floorRandomPosition(lightning);
            lightning.update();
            if (glow.isCollisionCenter(lightning)) {
                lightning.changeState(GadGetObject.State.DISCOVERED);
            } else {
                lightning.changeState(GadGetObject.State.UNDISCOVERED);
            }
            if (player.isCollisionCenter(lightning)) {
                //吃到地圖閃現
                AudioResourceController.getInstance().shot("/catchkey.wav");
                lightning.changeState(GadGetObject.State.GOTTEN);
                delayPrint.loop();
            }
        }
        if(fire.getState()!= Fire.FireState.GOTTEN) {
            if (glow.isCollisionCenter(fire)) {
                fire.changeState(Fire.FireState.DISCOVERED);
            }
            if (player.isCollisionCenter(fire)) {
                AudioResourceController.getInstance().play("/fire.wav");
                fire.changeState(Fire.FireState.GOTTEN);
            }
        }
        if(oilLamp == null && oilLampOfFrequency.count()){
            oilLamp = new OilLamp(floorArr.get(random(0, floorArr.size() - 1)).collider().centerX()
                    , floorArr.get(random(0, floorArr.size() - 1)).collider().centerY());
        }
        if(oilLamp!= null){
            floorRandomPosition(oilLamp);
            oilLamp.update();
            if (glow.isCollisionCenter(oilLamp)) {
                oilLamp.changeState(GadGetObject.State.DISCOVERED);
            } else {
                oilLamp.changeState(GadGetObject.State.UNDISCOVERED);
            }
            if (player.isCollisionCenter(oilLamp)) {
                AudioResourceController.getInstance().shot("/catchkey.wav");
                oilLamp = null;
                doubleAreaDelay.play();
                glow.doubleArea();
            }
        }


        wallArr.forEach(g -> {
            if (g.isCollisionCenter(player)) {
                player.unmovable();
            }
        });
        if (deadDelay.count() && player.getState() != Player.PlayerState.ALIVE) {

//            SceneController.getInstance().change(new TransitionScene(0));

        }
        spikeArr.forEach(g -> {
            for (int i = 0; i < monsters.size(); i++) {
                if (g.isCollisionCenter(monsters.get(i))
                        && monsters.get(i).getState() != Monster.MonsterState.DEAD) {
                    monsters.get(i).changeState(Monster.MonsterState.DYING);
                    if (monsters.get(i).getState() == Monster.MonsterState.DYING) {
                        AudioResourceController.getInstance().play("/monster-dying.wav");
                        monsters.get(i).changeState(Monster.MonsterState.DEAD);
                        searchMap[monsters.get(i).collider().centerY()/UNIT][monsters.get(i).collider().centerX()/UNIT]
                                .isObstacle = true;
                    }
                }
                if (player.isCollisionCenter(monsters.get(i))
                        && monsters.get(i).getState() == Monster.MonsterState.DEAD) {
                    player.unmovable();
                }
            }
            if (g.isCollisionCenter(player) && player.getState() != Player.PlayerState.DEAD) {
                player.changeState(Player.PlayerState.DYING);
                if (player.getState() == Player.PlayerState.DYING) {
                    AudioResourceController.getInstance().play("/hit-arg.wav");
                    player.changeState(Player.PlayerState.DEAD);
                }
            }
        });

        stairArr.forEach(g -> {
            if (g.isCollisionCenter(player) && player.keyCondition()) {
                //過關 需要吃到鑰匙
//                SceneController.getInstance().change(new TestFirstScene());
            }
        });

        monsters.forEach(g -> {
            if (player.isCollisionCenter(g)) {
                AudioResourceController.getInstance().play("/headchop.wav");
                player.changeState(Player.PlayerState.DAMAGED_BY_MONSTER);

            }
            if (glow.inRange(g)
                    && g.getState() == Monster.MonsterState.SLEEP) {
                shakeCameraDelay.play();
                AudioResourceController.getInstance().play("/monster-moans.wav");
                g.changeState(Monster.MonsterState.ALIVE);
            }
            if(g.getState()== Monster.MonsterState.ALIVE) {
                g.trackingPath(player, searchMap);
            }
            if (shakeCameraDelay.isStop()) {
                g.update();
            }
        });
        player.setLastPosition(player.collider().centerX(), player.collider().centerY());
        if(doubleAreaDelay.count()){
            glow.resetArea();
        }
        glow.update();
        camera.update();
    }

    public void mapInit() {
        floorArr = Map.genFloor("/Map00.bmp", "/Map00.txt", "floor0");
        floorArr.addAll(Map.genFloor("/Map00.bmp", "/Map00.txt", "floor_wall_down"));
        floorArr.addAll(Map.genFloor("/Map00.bmp", "/Map00.txt", "floor_wall_down_up"));
        floorArr.addAll(Map.genFloor("/Map00.bmp", "/Map00.txt", "floor_wall_left"));
        floorArr.addAll(Map.genFloor("/Map00.bmp", "/Map00.txt", "floor_wall_left_down"));
        floorArr.addAll(Map.genFloor("/Map00.bmp", "/Map00.txt", "floor_wall_left_up"));
        floorArr.addAll(Map.genFloor("/Map00.bmp", "/Map00.txt", "floor_wall_leftx3"));
        floorArr.addAll(Map.genFloor("/Map00.bmp", "/Map00.txt", "floor_wall_right"));
        floorArr.addAll(Map.genFloor("/Map00.bmp", "/Map00.txt", "floor_wall_right_down"));
        floorArr.addAll(Map.genFloor("/Map00.bmp", "/Map00.txt", "floor_wall_right_left"));
        floorArr.addAll(Map.genFloor("/Map00.bmp", "/Map00.txt", "floor_wall_right_up"));
        floorArr.addAll(Map.genFloor("/Map00.bmp", "/Map00.txt", "floor_wall_rightx3"));
        floorArr.addAll(Map.genFloor("/Map00.bmp", "/Map00.txt", "floor_wall_up"));


        spikeArr.addAll(Map.genSpike("/Map00.bmp", "/Map00.txt", "spike0"));
        stairArr = Map.genStair("/Map00.bmp", "/Map00.txt", "stair0_right");
        wallArr = Map.genWall("/Map00.bmp", "/Map00.txt", "brownwall");
        smallWallArr = Map.genWall("/Map19x19.bmp", "/Map19x19.txt", "wall_smallmap");
    }

    public void printFireRange(Graphics g) {
        wallArr.forEach(a -> {
            if(fire.getState()== Fire.FireState.GOTTEN && fire.inRange(a)){
                a.paint(g);
            }
        });
        floorArr.forEach(a -> {
            if (fire.getState()== Fire.FireState.GOTTEN && fire.inRange(a)) {
                a.paint(g);
            }
        });
        spikeArr.forEach(a -> {
            if (fire.getState()== Fire.FireState.GOTTEN && fire.inRange(a)) {
                a.paint(g);
            }
        });
    }

    public void closeView(Graphics g) {
        printFireRange(g);
        if (glow.getState() == Glow.GlowState.ON) {
            wallArr.forEach(a -> {
                if (glow.inRange(a)) {
                    a.paint(g);
                }
            });
            floorArr.forEach(a -> {
                if (glow.inRange(a) || player.isCollisionCenter(a)) {
                    a.paint(g);
                }
                for (int i = 0; i < monsters.size(); i++) {
                    if (monsters.get(i).isCollisionCenter(a)
                            && monsters.get(i).getState() == Monster.MonsterState.ALIVE) {
                        a.paint(g);
                    }
                }
            });
            stairArr.forEach(a -> {
                if (glow.inRange(a) || player.isCollisionCenter(a)) {
                    a.paint(g);
                }
            });
            spikeArr.forEach(a -> {
                if (glow.inRange(a) || player.isCollisionCenter(a)) {
                    a.paint(g);
                }
                for (int i = 0; i < monsters.size(); i++) {
                    if (monsters.get(i).isCollisionCenter(a)
                            && monsters.get(i).getState() == Monster.MonsterState.DEAD) {
                        a.paint(g);
                    }
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
                for (int k = 0; k < floorArr.size(); k++) {
                    if (floorArr.get(k).collider().centerX() == x
                            && floorArr.get(k).collider().centerY() == y) {
                        searchMap[i][j].isObstacle = false;
                    }
                }
                for (int k = 0; k < spikeArr.size(); k++) {
                    if (spikeArr.get(k).collider().centerX() == x
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
            player.setDirection(commandCode);
        }
        if (lastCommandCode == commandCode) {
            player.setDirection(commandCode);
            player.update();
        }
    }

    public void floorRandomPosition(GadGetObject obj) {
        int random = random(0, floorArr.size() - 1);
        obj.setGivenXY(floorArr.get(random).collider().centerX()
                , floorArr.get(random).collider().centerY());
    }


}