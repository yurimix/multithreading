package dev.example.multithreading.blockingqueue.waitnotify;

import static java.lang.Thread.currentThread;

import java.util.LinkedList;

import dev.example.multithreading.blockingqueue.DataTransferObject;
import dev.example.multithreading.blockingqueue.MyBlockingQueue;

public class WaitNotifyMyBlockingQueueImpl<T> implements MyBlockingQueue<DataTransferObject<T>> {

    private final LinkedList<DataTransferObject<T>> data;

    private final int capacity;

    public WaitNotifyMyBlockingQueueImpl(final int capacity) {
        this.data = new LinkedList<>();
        this.capacity = capacity;
    }

    public synchronized void put(final DataTransferObject<T> data) {
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

    public synchronized DataTransferObject<T> take() {
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
