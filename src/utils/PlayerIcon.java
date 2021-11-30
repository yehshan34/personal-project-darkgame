package utils;

import controller.ImageController;

import java.awt.*;

public class PlayerIcon {
    private Image image;
    private int x;
    private int y;

    public PlayerIcon(int x, int y) {
        image = ImageController.getInstance().tryGet("/fouractors_deadcount.png");
        this.x = x;
        this.y = y;
    }

    public void paint(Graphics g, int playerNum, int degreeNum) {
        int tx;

        if(degreeNum>5){
            tx = Global.UNIT*3;
        }else if (degreeNum >3) {
            tx = Global.UNIT*2;
        }else if(degreeNum>1){
            tx = Global.UNIT;
        }else{
            tx = 0;
        }

        int ty = (playerNum % 4) * 128;
        g.drawImage(this.image, x, y, x + Global.UNIT, Global.UNIT+30
                , tx, ty
                , tx + 128
                , ty + 128, null);
    }
}
