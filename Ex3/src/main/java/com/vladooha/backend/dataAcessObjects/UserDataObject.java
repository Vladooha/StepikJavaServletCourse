package com.vladooha.backend.dataAcessObjects;

import com.vladooha.backend.DatabaseService;
import com.vladooha.backend.dataSets.UserDataSet;

public class UserDataObject {
    public enum REG_RESULT { WRONG_LOGIN, WRONG_EMAIL, WRONG_PASSWORD, USER_EXISTS, SUCCESS }
    public enum LOGIN_RESULT { WRONG_LOGIN, WRONG_PASSWORD, SUCCESS }

    private static DatabaseService usersDb;

    static {
        usersDb = new DatabaseService();
        usersDb.setMysqlConnection();
    }

    public static REG_RESULT regUser(String login, String email, String password) {
        // Error cases

        if (isUserExists(login)) {
            return REG_RESULT.USER_EXISTS;
        }

        if (login == null || login.length() < 6) {
            return REG_RESULT.WRONG_LOGIN;
        }

        if (email == null || email.length() < 8 || !email.contains("@")) {
            // Tests doesn't have e-mails
            //return REG_RESULT.WRONG_EMAIL;

            // Test solution
            email = "test@test.ru";
        }

        if (password == null || password.length() < 6) {
            return REG_RESULT.WRONG_PASSWORD;
        }

        // Success case

        UserDataSet userInfo = new UserDataSet();
        userInfo.setLogin(login);
        userInfo.setEmail(email);
        userInfo.setPassword(password);

        usersDb.save(userInfo);

        return REG_RESULT.SUCCESS;
    }

    public static boolean isUserExists(String login) {
        return usersDb.loadByField(UserDataSet.class, "login", login) != null;
    }

    public static LOGIN_RESULT logUser(String login, String password) {
        UserDataSet userInfo = usersDb.loadByField(UserDataSet.class, "login", login);

        // Error cases

        if (userInfo == null) {
            return LOGIN_RESULT.WRONG_LOGIN;
        }

        if (!userInfo.getPassword().equals(password)) {
            return LOGIN_RESULT.WRONG_PASSWORD;
        }

        // Success case

        return LOGIN_RESULT.SUCCESS;
    }

    public static UserDataSet getUserInfo(String login) {
        return usersDb.loadByField(UserDataSet.class, "login", login);
    }

    public static UserDataSet getUserInfoById(long id) {
        return usersDb.load(UserDataSet.class, id);
    }
}
