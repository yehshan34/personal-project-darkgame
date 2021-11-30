package gameobj;

import controller.ImageController;
import utils.Delay;
import utils.Global;

import java.awt.*;

public class Blood  {

    private Animator bloodAnimator;
    private int[] walk = {0,1};
    public Blood() {
        bloodAnimator = new Animator("/bloodscene.png",walk);
    }
    public Animator getBloodAnimator(){
        return this.bloodAnimator;
    }

}
