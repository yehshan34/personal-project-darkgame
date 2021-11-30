//package scene;
//
//import controller.ImageController;
//import controller.SceneController;
//import menu.Button;
//import menu.Label;
//import menu.MouseTriggerImpl;
//import menu.Theme;
//import utils.CommandSolver;
//import utils.Global;
//
//import java.awt.*;
//import java.awt.event.MouseEvent;
//import java.util.ArrayList;
//
//public class Tutorials2Scene extends Scene{
//    private Image background;
//    private Image moreInfo2;
//    private Button backButton;
//
//    @Override
//    public void sceneBegin() {
//        background = ImageController.getInstance().tryGet("/darkmenu.png");
//        moreInfo2 = ImageController.getInstance().tryGet("/moreInfo2.png");
//        backButton = new Button(565, 705, Theme.get(6));
//        backButton.setClickedActionPerformed((int x, int y) -> {
//            SceneController.getInstance().change(new MenuScene());
//        });
//    }
//
//    @Override
//    public void sceneEnd() {
//
//    }
//
//    @Override
//    public CommandSolver.MouseListener mouseListener() {
//        return (MouseEvent e, CommandSolver.MouseState state, long trigTime) -> {
//            MouseTriggerImpl.mouseTrig(backButton, e, state);
//        };
//    }
//
//    @Override
//    public CommandSolver.KeyListener keyListener() {
//        return null;
//    }
//
//    @Override
//    public void paint(Graphics g) {
//        g.drawImage(background, 0, 0, Global.WINDOW_WIDTH, Global.WINDOW_HEIGHT, null);
//        g.drawImage(moreInfo2,950,180, 400 ,450,null);
//        backButton.paint(g);
//    }
//
//    @Override
//    public void update() {
//
//    }
//}
