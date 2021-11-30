package gameobj;

import controller.AudioResourceController;
import controller.ImageController;
import utils.Global;

import java.awt.*;

public class Fire extends GameObject {
    private Image img;
    private FireState state;
    private Animator candleAnimator;
    private Animator lightAnimator;

    private int[] candleWalk = {0, 1, 2, 1, 3};
    private int[] lightWalk = {0,1,2,3,4,3,2,1};

    public enum FireState {
        UNDISCOVERED,
        DISCOVERED,
        GOTTEN
    }

    public Fire(int x, int y, int dir) {
        super(x, y, Global.UNIT + 192, Global.UNIT + 192
                , x, y, Global.UNIT, Global.UNIT);
        switch (dir) {
            case 1:
                img = ImageController.getInstance().tryGet("/redcandle_left.png");
                candleAnimator = new Animator("/redcandle_left00.png", candleWalk);
                break;
            case 2:
                img = ImageController.getInstance().tryGet("/redcandle_right.png");
                candleAnimator = new Animator("/redcandle_right00.png", candleWalk);
                break;
        }
        lightAnimator = new Animator("/fireLight.png", lightWalk,15);
        state = FireState.UNDISCOVERED;
    }

    public void changeState(FireState state) {
        this.state = state;
    }

    public FireState getState() {
        return this.state;
    }

    public Image getImg() {
        return this.img;
    }

    @Override
    public void paintComponent(Graphics g) {

        if (state == FireState.DISCOVERED) {
            g.drawImage(img, painter().left(), painter().top(), null);
        }
        if (state == FireState.GOTTEN) {
            candleAnimator.paint(g, 0,painter().left(), painter().top()
                    , painter().right(), painter().bottom());
            lightAnimator.paint(g, 0,collider().left(), collider().top()
                    , collider().right(), collider().bottom(),384);
        }
    }

    @Override
    public void update() {
    }
}
