package dev.example.multithreading.objectpool.pool;

import dev.example.multithreading.objectpool.model.Car;

import static dev.example.multithreading.objectpool.pool.CarPoolGenerator.init;

public class CarPool extends AbstractPool<Car> {

    public CarPool() {
        super(init(), CarPool::washCar);
    }

    public Car takeCar() {
        return acquire();
    }

    public void returnCar(Car car) {
        release(car);
    }

    public static void washCar(Car car) {
        car.setDirty(false);
    }
}
