package org.example.jmxdemo.notification;

import org.springframework.stereotype.Component;

import javax.management.MBeanServer;
import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

@Component
public class NotificationConfig extends NotificationBroadcasterSupport implements NotificationConfigMBean {
    private long sequenceNumber = 1;
    private volatile boolean running = false;
    private Thread worker;

    public NotificationConfig() throws Exception {
        // register with platform server under a specific name
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName("com.example.jmx:type=NotificationConfig");
        if (!mbs.isRegistered(name)) mbs.registerMBean(this, name);
    }

    @Override
    public void start() {
        if (running) return;
        running = true;
        worker = new Thread(() -> {
            try {
                while (running) {
                    Notification n = new Notification("com.example.jmx.notification", this, sequenceNumber++, System.currentTimeMillis(), "heartbeat");
                    sendNotification(n);
                    Thread.sleep(5000);
                }
            } catch (InterruptedException ignored) {
            }
        }, "jmx-notifier");
        worker.setDaemon(true);
        worker.start();
    }

    @Override
    public void stop() {
        running = false;
        if (worker != null) {
            worker.interrupt();
        }
    }
}
