package maploader;

import java.io.*;
import java.util.ArrayList;
import java.util.function.Consumer;

public class ReadFile {//讀取txt檔，獲取物件類名與尺寸

    public ArrayList<String[]> readFile(final String path) throws IOException {
        final ArrayList<String> tmp = new ArrayList();
        String pathname = MapLoader.class.getResource(path).getFile();
        pathname = java.net.URLDecoder.decode(pathname, "utf-8");
        final File filename = new File(pathname);
        final InputStreamReader reader = new InputStreamReader(new FileInputStream(filename));
        final BufferedReader br = new BufferedReader(reader);
        String line = "";
        while ((line = br.readLine()) != null) {
            tmp.add(line);
        }
        final ArrayList<String[]> filterArr = new ArrayList();

        tmp.forEach(new Consumer() {
            @Override
            public void accept(final Object a) {
                final String[] tmp = new String[4];
                tmp[0] = ((String) a).split(",")[0];
                tmp[1] = ((String) a).split(",")[1];
                tmp[2] = ((String) a).split(",")[3];
                tmp[3] = ((String) a).split(",")[4];
                filterArr.add(tmp);
            }
        });
        return filterArr;
    }
}
