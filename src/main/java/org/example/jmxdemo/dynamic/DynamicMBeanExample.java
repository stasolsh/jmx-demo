package org.example.jmxdemo.dynamic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.*;
import java.lang.management.ManagementFactory;

public class DynamicMBeanExample implements DynamicMBean {
    private static final String MESSAGE = "Message";
    private static final Logger log = LoggerFactory.getLogger(DynamicMBeanExample.class);
    private String message = "hello";

    public DynamicMBeanExample() {
    }

    @Override
    public Object getAttribute(String attribute) throws AttributeNotFoundException {
        if (MESSAGE.equals(attribute)) {
            return message;
        }
        throw new AttributeNotFoundException(attribute);
    }

    @Override
    public void setAttribute(Attribute attribute) throws AttributeNotFoundException {
        if (attribute == null) {
            return;
        }
        if (MESSAGE.equals(attribute.getName())) {
            this.message = (String) attribute.getValue();
        } else {
            throw new AttributeNotFoundException(attribute.getName());
        }
    }

    @Override
    public AttributeList getAttributes(String[] attributes) {
        AttributeList list = new AttributeList();
        for (String attribute : attributes) {
            try {
                list.add(new Attribute(attribute, getAttribute(attribute)));
            } catch (Exception exception) {
                log.error(exception.getMessage(), exception);
            }
        }
        return list;
    }

    @Override
    public AttributeList setAttributes(AttributeList attributes) {
        AttributeList ret = new AttributeList();
        for (Object attribute : attributes) {
            Attribute a = (Attribute) attribute;
            try {
                setAttribute(a);
            } catch (AttributeNotFoundException e) {
                throw new RuntimeException(e);
            }
            ret.add(a);
        }
        return ret;
    }

    @Override
    public Object invoke(String actionName, Object[] params, String[] signature) {
        if ("echo".equals(actionName) && params != null && params.length == 1) {
            return "Echo: " + params[0];
        }
        return null;
    }

    @Override
    public MBeanInfo getMBeanInfo() {
        MBeanAttributeInfo[] attrs = new MBeanAttributeInfo[]{
                new MBeanAttributeInfo("Message", "java.lang.String", "A message attribute", true, true, false)
        };
        MBeanOperationInfo[] ops = new MBeanOperationInfo[]{
                new MBeanOperationInfo("echo", "Echo an object", new MBeanParameterInfo[]{new MBeanParameterInfo("in", "java.lang.Object", "input")}, "java.lang.String", MBeanOperationInfo.INFO)
        };
        return new MBeanInfo(this.getClass().getName(), "Dynamic MBean Example", attrs, null, ops, null);
    }

    // helper to register into platform MBean server
    public static void register() throws Exception {
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName("com.example.jmx:type=DynamicMBeanExample");
        DynamicMBeanExample mbean = new DynamicMBeanExample();
        if (!mbs.isRegistered(name)) {
            mbs.registerMBean(mbean, name);
        }
    }
}