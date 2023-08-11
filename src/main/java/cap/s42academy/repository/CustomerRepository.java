package cap.s42academy.repository;

import cap.s42academy.model.Customer;
import cap.s42academy.repository.custom.CustomCustomerRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long>, CustomCustomerRepository {

}
