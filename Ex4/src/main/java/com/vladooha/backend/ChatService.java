package com.vladooha.backend;

import com.vladooha.websocket.ChatWebSocket;

import java.util.LinkedList;
import java.util.List;

public class ChatService {
    List<ChatWebSocket> webSocketList = new LinkedList<>();

    public void add(ChatWebSocket socket) {
        webSocketList.add(socket);
    }

    public void remove(ChatWebSocket socket) {
        webSocketList.remove(socket);
    }

    public void sendMessage(String message) {
        for (ChatWebSocket socket : webSocketList) {
            socket.write(message);
        }
    }
}
