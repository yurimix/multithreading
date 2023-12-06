package dev.example.multithreading.blockingqueue.awaitsignal;

import static java.lang.Thread.currentThread;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import dev.example.multithreading.blockingqueue.DataTransferObject;
import dev.example.multithreading.blockingqueue.MyBlockingQueue;

public class LockConditionMyBlockingQueueImpl<T> implements MyBlockingQueue<DataTransferObject<T>> {
    private final LinkedList<DataTransferObject<T>> data;
    private final int capacity;
    private final Lock lock;
    private final Condition queueEmptyCondition;
    private final Condition queueFullCondition;

    public LockConditionMyBlockingQueueImpl(final int capacity) {
        this.data = new LinkedList<>();
        this.capacity = capacity;

        this.lock = new ReentrantLock();
        this.queueEmptyCondition = lock.newCondition();
        this.queueFullCondition = lock.newCondition();
    }

    @Override
    public void put(final DataTransferObject<T> data) {
        lock.lock();
        try {
            while (this.data.size() == this.capacity) {
                queueFullCondition.await();
            }
            this.data.add(data);
            queueEmptyCondition.signal();
        } catch (InterruptedException ex) {
            currentThread().interrupt();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public DataTransferObject<T> take() {
        lock.lock();
        try {
            while (this.data.isEmpty()) {
                queueEmptyCondition.await();
            }
            final var dto = this.data.poll();
            queueFullCondition.signal();
            return dto;
        } catch (InterruptedException ex) {
            currentThread().interrupt();
            throw new RuntimeException("Cannot take object, condition was interrupted.");
        } finally {
            lock.unlock();
        }
    }

    @Override
    public int size() {
        lock.lock();
        try {
            return this.data.size();
        } finally {
            lock.unlock();
        }
    }
}
