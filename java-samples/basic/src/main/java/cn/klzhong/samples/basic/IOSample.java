package cn.klzhong.samples.basic;

import java.io.FileWriter;

public class IOSample {

    public void fileAppend() {
        String fileName = "append.txt";
        String[] ss = new String[]{"aaa", "bbb", "ccc"};
        try (FileWriter fw = new FileWriter(fileName, true)) {
            for (String s : ss) {
                fw.write(s);
                fw.write("\n");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
