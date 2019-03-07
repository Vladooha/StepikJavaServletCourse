package com.vladooha.servlets;

import com.vladooha.backend.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignUpServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Signup GET request");

        response.setContentType("text/html;charset=utf-8");
        response.sendRedirect("/registration.html");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Signup POST request");

        String login = request.getParameter("login");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        System.out.println("Login: " + login);
        System.out.println("Email: " + email);
        System.out.println("Password: " + password);

        response.setContentType("text/html;charset=utf-8");
        UserService.REG_RESULT regAttempt = UserService.regUser(login, email, password);

        System.out.println("Registration status: " + regAttempt);

//        if (regAttempt != UserService.REG_RESULT.SUCCESS) {
//            response.getWriter().println("Unsuccessfully registration =(");
//        } else {
//            this.getServletContext().getRequestDispatcher("/signin").forward(request, response);
//        }

        response.setStatus(HttpServletResponse.SC_OK);

    }
}