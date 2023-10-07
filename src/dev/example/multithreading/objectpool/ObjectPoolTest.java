package dev.example.multithreading.objectpool;

import dev.example.multithreading.objectpool.client.CarRentalServiceClient;
import dev.example.multithreading.objectpool.service.CarRentalService;
import dev.example.multithreading.objectpool.service.CarRentalServiceImpl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ObjectPoolTest {

    static CarRentalService service;

    public static void main(String[] args) throws InterruptedException {

        service = new CarRentalServiceImpl();
        final int clientsNum = 100;

        var clients = Stream.
            generate(ObjectPoolTest::createCarRentalServiceClient).
            limit(clientsNum).
            collect(Collectors.toList());

        try(ExecutorService executorService = Executors.newFixedThreadPool(clientsNum)) {
            executorService.invokeAll(clients);
            executorService.shutdown();
        }

        service.printStatistics();

    }

    private static CarRentalServiceClient createCarRentalServiceClient() {
        return new CarRentalServiceClient(service);
    }
}
