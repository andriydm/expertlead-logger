package com.expertlead.logger.target.impl;

import com.expertlead.logger.Level;
import com.expertlead.logger.Message;
import com.expertlead.logger.target.Target;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TimedBatchedLogTarget implements Target {

    private final Target target;
    private final int batchSize;
    private final int timeIntervalSeconds;
    private final List<Message> buffer;
    private ScheduledExecutorService executorService;

    public TimedBatchedLogTarget(Target target, int batchSize, int timeIntervalSeconds) {
        this.target = target;
        this.batchSize = batchSize;
        this.timeIntervalSeconds = timeIntervalSeconds;
        this.buffer = new ArrayList<>();
        this.executorService = Executors.newSingleThreadScheduledExecutor();
        scheduleFlushTask();
    }

    @Override
    public void log(Message message) {
        synchronized (buffer) {
            buffer.add(message);
        }
    }

    @Override
    public void log(List<Message> message) {
        message.forEach(m -> log(m));
    }

    @Override
    public Level getLogLevel() {
        return target.getLogLevel();
    }

    private void scheduleFlushTask() {
        executorService.scheduleAtFixedRate(this::flush, timeIntervalSeconds, timeIntervalSeconds, TimeUnit.SECONDS);
    }

    public void flush() {
        synchronized (buffer) {
            if (buffer.isEmpty()) {
                return;
            }
            List<Message> batch = new ArrayList<>(buffer.subList(0, Math.min(batchSize, buffer.size())));
            target.log(batch);
            buffer.removeAll(batch);
        }
    }

    public void shutdown() {
        executorService.shutdown();
        flush();
    }
}
