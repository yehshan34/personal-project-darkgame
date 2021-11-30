package gameobj;

import controller.ImageController;
import utils.Delay;

import java.awt.*;

public class Animator {
    private int[] walk = {0,1,2,3};
    private Image image;
    private int count;
    private Delay delay;
    private Delay delay2;
    private int num;
    private boolean isbarrier;

    public Animator(String imagePath) {
        this.image = ImageController.getInstance().tryGet(imagePath);
        this.delay = new Delay(30);
        this.count = 0;
        delay.loop();
        isbarrier = false;

    }
    public Animator(String imagePath, int[] walk){
        this(imagePath, walk, 30);
    }
    public Animator(String ImagePath, int[] walk, int delayTime){
        this.image = ImageController.getInstance().tryGet(ImagePath);
        this.walk = walk;
        this.delay = new Delay(delayTime);
        this.delay2 = new Delay(75);
        this.count = 0;
        delay.loop();
        delay2.pause();
    }
    public void paint(Graphics g, int num, int left, int top, int right, int bottom) {
        if (delay.count()) {
            count = ++count % walk.length;
        }
        int tx = 0;
        int ty = (num % 4) * 128;
        g.drawImage(this.image, left, top, right, bottom
                        , tx + walk[count] * 128, ty
                        , tx + 128 + walk[count] * 128
                        , ty + 128, null);

    }
    public void paint(Graphics g, int num, int left, int top, int right, int bottom,int unit) {
        if (delay.count()) {
            count = ++count % walk.length;

        }
        int tx = 0;
        int ty = (num % 4) * unit;
        g.drawImage(this.image, left, top, right, bottom
                , tx + walk[count] * unit, ty
                , tx + unit + walk[count] * unit
                , ty + unit, null);

    }

    public void paint(Graphics g, int left, int top, int right, int bottom) {
        if (delay.count()) {
            count++;
        }
        if(count>3) {
            count = 0;
            delay.pause();
            delay2.play();
        }
        if(delay2.count()){
            delay.loop();
            delay2.pause();
        }

        int tx = 0;
        int ty = (num % 4) * 128;
        g.drawImage(this.image, left, top, right, bottom
                , tx + count * 128, ty
                , tx + 128 + count * 128
                , ty + 128, null);

    }

    public int getCount() {
        return count;
    }
}
