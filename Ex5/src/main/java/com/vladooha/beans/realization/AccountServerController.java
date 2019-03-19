package com.vladooha.beans.realization;

import com.vladooha.backend.AuthService;
import com.vladooha.backend.realization.AuthServiceImpl;
import com.vladooha.beans.AccountServerControllerMBean;

public class AccountServerController implements AccountServerControllerMBean {
    private final AuthService authService;

    public AccountServerController() {
        authService = AuthServiceImpl.getInstance();
    }

    public void setUserLimit(int userLimit) {
        authService.setUserLimit(userLimit);
    }

    public int getUserLimit() {
        return authService.getUserLimit();
    }

    public int getUserCount() {
        return authService.getUserCount();
    }
}
