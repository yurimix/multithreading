/**
 * Like a blocking queue...
 */
package dev.example.multithreading.blockingqueue;

public interface MyBlockingQueue<T> {

    void put(T data);

    T take();

    int size();
}
