package dev.example.multithreading.objectpool.pool;

import dev.example.multithreading.objectpool.model.Car;
import dev.example.multithreading.objectpool.model.CarBrand;
import dev.example.multithreading.objectpool.model.CarType;
import dev.example.multithreading.objectpool.pool.PoolObject;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CarPoolGenerator {
    public static List<PoolObject<Car>> init() {
        Random random = new Random();
        CarType[] carTypes = CarType.values();
        CarBrand[] carBrands = CarBrand.values();
        return IntStream.range(0, carBrands.length).
                mapToObj(i -> new Car(carTypes[random.nextInt(carTypes.length)], carBrands[i])).
                map(PoolObject::new).
                collect(Collectors.toList());
    }
}
