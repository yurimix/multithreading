package dev.example.multithreading.objectpool.pool;

public class PoolObject <T> {

    private final T data;
    private boolean issued;

    public PoolObject(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public boolean isIssued() {
        return issued;
    }

    public void setIssued(boolean issued) {
        this.issued = issued;
    }
}
