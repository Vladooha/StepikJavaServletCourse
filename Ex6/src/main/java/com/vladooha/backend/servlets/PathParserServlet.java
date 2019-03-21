package com.vladooha.backend.servlets;

import com.vladooha.backend.ServiceContext;
import com.vladooha.backend.services.ResourceService;
import com.vladooha.beans.BeansContext;
import com.vladooha.beans.ResourceServerControllerMBean;
import com.vladooha.beans.realization.ResourceServerController;
import com.vladooha.resources.TestResource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PathParserServlet extends HttpServlet {
    public static final Logger logger = LogManager.getLogger(PathParserServlet.class.getName());

    private ServiceContext serviceContext;

    public PathParserServlet(ServiceContext serviceContext) {
        this.serviceContext = serviceContext;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ResourceService resourceService = serviceContext.get(ResourceService.class);
        if (resourceService == null) {
            logger.error("Can't initialize " + ResourceService.class);

            throw new ServletException(ResourceService.class + " service implementation not found");
        }

        BeansContext beans = BeansContext.getInstance();
        ResourceServerControllerMBean tempResourceBean = beans.get(ResourceServerControllerMBean.class);
        ResourceServerController resourceBean;
        if (tempResourceBean == null || !(tempResourceBean instanceof ResourceServerController)) {
            logger.error("Can't initialize " + ResourceServerControllerMBean.class);

            throw new ServletException(ResourceServerControllerMBean.class + " service implementation not found");
        }
        resourceBean = (ResourceServerController) tempResourceBean;

        String path = request.getParameter("path");
        logger.info("PathParser got path=\"" + path + "\"");

        resourceService.setLink(path);
        Object resource = resourceService.getResource();

        if (resource instanceof TestResource) {
            TestResource testResource = (TestResource)resource;
            resourceBean.setRecource(testResource);

            logger.info("Parsed object data:\nName: " + testResource.getName() + "\nAge: " + testResource.getAge());
            logger.info("Bean data:\nName: " + resourceBean.getName() + "\nAge: " + resourceBean.getAge());
        } else {
            logger.error("Wrong type of resource on '" + path + "'\nExpected type: '" + TestResource.class + "'");

            throw new ServletException("Can't cast '" + resource + "' to  class '" + TestResource.class + "'");
        }
    }
}
