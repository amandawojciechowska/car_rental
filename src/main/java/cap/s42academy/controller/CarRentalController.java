package cap.s42academy.controller;

import cap.s42academy.model.Car;
import cap.s42academy.model.CarRental;
import cap.s42academy.model.Customer;
import cap.s42academy.service.CarRentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Optional;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/rental")
@RequiredArgsConstructor
public class CarRentalController {

    private final CarRentalService carRentalService;

    @PostMapping
    @ResponseStatus(CREATED)
    public CarRental createCustomer(@RequestBody @Validated CarRental carRental) {
        return carRentalService.addCarRental(carRental);
    }

    @PutMapping
    public BigDecimal returnCarAndGetRentalFee(@RequestBody CarRental carRental) {
        return carRentalService.returnCarAndGetRentalFee(carRental);
    }

    @GetMapping("/income")
    public BigDecimal getTotalIncome() {
        return carRentalService.getTotalIncome();
    }

    @GetMapping("/car")
    public Optional<Car> getTheMostRentedCar() {
        return carRentalService.getTheMostRentedCar();
    }

    @GetMapping("/customer")
    public Optional<Customer> getCustomerWhoRentedTheMostCars() {
        return carRentalService.getCustomerWhoRentedTheMostCars();
    }

}
