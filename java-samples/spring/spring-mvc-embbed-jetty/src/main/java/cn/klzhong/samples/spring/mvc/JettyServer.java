package cn.klzhong.samples.spring.mvc;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.DispatcherServlet;

import java.io.File;
import java.net.URL;

public class JettyServer {
    private static final Logger log = LoggerFactory.getLogger(JettyServer.class);

    public static final String WEB_APP_ROOT = "webapp"; // that folder has to be just somewhere in classpath
    public static final String MVC_SERVLET_NAME = "mvcDispatcher";
    public static final String JSP_SERVLET_NAME = "jspServlet";

    private final int port;

    private Server server;

    public JettyServer(int port) {
        this.port = port;
    }

    public void start() {
        server = new Server(port);
        server.setHandler( getServletHandler() );

        try {
            server.start();
        } catch (Exception e) {
            log.error("Failed to start server", e);
            throw new RuntimeException();
        }

        log.info("Server started");
    }

    private ServletContextHandler getServletHandler() {
        ServletHolder mvcServletHolder = new ServletHolder(MVC_SERVLET_NAME, new DispatcherServlet());
        mvcServletHolder.setInitParameter("contextConfigLocation", "web-context.xml");

        ServletHolder jspServletHolder = new ServletHolder(JSP_SERVLET_NAME, new org.apache.jasper.servlet.JspServlet());
        // these two lines are not strictly required - they will keep classes generated from JSP in "${javax.servlet.context.tempdir}/views/generated"
        jspServletHolder.setInitParameter("keepgenerated", "true");
        jspServletHolder.setInitParameter("scratchDir", "views/generated");

        // session has to be set, otherwise Jasper won't work
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setAttribute("javax.servlet.context.tempdir", new File("../tmp/webapp"));
        // that classloader is requres to set JSP classpath. Without it you will just get NPE
        context.setClassLoader(Thread.currentThread().getContextClassLoader());
        context.addServlet(jspServletHolder, "*.jsp");
        context.addServlet(mvcServletHolder, "/");
        context.setResourceBase( getBaseUrl() );

        return context;
    }

    public void join() throws InterruptedException {
        server.join();
    }

    private String getBaseUrl() {
        URL webInfUrl = JettyServer.class.getClassLoader().getResource(WEB_APP_ROOT);
        if (webInfUrl == null) {
            throw new RuntimeException("Failed to find web application root: " + WEB_APP_ROOT);
        }
        return webInfUrl.toExternalForm();
    }
}
