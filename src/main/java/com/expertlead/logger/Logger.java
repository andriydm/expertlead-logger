package com.expertlead.logger;

public interface Logger {

    void error(String message);

    void warn(String message);

    void info(String message);

    void debug(String message);
}
