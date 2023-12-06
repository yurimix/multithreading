package dev.example.multithreading.objectpool.service;

import static java.lang.System.out;

import dev.example.multithreading.objectpool.model.Car;
import dev.example.multithreading.objectpool.pool.CarPool;

public class CarRentalServiceImpl implements CarRentalService {

    private final CarPool carPool;

    public CarRentalServiceImpl() {
        this.carPool = new CarPool();
    }

    @Override
    public Car rentCar() {
        out.println("The client wants to rent a car...");
        var car = carPool.takeCar();
        out.println("The car " + car + " was rented");
        return car;
    }

    @Override
    public void returnCar(Car car) {
        car.setDirty(true);
        out.println("The client wants to return the car " + car);
        carPool.returnCar(car);
        out.println("The car " + car.getVin() + " was returned");
    }

    public void printStatistics() {
        this.carPool.statistics().forEach(out::println);
    }

}
