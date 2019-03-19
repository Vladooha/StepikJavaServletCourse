package com.vladooha.beans;

import javax.management.DynamicMBean;

public interface AccountServerControllerMBean {
    void setUserLimit(int userLimit);
    int getUserLimit();

    int getUserCount();
}
