package dev.example.multithreading.blockingqueue;

import static java.lang.System.out;
import static java.lang.Thread.currentThread;
import static java.util.concurrent.TimeUnit.SECONDS;

public class TakeDataTask implements Runnable {

    private final MyBlockingQueue queue;

    private final int delayInSeconds;

    public TakeDataTask(MyBlockingQueue queue, int delayInSeconds) {
        this.queue = queue;
        this.delayInSeconds = delayInSeconds;
    }

    @Override
    public void run() {
        out.println("Take data task started.");
        try {
            while (!currentThread().isInterrupted()) {
                var dto = queue.take();
                out.println("The object was taken from the queue: " + dto + ", queue size: " + queue.size());
                SECONDS.sleep(delayInSeconds);
            }
        } catch (InterruptedException ex) {
            currentThread().interrupt();
        } catch (Exception ex) {
            out.println("Something was wrong:" + ex);
        }
        out.println("Take data task stopped.");
    }
}


