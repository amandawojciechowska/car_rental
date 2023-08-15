package cap.s42academy.service;

import cap.s42academy.model.Car;
import cap.s42academy.model.CarRental;
import cap.s42academy.model.Customer;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CarRentalService {

    CarRental addCarRental(CarRental carRental);

    BigDecimal returnCarAndGetRentalFee(CarRental carRental);

    BigDecimal getTotalIncome();

    Optional<Car> getTheMostRentedCar();

    Optional<Customer> getCustomerWhoRentedTheMostCars();

    Boolean isCarAvailableOnGivenTime(Car car, LocalDate dateFrom, LocalDate dateTo);

    Boolean isCustomerHasRentedCarOnGivenTime(Customer customer, LocalDate dateFrom, LocalDate dateTo);

}
