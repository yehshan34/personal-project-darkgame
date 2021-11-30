package scene;

import controller.AudioResourceController;
import controller.SceneController;
import gameobj.Player;
import utils.CommandSolver;
import utils.Delay;
import utils.Global;

import java.awt.*;
import java.awt.event.MouseEvent;

public class TransitionScene extends Scene{
    private Player player;
    private Delay delay;
    private int num;
    private int playerNum;
    public TransitionScene(int num, int playerNum){
        this.num = num;
        this.playerNum = playerNum;
    }
    @Override
    public void sceneBegin() {

        AudioResourceController.getInstance().stop("/background.wav");
        player = new Player(playerNum,720 , 440);
        delay = new Delay(60*2);
        delay.play();
//        testPop = new PopupWindowScene(50,50);


    }

    @Override
    public void sceneEnd() {

    }

    @Override
    public CommandSolver.MouseListener mouseListener() {
        return (e, state, trigTime)->{
            if(state != null){
//                switch (state){
//                    case MOVED:
//                        testPop.show();
//                        break;
//                }
            }
        };
    }

    @Override
    public CommandSolver.KeyListener keyListener() {
        return null;
    }

    @Override
    public void paint(Graphics g) {
        player.paint(g);

//        testPop.paint(g);
    }

    @Override
    public void update() {
        AudioResourceController.getInstance().stop("/background.wav");
        player.setDirection(Global.NO_DIR);
        if(delay.count()){
            switch (num){
                case 0:
                    SceneController.getInstance().change(new FirstScene(playerNum));
                    break;
                case 1:
                    SceneController.getInstance().change(new SecondScene(playerNum));
                    break;
                case 2:
                    SceneController.getInstance().change(new ThirdScene(playerNum));
                    break;
                case 3:
                    SceneController.getInstance().change(new FourthScene(playerNum));
                    break;
                case 4:
                    SceneController.getInstance().change(new FifthScene(playerNum));
                    break;
            }

        }
    }
}
