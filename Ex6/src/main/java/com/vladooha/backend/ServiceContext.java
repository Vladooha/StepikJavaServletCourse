package com.vladooha.backend;

import java.util.HashMap;
import java.util.Map;

public class ServiceContext {
    private Map<Class, Object> serviceMap = new HashMap<>();

    public boolean put(Class serviceClass, Object service) {
        if (serviceClass != null &&
                !serviceMap.containsKey(serviceClass) &&
                service != null &&
                serviceClass.isAssignableFrom(service.getClass())) {

            serviceMap.put(serviceClass, service);

            return true;
        } else {
            return false;
        }
    }

    public <T> T get(Class<T> serviceClass) {
        if (serviceClass != null && serviceMap.containsKey(serviceClass)) {
            return (T)(serviceMap.get(serviceClass));
        } else {
            return null;
        }
    }

    public <T> T remove(Class<T> serviceClass) {
        if (serviceClass != null && serviceMap.containsKey(serviceClass)) {
            return (T)(serviceMap.remove(serviceClass));
        } else {
            return null;
        }
    }
}