package gameobj;

import controller.ImageController;
import utils.Delay;
import utils.Global;

import java.awt.*;

public class Cloud extends GameObject {
    private Image cloud1;
    private Image cloud2;
    private Image cloud3;
    private Image cloud4;

    public Cloud(int x, int y, int width, int height) {
        super(x, y, width, height);
        cloud1 = ImageController.getInstance().tryGet("/cloud1.png");
        cloud2 = ImageController.getInstance().tryGet("/cloud2.png");
        cloud3 = ImageController.getInstance().tryGet("/cloud3.png");
        cloud4 = ImageController.getInstance().tryGet("/cloud3.png");
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(cloud2,212,32,390,210,null);
        g.drawImage(cloud1,100,60,390,228,null);
        g.drawImage(cloud3,735,-40,356,370,null);
        g.drawImage(cloud4,820,10,530,446,null);
    }

    @Override
    public void update() {

    }
}
