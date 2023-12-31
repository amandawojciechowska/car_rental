package cap.s42academy.service.impl;

import cap.s42academy.exceptions.BusinessException;
import cap.s42academy.model.Car;
import cap.s42academy.model.CarRental;
import cap.s42academy.model.Customer;
import cap.s42academy.repository.CarRentalRepository;
import cap.s42academy.service.CarRentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CarRentalServiceImpl implements CarRentalService {

    private final CarRentalRepository carRentalRepository;

    @Override
    public CarRental addCarRental(CarRental carRental) {
        //walidacje wydzielic do prywatnej metody
        if (carRental.getCar() == null) {
            throw new BusinessException("Car cannot be null");
        }
        if (carRental.getCustomer() == null) {
            throw new BusinessException("Customer cannot be null");
        }
        Boolean isCarAvailable = isCarAvailableOnGivenTime(carRental.getCar(), carRental.getRentalDateFrom(), carRental.getRentalDateTo());
        if (!isCarAvailable) {
            throw new BusinessException(String.format(
                    "In the given dates from: %s and to: %s it is not possible to rent car with id: %s",
                    carRental.getRentalDateFrom(),
                    carRental.getRentalDateTo(),
                    carRental.getCar().getId()));
        }
        Boolean rented = isCustomerHasRentedCarOnGivenTime(carRental.getCustomer(), carRental.getRentalDateFrom(), carRental.getRentalDateTo());
        if (rented) {
            throw new BusinessException(String.format(
                    "In the given dates from: %s and to: %s the customer with id: %s has already rented a car",
                    carRental.getRentalDateFrom(),
                    carRental.getRentalDateTo(),
                    carRental.getCustomer().getId()));
        }
        if (carRental.getDays() < 3 || carRental.getDays() > 90) {
            throw new BusinessException(String.format(
                    "The minimum number of rental days is %s days and the maximum is %s", 3, 90));
        }
        if (carRental.getRentalDateFrom().isBefore(LocalDate.now())) {
            throw new BusinessException("It is not possible to rent a car before date.now()");
        }
        return carRentalRepository.save(carRental);
    }

    @Override
    public BigDecimal returnCarAndGetRentalFee(CarRental carRental) {
        return carRentalRepository.returnCarAndGetRentalFee(carRental);
    }

    @Override
    public BigDecimal getTotalIncome() {
        return carRentalRepository.getTotalIncome();
    }

    @Override
    public Optional<Car> getTheMostRentedCar() {
        return carRentalRepository.getTheMostRentedCar();
    }

    @Override
    public Optional<Customer> getCustomerWhoRentedTheMostCars() {
        return carRentalRepository.getCustomerWhoRentedTheMostCars();
    }

    @Override
    public Boolean isCarAvailableOnGivenTime(Car car, LocalDate dateFrom, LocalDate dateTo) {
        List<CarRental> isNotAvailable = carRentalRepository.isCarAvailableOnGivenTime(car, dateFrom, dateTo);
        if (isNotAvailable.isEmpty()) {
            return true;
        }
        return false;
    }

    @Override
    public Boolean isCustomerHasRentedCarOnGivenTime(Customer customer, LocalDate dateFrom, LocalDate dateTo) {
        List<CarRental> rented = carRentalRepository.isCustomerHasRentedCarOnGivenTime(customer, dateFrom, dateTo);
        if (rented.isEmpty()) {
            return false;
        }
        return true;
    }
}
