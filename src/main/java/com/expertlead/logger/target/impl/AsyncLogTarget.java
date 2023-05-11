package com.expertlead.logger.target.impl;

import com.expertlead.logger.Level;
import com.expertlead.logger.Message;
import com.expertlead.logger.target.Target;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class AsyncLogTarget implements Target {

    private final Target target;
    private final int workerThreads;
    private final BlockingQueue<Message> queue;
    private final ExecutorService executorService;

    public AsyncLogTarget(Target target, int workerThreads) {

        this.target = target;
        this.workerThreads = workerThreads;
        this.queue = new LinkedBlockingQueue<>();
        this.executorService = Executors.newFixedThreadPool(workerThreads);
        startWorkerThreads();
    }

    @Override
    public void log(Message message) {
        try {
            queue.put(message);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
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

    private void startWorkerThreads() {
        for (int i = 0; i < workerThreads; i++) {
            executorService.submit(new LogWorker());
        }
    }

    private class LogWorker implements Runnable {
        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Message message = queue.take();
                    target.log(message);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public void shutdown() {
        executorService.shutdown();
    }
}
