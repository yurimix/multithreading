# Like a blocking queue...

This code is not pretend to replace JAVA BlockingQueue :),
just an example how to work with data like a queue concurrently
using standard Java approaches.

## MyBlockingQueue interface
```java
public interface MyBlockingQueue<T> {

    void put(T data);

    T take();

    int size();
}
```
I guess I don't need to comment on what these methods do...

The example contains two implementations of this interface:

* based on `wait-notify`
* based on lock condition `await-signal`

__Main behavior__: one thread puts data into a queue, another one reads.  
__Additional complication:__ the queue size cannot exceed a specified value.  
* If a thread tries to put data into the queue when the queue is full,
the thread must wait until the queue size decreases.  
* If a thread tries to read data from the queue when the queue is empy,
the thread must wait until the data appears.  






