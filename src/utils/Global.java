/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.*;

/**
 * @author user1
 */
public class Global {

    // 視窗大小
    public static final int WINDOW_WIDTH = 2560;
    public static final int WINDOW_HEIGHT = 1600;

    public static final int MAP_WIDTH = 2304;
    public static final int MAP_HEIGHT = 2304;

    //  視野大小
    public static final int CAMERA_WIDTH = 1070;
    public static final int CAMERA_HEIGHT = 900;

    //追焦對象大小
    public static int UNIT = 128;
    public static int UNIT_X = 128;
    public static int UNIT_Y = 128;


    public static final boolean IS_DEBUG = false;
    public static final boolean IS_DISPLAY_AREA = true;

    public static void log(String str) {
        if (IS_DEBUG) {
            System.out.println(str);
        }
    }



    public static int characterNum = 0;
    public static int Z_CommandCode;
    public static int deathCount;
    public static int cumulativeDeathCount;
    //commandCode
    public static final int NO_DIR = 0;
    public static final int DOWN = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;
    public static final int UP = 4;
    public static final int SPACE = 5;
    public static final int ESCAPE = 6;

    public static final int ONE = 7;
    public static final int TWO = 8;
    public static final int THREE = 9;
    public static final int FOUR = 10;
    public static final int FIVE = 11;

    public static final int ENTER = 12;
    public static final int Z = 13;

    //區網連線
    public static boolean isServer = false;

    public static class NetEvent {
        public static final int PLAYER_CONNECT = 1;
        public static final int PLAYER_MOVE = 2;
        public static final int PLAYER_DISCONNECT = 3;
        public static final int KEY = 4;
        public static final int PLAYER_GET_KEY = 5;

    }


    public static int random(int min, int max) {
        return (int) (Math.random() * (max - min + 1) + min);
    }

    public static int[] generateIncreasingRandoms(int amount, int max) {
        int[] randomNumbers = new int[amount];
        Random random = new Random();
        for (int i = 0; i < randomNumbers.length; i++) {
            randomNumbers[i] = random.nextInt(max);
        }
        Arrays.sort(randomNumbers);
        return randomNumbers;
    }


}
