package cap.s42academy.exceptions;

public class CarNotFoundException extends RuntimeException {

    private CarNotFoundException(String message) {
        super(message);
    }

    public CarNotFoundException(Long id) {
        this(String.format("Car with ID=%s was not found", id));
    }

}