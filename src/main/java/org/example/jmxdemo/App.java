package org.example.jmxdemo;

import org.example.jmxdemo.remote.RemoteAgent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(App.class, args);
        RemoteAgent.startRmiConnector(1099);
    }
}
