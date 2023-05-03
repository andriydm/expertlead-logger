package com.expertlead.logger;

import com.expertlead.logger.target.Target;

import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;

public class SimpleLogger implements Logger {

    private ConcurrentHashMap<String, Target> logTargets = new ConcurrentHashMap<>();

    public void log(Level level, String message) {

        Message logMessage = new Message(LocalDateTime.now(), level, message);
        for (Target target: logTargets.values()) {
            if (target.getLogLevel().isEqualOrLessThan(logMessage.getLogLevel())) {
                target.log(logMessage);
            }
        }
    }

    public void addLogTarget(String name, Target logTarget) {
        logTargets.put(name, logTarget);
    }

    public void removeLogTarget(String name) {
        logTargets.remove(name);
    }

    @Override
    public void error(String message) {
        log(Level.ERROR, message);
    }

    @Override
    public void warn(String message) {
        log(Level.WARN, message);
    }

    @Override
    public void info(String message) {
        log(Level.INFO, message);
    }

    @Override
    public void debug(String message) {
        log(Level.DEBUG, message);
    }
}
