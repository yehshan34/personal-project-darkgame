package scene;

import controller.ImageController;
import controller.SceneController;
import gameobj.Player;
import menu.MouseTriggerImpl;
import menu.Theme;
import menu.Button;
import utils.CommandSolver;
import utils.Global;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class ChooseRoleScene extends Scene {
    private ArrayList<Player> players;
    private Image background;
    private Image title;
    private Image carpet4;
    private Button startButton;
    private Button backButton;

    @Override
    public void sceneBegin() {
        background = ImageController.getInstance().tryGet("/darkmenu.png");
        title = ImageController.getInstance().tryGet("/choosecharacter.png");
        carpet4 = ImageController.getInstance().tryGet("/carpet4colors.png");
        players = new ArrayList<>();
        players.add(new Player(0, 600, 360));
        players.add(new Player(1, 850, 360));
        players.add(new Player(2, 600, 560));
        players.add(new Player(3, 850, 560));
        players.forEach(a -> a.setDirection(Global.DOWN));
        startButton = new Button(765, 695, Theme.get(5));
        backButton = new Button(360, 695, Theme.get(6));
        backButton.setClickedActionPerformed((int x, int y) -> {
            SceneController.getInstance().change(new MenuScene());
        });
    }

    @Override
    public void sceneEnd() {

    }

    @Override
    public CommandSolver.MouseListener mouseListener() {
        return (MouseEvent e, CommandSolver.MouseState state, long trigTime) -> {

            players.forEach(a -> {
                if (!startButton.getIsFocus() && a.getDirection() == Global.NO_DIR) {
                    MouseTriggerImpl.mouseTrig(startButton, e, state);
                }
            });

            MouseTriggerImpl.mouseTrig(backButton, e, state);

            if (!startButton.getIsFocus()) {
                for (int i = 0; i < players.size(); i++) {
                    if (state == CommandSolver.MouseState.RELEASED
                            && players.get(i).collider().inRange(e.getX(), e.getY())) {
                        if (e.getButton() == 1) {
                            players.forEach(player -> player.setDirection(Global.DOWN));
                            players.get(i).setDirection(Global.NO_DIR);
                        }
                        if (e.getButton() == 3) {
                            players.get(i).setDirection(Global.DOWN);
                        }
                    }
                }
            }
        };
    }

    @Override
    public CommandSolver.KeyListener keyListener() {
        return null;
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(background, 0, 0, Global.WINDOW_WIDTH, Global.WINDOW_HEIGHT, null);
        g.drawImage(title, 255, 60, 980, 150, null);
        g.drawImage(carpet4, 470, 260, 500, 400, null);
        startButton.paint(g);
        players.forEach(player -> player.paint(g));
        backButton.paint(g);

    }

    @Override
    public void update() {
        players.forEach(a -> {
            if (a.getDirection() == Global.NO_DIR && startButton.getIsFocus()) {
                Global.characterNum = a.getNum();
                SceneController.getInstance().change(new FirstScene(a.getNum()));
            }
        });

    }
}
