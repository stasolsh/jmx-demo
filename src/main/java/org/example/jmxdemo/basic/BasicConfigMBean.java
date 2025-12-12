package org.example.jmxdemo.basic;

public interface BasicConfigMBean {
    int getThreadCount();

    void setThreadCount(int noOfThreads);

    String getSchemaName();

    void setSchemaName(String schemaName);

    void doConfig();
}
