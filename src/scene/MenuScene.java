package scene;


import controller.AudioResourceController;
import controller.ImageController;

import controller.SceneController;
import gameobj.TowerFire;
import menu.*;
import menu.Button;
import menu.Label;
import utils.CommandSolver;
import utils.Delay;
import utils.Global;

import java.awt.*;
import java.awt.event.MouseEvent;

import java.util.ArrayList;

public class MenuScene extends Scene {
    private Image background;
    private Image title;
    private Image candle;
    private Button playButton;
//    private Button multiplayer;
    private Button tutorials;
    private TowerFire towerFire;
    private Delay delay;



    @Override
    public void sceneBegin() {
        background = ImageController.getInstance().tryGet("/darkmenu.png");
        title = ImageController.getInstance().tryGet("/mainmenu0.png");
        candle = ImageController.getInstance().tryGet("/menu_candle.png");
        towerFire = new TowerFire(735, 520);
        delay = new Delay(60 * 10);
        delay.loop();
        playButton = new Button(360, 695, Theme.get(0));
        tutorials = new Button(765, 695, Theme.get(2));
        playButton.setClickedActionPerformed((int x, int y) -> {
            SceneController.getInstance().change(new ChooseRoleScene());
        });
//        multiplayer.setClickedActionPerformed((int x, int y) -> {
//            SceneController.getInstance().change(new ConnectScene());
//        });
        tutorials.setClickedActionPerformed((int x, int y) -> {
            SceneController.getInstance().change(new TutorialsScene());
        });

        //music
        AudioResourceController.getInstance().play("/fire.wav");
    }

    @Override
    public void sceneEnd() {
        System.out.println("主選單場景結束");

    }



    @Override
    public CommandSolver.MouseListener mouseListener() {
        return (MouseEvent e, CommandSolver.MouseState state, long trigTime) -> {
            MouseTriggerImpl.mouseTrig(playButton, e, state);
//            MouseTriggerImpl.mouseTrig(multiplayer, e, state);
            MouseTriggerImpl.mouseTrig(tutorials, e, state);
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
                if(commandCode == Global.Z){
                    Global.Z_CommandCode = Global.Z;
                }
                if(Global.Z_CommandCode == Global.Z){
                    Global.Z_CommandCode = commandCode;

                    switch (commandCode){
                        case Global.ONE:
                            SceneController.getInstance().change(new FirstScene(Global.characterNum));
                            break;
                        case Global.TWO:
                            SceneController.getInstance().change(new SecondScene(Global.characterNum));
                            break;
                        case Global.THREE:
                            SceneController.getInstance().change(new ThirdScene(Global.characterNum));
                            break;
                        case Global.FOUR:
                            SceneController.getInstance().change(new FourthScene(Global.characterNum));
                            break;
                        case Global.FIVE:
                            SceneController.getInstance().change(new FifthScene(Global.characterNum));
                            break;
                    }
                }

            }

            @Override
            public void keyTyped(char c, long trigTime) {
            }
        };
    }


    @Override
    public void paint(Graphics g) {
        g.drawImage(background, 0, 0, Global.WINDOW_WIDTH, Global.WINDOW_HEIGHT, null);
        g.drawImage(title, 330, 5, 770, 710, null);
        g.drawImage(candle,675,610,96,96,null);
        playButton.paint(g);
//        multiplayer.paint(g);
        tutorials.paint(g);
        towerFire.paint(g);
    }


    @Override
    public void update() {
    }
}
