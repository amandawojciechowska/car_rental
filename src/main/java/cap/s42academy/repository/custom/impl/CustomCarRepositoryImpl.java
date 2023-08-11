package cap.s42academy.repository.custom.impl;

import cap.s42academy.model.Car;
import cap.s42academy.repository.custom.CustomCarRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Optional;

public class CustomCarRepositoryImpl implements CustomCarRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Car> getCarByLicencePlate(final String licencePlate) {
        String sql = "SELECT c FROM Car c WHERE c.licencePlate = :licencePlate";
        Query query = entityManager.createQuery(sql, Car.class);
        query.setParameter("licencePlate", licencePlate);
        query.setMaxResults(1);
        return query.getResultList()
                .stream()
                .findFirst();
    }

}
