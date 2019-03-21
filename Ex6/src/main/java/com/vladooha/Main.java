package com.vladooha;

import com.vladooha.backend.ServiceContext;
import com.vladooha.backend.services.ResourceService;
import com.vladooha.backend.services.realization.ResourceServiceImpl;
import com.vladooha.backend.servlets.PathParserServlet;
import com.vladooha.beans.BeansContext;
import com.vladooha.beans.ResourceServerControllerMBean;
import com.vladooha.beans.realization.ResourceServerController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.management.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.management.ManagementFactory;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class Main {
    private static final Logger logger;

    private static int PORT = 8080;

    static {
        System.setProperty("log4j.configurationFile", "log4j2.xml");
        logger = LogManager.getLogger(Main.class.getName());
    }

    public static void main(String[] args) {
        Properties properties = new Properties();
        try (InputStream configStr = new FileInputStream("config.properties")) {
            properties.load(configStr);

            PORT = Integer.parseInt(properties.getProperty("port"));
        } catch (FileNotFoundException e) {
            logger.error("Config file not found");
        } catch (IOException e) {
            logger.error("Can't get acces to config file", e);
        }

        Server server = new Server(PORT);
        logger.info("Server started on port " + PORT);

        try {
            ResourceServerControllerMBean resourceBean = new ResourceServerController();
            StandardMBean standardAuthBean = new StandardMBean(resourceBean, ResourceServerControllerMBean.class);
            MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
            ObjectName objectName = new ObjectName("Admin:type=ResourceServerController");

            mBeanServer.registerMBean(standardAuthBean, objectName);

            BeansContext beansContext = BeansContext.getInstance();
            beansContext.put(ResourceServerControllerMBean.class, resourceBean);
        } catch (MalformedObjectNameException e) {
            logger.error("Wrong object name", e);
        } catch (NotCompliantMBeanException e) {
            logger.error("Wrong bean metainfo", e);
        } catch (MBeanRegistrationException e) {
            logger.error("Unknown error till registration '" + ResourceServerControllerMBean.class + "'", e);
        } catch (InstanceAlreadyExistsException e) {
            logger.error("Bean '" + ResourceServerControllerMBean.class + "'already registered", e);
        }


        ServiceContext pathParserServiceContext = new ServiceContext();
        pathParserServiceContext.put(ResourceService.class, ResourceServiceImpl.getInstance());

        PathParserServlet pathParserServlet = new PathParserServlet(pathParserServiceContext);

        ServletContextHandler servletContextHandler = new ServletContextHandler();
        servletContextHandler.addServlet(new ServletHolder(pathParserServlet), "/resources");

        server.setHandler(servletContextHandler);

        try {
            server.start();
            java.util.logging.Logger.getGlobal().log(new LogRecord(Level.INFO, "Server started"));

            server.join();
        } catch (Exception e) {
            logger.error("Error has been occurred till server work", e);
        }
    }
}
