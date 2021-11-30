package maploader;

public class MapInfo {//儲存單一位置地圖物件資訊

    private final String name;
    private final int x;
    private final int y;
    private final int sizeX;
    private final int sizeY;

    public MapInfo(final String name, final int x, final int y, final int sizeX, final int sizeY) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    public String getName() {
        return this.name;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getSizeX() {
        return this.sizeX;
    }

    public int getSizeY() {
        return this.sizeY;
    }

}
