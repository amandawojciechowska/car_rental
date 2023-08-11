package cap.s42academy.repository.custom.impl;

import cap.s42academy.model.Car;
import cap.s42academy.model.CarRental;
import cap.s42academy.repository.custom.CustomCarRentalRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

public class CustomCarRentalRepositoryImpl implements CustomCarRentalRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<CarRental> getHistoryByCar(Car car) {
        String sql = "SELECT cr FROM CarRental cr JOIN cr.car c WHERE c.id = :carId";
        Query query = entityManager.createQuery(sql, CarRental.class);
        query.setParameter("carId", car.getId());
        return (List<CarRental>) query.getResultList();
    }
}
