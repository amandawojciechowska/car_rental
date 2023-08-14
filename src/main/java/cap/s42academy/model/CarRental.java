package cap.s42academy.model;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@EqualsAndHashCode
@Table(name = "CAR_RENTAL")
public class CarRental implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "CAR_ID", nullable = false)
    private Car car;

    @ManyToOne
    @JoinColumn(name = "CUSTOMER_ID", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Customer customer;

    @Min(3)
    @Max(90)
    @Column(name = "DAYS", nullable = false)
    private Long days;

    @Column(name = "RENTAL_DATE_FROM", nullable = false)
    private LocalDate rentalDateFrom;

    @Column(name = "RENTAL_DATE_TO")
    private LocalDate rentalDateTo;

    public CarRental(Car car, Customer customer, Long days, LocalDate rentalDateFrom, LocalDate rentalDateTo) {
        this.car = car;
        this.customer = customer;
        this.days = days;
        this.rentalDateFrom = rentalDateFrom;
        this.rentalDateTo = rentalDateTo;
    }
}