package com.expertlead.logger.target.impl;

import com.expertlead.logger.Level;

public abstract class AbstractLogTarget {

    private volatile Level minLogLevel = Level.INFO;

    public Level getMinLogLevel() {
        return minLogLevel;
    }

    public void setMinLogLevel(Level minLogLevel) {
        this.minLogLevel = minLogLevel;
    }
}
