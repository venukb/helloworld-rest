package com.venu.rest;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Singleton;
import com.google.inject.multibindings.MapBinder;
import com.google.inject.name.Names;
import com.venu.rest.server.JettyServer;
import com.venu.rest.server.Server;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HelloWorldGuiceModule implements Module {

    @Override
    public void configure(Binder binder) {
        binder.bind(Server.class).to(JettyServer.class).in(Singleton.class);
    }
}
