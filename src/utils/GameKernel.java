package utils;

import java.awt.*;// 我們需要繼承Canvas
import java.awt.image.BufferStrategy;// 調用硬體的緩衝機制用的類

//遊戲主迴圈的核心 處理 畫面更新以及 遊戲邏輯更新 不應該去決定畫圖畫什麼 以及遊戲的邏輯有哪些 此迴圈應該套用在任何遊戲裡
public class GameKernel extends Canvas { //canvas 繼承自component 所以可以丟進去JFrame 需要自己重建graphic物件 繪畫區域 支援雙緩衝機制
    private final long nsPerUpdates;//每一次邏輯花費的時間(奈秒)  原設1/60秒
    //畫面更新時間
    private final long frameDeltaTime;//繪畫間隔時間/每畫一張圖時間 1秒換成奈秒 除以一秒動(畫)幾次 原設1/60秒
    private PaintInterface pi;
    private UpdateInterface updateInterface;
    private utils.CommandSolver cs;


    private GameKernel(final int updatePerSec, final int framePerSec, final PaintInterface pi, final UpdateInterface ui, final CommandSolver.BuildStream buildStream) {//每秒更新60次遊戲邏輯// 每秒畫60張圖
        this.nsPerUpdates = 1000000000 / updatePerSec;
        this.frameDeltaTime = 1000000000 / framePerSec;
        this.pi = pi;
        this.updateInterface = ui;
        if (buildStream != null) {
            this.cs = buildStream.bind(this, updatePerSec);
        }
    }
    private void paint() {
        // 當沒有緩衝機制時我們便調用方法創建
        final BufferStrategy bs = this.getBufferStrategy();// 內部方法 抓硬體的雙緩衝機制
        if (bs == null) {
            this.createBufferStrategy(3);//3層
            return;//創立當前剛回合 不能直接用 先return;
        }
        final Graphics g = bs.getDrawGraphics();// 從BufferStrategy中取出Graphics 緩衝機制會自行判斷並進行Cache處理
        g.fillRect(0, 0, this.getWidth(), this.getHeight());// 先畫一個跟畫布一樣大小的區塊
        if (this.pi != null) {
            this.pi.paint(g);
        }
        // end
        g.dispose();// 畫完之後釋放掉相關資源
        bs.show();// 畫出畫面
    }

    public void run(boolean isDebug) {//把遊戲處理 和視窗處理分開
        this.cs.start();
        final long startTime = System.nanoTime();//系統一開始程式開始的時間 把現在的時間抓出來---1
        long passedUpdated = 0;//開始到現在應該要更新的次數(實際上)
        long lastRepaintTime = System.nanoTime();//上一次重新畫圖的時間 一開始跟開始的時間一樣---2
        int paintTimes = 0;//畫圖總數
        long timer = System.nanoTime();
        while (true) {//每一次while 撈一次系統的時間 來判斷畫的時間有沒有超過畫的間距 //
            final long currentTime = System.nanoTime();//這一圈迴圈開始的系統時間 每一次while 撈一次系統的時間 來判斷畫的時間有沒有超過畫的間距---3
            final long totalTime = currentTime - startTime;//程式開始到現在的時間 得知從遊戲到現在經過了多久
            final long targetTotalUpdated = totalTime / this.nsPerUpdates;//開始到現在應該要更新的次數(理論上)
            while (passedUpdated < targetTotalUpdated) {// 如果當前經過的次數小於實際應該更新的次數 更新追上當前的次數
//                gameJPanel.moveX();//移動到該移動的次數
                if (this.cs != null) {//紀錄每1/60秒 按的動作 要刷新邏輯的時候 依據時間順序做給你 確保輸入會在邏輯之前 讓邏輯沒問題 不會被漏掉
                    this.cs.update();
                }
                if (this.updateInterface != null) {//更新邏輯
                    this.updateInterface.update();
                }
                passedUpdated++;
            }
            if (currentTime - timer >= 1000000000) {
                // 每過1秒 來看當前paint畫了幾張 就是偵數------------6
                if(isDebug){
                    System.out.println("FPS:" + paintTimes);
                }
                paintTimes = 0;
                timer = currentTime;

            }
            if (this.frameDeltaTime <= currentTime - lastRepaintTime) {//只要現在的時間與上一次重劃一張圖的時間 相差超過需要的間隔 就需要畫一次---4
                lastRepaintTime = currentTime;//更新時間 ------5 做之前更新 才能把做的時間歸到等待的時間 如果放在做完後面 還是等待的概念
                paint();
                paintTimes++;
            }
        }
    }

    @FunctionalInterface
    public interface PaintInterface {
        void paint(Graphics g);
    }

    @FunctionalInterface
    public interface UpdateInterface {
        void update();
    }

    public static class Builder {
        private PaintInterface paintInterFace;
        private UpdateInterface updateInterFace;
        private CommandSolver.BuildStream buildStream;
        private int updatePerSec;
        private int framePerSec;

        public Builder() {
            this.paintInterFace = null;
            this.updateInterFace = null;
            this.buildStream = null;
            this.updatePerSec = 60;
            this.framePerSec = 60;
        }

        public Builder paint(final PaintInterface paintInterFace) {
            this.paintInterFace = paintInterFace;
            return this;
        }

        public Builder update(final UpdateInterface updateInterFace) {
            this.updateInterFace = updateInterFace;
            return this;
        }

        public Builder fps(final int framePerSec) {
            this.framePerSec = framePerSec;
            return this;
        }

        public Builder ups(final int updatePerSec) {
            this.updatePerSec = updatePerSec;
            return this;
        }

        public Builder input(final CommandSolver.BuildStream buildStream) {
            this.buildStream = buildStream;
            return this;
        }
        public GameKernel gen() {
            return new GameKernel(this.updatePerSec, this.framePerSec, this.paintInterFace, this.updateInterFace, this.buildStream);
        }
    }
}
