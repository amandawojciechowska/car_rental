package cap.s42academy.repository.custom;

import cap.s42academy.model.Car;
import cap.s42academy.model.CarRental;

import java.util.List;

public interface CustomCarRentalRepository {

    List<CarRental> getHistoryByCar(Car car);

}
