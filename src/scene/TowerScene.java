package scene;

import controller.AudioResourceController;
import controller.ImageController;
import controller.SceneController;
import gameobj.*;
import utils.CommandSolver;
import utils.Delay;
import utils.Global;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class TowerScene extends Scene {
    private Image background;
    private Image tower;

    private Cloud cloud;
    private TowerCandle towerCandle;
    private TowerFire towerFire;

    private ArrayList<TowerFire> towerFires;
    private Delay delay;
    private Delay delayPaint;

    private int num;
    private int count;
    private int playerNum;

    public TowerScene(int num, int playerNum) {
        this.num = num;
        this.playerNum = playerNum;
    }

    @Override
    public void sceneBegin() {
        Global.cumulativeDeathCount += Global.deathCount;
        System.out.println(Global.deathCount);
        background = ImageController.getInstance().tryGet("/towerscene.png");
        tower = ImageController.getInstance().tryGet("/tower.png");
        cloud = new Cloud(30, 40, 1, 1);
        towerCandle = new TowerCandle(721, 745, 1);
        towerFires = new ArrayList<>();
        delay = new Delay(60 * 4);

        delay.loop();
        delayPaint = new Delay(60*1);
        delayPaint.loop();
        //music
        AudioResourceController.getInstance().stop("/background.wav");
        AudioResourceController.getInstance().play("/fire.wav");

    }

    @Override
    public void sceneEnd() {
        Global.deathCount = 0;
//        System.out.println("塔");
    }


    @Override
    public CommandSolver.MouseListener mouseListener() {
        return (MouseEvent e, CommandSolver.MouseState state, long trigTime) -> {

        };
    }

    @Override
    public CommandSolver.KeyListener keyListener() {
        return new CommandSolver.KeyListener() {
            @Override
            public void keyPressed(int commandCode, long trigTime) {
            }

            @Override
            public void keyReleased(int commandCode, long trigTime) {
            }

            @Override
            public void keyTyped(char c, long trigTime) {
            }
        };
    }


    @Override
    public void paint(Graphics g) {
        g.drawImage(background, -5, -5,1500 , 920, null);
        g.drawImage(tower, 590, 220, 260, 580, null);
        cloud.paint(g);
        towerCandle.paint(g);

        towerFires.forEach(a->a.paint(g));
    }


    @Override
    public void update() {
        while(towerFires.size()<num) {
            towerFire = new TowerFire(723, 635 - count++ * 98, 1);
            towerFires.add(towerFire);
        }
        if (delayPaint.count()) {
            towerFire = new TowerFire(723, 635-num*98,1);
            towerFires.add(towerFire);
        }

        if (delay.count()) {
            switch (num) {
                case 0:
                    SceneController.getInstance().change(new SecondScene(playerNum));
                    break;
                case 1:
                    SceneController.getInstance().change(new ThirdScene(playerNum));
                    break;
                case 2:
                    SceneController.getInstance().change(new FourthScene(playerNum));
                    break;
                case 3:
                    SceneController.getInstance().change(new FifthScene(playerNum));
                    break;
                case 4:
                    //過關
                    break;

            }
        }
    }

}