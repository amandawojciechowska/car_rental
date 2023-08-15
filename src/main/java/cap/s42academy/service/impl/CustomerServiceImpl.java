package cap.s42academy.service.impl;

import cap.s42academy.exceptions.BusinessException;
import cap.s42academy.exceptions.CustomerNotFoundException;
import cap.s42academy.model.CarRental;
import cap.s42academy.model.Customer;
import cap.s42academy.repository.CarRentalRepository;
import cap.s42academy.repository.CustomerRepository;
import cap.s42academy.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CarRentalRepository carRentalRepository;

    @Override
    @Transactional(readOnly = true)
    public Optional<Customer> getCustomer(Long customerId) {
        return customerRepository.findById(customerId);
    }

    @Override
    public Customer addCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        Optional<Customer> customerToUpdate = getCustomer(customer.getId());
        if (customerToUpdate.isEmpty()) {
            throw new CustomerNotFoundException(customer.getId());
        }
        return customerRepository.save(customer);
    }

    @Override
    public Optional<Customer> getCustomerByDrivingLicence(String drivingLicence) {
        return customerRepository.getCustomerByDrivingLicence(drivingLicence);
    }

    @Override
    public void deleteCustomer(Long customerId) {
        Customer customer = getCustomer(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));
        List<CarRental> rentListCustomer = carRentalRepository.checkBookingByCustomer(customerId);
        if (!rentListCustomer.isEmpty()) {
            throw new BusinessException(String.format(
                    "Customer with id: %s has an active reservation!", customerId));
        }
        customerRepository.delete(customer);
    }

}
