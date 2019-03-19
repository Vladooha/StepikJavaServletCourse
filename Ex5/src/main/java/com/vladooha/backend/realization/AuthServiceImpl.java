package com.vladooha.backend.realization;

import com.vladooha.backend.AuthService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AuthServiceImpl implements AuthService {
    private static final Logger logger = LogManager.getLogger(AuthServiceImpl.class.getName());

    private static AuthServiceImpl instance = null;

    public static AuthServiceImpl getInstance() {
        if (instance == null) {
            instance = new AuthServiceImpl();
        }

        return instance;
    }

    private int userLimit = 10;
    private int userCount = 0;

    private AuthServiceImpl() { }

    public int getUserLimit() {
        return userLimit;
    }

    public void setUserLimit(int userLimit) {
        this.userLimit = userLimit;
    }

    public int getUserCount() {
        return userCount;
    }

    public boolean authUser(String user) {
        if (userCount < userLimit) {
            userCount++;

            logger.info("User '" + user +"' logged in");
            logger.info("Online users: " + userCount + ", limit: " + userLimit);

            return true;
        } else {
            logger.info("User '" + user +"' wasn't logged in, limit is reached");
            logger.info("Online users: " + userCount + ", limit: " + userLimit);

            return false;
        }
    }

    public boolean unlogUser(String user) {
        if (userCount > 0) {
            userCount--;

            logger.info("User '" + user +"' unlogged");
            logger.info("Online users: " + userCount + ", limit: " + userLimit);

            return true;
        } else {
            logger.info("User '" + user +"' wasn't unlogged");
            logger.info("Online users: " + userCount + ", limit: " + userLimit);

            return false;
        }
    }

    public void unlogAll() {
        logger.info("Unlogging all users");
        logger.info("Online users: " + userCount + ", limit: " + userLimit);

        userCount = 0;
    }
}
