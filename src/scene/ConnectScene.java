package scene;

import client.ClientClass;
import controller.ImageController;
import controller.SceneController;
import gameobj.GadGetObject;
import gameobj.Glow;
import gameobj.Player;
import menu.*;
import server.Server;
import utils.CommandSolver;
import utils.Global;

import java.awt.*;

import menu.Button;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectScene extends Scene {
    private Image background;
    private Button serverButton;
    private Button clientButton;
    private Button startButton;

    private EditText editText;
    private String IPAddress;

    private ArrayList<Player> players;


    @Override
    public void sceneBegin() {
        background = ImageController.getInstance().tryGet("/darkmenu.png");
        players = new ArrayList<>();
        players.add(new Player(0, 750, 128));
        players.add(new Player(1, 750, 256));
        players.add(new Player(2, 750, 384));
        players.add(new Player(3, 750, 512));
        players.forEach(a -> a.setDirection(Global.DOWN));
        serverButton = new Button(300, 100, Theme.get(0));
        clientButton = new Button(300, 300, Theme.get(1));
        startButton = new Button(820, 400, Theme.get(0));
        serverButton.setClickedActionPerformed((int x, int y) -> {
            Global.isServer = true;
            Server.instance().create(12345);
            Server.instance().start();
            System.out.println(Server.instance().getLocalAddress()[0]);
            try {
                ClientClass.getInstance().connect("127.0.0.1", 12345);
            } catch (IOException ex) {
                Logger.getLogger(ConnectScene.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        clientButton.setClickedActionPerformed((int x, int y) -> {
            editText = new EditText(350, 500, "輸入IP位址");
            editText.setStyleNormal(new Style.StyleRect(200, 50, true
                    , new BackgroundType.BackgroundColor(new Color(2, 10, 19)))
                    .setTextColor(new Color(128, 128, 128))
                    .setHaveBorder(true)
                    .setBorderColor(new Color(97, 113, 110))
                    .setBorderThickness(5)
                    .setTextFont(new Font("", Font.TYPE1_FONT, 30)));
            editText.setStyleHover(new Style.StyleRect(200, 50, true
                    , new BackgroundType.BackgroundColor(new Color(83, 95, 47)))
                    .setTextColor(new Color(128, 128, 128))
                    .setHaveBorder(true)
                    .setBorderColor(new Color(97, 113, 110))
                    .setBorderThickness(5)
                    .setTextFont(new Font("", Font.TYPE1_FONT, 30)));
            editText.setStyleFocus(new Style.StyleRect(200, 50, true
                    , new BackgroundType.BackgroundColor(new Color(199, 178, 153)))
                    .setTextColor(new Color(128, 128, 128))
                    .setHaveBorder(true)
                    .setBorderColor(new Color(97, 113, 110))
                    .setBorderThickness(5)
                    .setTextFont(new Font("", Font.TYPE1_FONT, 30)));
        });
    }

    @Override
    public void sceneEnd() {

    }

    @Override
    public CommandSolver.MouseListener mouseListener() {
        return (MouseEvent e, CommandSolver.MouseState state, long trigTime) -> {
            if (!serverButton.getIsFocus()) {
                MouseTriggerImpl.mouseTrig(serverButton, e, state);
            }
            if (!clientButton.getIsFocus()) {
                MouseTriggerImpl.mouseTrig(clientButton, e, state);
            }
            players.forEach(a -> {
                if (!startButton.getIsFocus() && a.getDirection() == Global.NO_DIR) {
                    MouseTriggerImpl.mouseTrig(startButton, e, state);
                }
            });

            if (editText != null) {
                MouseTriggerImpl.mouseTrig(editText, e, state);
            }
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
        return new CommandSolver.KeyListener() {
            @Override
            public void keyPressed(int commandCode, long trigTime) {
                if (commandCode == Global.ENTER) {
                    IPAddress = editText.getEditText();
                }

            }

            @Override
            public void keyReleased(int commandCode, long trigTime) {

            }

            @Override
            public void keyTyped(char c, long trigTime) {
                editText.keyTyped(c);
            }
        };
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(background, 0, 0, Global.WINDOW_WIDTH, Global.WINDOW_HEIGHT, null);
        serverButton.paint(g);
        clientButton.paint(g);
        startButton.paint(g);
        if (editText != null) {
            editText.paint(g);
        }
        g.setColor(new Color(199, 178, 153, 50));
        g.fillRect(680, 50, 192, 512 + 50);
        players.forEach(player -> player.paint(g));
    }

    @Override
    public void update() {
        if (IPAddress != null) {
            try {
                ClientClass.getInstance().connect(IPAddress, 12345); // ("SERVER端IP", "SERVER端PORT")
            } catch (IOException ex) {
                Logger.getLogger(LanScene.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        players.forEach(a -> {
            if (a.getDirection() == Global.NO_DIR && startButton.getIsFocus()) {
                SceneController.getInstance().change(new LanScene(a.getNum()));
            }
        });


    }

}
