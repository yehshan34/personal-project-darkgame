package utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;


public class AStar {
    public static final int WEIGHT = 128;
    private Node[][] map;

    public AStar(Node[][] map) {
        this.map = map;
    }

    private void resetData(){
        for(int i=0; i< map.length; i++){
            for(int j=0; j< map[i].length; j++){
                map[i][j].f = Integer.MAX_VALUE;
                map[i][j].g = Integer.MAX_VALUE;
                map[i][j].h = 0;
                map[i][j].neighbor = new ArrayList<>();
                map[i][j].parent = null;
            }
        }
    }

    public ArrayList<Node> pathSearch(Node start, Node target) {
        resetData();
        PriorityQueue<Node> closedList = new PriorityQueue<>();
        PriorityQueue<Node> openList = new PriorityQueue<>(new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                return Integer.compare(o1.f,o2.f);
            }
        });

        start.g = 0;
        start.f = start.g + start.calculateH(target);

        openList.add(start);

        while (!openList.isEmpty()) {
//            System.out.println("------確認openList-----");
//            openList.forEach(n -> System.out.println(n.x + "," + n.y + "), f: "+ n.f+ ", g: "+ n.g));
            Node node = openList.poll(); //從OPEN表中取估值f最小的節點n(Node由實現排序介面)
            closedList.add(node);
            openList.remove(node);
//            System.out.println("------確認closeList-----");

//            closedList.forEach(n -> {if(n.parent != null) {
//                System.out.println(n.x + "," + n.y + "), f: "+ n.f+ ", g: "+ n.g
//                    + " parent node:"+ n.parent.x+ ","+ n.parent.y);}});
//            System.out.println("--取到的Node:" + node.x + ", " + node.y);

            if (node.x == target.x && node.y == target.y) {
                ArrayList<Node> path = new ArrayList<>();
                while (node != null) {
                    path.add(node);
                    node = node.parent;
                }
                return path;
            }

            ArrayList<Node> neighbor = node.getNeighbours(map);
//            System.out.println("------neighbor--------------");
//            neighbor.forEach(n -> System.out.println(n.x + "," + n.y));

            for (int i = 0; i < neighbor.size(); i++) {
                Node m = neighbor.get(i);
                int totalWeight = node.g + WEIGHT;
                if (closedList.contains(m)) {
                    neighbor.remove(m);
                    i--;
                    continue;
                }
                if (openList.contains(m)) {
                    if (totalWeight < m.g) {
                        m.parent = node;
                        m.g = totalWeight;
                        m.f = m.g + m.calculateH(target);

                        neighbor.remove(m);
                        i--;
                        continue;
                    }
                } else {
                    openList.add(m);
                    m.parent = node;
                    m.g = totalWeight;
                    m.f = m.g + m.calculateH(target);
                }
            }
        }
        return null;
    }
}

