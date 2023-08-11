package cap.s42academy.controller;

import cap.s42academy.exceptions.CarNotFoundException;
import cap.s42academy.model.Car;
import cap.s42academy.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/cars")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    @GetMapping("/{carId}")
    public Car getCar(@PathVariable long carId) {
        return carService.getCar(carId).orElseThrow(() -> new CarNotFoundException(carId));
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public Car createCar(@RequestBody @Validated Car car) {
        return carService.addCar(car);
    }

    @PutMapping
    public Car editPost(@RequestBody Car car) {
        return carService.updateCar(car);
    }

}
