package utils;

import java.util.*;
import static utils.Global.*;

public class Node implements Comparable<Node> {


    public Node parent;
    public ArrayList<Node> neighbor;
    public boolean isObstacle;

    public int x;
    public int y;

    public int f = 0;
    public int g = 0;
    public int h = 0;

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
        parent = null;
        neighbor = new ArrayList<>();
    }

    public int calculateH(Node finalNode) {
        //Manhattan
        this.h = Math.abs(finalNode.x - x) + Math.abs(finalNode.y - y);
        return h;
    }




    public ArrayList<Node> getNeighbours(Node[][] map) {
        if (((x-64)/UNIT- 1) >= 0 && !map[(y-64)/UNIT][(x-64)/UNIT - 1].isObstacle) {

            neighbor.add(map[(y-64)/UNIT][(x-64)/UNIT - 1]);
        }
        if (((x-64)/UNIT + 1) < map.length && !map[(y-64)/UNIT][(x-64)/UNIT + 1].isObstacle) {
            neighbor.add(map[(y-64)/UNIT][(x-64)/UNIT + 1]);
        }
        if (((y-64)/UNIT - 1) >= 0 && !map[(y-64)/UNIT - 1][(x-64)/UNIT].isObstacle) {
            neighbor.add(map[(y-64)/UNIT - 1][(x-64)/UNIT]);
        }
        if (((y-64)/UNIT + 1) < map[0].length && !map[(y-64)/UNIT + 1][(x-64)/UNIT].isObstacle) {
            neighbor.add(map[(y-64)/UNIT + 1][(x-64)/UNIT]);
        }
        return neighbor;
    }


    @Override
    public int compareTo(Node o) {
        return Integer.compare(this.f,o.f);
    }
}
