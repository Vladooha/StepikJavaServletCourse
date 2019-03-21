package com.vladooha.beans.realization;

import com.vladooha.beans.ResourceServerControllerMBean;
import com.vladooha.resources.TestResource;

public class ResourceServerController implements ResourceServerControllerMBean {
    private TestResource resource;

    public void setRecource(TestResource resource) {
        this.resource = resource;
    }

    @Override
    public int getAge() {
        return resource.getAge();
    }

    @Override
    public String getName() {
        return resource.getName();
    }
}
