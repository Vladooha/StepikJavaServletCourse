package com.vladooha.servlets;

import com.vladooha.Main;
import com.vladooha.backend.AuthService;
import com.vladooha.backend.ServiceContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthServlet extends HttpServlet {
    public static final Logger logger = LogManager.getLogger(Main .class.getName());

    private ServiceContext serviceContext;

    public AuthServlet(ServiceContext serviceContext) {
        this.serviceContext = serviceContext;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AuthService authService = serviceContext.get(AuthService.class);
        if (authService == null) {
            logger.error("Can't initialize AuthService");

            throw new ServletException(AuthService.class + " service implementation not found");
        }

        if (authService.authUser("New user")) {
            response.getWriter().println(authService.getUserLimit());
        } else {
            response.getWriter().println("There is no user slots");
        }
    }
}
