package dev.example.multithreading.objectpool.pool;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Semaphore;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class AbstractPool <T> {

    private final List<PoolObject<T>> poolObjects;

    private final Consumer<T>cleaner;

    private final Semaphore semaphore;

    /**
     * Constructor
     * @param poolObjects - list of objects
     * @param cleaner - to clean pool object after use
     */
    protected AbstractPool(List<PoolObject<T>> poolObjects, Consumer<T>cleaner) {
        this.poolObjects = poolObjects;
        this.cleaner = cleaner;
        this.semaphore = new Semaphore(poolObjects.size());
    }

    protected T acquire() {
        this.semaphore.acquireUninterruptibly();
        try {
            return acquireObject();
        } catch (IllegalStateException ex) {
            this.semaphore.release();
            throw ex;
        }
    }

    protected void release(T obj) {
        releaseObject(obj);
        this.semaphore.release();
    }

    private synchronized T acquireObject() {
        return this.poolObjects.stream().
            filter(p -> !p.isIssued()).
            findFirst().
            map(this::issueObject).
            map(PoolObject::getData).
            orElseThrow(IllegalStateException::new);
    }

    private synchronized void releaseObject(T po) {
        this.poolObjects.stream().
            filter(PoolObject::isIssued).
            filter(p -> Objects.equals(p.getData(), po)).
            findFirst().
            map(this::returnObject).
            orElseThrow(IllegalArgumentException::new);
    }

    private PoolObject<T> issueObject(PoolObject<T> po) {
        po.setIssued(true);
        return po;
    }

    private PoolObject<T> returnObject(PoolObject<T> po) {
        po.setIssued(false);
        this.cleaner.accept(po.getData());
        return po;
    }

    public List<String> statistics() {
        return poolObjects.stream().
            map(po -> po.getData().toString() + ", issued: " + po.isIssued()).collect(Collectors.toList());
    }
}
