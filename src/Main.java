
import controller.ImageController;
import controller.SceneController;
import menu.BackgroundType;
import menu.Style;
import menu.Theme;
import scene.*;
import utils.CommandSolver;
import utils.GameKernel;
import utils.Global;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

import static utils.Global.*;


public class Main {


    public static void main(final String[] args) {//test
        initTheme(); // 主題
        final JFrame jFrame = new JFrame();
        jFrame.setTitle("Dark");
        jFrame.setSize(Global.WINDOW_WIDTH, Global.WINDOW_HEIGHT);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        SceneController sceneController = SceneController.getInstance();

        sceneController.change(new MenuScene());

        final GameKernel gameKernel = new GameKernel.Builder()
                .input(new CommandSolver.BuildStream()
                        .mouseTrack().forceRelease().subscribe(sceneController)
                        .keyboardTrack()
                        .add(KeyEvent.VK_LEFT, LEFT)
                        .add(KeyEvent.VK_RIGHT, RIGHT)
                        .add(KeyEvent.VK_UP, UP)
                        .add(KeyEvent.VK_DOWN, DOWN)
                        .add(KeyEvent.VK_SPACE, SPACE)
                        .add(KeyEvent.VK_ESCAPE, ESCAPE)
                        .add(KeyEvent.VK_ENTER, ENTER)
                        .add(KeyEvent.VK_1,ONE)
                        .add(KeyEvent.VK_2,TWO)
                        .add(KeyEvent.VK_3,THREE)
                        .add(KeyEvent.VK_4,FOUR)
                        .add(KeyEvent.VK_5,FIVE)
                        .add(KeyEvent.VK_Z,Z)
                        .next().trackChar().keyCleanMode().subscribe(sceneController))
                .paint(sceneController)
                .update(sceneController)
                .gen();

        jFrame.add(gameKernel);
        jFrame.setVisible(true);
        gameKernel.run(IS_DEBUG);
    }

    public static void initTheme() {
        Style sure = new Style.StyleRect(320, 170, new BackgroundType.BackgroundImage(ImageController.getInstance().tryGet("/button1_light.png")));
        Style home = new Style.StyleRect(320, 170, new BackgroundType.BackgroundImage(ImageController.getInstance().tryGet("/button1_light.png")));
        Style play = new Style.StyleRect(320, 170, new BackgroundType.BackgroundImage(ImageController.getInstance().tryGet("/button1.png")));
        Theme.add(new Theme(play, sure, home)); // 開始主題(平常，滑鼠移上去，按下) 0
        Style sure1 = new Style.StyleRect(320, 170, new BackgroundType.BackgroundImage(ImageController.getInstance().tryGet("/button1_light.png")));
        Style home1 = new Style.StyleRect(320, 170, new BackgroundType.BackgroundImage(ImageController.getInstance().tryGet("/button1_light.png")));
        Style play1 = new Style.StyleRect(320, 170, new BackgroundType.BackgroundImage(ImageController.getInstance().tryGet("/button1.png")));
        Theme.add(new Theme(play1, sure1, home1)); // 開始主題(平常，滑鼠移上去，按下) 1
        Style sure2 = new Style.StyleRect(320, 170, new BackgroundType.BackgroundImage(ImageController.getInstance().tryGet("/button3_tutorials_light.png")));
        Style home2 = new Style.StyleRect(320, 170, new BackgroundType.BackgroundImage(ImageController.getInstance().tryGet("/button3_tutorials_light.png")));
        Style play2 = new Style.StyleRect(320, 170, new BackgroundType.BackgroundImage(ImageController.getInstance().tryGet("/button3_tutorials.png")));
        Theme.add(new Theme(play2, sure2, home2)); // 開始主題(平常，滑鼠移上去，按下) 2
        Style sure3 = new Style.StyleRect(320, 170, new BackgroundType.BackgroundImage(ImageController.getInstance().tryGet("/button_next_light.png")));
        Style home3 = new Style.StyleRect(320, 170, new BackgroundType.BackgroundImage(ImageController.getInstance().tryGet("/button_next_light.png")));
        Style play3 = new Style.StyleRect(320, 170, new BackgroundType.BackgroundImage(ImageController.getInstance().tryGet("/button_next.png")));
        Theme.add(new Theme(play3, sure3, home3)); // 開始主題(平常，滑鼠移上去，按下) 3
        Style play4 = new Style.StyleRect(78, 87, new BackgroundType.BackgroundImage(ImageController.getInstance().tryGet("/button_startgame.png")));
        Style sure4 = new Style.StyleRect(78, 87, new BackgroundType.BackgroundImage(ImageController.getInstance().tryGet("/button_startgame_light.png")));
        Style home4 = new Style.StyleRect(78, 87, new BackgroundType.BackgroundImage(ImageController.getInstance().tryGet("/button_startgame_light.png")));
        Theme.add(new Theme(play4, sure4, home4)); // 開始主題(平常，滑鼠移上去，按下) 4
        Style play5 = new Style.StyleRect(320, 170, new BackgroundType.BackgroundImage(ImageController.getInstance().tryGet("/button_startgame.png")));
        Style sure5 = new Style.StyleRect(320, 170, new BackgroundType.BackgroundImage(ImageController.getInstance().tryGet("/button_startgame_light.png")));
        Style home5 = new Style.StyleRect(320, 170, new BackgroundType.BackgroundImage(ImageController.getInstance().tryGet("/button_startgame_light.png")));
        Theme.add(new Theme(play5, sure5, home5)); // 開始主題(平常，滑鼠移上去，按下) 5
        Style sure6 = new Style.StyleRect(320, 170, new BackgroundType.BackgroundImage(ImageController.getInstance().tryGet("/button3_goback_light.png")));
        Style home6 = new Style.StyleRect(320, 170, new BackgroundType.BackgroundImage(ImageController.getInstance().tryGet("/button3_goback_light.png")));
        Style play6 = new Style.StyleRect(320, 170, new BackgroundType.BackgroundImage(ImageController.getInstance().tryGet("/button3_goback.png")));
        Theme.add(new Theme(play6, sure6, home6)); // 開始主題(平常，滑鼠移上去，按下) 6

    }
}




