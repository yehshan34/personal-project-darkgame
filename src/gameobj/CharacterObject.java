package gameobj;

import utils.Global;

import java.awt.*;

public abstract class CharacterObject extends GameObject{

    private int direction;
    private int lastPositionX;
    private int lastPositionY;

    public CharacterObject(int x, int y, int width, int height) {
        super(x, y, width, height);
        this.direction = Global.NO_DIR;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction){
        this.direction = direction;
    }
    public void setLastPosition(int x, int y){
        this.lastPositionX = x;
        this.lastPositionY = y;
    }
    public int lastPositionX(){
        return lastPositionX;
    }
    public int lastPositionY(){
        return lastPositionY;
    }
}
