package dev.example.multithreading.blockingqueue;

import static java.lang.System.out;
import static java.lang.Thread.currentThread;
import static java.util.concurrent.TimeUnit.SECONDS;

public class PutDataTask implements Runnable {

    private final MyBlockingQueue queue;
    private final DataTransferObject[] dataObjects;

    private final int delayInSeconds;

    public PutDataTask(MyBlockingQueue queue, DataTransferObject[] dataObjects, int delayInSeconds) {
        this.queue = queue;
        this.dataObjects = dataObjects;
        this.delayInSeconds = delayInSeconds;
    }

    @Override
    public void run() {
        try {
            out.printf("Putting %d data objects into the queue\n", dataObjects.length);
            for (final DataTransferObject dto : dataObjects) {
                if (!currentThread().isInterrupted()) {
                    queue.put(dto);
                    out.println("Transfer object was queued: " + dto + ", queue size: " + queue.size());
                    SECONDS.sleep(delayInSeconds);
                }
            }
            out.println("All data objects were put.");
        } catch (InterruptedException e) {
            currentThread().interrupt();
        }
    }
}
