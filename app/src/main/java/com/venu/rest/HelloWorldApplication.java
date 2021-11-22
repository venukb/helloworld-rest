package com.venu.rest;

import com.codahale.metrics.jersey2.InstrumentedResourceMethodApplicationListener;
import com.venu.rest.common.util.HelloWorldUtil;

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.message.DeflateEncoder;
import org.glassfish.jersey.message.GZipEncoder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.EncodingFilter;
import org.jvnet.hk2.guice.bridge.api.GuiceBridge;
import org.jvnet.hk2.guice.bridge.api.GuiceIntoHK2Bridge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

public class HelloWorldApplication extends ResourceConfig {

    private static final Logger LOG = LoggerFactory.getLogger(HelloWorldApplication.class);

    @Inject
    public HelloWorldApplication(ServiceLocator serviceLocator) {

        LOG.info("Initializing HelloWorldApplication Application...");

        /* Jersey 2 Metring by Dropwizard */
        register(new InstrumentedResourceMethodApplicationListener(HelloWorldUtil.METRIC_REGISTRY));

        /* Packages to find REST/Jersey resources */
        packages("com.venu.rest.res", "com.venu.rest.res.filters");

        /* Use Jackson as JSON provider for Jersey*/
        register(JacksonFeature.withoutExceptionMappers());

        /* Jackson ObjectMapper */
        register(HelloWorldObjectMapperProvider.class);

        /* Accept-Encoding - Content-Encoding Handling */
        register(EncodingFilter.class);
        register(GZipEncoder.class);
        register(DeflateEncoder.class);

        /* Initialize Guice bridge */
        GuiceBridge.getGuiceBridge().initializeGuiceBridge(serviceLocator);
        final GuiceIntoHK2Bridge guiceBridge = serviceLocator.getService(GuiceIntoHK2Bridge.class);
        guiceBridge.bridgeGuiceInjector(Main.GUICEINJ);
    }
}


