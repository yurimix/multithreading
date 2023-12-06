package dev.example.multithreading.blockingqueue;

import java.util.List;

import static java.lang.System.out;
import static java.lang.Thread.currentThread;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class PutDataTask<T> implements Runnable {

    private final MyBlockingQueue<DataTransferObject<T>> queue;
    private final List<DataTransferObject<T>> dataObjects;

    private final int delayInMs;

    public PutDataTask(MyBlockingQueue<DataTransferObject<T>> queue, List<DataTransferObject<T>> dataObjects, int delayInMs) {
        this.queue = queue;
        this.dataObjects = dataObjects;
        this.delayInMs = delayInMs;
    }

    @Override
    public void run() {
        out.println("Put data task started.");
        try {
            out.printf("Putting %d data objects into the queue\n", dataObjects.size());
            for (final DataTransferObject<T>dto : dataObjects) {
                if (!currentThread().isInterrupted()) {
                    queue.put(dto);
                    out.println("Transfer object was queued: " + dto + ", queue size: " + queue.size());
                    MILLISECONDS.sleep(delayInMs);
                }
            }
            out.println("Put data task finished.");
        } catch (InterruptedException e) {
            out.println("Put data task interrupted: " + e );
            currentThread().interrupt();
        }
    }
}
