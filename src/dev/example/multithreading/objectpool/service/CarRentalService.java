package dev.example.multithreading.objectpool.service;

import dev.example.multithreading.objectpool.model.Car;
import dev.example.multithreading.objectpool.model.CarType;

public interface CarRentalService {

    Car rentCar();

    void returnCar(Car car);

    void printStatistics();
}
