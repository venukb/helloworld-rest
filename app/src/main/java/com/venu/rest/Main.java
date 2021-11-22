package com.venu.rest;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.venu.rest.server.Server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger LOG = LoggerFactory.getLogger(Main.class);
    public static final Injector GUICEINJ = Guice.createInjector(new HelloWorldGuiceModule());

    private Server instance;

    public static void main(String[] args) throws Exception {
        new Main().process(args);
    }

    /**
     *
     */
    public void process(String[] args) throws Exception {
        startWebServer();
    }

    private void startWebServer() {
        instance = GUICEINJ.getInstance(Server.class);
        instance.start();
    }

    public void stopWebServer() {
        if(instance != null) {
            instance.stop();
        }
    }

}
