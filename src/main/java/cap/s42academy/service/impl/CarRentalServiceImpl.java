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
        if (carRental.getCar() == null) {
            throw new BusinessException("Car cannot be null");
        }
        if (carRental.getCustomer() == null) {
            throw new BusinessException("Customer cannot be null");
        }
        //walidacja wypozyczenia auta musi byc wolne
        //customer nie moze miec dwoch autow
        //walidacja dat przeszlosc
        //walidacja dni
        return carRentalRepository.save(carRental);
    }

    @Override
    public BigDecimal returnCarAndGetRentalFee(CarRental carRental) {
        return carRentalRepository.returnCarAndGetRentalFee(carRental);
    }

    @Override
    public List<CarRental> checkBookingByCustomer(Customer customer) {
        return carRentalRepository.checkBookingByCustomer(customer);
    }

    @Override
    public List<CarRental> checkBookingByCar(Car car) {
        return carRentalRepository.checkBookingByCar(car);
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
    public List<CarRental> getCarAvailableOnGivenTime(Car car, LocalDate dateFrom, LocalDate dateTo) {
        return carRentalRepository.getCarAvailableOnGivenTime(car, dateFrom, dateTo);
    }

    @Override
    public List<CarRental> getCustomerHasRentedCarOnGivenTime(Customer customer, LocalDate dateFrom, LocalDate dateTo) {
        return carRentalRepository.getCustomerHasRentedCarOnGivenTime(customer, dateFrom, dateTo);
    }
}
