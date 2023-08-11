package cap.s42academy.service.impl;

import cap.s42academy.exceptions.CustomerNotFoundException;
import cap.s42academy.model.Customer;
import cap.s42academy.service.CarRentalService;
import cap.s42academy.service.CustomerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static cap.s42academy.SampleTestDataFactory.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class CustomerServiceImplTest {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private CarRentalService carRentalService;


    @Test
    void shouldReturnCustomer_whenGetCustomerById() {
        // given
        Customer customer1 = customer1();

        // when
        Customer savedCustomer = this.customerService.addCustomer(customer1);
        Optional<Customer> foundCustomer = this.customerService.getCustomer(savedCustomer.getId());

        // then
        Assertions.assertAll(() -> {
            assertFalse(foundCustomer.isEmpty());
            assertEquals(savedCustomer.getId(),
                    foundCustomer.get()
                            .getId());
            assertEquals(savedCustomer.getFirstName(),
                    foundCustomer.get()
                            .getFirstName());
            assertEquals(savedCustomer.getLastName(),
                    foundCustomer.get()
                            .getLastName());
            assertEquals(savedCustomer.getEmail(),
                    foundCustomer.get()
                            .getEmail());
            assertEquals(savedCustomer.getDrivingLicence(),
                    foundCustomer.get()
                            .getDrivingLicence());
        });
    }

    @Test
    void shouldReturnEmptyOptional_whenFindingCustomerById_andItDoesNotExist() {
        //given
        //when
        Optional<Customer> foundCustomer = this.customerService.getCustomer(Long.MAX_VALUE);

        //then
        assertTrue(foundCustomer.isEmpty());
    }

    @Test
    void shouldReturnCustomer_whenAddingNewCustomer() {
        // given
        Customer customer1 = customer1();

        // when
        Customer savedCustomer = this.customerService.addCustomer(customer1);

        // then
        Assertions.assertAll(() -> {
            assertNotNull(savedCustomer);
            assertNotNull(savedCustomer.getId());
            assertEquals(savedCustomer.getEmail(), customer1.getEmail());
            assertEquals(savedCustomer.getDrivingLicence(), customer1.getDrivingLicence());
        });
    }

    @Test
    void shouldUpdateCustomer_whenUpdatingCustomer() {
        // given
        Customer customer1 = this.customerService.addCustomer(customer1());

        // when
        customer1.setEmail("newMail123@gmail.com");
        this.customerService.updateCustomer(customer1);
        Optional<Customer> updatedCustomer = this.customerService.getCustomer(customer1.getId());

        //then
        Assertions.assertAll(() -> {
            assertThat(updatedCustomer).isPresent();
            assertEquals("newMail123@gmail.com",
                    updatedCustomer.get()
                            .getEmail());
            assertEquals(customer1.getId(),
                    updatedCustomer.get()
                            .getId());
        });
    }

    @Test
    void shouldReturnCustomer_whenFindingCustomerByDrivingLicence() {
        // given
        Customer customer1 = customer1();
        Customer customer2 = customer2();

        // when
        this.customerService.addCustomer(customer1);
        this.customerService.addCustomer(customer2);
        Optional<Customer> foundCustomer = this.customerService.getCustomerByDrivingLicence("12345Q12BN");

        // then
        Assertions.assertAll(() -> {
            assertFalse(foundCustomer.isEmpty());
            assertEquals("12345Q12BN",
                    foundCustomer.get()
                            .getDrivingLicence());
        });
    }

    @Test
    void shouldReturnEmptyOptional_whenFindingCustomerByDrivingLicence_andItDoesNotExist() {
        //given
        Customer customer2 = customer2();

        //when
        Optional<Customer> foundCustomer = this.customerService.getCustomerByDrivingLicence("12345Q12BN");

        //then
        assertTrue(foundCustomer.isEmpty());
    }

    @Test
    void shouldDeleteCustomer_whenDeletingCustomerById() {
        // given
        Customer customer1 = customer1();

        // when
        Customer savedCustomer = this.customerService.addCustomer(customer1);
        this.customerService.deleteCustomer(savedCustomer.getId());

        // then
        Assertions.assertAll(() -> {
            assertTrue(customerService.getCustomer(savedCustomer.getId())
                    .isEmpty());
        });
    }

    @Test
    void shouldThrowCustomerNotFoundException_whenDeletingCustomer() {
        // given
        // when
        // then
        assertThatThrownBy(() -> this.customerService.deleteCustomer(Long.MAX_VALUE)).isInstanceOf(
                CustomerNotFoundException.class);
    }

}
