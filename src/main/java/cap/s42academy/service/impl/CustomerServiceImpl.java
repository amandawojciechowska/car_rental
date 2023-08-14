package cap.s42academy.service.impl;

import cap.s42academy.exceptions.CustomerNotFoundException;
import cap.s42academy.model.Customer;
import cap.s42academy.repository.CustomerRepository;
import cap.s42academy.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

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
        //dodac walidacje, ze jezeli klient ma rezerwacje aktualna badz w przyszlosci nie mozna go usunac
        Customer customer = getCustomer(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));
        customerRepository.delete(customer);
    }

}
