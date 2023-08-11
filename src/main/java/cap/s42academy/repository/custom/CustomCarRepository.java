package cap.s42academy.repository.custom;

import cap.s42academy.model.Car;

import java.util.Optional;

public interface CustomCarRepository {

    Optional<Car> getCarByLicencePlate(String licencePlate);

}
