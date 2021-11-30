package gameobj;

import controller.ImageController;
import utils.Delay;
import utils.Global;

import java.awt.*;

public class Glow extends CharacterObject{

    private Animator glowAnimator;
    private CharacterObject followingObj;
    private boolean isDoubleArea = false;
    private Image img;
    private int[] walk = {0, 1};
    public static GlowState state;

    public enum GlowState{
        ON,
        OFF
    }

    public Glow(int x, int y, CharacterObject following) {
        super(x, y, Global.UNIT, Global.UNIT);
//        glowAnimator = new Animator("/glow1.png",walk, 45);
        img = ImageController.getInstance().tryGet("/newglow.png");
        this.followingObj = following;
        this.state = GlowState.ON;
    }

    public void changeState(){
        if(state == GlowState.ON){
            this.state = GlowState.OFF;
        }else{
            this.state = GlowState.ON;
        }
    }
    public void doubleArea(){
        this.isDoubleArea = true;
    }
    public void resetArea(){
        this.isDoubleArea = false;
    }
    public GlowState getState(){
        return this.state;
    }

    @Override
    public void paintComponent(Graphics g) {
        if(state== GlowState.ON) {
//            glowAnimator.paint(g,0, collider().left(), collider().top()
//                    , collider().right(), collider().bottom());
            g.drawImage(img, collider().left(), collider().top()
                    , collider().right(), collider().bottom(),null);
        }

    }

    @Override
    public void update() {
        switch (followingObj.getDirection()){
            case Global.UP:
                if(isDoubleArea){
                    resetPosition(followingObj.collider().centerX()
                            , followingObj.collider().top() - Global.UNIT
                            , Global.UNIT, Global.UNIT*2);

                }else {
                    resetPosition(followingObj.collider().centerX()
                            , followingObj.collider().centerY() - Global.UNIT
                            , Global.UNIT, Global.UNIT);
                }
                break;
            case Global.DOWN:
                if(isDoubleArea){
                    resetPosition(followingObj.collider().centerX()
                            , followingObj.collider().bottom() + Global.UNIT
                            , Global.UNIT, Global.UNIT*2);

                }else {
                    resetPosition(followingObj.collider().centerX()
                            , followingObj.collider().centerY() + Global.UNIT
                            , Global.UNIT, Global.UNIT);
                }
                break;
            case Global.LEFT:
                if(isDoubleArea) {
                    resetPosition(followingObj.collider().left()- Global.UNIT
                            , followingObj.collider().centerY()
                            , Global.UNIT*2, Global.UNIT);
                }else {
                    resetPosition(followingObj.collider().centerX() - Global.UNIT
                            , followingObj.collider().centerY(),
                            Global.UNIT, Global.UNIT);
                }
                break;
            case Global.RIGHT:
                if(isDoubleArea) {
                    resetPosition(followingObj.collider().right()+ Global.UNIT
                            , followingObj.collider().centerY()
                            , Global.UNIT*2, Global.UNIT);
                }else {
                    resetPosition(followingObj.collider().centerX() + Global.UNIT
                            , followingObj.collider().centerY()
                            , Global.UNIT, Global.UNIT);
                }
                break;
        }
    }
}