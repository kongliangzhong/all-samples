package cn.klzhong.samples.spring.mvc;

import java.io.IOException;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class Main {
    public static void main(String[] args) throws Exception {
        EmbeddedJetty embeddedJetty = new EmbeddedJetty();
        int port = embeddedJetty.getPortFromArgs(args);
        embeddedJetty.startJetty(port);
    }
}
