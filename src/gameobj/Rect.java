package gameobj;


import utils.Global;

//物件長方形
public class Rect {

    private int left;
    private int top;
    private int right;
    private int bottom;


    public Rect(int left, int top, int right, int bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;

    }

    public Rect(Rect rect) {
        this.left = rect.left;
        this.top = rect.top;
        this.right = rect.right;
        this.bottom = rect.bottom;
    }


    public static Rect genWithCenter(int x, int y, int width, int height) {
        int left = x - width / 2;
        int right = left + width;
        int top = y - height / 2;
        int bottom = top + height;
        return new Rect(left, top, right, bottom);
    }

    // x, y 為中心點
    public void resetWithCenter(int x, int y, int width, int height) {
        left = x - width / 2;
        right = x + width / 2;
        top = y - height / 2;
        bottom = y + height / 2;
    }


    //碰撞判斷 傳參數
    public final boolean overlap(int left, int top, int right, int bottom) {
        if (this.left() > right) {
            return false;
        }
        if (this.right() < left) {
            return false;
        }
        if (this.top() > bottom) {
            return false;
        }
        if (this.bottom() < top) {
            return false;
        }
        return true;
    }

    //碰撞判斷 傳長方形
    public final boolean overlap(Rect b) {
        return overlap(b.left, b.top, b.right, b.bottom);
    }
    //判斷中心點有沒有在方框裡
    public final boolean inRange(int x, int y) {
        if (this.left() > x) {
            return false;
        }
        if (this.right() < x) {
            return false;
        }
        if (this.top() > y) {
            return false;
        }
        if (this.bottom() < y) {
            return false;
        }
        return true;
    }

    //中心點判斷碰撞
    public boolean collisionCenter(Rect b) {

        if (centerX() == b.centerX() && centerY() == b.centerY()) {
            return true;
        }
        return false;
    }

    //  圖形的ｘ中心
    public int centerX() {
        return (left + right) / 2;
    }

    //  圖形的y中心
    public int centerY() {
        return (top + bottom) / 2;
    }

    //浮點數版本
    public float exactCenterX() {
        return (left + right) / 2f;
    }

    //浮點數版本
    public float exactCenterY() {
        return (top + bottom) / 2f;
    }

    //設定中心
    public final void setCenter(int x, int y) {
        translate(x - centerX(), y - centerY());
    }

    public final Rect translate(int dx, int dy) {
        this.left += dx;
        this.right += dx;
        this.top += dy;
        this.bottom += dy;
        return this;
    }

    public final Rect translateX(int dx) {
        this.left += dx;
        this.right += dx;
        return this;
    }

    public final Rect translateY(int dy) {
        this.top += dy;
        this.bottom += dy;
        return this;
    }

    public int left() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int top() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public int right() {
        return right;
    }

    public void setRight(int right) {
        this.right = right;
    }

    public int bottom() {
        return bottom;
    }

    public void setBottom(int bottom) {
        this.bottom = bottom;
    }

    public int width() {
        return this.right - this.left;
    }

    public int height() {
        return this.bottom - this.top;
    }

}
