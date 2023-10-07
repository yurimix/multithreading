package dev.example.multithreading.blockingqueue.waitnotify;

import dev.example.multithreading.blockingqueue.DataTransferObject;
import dev.example.multithreading.blockingqueue.MyBlockingQueue;

import java.util.LinkedList;

import static java.lang.Thread.currentThread;

public class WaitNotifyMyBlockingQueueImpl implements MyBlockingQueue<DataTransferObject> {

    private final LinkedList<DataTransferObject> data;

    private final int capacity;

    public WaitNotifyMyBlockingQueueImpl(final int capacity) {
        this.data = new LinkedList<>();
        this.capacity = capacity;
    }

    public synchronized void put(final DataTransferObject data) {
        try {
            while (this.data.size() == this.capacity) {
                wait();
            }
            this.data.add(data);
            notify();
        } catch (InterruptedException e) {
            currentThread().interrupt();
        }
    }

    public synchronized DataTransferObject take() {
        try {
            while (this.data.isEmpty()) {
                wait();
            }
            final var dto = this.data.poll();
            notify();
            return dto;
        } catch (InterruptedException e) {
            currentThread().interrupt();
            throw new RuntimeException("Cannot take object, monitor was interrupted.");
        }
    }

    public synchronized int size() {
        return this.data.size();
    }
}
