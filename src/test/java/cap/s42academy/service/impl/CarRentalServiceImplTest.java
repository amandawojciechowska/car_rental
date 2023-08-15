package cap.s42academy.service.impl;

import cap.s42academy.exceptions.BusinessException;
import cap.s42academy.model.Car;
import cap.s42academy.model.CarRental;
import cap.s42academy.model.Customer;
import cap.s42academy.service.CarRentalService;
import cap.s42academy.service.CarService;
import cap.s42academy.service.CustomerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static cap.s42academy.SampleTestDataFactory.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class CarRentalServiceImplTest {

    @Autowired
    private CarRentalService carRentalService;

    @Autowired
    private CarService carService;

    @Autowired
    private CustomerService customerService;

    @Test
    void shouldReturnCarRental_whenAddingNewCarRental() {
        // given
        Car car1 = this.carService.addCar(car1());
        Customer customer1 = this.customerService.addCustomer(customer1());
        CarRental carRental1 = carRental(car1, customer1);

        // when
        CarRental savedCarRental = this.carRentalService.addCarRental(carRental1);

        // then
        Assertions.assertAll(() -> {
            assertNotNull(savedCarRental);
            assertNotNull(savedCarRental.getId());
            assertEquals(savedCarRental.getCar(), car1);
            assertEquals(savedCarRental.getCustomer(), customer1);
            assertEquals(savedCarRental.getDays(), carRental1.getDays());
        });
    }

    @Test
    void shouldThrowBusinessException_whenAddingNewNotAvailableCar() {
        // given
        Car car1 = this.carService.addCar(car1());
        Customer customer1 = this.customerService.addCustomer(customer1());
        Customer customer2 = this.customerService.addCustomer(customer2());
        CarRental carRental1 = carRental(car1, customer1);
        CarRental carRental2 = carRental(car1, customer2);

        // when
        this.carRentalService.addCarRental(carRental1);

        // then
        assertThatThrownBy(() -> this.carRentalService.addCarRental(carRental2)).isInstanceOf(
                BusinessException.class);
    }

    @Test
    void shouldThrowBusinessException_whenAddingNewCarRentalWithCostumerThatAlreadyRentACar() {
        // given
        Car car1 = this.carService.addCar(car1());
        Car car2 = this.carService.addCar(car2());
        Customer customer1 = this.customerService.addCustomer(customer1());
        CarRental carRental1 = carRental(car1, customer1);
        CarRental carRental2 = carRental(car2, customer1);

        // when
        this.carRentalService.addCarRental(carRental1);

        // then
        assertThatThrownBy(() -> this.carRentalService.addCarRental(carRental2)).isInstanceOf(
                BusinessException.class);
    }

    @Test
    void shouldThrowBusinessException_whenAddingNewCarRentalWithBellowMinDays() {
        // given
        Car car1 = this.carService.addCar(car1());
        Customer customer1 = this.customerService.addCustomer(customer1());
        CarRental carRental1 = carRental(car1, customer1, LocalDate.now().plusDays(1), LocalDate.now().plusDays(2), 1L);

        // when
        // then
        assertThatThrownBy(() -> this.carRentalService.addCarRental(carRental1)).isInstanceOf(
                BusinessException.class);
    }

    @Test
    void shouldThrowBusinessException_whenAddingNewCarRentalWithOverMaxDays() {
        // given
        Car car1 = this.carService.addCar(car1());
        Customer customer1 = this.customerService.addCustomer(customer1());
        CarRental carRental1 = carRental(car1, customer1, LocalDate.now().plusDays(1), LocalDate.now().plusDays(92), 91L);

        // when
        // then
        assertThatThrownBy(() -> this.carRentalService.addCarRental(carRental1)).isInstanceOf(
                BusinessException.class);
    }

    @Test
    void shouldReturnRentalFee() {
        // given
        Car car1 = this.carService.addCar(car1());
        Customer customer1 = this.customerService.addCustomer(customer1());
        CarRental carRental1 = carRental(car1, customer1);
        CarRental savedCarRental = this.carRentalService.addCarRental(carRental1);

        // when
        BigDecimal rentalFee = this.carRentalService.returnCarAndGetRentalFee(savedCarRental);

        // then
        Assertions.assertAll(() -> {
            assertTrue(BigDecimal.valueOf(560).compareTo(rentalFee) == 0);
        });
    }

    @Test
    void shouldGetTheMostRentedCar() {
        // given
        Car car1 = this.carService.addCar(car1());
        Car car2 = this.carService.addCar(car2());
        this.carService.addCar(car3());
        Customer customer1 = this.customerService.addCustomer(customer1());
        Customer customer2 = this.customerService.addCustomer(customer2());
        CarRental carRental1 = carRental(car1, customer1, LocalDate.now().plusDays(1), LocalDate.now().plusDays(6), 5L);
        CarRental carRental2 = carRental(car1, customer2, LocalDate.now().plusDays(10), LocalDate.now().plusDays(20), 10L);
        CarRental carRental3 = carRental(car2, customer1, LocalDate.now().plusDays(10), LocalDate.now().plusDays(20), 10L);

        // when
        this.carRentalService.addCarRental(carRental1);
        this.carRentalService.addCarRental(carRental2);
        this.carRentalService.addCarRental(carRental3);
        Optional<Car> optionalCar = this.carRentalService.getTheMostRentedCar();

        // then
        Assertions.assertAll(() -> {
            assertEquals(optionalCar.get(), car1);
        });
    }

    @Test
    void shouldGetCustomerWhoRentedTheMostCars() {
        // given
        Car car1 = this.carService.addCar(car1());
        Car car2 = this.carService.addCar(car2());
        Customer customer1 = this.customerService.addCustomer(customer1());
        Customer customer2 = this.customerService.addCustomer(customer2());
        CarRental carRental1 = carRental(car1, customer1, LocalDate.now().plusDays(1), LocalDate.now().plusDays(6), 5L);
        CarRental carRental2 = carRental(car1, customer2, LocalDate.now().plusDays(10), LocalDate.now().plusDays(20), 10L);
        CarRental carRental3 = carRental(car2, customer1, LocalDate.now().plusDays(10), LocalDate.now().plusDays(20), 10L);

        // when
        this.carRentalService.addCarRental(carRental1);
        this.carRentalService.addCarRental(carRental2);
        this.carRentalService.addCarRental(carRental3);
        Optional<Customer> optionalCustomer = this.carRentalService.getCustomerWhoRentedTheMostCars();

        // then
        Assertions.assertAll(() -> {
            assertEquals(optionalCustomer.get(), customer1);
        });
    }

    @Test
    void shouldCheckCarAvailableOnGivenTime() {
        // given
        Car car1 = this.carService.addCar(car1());
        Car car2 = this.carService.addCar(car2());
        Customer customer1 = this.customerService.addCustomer(customer1());
        CarRental carRental1 = carRental(car1, customer1, LocalDate.now().plusDays(1), LocalDate.now().plusDays(6), 5L);
        this.carRentalService.addCarRental(carRental1);

        // when
        Boolean isCarAvailable1 = this.carRentalService.isCarAvailableOnGivenTime(car1, LocalDate.now(), LocalDate.now().plusDays(5));
        Boolean isCarAvailable2 = this.carRentalService.isCarAvailableOnGivenTime(car1, LocalDate.now().plusDays(2), LocalDate.now().plusDays(5));
        Boolean isCarAvailable3 = this.carRentalService.isCarAvailableOnGivenTime(car1, LocalDate.now().plusDays(2), LocalDate.now().plusDays(9));
        Boolean isCarAvailable4 = this.carRentalService.isCarAvailableOnGivenTime(car1, LocalDate.now(), LocalDate.now().plusDays(9));
        Boolean isCarAvailable5 = this.carRentalService.isCarAvailableOnGivenTime(car1, LocalDate.now().plusDays(10), LocalDate.now().plusDays(15));
        Boolean isCarAvailable6 = this.carRentalService.isCarAvailableOnGivenTime(car2, LocalDate.now(), LocalDate.now().plusDays(15));
        // then
        Assertions.assertAll(() -> {
            assertFalse(isCarAvailable1);
            assertFalse(isCarAvailable2);
            assertFalse(isCarAvailable3);
            assertFalse(isCarAvailable4);
            assertTrue(isCarAvailable5);
            assertTrue(isCarAvailable6);
        });
    }

    @Test
    void shouldCheckCustomerHasRentedCarOnGivenTime() {
        // given
        Car car1 = this.carService.addCar(car1());
        Customer customer1 = this.customerService.addCustomer(customer1());
        Customer customer2 = this.customerService.addCustomer(customer2());
        CarRental carRental1 = carRental(car1, customer1, LocalDate.now().plusDays(1), LocalDate.now().plusDays(6), 5L);
        this.carRentalService.addCarRental(carRental1);

        // when
        Boolean hasRented1 = this.carRentalService.isCustomerHasRentedCarOnGivenTime(customer1, LocalDate.now(), LocalDate.now().plusDays(5));
        Boolean hasRented2 = this.carRentalService.isCustomerHasRentedCarOnGivenTime(customer1, LocalDate.now().plusDays(2), LocalDate.now().plusDays(5));
        Boolean hasRented3 = this.carRentalService.isCustomerHasRentedCarOnGivenTime(customer1, LocalDate.now().plusDays(2), LocalDate.now().plusDays(9));
        Boolean hasRented4 = this.carRentalService.isCustomerHasRentedCarOnGivenTime(customer1, LocalDate.now(), LocalDate.now().plusDays(9));
        Boolean hasRented5 = this.carRentalService.isCustomerHasRentedCarOnGivenTime(customer1, LocalDate.now().plusDays(10), LocalDate.now().plusDays(15));
        Boolean hasRented6 = this.carRentalService.isCustomerHasRentedCarOnGivenTime(customer2, LocalDate.now(), LocalDate.now().plusDays(15));
        // then
        Assertions.assertAll(() -> {
            assertTrue(hasRented1);
            assertTrue(hasRented2);
            assertTrue(hasRented3);
            assertTrue(hasRented4);
            assertFalse(hasRented5);
            assertFalse(hasRented6);
        });
    }
}
