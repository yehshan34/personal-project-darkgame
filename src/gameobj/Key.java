package gameobj;

import utils.Delay;
import utils.Global;

import java.awt.*;

public class Key extends GadGetObject{
    private Delay delay;
    public Key(int x, int y) {
        super(x, y, "/key0.png", "/smallmapkey.png");
        delay = new Delay(60*10);
        delay.loop();

    }

    @Override
    public void paintComponent(Graphics g) {
        if(getState()==State.DISCOVERED){
            g.drawImage(getImg(), collider().left(), collider().top(), Global.UNIT, Global.UNIT, null);
        }


    }

    @Override
    public void update() {
        if (delay.count()) {
            resetPosition(givenX(), givenY(),Global.UNIT,Global.UNIT);
        }
    }
}
