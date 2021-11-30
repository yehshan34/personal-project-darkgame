package controller;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class ImageController {//懶散單例模式
    //所有圖片由ImageController創建
    //相同圖片不會重複創建
    private static ImageController imageController;
    //傳入keyPair之前有兩個Arraylist<path>,<image>把兩個陣列存起來
    private ArrayList<KeyPair> keyPairs;//

    ImageController() {
        this.keyPairs = new ArrayList<>();
    }

    public static ImageController getInstance() {//推遲到第一次呼叫getInstance()才創建實體
        if (imageController == null) {
            imageController = new ImageController();
        }
        return imageController;
    }

    public Image tryGet(final String path) {//傳入圖片路徑
        for (int i = 0; i < this.keyPairs.size(); i++) {//迴圈判斷string跟keyPair其中一個屬性(path)是否相同
            if (this.keyPairs.get(i).path.equals(path)) {//若相同
                return this.keyPairs.get(i).image;//就回傳keyPair其中一個屬性(image)
            }
        }
        return add(path);//若沒有就把路徑傳入add的方法中
    }

    private Image add(final String path) {//用String路徑創建圖片
        Image image = null;
        try {
            image = ImageIO.read(getClass().getResource(path));//創建新圖片
            this.keyPairs.add(new KeyPair(path, image));//新增一個keyPair
        } catch (final IOException e) {
            e.printStackTrace();
        }
        return image;//回傳圖片
    }

    private static class KeyPair {//內部類別
        private String path;//路徑
        private Image image;//圖片

        public KeyPair(final String path, final Image image) {
            this.path = path;
            this.image = image;
        }
    }

    public void clear() {
        keyPairs.clear();
    }
}
