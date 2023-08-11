package cap.s42academy.repository;

import cap.s42academy.model.Car;
import cap.s42academy.repository.custom.CustomCarRentalRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRentalRepository extends JpaRepository<Car, Long>, CustomCarRentalRepository {

}
