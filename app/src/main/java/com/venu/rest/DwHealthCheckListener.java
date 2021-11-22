package com.venu.rest;

import com.codahale.metrics.health.HealthCheckRegistry;
import com.codahale.metrics.servlets.HealthCheckServlet;

import javax.servlet.annotation.WebListener;

@WebListener
public class DwHealthCheckListener extends HealthCheckServlet.ContextListener {


    public static final HealthCheckRegistry HEALTH_CHECK_REGISTRY = new HealthCheckRegistry();

    static {
        HEALTH_CHECK_REGISTRY.register("VKHW", new HelloWorldHealthCheck());
    }

    @Override
    protected HealthCheckRegistry getHealthCheckRegistry() {
        return HEALTH_CHECK_REGISTRY;
    }


}
