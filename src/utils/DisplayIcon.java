package utils;

import controller.ImageController;

import java.awt.*;

import static utils.Global.UNIT;

public class DisplayIcon {

    private Image img;
    private boolean isTouch;
    private int x;
    private int y;

    public DisplayIcon(String imagePath, int x, int y){
        this.img = ImageController.getInstance().tryGet(imagePath);
        this.x = x;
        this.y = y;
    }
    public void touch(){
        this.isTouch = true;
    }
    public void noTouch(){
        this.isTouch = false;
    }
    public boolean isTouch() {
        return isTouch;
    }
    public void paint(Graphics g){
        if(isTouch){
            g.drawImage(img, this.x, this.y, this.x + 128, this.y + 128
                    , 128, 0, 128 + UNIT, UNIT, null);
        }else{
            g.drawImage(img,
                    this.x, this.y, this.x + 128, this.y + 128
                    , 0, 0, UNIT, UNIT, null);
        }
    }
}
