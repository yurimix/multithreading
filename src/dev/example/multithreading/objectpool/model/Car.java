package dev.example.multithreading.objectpool.model;

import java.util.Objects;
import java.util.UUID;

import static java.util.UUID.randomUUID;

public class Car {

    private final UUID vin;
    private final CarType carType;
    private final CarBrand carBrand;
    private boolean dirty;

    private int rentNum = 0;

    public Car(CarType carType, CarBrand carBrand) {
        this.vin = randomUUID();
        this.carType = carType;
        this.carBrand = carBrand;
    }

    public CarType getCarType() {
        return carType;
    }

    public CarBrand getCarBrand() {
        return carBrand;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Car car = (Car) o;
        return Objects.equals(this.vin, car.vin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.vin);
    }

    public boolean isDirty() {
        return dirty;
    }

    public void setDirty(boolean dirty) {
        this.dirty = dirty;
        if (dirty) {
            rentNum++;
        }
    }

    public UUID getVin() {
        return vin;
    }

    @Override
    public String toString() {
        return "Car {" + "VIN: " + vin +
            ", type: " + carType +
            ", brand: " + carBrand +
            ", dirty: " + dirty +
            ", rentNum: " + rentNum +
            '}';
    }
}

