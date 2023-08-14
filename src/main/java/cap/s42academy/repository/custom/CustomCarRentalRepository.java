package cap.s42academy.repository.custom;

import cap.s42academy.model.Car;
import cap.s42academy.model.CarRental;
import cap.s42academy.model.Customer;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CustomCarRentalRepository {

    BigDecimal returnCarAndGetRentalFee(CarRental carRental);

    List<CarRental> checkBookingByCustomer(Long customerId);

    List<CarRental> checkBookingByCar(Long carId);

    BigDecimal getTotalIncome();

    Optional<Car> getTheMostRentedCar();

    Optional<Customer> getCustomerWhoRentedTheMostCars();

    List<CarRental> getCarAvailableOnGivenTime(Car car, LocalDate dateFrom, LocalDate dateTo);

    List<CarRental> getCustomerHasRentedCarOnGivenTime(Customer customer, LocalDate dateFrom, LocalDate dateTo);

}
