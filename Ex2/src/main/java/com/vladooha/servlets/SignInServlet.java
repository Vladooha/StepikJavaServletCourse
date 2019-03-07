package com.vladooha.servlets;

import com.vladooha.backend.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignInServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Signin GET request");

        response.setContentType("text/html;charset=utf-8");
        response.sendRedirect("/login.html");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Signin POST request");

        String login = request.getParameter("login");
        String password = request.getParameter("password");

        System.out.println("Login: " + login);
        System.out.println("Password: " + password);

        response.setContentType("text/html;charset=utf-8");
        UserService.LOGIN_RESULT loginAttempt = UserService.logUser(login, password);

        System.out.println("Login status: " + loginAttempt);

        if (loginAttempt == UserService.LOGIN_RESULT.SUCCESS) {
            response.getWriter().println("Authorized: " + login);
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.getWriter().println("Unauthorized");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }


    }
}
