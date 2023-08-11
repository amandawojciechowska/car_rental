package cap.s42academy.controller;

import cap.s42academy.service.CarRentalService;
import cap.s42academy.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rental")
@RequiredArgsConstructor
public class CarRentalController {

    private final CarRentalService carRentalService;

}
