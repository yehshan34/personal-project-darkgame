package maploader;


import gameobj.GameObject;

import java.io.IOException;
import java.util.ArrayList;

public class MapLoader {//整合讀取資料

    private final ArrayList<int[][]> mapArr;
    private final ArrayList<String[]> txtArr;

    public MapLoader(final String MapPath, final String txtPath) throws IOException { //txt檔名  bmp檔名
        //若程式異常如何處理
        this.mapArr = new ReadBmp().readBmp(MapPath);
        this.txtArr = new ReadFile().readFile(txtPath);
    }

    public ArrayList<GameObject> creatObjectArray(final String gameObject, final int mapObjectSize, final ArrayList<MapInfo> mapInfo, final CompareClass compare) {
        final ArrayList<GameObject> tmp = new ArrayList();
        mapInfo.forEach((e) -> {
            final GameObject tmpObject = compare.compareClassName(gameObject, e.getName(), e, mapObjectSize);
            if (tmpObject != null) {
                tmp.add(tmpObject);
            }
        });
        return tmp;
    }

    public ArrayList<MapInfo> combineInfo() {  //整合需要資料   類名  x座標  y座標 尺寸(e.g. 1 * 1)
        final ArrayList<MapInfo> result = new ArrayList();
        for (int i = 0; i < this.mapArr.size(); i++) {
            for (int j = 0; j < this.txtArr.size(); j++) {
                if (this.mapArr.get(i)[1][0] == Integer.parseInt(this.txtArr.get(j)[1])) {
                    final MapInfo tmp = new MapInfo(this.txtArr.get(j)[0],
                            this.mapArr.get(i)[0][0],
                            this.mapArr.get(i)[0][1],
                            Integer.parseInt(this.txtArr.get(j)[2]),
                            Integer.parseInt(this.txtArr.get(j)[3]));
                    result.add(tmp);
                }
            }
        }
        return result;
    }

    //    public ArrayList<String[]> combineInfo() {  //整合需要資料   類名  x座標  y座標 尺寸(e.g. 1 * 1)
//        ArrayList<String[]> result = new ArrayList();
//        for (int i = 0; i < mapArr.size(); i++) {
//            for (int j = 0; j < txtArr.size(); j++) {
//                if (mapArr.get(i)[1][0] == Integer.parseInt(txtArr.get(j)[1])) {
//                    String[] tmp = new String[5];
//                    tmp[0] = txtArr.get(j)[0];
//                    tmp[1] = String.valueOf(mapArr.get(i)[0][0]);
//                    tmp[2] = String.valueOf(mapArr.get(i)[0][1]);
//                    tmp[3] = txtArr.get(j)[2];
//                    tmp[4] = txtArr.get(j)[3];
//                    result.add(tmp);
//                }
//            }
//        }
//        return result;
//    }
    public ArrayList<int[][]> getMapArr() {
        return this.mapArr;
    }

    public ArrayList getTxtArr() {
        return this.txtArr;
    }

    public static interface CompareClass {

        public GameObject compareClassName(String gameObject, String name, MapInfo mapInfo, int MapObjectSize);
    }

}
