package scene;

import camera.Camera;
import camera.MapInformation;
import controller.SceneController;
import gameobj.GameObject;
import gameobj.Player;
import map.Map;
import utils.CommandSolver;
import utils.Global;

import java.awt.*;
import java.util.ArrayList;

import static utils.Global.SPACE;

public class EndScene extends Scene{
    private ArrayList<GameObject> wallArr;
    private ArrayList<GameObject> stairArr;
    private ArrayList<GameObject> floorArr;
    private Player player;
    private int playerNum;
    private Camera camera;
    private int lastCommandCode = 0;
    public EndScene(int num) {
        this.playerNum = num;
    }
    @Override
    public void sceneBegin() {
        MapInformation.setMapInfo(0, 0, Global.MAP_WIDTH, Global.MAP_HEIGHT);
        floorArr = new ArrayList<>();
        wallArr = new ArrayList<>();
        stairArr = new ArrayList<>();
        mapInit();

        player = new Player(playerNum, 576, 1472);
        camera = new Camera.Builder(Global.CAMERA_WIDTH, Global.CAMERA_HEIGHT)
                .setCameraStartLocation(300, 1500)
                .setChaseObj(player, 50, 50).gen();
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
            }

            @Override
            public void keyTyped(char c, long trigTime) {
            }
        };
    }

    @Override
    public void paint(Graphics g) {
        camera.start(g);
        floorArr.forEach(a -> a.paint(g));
        wallArr.forEach(a -> a.paint(g));
        stairArr.forEach(a -> a.paint(g));
        player.paint(g);
        camera.end(g);

    }

    @Override
    public void update() {
        System.out.println(player.collider().centerX()+","+player.collider().centerY());
        wall();
        stair();
        camera.update();
    }
    public void mapInit() {
        floorArr = Map.genFloor("/Map06.bmp", "/Map06.txt", "floor0");
        floorArr.addAll(Map.genFloor("/Map06.bmp", "/Map06.txt", "floor_wall_up"));
        floorArr.addAll(Map.genFloor("/Map06.bmp", "/Map06.txt", "floor_wall_down"));
        floorArr.addAll(Map.genFloor("/Map06.bmp", "/Map06.txt", "floor_wall_leftx3"));
        floorArr.addAll(Map.genFloor("/Map06.bmp", "/Map06.txt", "floor_wall_down_up"));
        floorArr.addAll(Map.genFloor("/Map06.bmp", "/Map06.txt", "floor_wall_right_left"));
        floorArr.addAll(Map.genFloor("/Map06.bmp", "/Map06.txt", "floor_wall_right_down"));
        floorArr.addAll(Map.genFloor("/Map06.bmp", "/Map06.txt", "floor_wall_left_up"));

        stairArr = Map.genStair("/Map06.bmp", "/Map06.txt", "stair0_right");
        wallArr = Map.genWall("/Map06.bmp", "/Map06.txt", "brownwall");
    }
    public void playerMove(int commandCode) {
        if (player.getState() == Player.PlayerState.ALIVE) {
            if (lastCommandCode != commandCode) {
                player.setDirection(commandCode);
            }
            if (lastCommandCode == commandCode) {
                player.setDirection(commandCode);
                player.update();
            }
        }
    }
    public void wall() {
        wallArr.forEach(g -> {
            if (g.isCollisionCenter(player)) {
                player.unmovable();
            }
        });
    }
    public void stair() {
        stairArr.forEach(g -> {
            if (g.isCollisionCenter(player) && player.keyCondition()) {
//                SceneController.getInstance().change(new TowerScene(0, playerNum));
            }
        });
    }
}
