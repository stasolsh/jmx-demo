package org.example.jmxdemo.basic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;


@Component
@ManagedResource(objectName = "com.example.jmx:type=BasicConfig", description = "Basic JMX Config")
public class BasicConfig implements BasicConfigMBean {
    private static final Logger log = LoggerFactory.getLogger(BasicConfig.class);
    private int threadCount = 10;
    private String schemaName = "default";

    @ManagedAttribute
    public int getThreadCount() {
        return threadCount;
    }

    @ManagedAttribute
    public void setThreadCount(int threadCount) {
        this.threadCount = threadCount;
    }

    @ManagedAttribute
    public String getSchemaName() {
        return schemaName;
    }

    @ManagedAttribute
    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    @ManagedOperation
    public void doConfig() {
        log.info("Applying config -> threads: {}, schema: {}", threadCount, schemaName);
    }
}
