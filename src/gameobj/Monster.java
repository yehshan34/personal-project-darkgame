package gameobj;

import controller.AudioResourceController;
import controller.ImageController;
import utils.AStar;
import utils.Delay;
import utils.Global;
import utils.Node;

import java.awt.*;
import java.util.ArrayList;

import static utils.Global.UNIT;

public class Monster extends CharacterObject {
    public enum MonsterState {
        SLEEP,
        ALIVE,
        DYING,
        DEAD,
    }

    private int num;
    private MonsterState state;
    private ArrayList<Node> trackingPath;
    private Delay delayMove;
    private int recordMonsterStep;
    private static final int[] ACTOR_WALK = {1, 2, 1, 3, 1, 4};
    private Animator monsterAnimator;
    private boolean isInRangeOfFire;


    public Monster(int num, int x, int y, int delayMoveTime) {
        super(x, y, Global.UNIT, Global.UNIT);
        this.num = num;
        monsterAnimator = new Animator("/fourmonsters0.png", ACTOR_WALK);
        state = MonsterState.SLEEP;
        delayMove = new Delay(delayMoveTime);
        delayMove.loop();

    }
    public void changeIsInRangeOfFire(boolean isInRangeOfFire){
        this.isInRangeOfFire = isInRangeOfFire;
    }

    public ArrayList<Node> getTrackingPath() {
        return this.trackingPath;
    }

    public void changeState(MonsterState state) {
        this.state = state;
    }

    public MonsterState getState() {
        return state;
    }

    public void trackingPath(Player target, Node[][] searchMap) {
        AStar aStar = new AStar(searchMap);
//        if (!target.stayWithSamePosition()) {
            trackingPath = aStar.pathSearch(
                    searchMap[(collider().centerY() - 64) / UNIT][(collider().centerX() - 64) / UNIT]
                    , searchMap[(target.collider().centerY() - 64) / UNIT][(target.collider().centerX() - 64) / UNIT]);
            recordMonsterStep = 1;
//        }
    }


    @Override
    public void paintComponent(Graphics g) {
        if (state == MonsterState.DEAD) {
            g.drawImage(ImageController.getInstance().tryGet("/deadmonsters0.png")
                    , painter().left(), painter().top()
                    , painter().right(), painter().bottom()
                    , 0, Global.UNIT * num, Global.UNIT, Global.UNIT * (num + 1)
                    , null);
        }else if ( Glow.state == Glow.GlowState.ON && state == MonsterState.ALIVE) {
            monsterAnimator.paint(g, this.num, painter().left(), painter().top()
                    , painter().right(), painter().bottom());
        }else if(isInRangeOfFire && state == MonsterState.ALIVE){
            monsterAnimator.paint(g, this.num, painter().left(), painter().top()
                    , painter().right(), painter().bottom());
        } else if (Glow.state == Glow.GlowState.OFF && state == MonsterState.ALIVE) {

            g.drawImage(ImageController.getInstance().tryGet("/monster_eyes.png")
                    , collider().left(), collider().top(), 128, 128, null);
        }

    }


    @Override
    public void update() {
        if (delayMove.count()) {
            if (Glow.state == Glow.GlowState.ON && state == MonsterState.ALIVE) {
                if (getTrackingPath() != null && recordMonsterStep < trackingPath.size()) {
                    resetPosition(getTrackingPath().get(trackingPath.size() - 1 - recordMonsterStep).x
                            , getTrackingPath().get(trackingPath.size() - 1 - recordMonsterStep).y
                            ,Global.UNIT,Global.UNIT);
                    recordMonsterStep++;
                }

            }
        }
    }
}
