package cap.s42academy.repository.custom;

import cap.s42academy.model.Customer;

import java.util.Optional;

public interface CustomCustomerRepository {

    Optional<Customer> getCustomerByDrivingLicence(String drivingLicence);

}
