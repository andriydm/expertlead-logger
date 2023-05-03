package com.expertlead.logger.target.impl;

import com.expertlead.logger.Level;
import com.expertlead.logger.Message;
import com.expertlead.logger.target.Target;

import java.util.List;

public class FileLogTarget extends AbstractLogTarget implements Target {

    @Override
    public void log(Message message) {
        System.out.printf("I AM A FILE TARGET: %s%n", message.toString());
    }

    @Override
    public void log(List<Message> message) {
        message.forEach(m -> log(message));
    }

    @Override
    public Level getLogLevel() {
        return getMinLogLevel();
    }
}
