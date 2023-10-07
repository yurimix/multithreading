package dev.example.multithreading.objectpool.service;

import dev.example.multithreading.objectpool.model.Car;
import dev.example.multithreading.objectpool.model.CarType;
import dev.example.multithreading.objectpool.pool.CarPool;

import static java.lang.System.out;

public class CarRentalServiceImpl implements CarRentalService {

    private final CarPool carPool;

    public CarRentalServiceImpl() {
        this.carPool = new CarPool();
    }

    @Override
    public Car rentCar() {
        out.println("Client want to rent a car...");
        var car = carPool.takeCar();
        out.println("Car " + car + " was rented");
        return car;
    }

    @Override
    public void returnCar(Car car) {
        car.setDirty(true);
        out.println("Client want to return car " + car);
        carPool.returnCar(car);
        out.println("Car " + car.getVin() + " was returned");
    }

    public void printStatistics() {
        this.carPool.statistics().forEach(out::println);
    }

}
