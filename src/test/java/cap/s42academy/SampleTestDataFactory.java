package cap.s42academy;

import cap.s42academy.model.Car;
import cap.s42academy.model.CarRental;
import cap.s42academy.model.Customer;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Random;

import static java.util.UUID.randomUUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SampleTestDataFactory {

    private static final Random rand = new Random();
    private static Long rentalDaysCounter = 7L;

    public static final LocalDate DATE_NOW = LocalDate.now();
    public static final LocalDate DATE_FROM = LocalDate.now().plusDays(5L);
    public static final LocalDate DATE_TO = DATE_FROM.plusDays(rentalDaysCounter);


    public static Car car1() {
        return new Car("AUDI", "Q6", "2020", "ONYXD123", new BigDecimal("80.00"));
    }

    public static Car car2() {
        return new Car("BMW", "X7", "2022", "ONYXD666", new BigDecimal("110.00"));
    }

    public static Car car3() {
        return new Car("LEXUS", "LS", "2019", "ONYXD978", new BigDecimal("90.00"));
    }

    public static Customer customer1() {
        return new Customer(randomUUID().toString(), randomUUID().toString(), randomUUID().toString(), "12345Q12BN");
    }

    public static Customer customer2() {
        return new Customer(randomUUID().toString(), randomUUID().toString(), randomUUID().toString(), "143545QXBN");
    }

    public static CarRental carRental(Car car, Customer customer) {
        return new CarRental(car, customer, rentalDaysCounter, DATE_FROM, DATE_TO);
    }
}
