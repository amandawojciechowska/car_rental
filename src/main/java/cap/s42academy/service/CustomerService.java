package cap.s42academy.service;

import cap.s42academy.model.Customer;

import java.util.Optional;

public interface CustomerService {
    Optional<Customer> getCustomer(Long customerId);

    Customer addCustomer(Customer customer);

    Customer updateCustomer(Customer customer);

    Optional<Customer> getCustomerByDrivingLicence(String drivingLicence);

    void deleteCustomer(Long customerId);
}
