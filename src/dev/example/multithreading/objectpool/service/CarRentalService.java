package dev.example.multithreading.objectpool.service;

import dev.example.multithreading.objectpool.model.Car;

public interface CarRentalService {

    Car rentCar();

    void returnCar(Car car);

    void printStatistics();
}
