package gameobj;

import controller.ImageController;
import utils.Global;

import java.awt.*;

public class TowerCandle extends GameObject {
    private Image img;
    //    private TowerCandle.TowerCandleState state;
    private Animator towerCandleAnimator;
    private int[] towerCandle_Walk = {0, 1, 2, 1, 3, 4, 5};


    public TowerCandle(int x, int y, int dir) {
        super(x, y, 32, 32
                , x, y, 115, 115);
        img = ImageController.getInstance().tryGet("/towercandle.png");
        towerCandleAnimator = new Animator("/towercandle.png", towerCandle_Walk);
    }

    public Image getImg() {
        return this.img;
    }

    @Override
    public void paintComponent(Graphics g) {
        towerCandleAnimator.paint(g, 0, painter().left(), painter().top()
                , painter().right(), painter().bottom());
    }

    @Override
    public void update() {
    }
}