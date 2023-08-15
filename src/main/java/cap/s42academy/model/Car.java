package cap.s42academy.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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

    @NotNull
    @NotBlank
    @Column(name = "BRAND", nullable = false)
    private String brand;

    @NotNull
    @NotBlank
    @Column(name = "MODEL", nullable = false)
    private String model;

    @NotNull
    @NotBlank
    @Column(name = "YEAR_OF_PRODUCTION", nullable = false)
    private String yearOfProduction;

    @NotNull
    @NotBlank
    @Column(name = "LICENCE_PLATE", nullable = false)
    private String licencePlate;

    @DecimalMin(value = "10.0")
    @Column(name = "DAILY_RATE")
    private BigDecimal dailyRate;

    public Car(String brand, String model, String yearOfProduction, String licencePlate, BigDecimal dailyRate) {
        this.brand = brand;
        this.model = model;
        this.yearOfProduction = yearOfProduction;
        this.licencePlate = licencePlate;
        this.dailyRate = dailyRate;
    }
}
