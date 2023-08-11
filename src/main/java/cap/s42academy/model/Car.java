package cap.s42academy.model;

import cap.s42academy.enums.RentalType;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@EqualsAndHashCode
@Table(name = "CAR")
public class Car implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "BRAND", nullable = false)
    private String brand;

    @Column(name = "MODEL", nullable = false)
    private String model;

    @Column(name = "YEAR_OF_PRODUCTION", nullable = false)
    private String yearOfProduction;

    @Column(name = "LICENCE_PLATE", nullable = false)
    private String licencePlate;

    @DecimalMin(value = "10.0")
    @Column(name = "DAILY_RATE")
    private BigDecimal dailyRate;

    @Column(name = "RENTAL_TYPE", nullable = false)
    @Enumerated(EnumType.STRING)
    private RentalType rentalType;

    public Car(String brand, String model, String yearOfProduction, String licencePlate, BigDecimal dailyRate, RentalType rentalType) {
        this.brand = brand;
        this.model = model;
        this.yearOfProduction = yearOfProduction;
        this.licencePlate = licencePlate;
        this.dailyRate = dailyRate;
        this.rentalType = rentalType;
    }
}
