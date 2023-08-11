package cap.s42academy.exceptions;

public class CustomerNotFoundException extends RuntimeException {

    private CustomerNotFoundException(String message) {
        super(message);
    }

    public CustomerNotFoundException(Long id) {
        this(String.format("Customer with ID=%s was not found", id));
    }

}
