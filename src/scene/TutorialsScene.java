package scene;

import controller.ImageController;
import controller.SceneController;
import menu.Button;
import menu.Label;
import menu.MouseTriggerImpl;
import menu.Theme;
import utils.CommandSolver;
import utils.Global;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class TutorialsScene extends Scene {
    private Image background;
    private Image tutorials;
    private Button nextButton;

    @Override
    public void sceneBegin() {
        background = ImageController.getInstance().tryGet("/darkmenu.png");
        tutorials = ImageController.getInstance().tryGet("/tutorials_new.png");
        nextButton = new Button(565, 730, Theme.get(6));
        nextButton.setClickedActionPerformed((int x, int y) -> {
            SceneController.getInstance().change(new MenuScene());
        });
    }

    @Override
    public void sceneEnd() {
        System.out.println("基本說明");
    }

    @Override
    public CommandSolver.MouseListener mouseListener() {
        return (MouseEvent e, CommandSolver.MouseState state, long trigTime) -> {
            MouseTriggerImpl.mouseTrig(nextButton, e, state);
        };
    }

    @Override
    public CommandSolver.KeyListener keyListener() {
        return null;
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(background, 0, 0, Global.WINDOW_WIDTH, Global.WINDOW_HEIGHT, null);
        g.drawImage(tutorials, 100, 20, 1300, 820, null);
        nextButton.paint(g);
    }

    @Override
    public void update() {

    }
}
