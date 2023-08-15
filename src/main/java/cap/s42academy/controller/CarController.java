package cap.s42academy.controller;

import cap.s42academy.exceptions.CarNotFoundException;
import cap.s42academy.model.Car;
import cap.s42academy.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/cars")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    @GetMapping("/{carId}")
    public Car getCar(@PathVariable Long carId) {
        return carService.getCar(carId).orElseThrow(() -> new CarNotFoundException(carId));
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public Car createCar(@RequestBody @Validated Car car) {
        return carService.addCar(car);
    }

    @PutMapping
    public Car editCar(@RequestBody Car car) {
        return carService.updateCar(car);
    }

    @GetMapping(params = {"licencePlate"})
    public Optional<Car> getCarByLicencePlate(@RequestParam("licencePlate") String licencePlate) {
        return carService.getCarByLicencePlate(licencePlate);
    }

    @DeleteMapping("/{carId}")
    @ResponseStatus(NO_CONTENT)
    public void deleteCar(@PathVariable Long carId) {
        carService.deleteCar(carId);
    }

}
