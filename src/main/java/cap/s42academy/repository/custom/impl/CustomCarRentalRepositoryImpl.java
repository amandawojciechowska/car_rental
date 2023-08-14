package cap.s42academy.repository.custom.impl;

import cap.s42academy.model.Car;
import cap.s42academy.model.CarRental;
import cap.s42academy.model.Customer;
import cap.s42academy.repository.custom.CustomCarRentalRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class CustomCarRentalRepositoryImpl implements CustomCarRentalRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public BigDecimal returnCarAndGetRentalFee(CarRental carRental) {
        String sql = "SELECT sum(c.daily_rate * cr.days) AS daily_rate_sum FROM car_rental cr JOIN car c ON c.id = cr.car_id WHERE cr.id = :carRentalId";
        Query query = entityManager.createNativeQuery(sql, BigDecimal.class);
        query.setParameter("carRentalId", carRental.getId());
        return (BigDecimal) query.getSingleResult();
    }

    @Override
    public List<CarRental> checkBookingByCustomer(Customer customer) {
        String sql = "SELECT cr FROM CarRental cr JOIN cr.customer c WHERE c.id = :customerId and cr.rentalDateTo >= :dateNow";
        Query query = entityManager.createQuery(sql, CarRental.class);
        query.setParameter("customerId", customer.getId());
        query.setParameter("dateNow", LocalDate.now());
        return (List<CarRental>) query.getResultList();
    }

    @Override
    public List<CarRental> checkBookingByCar(Car car) {
        String sql = "SELECT cr FROM CarRental cr JOIN cr.car c WHERE c.id = :carId and cr.rentalDateTo >= :dateNow";
        Query query = entityManager.createQuery(sql, CarRental.class);
        query.setParameter("carId", car.getId());
        query.setParameter("dateNow", LocalDate.now());
        return (List<CarRental>) query.getResultList();
    }

    @Override
    public BigDecimal getTotalIncome() {
        String sql = "SELECT sum(c.dailyRate * cr.days) FROM CarRental cr JOIN cr.car c";
        Query query = entityManager.createQuery(sql, BigDecimal.class);
        return (BigDecimal) query.getSingleResult();
    }

    @Override
    public Optional<Car> getTheMostRentedCar() {
        String sql = "SELECT cr.car FROM CarRental cr GROUP BY cr.car ORDER BY count(cr) DESC";
        Query query = entityManager.createQuery(sql, Car.class);
        query.setMaxResults(1);
        return query.getResultList()
                .stream()
                .findFirst();
    }

    @Override
    public Optional<Customer> getCustomerWhoRentedTheMostCars() {
        String sql = "SELECT cr.customer FROM CarRental cr GROUP BY cr.customer ORDER BY count(cr) DESC";
        Query query = entityManager.createQuery(sql, Customer.class);
        query.setMaxResults(1);
        return query.getResultList()
                .stream()
                .findFirst();
    }

    @Override
    public List<CarRental> getCarAvailableOnGivenTime(Car car, LocalDate dateFrom, LocalDate dateTo) {
        String sql = "SELECT cr FROM car_rental cr WHERE (cr.car_id = :carId and ((cr.rental_date_from <= :dateFrom and cr.rental_date_to > :dateTo) or (cr.rental_date_to >= :dateFrom and cr.rental_date_to <= :dateTo) or (cr.rental_date_from >= :dateFrom and cr.rental_date_from <= :dateTo)))";
        Query query = entityManager.createNativeQuery(sql, CarRental.class);
        query.setParameter("carId", car.getId());
        query.setParameter("dateFrom", dateFrom);
        query.setParameter("dateTo", dateTo);
        return (List<CarRental>) query.getResultList();
    }

    @Override
    public List<CarRental> getCustomerHasRentedCarOnGivenTime(Customer customer, LocalDate dateFrom, LocalDate dateTo) {
        String sql = "SELECT cr FROM car_rental cr WHERE (cr.customer_id = :customerId and ((cr.rental_date_from <= :dateFrom and cr.rental_date_to > :dateTo) or (cr.rental_date_to >= :dateFrom and cr.rental_date_to <= :dateTo) or (cr.rental_date_from >= :dateFrom and cr.rental_date_from <= :dateTo)))";
        Query query = entityManager.createNativeQuery(sql, CarRental.class);
        query.setParameter("customerId", customer.getId());
        query.setParameter("dateFrom", dateFrom);
        query.setParameter("dateTo", dateTo);
        return (List<CarRental>) query.getResultList();
    }

}
