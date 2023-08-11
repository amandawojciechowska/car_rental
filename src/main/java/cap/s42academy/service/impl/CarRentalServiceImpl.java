package cap.s42academy.service.impl;

import cap.s42academy.repository.CarRentalRepository;
import cap.s42academy.service.CarRentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CarRentalServiceImpl implements CarRentalService {

    private final CarRentalRepository carRentalRepository;

}
