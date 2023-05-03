package com.expertlead.logger;

import com.expertlead.logger.utils.StringUtils;

public final class Level implements Comparable<Level> {

    public static final Level ERROR;
    public static final Level WARN;
    public static final Level INFO;
    public static final Level DEBUG;

    private final String name;
    private final int order;

    private Level(final String name, final int order) {

        if (StringUtils.isEmpty(name)) {
            throw new IllegalArgumentException("Level name can not be null or empty.");
        } else if (order < 0) {
            throw new IllegalArgumentException("Level order must be equals or greater than zero.");
        } else {
            this.name = name;
            this.order = order;
        }
    }

    public boolean isEqualOrLessThan(final Level level) {
        return compareTo(level) >= 0 ;
    }

    @Override
    public int compareTo(Level other) {
        return Integer.compare(this.order, other.order);
    }

    public String toString() {
        return this.name;
    }

    static {
        ERROR = new Level("ERROR", 0);
        WARN = new Level("WARN", 1);
        INFO = new Level("INFO", 2);
        DEBUG = new Level("DEBUG", 3);
    }
}
