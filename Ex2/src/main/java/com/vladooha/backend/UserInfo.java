package com.vladooha.backend;

public class UserInfo {
    private String login;
    private String email;
    private String password;

    public boolean setLogin(String login) {
        if (login == null || login.length() < 6) {
            return false;
        } else {
            this.login = login;

            return true;
        }
    }

    public boolean setEmail(String email) {
        if (email == null || email.length() < 8 || !email.contains("@")) {
            return false;
        } else {
            this.email = email;

            return true;
        }
    }

    public boolean setPassword(String password) {
        if (password == null || password.length() < 6) {
            return false;
        } else {
            this.password = password;

            return true;
        }
    }

    public String getLogin() {
        return login;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
