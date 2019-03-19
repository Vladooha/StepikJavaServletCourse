package com.vladooha.servlets;

import com.vladooha.backend.AuthService;
import com.vladooha.backend.ServiceContext;
import com.vladooha.backend.realization.AuthServiceImpl;
import org.mockito.internal.util.reflection.FieldSetter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServletTest {
    private AuthService authService;
    private HttpSession session;
    private HttpServletRequest request;
    private HttpServletResponse response;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        authService = spy(AuthServiceImpl.getInstance());

        session = mock(HttpSession.class);

        request = mock(HttpServletRequest.class);
        when(request.getSession()).thenReturn(session);

        response = mock(HttpServletResponse.class);
    }

    private void setupRequest(String url) {
        when(request.getPathInfo()).thenReturn(url);
    }

    private void setupResponse(StringWriter stringWriter) {
        PrintWriter printWriter = new PrintWriter(stringWriter);

        try {
            when(response.getWriter()).thenReturn(printWriter);
        } catch (IOException e) {

        }
    }

    @org.junit.jupiter.api.Test
    void doGet() {
        setupRequest("/admin");

        StringWriter firstAttemptWriter = new StringWriter();
        setupResponse(firstAttemptWriter);

        ServiceContext serviceContext = new ServiceContext();
        serviceContext.put(AuthService.class, authService);

        try {
            assertEquals(0, authService.getUserCount());
            assertEquals(10, authService.getUserLimit());

            AuthServlet authServlet = new AuthServlet(serviceContext);
            authServlet.doGet(request, response);

            assertEquals("10", firstAttemptWriter.toString().trim());
            verify(authService, times(1)).authUser("New user");

            for (int i = 0; i < 9; ++i) {
                authServlet.doGet(request, response);
            }

            StringWriter fewAttemptWriter = new StringWriter();
            setupResponse(fewAttemptWriter);

            authServlet.doGet(request, response);

            assertEquals("There is no user slots", fewAttemptWriter.toString().trim());
            verify(authService, atLeast(10)).authUser("New user");
        } catch (Exception e) {
            e.printStackTrace();

            fail();
        }
    }
}
