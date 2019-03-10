package com.vladooha.websocket;


import com.vladooha.backend.ChatService;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import java.io.IOException;

@WebSocket
public class ChatWebSocket {
    private Session session;
    private ChatService chatService;

    public ChatWebSocket(ChatService chatService) {
        this.chatService = chatService;
    }

    @OnWebSocketConnect
    public void onConnect(Session session) {
        this.session = session;
        chatService.add(this);
    }

    @OnWebSocketMessage
    public void onMessage(String data) {
        chatService.sendMessage(data);
    }

    @OnWebSocketClose
    public void onClose(int code, String reason) {
        session.close();
        chatService.remove(this);
    }

    public void write(String message) {
        try {
            session.getRemote().sendString(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
