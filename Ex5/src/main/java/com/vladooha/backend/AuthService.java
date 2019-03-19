package com.vladooha.backend;

public interface AuthService {
    int getUserLimit();
    void setUserLimit(int userLimit);
    int getUserCount();
    boolean authUser(String user);
    boolean unlogUser(String user);
    void unlogAll();
}
