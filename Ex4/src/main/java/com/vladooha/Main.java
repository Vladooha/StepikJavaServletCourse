package com.vladooha;

import com.vladooha.servlets.ChatServlet;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) {
        Server server = new Server(8080);

        ServletContextHandler servletContextHandler = new ServletContextHandler();
        servletContextHandler.addServlet(new ServletHolder(new ChatServlet()), "/chat");

        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase(Main.class.getResource("/html/").toString());

        HandlerList handlerList = new HandlerList();
        handlerList.setHandlers(new Handler[]{resourceHandler, servletContextHandler});

        server.setHandler(handlerList);

        try {
            server.start();
            Logger.getGlobal().info("Server started");
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
