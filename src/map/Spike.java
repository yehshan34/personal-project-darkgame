package map;

import controller.ImageController;
import gameobj.CharacterObject;
import gameobj.GameObject;

import java.awt.*;

import static utils.Global.UNIT;

public class Spike extends GameObject {
    private Image img;
    public Spike(String imgPath, int x, int y, int width, int height) {
        super(x+UNIT/2, y+UNIT/2, width, height);
        img = ImageController.getInstance().tryGet(imgPath);
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(img, collider().left(), collider().top(), null);

    }

    @Override
    public void update() {

    }
}
