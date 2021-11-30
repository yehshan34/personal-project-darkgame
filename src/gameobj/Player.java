package gameobj;

import controller.AudioResourceController;
import controller.ImageController;
import utils.Delay;
import utils.Global;

import java.awt.*;

import static utils.Global.UNIT;

public class Player extends CharacterObject {

    public enum PlayerState {
        ALIVE,
        DYING,
        DEAD,
        DAMAGED_BY_MONSTER,
    }

    private int id;
    private int num;
    private PlayerAnimator playerAnimator;
    private static final int[] CANDLE_DIR = {0, 1, 2, 3, 2, 1};
    private static final int[] CANDLE_LIGHT = {0, 1, 2, 3, 4, 3, 2, 1};
    private Animator candleAnimator;
    private Animator candleLightAnimator;
    private PlayerState state;
    private Image smallImage;
    private Animator damagedByMonster;
    private boolean getKey;

    public static final int ACTOR_SPEED = 128;

    public Player(int num, int x, int y) {
        super(x, y, UNIT, UNIT);
        this.num = num;
        playerAnimator = new PlayerAnimator();
        candleAnimator = new Animator("/candle_direction_new.png", CANDLE_DIR, 15);
        candleLightAnimator = new Animator("/candleLight.png",CANDLE_LIGHT, 5);
        state = PlayerState.ALIVE;
        smallImage = ImageController.getInstance().tryGet("/" + num + ".png");
        damagedByMonster = new Animator("/deadactors_monster.png");
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public int getNum() {
        return this.num;
    }

    public void getKey() {
        this.getKey = true;
    }

    public boolean isGetKey() {
        return getKey;
    }

    public boolean keyCondition() {
        return getKey;
    }

    public Image getSmallImage() {
        return smallImage;
    }

    public void changeState(PlayerState state) {
        this.state = state;
    }

    public PlayerState getState() {
        return state;
    }

    @Override
    public void paintComponent(Graphics g) {
        if (state == PlayerState.ALIVE) {
            playerAnimator.paint(g, this.num, painter().left(), painter().top()
                    , painter().right(), painter().bottom(), getDirection());
            if (Glow.state == Glow.GlowState.ON) {
                candleAnimator.paint(g, getDirection() - 1, painter().left(), painter().top()
                        , painter().right(), painter().bottom());
                candleLightAnimator.paint(g, getDirection() - 1, painter().left(), painter().top()
                        , painter().right(), painter().bottom());
            }
        }
        if (state == PlayerState.DEAD) {
            g.drawImage(ImageController.getInstance().tryGet("/deadactors_new.png")
                    , painter().left(), painter().top(), painter().right(), painter().bottom()
                    , 0, UNIT * num
                    , UNIT, UNIT * (num + 1), null);
        }
        if (state == PlayerState.DAMAGED_BY_MONSTER) {
            damagedByMonster.paint(g, 0, painter().left(), painter().top()
                    , painter().right(), painter().bottom());
        }

    }

    @Override
    public void update() {
        AudioResourceController.getInstance().shot("/walking2.wav");
        switch (getDirection()) {
            case Global.UP:
                translateY(-ACTOR_SPEED);
                break;
            case Global.DOWN:
                translateY(ACTOR_SPEED);
                break;
            case Global.LEFT:
                translateX(-ACTOR_SPEED);
                break;
            case Global.RIGHT:
                translateX(ACTOR_SPEED);
                break;
            case Global.NO_DIR:
                break;
        }
    }

    public boolean stayWithSamePosition() {
        if (collider().centerX() == lastPositionX()
                && collider().centerY() == lastPositionY()) {
            return true;
        }
        return false;

    }

    public void unmovable() {
        switch (getDirection()) {
            case Global.DOWN:
                translateY(-ACTOR_SPEED);
                break;
            case Global.UP:
                translateY(ACTOR_SPEED);
                break;
            case Global.LEFT:
                translateX(ACTOR_SPEED);
                break;
            case Global.RIGHT:
                translateX(-ACTOR_SPEED);
                break;
        }
    }


    private static class PlayerAnimator {
        private static final int[] ACTOR_WALK = {1, 0, 2, 0};

        private Image img;
        private Image carpet;
        private int count;
        private Delay delay;

        public PlayerAnimator() {
            img = ImageController.getInstance().tryGet("/fouractors_new.png");
            carpet = ImageController.getInstance().tryGet("/carpetAll.png");
            delay = new Delay(30);
            delay.loop();
            this.count = 0;
        }

        public void paint(Graphics g, int num, int left, int top, int right, int bottom, int dir) {
            if (delay.count()) {
                count = ++count % 4;
            }
            int tx = 0;
            int ty = (num % 4) * 128;

            if (dir > 0) {
                if (Glow.state == Glow.GlowState.ON) {
                    g.drawImage(this.carpet, left, top, right, bottom
                            , tx, ty
                            , tx + 128
                            , ty + 128, null);
                }
                g.drawImage(this.img, left, top, right, bottom
                        , tx + 128 * (dir - 1), ty
                        , tx + 128 * dir
                        , ty + 128, null);
            } else {
                g.drawImage(this.img, left, top, right, bottom
                        , tx + ACTOR_WALK[count] * 128, ty
                        , tx + 128 + ACTOR_WALK[count] * 128
                        , ty + 128, null);
            }

        }
    }
}

