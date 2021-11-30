package gameobj;

import utils.GameKernel;
import camera.MapInformation;
import utils.Global;

import java.awt.*;

public abstract class GameObject implements GameKernel.PaintInterface, GameKernel.UpdateInterface {

    //判斷碰撞的匡
    private Rect collider;
    //畫圖的匡
    private Rect painter;





    public GameObject(int x, int y, int width, int height) {
        this(x, y, width, height, x, y, width, height);

    }

    public GameObject(Rect rect) {
        collider = new Rect(rect);
        painter = new Rect(rect);
    }

    public GameObject(int x, int y, int width, int height,
                      int x2, int y2, int width2, int height2) {
        collider = Rect.genWithCenter(x, y, width, height);
        painter = Rect.genWithCenter(x2, y2, width2, height2);
    }

    public GameObject(Rect rect, Rect rect2) {
        collider = new Rect(rect);
        painter = new Rect(rect2);
    }
    public void resetPosition(int x, int y, int width, int height){
        collider.resetWithCenter(x, y, width, height);
        painter.resetWithCenter(x, y, width, height);

    }
    public final Rect collider() {
        return collider;
    }

    public boolean isCollisionCenter(GameObject obj) {
        return collider.collisionCenter(obj.collider);
    }
    public boolean inRange(GameObject obj){
        return collider.inRange(obj.collider().centerX(), obj.collider().centerY());
    }
    public boolean painterRange(GameObject obj){
        return painter.inRange(obj.collider().centerX(), obj.collider().centerY());
    }


    public final Rect painter() {
        return painter;
    }

    //超過境頭視野就不用畫
    public boolean outOfScreen() {
        if (painter.bottom() <= 0) {
            return true;
        }
        if (painter.right() <= 0) {
            return true;
        }
        if (painter.left() >= MapInformation.mapInfo().right()) {
            return true;
        }
        if (painter.top() >= MapInformation.mapInfo().bottom() ) {
            return true;
        }
        return false;
    }

    //這四個是專門寫給讓主角在中間的方法 只要被追焦對象改即可
    public boolean touchTop2() {
        return collider.top() <= 0 + Global.CAMERA_WIDTH / 2 - Global.UNIT_X / 2; //鏡頭到  地圖邊界的距離 ;
    }

    public boolean touchLeft2() {
        return collider.left() <= 0 + Global.CAMERA_HEIGHT / 2 - Global.UNIT_Y / 2;
    }

    public boolean touchRight2() {
        return collider.right() >= MapInformation.mapInfo().right() - Global.CAMERA_HEIGHT / 2 + Global.UNIT_Y / 2;
    }

    public boolean touchBottom2() {
        return collider.bottom() >= MapInformation.mapInfo().bottom() - Global.CAMERA_WIDTH / 2 + Global.UNIT_X / 2;
    }

    public boolean touchTop() {
        return collider.top() <= 0;
    }

    public boolean touchLeft() {
        return collider.left() <= 0;
    }

    public boolean touchRight() {
        return collider.right() >= MapInformation.mapInfo().right();
    }

    public boolean touchBottom() {
        return collider.bottom() >= MapInformation.mapInfo().bottom();
    }


    //是否在境頭視野內
    public boolean isCollision(GameObject obj) {
        return collider.overlap(obj.collider);
    }

    //移動 x及y的位置
    public final void translate(int x, int y) {
        collider.translate(x, y);
        painter.translate(x, y);
    }

    public final void translateX(int x) {
        collider.translateX(x);
        painter.translateX(x);
    }

    public final void translateY(int y) {
        collider.translateY(y);
        painter.translateY(y);
    }

    @Override
    public final void paint(Graphics g) {
        paintComponent(g);
        if (Global.IS_DEBUG) {
            g.setColor(Color.RED);
            g.drawRect(this.painter.left(), this.painter.top(), this.painter.width(), this.painter.height());
            g.setColor(Color.BLUE);
            g.drawRect(this.collider.left(), this.collider.top(), this.collider.width(), this.collider.height());
            g.setColor(Color.BLACK);
        }
    }

    public abstract void paintComponent(Graphics g);





}
