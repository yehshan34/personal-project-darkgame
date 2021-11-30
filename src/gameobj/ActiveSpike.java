package gameobj;

import controller.ImageController;
import utils.Delay;
import utils.Global;

import java.awt.*;

public class ActiveSpike extends GameObject{
    private Animator activeSpike;

    private int[] walk = {0,1,2,3};
    private Image img;

    public ActiveSpike(int x, int y) {
        super(x, y, Global.UNIT, Global.UNIT);
        activeSpike = new Animator("/activespike.png",walk,30);
        img = ImageController.getInstance().tryGet("/activespike.png");


    }

    @Override
    public void paintComponent(Graphics g) {
        activeSpike.paint(g, collider().left(), collider().top()
                    , collider().right(), collider().bottom());
    }

    @Override
    public void update() {
        if(activeSpike.getCount() ==0){

        }

    }
}
