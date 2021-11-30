package gameobj;

import utils.Delay;
import utils.Global;

import java.awt.*;

public class OilLamp extends GadGetObject{
    private Delay delay;
    public OilLamp(int x, int y) {
        super(x, y, "/oillamp.png", "/smallmapoillamp.png");
        delay = new Delay(60*30);
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
