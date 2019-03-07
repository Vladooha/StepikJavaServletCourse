package com.vladooha.backend;

import java.util.HashMap;
import java.util.Map;

public class UserService {
    public enum REG_RESULT { WRONG_LOGIN, WRONG_EMAIL, WRONG_PASSWORD, USER_EXISTS, SUCCESS }
    public enum LOGIN_RESULT { WRONG_LOGIN, WRONG_PASSWORD, SUCCESS }

    private static Map<String, UserInfo> userMap = new HashMap<String, UserInfo>();

    public static REG_RESULT regUser(String login, String email, String password) {
        if (isUserExists(login)) {
            return REG_RESULT.USER_EXISTS;
        } else {
            UserInfo newUser = new UserInfo();

            if (!newUser.setLogin(login)) {
                return REG_RESULT.WRONG_LOGIN;
            //} else if (!newUser.setEmail(email)) {
                //return REG_RESULT.WRONG_EMAIL;
            } else if (!newUser.setPassword(password)) {
                return REG_RESULT.WRONG_PASSWORD;
            } else {
                userMap.put(login, newUser);

                return REG_RESULT.SUCCESS;
            }
        }
    }

    public static boolean isUserExists(String login) {
        return userMap.containsKey(login);
    }

    public static LOGIN_RESULT logUser(String login, String password) {
        if (!isUserExists(login)) {
            return LOGIN_RESULT.WRONG_LOGIN;
        } else {
            UserInfo user = userMap.get(login);

            if (!user.getPassword().equals(password)) {
                return LOGIN_RESULT.WRONG_PASSWORD;
            } else {
                return LOGIN_RESULT.SUCCESS;
            }
        }
    }

    public static UserInfo getUserInfo(String login) {
        if (!isUserExists(login)) {
            return null;
        } else {
            return userMap.get(login);
        }
    }
}
;