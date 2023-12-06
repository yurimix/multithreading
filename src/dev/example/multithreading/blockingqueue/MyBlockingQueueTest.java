package dev.example.multithreading.blockingqueue;

import static java.lang.System.out;

import dev.example.multithreading.blockingqueue.awaitsignal.LockConditionMyBlockingQueueImpl;
import dev.example.multithreading.blockingqueue.waitnotify.WaitNotifyMyBlockingQueueImpl;

import java.util.concurrent.TimeUnit;

import static dev.example.multithreading.blockingqueue.Utils.generateDataChain;

public class MyBlockingQueueTest {

    public static final int DATA_QUEUE_CAPACITY = 10;
    public static final int PUT_DATA_CHAIN_SIZE = 50;
    public static final int PUT_DATA_DELAY_MS = 100;
    public static final int TAKE_DATA_DELAY_MS = 150;

    public static void main(String[] args) throws InterruptedException {
        test(new WaitNotifyMyBlockingQueueImpl<String>(DATA_QUEUE_CAPACITY), "WaitNotifyBlockingQueue");
        test(new LockConditionMyBlockingQueueImpl<String>(DATA_QUEUE_CAPACITY), "LockConditionBlockingQueue");
    }

    private static void test(MyBlockingQueue<DataTransferObject<String>> blockingQueue, String queueName) throws InterruptedException {
        out.println(queueName + " >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        // data
        var dataChain = generateDataChain(PUT_DATA_CHAIN_SIZE);

        // tasks
        var putDataTask = new PutDataTask<String>(blockingQueue, dataChain, PUT_DATA_DELAY_MS);
        var takeDataTask = new TakeDataTask<String>(blockingQueue, TAKE_DATA_DELAY_MS);

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
        takeDataThread.join();
        out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< " + queueName);
    }

}
