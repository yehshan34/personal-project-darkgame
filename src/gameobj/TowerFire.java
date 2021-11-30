package gameobj;

import controller.ImageController;

import java.awt.*;

public class TowerFire extends GameObject {
    private Image img;
    private Animator towerFireAnimator;
    private int[] towerFire_Walk = {0, 1, 2, 3, 4, 5};


    public TowerFire(int x, int y, int dir) {
        super(x, y, 32, 32
                , x, y, 96, 96);
        img = ImageController.getInstance().tryGet("/towerfire1.png");
        towerFireAnimator = new Animator("/towerfire1.png", towerFire_Walk);
    }
    public TowerFire(int x , int y){
        super(x, y, 280, 250);
        img = ImageController.getInstance().tryGet("/towerfire1.png");
        towerFireAnimator = new Animator("/towerfire1.png", towerFire_Walk);
    }
    public Image getImg() {
        return this.img;
    }

    @Override
    public void paintComponent(Graphics g) {
        towerFireAnimator.paint(g, 0, painter().left(), painter().top()
                , painter().right(), painter().bottom());
    }

    @Override
    public void update() {
    }
}