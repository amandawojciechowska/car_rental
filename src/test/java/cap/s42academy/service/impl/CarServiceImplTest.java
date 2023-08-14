package cap.s42academy.service.impl;

import cap.s42academy.exceptions.CarNotFoundException;
import cap.s42academy.model.Car;
import cap.s42academy.service.CarRentalService;
import cap.s42academy.service.CarService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.*;

import static cap.s42academy.SampleTestDataFactory.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class CarServiceImplTest {

    @Autowired
    private CarService carService;
    @Autowired
    private CarRentalService carRentalService;


    @Test
    void shouldReturnCar_whenGetCarById() {
        // given
        Car car1 = car1();

        // when
        Car savedCar = this.carService.addCar(car1);
        Optional<Car> foundCar = this.carService.getCar(savedCar.getId());

        // then
        Assertions.assertAll(() -> {
            assertFalse(foundCar.isEmpty());
            assertEquals(savedCar.getId(),
                    foundCar.get()
                            .getId());
            assertEquals(savedCar.getBrand(),
                    foundCar.get()
                            .getBrand());
            assertEquals(savedCar.getModel(),
                    foundCar.get()
                            .getModel());
            assertEquals(savedCar.getYearOfProduction(),
                    foundCar.get()
                            .getYearOfProduction());
            assertEquals(savedCar.getLicencePlate(),
                    foundCar.get()
                            .getLicencePlate());
            assertEquals(savedCar.getDailyRate(),
                    foundCar.get()
                            .getDailyRate());
        });
    }

    @Test
    void shouldReturnEmptyOptional_whenFindingCarById_andItDoesNotExist() {
        //given
        //when
        Optional<Car> foundCar = this.carService.getCar(Long.MAX_VALUE);

        //then
        assertTrue(foundCar.isEmpty());
    }

    @Test
    void shouldReturnCar_whenAddingNewCar() {
        // given
        Car car1 = car1();

        // when
        Car savedCar = this.carService.addCar(car1);

        // then
        Assertions.assertAll(() -> {
            assertNotNull(savedCar);
            assertNotNull(savedCar.getId());
            assertEquals(savedCar.getBrand(), car1.getBrand());
            assertEquals(savedCar.getDailyRate(), car1.getDailyRate());
        });
    }

    @Test
    void shouldUpdateCar_whenUpdatingCar() {
        // given
        Car car1 = this.carService.addCar(car1());

        // when
        car1.setDailyRate(new BigDecimal("70.00"));
        this.carService.updateCar(car1);
        Optional<Car> updatedCar = this.carService.getCar(car1.getId());

        //then
        Assertions.assertAll(() -> {
            assertThat(updatedCar).isPresent();
            assertEquals(new BigDecimal("70.00"),
                    updatedCar.get()
                            .getDailyRate());
            assertEquals(car1.getId(),
                    updatedCar.get()
                            .getId());
        });
    }

    @Test
    void shouldReturnCar_whenFindingCarByLicencePlate() {
        // given
        Car car1 = car1();
        Car car2 = car2();

        // when
        this.carService.addCar(car1);
        this.carService.addCar(car2);
        Optional<Car> foundCar = this.carService.getCarByLicencePlate("ONYXD123");

        // then
        Assertions.assertAll(() -> {
            assertFalse(foundCar.isEmpty());
            assertEquals("ONYXD123",
                    foundCar.get()
                            .getLicencePlate());
            assertEquals("AUDI",
                    foundCar.get()
                            .getBrand());
        });
    }

    @Test
    void shouldReturnEmptyOptional_whenFindingCarByLicencePlate_andItDoesNotExist() {
        //given
        this.carService.addCar(car2());

        //when
        Optional<Car> foundCar = this.carService.getCarByLicencePlate("ONYXD123");

        //then
        assertTrue(foundCar.isEmpty());
    }

    @Test
    void shouldDeleteCar_whenDeletingCarById() {
        // given
        Car car1 = car1();

        // when
        Car savedCar = this.carService.addCar(car1);
        this.carService.deleteCar(savedCar.getId());

        // then
        Assertions.assertAll(() -> {
            assertTrue(carService.getCar(savedCar.getId())
                    .isEmpty());
        });
    }

    @Test
    void shouldThrowCarNotFoundException_whenDeletingCar() {
        // given
        // when
        // then
        assertThatThrownBy(() -> this.carService.deleteCar(Long.MAX_VALUE)).isInstanceOf(
                CarNotFoundException.class);
    }

}