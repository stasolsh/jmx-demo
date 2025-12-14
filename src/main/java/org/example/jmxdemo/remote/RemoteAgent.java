package org.example.jmxdemo.remote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.MBeanServer;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;
import java.lang.management.ManagementFactory;
import java.rmi.registry.LocateRegistry;

public class RemoteAgent {
    private static final Logger log = LoggerFactory.getLogger(RemoteAgent.class);
    private static JMXConnectorServer connectorServer;

    public static void startRmiConnector(int port) throws Exception {
        // Start RMI registry if not already running
        try {
            LocateRegistry.createRegistry(port);
            log.info("RMI registry started on port {}", port);
        } catch (Exception e) {
            log.error("RMI registry already running on port {}", port);
        }

        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        String serviceUrl = String.format("service:jmx:rmi:///jndi/rmi://localhost:%d/jmxrmi", port);
        JMXServiceURL url = new JMXServiceURL(serviceUrl);

        connectorServer = JMXConnectorServerFactory.newJMXConnectorServer(url, null, mbs);
        connectorServer.start();
        log.info("JMX RMI connector started at: {}", serviceUrl);
    }

    public static void stop() throws Exception {
        if (connectorServer != null) {
            connectorServer.stop();
        }
    }
}
