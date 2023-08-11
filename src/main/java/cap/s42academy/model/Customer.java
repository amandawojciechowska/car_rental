package cap.s42academy.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@EqualsAndHashCode
@Table(name = "CUSTOMER")
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "FIRST_NAME", nullable = false)
    private String firstName;

    @Column(name = "LAST_NAME", nullable = false)
    private String lastName;

    @Column(name = "EMAIL", nullable = false)
    private String email;

    @Column(name = "DRIVING_LICENCE", nullable = false)
    private String drivingLicence;

    public Customer(String firstName, String lastName, String email, String drivingLicence) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.drivingLicence = drivingLicence;
    }
}
