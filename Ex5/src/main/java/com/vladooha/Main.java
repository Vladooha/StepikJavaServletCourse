package com.vladooha;

import com.vladooha.backend.AuthService;
import com.vladooha.backend.ServiceContext;
import com.vladooha.backend.realization.AuthServiceImpl;
import com.vladooha.beans.realization.AccountServerController;
import com.vladooha.beans.AccountServerControllerMBean;
import com.vladooha.servlets.AuthServlet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.management.*;
import java.lang.management.ManagementFactory;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class Main {
    private static final Logger logger;

    static {
        System.setProperty("log4j.configurationFile", "log4j2.xml");
        logger = LogManager.getLogger(Main.class.getName());
    }

    private static final int PORT = 8080;

    public static void main(String[] args) {
        Server server = new Server(PORT);
        logger.info("Server created on port " + PORT);

        ServiceContext authServiceContext = new ServiceContext();
        authServiceContext.put(AuthService.class, AuthServiceImpl.getInstance());

        ServletContextHandler servletContextHandler = new ServletContextHandler();
        servletContextHandler.addServlet(new ServletHolder(new AuthServlet(authServiceContext)), "/admin");

        server.setHandler(servletContextHandler);

        try {
            AccountServerControllerMBean authBean = new AccountServerController();
            StandardMBean standardAuthBean = new StandardMBean(authBean, AccountServerControllerMBean.class);
            MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
            mBeanServer.registerMBean(standardAuthBean, new ObjectName("Admin:type=AccountServerController"));
        } catch (MalformedObjectNameException e) {
            logger.error("AuthController bean registration", e);
        } catch (NotCompliantMBeanException e) {
            logger.error("AuthController bean registration", e);
        } catch (MBeanRegistrationException e) {
            logger.error("AuthController bean registration", e);
        } catch (InstanceAlreadyExistsException e) {
            logger.error("AuthController bean registration", e);
        }

        try {
            server.start();

            logger.info("Server started");
            java.util.logging.Logger.getGlobal().log(new LogRecord(Level.INFO, "Server started"));

            server.join();
        } catch (InterruptedException e) {
            logger.error("Server thread interrupted", e);
        } catch (Exception e) {
            logger.error("Server start error", e);
        }
    }
}