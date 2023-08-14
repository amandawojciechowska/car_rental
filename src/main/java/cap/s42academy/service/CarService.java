package cap.s42academy.service;

import cap.s42academy.model.Car;

import java.util.List;
import java.util.Optional;

public interface CarService {

    Optional<Car> getCar(Long carId);

    Car addCar(Car car);

    Car updateCar(Car car);

    Optional<Car> getCarByLicencePlate(String licencePlate);

    void deleteCar(Long carId);

}
