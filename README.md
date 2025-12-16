# JMX Demo (Spring Boot)


This demo includes examples of:
- Basic MBean (Spring @ManagedResource)
- Dynamic MBean (implements `DynamicMBean`)
- Notification MBean (`NotificationBroadcasterSupport`)
- Remote JMX via RMI connector (programmatic)


## Run


1. Build: `mvn -q -DskipTests package`
2. Run: `java -jar target/jmx-demo-0.0.1-SNAPSHOT.jar`


The application will:
- Start Spring Boot
- Register Basic MBean and Notification MBean (via component scanning)
- Start an RMI-based JMX connector on port 1099
- Register a Dynamic MBean programmatically (see `DynamicMBeanExample.register()`)


## Access
- Local: open `jconsole` and connect to the local process
- Remote: connect JConsole to `service:jmx:rmi:///jndi/rmi://localhost:1099/jmxrmi`


## Notes
- Spring Boot's `spring.jmx.enabled` is enabled by default in many starters. If not, enable it in `application.properties`:


```
spring.jmx.enabled=true
```


- Jolokia is included for HTTP-based JMX if you prefer a browser API.