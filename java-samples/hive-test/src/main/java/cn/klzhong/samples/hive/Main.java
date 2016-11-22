package cn.klzhong.samples.hive;

import java.io.IOException;
import java.io.File;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws Exception {
        ProcessBuilder hiveProcessBuilder = new ProcessBuilder("hive", "-e",
                                                               "SET hive.cli.print.header=true; select * from pokes limit 5");
        hiveProcessBuilder.directory(new File("/usr/local/klzhong/tools/apache-hive-1.2.1-bin"));
        //String path = processEnv.get("PATH");
        Process hiveProcess = hiveProcessBuilder.start();

        OutputRedirector outRedirect = new OutputRedirector(hiveProcess.getInputStream(), "HIVE_OUTPUT");
        OutputRedirector outToConsole = new OutputRedirector(hiveProcess.getErrorStream(), "HIVE_LOG");

        outRedirect.start();
        outToConsole.start();
    }

}
