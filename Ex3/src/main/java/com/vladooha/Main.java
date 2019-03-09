package com.vladooha;

import com.vladooha.servlets.SignInServlet;
import com.vladooha.servlets.SignUpServlet;
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

        ServletContextHandler servletHandler = new ServletContextHandler();
        servletHandler.addServlet(new ServletHolder(new SignInServlet()), "/signin");
        servletHandler.addServlet(new ServletHolder(new SignUpServlet()), "/signup");

        ResourceHandler resource_handler = new ResourceHandler();
        String htmlResourcesPath = Main.class.getResource("/html").toString();
        resource_handler.setResourceBase(htmlResourcesPath);
        System.out.println("HTML files directory: " + htmlResourcesPath);

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resource_handler, servletHandler});

        server.setHandler(handlers);

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
