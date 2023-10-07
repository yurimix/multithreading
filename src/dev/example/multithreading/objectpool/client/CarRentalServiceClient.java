package dev.example.multithreading.objectpool.client;

import dev.example.multithreading.objectpool.service.CarRentalService;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.currentThread;
import static java.util.concurrent.TimeUnit.SECONDS;

public class CarRentalServiceClient implements Callable<Integer> {

    private final CarRentalService service;

    public CarRentalServiceClient(CarRentalService service) {
        this.service = service;
    }

    @Override
    public Integer call() {
        var car = this.service.rentCar();
        try {
            SECONDS.sleep(new Random().nextInt(3));
        } catch (InterruptedException ex) {
            currentThread().interrupt();
        } finally {
            this.service.returnCar(car);
        }
        return 0;
    }
}
