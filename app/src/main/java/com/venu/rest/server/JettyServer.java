package com.venu.rest.server;

import com.codahale.metrics.jetty9.InstrumentedHandler;
import com.venu.rest.Main;
import com.venu.rest.common.util.HelloWorldUtil;

import org.eclipse.jetty.jmx.MBeanContainer;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.thread.MonitoredQueuedThreadPool;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.management.ManagementFactory;
import java.net.URL;
import java.security.ProtectionDomain;

public class JettyServer implements Server {

    private static final Logger LOG = LoggerFactory.getLogger(JettyServer.class);

    private org.eclipse.jetty.server.Server server;

    @Override
    public void start() {
        QueuedThreadPool threadPool = new MonitoredQueuedThreadPool();
        threadPool.setName("HelloWorldQTPool");
        threadPool.setMaxThreads(50);
        threadPool.setMinThreads(20);
        threadPool.setIdleTimeout(30000);

        HttpConfiguration httpConfig = new HttpConfiguration();
        httpConfig.setSecureScheme("https");
        httpConfig.setSecurePort(443);
        httpConfig.setSendServerVersion(false);
        httpConfig.setSendDateHeader(false);

        server = new org.eclipse.jetty.server.Server(threadPool);

        /* Add Jetty Logging */
        //        server.setRequestLog(new JettyRequestLog());

        server.setAttribute("org.eclipse.jetty.server.Request.maxFormContentSize",
                            3000000);

        ServerConnector httpConnector = new ServerConnector(server, new HttpConnectionFactory(httpConfig));
        httpConnector.setPort(8080);
        httpConnector.setIdleTimeout(30000);
        server.addConnector(httpConnector);

        /* ------------------ */
        /* Add WebApp Context */
        /* ------------------ */
        ProtectionDomain domain = Main.class.getProtectionDomain();
        URL location = domain.getCodeSource().getLocation();
        WebAppContext webAppContext = new WebAppContext();
        webAppContext.setContextPath("/");
        webAppContext.setWar(location.toExternalForm());

        /* Disable directory listing */
        webAppContext.setInitParameter("org.eclipse.jetty.servlet.Default.dirAllowed", Boolean.FALSE.toString());

        InstrumentedHandler instrumentedHandler = new InstrumentedHandler(HelloWorldUtil.METRIC_REGISTRY);
        instrumentedHandler.setHandler(webAppContext);
        server.setHandler(instrumentedHandler);

        //        server.addLifeCycleListener(jettyListener);

        /* Setup JMX */
        MBeanContainer mbeanContainer = new MBeanContainer(ManagementFactory.getPlatformMBeanServer());
        server.addBean(mbeanContainer);
        server.addBean(Log.getLog());

        /* Start the server */
        try {
            server.start();
            server.join();
        } catch (Exception e) {
            LOG.error("Error starting jetty server...", e);
        }
    }

    @Override
    public void stop() {
        try {
            server.stop();
        } catch (Exception e) {
            LOG.error("Error stopping Jetty Server.", e);
        }
    }
}
