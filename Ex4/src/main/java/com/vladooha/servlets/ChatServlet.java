package com.vladooha.servlets;

import com.vladooha.backend.ChatService;
import com.vladooha.websocket.ChatWebSocket;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

import javax.servlet.annotation.WebServlet;

@WebServlet(name = "ChatServlet", urlPatterns = {"/chat"})
public class ChatServlet extends WebSocketServlet {
    private static final long TIMEOUT = 10000;

    private ChatService chatService;

    public ChatServlet() {
        chatService = new ChatService();
    }

    @Override
    public void configure(WebSocketServletFactory webSocketServletFactory) {
        webSocketServletFactory.getPolicy().setIdleTimeout(TIMEOUT);
        webSocketServletFactory.setCreator((request, response) -> new ChatWebSocket(chatService));
    }
}
