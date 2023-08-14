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

    List<CarRental> checkBookingByCustomer(Customer customer);

    List<CarRental> checkBookingByCar(Car car);

    BigDecimal getTotalIncome();

    Optional<Car> getTheMostRentedCar();

    Optional<Customer> getCustomerWhoRentedTheMostCars();

    List<CarRental> getCarAvailableOnGivenTime(Car car, LocalDate dateFrom, LocalDate dateTo);

    List<CarRental> getCustomerHasRentedCarOnGivenTime(Customer customer, LocalDate dateFrom, LocalDate dateTo);

}
