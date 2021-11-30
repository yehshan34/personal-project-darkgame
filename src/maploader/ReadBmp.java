package maploader;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReadBmp {//讀取Bmp檔，獲取各個物件座標

    public ArrayList<int[][]> readBmp(final String path) {
        final ArrayList<int[][]> rgbArr = new ArrayList();
        BufferedImage bi = null;
        try {
            bi = ImageIO.read(getClass().getResource(path));
        } catch (final IOException ex) {
            Logger.getLogger(ReadBmp.class.getName()).log(Level.SEVERE, null, ex);
        }

        final int width = bi.getWidth();
        final int height = bi.getHeight();
        final int minx = bi.getMinX();
        final int miny = bi.getMinY();
        for (int i = minx; i < width; i++) {
            for (int j = miny; j < height; j++) {
                final int[][] rgbContent = new int[2][];
                final int pixel = bi.getRGB(i, j);
                rgbContent[0] = new int[]{i, j};
                rgbContent[1] = new int[]{pixel};
                rgbArr.add(rgbContent);
            }
        }
        return rgbArr;
    }
}
