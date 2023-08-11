package cap.s42academy.repository.custom.impl;

import cap.s42academy.model.Customer;
import cap.s42academy.repository.custom.CustomCustomerRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Optional;

public class CustomCustomerRepositoryImpl implements CustomCustomerRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Customer> getCustomerByDrivingLicence(String drivingLicence) {
        String sql = "SELECT c FROM Customer c WHERE c.drivingLicence = :drivingLicence";
        Query query = entityManager.createQuery(sql, Customer.class);
        query.setParameter("drivingLicence", drivingLicence);
        query.setMaxResults(1);
        return query.getResultList()
                .stream()
                .findFirst();
    }
}
