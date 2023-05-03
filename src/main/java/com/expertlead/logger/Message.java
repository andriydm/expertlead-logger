package com.expertlead.logger;

import java.time.LocalDateTime;

public class Message {

    private final LocalDateTime timestamp;
    private final Level level;
    private final String message;

    public Message(LocalDateTime timestamp, Level logLevel, String message) {

        this.timestamp = timestamp;
        this.level = logLevel;
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public Level getLogLevel() {
        return level;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return String.format("{%s} [%s] - %s", timestamp.toString(), level.toString(), message);
    }
}

