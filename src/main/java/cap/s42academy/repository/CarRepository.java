package cap.s42academy.repository;

import cap.s42academy.model.Car;
import cap.s42academy.repository.custom.CustomCarRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long>, CustomCarRepository {

}