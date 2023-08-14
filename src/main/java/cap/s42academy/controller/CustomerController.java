package cap.s42academy.controller;

import cap.s42academy.exceptions.CustomerNotFoundException;
import cap.s42academy.model.Customer;
import cap.s42academy.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/{customerId}")
    public Customer getCustomer(@PathVariable Long customerId) {
        return customerService.getCustomer(customerId).orElseThrow(() -> new CustomerNotFoundException(customerId));
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public Customer createCustomer(@RequestBody @Validated Customer customer) {
        return customerService.addCustomer(customer);
    }

    @PutMapping
    public Customer editCustomer(@RequestBody Customer customer) {
        return customerService.updateCustomer(customer);
    }

    @GetMapping(params = {"drivingLicence"})
    public Optional<Customer> getCustomerByDrivingLicence(@RequestParam("drivingLicence") String drivingLicence) {
        return customerService.getCustomerByDrivingLicence(drivingLicence);
    }

    @DeleteMapping("/{customerId}")
    @ResponseStatus(NO_CONTENT)
    public void deleteCustomer(@PathVariable Long customerId) {
        customerService.deleteCustomer(customerId);
    }

}
