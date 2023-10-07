# Poll of objects

__The issue:__ a car rental service has several cars for business use.
Clients of the service want to rent cars for a while,
but the number of cars is significantly less than the number of clients.
Fortunately they are willing to wait :)

Yes, this example is a bit contrived, but its purpose is to show
how to create a pool of objects using semaphores.

## Acquire object from the pool 

```uml
title Rent a car
Client->CarRentalService: rent a car
CarRentalService->+CarPool: take a car
CarPool->CarPool: semaphore.acquireUninterruptibly()
CarPool->CarPool: synchronized acquireObject() 
CarPool->CarRentalService: Car
CarRentalService->Client: Car
```
![](https://www.websequencediagrams.com/cgi-bin/cdraw?lz=dGl0bGUgUmVudCBhIGNhcgpDbGllbnQtPkNhclJlbnRhbFNlcnZpY2U6IHIAGgsADg8tPitDYXJQb29sOiB0YWtlAB0KUG9vbABIBQAXBnNlbWFwaG9yZS5hY3F1aXJlVW5pbnRlcnJ1cHRpYmx5KCkAIRR5bmNocm9uaXplZCAAMQdPYmplY3QoKSAAWA0AgSMPQwCBFhUAgVwGABgG&s=default)

__Code template__
```java
Semaphore semaphore;

T acquire() {

    this.semaphore.acquire();
    // work with the pool
    synchronized(pool) {
        // get not issued object from a poll
        // mark the object as issued
        // ... something else ...
        // return the object
    }
}

```

## Return object to the pool

```uml
title Return the car
Client->CarRentalService: return the car
CarRentalService->+CarPool: return the car
CarPool->CarPool: synchronized releaseObject() 
CarPool->CarPool: semaphore.release()
CarPool->CarRentalService: 
CarRentalService->Client:
```

![](https://www.websequencediagrams.com/cgi-bin/cdraw?lz=dGl0bGUgUmV0dXJuIHRoZSBjYXIKQ2xpZW50LT5DYXJSZW50YWxTZXJ2aWNlOiByABoPABIPLT4rQ2FyUG9vbAAXFFBvb2wAUAUAGwZzeW5jaHJvbml6ZWQgcmVsZWFzZU9iamVjdCgpIAAcFGVtYXBob3JlLgAmBygpAEkNAIEcDwCBChMAgVIGOgoK&s=default)

__Code template__
```java
Semaphore semaphore;

void release(T obj) {

    // work with the pool
    synchronized(pool) {
        // find an appropiated issued object in the pool
        // if such object is not present -- return (or throw exception)
        // mark the object as not issued
        // ... something else ...
    }
    semaphore.release();
}

```
