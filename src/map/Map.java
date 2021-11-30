package map;

import gameobj.GameObject;
import maploader.MapInfo;
import maploader.MapLoader;

import java.io.IOException;
import java.util.ArrayList;

public class Map {
    public static ArrayList<GameObject> genSpike(String mapPath, String txtPath, String imgName) {
        ArrayList<GameObject> mapArr = new ArrayList<>();
        try {
            MapLoader mapLoader = new MapLoader(mapPath, txtPath);
            ArrayList<MapInfo> mapInfo = mapLoader.combineInfo();
            mapArr = mapLoader.creatObjectArray(imgName, 128, mapInfo, new MapLoader.CompareClass() {
                @Override
                public GameObject compareClassName(String gameObject, String name, MapInfo mapInfo, int mapObjectSize) {
                    GameObject tmp = null;
                    if (gameObject.equals(name)) {
                        tmp = new Spike("/"+imgName+".png",mapInfo.getX() * mapObjectSize, mapInfo.getY() * mapObjectSize
                                , mapInfo.getSizeX() * mapObjectSize, mapInfo.getSizeY() * mapObjectSize);
                        return tmp;
                    }
                    return null;
                }
            });
            return mapArr;
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            return mapArr;
        }
    }
    public static ArrayList<GameObject> genStair(String mapPath, String txtPath, String imgName) {
        ArrayList<GameObject> mapArr = new ArrayList<>();
        try {
            MapLoader mapLoader = new MapLoader(mapPath, txtPath);
            ArrayList<MapInfo> mapInfo = mapLoader.combineInfo();
            mapArr = mapLoader.creatObjectArray(imgName, 128, mapInfo, new MapLoader.CompareClass() {
                @Override
                public GameObject compareClassName(String gameObject, String name, MapInfo mapInfo, int mapObjectSize) {
                    GameObject tmp = null;
                    if (gameObject.equals(name)) {
                        tmp = new Stair("/"+imgName+".png",mapInfo.getX() * mapObjectSize, mapInfo.getY() * mapObjectSize
                                , mapInfo.getSizeX() * mapObjectSize, mapInfo.getSizeY() * mapObjectSize);
                        return tmp;
                    }
                    return null;
                }
            });
            return mapArr;
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            return mapArr;
        }
    }
    public static ArrayList<GameObject> genFloor(String mapPath, String txtPath, String imgName) {
        ArrayList<GameObject> mapArr = new ArrayList<>();
        try {
            MapLoader mapLoader = new MapLoader(mapPath, txtPath);
            ArrayList<MapInfo> mapInfo = mapLoader.combineInfo();
            mapArr = mapLoader.creatObjectArray(imgName, 128, mapInfo, new MapLoader.CompareClass() {
                @Override
                public GameObject compareClassName(String gameObject, String name, MapInfo mapInfo, int mapObjectSize) {
                    GameObject tmp = null;
                    if (gameObject.equals(name)) {
                        tmp = new Floor("/"+imgName+".png",mapInfo.getX() * mapObjectSize, mapInfo.getY() * mapObjectSize
                                , mapInfo.getSizeX() * mapObjectSize, mapInfo.getSizeY() * mapObjectSize);
                        return tmp;
                    }
                    return null;
                }
            });
            return mapArr;
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            return mapArr;
        }
    }

    public static ArrayList<GameObject> genWall(String mapPath, String txtPath, String imgName) {
        ArrayList<GameObject> mapArr = new ArrayList<>();
        try {
            MapLoader mapLoader = new MapLoader( mapPath, txtPath);
            ArrayList<MapInfo> mapInfo = mapLoader.combineInfo();
            mapArr = mapLoader.creatObjectArray(imgName, 128, mapInfo, new MapLoader.CompareClass() {
                @Override
                public GameObject compareClassName(String gameObject, String name, MapInfo mapInfo, int mapObjectSize) {
                    GameObject tmp = null;
                    if (gameObject.equals(name)) {

                        tmp = new Wall("/"+imgName+".png",mapInfo.getX() * mapObjectSize, mapInfo.getY() * mapObjectSize
                                , mapInfo.getSizeX() * mapObjectSize, mapInfo.getSizeY() * mapObjectSize);

                        return tmp;
                    }
                    return null;
                }
            });
            return mapArr;
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            return mapArr;
        }
    }
}
