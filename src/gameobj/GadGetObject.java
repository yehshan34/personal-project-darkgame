package gameobj;

import controller.ImageController;
import utils.Delay;
import utils.Global;

import java.awt.*;

public abstract class GadGetObject extends GameObject{
    private Image img;
    private Image smallMapImage;
    private int givenX;
    private int givenY;
    private State state;
    public enum State{
        UNDISCOVERED,
        DISCOVERED,
        GOTTEN
    }
    public GadGetObject(int x, int y, String imgPath){
        this(x,y,imgPath,imgPath);
    }
    public GadGetObject(int x, int y, String imgPath, String smallMapImgPath) {
        super(x, y, Global.UNIT, Global.UNIT);
        img = ImageController.getInstance().tryGet(imgPath);
        smallMapImage = ImageController.getInstance().tryGet(smallMapImgPath);
        state = State.UNDISCOVERED;
    }
    public Image getImg(){
        return this.img;
    }
    public Image getSmallMapImage(){
        return this.smallMapImage;
    }
    public void changeState(State state){
        this.state = state;
    }
    public State getState(){
        return this.state;
    }
    public void setGivenXY(int x, int y){
        this.givenX = x;
        this.givenY = y;
    }
    public int givenX(){
        return givenX;
    }
    public int givenY(){
        return givenY;
    }



}
