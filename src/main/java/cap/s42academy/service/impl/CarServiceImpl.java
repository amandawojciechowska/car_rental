package cap.s42academy.service.impl;

import cap.s42academy.exceptions.BusinessException;
import cap.s42academy.exceptions.CarNotFoundException;
import cap.s42academy.model.Car;
import cap.s42academy.model.CarRental;
import cap.s42academy.repository.CarRentalRepository;
import cap.s42academy.repository.CarRepository;
import cap.s42academy.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final CarRentalRepository carRentalRepository;

    @Override
    @Transactional(readOnly = true)
    public Optional<Car> getCar(final Long carId) {
        return carRepository.findById(carId);
    }

    @Override
    public Car addCar(Car car) {
        return carRepository.save(car);
    }

    @Override
    public Car updateCar(Car car) {
        Optional<Car> carToUpdate = getCar(car.getId());
        if (carToUpdate.isEmpty()) {
            throw new CarNotFoundException(car.getId());
        }
        return carRepository.save(car);
    }

    @Override
    public Optional<Car> getCarByLicencePlate(String licencePlate) {
        return carRepository.getCarByLicencePlate(licencePlate);
    }

    @Override
    public void deleteCar(final Long carId) {
        Car car = getCar(carId)
                .orElseThrow(() -> new CarNotFoundException(carId));
        List<CarRental> rentListCar = carRentalRepository.checkBookingByCar(carId);
        if (!rentListCar.isEmpty()) {
            throw new BusinessException(String.format(
                    "Car with id: %s has an active reservation!", carId));
        }
        carRepository.delete(car);
    }

}
