package com.example.config.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigServiceLogger {
    private static final Logger payloadLogger = LoggerFactory.getLogger("payload");
    private static final Logger errorLogger = LoggerFactory.getLogger("error");
    private static final Logger performanceLogger = LoggerFactory.getLogger("performance");

    /**
     * this constructor is not being called directly anywhere
     */
    private ConfigServiceLogger(){}

    public static Logger getPayloadLogger() {
        return payloadLogger;
    }

    public static Logger getErrorLogger() {
        return errorLogger;
    }

    public static Logger getPerformanceLogger() {
        return performanceLogger;
    }
}
