package dev.example.multithreading.blockingqueue;

import dev.example.multithreading.blockingqueue.waitnotify.WaitNotifyMyBlockingQueueImpl;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import static dev.example.multithreading.blockingqueue.Utils.generateDataChain;

public class MyBlockingQueueTest {

    public static final int DATA_QUEUE_CAPACITY = 5;
    public static final int PUT_DATA_CHAIN_SIZE = 10;
    public static final int PUT_DATA_DELAY = 1;
    public static final int TAKE_DATA_DELAY = 1;

    public static void main(String[] args) throws InterruptedException {

        // queue
        MyBlockingQueue blockingQueue;

        LinkedBlockingQueue bq;

        blockingQueue = new WaitNotifyMyBlockingQueueImpl(DATA_QUEUE_CAPACITY);
        //blockingQueue = new ReentrantLockMyBlockingQueueImpl(DATA_QUEUE_CAPACITY);

        // data
        var dataChain = generateDataChain(PUT_DATA_CHAIN_SIZE);

        // tasks
        var putDataTask = new PutDataTask(blockingQueue, dataChain, PUT_DATA_DELAY);
        var takeDataTask = new TakeDataTask(blockingQueue, TAKE_DATA_DELAY);

        // threads
        var putDataThread = new Thread(putDataTask);
        var takeDataThread = new Thread(takeDataTask);

        // start
        putDataThread.start();
        takeDataThread.start();

        // final action
        putDataThread.join();
        while(blockingQueue.size() > 0) {
            TimeUnit.SECONDS.sleep(1);
        }
        takeDataThread.interrupt();
    }

}
