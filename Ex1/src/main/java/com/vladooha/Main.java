package com.vladooha;

import com.vladooha.servlets.TestServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) {
        TestServlet testServlet = new TestServlet();

        ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        contextHandler.addServlet(new ServletHolder(testServlet), "/mirror");

        Server server = new Server(8080);
        server.setHandler(contextHandler);

        try {
            server.start();
            System.out.println("Server started");
            Logger.getGlobal().info("Server started");
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
