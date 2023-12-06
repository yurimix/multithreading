package dev.example.multithreading.blockingqueue;

import static java.lang.System.out;
import static java.lang.Thread.currentThread;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class TakeDataTask<T> implements Runnable {

    private final MyBlockingQueue<DataTransferObject<T>> queue;

    private final int delayInMs;

    public TakeDataTask(MyBlockingQueue<DataTransferObject<T>> queue, int delayInMs) {
        this.queue = queue;
        this.delayInMs = delayInMs;
    }

    @Override
    public void run() {
        out.println("Take data task started.");
        try {
            while (!currentThread().isInterrupted()) {
                var dto = queue.take();
                out.println("The object was taken from the queue: " + dto + ", queue size: " + queue.size());
                MILLISECONDS.sleep(delayInMs);
            }
        } catch (InterruptedException ex) {
            currentThread().interrupt();
        } catch (Exception ex) {
            if (!currentThread().isInterrupted()) {
                out.println("Take data task: something was wrong:" + ex);
            }
        }
        out.println("Take data task finished.");
    }
}
